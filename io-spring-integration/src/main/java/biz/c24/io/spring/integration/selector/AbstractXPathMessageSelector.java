/**
 * 
 */
package biz.c24.io.spring.integration.selector;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageSelector;
import org.springframework.util.Assert;
import org.springframework.xml.xpath.XPathException;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.IOXPath;
import biz.c24.io.api.data.IOXPathException;
import biz.c24.io.api.data.IOXPathFactory;
import biz.c24.io.api.data.XPathStatement;

/**
 * @author askogman
 * 
 */
public abstract class AbstractXPathMessageSelector implements MessageSelector {

	XPathStatement statement;

	public AbstractXPathMessageSelector(String statement) {
		Assert.notNull(statement, "The XPath statement must not be null.");

		this.statement = new XPathStatement(statement);
	}

	public AbstractXPathMessageSelector(XPathStatement statement) {
		Assert.notNull(statement, "The XPath statement must not be null.");

		this.statement = statement;
	}

	protected IOXPath createXPath() throws IOXPathException {
		return IOXPathFactory.getInstance(statement);
	}

	final public boolean accept(Message<?> message) {
		Object payload = message.getPayload();

		ComplexDataObject cdo;
		try {
			cdo = (ComplexDataObject) payload;
		} catch (ClassCastException e) {
			throw new MessagingException("Cannot evaluate payload of type ["
					+ payload.getClass().getName()
					+ "]. Only ComplexDataObject is supported.", e);
		}

		try {
			return doAcceptPayload(cdo);
		} catch (IOXPathException e) {
			throw new XPathException("Exception thrown trying to evaluate ["
					+ statement + "]", e);
		}
	}

	abstract protected boolean doAcceptPayload(ComplexDataObject payload)
			throws IOXPathException;

}
