package biz.c24.io.spring.integration.transformer;

import static biz.c24.io.spring.integration.test.TestUtils.loadObject;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import biz.c24.io.examples.models.basic.InputDocumentRootElement;
import biz.c24.io.examples.models.basic.OutputDocumentRoot;
import biz.c24.io.examples.transforms.basic.ExampleTransform;
import biz.c24.io.spring.util.C24Model;


public class IoTransformerIUTests {

	C24Model model = new C24Model(InputDocumentRootElement.getInstance());

	@Test
	public void canTransform() throws Exception {

		IoTransformer transformer = new IoTransformer();
		transformer.setTransformClass(ExampleTransform.class);

		Message<?> message = MessageBuilder.withPayload(loadObject()).build();

		Message<?> outputMessage = transformer.transform(message);

		assertThat(outputMessage.getPayload(), notNullValue());
		assertThat(outputMessage.getPayload(), is(OutputDocumentRoot.class));


	}

}
