/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import com.google.gwt.chrome.crx.linker.emision.Emision;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;

/**
 * @author webdizz
 * @param <E> the type of emision to be emited
 * 
 */
public interface Emiter<E extends Emision> {

	String emit(TreeLogger logger, GeneratorContext context, E emision);

}
