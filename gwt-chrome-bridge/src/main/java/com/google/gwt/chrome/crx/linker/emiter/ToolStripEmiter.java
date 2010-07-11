package com.google.gwt.chrome.crx.linker.emiter;

import com.google.gwt.chrome.crx.linker.artifact.ToolStripArtifact;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;


public class ToolStripEmiter extends AbstractEmiter {

	/* (non-Javadoc)
	 * @see com.google.gwt.chrome.crx.linker.emiter.Emiter#emit(com.google.gwt.core.ext.TreeLogger, com.google.gwt.core.ext.GeneratorContext, com.google.gwt.core.ext.typeinfo.JClassType, java.lang.String)
	 */
	@Override
	public String emit(TreeLogger logger, GeneratorContext context, JClassType userType, String typeName)
			throws UnableToCompleteException {
		String name = userType.getSimpleSourceName();
		String path = name + ".html";
		emitComponentPage(logger, context, name, path);
		context.commitArtifact(logger, new ToolStripArtifact(path));
		return emitComponentPageCode(logger, context, userType);

	}




}
