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
