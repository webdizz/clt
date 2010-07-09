/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.chrome.crx.client.BrowserAction;
import com.google.gwt.chrome.crx.client.Icon;
import com.google.gwt.chrome.crx.linker.artifact.BrowserActionArtifact;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author webdizz
 * 
 */
public class BrowserActionEmiter extends AbstractEmiter {

	@Override
	public String emit(TreeLogger logger, GeneratorContext context, JClassType userType, String typeName)
			throws UnableToCompleteException {
		BrowserAction.ManifestInfo spec = userType.getAnnotation(BrowserAction.ManifestInfo.class);
		Validator<BrowserAction.ManifestInfo> validator;
		validator = new Validator<BrowserAction.ManifestInfo>(logger, BrowserAction.class.getName(), typeName);
		validator.ensureAnnotatedWithManifest(spec);
		JMethod[] methods = userType.getMethods();
		List<String> iconFiles = new ArrayList<String>();
		List<String> iconMethods = new ArrayList<String>();

		processIconMethods(methods, iconFiles, iconMethods);
		validator.ensureActionHasIcon(iconFiles);
		BrowserActionArtifact artifact = new BrowserActionArtifact(spec.name(), iconFiles.toArray(new String[0]),
				spec.defaultIcon());
		context.commitArtifact(logger, artifact);
		return emitCode(logger, context, userType, spec.name(), iconMethods, iconFiles);
	}

	protected void processIconMethods(JMethod[] methods, List<String> iconFiles, List<String> iconMethods) {
		// TODO(jaimeyap): Do something smarter about verifying that the files
		// actually exist on disk, and then coming up with something sane for
		// the path information. May even consider strong names. See what
		// ClientBundle/ImageResource does.
		for (JMethod method : methods) {
			if (method.getReturnType().getQualifiedSourceName().equals(Emiter.ICON_USER_TYPE)) {
				String iconFileName;
				Icon.Source iconSource = method.getAnnotation(Icon.Source.class);
				if (iconSource == null) {
					iconFileName = method.getName() + ".png";
				} else {
					iconFileName = iconSource.value();
				}
				iconFiles.add(iconFileName);
				iconMethods.add(method.getName());
			}
		}
	}

	private String emitCode(TreeLogger logger, GeneratorContext context, JClassType userType, String name,
			List<String> icons, List<String> iconPaths) {
		final String subclassName = userType.getSimpleSourceName().replace('.', '_') + "_generated";
		final String packageName = userType.getPackage().getName();
		final ClassSourceFileComposerFactory sourceFileComposerFactory = new ClassSourceFileComposerFactory(
				packageName, subclassName);
		sourceFileComposerFactory.setSuperclass(userType.getQualifiedSourceName());
		final PrintWriter pw = context.tryCreate(logger, packageName, subclassName);
		if (pw != null) {
			SourceWriter sw = sourceFileComposerFactory.createSourceWriter(context, pw);
			
			emitNameGetter(sw, name);
			emitIcons(sw, icons, iconPaths);
			sw.commit(logger);
		}
		return sourceFileComposerFactory.getCreatedClassName();
	}

}
