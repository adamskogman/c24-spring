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
package biz.c24.io.spring.sink;

import org.apache.xml.serialize.OutputFormat;

import biz.c24.io.api.presentation.Sink;
import biz.c24.io.api.presentation.XMLSink;

/**
 * @author askogman
 * 
 */
public class XmlSinkFactory extends AbstractSinkFactory {

	private OutputFormat outputFormat;

	@Override
	protected Sink createSink() {
		return new XMLSink();
	}

	public OutputFormat getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(OutputFormat outputFormat) {
		this.outputFormat = outputFormat;
	}

	@Override
	protected void configureSink(Sink sink) {
		super.configureSink(sink);

		XMLSink xmlSink = (XMLSink) sink;

		if (outputFormat != null) {
			xmlSink.setFormat(outputFormat);
		}
	}

}
