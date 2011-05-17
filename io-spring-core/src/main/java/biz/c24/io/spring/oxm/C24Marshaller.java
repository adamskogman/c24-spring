package biz.c24.io.spring.oxm;

import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

/**
 * 
 * @author Oliver Gierke
 */
public class C24Marshaller implements Marshaller, Unmarshaller {

	/* (non-Javadoc)
	 * @see org.springframework.oxm.Marshaller#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> clazz) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.Unmarshaller#unmarshal(javax.xml.transform.Source)
	 */
	public Object unmarshal(Source source) throws IOException, XmlMappingException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.oxm.Marshaller#marshal(java.lang.Object, javax.xml.transform.Result)
	 */
	public void marshal(Object graph, Result result) throws IOException, XmlMappingException {
		// TODO Auto-generated method stub

	}
}
