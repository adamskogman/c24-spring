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
package biz.c24.io.spring.integration.transformer;

import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.util.Assert;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.IOXPath;
import biz.c24.io.api.data.IOXPathException;
import biz.c24.io.api.data.IOXPathFactory;
import biz.c24.io.api.data.XPathStatement;
import biz.c24.io.spring.integration.xpath.XPathEvaluationType;

/**
 * @author askogman
 * 
 */
public class C24XPathTransformer extends
AbstractPayloadTransformer<ComplexDataObject, Object> {

	volatile XPathStatement statement;
	volatile XPathEvaluationType evaluationType = XPathEvaluationType.OBJECT_RESULT;

	public C24XPathTransformer(String statement) {
		Assert.notNull(statement, "The XPath statement must not be null.");

		this.statement = new XPathStatement(statement);
	}

	public C24XPathTransformer(XPathStatement statement) {
		Assert.notNull(statement, "The XPath statement must not be null.");

		this.statement = statement;
	}

	@Override
	protected Object transformPayload(ComplexDataObject payload)
			throws Exception {

		IOXPath xpath = createXPath();

		Object transformedPayload = evaluationType
				.evaluateXPath(xpath, payload);
		return transformedPayload;
	}

	protected IOXPath createXPath() throws IOXPathException {
		return IOXPathFactory.getInstance(statement);
	}

	public XPathStatement getStatement() {
		return statement;
	}

	public void setStatement(XPathStatement statement) {
		Assert.notNull(statement, "The XPath statement must not be null.");

		this.statement = statement;
	}

	public XPathEvaluationType getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(XPathEvaluationType evaluationType) {
		Assert.notNull(evaluationType,
				"The XPath evaluation type must not be null.");
		this.evaluationType = evaluationType;
	}

}
