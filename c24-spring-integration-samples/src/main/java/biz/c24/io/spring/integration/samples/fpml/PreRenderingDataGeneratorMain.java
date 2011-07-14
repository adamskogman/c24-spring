/**
 * 
 */
package biz.c24.io.spring.integration.samples.fpml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author askogman
 * 
 */
public class PreRenderingDataGeneratorMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:/biz/c24/io/spring/integration/samples/fpml/generator/prerender-si-context.xml");

		context.registerShutdownHook();

		
		
	}

}
