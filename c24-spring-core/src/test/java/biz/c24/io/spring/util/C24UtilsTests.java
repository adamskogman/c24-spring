/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *			http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
