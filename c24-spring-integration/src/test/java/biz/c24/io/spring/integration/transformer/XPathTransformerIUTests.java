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
import static org.junit.matchers.JUnitMatchers.*;

import java.util.List;

import org.junit.Test;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import biz.c24.io.examples.models.basic.Employee;
import biz.c24.io.spring.integration.xpath.XPathEvaluationType;
public class XPathTransformerIUTests {

	@Test
	public void canTransformToListOfString() throws Exception {

		C24XPathTransformer transformer = new C24XPathTransformer("//FirstName");
		transformer.setEvaluationType(XPathEvaluationType.LIST_RESULT);

		Message<?> message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = transformer.transform(message);

		@SuppressWarnings("unchecked")
		List<String> payload = (List<String>) outputMessage.getPayload();
		assertThat(payload, notNullValue());
		assertThat(payload, hasItems("Andy", "Joe", "Greg"));

	}

	@Test
	public void canTransformToListOfEmployee() throws Exception {

		C24XPathTransformer transformer = new C24XPathTransformer("//Employee");
		transformer.setEvaluationType(XPathEvaluationType.LIST_RESULT);

		Message<?> message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = transformer.transform(message);

		List<Object> payload = (List<Object>) outputMessage.getPayload();
		assertThat(payload, notNullValue());
		for (Object o : payload) {
			assertThat(o, is(Employee.class));
		}
	}

	@Test
	public void canTransformToString() throws Exception {

		C24XPathTransformer transformer = new C24XPathTransformer(
				"//Employee[1]/FirstName");
		transformer.setEvaluationType(XPathEvaluationType.STRING_RESULT);

		Message<?> message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = transformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat((String) outputMessage.getPayload(), is("Andy"));
	}

}
