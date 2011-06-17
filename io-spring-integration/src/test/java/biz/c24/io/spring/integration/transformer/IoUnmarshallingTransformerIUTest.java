package biz.c24.io.spring.integration.transformer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.FileCopyUtils;

import biz.c24.io.spring.source.TextualSourceFactory;
import biz.c24.io.spring.source.XmlSourceFactory;
import biz.c24.io.spring.util.C24Model;

import com.progress.ads.examples.models.basic.Employees;
import com.progress.ads.examples.models.basic.InputDocumentRootElement;

public class IoUnmarshallingTransformerIUTest {

	C24Model model = new C24Model(InputDocumentRootElement.getInstance());

	@Test
	public void canUnmarshalTextFromBytearray() throws Exception {

		ClassPathResource resource = new ClassPathResource("valid-1.txt",
				IoUnmarshallingTransformerIUTest.class);
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());

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

		ClassPathResource resource = new ClassPathResource("valid-1.txt",
				IoUnmarshallingTransformerIUTest.class);
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());
		String validString = new String(valid1, "UTF-8");

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

		ClassPathResource resource = new ClassPathResource("valid-XML-1.xml",
				IoUnmarshallingTransformerIUTest.class);
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());
		String validString = new String(valid1, "UTF-8");

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

		ClassPathResource resource = new ClassPathResource("valid-XML-1.xml",
				IoUnmarshallingTransformerIUTest.class);
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());

		IoUnmarshallingTransformer transformer = new IoUnmarshallingTransformer(
				model, new XmlSourceFactory());

		Message message = MessageBuilder.withPayload(valid1).build();

		Message<?> outputMessage = transformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(Employees.class));

		Employees employees = (Employees) outputMessage.getPayload();

	}

}
