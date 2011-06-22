package biz.c24.io.spring.integration.transformer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import biz.c24.io.examples.models.basic.Employees;
import biz.c24.io.examples.models.basic.InputDocumentRootElement;
import biz.c24.io.spring.source.TextualSourceFactory;
import biz.c24.io.spring.source.XmlSourceFactory;
import biz.c24.io.spring.util.C24Model;
import static biz.c24.io.spring.integration.test.TestUtils.*;

public class IoUnmarshallingTransformerIUTests {

	C24Model model = new C24Model(InputDocumentRootElement.getInstance());

	@Test
	public void canUnmarshalTextFromBytearray() throws Exception {

		byte[] valid1 = loadCsvBytes();
		IoUnmarshallingTransformer transformer = new IoUnmarshallingTransformer(
				model, new TextualSourceFactory());

		Message message = MessageBuilder.withPayload(valid1).build();

		Message<?> outputMessage = transformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(Employees.class));

		Employees employees = (Employees) outputMessage.getPayload();

	}

	@Test
	public void canUnmarshalTextFromString() throws Exception {

		
		String validString = loadCsvString();

		IoUnmarshallingTransformer transformer = new IoUnmarshallingTransformer(
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

		IoUnmarshallingTransformer transformer = new IoUnmarshallingTransformer(
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

		IoUnmarshallingTransformer transformer = new IoUnmarshallingTransformer(
				model, new XmlSourceFactory());

		Message message = MessageBuilder.withPayload(valid1).build();

		Message<?> outputMessage = transformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(Employees.class));

		Employees employees = (Employees) outputMessage.getPayload();

	}

}
