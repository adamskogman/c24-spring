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

import biz.c24.io.spring.model.TestConstants;
import biz.c24.io.spring.util.C24Model;

/**
 * 
 * @author Oliver Gierke
 */
@RunWith(MockitoJUnitRunner.class)
public class C24MarshallerUnitTests {

	@Mock
	Source source;

	C24Model model = new C24Model(new CustomerElement());

	@Test
	public void supportsSubclassesOfComplexDataObject() {
		C24Marshaller marshaller = new C24Marshaller(model);
		assertThat(marshaller.supports(CustomerLocal.class), is(true));
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNonStreamSourceImplementationsForUnmarshalling() throws XmlMappingException, IOException {
		new C24Marshaller(model).unmarshal(source);
	}

	@Test
	public void convertsInputIntoASampleClass() throws XmlMappingException, IOException {
		StringSource source = new StringSource(TestConstants.SAMPLE_XML);
		Object result = new C24Marshaller(model).unmarshal(source);
		assertTrue(result instanceof CustomerLocal);
	}
}
