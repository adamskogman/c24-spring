/**
 * 
 */
package biz.c24.io.spring.integration.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractTransformerParser;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import biz.c24.io.spring.config.C24ModelBeanDefinitionParser;

/**
 * @author Adam Skogman
 * 
 */
public class IoUnmarshallingTransformerParser extends AbstractTransformerParser {

	@Override
	protected String getTransformerClassName() {
		return "biz.c24.io.spring.integration.transformer.IoUnmarshallingTransformer";
	}

	@Override
	protected void parseTransformer(Element element,
			ParserContext parserContext, BeanDefinitionBuilder builder) {

		String modelRef = element.getAttribute("model-ref");
		modelRef = StringUtils.hasText(modelRef) ? modelRef
				: C24ModelBeanDefinitionParser.DEFAULT_BEAN_NAME;
		builder.addConstructorArgReference(modelRef);

		// IntegrationNamespaceUtils.setValueIfAttributeDefined(builder,
		// element, "send-timeout");

		String sourceFactory = element.getAttribute("source-factory-ref");
		Assert.hasText(sourceFactory,
				"the 'source-factory' attribute is required");
		builder.addConstructorArgReference(sourceFactory);

	}

}
