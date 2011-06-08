/**
 * 
 */
package biz.c24.io.spring.sink;

import java.io.OutputStream;
import java.io.Writer;

import biz.c24.io.api.presentation.Sink;

/**
 * @author askogman
 * 
 */
public interface SinkFactory {

	Sink createSink(Writer writer);

	Sink createSink(OutputStream stream);

}
