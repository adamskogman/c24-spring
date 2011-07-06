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

	}
}
