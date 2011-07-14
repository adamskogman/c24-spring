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
package biz.c24.io.spring.integration.transformer;

import static biz.c24.io.spring.integration.test.TestUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import biz.c24.io.examples.models.basic.Employees;
import biz.c24.io.examples.models.basic.InputDocumentRootElement;
import biz.c24.io.spring.source.TextualSourceFactory;
import biz.c24.io.spring.source.XmlSourceFactory;
import biz.c24.io.spring.util.C24Model;

public class IoUnmarshallingTransformerIUTests {

	C24Model model = new C24Model(InputDocumentRootElement.getInstance());

	@Test
	public void canUnmarshalTextFromBytearray() throws Exception {

		byte[] valid1 = loadCsvBytes();
		C24UnmarshallingTransformer transformer = new C24UnmarshallingTransformer(
				model);
		transformer.setSourceFactory(new TextualSourceFactory());

		Message message = MessageBuilder.withPayload(valid1).build();

		Message<?> outputMessage = transformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(Employees.class));

		Employees employees = (Employees) outputMessage.getPayload();

	}

	@Test
	public void canUnmarshalTextFromString() throws Exception {

		String validString = loadCsvString();

		C24UnmarshallingTransformer transformer = new C24UnmarshallingTransformer(
				model, new TextualSourceFactory());

		Message message = MessageBuilder.withPayload(validString).build();

		Message<?> outputMessage = transformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(Employees.class));

		Employees employees = (Employees) outputMessage.getPayload();

	}

	@Test
	public void canUnmarshalXmlFromString() throws Exception {

		String validString = loadXmlString();

		C24UnmarshallingTransformer transformer = new C24UnmarshallingTransformer(
				model, new XmlSourceFactory());

		Message message = MessageBuilder.withPayload(validString).build();

		Message<?> outputMessage = transformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(Employees.class));

		Employees employees = (Employees) outputMessage.getPayload();

	}

	@Test
	public void canUnmarshalXmlFromBytearray() throws Exception {

		byte[] valid1 = loadXmlBytes();

		C24UnmarshallingTransformer transformer = new C24UnmarshallingTransformer(
				model, new XmlSourceFactory());

		Message message = MessageBuilder.withPayload(valid1).build();

		Message<?> outputMessage = transformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(Employees.class));

		Employees employees = (Employees) outputMessage.getPayload();

	}

}
