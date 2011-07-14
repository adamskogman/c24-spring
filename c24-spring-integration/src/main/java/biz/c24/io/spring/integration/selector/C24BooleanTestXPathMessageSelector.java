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
package biz.c24.io.spring.integration.selector;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.IOXPathException;
import biz.c24.io.api.data.XPathStatement;

/**
 * @author askogman
 * 
 */
public class C24BooleanTestXPathMessageSelector extends
AbstractXPathMessageSelector {

	/**
	 * @param statement
	 */
	public C24BooleanTestXPathMessageSelector(String statement) {
		super(statement);
	}

	/**
	 * @param statement
	 */
	public C24BooleanTestXPathMessageSelector(XPathStatement statement) {
		super(statement);
	}

	@Override
	protected boolean doAcceptPayload(ComplexDataObject payload) throws IOXPathException {
		return createXPath().getBoolean(payload);
	}

}
