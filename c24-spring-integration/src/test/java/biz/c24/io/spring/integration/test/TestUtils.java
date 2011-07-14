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
package biz.c24.io.spring.integration.test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.TextualSource;
import biz.c24.io.examples.models.basic.InputDocumentRootElement;

/**
 * @author askogman
 * 
 */
public class TestUtils {

	public static ComplexDataObject loadObject() throws Exception {

		ClassPathResource resource = new ClassPathResource("valid-1.txt");

		TextualSource textualSource = new TextualSource(
				resource.getInputStream());

		ComplexDataObject object = textualSource
				.readObject(InputDocumentRootElement.getInstance());

		return object;
	}

	public static byte[] loadXmlBytes() throws Exception {

		ClassPathResource resource = new ClassPathResource("valid-XML-1.xml");
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());

		return valid1;
	}

	public static String loadXmlString() throws Exception {

		return new String(loadXmlBytes(), "UTF-8");
	}

	public static byte[] loadCsvBytes() throws Exception {

		ClassPathResource resource = new ClassPathResource("valid-1.txt");
		byte[] valid1 = FileCopyUtils
				.copyToByteArray(resource.getInputStream());

		return valid1;
	}

	public static String loadCsvString() throws Exception {

		return new String(loadCsvBytes(), "UTF-8");
	}

}
