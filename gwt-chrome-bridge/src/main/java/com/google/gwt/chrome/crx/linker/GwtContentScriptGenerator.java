/**
 * 
 */
package com.google.gwt.chrome.crx.linker;

import com.google.gwt.chrome.crx.client.GwtContentScript;
import com.google.gwt.chrome.crx.client.GwtContentScript.ManifestInfo;
import com.google.gwt.chrome.crx.linker.artifact.GwtContentScriptArtifact;
import com.google.gwt.chrome.crx.linker.emiter.Validator;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
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
		Validator<ManifestInfo> validator;
		validator = new Validator<ManifestInfo>(logger, GwtContentScript.class.getSimpleName(), typeName);
		try {
			JClassType userType;
			userType = context.getTypeOracle().getType(typeName);
			final ManifestInfo spec = userType.getAnnotation(ManifestInfo.class);
			validator.ensureAnnotatedWithManifest(spec);

			// create artifact
			String path = userType.getSimpleSourceName();
			GwtContentScriptArtifact artifact;
			artifact = new GwtContentScriptArtifact(path, spec.matches(), spec.runAt(), spec.allFrames());
			context.commitArtifact(logger, artifact);

			// TODO: implement class if needed
			// generate extension type
			//final String subclassName = userType.getSimpleSourceName().replace('.', '_') + "_generated";
			//final String packageName = userType.getPackage().getName();
			//final ClassSourceFileComposerFactory composerFactory;
			//composerFactory = new ClassSourceFileComposerFactory(packageName, subclassName);
			//composerFactory.setSuperclass(userType.getQualifiedSourceName());
			//context.tryCreate(logger, packageName, subclassName);
			//return composerFactory.getCreatedClassName();
		} catch (NotFoundException e) {
			logger.log(TreeLogger.ERROR, "Unsupported Type: " + typeName);
			throw new UnableToCompleteException();
		}
		return null;
	}

}
