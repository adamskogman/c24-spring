package biz.c24.io.spring.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
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
import biz.c24.io.spring.http.DataFormat.Type;
import biz.c24.io.spring.util.C24Model;
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


	private class DataFormats {

		private final Collection<DataFormat> formats;

		public DataFormats(Collection<DataFormat> formats) {
			Assert.notNull(formats);
			this.formats = formats;
		}

		public Type getTypeFor(MediaType mediaType) {
			for (DataFormat format : formats) {
				if (format.supports(mediaType)) {
					return format.getType();
				}
			}

			return null;
		}

		public List<MediaType> getSupportedMediaTypes() {
			List<MediaType> result = new ArrayList<MediaType>();
			for (DataFormat format : formats) {
				result.addAll(format.getMediaTypes());
			}
			return result;
		}


		public boolean supports(MediaType mediaType) {

			for (DataFormat format : formats) {
				return format.supports(mediaType);
			}

			return false;
		}
	}
}
