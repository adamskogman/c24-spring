package biz.c24.io.spring.http;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;

import nonamespace.CustomerLocal;
import nonamespace.SampleModelDocumentRootElement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;

import biz.c24.io.spring.http.DataFormat.Type;
import biz.c24.io.spring.model.TestConstants;
import biz.c24.io.spring.util.C24Model;

/**
 * 
 * @author Oliver Gierke
 */
@RunWith(MockitoJUnitRunner.class)
public class C24HttpMessageConverterUnitTests {

	C24Model model = new C24Model(SampleModelDocumentRootElement.getInstance());
	C24HttpMessageConverter converter = new C24HttpMessageConverter(model,
			Collections.singleton(new DataFormat(Type.XML)));

	@Mock
	HttpInputMessage inputMessage;

	@Test
	public void canConvertClassIfMediaTypeMatches() {
		assertThat(converter.canRead(CustomerLocal.class, MediaType.APPLICATION_XML), is(true));
	}

	@Test
	public void cannotConvertNonComplexDataObject() {
		assertThat(converter.canRead(String.class, MediaType.APPLICATION_XML), is(false));
	}

	@Test
	public void cannotConvertNonMatchingMediaType() {
		C24HttpMessageConverter converter = new C24HttpMessageConverter(model, Collections.singleton(new DataFormat(
				Type.TEXT)));
		assertThat(converter.canRead(CustomerLocal.class, MediaType.APPLICATION_XML), is(false));
	}

	@Test
	public void readsXmlCorrectly() throws IOException {

		when(inputMessage.getBody()).thenReturn(new ByteArrayInputStream(TestConstants.SAMPLE_XML.getBytes()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		when(inputMessage.getHeaders()).thenReturn(headers);

		CustomerLocal result = (CustomerLocal) converter.read(CustomerLocal.class, inputMessage);
		assertThat(result, is(notNullValue()));
		assertThat(result.getFirstname(), is("Firstname"));
	}
}
