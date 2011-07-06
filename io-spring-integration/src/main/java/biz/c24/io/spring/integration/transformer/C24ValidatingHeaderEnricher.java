package biz.c24.io.spring.integration.transformer;

import org.springframework.integration.transformer.HeaderEnricher;

import biz.c24.io.spring.integration.validation.C24ValidatingMessageProcessor;

/**
 * @author askogman
 * 
 */
public class C24ValidatingHeaderEnricher extends HeaderEnricher {


	public C24ValidatingHeaderEnricher() {
		super();
		this.setMessageProcessor(new C24ValidatingMessageProcessor());
	}

}
