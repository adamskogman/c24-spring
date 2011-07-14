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
package biz.c24.io.spring.integration.selector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageRejectedException;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageSelector;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.ValidationEventCollector;
import biz.c24.io.api.data.ValidationException;
import biz.c24.io.api.data.ValidationManager;
import biz.c24.io.spring.integration.validation.C24AggregatedMessageValidationException;

/**
 * @author askogman
 * 
 */
public class C24ValidatingMessageSelector implements MessageSelector {

	@SuppressWarnings("unused")
	private final Log logger = LogFactory.getLog(this.getClass());

	private volatile boolean throwExceptionOnRejection;

	private volatile boolean failFast = false;

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

		boolean result;
		if (failFast) {
			result = validateFailFast(cdo, message);
		} else {
			result = validateAllEvents(cdo);
		}

		return result;
	}

	boolean validateAllEvents(ComplexDataObject cdo) {

		ValidationManager manager = new ValidationManager();
		ValidationEventCollector vec = new ValidationEventCollector();
		manager.addValidationListener(vec);

		boolean isValid = false;

		if (manager.validateByEvents(cdo)) {
			isValid = true;
		} else {

			if (vec.hasFailEvents()) {
				if (throwExceptionOnRejection) {
					throw new C24AggregatedMessageValidationException(
							vec.getFailEvents());
				}
			} else {
				// No fail events, so it passed
				isValid = true;
			}

		}

		return isValid;
	}

	boolean validateFailFast(ComplexDataObject cdo, Message<?> message) {

		ValidationManager manager = new ValidationManager();

		boolean isValid = false;
		try {
			manager.validateByException(cdo);
			isValid = true;
		} catch (ValidationException ve) {
			if (throwExceptionOnRejection) {
				throw new MessageRejectedException(message,
						"Validation failed.", ve);
			}
		}

		return isValid;

	}

	public boolean isThrowExceptionOnRejection() {
		return throwExceptionOnRejection;
	}

	/**
	 * Determines how validations failures are handled within the Spring
	 * Integration framework. If set to true, an Exception will be thrown
	 * instead of passing the message to the discardChannel.
	 * 
	 * @param throwExceptionOnRejection
	 */
	public void setThrowExceptionOnRejection(boolean throwExceptionOnRejection) {
		this.throwExceptionOnRejection = throwExceptionOnRejection;
	}

	public boolean isFailFast() {
		return failFast;
	}

	/**
	 * Configure if this validator should fail fast, ie. fail as soon as there
	 * is an exception, or not fail fast, i.e collect all the validation errors.
	 * 
	 * Default is false, meaning that all validation errors will be collected.
	 * 
	 * @param failFast
	 */
	public void setFailFast(boolean failFast) {
		this.failFast = failFast;
	}

}
