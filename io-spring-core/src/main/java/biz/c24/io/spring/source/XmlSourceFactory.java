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

	private Boolean validationEnabled;
	private Boolean schemaValidationEnabled;
	private Boolean schemaFullCheckingEnabled;
	private Boolean dynamicValidationEnabled;
	private Boolean externalGeneralEntities;
	private Boolean externalParameterEntities;
	private Boolean warnOnDuplicateAttDef;
	private Boolean warnOnUndeclaredElemDef;
	private Boolean continueAfterFatalError;
	private Boolean loadDTDGrammer;
	private Boolean loadExternalDTD;

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

	/**
	 * Configure the source
	 * 
	 * @param source
	 */
	final protected void configureSource(XMLSource source) {

		// Only set the property if a value has been provided to one of the
		// setters. If not, let the underlying Source retain the default.

		if (continueAfterFatalError != null)
			source.setContinueAfterFatalError(continueAfterFatalError);

		if (dynamicValidationEnabled != null)
			source.setDynamicValidationEnabled(dynamicValidationEnabled);

		if (externalGeneralEntities != null)
			source.setExternalGeneralEntities(externalGeneralEntities);

		if (externalParameterEntities != null)
			source.setExternalParameterEntities(externalParameterEntities);

		if (loadDTDGrammer != null)
			source.setLoadDTDGrammer(loadDTDGrammer);

		if (loadExternalDTD != null)
			source.setLoadExternalDTD(loadExternalDTD);

		if (schemaFullCheckingEnabled != null)
			source.setSchemaFullCheckingEnabled(schemaFullCheckingEnabled);

		if (schemaValidationEnabled != null)
			source.setSchemaValidationEnabled(schemaValidationEnabled);

		if (validationEnabled != null)
			source.setValidationEnabled(validationEnabled);

		if (warnOnDuplicateAttDef != null)
			source.setWarnOnDuplicateAttDef(warnOnDuplicateAttDef);

		if (warnOnUndeclaredElemDef != null)
			source.setWarnOnUndeclaredElemDef(warnOnUndeclaredElemDef);

		if (encoding != null)
			source.setEncoding(encoding);

		doConfigure(source);

	}

	/**
	 * Override this method to provide custom config.
	 * 
	 * @param source
	 */
	protected void doConfigure(XMLSource source) {

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

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Boolean getValidationEnabled() {
		return validationEnabled;
	}

	public void setValidationEnabled(Boolean validationEnabled) {
		this.validationEnabled = validationEnabled;
	}

	public Boolean getSchemaValidationEnabled() {
		return schemaValidationEnabled;
	}

	public void setSchemaValidationEnabled(Boolean schemaValidationEnabled) {
		this.schemaValidationEnabled = schemaValidationEnabled;
	}

	public Boolean getSchemaFullCheckingEnabled() {
		return schemaFullCheckingEnabled;
	}

	public void setSchemaFullCheckingEnabled(Boolean schemaFullCheckingEnabled) {
		this.schemaFullCheckingEnabled = schemaFullCheckingEnabled;
	}

	public Boolean getDynamicValidationEnabled() {
		return dynamicValidationEnabled;
	}

	public void setDynamicValidationEnabled(Boolean dynamicValidationEnabled) {
		this.dynamicValidationEnabled = dynamicValidationEnabled;
	}

	public Boolean getExternalGeneralEntities() {
		return externalGeneralEntities;
	}

	public void setExternalGeneralEntities(Boolean externalGeneralEntities) {
		this.externalGeneralEntities = externalGeneralEntities;
	}

	public Boolean getExternalParameterEntities() {
		return externalParameterEntities;
	}

	public void setExternalParameterEntities(Boolean externalParameterEntities) {
		this.externalParameterEntities = externalParameterEntities;
	}

	public Boolean getWarnOnDuplicateAttDef() {
		return warnOnDuplicateAttDef;
	}

	public void setWarnOnDuplicateAttDef(Boolean warnOnDuplicateAttDef) {
		this.warnOnDuplicateAttDef = warnOnDuplicateAttDef;
	}

	public Boolean getWarnOnUndeclaredElemDef() {
		return warnOnUndeclaredElemDef;
	}

	public void setWarnOnUndeclaredElemDef(Boolean warnOnUndeclaredElemDef) {
		this.warnOnUndeclaredElemDef = warnOnUndeclaredElemDef;
	}

	public Boolean getContinueAfterFatalError() {
		return continueAfterFatalError;
	}

	public void setContinueAfterFatalError(Boolean continueAfterFatalError) {
		this.continueAfterFatalError = continueAfterFatalError;
	}

	public Boolean getLoadDTDGrammer() {
		return loadDTDGrammer;
	}

	public void setLoadDTDGrammer(Boolean loadDTDGrammer) {
		this.loadDTDGrammer = loadDTDGrammer;
	}

	public Boolean getLoadExternalDTD() {
		return loadExternalDTD;
	}

	public void setLoadExternalDTD(Boolean loadExternalDTD) {
		this.loadExternalDTD = loadExternalDTD;
	}

}
