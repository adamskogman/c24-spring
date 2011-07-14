/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package biz.c24.io.spring.integration;

/**
 * Pre-defined names and prefixes to be used for setting and/or retrieving C24
 * message headers.
 * 
 * @author Adam Skogman
 */
public abstract class C24Headers {

	/**
	 * Prefix used for C24 related headers in order to distinguish from
	 * user-defined headers and other internal headers
	 */
	public static final String PREFIX = "c24_";

	// Header Name Constants

	public static final String STATISTICS = PREFIX + "statistics";
	public static final String PASS_EVENTS = PREFIX + "passEvents";
	public static final String FAIL_EVENTS = PREFIX + "failEvents";
	public static final String VALID = PREFIX + "valid";

}
