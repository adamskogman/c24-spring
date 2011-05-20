package biz.c24.io.spring.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import biz.c24.io.spring.http.C24HttpMessageConverter;

/**
 * 
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "converter.xml")
public class C24HttpMessageConverterBeanDefinitionParserIntegrationTests {

	@Autowired
	C24HttpMessageConverter converter;

	@Test
	public void testname() {
		assertThat(converter, is(notNullValue()));
	}
}
