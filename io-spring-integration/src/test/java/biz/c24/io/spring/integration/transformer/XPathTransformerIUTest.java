package biz.c24.io.spring.integration.transformer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.*;

import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.TextualSource;
import biz.c24.io.spring.integration.xpath.XPathEvaluationType;

import com.progress.ads.examples.models.basic.Employee;
import com.progress.ads.examples.models.basic.InputDocumentRootElement;

public class XPathTransformerIUTest {

	@Test
	public void canTransformToListOfString() throws Exception {

		XPathTransformer transformer = new XPathTransformer("//FirstName",
				XPathEvaluationType.LIST_RESULT);

		Message<?> message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = transformer.transform(message);

		@SuppressWarnings("unchecked")
		List<String> payload = (List<String>) outputMessage.getPayload();
		assertThat(payload, notNullValue());
		assertThat(payload, hasItems("Andy", "Joe", "Greg"));

	}

	@Test
	public void canTransformToListOfEmployee() throws Exception {

		XPathTransformer transformer = new XPathTransformer("//Employee",
				XPathEvaluationType.LIST_RESULT);

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

		XPathTransformer transformer = new XPathTransformer("//Employee[1]/FirstName",
				XPathEvaluationType.STRING_RESULT);

		Message<?> message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = transformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat((String)outputMessage.getPayload(), is("Andy"));
	}

	
	ComplexDataObject loadObject() throws Exception {

		ClassPathResource resource = new ClassPathResource("valid-1.txt",
				IoUnmarshallingTransformerIUTest.class);

		TextualSource textualSource = new TextualSource(
				resource.getInputStream());

		ComplexDataObject object = textualSource
				.readObject(InputDocumentRootElement.getInstance());

		return object;
	}

}
