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

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * {@link NamespaceHandler} implementation that registers
 * {@link BeanDefinitionParser}s for our namespace elements.
 * 
 * @author Adam Skogman
 */
class C24NamespaceHandler extends NamespaceHandlerSupport {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	public void init() {
		registerBeanDefinitionParser("unmarshalling-transformer",
				new UnmarshallingTransformerParser());
		registerBeanDefinitionParser("marshalling-transformer",
				new MarshallingTransformerParser());
		registerBeanDefinitionParser("transformer", new TransformerParser());
		registerBeanDefinitionParser("xpath-transformer",
				new XPathTransformerParser());

		registerBeanDefinitionParser("xpath-router", new XPathRouterParser());
		registerBeanDefinitionParser("xpath-selector",
				new XPathSelectorParser());

		registerBeanDefinitionParser("xpath-header-enricher",
				new XPathHeaderEnricherParser());

		registerBeanDefinitionParser("validating-header-enricher",
				new ValidatingHeaderEnricherParser());


	}
}
