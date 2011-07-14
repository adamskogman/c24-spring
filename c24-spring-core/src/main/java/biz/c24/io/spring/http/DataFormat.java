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
