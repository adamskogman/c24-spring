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

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.http.MediaType;
import org.w3c.dom.Element;

import biz.c24.io.spring.http.DataFormat;

/**
 *
 * @author Oliver Gierke
 */
public class FormatBeanDefinitionParser implements BeanDefinitionParser {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.BeanDefinitionParser#parse(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
	 */
	public BeanDefinition parse(Element element, ParserContext parserContext) {

		List<MediaType> mediaTypes = MediaType.parseMediaTypes(element.getAttribute("content-type"));
		String format = element.getAttribute("type");

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(DataFormat.class);
		builder.addConstructorArgValue(format.toUpperCase());
		builder.addConstructorArgValue(mediaTypes);

		return BeanDefinitionUtils.getSourcedBeanDefinition(builder, parserContext.extractSource(element));
	}
}
