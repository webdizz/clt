/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import com.google.gwt.chrome.crx.client.BrowserAction;

import com.google.gwt.chrome.crx.client.Plugin;
import com.google.gwt.chrome.crx.linker.artifact.PluginArtifact;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;

/**
 * @author zinur
 *
 */
public class PluginEmiter extends AbstractEmiter {

	@Override
	public String emit(TreeLogger logger, GeneratorContext context, JClassType userType, String typeName)
			throws UnableToCompleteException {
		Plugin.ManifestInfo spec = userType.getAnnotation(Plugin.ManifestInfo.class);
		Validator<Plugin.ManifestInfo> validator;
		validator = new Validator<Plugin.ManifestInfo>(logger, BrowserAction.class.getName(), typeName);
		validator.ensureAnnotatedWithManifest(spec);
		context.commitArtifact(logger, new PluginArtifact(spec.path(), spec.isPublic()));
		return null;
	}

}
