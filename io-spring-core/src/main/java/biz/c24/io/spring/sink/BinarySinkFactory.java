/**
 * 
 */
package biz.c24.io.spring.sink;

import biz.c24.io.api.presentation.BinarySink;
import biz.c24.io.api.presentation.Sink;
import biz.c24.io.api.presentation.TextualSink;

/**
 * @author askogman
 * 
 */
public class BinarySinkFactory extends AbstractSinkFactory {

	@Override
	protected Sink createSink() {
		return new BinarySink();
	}

}
