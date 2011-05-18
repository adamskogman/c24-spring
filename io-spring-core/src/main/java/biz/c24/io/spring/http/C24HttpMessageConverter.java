package biz.c24.io.spring.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

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
import biz.c24.io.spring.util.C24Model;
import biz.c24.io.spring.util.C24Utils;

/**
 * {@link HttpMessageConverter} being able to convert objects of a {@link C24Model} into the specified data format.
 * 
 * @author Oliver Gierke
 */
public class C24HttpMessageConverter implements HttpMessageConverter<ComplexDataObject> {

	private final C24Model model;
	private final DataFormat dataFormat;
	private final List<MediaType> mediaTypes;

	/**
	 * Creates a new {@link C24HttpMessageConverter} for the given {@link C24Model} and {@link DataFormat}.
	 * 
	 * @param model
	 * @param dataFormat
	 */
	public C24HttpMessageConverter(C24Model model, DataFormat dataFormat) {

		Assert.notNull(model);
		Assert.notNull(dataFormat);

		this.model = model;
		this.dataFormat = dataFormat;
		this.mediaTypes = dataFormat.getDefaultMediaTypes();
	}

	/* (non-Javadoc)
	 * @see org.springframework.http.converter.HttpMessageConverter#canRead(java.lang.Class, org.springframework.http.MediaType)
	 */
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return ComplexDataObject.class.isAssignableFrom(clazz) && mediaTypes.contains(mediaType);
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
		return mediaTypes;
	}

	/* (non-Javadoc)
	 * @see org.springframework.http.converter.HttpMessageConverter#read(java.lang.Class, org.springframework.http.HttpInputMessage)
	 */
	public ComplexDataObject read(Class<? extends ComplexDataObject> clazz, HttpInputMessage inputMessage)
	throws IOException, HttpMessageNotReadableException {

		Source source = getSourceFor(inputMessage.getBody());
		ComplexDataObject result = source.readObject(model.getElementFor(clazz));

		return C24Utils.potentiallyUnwrapDocumentRoot(result);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.http.converter.HttpMessageConverter#write(java.lang.Object, org.springframework.http.MediaType, org.springframework.http.HttpOutputMessage)
	 */
	public void write(ComplexDataObject t, MediaType contentType, HttpOutputMessage outputMessage) throws IOException,
	HttpMessageNotWritableException {

		Sink sink = getSinkFor(outputMessage.getBody());
		sink.writeObject(t);
	}

	private Source getSourceFor(InputStream stream) {
		switch (dataFormat) {
		case XML:
			return new XMLSource(stream);
		case TEXT:
			return new TextualSource(stream);
		default:
			throw new IllegalStateException("Unsupported data format!");
		}
	}

	private Sink getSinkFor(OutputStream stream) {
		switch (dataFormat) {
		case XML:
			return new XMLSink(stream);
		case TEXT:
			return new TextualSink(stream);
		default:
			throw new IllegalStateException("Unsupported data format!");
		}
	}
}
