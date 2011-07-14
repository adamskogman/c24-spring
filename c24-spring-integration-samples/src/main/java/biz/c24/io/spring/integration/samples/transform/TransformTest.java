/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *			http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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