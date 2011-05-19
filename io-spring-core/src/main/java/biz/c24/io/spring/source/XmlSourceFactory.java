/**
 * 
 */
package biz.c24.io.spring.source;

import java.io.InputStream;
import java.io.Reader;

import org.apache.poi.util.StringUtil;

import biz.c24.io.api.presentation.Source;
import biz.c24.io.api.presentation.XMLSource;

/**
 * @author askogman
 * 
 */
public class XmlSourceFactory implements SourceFactory {

	private String encoding;

	private boolean validationEnabled;
	private boolean schemaValidationEnabled;
	private boolean schemaFullCheckingEnabled;
	private boolean dynamicValidationEnabled;
	private boolean externalGeneralEntities = true;
	private boolean externalParameterEntities = true;
	private boolean warnOnDuplicateAttDef;
	private boolean warnOnUndeclaredElemDef;
	private boolean continueAfterFatalError;
	private boolean loadDTDGrammer = true;
	private boolean loadExternalDTD = true;

	/**
	 * 
	 */
	public XmlSourceFactory() {
	}

	/**
	 * Creates an XMLSource and sets all the properties on it that was given to
	 * this factory. Encoding is only set if provided.
	 * 
	 * @see biz.c24.io.spring.source.SourceFactory#getSource(java.io.Reader)
	 */
	public Source getSource(Reader reader) {

		XMLSource source = new XMLSource(reader);

		configureSource(source);

		return source;
	}

	protected void configureSource(XMLSource source) {
		source.setContinueAfterFatalError(continueAfterFatalError);
		source.setDynamicValidationEnabled(dynamicValidationEnabled);
		source.setExternalGeneralEntities(externalGeneralEntities);
		source.setExternalParameterEntities(externalParameterEntities);
		source.setLoadDTDGrammer(loadDTDGrammer);
		source.setLoadExternalDTD(loadExternalDTD);
		source.setSchemaFullCheckingEnabled(schemaFullCheckingEnabled);
		source.setSchemaValidationEnabled(schemaValidationEnabled);
		source.setValidationEnabled(validationEnabled);
		source.setWarnOnDuplicateAttDef(warnOnDuplicateAttDef);
		source.setWarnOnUndeclaredElemDef(warnOnUndeclaredElemDef);

		if (encoding != null)
			source.setEncoding(encoding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.c24.io.spring.source.SourceFactory#getSource(java.io.InputStream)
	 */
	public Source getSource(InputStream stream) {

		XMLSource source = new XMLSource(stream);

		configureSource(source);

		return source;

	}

}
