package biz.c24.io.spring.source;

import java.io.InputStream;
import java.io.Reader;

import biz.c24.io.api.presentation.Source;

/**
 * Creates and configures a C24 IO Source.
 * 
 * @author askogman
 */
public interface SourceFactory {

	public abstract Source getSource(Reader reader);

	public abstract Source getSource(InputStream stream);

}
