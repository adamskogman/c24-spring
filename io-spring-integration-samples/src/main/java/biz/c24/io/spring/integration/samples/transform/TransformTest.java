/**
 * 
 */
package biz.c24.io.spring.integration.samples.transform;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.core.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("c24-transform-sample-context.xml")
public class TransformTest {

	@Autowired
	MessageChannel inputChannel;

	private MessagingTemplate template;

	@Autowired
	PollableChannel outputChannel;

	@Before
	public void before() {
		template = new MessagingTemplate(inputChannel);
	}

	@Test
	public void transform() throws Exception {

		template.convertAndSend(loadCsvBytes());

		Message<?> message = outputChannel.receive(1);

		System.out.println(message.getPayload());

	}

	byte[] loadCsvBytes() throws Exception {

		ClassPathResource resource = new ClassPathResource("valid-1.txt",
				this.getClass());
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());

		return valid1;
	}

}