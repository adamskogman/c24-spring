package biz.c24.io.spring.http;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;

/**
 * Simple enum to list all supported data formats.
 * 
 * @author Oliver Gierke
 */
public class DataFormat {

	private final Type type;
	private final List<MediaType> mediaTypes;

	public DataFormat(Type type) {
		this(type, type.getDefaultMediaTypes());
	}

	public DataFormat(Type type, List<MediaType> mediaTypes) {
		this.type = type;
		this.mediaTypes = mediaTypes.isEmpty() ? type.getDefaultMediaTypes() : mediaTypes;
	}

	public List<MediaType> getMediaTypes() {
		return mediaTypes;
	}

	public Type getType() {
		return type;
	}

	public boolean supports(MediaType mediaType) {

		for (MediaType type : mediaTypes) {
			if (type.isCompatibleWith(mediaType)) {
				return true;
			}
		}

		return false;
	}

	public static enum Type {

		XML {
			@Override
			public List<MediaType> getDefaultMediaTypes() {
				return Arrays.asList(MediaType.APPLICATION_XML);
			}
		},

		TEXT {
			@Override
			public List<MediaType> getDefaultMediaTypes() {
				return Arrays.asList(MediaType.TEXT_PLAIN);
			}
		};

		/**
		 * Returns the {@link MediaType}s the {@link DataFormat} supports by default.
		 * 
		 * @return
		 */
		abstract List<MediaType> getDefaultMediaTypes();
	}
}
