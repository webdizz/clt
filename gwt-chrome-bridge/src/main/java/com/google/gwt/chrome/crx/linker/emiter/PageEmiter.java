package com.google.gwt.chrome.crx.linker.emiter;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;

/**
 * @author zinur
 *
 */
public class PageEmiter  extends AbstractEmiter{

	@Override
	public String emit(TreeLogger logger, GeneratorContext context, JClassType userType, String typeName)
			throws UnableToCompleteException {
		String name = userType.getSimpleSourceName();
		String path = name + ".html";
		emitComponentPage(logger, context, name, path);
		// No need to commit any artifact for the linker.
		return emitComponentPageCode(logger, context, userType);
	}

}
