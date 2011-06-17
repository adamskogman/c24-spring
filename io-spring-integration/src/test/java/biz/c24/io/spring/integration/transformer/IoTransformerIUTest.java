package biz.c24.io.spring.integration.transformer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.TextualSource;
import biz.c24.io.spring.util.C24Model;

import com.progress.ads.examples.models.basic.InputDocumentRootElement;
import com.progress.ads.examples.models.basic.OutputDocumentRoot;
import com.progress.ads.examples.transforms.basic.ExampleTransform;

public class IoTransformerIUTest {

	C24Model model = new C24Model(InputDocumentRootElement.getInstance());

	@Test
	public void canTransform() throws Exception {

		IoTransformer transformer = new IoTransformer();
		transformer.setTransformClass(ExampleTransform.class);

		Message<?> message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = transformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(OutputDocumentRoot.class));

		System.out.println(outputMessage.getPayload());

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
