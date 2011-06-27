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
				new IoUnmarshallingTransformerParser());
		registerBeanDefinitionParser("marshalling-transformer",
				new IoMarshallingTransformerParser());
		registerBeanDefinitionParser("transformer", new IoTransformerParser());
		registerBeanDefinitionParser("xpath-transformer",
				new IoXPathTransformerParser());

		registerBeanDefinitionParser("xpath-router", new IoXPathRouterParser());
		registerBeanDefinitionParser("xpath-selector",
				new IoXPathSelectorParser());

		registerBeanDefinitionParser("xpath-header-enricher",
				new IoXPathHeaderEnricherParser());

	}
}
