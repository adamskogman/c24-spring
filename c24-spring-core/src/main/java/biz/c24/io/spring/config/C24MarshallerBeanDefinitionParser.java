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

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import biz.c24.io.spring.oxm.C24Marshaller;

/**
 * {@link BeanDefinitionParser} to parse {@code marshaller} elements.
 * 
 * @author Oliver Gierke
 */
class C24MarshallerBeanDefinitionParser extends AbstractBeanDefinitionParser {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractBeanDefinitionParser#parseInternal(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
	 */
	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {

		String modelRef = element.getAttribute("model-ref");
		modelRef = StringUtils.hasText(modelRef)? modelRef : C24ModelBeanDefinitionParser.DEFAULT_BEAN_NAME;

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(C24Marshaller.class);
		builder.addConstructorArgReference(modelRef);
		return getSourcedBeanDefinition(builder, parserContext.extractSource(element));
	}
}
