package biz.c24.io.spring.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.DocumentRoot;
import biz.c24.io.api.data.Element;

/**
 * Utility classes to work with a C24 model.
 *
 * @author Oliver Gierke
 */
public abstract class C24Utils {

	private C24Utils() {

	}

	/**
	 * Returns all the classes supported by the given model {@link Element}.
	 * 
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<Class<? extends ComplexDataObject>> getSupportedTypes(Element element) {

		Class<? extends ComplexDataObject> type = element.getType().getValidObjectClass();
		HashSet<Class<? extends ComplexDataObject>> result = new HashSet<Class<? extends ComplexDataObject>>();

		if (!DocumentRoot.class.isAssignableFrom(type)) {
			result.add(type);
			return result;
		}

		DocumentRoot root = (DocumentRoot) BeanUtils.instantiateClass(type);

		for (int i=0; i < root.getElementDeclCount(); i++) {
			Element declaredElement = root.getElementDecl(i);
			result.add(declaredElement.getType().getValidObjectClass());
		}

		return result;
	}

	/**
	 * Will return the given source {@link ComplexDataObject} if it's an arbitrary one or extract the root object in case
	 * the given argument is a {@link DocumentRoot}.
	 * 
	 * @param source
	 * @return
	 */
	public static ComplexDataObject potentiallyUnwrapDocumentRoot(ComplexDataObject source) {
		return source instanceof DocumentRoot ? (ComplexDataObject) ((DocumentRoot) source).getRootElement() : source;
	}
}
