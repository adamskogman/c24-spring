/**
 * 
 */
package biz.c24.io.spring.integration.transformer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.MessagingException;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.util.Assert;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.Source;
import biz.c24.io.spring.source.SourceFactory;
import biz.c24.io.spring.util.C24Model;
import biz.c24.io.spring.util.C24Utils;

/**
 * Unmarshals a raw input to a Complex object.
 * 
 * Goes through the following steps:
 * <ol>
 * <li>Turn the incoming payload into an InputStream or Reader</li>
 * <li>Using the SourceFactory, turn the IS/reader into a Source. Plain or XML
 * factories are available.
 * <li>Read from the Source using the root element of the Model.
 * <li>Unpack the complext object if it's a document root, i.e. let C24
 * determine the Complex Object and we just pick then one it parsed to
 * </ol>
 * 
 * @author askogman
 * 
 */
public class IoUnmarshallingTransformer extends
		AbstractPayloadTransformer<Object, Object> {

	private final C24Model model;
	private final SourceFactory sourceFactory;

	protected final Log logger = LogFactory.getLog(getClass());

	public IoUnmarshallingTransformer(C24Model model,
			SourceFactory sourceFactory) {
		super();

		Assert.notNull(model);
		Assert.notNull(sourceFactory);

		this.model = model;
		this.sourceFactory = sourceFactory;
	}

	@Override
	protected Object transformPayload(Object payload) throws Exception {

		Source source = getSourceFor(payload);

		ComplexDataObject result = source.readObject(model.getRootElement());

		return C24Utils.potentiallyUnwrapDocumentRoot(result);

	}

	Source getSourceFor(Object payload) throws Exception {
		Source source;

		// Things that can be turned into a Reader
		if (payload instanceof Reader) {
			source = sourceFactory.getSource((Reader) payload);
		} else if (payload instanceof String) {
			source = sourceFactory
					.getSource(new StringReader((String) payload));
		}
		// Things that can be turned into an input stream
		else if (payload instanceof InputStream) {
			source = sourceFactory.getSource((InputStream) payload);
		} else if (payload instanceof byte[]) {
			source = sourceFactory.getSource(new ByteArrayInputStream(
					(byte[]) payload));
		} else if (payload instanceof File) {
			File file = (File) payload;
			source = sourceFactory.getSource(new FileInputStream(file));
		} else {
			throw new MessagingException(
					"failed to transform message, payload not assignable from java.io.InputStream/Reader and no conversion possible");
		}

		return source;

	}

}
