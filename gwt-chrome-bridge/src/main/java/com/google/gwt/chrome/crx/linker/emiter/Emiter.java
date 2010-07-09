/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;

/**
 * @author webdizz
 * @param <E>
 *            the type of emision to be emited
 * 
 */
public interface Emiter {

	String emit(TreeLogger logger, GeneratorContext context, JClassType userType, String typeName)
			throws UnableToCompleteException;

}
