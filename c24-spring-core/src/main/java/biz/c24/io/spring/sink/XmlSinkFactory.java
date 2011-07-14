/**
 * 
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

		if (outputFormat != null)
			xmlSink.setFormat(outputFormat);
	}

}
