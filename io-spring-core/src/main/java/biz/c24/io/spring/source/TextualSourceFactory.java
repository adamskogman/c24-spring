/**
 * 
 */
package biz.c24.io.spring.source;

import java.io.InputStream;
import java.io.Reader;

import biz.c24.io.api.presentation.Source;
import biz.c24.io.api.presentation.TextualSource;

/**
 * @author askogman
 * 
 */
public class TextualSourceFactory implements SourceFactory {

	private String encoding;

	private Boolean endOfDataRequired = null;

	private Integer lookAhead = null;

	private Integer lookBehind = null;

	/**
	 * 
	 */
	public TextualSourceFactory() {
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

		TextualSource textualSource = new TextualSource(stream);

		configure(textualSource);

		return textualSource;

	}

	final protected void configure(TextualSource textualSource) {

		// Only set the property if a value has been provided to one of the
		// setters. If not, let the underlying Source retain the default.

		textualSource.setEncoding(encoding);

		doConfigure(textualSource);

	}

	/**
	 * Override this to provide configuration to the source
	 * 
	 * @param textualSource
	 */
	protected void doConfigure(TextualSource textualSource) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see biz.c24.io.spring.source.SourceFactory#getSource(java.io.Reader)
	 */
	public Source getSource(Reader reader) {
		TextualSource textualSource = new TextualSource(reader);

		configure(textualSource);

		return textualSource;
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
