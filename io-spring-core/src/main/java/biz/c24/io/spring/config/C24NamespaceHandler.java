package biz.c24.io.spring.config;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * {@link NamespaceHandler} implementation that registers {@link BeanDefinitionParser}s for our namespace elements.
 * 
 * @author Oliver Gierke
 */
class C24NamespaceHandler extends NamespaceHandlerSupport {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	public void init() {
		registerBeanDefinitionParser("marshaller", new C24MarshallerBeanDefinitionParser());
		registerBeanDefinitionParser("http-message-converter", new C24HttpMessageConverterBeanDefinitionParser());
		registerBeanDefinitionParser("model", new C24ModelBeanDefinitionParser());
	}
}
