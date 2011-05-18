package biz.c24.io.spring.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;

/**
 * Simple helper class to ease building {@link BeanDefinition}s a bit.
 * 
 * @author Oliver Gierke
 */
class BeanDefinitionUtils {

	/**
	 * Returns the {@link AbstractBeanDefinition} built by the given builder applying the given source to it. The actual
	 * source will be extracted using the given {@link ParserContext}.
	 * 
	 * @param builder
	 * @param source
	 * @param context
	 * @return
	 */
	public static AbstractBeanDefinition getSourcedBeanDefinition(BeanDefinitionBuilder builder, Object source,
			ParserContext context) {

		Assert.notNull(builder);
		Assert.notNull(source);
		Assert.notNull(context);

		AbstractBeanDefinition definition = builder.getBeanDefinition();
		definition.setSource(context.extractSource(source));
		return definition;
	}

	/**
	 * Returns the {@link AbstractBeanDefinition} built by the given builder applying the given source to it.
	 * 
	 * @param builder
	 * @param source
	 * @return
	 */
	public static AbstractBeanDefinition getSourcedBeanDefinition(BeanDefinitionBuilder builder, Object source) {

		Assert.notNull(builder);
		Assert.notNull(source);

		AbstractBeanDefinition definition = builder.getBeanDefinition();
		definition.setSource(source);
		return definition;
	}
}
