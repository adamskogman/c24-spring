/**
 * 
 */
package biz.c24.io.spring.sink;

import biz.c24.io.api.presentation.Sink;
import biz.c24.io.api.presentation.TagValuePairSink;

/**
 * @author askogman
 * 
 */
public class TagValuePairSinkFactory extends AbstractSinkFactory {

	private Boolean indenting = null;

	@Override
	protected Sink createSink() {
		TagValuePairSink tagValuePairSink = new TagValuePairSink();

		if (indenting != null)
			tagValuePairSink.setIndenting(indenting);

		return tagValuePairSink;
	}

	public Boolean getIndenting() {
		return indenting;
	}

	public void setIndenting(Boolean indenting) {
		this.indenting = indenting;
	}
}
