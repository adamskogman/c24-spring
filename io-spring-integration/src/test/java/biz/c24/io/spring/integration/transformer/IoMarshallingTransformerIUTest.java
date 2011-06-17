package biz.c24.io.spring.integration.transformer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.FileCopyUtils;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.TextualSource;
import biz.c24.io.spring.sink.OutputType;
import biz.c24.io.spring.sink.TextualSinkFactory;
import biz.c24.io.spring.sink.XmlSinkFactory;
import biz.c24.io.spring.util.C24Model;

import com.progress.ads.examples.models.basic.InputDocumentRootElement;

public class IoMarshallingTransformerIUTest {

	C24Model model = new C24Model(InputDocumentRootElement.getInstance());

	@Test
	public void canMarshalXmlToBytearray() throws Exception {

		IoMarshallingTransformer ioMarshallingTransformer = new IoMarshallingTransformer();
		ioMarshallingTransformer.setOutputType(OutputType.BYTE_ARRAY);
		XmlSinkFactory xmlSinkFactory = new XmlSinkFactory();
		ioMarshallingTransformer.setSinkFactory(xmlSinkFactory);

		Message<?> message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = ioMarshallingTransformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(byte[].class));

		String xml = new String((byte[]) outputMessage.getPayload(), "UTF-8");
		System.out.println(xml);

		// TODO: XML equivalence match

	}

	@Test
	public void canMarshalXmlToString() throws Exception {

		IoMarshallingTransformer ioMarshallingTransformer = new IoMarshallingTransformer();
		ioMarshallingTransformer.setOutputType(OutputType.STRING);
		XmlSinkFactory xmlSinkFactory = new XmlSinkFactory();
		ioMarshallingTransformer.setSinkFactory(xmlSinkFactory);

		Message message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = ioMarshallingTransformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(String.class));

		System.out.println(outputMessage.getPayload());

		// TODO: XML equivalence match

	}

	@Test
	public void canMarshalTextToString() throws Exception {

		IoMarshallingTransformer ioMarshallingTransformer = new IoMarshallingTransformer();
		ioMarshallingTransformer.setOutputType(OutputType.STRING);
		ioMarshallingTransformer.setSinkFactory(new TextualSinkFactory());

		Message message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = ioMarshallingTransformer.transform(message);

		assertThat((String) outputMessage.getPayload(), is(loadCsvString()));

	}

	@Test
	public void canMarshalTextToByteArray() throws Exception {

		IoMarshallingTransformer ioMarshallingTransformer = new IoMarshallingTransformer();
		ioMarshallingTransformer.setOutputType(OutputType.BYTE_ARRAY);
		ioMarshallingTransformer.setSinkFactory(new TextualSinkFactory());

		Message message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = ioMarshallingTransformer.transform(message);

		assertThat((byte[]) outputMessage.getPayload(), is(loadCsvBytes()));

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

	byte[] loadXmlBytes() throws Exception {

		ClassPathResource resource = new ClassPathResource("valid-XML-1.xml",
				IoUnmarshallingTransformerIUTest.class);
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());

		return valid1;
	}

	byte[] loadCsvBytes() throws Exception {

		ClassPathResource resource = new ClassPathResource("valid-1.txt",
				IoUnmarshallingTransformerIUTest.class);
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());

		return valid1;
	}

	String loadCsvString() throws Exception {

		ClassPathResource resource = new ClassPathResource("valid-1.txt",
				IoUnmarshallingTransformerIUTest.class);
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());

		return new String(valid1, "UTF-8");
	}

}
