/**
 * 
 */
package com.google.gwt.chrome.crx.linker;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.NotFoundException;

/**
 * @author webdizz
 * 
 */
public class GwtContentScriptGenerator extends Generator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.core.ext.Generator#generate(com.google.gwt.core.ext.TreeLogger
	 * , com.google.gwt.core.ext.GeneratorContext, java.lang.String)
	 */
	@Override
	public String generate(final TreeLogger logger, final GeneratorContext context, final String typeName)
			throws UnableToCompleteException {
		try {
			context.getTypeOracle().getType(typeName);
			// TODO: implement class creation if needed
		} catch (NotFoundException e) {
			logger.log(TreeLogger.ERROR, "Unsupported Type: " + typeName);
			throw new UnableToCompleteException();
		}
		return null;
	}

}
