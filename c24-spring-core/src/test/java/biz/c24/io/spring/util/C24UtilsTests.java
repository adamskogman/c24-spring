package biz.c24.io.spring.util;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;

import nonamespace.AccountLocal;
import nonamespace.CustomerElement;
import nonamespace.CustomerLocal;
import nonamespace.SampleModelDocumentRootElement;

import org.junit.Test;

import biz.c24.io.api.data.ComplexDataObject;

/**
 *
 * @author Oliver Gierke
 */
public class C24UtilsTests {

	@Test
	public void findsAllTypesSupportedBySimpleElement() {

		Set<Class<? extends ComplexDataObject>> result = C24Utils.getSupportedTypes(CustomerElement.getInstance());
		assertThat(result.size(), is(1));
		assertThat(result.contains(CustomerLocal.class), is(true));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void findsAllTypesSupportedByDocumentRoot() {

		Set<Class<? extends ComplexDataObject>> result = C24Utils.getSupportedTypes(SampleModelDocumentRootElement.getInstance());
		assertThat(result.size(), is(2));
		assertThat(result.containsAll(Arrays.asList(CustomerLocal.class, AccountLocal.class)), is(true));
	}
}
