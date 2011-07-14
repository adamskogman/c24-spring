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
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractTransformerParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import biz.c24.io.spring.config.C24ModelBeanDefinitionParser;

/**
 * @author Adam Skogman
 * 
 */
public class UnmarshallingTransformerParser extends AbstractTransformerParser {

	@Override
	protected String getTransformerClassName() {
		return "biz.c24.io.spring.integration.transformer.C24UnmarshallingTransformer";
	}

	@Override
	protected void parseTransformer(Element element,
			ParserContext parserContext, BeanDefinitionBuilder builder) {

		String modelRef = element.getAttribute("model-ref");
		modelRef = StringUtils.hasText(modelRef) ? modelRef
				: C24ModelBeanDefinitionParser.DEFAULT_BEAN_NAME;
		builder.addConstructorArgReference(modelRef);

		IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder,
				element, "source-factory-ref", "sourceFactory");

		IntegrationNamespaceUtils.setValueIfAttributeDefined(builder,
				element, "unwrap-document-root", "unwrapDocumentRoot");

	}

}
