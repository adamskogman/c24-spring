package biz.c24.io.spring.integration.config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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
@ContextConfiguration("marshal.xml")
public class MarshalITests {

	@Autowired
	MessageChannel textInputChannel;

	private MessagingTemplate template;

	@Autowired
	PollableChannel outputChannel;

	@Before
	public void before() {
		template = new MessagingTemplate(textInputChannel);
	}

	@Test
	public void canMarshal() throws Exception {

		template.convertAndSend(loadCsvBytes());

		Message<?> message = outputChannel.receive(1);

		assertThat(message, notNullValue());
		assertThat(message.getPayload(), is(byte[].class));

		
	}

	byte[] loadCsvBytes() throws Exception {

		ClassPathResource resource = new ClassPathResource("valid-1.txt");
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());

		return valid1;
	}

}
