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
package biz.c24.io.spring.integration.xpath;

import java.util.ArrayList;
import java.util.List;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.IOContext;
import biz.c24.io.api.data.IOXPath;
import biz.c24.io.api.data.IOXPathException;

/**
 * Enumeration of different types of XPath evaluation used to indicate the type
 * of evaluation that should be carried out using a provided XPath expression.
 */
public enum XPathEvaluationType {

	BOOLEAN_RESULT {
		@Override
		public Object evaluateXPath(IOXPath xpath, ComplexDataObject dataObject)
				throws IOXPathException {
			return xpath.getBoolean(dataObject);
		}
	},

	STRING_RESULT {
		@Override
		public Object evaluateXPath(IOXPath xpath, ComplexDataObject dataObject)
				throws IOXPathException {
			return xpath.getString(dataObject);
		}
	},

	NUMBER_RESULT {
		@Override
		public Object evaluateXPath(IOXPath xpath, ComplexDataObject dataObject)
				throws IOXPathException {
			return xpath.getNumber(dataObject);
		}

	},

	OBJECT_RESULT {
		@Override
		public Object evaluateXPath(IOXPath xpath, ComplexDataObject dataObject)
				throws IOXPathException {
			return xpath.getObject(dataObject);
		}

	},

	/**
	 * The returned list will be a list of domain objects, not a list of
	 * IOContext.
	 * 
	 * @author askogman
	 * 
	 */
	LIST_RESULT {
		@Override
		public Object evaluateXPath(IOXPath xpath, ComplexDataObject dataObject)
				throws IOXPathException {
			@SuppressWarnings("unchecked")
			List<IOContext> list = xpath.getList(dataObject);

			// Fail-fast
			if (list == null) {
				return null;
			}

			List<Object> payload = new ArrayList<Object>(list.size());
			for (IOContext ioContext : list) {
				payload.add(ioContext.getInstance());
			}

			return payload;

		}

	};

	public abstract Object evaluateXPath(IOXPath xpath,
			ComplexDataObject dataObject) throws IOXPathException;

}
