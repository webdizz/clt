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

	static final String ICON_USER_TYPE = "com.google.gwt.chrome.crx.client.Icon";
	
	static final String BROWSERACTION_USER_TYPE = "com.google.gwt.chrome.crx.client.BrowserAction";
	
	static final String PAGEACTION_USER_TYPE = "com.google.gwt.chrome.crx.client.PageAction";
	
	static final String CONTENTSCRIPT_USER_TYPE = "com.google.gwt.chrome.crx.client.ContentScript";

	/**
	 * Abstract method to be implemented by {@link Emiter}s to support code <br/>
	 * emision of Chrome extension artifacts e.g. BrowserAction, PageAction etc.
	 * 
	 * @param logger
	 *            the {@link TreeLogger}
	 * @param context
	 *            the {@link GeneratorContext}
	 * @param userType
	 *            the {@link JClassType}
	 * @param typeName
	 *            the emision artifact class name
	 * @return emited class name
	 * @throws UnableToCompleteException
	 *             if emison cannot be completed
	 */
	String emit(TreeLogger logger, GeneratorContext context, JClassType userType, String typeName)
			throws UnableToCompleteException;

}
