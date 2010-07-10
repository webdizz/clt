/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import com.google.gwt.chrome.crx.client.ContentScript;
import com.google.gwt.chrome.crx.linker.artifact.ContentScriptArtifact;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;

/**
 * {@link ContentScriptEmiter} is responsible for creation of content script and
 * including it into manifest.json file.
 * 
 * @author webdizz
 * 
 */
public class ContentScriptEmiter extends AbstractEmiter {

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
		ContentScript.ManifestInfo spec = userType.getAnnotation(ContentScript.ManifestInfo.class);
		Validator<ContentScript.ManifestInfo> validator;
		validator = new Validator<ContentScript.ManifestInfo>(logger, ContentScript.class.getName(), typeName);

		validator.ensureAnnotatedWithManifest(spec);

		context.commitArtifact(logger, new ContentScriptArtifact(spec.path(), spec.whiteList(), spec.runAt()));
		// should return null as we do not need to something other
		return null;
	}

}
