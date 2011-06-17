/**
 * 
 */
package biz.c24.io.spring.integration.selector;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.IOXPathException;
import biz.c24.io.api.data.XPathStatement;

/**
 * @author askogman
 * 
 */
public class BooleanTestXPathMessageSelector extends
		AbstractXPathMessageSelector {

	/**
	 * @param statement
	 */
	public BooleanTestXPathMessageSelector(String statement) {
		super(statement);
	}

	/**
	 * @param statement
	 */
	public BooleanTestXPathMessageSelector(XPathStatement statement) {
		super(statement);
	}

	@Override
	protected boolean doAcceptPayload(ComplexDataObject payload) throws IOXPathException {
		return createXPath().getBoolean(payload);
	}

}
