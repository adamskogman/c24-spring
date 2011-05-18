package biz.c24.io.spring.http;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;

/**
 * Simple enum to list all supported data formats.
 * 
 * @author Oliver Gierke
 */
public enum DataFormat {

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
