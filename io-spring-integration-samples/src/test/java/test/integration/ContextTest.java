package test.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import biz.c24.io.spring.util.C24Model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/si-context.xml")
public class ContextTest {

	@Autowired
	ApplicationContext context;

	@Test
	public void helloWorld() throws Exception {

		context.getBean(C24Model.class);

	}

}
