package biz.c24.io.spring.integration.router;

import static biz.c24.io.spring.integration.test.TestUtils.loadObject;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

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
		XPathRouter router = new XPathRouter("//Employee[1]/FirstName");
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
