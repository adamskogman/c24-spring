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
package biz.c24.io.spring.core;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.util.Assert;

/**
 * Simple enum to list all supported data formats.
 * 
 * @author Oliver Gierke
 */
public class DataFormat {

	private final Type type;
	private final List<MediaType> mediaTypes;

	/**
	 * Creates a new {@link DataFormat} for the given {@link Type}.
	 * 
	 * @param type must not be {@literal null}.
	 */
	public DataFormat(Type type, MediaType... mediaTypes) {
		this(type, Arrays.asList(mediaTypes));
	}

	/**
	 * Creates a new {@link DataFormat} of the given type to be bound to the given {@link List} of {@link MediaType}s.
	 * 
	 * @param type must not be {@literal null}.
	 * @param mediaTypes must not be {@literal null}.
	 */
	public DataFormat(Type type, List<MediaType> mediaTypes) {

		Assert.notNull(type);
		Assert.notNull(mediaTypes);

		this.type = type;
		this.mediaTypes = mediaTypes.isEmpty() ? type.getDefaultMediaTypes() : mediaTypes;
	}

	/**
	 * Returns all supported {@link MediaType}s.
	 * 
	 * @return
	 */
	public List<MediaType> getMediaTypes() {
		return mediaTypes;
	}

	/**
	 * Returns the {@link Type}.
	 * 
	 * @return
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Returns whether the {@link DataFormat} supports the given {@link MediaType}.
	 * 
	 * @param mediaType
	 * @return
	 */
	public boolean supports(MediaType mediaType) {

		for (MediaType type : mediaTypes) {
			if (type.isCompatibleWith(mediaType)) {
				return true;
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || !getClass().equals(obj.getClass())) {
			return false;
		}

		DataFormat that = (DataFormat) obj;

		return this.type.equals(that.type) && this.mediaTypes.equals(that.mediaTypes);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result += 31 * type.hashCode();
		result += 31 * mediaTypes.hashCode();
		return result;
	}

	/**
	 * Enum for various supported data types.
	 *
	 * @author Oliver Gierke
	 */
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
