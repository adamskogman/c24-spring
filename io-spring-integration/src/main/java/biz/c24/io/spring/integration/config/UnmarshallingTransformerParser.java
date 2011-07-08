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
 * @author Adam Skogman
 * 
 */
public class UnmarshallingTransformerParser extends AbstractTransformerParser {

	@Override
	protected String getTransformerClassName() {
		return "biz.c24.io.spring.integration.transformer.C24UnmarshallingTransformer";
	}

	@Override
	protected void parseTransformer(Element element,
			ParserContext parserContext, BeanDefinitionBuilder builder) {

		String modelRef = element.getAttribute("model-ref");
		modelRef = StringUtils.hasText(modelRef) ? modelRef
				: C24ModelBeanDefinitionParser.DEFAULT_BEAN_NAME;
		builder.addConstructorArgReference(modelRef);

		IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder,
				element, "source-factory-ref", "sourceFactory");

		IntegrationNamespaceUtils.setValueIfAttributeDefined(builder,
				element, "unwrap-document-root", "unwrapDocumentRoot");

	}

}
