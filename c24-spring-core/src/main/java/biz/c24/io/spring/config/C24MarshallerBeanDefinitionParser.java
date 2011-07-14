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
