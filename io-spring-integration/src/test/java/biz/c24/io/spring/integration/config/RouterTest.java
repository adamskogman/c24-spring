package biz.c24.io.spring.integration.config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.core.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

import com.progress.ads.examples.models.basic.Employees;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("router.xml")
public class RouterTest {

	@Autowired
	MessageChannel textInputChannel;

	private MessagingTemplate template;

	@Autowired
	@Qualifier("rightChannel")
	PollableChannel rightChannel;

	@Before
	public void before() {
		template = new MessagingTemplate(textInputChannel);
	}

	@Test
	public void canUnmarshal() throws Exception {

		template.convertAndSend(loadCsvBytes());

		Message<?> message = rightChannel.receive(1);

		assertThat(message.getPayload(), notNullValue());
		assertThat(message.getPayload(), is(Employees.class));

	}

	byte[] loadCsvBytes() throws Exception {

		ClassPathResource resource = new ClassPathResource("valid-1.txt");
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());

		return valid1;
	}

}
