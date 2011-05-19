/**
 * 
 */
package biz.c24.io.spring.integration.transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
 * 
 * 
 * 
 * @author askogman
 * 
 */
public class C24UnmarshallingTransformer extends
		AbstractPayloadTransformer<Object, Object> {

	private final C24Model model;
	private final SourceFactory sourceFactory;

	protected final Log logger = LogFactory.getLog(getClass());

	public C24UnmarshallingTransformer(C24Model model,
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

	private Source getSourceFor(Object payload) throws Exception {
		Source source;
		if (payload instanceof InputStream) {
			source = sourceFactory.getSource((InputStream) payload);
		} else if (payload instanceof Reader) {
			source = sourceFactory.getSource((Reader) payload);
		} else if (payload instanceof String) {
			source = sourceFactory
					.getSource(new StringReader((String) payload));
		} else if (payload instanceof File) {
			File file = (File) payload;
			source = sourceFactory.getSource(new FileInputStream(file));
		} // TODO More conversions?
		else {
			throw new MessagingException(
					"failed to transform message, payload not assignable from java.io.InputStream/Reader and no conversion possible");
		}

		return source;

	}

}
