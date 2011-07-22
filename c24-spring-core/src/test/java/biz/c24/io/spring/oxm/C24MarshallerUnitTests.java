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

import biz.c24.io.spring.core.C24Model;
import biz.c24.io.spring.model.TestConstants;

/**
 * Unit tests for {@link C24Marshaller}.
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
