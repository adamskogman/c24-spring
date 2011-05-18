package biz.c24.io.spring.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues.ValueHolder;
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
		assertThat(constructorArgument.getValue(), is(String.class));
	}

	private BeanDefinition getDefinitionFromFile(String filename, String beanName) {
		XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource(filename, this.getClass()));
		return factory.getBeanDefinition(beanName);
	}
}
