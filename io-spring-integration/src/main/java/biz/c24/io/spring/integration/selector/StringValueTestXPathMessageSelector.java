/**
 * 
 */
package biz.c24.io.spring.integration.selector;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.IOXPathException;
import biz.c24.io.api.data.XPathStatement;

/**
 * @author askogman
 * 
 */
public class StringValueTestXPathMessageSelector extends
		AbstractXPathMessageSelector implements InitializingBean {

	private volatile boolean caseSensitive = true;

	private volatile String valueToTestFor;

	/**
	 * @param statement
	 */
	public StringValueTestXPathMessageSelector(String statement) {
		super(statement);
	}

	/**
	 * @param statement
	 */
	public StringValueTestXPathMessageSelector(XPathStatement statement) {
		super(statement);
	}

	@Override
	protected boolean doAcceptPayload(ComplexDataObject payload) throws IOXPathException {

		String value = createXPath().getString(payload);
		
		if (this.caseSensitive) {
			return this.valueToTestFor.equals(value);
		}
		else {
			return this.valueToTestFor.equalsIgnoreCase(value);
		}

		
	}

	public String getValueToTestFor() {
		return valueToTestFor;
	}

	public void setValueToTestFor(String valueToTestFor) {
		Assert.notNull(valueToTestFor, "'valueToTestFor' must not be null.");
		this.valueToTestFor = valueToTestFor;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(valueToTestFor, "'valueToTestFor' must not be null.");
	}

}
