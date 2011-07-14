/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *			http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

		AbstractBeanDefinition definition = builder.getBeanDefinition();
		definition.setSource(source);
		return definition;
	}
}
