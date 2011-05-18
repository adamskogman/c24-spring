package biz.c24.io.spring.oxm;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.transform.Source;

import nonamespace.CustomerElement;
import nonamespace.CustomerLocal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.oxm.XmlMappingException;
import org.springframework.xml.transform.StringSource;

import biz.c24.io.api.data.Element;
import biz.c24.io.spring.model.TestConstants;

/**
 * 
 * @author Oliver Gierke
 */
@RunWith(MockitoJUnitRunner.class)
public class C24MarshallerUnitTests {

	@Mock
	Source source;

	Element element = new CustomerElement();

	@Test
	public void supportsSubclassesOfComplexDataObject() {
		C24Marshaller marshaller = new C24Marshaller(element);
		assertThat(marshaller.supports(CustomerLocal.class), is(true));
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNonStreamSourceImplementationsForUnmarshalling() throws XmlMappingException, IOException {
		new C24Marshaller(element).unmarshal(source);
	}

	@Test
	public void convertsInputIntoASampleClass() throws XmlMappingException, IOException {
		StringSource source = new StringSource(TestConstants.SAMPLE_XML);
		Object result = new C24Marshaller(element).unmarshal(source);
		assertTrue(result instanceof CustomerLocal);
	}
}
