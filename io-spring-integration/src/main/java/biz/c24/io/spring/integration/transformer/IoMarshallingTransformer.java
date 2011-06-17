/**
 * 
 */
package biz.c24.io.spring.integration.transformer;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.integration.MessagingException;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.util.Assert;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.Sink;
import biz.c24.io.spring.sink.OutputType;
import biz.c24.io.spring.sink.SinkFactory;

/**
 * @author askogman
 * 
 */
public class IoMarshallingTransformer extends
		AbstractPayloadTransformer<Object, Object> {

	SinkFactory sinkFactory;

	OutputType outputType;

	public OutputType getOutputType() {
		return outputType;
	}

	public void setOutputType(OutputType outputType) {
		this.outputType = outputType;
	}

	public SinkFactory getSinkFactory() {
		return sinkFactory;
	}

	@Required
	public void setSinkFactory(SinkFactory sinkFactory) {
		this.sinkFactory = sinkFactory;
	}

	@Override
	protected void onInit() throws Exception {
		super.onInit();

		Assert.notNull(sinkFactory, "The property 'sinkFactory' must be set.");
		Assert.notNull(outputType, "The property 'outputType' must be set.");
	}

	@Override
	protected Object transformPayload(Object payload) throws Exception {

		ComplexDataObject cdo;
		try {
			cdo = (ComplexDataObject) payload;
		} catch (ClassCastException e) {
			throw new MessagingException("Cannot marshal payload of type ["
					+ payload.getClass().getName()
					+ "]. Only ComplexDataObject is supported.", e);
		}

		Sink sink = outputType.getSink(sinkFactory);

		sink.writeObject(cdo);

		Object outputPayload = outputType.getOutput(sink);

		return outputPayload;
	}

}
