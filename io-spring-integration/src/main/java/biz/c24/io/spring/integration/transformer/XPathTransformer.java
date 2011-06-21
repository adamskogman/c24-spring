/**
 * 
 */
package biz.c24.io.spring.integration.transformer;

import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.util.Assert;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.IOXPath;
import biz.c24.io.api.data.IOXPathException;
import biz.c24.io.api.data.IOXPathFactory;
import biz.c24.io.api.data.XPathStatement;
import biz.c24.io.spring.integration.xpath.XPathEvaluationType;

/**
 * @author askogman
 * 
 */
public class XPathTransformer extends
		AbstractPayloadTransformer<ComplexDataObject, Object> {

	volatile XPathStatement statement;
	volatile XPathEvaluationType evaluationType = XPathEvaluationType.OBJECT_RESULT;

	public XPathTransformer(String statement) {
		Assert.notNull(statement, "The XPath statement must not be null.");

		this.statement = new XPathStatement(statement);
	}

	public XPathTransformer(XPathStatement statement) {
		Assert.notNull(statement, "The XPath statement must not be null.");

		this.statement = statement;
	}

	@Override
	protected Object transformPayload(ComplexDataObject payload)
			throws Exception {

		IOXPath xpath = createXPath();

		Object transformedPayload = evaluationType
				.evaluateXPath(xpath, payload);
		return transformedPayload;
	}

	protected IOXPath createXPath() throws IOXPathException {
		return IOXPathFactory.getInstance(statement);
	}

	public XPathStatement getStatement() {
		return statement;
	}

	public void setStatement(XPathStatement statement) {
		Assert.notNull(statement, "The XPath statement must not be null.");

		this.statement = statement;
	}

	public XPathEvaluationType getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(XPathEvaluationType evaluationType) {
		Assert.notNull(evaluationType,
				"The XPath evaluation type must not be null.");
		this.evaluationType = evaluationType;
	}

}
