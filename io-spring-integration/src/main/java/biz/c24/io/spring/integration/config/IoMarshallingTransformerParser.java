/**
 * 
 */
package biz.c24.io.spring.integration.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractTransformerParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import biz.c24.io.spring.config.C24ModelBeanDefinitionParser;

/**
 * @author askogman
 * 
 */
public class IoMarshallingTransformerParser extends AbstractTransformerParser {

	@Override
	protected String getTransformerClassName() {
		return "biz.c24.io.spring.integration.transformer.IoMarshallingTransformer";
	}

	@Override
	protected void parseTransformer(Element element,
			ParserContext parserContext, BeanDefinitionBuilder builder) {

		String sinkFactory = element.getAttribute("sink-factory");
		Assert.hasText(sinkFactory, "the 'sink-factory' attribute is required");
		builder.addPropertyReference("sinkFactory", sinkFactory);

		String outputType = element.getAttribute("output-type");
		Assert.hasText(outputType, "the 'output-type' attribute is required");
		builder.addPropertyValue("outputType", outputType);

	}

}
