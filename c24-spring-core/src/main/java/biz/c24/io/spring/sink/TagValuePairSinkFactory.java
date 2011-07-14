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
package biz.c24.io.spring.sink;

import biz.c24.io.api.presentation.Sink;
import biz.c24.io.api.presentation.TagValuePairSink;

/**
 * @author askogman
 * 
 */
public class TagValuePairSinkFactory extends AbstractSinkFactory {

	private Boolean indenting = null;

	@Override
	protected Sink createSink() {
		TagValuePairSink tagValuePairSink = new TagValuePairSink();

		if (indenting != null) {
			tagValuePairSink.setIndenting(indenting);
		}

		return tagValuePairSink;
	}

	public Boolean getIndenting() {
		return indenting;
	}

	public void setIndenting(Boolean indenting) {
		this.indenting = indenting;
	}
}
