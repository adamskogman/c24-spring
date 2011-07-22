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
package biz.c24.io.spring.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.Sink;
import biz.c24.io.api.presentation.Source;
import biz.c24.io.api.presentation.TextualSink;
import biz.c24.io.api.presentation.TextualSource;
import biz.c24.io.api.presentation.XMLSink;
import biz.c24.io.api.presentation.XMLSource;
import biz.c24.io.spring.core.C24Model;
import biz.c24.io.spring.core.DataFormat;
import biz.c24.io.spring.core.DataFormats;
import biz.c24.io.spring.util.C24Utils;

/**
 * {@link HttpMessageConverter} being able to convert objects of a {@link C24Model} into the specified data format.
 * 
 * @author Oliver Gierke
 */
public class C24HttpMessageConverter implements HttpMessageConverter<ComplexDataObject> {

	private final C24Model model;
	private final DataFormats dataFormat;

	/**
	 * Creates a new {@link C24HttpMessageConverter} for the given {@link C24Model} and {@link DataFormat}.
	 * 
	 * @param model
	 * @param dataFormat
	 */
	public C24HttpMessageConverter(C24Model model, Set<DataFormat> dataFormat) {

		Assert.notNull(model);
		Assert.notNull(dataFormat);

		this.model = model;
		this.dataFormat = new DataFormats(dataFormat);
	}

	/* (non-Javadoc)
	 * @see org.springframework.http.converter.HttpMessageConverter#canRead(java.lang.Class, org.springframework.http.MediaType)
	 */
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return ComplexDataObject.class.isAssignableFrom(clazz) && dataFormat.supports(mediaType);
	}

	/* (non-Javadoc)
	 * @see org.springframework.http.converter.HttpMessageConverter#canWrite(java.lang.Class, org.springframework.http.MediaType)
	 */
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return canRead(clazz, mediaType);
	}

	/* (non-Javadoc)
	 * @see org.springframework.http.converter.HttpMessageConverter#getSupportedMediaTypes()
	 */
	public List<MediaType> getSupportedMediaTypes() {

		return dataFormat.getSupportedMediaTypes();
	}

	/* (non-Javadoc)
	 * @see org.springframework.http.converter.HttpMessageConverter#read(java.lang.Class, org.springframework.http.HttpInputMessage)
	 */
	public ComplexDataObject read(Class<? extends ComplexDataObject> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		HttpHeaders headers = inputMessage.getHeaders();
		MediaType contentType = headers.getContentType();

		Source source = getSourceFor(inputMessage.getBody(), contentType);
		ComplexDataObject result = source.readObject(model.getElementFor(clazz));

		return C24Utils.potentiallyUnwrapDocumentRoot(result);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.http.converter.HttpMessageConverter#write(java.lang.Object, org.springframework.http.MediaType, org.springframework.http.HttpOutputMessage)
	 */
	public void write(ComplexDataObject t, MediaType contentType, HttpOutputMessage outputMessage) throws IOException,
	HttpMessageNotWritableException {

		Sink sink = getSinkFor(outputMessage.getBody(), contentType);
		sink.writeObject(t);
	}

	private Source getSourceFor(InputStream stream, MediaType mediaType) {
		switch (dataFormat.getTypeFor(mediaType)) {
		case XML:
			return new XMLSource(stream);
		case TEXT:
			return new TextualSource(stream);
		default:
			throw new IllegalStateException("Unsupported data format!");
		}
	}

	private Sink getSinkFor(OutputStream stream, MediaType mediaType) {
		switch (dataFormat.getTypeFor(mediaType)) {
		case XML:
			return new XMLSink(stream);
		case TEXT:
			return new TextualSink(stream);
		default:
			throw new IllegalStateException("Unsupported data format!");
		}
	}
}
