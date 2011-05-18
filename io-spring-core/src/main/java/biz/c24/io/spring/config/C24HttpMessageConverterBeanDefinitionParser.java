package biz.c24.io.spring.config;

import static biz.c24.io.spring.config.BeanDefinitionUtils.*;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import biz.c24.io.spring.http.C24HttpMessageConverter;

/**
 * {@link BeanDefinitionParser} implementation to parse {@code http-message-converter} elements.
 *
 * @author Oliver Gierke
 */
class C24HttpMessageConverterBeanDefinitionParser extends AbstractBeanDefinitionParser {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractBeanDefinitionParser#parseInternal(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
	 */
	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext context) {

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(C24HttpMessageConverter.class);
		builder.addConstructorArgValue(element.getAttribute("format"));

		return getSourcedBeanDefinition(builder, context.extractSource(element));
	}
}
