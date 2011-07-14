/**
 * 
 */
package biz.c24.io.spring.integration.test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.TextualSource;
import biz.c24.io.examples.models.basic.InputDocumentRootElement;
import biz.c24.io.spring.integration.transformer.IoUnmarshallingTransformerIUTests;

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
