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

package biz.c24.io.spring.integration.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractRouterParser;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Parser for the &lt;xpath-router/&gt; element.
 * 
 * @author Adam Skogman
 */
public class XPathRouterParser extends AbstractRouterParser {

	@Override
	protected BeanDefinition doParseRouter(Element element,
			ParserContext parserContext) {

		BeanDefinitionBuilder xpathRouterBuilder = BeanDefinitionBuilder
				.genericBeanDefinition("biz.c24.io.spring.integration.router.C24XPathRouter");

		String expression = element.getAttribute("xpath-statement");
		String expressionRef = element.getAttribute("xpath-statement-ref");

		boolean hasRef = StringUtils.hasText(expressionRef);
		Assert.isTrue(
				hasRef ^ StringUtils.hasText(expression),
				"Exactly one of the 'xpath-statement' or 'xpath-statement-ref' attributes is required.");
		if (hasRef) {
			xpathRouterBuilder.addConstructorArgReference(expressionRef);
		} else {
			xpathRouterBuilder.addConstructorArgValue(expression);
		}

		return xpathRouterBuilder.getBeanDefinition();
	}

}
