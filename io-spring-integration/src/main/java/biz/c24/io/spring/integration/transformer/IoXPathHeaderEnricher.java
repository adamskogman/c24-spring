/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.c24.io.spring.integration.transformer;

import java.util.Map;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.util.Assert;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.IOXPath;
import biz.c24.io.api.data.IOXPathException;
import biz.c24.io.api.data.IOXPathFactory;
import biz.c24.io.api.data.XPathStatement;
import biz.c24.io.spring.integration.xpath.XPathEvaluationType;

/**
 * Transformer implementation that evaluates XPath expressions against the
 * message payload and inserts the result of the evaluation into a message
 * header. The header names will match the keys in the map of expressions.
 * 
 * @author Adam Skogman
 */
public class IoXPathHeaderEnricher extends HeaderEnricher {

	/**
	 * Create an instance of XPathHeaderEnricher using a map with header names
	 * as keys and XPathExpressionValueHolders to evaluate the values.
	 */
	public IoXPathHeaderEnricher(
			Map<String, XPathExpressionEvaluatingHeaderValueMessageProcessor> expressionMap) {
		super(expressionMap);
	}

	static class XPathExpressionEvaluatingHeaderValueMessageProcessor implements
			HeaderValueMessageProcessor<Object> {

		private final XPathStatement statement;

		private volatile XPathEvaluationType evaluationType = XPathEvaluationType.STRING_RESULT;

		private volatile Boolean overwrite = null;

		public XPathExpressionEvaluatingHeaderValueMessageProcessor(
				String statement) {
			Assert.hasText(statement, "statement must have text");
			this.statement = new XPathStatement(statement);

		}

		public XPathExpressionEvaluatingHeaderValueMessageProcessor(
				XPathStatement statement) {
			Assert.notNull(statement, "statement must not be null");
			this.statement = statement;
		}

		public void setEvaluationType(XPathEvaluationType evaluationType) {
			this.evaluationType = evaluationType;
		}

		public void setOverwrite(Boolean overwrite) {
			this.overwrite = overwrite;
		}

		public Boolean isOverwrite() {
			return this.overwrite;
		}

		public Object processMessage(Message<?> message) {

			Object payload = message.getPayload();

			ComplexDataObject cdo;
			try {
				cdo = (ComplexDataObject) payload;
			} catch (ClassCastException e) {
				throw new MessagingException(
						"Cannot evaluate payload of type ["
								+ payload.getClass().getName()
								+ "]. Only ComplexDataObject is supported.", e);
			}

			Object result;
			try {
				IOXPath xpath = createXPath(statement);

				result = evaluationType.evaluateXPath(xpath, cdo);
			} catch (IOXPathException e) {
				throw new MessagingException(message,
						"Exception during XPath evaluation", e);
			}

			if (result instanceof String && ((String) result).length() == 0) {
				result = null;
			}

			return result;
		}
	}

	protected static IOXPath createXPath(XPathStatement statement)
			throws IOXPathException {
		return IOXPathFactory.getInstance(statement);
	}

}
