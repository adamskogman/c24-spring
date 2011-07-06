package biz.c24.io.spring.integration.transformer;

import static biz.c24.io.spring.integration.test.TestUtils.loadCsvBytes;
import static biz.c24.io.spring.integration.test.TestUtils.loadCsvString;
import static biz.c24.io.spring.integration.test.TestUtils.loadObject;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import biz.c24.io.examples.models.basic.InputDocumentRootElement;
import biz.c24.io.spring.sink.OutputType;
import biz.c24.io.spring.sink.TextualSinkFactory;
import biz.c24.io.spring.sink.XmlSinkFactory;
import biz.c24.io.spring.util.C24Model;

public class IoMarshallingTransformerIUTests {

	C24Model model = new C24Model(InputDocumentRootElement.getInstance());

	@Test
	public void canMarshalXmlToBytearray() throws Exception {

		C24MarshallingTransformer ioMarshallingTransformer = new C24MarshallingTransformer();
		ioMarshallingTransformer.setOutputType(OutputType.BYTE_ARRAY);
		XmlSinkFactory xmlSinkFactory = new XmlSinkFactory();
		ioMarshallingTransformer.setSinkFactory(xmlSinkFactory);

		Message<?> message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = ioMarshallingTransformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(byte[].class));

		String xml = new String((byte[]) outputMessage.getPayload(), "UTF-8");

		// TODO: XML equivalence match

	}

	@Test
	public void canMarshalXmlToString() throws Exception {

		C24MarshallingTransformer ioMarshallingTransformer = new C24MarshallingTransformer();
		ioMarshallingTransformer.setOutputType(OutputType.STRING);
		XmlSinkFactory xmlSinkFactory = new XmlSinkFactory();
		ioMarshallingTransformer.setSinkFactory(xmlSinkFactory);

		Message message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = ioMarshallingTransformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(String.class));

		// TODO: XML equivalence match

	}

	@Test
	public void canMarshalTextToString() throws Exception {

		C24MarshallingTransformer ioMarshallingTransformer = new C24MarshallingTransformer();
		ioMarshallingTransformer.setOutputType(OutputType.STRING);
		ioMarshallingTransformer.setSinkFactory(new TextualSinkFactory());

		Message message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = ioMarshallingTransformer.transform(message);

		assertThat((String) outputMessage.getPayload(), is(loadCsvString()));

	}

	@Test
	public void canMarshalTextToByteArray() throws Exception {

		C24MarshallingTransformer ioMarshallingTransformer = new C24MarshallingTransformer();
		ioMarshallingTransformer.setOutputType(OutputType.BYTE_ARRAY);
		ioMarshallingTransformer.setSinkFactory(new TextualSinkFactory());

		Message message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = ioMarshallingTransformer.transform(message);

		assertThat((byte[]) outputMessage.getPayload(), is(loadCsvBytes()));

	}


}
