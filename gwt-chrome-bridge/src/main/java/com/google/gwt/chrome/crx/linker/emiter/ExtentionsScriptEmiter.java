package com.google.gwt.chrome.crx.linker.emiter;

import com.google.gwt.chrome.crx.client.BrowserAction;
import com.google.gwt.chrome.crx.client.ExtensionScript;
import com.google.gwt.chrome.crx.linker.artifact.ExtensionScriptArtifact;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;


/**
 * @author zinur
 * 
 */
public class ExtentionsScriptEmiter extends AbstractEmiter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.chrome.crx.linker.emiter.Emiter#emit(com.google.gwt.core
	 * .ext.TreeLogger, com.google.gwt.core.ext.GeneratorContext,
	 * com.google.gwt.core.ext.typeinfo.JClassType, java.lang.String)
	 */
	@Override
	public String emit(TreeLogger logger, GeneratorContext context, JClassType userType, String typeName)
			throws UnableToCompleteException {
		processExtensionScript(logger, context, userType, typeName);
		return typeName;
	}

	/**
	 * @param logger
	 * @param context
	 * @param userType
	 * @param typeName
	 * @throws UnableToCompleteException
	 */
	private void processExtensionScript(TreeLogger logger, GeneratorContext context, JClassType userType,
			String typeName) throws UnableToCompleteException {

		ExtensionScript.ManifestInfo spec = userType.getAnnotation(ExtensionScript.ManifestInfo.class);
		Validator<ExtensionScript.ManifestInfo> validator;
		validator = new Validator<ExtensionScript.ManifestInfo>(logger, BrowserAction.class.getName(), typeName);
		validator.ensureAnnotatedWithManifest(spec);
		context.commitArtifact(logger, new ExtensionScriptArtifact(spec.path(), spec.script()));
	}
}
