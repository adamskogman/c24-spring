package biz.c24.io.spring.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import biz.c24.io.spring.core.DataFormat.Type;


/**
 * Simple value object to capture functionality over a {@link Collection} of {@link DataFormat}s.
 *
 * @author Oliver Gierke
 */
public class DataFormats {

	private final Collection<DataFormat> formats;

	/**
	 * Creates a new {@link DataFormats} wrapper from the given {@link Collection} of {@link DataFormat}s.
	 * 
	 * @param formats
	 */
	public DataFormats(Collection<DataFormat> formats) {
		Assert.notNull(formats);
		this.formats = formats;
	}

	/**
	 * Returns the {@link Type} of the {@link DataFormat} supporting the given {@link MediaType}. Returns {@literal null}
	 * if none of the contained {@link DataFormat}s supports the given {@link MediaType}.
	 * 
	 * @param mediaType
	 * @return
	 */
	public Type getTypeFor(MediaType mediaType) {
		for (DataFormat format : formats) {
			if (format.supports(mediaType)) {
				return format.getType();
			}
		}

		return null;
	}

	/**
	 * Returns all {@link MediaType}s supported by the {@link DataFormats}.
	 * 
	 * @return
	 */
	public List<MediaType> getSupportedMediaTypes() {
		List<MediaType> result = new ArrayList<MediaType>();
		for (DataFormat format : formats) {
			result.addAll(format.getMediaTypes());
		}
		return result;
	}

	/**
	 * Returns whether the {@link DataFormats} support the given {@link MediaType}.
	 * 
	 * @param mediaType
	 * @return
	 */
	public boolean supports(MediaType mediaType) {

		for (DataFormat format : formats) {
			return format.supports(mediaType);
		}

		return false;
	}
}