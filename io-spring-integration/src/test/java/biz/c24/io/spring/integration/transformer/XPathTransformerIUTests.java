package biz.c24.io.spring.integration.transformer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.TextualSource;
import biz.c24.io.examples.models.basic.Employee;
import biz.c24.io.examples.models.basic.InputDocumentRootElement;
import biz.c24.io.spring.integration.xpath.XPathEvaluationType;
import static biz.c24.io.spring.integration.test.TestUtils.loadObject;
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
