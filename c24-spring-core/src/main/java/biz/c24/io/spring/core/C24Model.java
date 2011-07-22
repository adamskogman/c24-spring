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

import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.DocumentRoot;
import biz.c24.io.api.data.Element;
import biz.c24.io.api.presentation.Source;
import biz.c24.io.spring.source.SourceFactory;

/**
 * Simple value object to lookup supported types and {@link Element}s of a
 * {@link C24Model}.
 * 
 * @author Oliver Gierke
 */
public class C24Model implements SourceFactory {

	private final Map<Class<? extends ComplexDataObject>, Element> map = new HashMap<Class<? extends ComplexDataObject>, Element>();
	private final Element rootElement;

	/**
	 * Creates a new {@link C24Model} from the given {@link Element}.
	 * 
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	public C24Model(Element element) {

		Assert.notNull(element);
		this.rootElement = element;

		Class<? extends ComplexDataObject> type = element.getType()
				.getValidObjectClass();

		if (!DocumentRoot.class.isAssignableFrom(type)) {

			// If the model wraps something which is not a document root class,
			// then just use it as-is
			map.put(type, element);

		} else {

			// For document root classes, inspect the child elements.

			DocumentRoot root = (DocumentRoot) BeanUtils.instantiateClass(type);

			for (int i = 0; i < root.getElementDeclCount(); i++) {
				Element declaredElement = root.getElementDecl(i);
				map.put(declaredElement.getType().getValidObjectClass(), element);
			}

		}

	}

	/**
	 * Returns the root {@link Element} the model was created from.
	 * 
	 * @return
	 */
	public Element getRootElement() {
		return this.rootElement;
	}

	/**
	 * Returns the element backed by the given type.
	 * 
	 * @param type
	 * @return
	 */
	public Element getElementFor(Class<? extends ComplexDataObject> type) {
		return map.get(type);
	}

	/**
	 * Returns all types supported by this model.
	 * 
	 * @return
	 */
	public Set<Class<? extends ComplexDataObject>> getSupportedTypes() {
		return map.keySet();
	}

	/**
	 * @return A cloned instance of the models default source.
	 */
	public Source getRootElementSource() {
		// A clone is returned by source(), no need to create a new one.
		return getRootElement().getModel().source();
	}

	public Source getSource(Reader reader) {
		Source source = getRootElementSource();
		source.setReader(reader);
		return source;
	}

	public Source getSource(InputStream stream) {
		Source source = getRootElementSource();
		source.setInputStream(stream);
		return source;
	}

}
