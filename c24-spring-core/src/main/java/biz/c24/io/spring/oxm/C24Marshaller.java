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
package biz.c24.io.spring.oxm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.util.Assert;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.Element;
import biz.c24.io.api.presentation.XMLSink;
import biz.c24.io.api.presentation.XMLSource;
import biz.c24.io.spring.util.C24Model;
import biz.c24.io.spring.util.C24Utils;

/**
 * Spring OXM {@link Marshaller} and {@link Unmarshaller} implementation that are capable of converting C24 objects into
 * XML and vice versa. Currently only {@link StreamSource} and {@link StreamResult} implementations are supported.
 * 
 * @author Oliver Gierke
 */
public class C24Marshaller implements Marshaller, Unmarshaller {

	private static final String UNSUPPORTED_SOURCE = "Unsupported Source implementation! Only StreamSources are supported!";
	private static final String UNSUPPORTED_RESULT = "Unsupported Result implementation! Only StreamResults are supported!";

	private final C24Model model;

	/**
	 * Creates a new C24Marshaller from the given {@link Element}.
	 * 
	 * @param metamodelElement
	 */
	public C24Marshaller(C24Model model) {

		Assert.notNull(model);
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.oxm.Marshaller#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> clazz) {
		return model.getSupportedTypes().contains(clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.oxm.Unmarshaller#unmarshal(javax.xml.transform.Source)
	 */
	public Object unmarshal(Source source) throws IOException, XmlMappingException {

		Assert.isInstanceOf(StreamSource.class, source, UNSUPPORTED_SOURCE);

		StreamSource streamSource = (StreamSource) source;
		XMLSource xmlSource = getXmlSourceFrom(streamSource);

		ComplexDataObject result = xmlSource.readObject(model.getRootElement());
		return C24Utils.potentiallyUnwrapDocumentRoot(result);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.oxm.Marshaller#marshal(java.lang.Object, javax.xml.transform.Result)
	 */
	public void marshal(Object graph, Result result) throws IOException, XmlMappingException {

		Assert.isInstanceOf(StreamResult.class, result, UNSUPPORTED_RESULT);
		Assert.isInstanceOf(ComplexDataObject.class, graph);

		XMLSink sink = getXmlSinkFrom((StreamResult) result);
		sink.writeObject((ComplexDataObject) graph);
	}

	/**
	 * Returns a C24 {@link XMLSource} for the given {@link StreamSource}. Will prefer the source's {@link Reader} over
	 * its {@link InputStream}.
	 * 
	 * @param source
	 * @return
	 */
	private XMLSource getXmlSourceFrom(StreamSource source) {

		Reader reader = source.getReader();
		return reader != null ? new XMLSource(reader) : new XMLSource(source.getInputStream());
	}

	/**
	 * Returns a C24 {@link XMLSink} for the given {@link StreamResult}. Will prefer the result's {@link Writer} over its
	 * {@link OutputStream}.
	 * 
	 * @param result
	 * @return
	 */
	private XMLSink getXmlSinkFrom(StreamResult result) {

		Writer writer = result.getWriter();
		return writer != null ? new XMLSink(writer) : new XMLSink(result.getOutputStream());
	}
}
