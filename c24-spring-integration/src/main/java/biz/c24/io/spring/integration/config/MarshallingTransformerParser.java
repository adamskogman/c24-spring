/**
 * 
 */
package biz.c24.io.spring.integration.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractTransformerParser;
import org.springframework.util.Assert;
import org.w3c.dom.Element;

/**
 * @author Adam Skogman
 * 
 */
public class MarshallingTransformerParser extends AbstractTransformerParser {

	@Override
	protected String getTransformerClassName() {
		return "biz.c24.io.spring.integration.transformer.C24MarshallingTransformer";
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
