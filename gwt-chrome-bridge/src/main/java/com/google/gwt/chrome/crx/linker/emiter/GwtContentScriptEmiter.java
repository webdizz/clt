/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import com.google.gwt.chrome.crx.client.GwtContentScript;
import com.google.gwt.chrome.crx.linker.ContentScriptGeneratedResource;
import com.google.gwt.chrome.crx.linker.GwtContentScriptGenerator;
import com.google.gwt.chrome.crx.linker.artifact.GwtContentScriptArtifact;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.cfg.ModuleDef;
import com.google.gwt.dev.cfg.ModuleDefLoader;

/**
 * {@link GwtContentScriptEmiter} is responsible for creation of content script
 * from Java source code using GWT compiler and including it into manifest.json
 * file.
 * 
 * @author Izzet_Mustafayev
 * 
 */
public class GwtContentScriptEmiter extends AbstractEmiter {

	private static final String WEB_INF = "WEB-INF/";

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
		GwtContentScript.ManifestInfo spec = userType.getAnnotation(GwtContentScript.ManifestInfo.class);
		Validator<GwtContentScript.ManifestInfo> validator;
		validator = new Validator<GwtContentScript.ManifestInfo>(logger, GwtContentScript.class.getName(), typeName);

		validator.ensureAnnotatedWithManifest(spec);

		String moduleName = spec.module();
		ModuleDef moduleDef = ModuleDefLoader.loadFromClassPath(logger, moduleName);

		if (null == moduleDef) {
			logger.log(TreeLogger.ERROR, "Module was not loaded: " + moduleName);
			throw new UnableToCompleteException();
		}
		GwtContentScriptArtifact artifact;
		String moduleJavaScriptFile = moduleDef.getName() + ".js";
		artifact = new GwtContentScriptArtifact(moduleJavaScriptFile, spec.matches(), spec.runAt(), spec.allFrames());
		context.commitArtifact(logger, artifact);
		emitResource(logger, context, moduleDef, moduleJavaScriptFile);

		return null;
	}

	protected void emitResource(TreeLogger logger, GeneratorContext context, ModuleDef moduleDef,
			String moduleJavaScriptFile) throws UnableToCompleteException {
		URL path = Thread.currentThread().getContextClassLoader().getResource("./");
		String pathToModule = null;
		if (null != path && path.getPath().contains(WEB_INF)) {
			String modulePath = path.getPath();
			pathToModule = modulePath.substring(0, modulePath.indexOf(WEB_INF));
		}
		String pathToJsFile = pathToModule + moduleDef.getName() + "/" + moduleJavaScriptFile;
		try {
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(pathToJsFile));
			StringBuffer contentBuffer = new StringBuffer();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					contentBuffer.append(line);
				}
			} finally {
				reader.close();
			}
			if (contentBuffer.length() > 16) {
				ContentScriptGeneratedResource javaScriptFile = new ContentScriptGeneratedResource(
						GwtContentScriptGenerator.class, moduleJavaScriptFile, contentBuffer.toString().getBytes());
				context.commitArtifact(logger, javaScriptFile);
			}
		} catch (FileNotFoundException e) {
			logger.log(TreeLogger.ERROR, "Unable to find generated javascript file: " + pathToJsFile);
			throw new UnableToCompleteException();
		} catch (IOException e) {
			logger.log(TreeLogger.ERROR, "Unable to read generated javascript file: " + pathToJsFile);
			throw new UnableToCompleteException();
		}
	}
}
