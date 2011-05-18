package biz.c24.io.spring.config;

import static biz.c24.io.spring.config.BeanDefinitionUtils.*;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
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

		Object source = parserContext.extractSource(element);

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(C24Marshaller.class);
		builder.addConstructorArgValue(getElementBeanDefinition(element.getAttribute("element-type"), source));
		return getSourcedBeanDefinition(builder, source);
	}

	/**
	 * Will create a bean definition creating an instance of the configured {@link biz.c24.io.api.data.Element} class.
	 * 
	 * @param elementClassName
	 * @param source
	 * @return
	 */
	private BeanDefinition getElementBeanDefinition(String elementClassName, Object source) {

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(elementClassName);
		builder.setFactoryMethod("getInstance");

		return getSourcedBeanDefinition(builder, source);
	}
}
