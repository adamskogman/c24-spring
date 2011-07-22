package biz.c24.io.spring.core;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.MediaType;

/**
 * Unit tests for {@link DataFormat}.
 *
 * @author Oliver Gierke
 */
public class DateFormatUnitTests {

	DataFormat first = new DataFormat(DataFormat.Type.XML, MediaType.APPLICATION_XHTML_XML);
	DataFormat second = new DataFormat(DataFormat.Type.XML, MediaType.APPLICATION_XHTML_XML);
	DataFormat third = new DataFormat(DataFormat.Type.TEXT, MediaType.APPLICATION_XHTML_XML);
	DataFormat fourth = new DataFormat(DataFormat.Type.XML, MediaType.APPLICATION_ATOM_XML);

	@Test
	public void equalsSameInstance() {
		assertThat(first.equals(first), is(true));
	}

	@Test
	public void equalsSameValues() {
		assertThat(first.equals(second), is(true));
		assertThat(second.equals(first), is(true));
	}

	@Test
	public void doesNotEqualDifferentType() {
		assertThat(first.equals(third), is(false));
		assertThat(third.equals(first), is(false));
	}

	@Test
	public void doesNotEqualDifferentMediaType() {
		assertThat(first.equals(fourth), is(false));
		assertThat(fourth.equals(first), is(false));
	}
}
