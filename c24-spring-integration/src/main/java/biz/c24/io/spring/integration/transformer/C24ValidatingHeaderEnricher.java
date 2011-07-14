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
package biz.c24.io.spring.integration.transformer;

import org.springframework.integration.transformer.HeaderEnricher;

import biz.c24.io.spring.integration.validation.C24ValidatingMessageProcessor;

/**
 * @author askogman
 * 
 */
public class C24ValidatingHeaderEnricher extends HeaderEnricher {

	protected C24ValidatingMessageProcessor processor = new C24ValidatingMessageProcessor();

	public C24ValidatingHeaderEnricher() {
		super();
		this.setMessageProcessor(processor);
	}

	public void setAddFailEvents(boolean addFailEvents) {
		processor.setAddFailEvents(addFailEvents);
	}

	public void setAddPassEvents(boolean addPassEvents) {
		processor.setAddPassEvents(addPassEvents);
	}

	public void setAddStatistics(boolean addStatistics) {
		processor.setAddStatistics(addStatistics);
	}

}
