/**
 * 
 */
package biz.c24.io.spring.source;

import java.io.InputStream;
import java.io.Reader;

import biz.c24.io.api.presentation.FIXSource;
import biz.c24.io.api.presentation.Source;

/**
 * @author askogman
 * 
 */
public class FixSourceFactory implements SourceFactory {

	private String encoding;

	private Boolean endOfDataRequired = null;

	private Integer lookAhead = null;

	private Integer lookBehind = null;

	/**
	 * 
	 */
	public FixSourceFactory() {
	}

	public Boolean getEndOfDataRequired() {
		return endOfDataRequired;
	}

	public Integer getLookAhead() {
		return lookAhead;
	}

	public Integer getLookBehind() {
		return lookBehind;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.c24.io.spring.source.SourceFactory#getSource(java.io.InputStream)
	 */
	public Source getSource(InputStream stream) {

		FIXSource source = new FIXSource();

		source.setInputStream(stream);

		configure(source);

		return source;

	}

	final protected void configure(FIXSource source) {

		// Only set the property if a value has been provided to one of the
		// setters. If not, let the underlying Source retain the default.

		if (encoding != null)
			source.setEncoding(encoding);
		if (endOfDataRequired != null)
			source.setEndOfDataRequired(endOfDataRequired);
		if (lookAhead != null)
			source.setLookAhead(lookAhead);
		if (lookBehind != null)
			source.setLookBehind(lookBehind);

		doConfigure(source);

	}

	/**
	 * Override this to provide configuration to the source
	 * 
	 * @param source
	 */
	protected void doConfigure(FIXSource source) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see biz.c24.io.spring.source.SourceFactory#getSource(java.io.Reader)
	 */
	public Source getSource(Reader reader) {
		FIXSource source = new FIXSource();

		source.setReader(reader);

		configure(source);

		return source;
	}

	public void setEndOfDataRequired(Boolean endOfDataRequired) {
		this.endOfDataRequired = endOfDataRequired;
	}

	public void setLookAhead(Integer lookAhead) {
		this.lookAhead = lookAhead;
	}

	public void setLookBehind(Integer lookBehind) {
		this.lookBehind = lookBehind;
	}

}
