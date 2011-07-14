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

import java.io.OutputStream;
import java.io.Writer;

import biz.c24.io.api.presentation.Sink;

/**
 * @author askogman
 * 
 */
public abstract class AbstractSinkFactory implements SinkFactory {

	String encoding;

	/*
	 * (non-Javadoc)
	 * 
	 * @see biz.c24.io.spring.sink.SinkFactory#createSink(java.io.Writer)
	 */
	public Sink createSink(Writer writer) {

		Sink sink = createSink();
		sink.setWriter(writer);

		configureSink(sink);

		doConfigureSink(sink);

		return sink;
	}

	protected abstract Sink createSink();

	protected void configureSink(Sink sink) {
		if (encoding != null) {
			sink.setEncoding(encoding);
		}
	}

	/**
	 * Override to provide custom configuration for the sink.
	 * 
	 * @param sink
	 */
	protected void doConfigureSink(Sink sink) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see biz.c24.io.spring.sink.SinkFactory#createSink(java.io.OutputStream)
	 */
	public Sink createSink(OutputStream stream) {
		Sink sink = createSink();
		sink.setOutputStream(stream);

		configureSink(sink);

		doConfigureSink(sink);

		return sink;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
