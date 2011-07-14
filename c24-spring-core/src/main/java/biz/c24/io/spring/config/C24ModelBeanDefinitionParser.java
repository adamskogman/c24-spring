package biz.c24.io.spring.config;

import static biz.c24.io.spring.config.BeanDefinitionUtils.*;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import biz.c24.io.spring.util.C24Model;

/**
 * {@link BeanDefinitionParser} to build a {@link C24Model} bean definition.
 * 
 * @author Oliver Gierke
 */
public class C24ModelBeanDefinitionParser extends AbstractBeanDefinitionParser {

	public static final String DEFAULT_BEAN_NAME = "_c24model";

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.BeanDefinitionParser#parse(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
	 */
	@Override
	public AbstractBeanDefinition parseInternal(Element element, ParserContext context) {

		String basePackage = element.getAttribute("base-package");
		String baseElement = element.getAttribute("base-element");

		if (StringUtils.hasText(baseElement) && StringUtils.hasText(basePackage)) {
			throw new BeanDefinitionStoreException("Either base-package or base-element are allowed!");
		}

		Object source = context.extractSource(element);
		AbstractBeanDefinition modelDefinition = StringUtils.hasText(baseElement) ? getC24ModelFromElement(baseElement,
				context, source) : getC24ModelFromPackage(basePackage, context, source);

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(C24Model.class);
		builder.addConstructorArgValue(modelDefinition);

		return getSourcedBeanDefinition(builder, context.extractSource(element));
	}

	/**
	 * Classpath scans the given base package for subclasses of {@link biz.c24.io.api.data.Element} whose names end on
	 * {@code DocumentRootElement}.
	 * 
	 * @param basePackage
	 * @param context
	 * @param source
	 * @return
	 */
	private AbstractBeanDefinition getC24ModelFromPackage(String basePackage, ParserContext context, Object source) {

		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new TypeFilter() {

			private final TypeFilter nameFilter = new RegexPatternTypeFilter(Pattern.compile(".*DocumentRootElement"));
			private final TypeFilter typeFilter = new AssignableTypeFilter(biz.c24.io.api.data.Element.class);

			public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException {
				return nameFilter.match(metadataReader, metadataReaderFactory)
				&& typeFilter.match(metadataReader, metadataReaderFactory);
			}
		});

		Set<BeanDefinition> result = provider.findCandidateComponents(basePackage);
		if (result.size() != 1) {
			context.getReaderContext().error("Found none or multiple classes ending on 'DocumentRootElement'!", source);
		}

		String elementClassName = result.iterator().next().getBeanClassName();
		return getC24ModelFromElement(elementClassName, context, source);
	}

	/**
	 * Creates an {@link AbstractBeanDefinition} for an {@link biz.c24.io.api.data.Element}.
	 * 
	 * @param baseElement
	 * @param context
	 * @param source
	 * @return
	 */
	private AbstractBeanDefinition getC24ModelFromElement(String baseElement, ParserContext context, Object source) {

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(baseElement);
		builder.setFactoryMethod("getInstance");
		return getSourcedBeanDefinition(builder, source);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractBeanDefinitionParser#resolveId(org.w3c.dom.Element, org.springframework.beans.factory.support.AbstractBeanDefinition, org.springframework.beans.factory.xml.ParserContext)
	 */
	@Override
	protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext)
	throws BeanDefinitionStoreException {

		String resolvedId = super.resolveId(element, definition, parserContext);
		return StringUtils.hasText(resolvedId) ? resolvedId : DEFAULT_BEAN_NAME;
	}
}
