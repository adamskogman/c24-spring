package biz.c24.io.spring.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues.ValueHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import biz.c24.io.spring.http.C24HttpMessageConverter;
import biz.c24.io.spring.oxm.C24Marshaller;

/**
 *
 * @author Oliver Gierke
 */
public class C24MarshallerBeanDefinitionParserIntegrationTests {

	@Test
	public void plainMarshallerElementSetsUpAMarshallerBean() {
		BeanDefinition definition = getDefinitionFromFile("marshaller.xml", "c24marshaller");

		assertThat(definition, is(notNullValue()));
		assertThat(definition.getBeanClassName(), is(C24Marshaller.class.getName()));
	}

	@Test
	public void plainMessageConverterElementSetsUpAConverterAndDefaultMarshaller() {

		BeanDefinition definition = getDefinitionFromFile("converter.xml", "xml");

		assertThat(definition, is(notNullValue()));
		assertThat(definition.getBeanClassName(), is(C24HttpMessageConverter.class.getName()));

		ValueHolder constructorArgument = definition.getConstructorArgumentValues().getArgumentValue(0, null);
		assertThat(constructorArgument, is(notNullValue()));
		Object argument = constructorArgument.getValue();
		assertThat(argument, is(RuntimeBeanReference.class));
		assertThat(((RuntimeBeanReference) argument).getBeanName(), is(C24ModelBeanDefinitionParser.DEFAULT_BEAN_NAME));

		constructorArgument = definition.getConstructorArgumentValues().getArgumentValue(1, null);
		assertThat(constructorArgument, is(notNullValue()));
		assertThat(constructorArgument.getValue(), is(Collection.class));
	}

	private BeanDefinition getDefinitionFromFile(String filename, String beanName) {
		XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource(filename, this.getClass()));
		return factory.getBeanDefinition(beanName);
	}
}
