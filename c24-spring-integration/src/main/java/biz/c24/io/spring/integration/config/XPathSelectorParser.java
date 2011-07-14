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
package biz.c24.io.spring.integration.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author Adam Skogman
 */
public class XPathSelectorParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected boolean shouldGenerateId() {
		return false;
	}

	@Override
	protected boolean shouldGenerateIdAsFallback() {
		return true;
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext,
			BeanDefinitionBuilder builder) {

		String evaluationType = element.getAttribute("evaluation-result-type");

		String expression = element.getAttribute("xpath-statement");
		String expressionRef = element.getAttribute("xpath-statement-ref");

		boolean hasRef = StringUtils.hasText(expressionRef);
		Assert.isTrue(
				hasRef ^ StringUtils.hasText(expression),
				"Exactly one of the 'xpath-statement' or 'xpath-statement-ref' attributes is required.");
		if (hasRef) {
			builder.addConstructorArgReference(expressionRef);
		} else {
			builder.addConstructorArgValue(expression);
		}

		String stringTestValue = element.getAttribute("string-test-value");

		if (evaluationType.equals("boolean")) {
			builder.getBeanDefinition()
			.setBeanClass(
					biz.c24.io.spring.integration.selector.C24BooleanTestXPathMessageSelector.class);
			Assert.state(
					!StringUtils.hasText(stringTestValue),
					"'string-test-value' should not be specified when 'evaluation-result-type' is boolean");
		} else if (evaluationType.equals("string")) {
			Assert.hasText(stringTestValue,
					"'string-test-value' must be specified when 'evaluation-result-type' is string");
			builder.addPropertyValue("valueToTestFor", stringTestValue);
			builder.getBeanDefinition()
			.setBeanClass(
					biz.c24.io.spring.integration.selector.C24StringValueTestXPathMessageSelector.class);
		} else {
			throw new IllegalArgumentException(
					"Unsupported value ["
							+ evaluationType
							+ "] for 'evaluation-result-type', expected boolean or string.");
		}
	}

}
