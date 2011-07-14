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
public class C24StringValueTestXPathMessageSelector extends
		AbstractXPathMessageSelector implements InitializingBean {

	private volatile boolean caseSensitive = true;

	private volatile String stringTestValue;

	/**
	 * @param statement
	 */
	public C24StringValueTestXPathMessageSelector(String statement) {
		super(statement);
	}

	/**
	 * @param statement
	 */
	public C24StringValueTestXPathMessageSelector(XPathStatement statement) {
		super(statement);
	}

	@Override
	protected boolean doAcceptPayload(ComplexDataObject payload) throws IOXPathException {

		String value = createXPath().getString(payload);
		
		if (this.caseSensitive) {
			return this.stringTestValue.equals(value);
		}
		else {
			return this.stringTestValue.equalsIgnoreCase(value);
		}

		
	}

	public String getValueToTestFor() {
		return stringTestValue;
	}

	public void setValueToTestFor(String valueToTestFor) {
		Assert.notNull(valueToTestFor, "'valueToTestFor' must not be null.");
		this.stringTestValue = valueToTestFor;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(stringTestValue, "'valueToTestFor' must not be null.");
	}

}
