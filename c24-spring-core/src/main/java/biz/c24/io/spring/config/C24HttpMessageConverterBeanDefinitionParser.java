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
package biz.c24.io.spring.config;

import static biz.c24.io.spring.config.BeanDefinitionUtils.*;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import biz.c24.io.spring.http.C24HttpMessageConverter;

/**
 * {@link BeanDefinitionParser} implementation to parse {@code http-message-converter} elements.
 *
 * @author Oliver Gierke
 */
class C24HttpMessageConverterBeanDefinitionParser extends AbstractBeanDefinitionParser {

	private final FormatBeanDefinitionParser formatParser = new FormatBeanDefinitionParser();

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractBeanDefinitionParser#parseInternal(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
	 */
	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext context) {

		Object source = context.extractSource(element);

		String modelRef = element.getAttribute("model-ref");
		modelRef = StringUtils.hasText(modelRef) ? modelRef : C24ModelBeanDefinitionParser.DEFAULT_BEAN_NAME;

		Set<BeanDefinition> formats = new ManagedSet<BeanDefinition>();
		for(Element formatElement : DomUtils.getChildElementsByTagName(element, "format")) {
			formats.add(formatParser.parse(formatElement, context));
		}

		// Create C24HttpMessageConverter bean
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(C24HttpMessageConverter.class);
		builder.addConstructorArgReference(modelRef);
		builder.addConstructorArgValue(formats);

		return getSourcedBeanDefinition(builder, source);
	}
}
