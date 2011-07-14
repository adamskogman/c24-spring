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
