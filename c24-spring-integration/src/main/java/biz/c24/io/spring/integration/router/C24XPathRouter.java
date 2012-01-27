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
package biz.c24.io.spring.integration.router;

import java.util.Collections;
import java.util.List;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.router.AbstractMappingMessageRouter;
import org.springframework.util.Assert;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.IOXPath;
import biz.c24.io.api.data.IOXPathException;
import biz.c24.io.api.data.IOXPathFactory;
import biz.c24.io.api.data.XPathStatement;

/**
 * Message Router that uses {@link XPathStatement} evaluation to determine
 * channel names.
 * 
 * TODO Evaluate as list of strings, not just a single string.
 * 
 * @author askogman
 * 
 */
public class C24XPathRouter extends AbstractMappingMessageRouter {

	private final XPathStatement statement;

	/**
	 * Create a router that uses an XPath expression.
	 * 
	 * @param expression
	 *            the XPath expression as a String
	 */
	public C24XPathRouter(String expression) {
		Assert.hasText(expression, "expression must not be empty");
		this.statement = new XPathStatement(expression);
	}

	/**
	 * Create a router that uses the provided XPath statement.
	 * 
	 * @param statement
	 *            the XPath statement
	 */
	public C24XPathRouter(XPathStatement statement) {
		Assert.notNull(statement, "statement must not be null");
		this.statement = statement;
	}

	@Override
	public String getComponentType() {
		return "int-c24:xpath-router";
	}

	protected IOXPath createXPath() {
		try {
			return IOXPathFactory.getInstance(statement);
		} catch (IOXPathException e) {
			throw new MessagingException(
					"Exception when trying to instantiate the Xpath statement ["
							+ statement + "].", e);
		}
	}

	@Override
	protected List<Object> getChannelKeys(Message<?> message) {

		ComplexDataObject cdo;
		try {
			cdo = (ComplexDataObject) message.getPayload();
		} catch (ClassCastException e) {

			Object payload = message.getPayload();

			throw new MessagingException(
					"Cannot route based on payloads of type [" + payload == null ? "<null>"
							: payload.getClass().getName()
							+ "]. Only ComplexDataObject is supported.",
							e);
		}

		IOXPath xPath = createXPath();

		String channel;
		try {
			channel = xPath.getString(cdo);
		} catch (IOXPathException e) {
			throw new MessagingException(
					"Exception when trying to evaluate the Xpath statement ["
							+ statement + "] on the object [" + cdo
							+ "] to a String.", e);
		}

		return Collections.singletonList((Object) channel);

	}
}
