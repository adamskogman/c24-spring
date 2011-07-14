package biz.c24.io.spring.integration.samples.fpml;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.fpml.x2010.fpml49.FpMLElement;
import org.fpml.x2010.fpml49.FxOptionLeg;
import org.fpml.x2010.fpml49.FxOptionPremium;
import org.fpml.x2010.fpml49.NotificationMessageHeader;
import org.fpml.x2010.fpml49.SettlementInstruction;
import org.fpml.x2010.fpml49.TradeConfirmed;
import org.fpml.x2010.fpml49.TradeHeader;
import org.springframework.core.io.Resource;
import org.springframework.integration.Message;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.MessageBuilder;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.Element;
import biz.c24.io.api.data.ISO8601Date;
import biz.c24.io.api.data.ISO8601DateTime;
import biz.c24.io.api.presentation.Sink;
import biz.c24.io.api.presentation.Source;
import biz.c24.io.spring.sink.OutputType;
import biz.c24.io.spring.sink.SinkFactory;

public class PreRenderingFpmlGenerator extends MessageProducerSupport {

	private static final int ITERATIONS = 1000;

	private static final int THREADS = 4;

	Resource base;
	SinkFactory sinkFactory;

	class Generator implements Runnable {

		List<Object> payloads;

		public void run() {

			System.out.println("Starting!");

			for (Object payload : payloads) {

				Message<?> message = MessageBuilder.withPayload(payload)
						.build();

				sendMessage(message);
			}

			System.out.println("Done!");

		}

	}

	private static void breakFpml(TradeConfirmed tradeConfirmed) {

		tradeConfirmed.setHeader(null);

	}

