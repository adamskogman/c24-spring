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
package biz.c24.io.spring.integration.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.springframework.util.Assert;

import biz.c24.io.api.data.ValidationEvent;

/**
 * @author Adam Skogman
 */
@SuppressWarnings("serial")
public class C24AggregatedMessageValidationException extends RuntimeException {

	private final List<ValidationEvent> validationEvents;

	public C24AggregatedMessageValidationException(
			ValidationEvent[] validationEvents) {
		Assert.notEmpty(validationEvents);
		this.validationEvents = new ArrayList<ValidationEvent>(
				Arrays.asList(validationEvents));
	}

	public ListIterator<ValidationEvent> getFailEvents() {
		return validationEvents.listIterator();
	}

}
