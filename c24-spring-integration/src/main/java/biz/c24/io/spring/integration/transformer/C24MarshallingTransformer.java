/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *			http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public class C24MarshallingTransformer extends AbstractPayloadTransformer<Object, Object> {

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
