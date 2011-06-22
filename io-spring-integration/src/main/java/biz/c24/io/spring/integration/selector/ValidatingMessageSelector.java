/**
 * 
 */
package biz.c24.io.spring.integration.selector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageSelector;

import biz.c24.io.api.data.ComplexDataObject;

/**
 * @author askogman
 * 
 */
public class ValidatingMessageSelector implements MessageSelector {

	@SuppressWarnings("unused")
	private final Log logger = LogFactory.getLog(this.getClass());

	private volatile boolean throwExceptionOnRejection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.integration.core.MessageSelector#accept(org.
	 * springframework.integration.Message)
	 */
	public boolean accept(Message<?> message) {
		Object payload = message.getPayload();

		ComplexDataObject cdo;
		try {
			cdo = (ComplexDataObject) payload;
		} catch (ClassCastException e) {
			throw new MessagingException("Cannot validate payload of type ["
					+ payload.getClass().getName()
					+ "]. Only ComplexDataObject is supported.", e);
		}

		// TODO Code here
		
		return false;
	}

	public boolean isThrowExceptionOnRejection() {
		return throwExceptionOnRejection;
	}

	public void setThrowExceptionOnRejection(boolean throwExceptionOnRejection) {
		this.throwExceptionOnRejection = throwExceptionOnRejection;
	}

}
