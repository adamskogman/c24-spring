/**
 * 
 */
package biz.c24.io.spring.integration.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.handler.AbstractMessageProcessor;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.ValidationEvent;
import biz.c24.io.api.data.ValidationEventCollector;
import biz.c24.io.api.data.ValidationManager;

import static biz.c24.io.spring.integration.C24Headers.*;

/**
 * @author askogman
 * 
 */
public class C24ValidatingMessageProcessor extends
		AbstractMessageProcessor<Map<String, ?>> {

	boolean addFailEvents = true;
	boolean addPassEvents = false;
	boolean addStatistics = false;

	@Override
	public Map<String, ?> processMessage(Message<?> message) {

		Map<String, Object> result = new HashMap<String, Object>();

		Object payload = message.getPayload();

		ComplexDataObject cdo;
		try {
			cdo = (ComplexDataObject) payload;
		} catch (ClassCastException e) {
			throw new MessagingException("Cannot validate payload of type ["
					+ payload.getClass().getName()
					+ "]. Only ComplexDataObject is supported.", e);
		}

		ValidationManager manager = new ValidationManager();
		ValidationEventCollector vec = new ValidationEventCollector();
		manager.addValidationListener(vec);

		if (manager.validateByEvents(cdo)) {
			result.put(VALID, Boolean.TRUE);
		} else {
			result.put(VALID, Boolean.FALSE);
		}

		if (isAddFailEvents())
			result.put(
					FAIL_EVENTS,
					new ArrayList<ValidationEvent>(Arrays.asList(vec
							.getFailEvents())));

		if (isAddPassEvents())
			result.put(
					PASS_EVENTS,
					new ArrayList<ValidationEvent>(Arrays.asList(vec
							.getPassEvents())));

		if (isAddStatistics())
			result.put(STATISTICS, manager.getStatistics());

		return result;

	}

	public boolean isAddFailEvents() {
		return addFailEvents;
	}

	public void setAddFailEvents(boolean addFailEvents) {
		this.addFailEvents = addFailEvents;
	}

	public boolean isAddPassEvents() {
		return addPassEvents;
	}

	public void setAddPassEvents(boolean addPassEvents) {
		this.addPassEvents = addPassEvents;
	}

	public boolean isAddStatistics() {
		return addStatistics;
	}

	public void setAddStatistics(boolean addStatistics) {
		this.addStatistics = addStatistics;
	}

}
