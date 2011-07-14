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
