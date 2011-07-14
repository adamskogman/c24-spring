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
package biz.c24.io.spring.integration.router;

import static biz.c24.io.spring.integration.test.TestUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.integration.Message;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.support.channel.ChannelResolver;

import biz.c24.io.api.data.ComplexDataObject;

@RunWith(MockitoJUnitRunner.class)
public class XPathRouterTestIUTests {

	@Mock
	ChannelResolver channelResolver;

	QueueChannel messageChannel = new QueueChannel();

	@Test
	public void routesOnString() throws Exception {

		// Andy
		C24XPathRouter router = new C24XPathRouter("//Employee[1]/FirstName");
		router.setChannelResolver(channelResolver);

		when(channelResolver.resolveChannelName("Andy")).thenReturn(
				messageChannel);

		ComplexDataObject payload = loadObject();
		Message<?> message = MessageBuilder.withPayload(payload).build();

		router.handleMessage(message);

		// Check
		Message<?> actualMessage = messageChannel.receive();

		assertThat((ComplexDataObject) actualMessage.getPayload(),
				sameInstance(payload));

	}

}