	@Override
	protected void doStart() {
		super.doStart();

		try {
			List<Generator> generators = preRender();

			for (Generator generator : generators) {
				new Thread(generator).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<Generator> preRender() throws Exception {

		List<Generator> result = new ArrayList<Generator>(THREADS);

		final TradeConfirmed tradeConfirmed = readTradeConfirmed();

		ExecutorCompletionService<Generator> completionService = new ExecutorCompletionService<PreRenderingFpmlGenerator.Generator>(
				Executors.newFixedThreadPool(THREADS));

		for (int i = 0; i < THREADS; i++) {
			completionService
					.submit(new Callable<PreRenderingFpmlGenerator.Generator>() {

						public Generator call() throws Exception {
							System.out.println("Rendering... ");
							
							OutputType ot = OutputType.BYTE_ARRAY;
							Random rand = new Random();
							TradeConfirmed myTradeConfirmed = (TradeConfirmed) tradeConfirmed
									.cloneDeep();

							Generator gen = new Generator();

							List<Object> payloads = new ArrayList<Object>(
									ITERATIONS);

							for (int j = 0; j < ITERATIONS; j++) {

								TradeConfirmed fpML = randomizeFpML(myTradeConfirmed);

								if (rand.nextInt(100) == 0) {
									breakFpml(fpML);
								}

								Sink sink = ot.getSink(sinkFactory);
								sink.writeObject(fpML);
								Object payload = ot.getOutput(sink);

								payloads.add(payload);
							}

							gen.payloads = payloads;

							return gen;
						}
					});
		}

		for (int i = 0; i < THREADS; i++) {
			Future<Generator> future = completionService.take();
			result.add(future.get());
		}

		return result;

	}

	public TradeConfirmed readTradeConfirmed() throws Exception {

		Element element = FpMLElement.getInstance();
		// Appender appender = new ConsoleAppender(new SimpleLayout());
		// element.getLog().addAppender(appender);
		// element.getLog().setLevel(Level.OFF); // change this to see console

		System.out.println("Parsing... ");
		Source source = element.getModel().source();
		InputStream inputStream = base.getInputStream();
		source.setInputStream(inputStream);
		ComplexDataObject bean = source.readObject(element);
		inputStream.close();

		TradeConfirmed tradeConfirmed = (TradeConfirmed) bean;

		return tradeConfirmed;
	}

	private static TradeConfirmed randomizeFpML(TradeConfirmed tradeConfirmed)
			throws CloneNotSupportedException {
		ISO8601DateTime creationDate = new ISO8601DateTime(new Date());
		ISO8601Date tradeDate = new ISO8601Date(new GregorianCalendar(2011,
				Calendar.JULY, 5).getTime());
		ISO8601Date expiryDate = new ISO8601Date(new GregorianCalendar(2012,
				Calendar.JANUARY, 5).getTime());
		ISO8601Date settlementDate = new ISO8601Date(new GregorianCalendar(
				2011, Calendar.JULY, 7).getTime());
		ISO8601Date valueDate = new ISO8601Date(new GregorianCalendar(2012,
				Calendar.JANUARY, 9).getTime());

		Random random = new Random();

		BigDecimal putAmount = new BigDecimal(
				(1 + random.nextInt(999)) * 100000.);
		BigDecimal callAmount = new BigDecimal(
				(1 + random.nextInt(999)) * 100000.);
		BigDecimal fxRate = callAmount.divide(putAmount, 8,
				RoundingMode.HALF_EVEN).setScale(5, BigDecimal.ROUND_HALF_UP);
		BigDecimal premiumValue = new BigDecimal(0.001).setScale(5,
				BigDecimal.ROUND_HALF_UP);
		BigDecimal premiumAmount = callAmount.multiply(premiumValue).setScale(
				2, BigDecimal.ROUND_HALF_UP);

		String sendTo = "SendTo" + random.nextInt(999999);
		String sentBy = "SentBy" + random.nextInt(999999);
		String msgId = "Msg" + random.nextInt(999999);
		String conversationId = "Conv" + random.nextInt(999999);
		String tradeId1 = "ID1:" + random.nextInt(999999);
		String tradeId2 = "ID2:" + random.nextInt(999999);

		// Set the header
		NotificationMessageHeader header = tradeConfirmed.getHeader();
		header.getMessageHeadermodel().setCreationTimestamp(creationDate);
		header.getConversationId().setValue(conversationId);
		header.getMessageId().setValue(msgId);
		header.getMessageHeadermodel().getSentBy().setValue(sentBy);
		header.getMessageHeadermodel().getSendTo()[0].setValue(sendTo);

		// Set the TradeHeader
		TradeHeader tradeHeader = tradeConfirmed.getTrade().getTradeHeader();
		tradeHeader.getPartyTradeIdentifier()[0].getTradeIdentifierSG1()[0]
				.getTradeId().setValue(tradeId1);
		tradeHeader.getPartyTradeIdentifier()[1].getTradeIdentifierSG1()[0]
				.getTradeId().setValue(tradeId2);
		tradeHeader.getTradeDate().setValue(tradeDate);

		FxOptionLeg fxOptionLeg = (FxOptionLeg) tradeConfirmed.getTrade()
				.getProduct();
		fxOptionLeg.getExpiryDateTime().setExpiryDate(expiryDate);

		FxOptionPremium optionPremium = fxOptionLeg.getFxOptionPremium()[0];
		optionPremium.getPremiumAmount().setAmount(premiumAmount);
		optionPremium.setPremiumSettlementDate(settlementDate);
		SettlementInstruction settlementInstruction = optionPremium
				.getSettlementInformation().getSettlementInstruction();
		settlementInstruction.getCorrespondentInformation()
				.getRoutingIdentificationmodel().getRoutingIds().getRoutingId()[0]
				.setValue(sendTo);
		settlementInstruction.getBeneficiary().getRoutingIdentificationmodel()
				.getRoutingIds().getRoutingId()[0].setValue(sentBy);
		optionPremium.getPremiumQuote().setPremiumValue(premiumValue);
		fxOptionLeg.setValueDate(valueDate);

		fxOptionLeg.getPutCurrencyAmount().getCurrency().setValue("AUD");
		fxOptionLeg.getPutCurrencyAmount().setAmount(putAmount);

		fxOptionLeg.getPutCurrencyAmount().getCurrency().setValue("USD");
		fxOptionLeg.getCallCurrencyAmount().setAmount(callAmount);

		fxOptionLeg.getFxStrikePrice().setRate(fxRate);

		fxOptionLeg.getQuotedAs().getOptionOnCurrency().setValue("AUD");
		fxOptionLeg.getQuotedAs().getFaceOnCurrency().setValue("USD");
		fxOptionLeg.getQuotedAs().getQuotedTenor().setPeriod("M");
		fxOptionLeg.getQuotedAs().getQuotedTenor()
				.setPeriodMultiplier(new BigInteger("6"));

		tradeConfirmed.getParty()[0].getPartyId()[0].setValue(sendTo);
		tradeConfirmed.getParty()[1].getPartyId()[0].setValue(sentBy);

		return (TradeConfirmed) tradeConfirmed.cloneDeep();
	}

	public Resource getBase() {
		return base;
	}

	public void setBase(Resource base) {
		this.base = base;
	}

	public SinkFactory getSinkFactory() {
		return sinkFactory;
	}

	public void setSinkFactory(SinkFactory sinkFactory) {
		this.sinkFactory = sinkFactory;
	}

}
