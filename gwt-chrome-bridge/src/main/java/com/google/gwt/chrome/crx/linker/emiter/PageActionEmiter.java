/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.chrome.crx.client.PageAction;
import com.google.gwt.chrome.crx.linker.artifact.PageActionArtifact;
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
public class PageActionEmiter extends BrowserActionEmiter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.chrome.crx.linker.emiter.Emiter#emit(com.google.gwt.core
	 * .ext.TreeLogger, com.google.gwt.core.ext.GeneratorContext,
	 * com.google.gwt.core.ext.typeinfo.JClassType, java.lang.String)
	 */
	@Override
	public String emit(final TreeLogger logger, final GeneratorContext context, final JClassType userType,
			final String typeName) throws UnableToCompleteException {
		PageAction.ManifestInfo spec = userType.getAnnotation(PageAction.ManifestInfo.class);

		Validator<PageAction.ManifestInfo> validator;
		validator = new Validator<PageAction.ManifestInfo>(logger, PageAction.class.getName(), typeName);
		validator.ensureAnnotatedWithManifest(spec);

		JMethod[] methods = userType.getMethods();
		List<String> iconFiles = new ArrayList<String>();
		List<String> iconMethods = new ArrayList<String>();

		processIconMethods(methods, iconFiles, iconMethods);
		validator.ensureActionHasIcon(iconFiles);

		if ("".equals(spec.popup())) {
			PageActionArtifact artifact;
			artifact = new PageActionArtifact(spec.pageActionId(), spec.name(), iconFiles.toArray(new String[0]));
			context.commitArtifact(logger, artifact);
		} else {
			PageActionArtifact artifact;
			String[] files = iconFiles.toArray(new String[0]);
			artifact = new PageActionArtifact(spec.pageActionId(), spec.name(), files, spec.popup());
			context.commitArtifact(logger, artifact);
		}

		return emitPageActionCode(logger, context, userType, spec.pageActionId(), spec.name(), iconMethods, iconFiles,
				spec.popup());
	}

	private String emitPageActionCode(TreeLogger logger, GeneratorContext context, JClassType userType,
			String pageActionId, String name, List<String> icons, List<String> iconPaths, String popup) {
		final String subclassName = createSubclassName(userType);
		final String packageName = userType.getPackage().getName();
		final ClassSourceFileComposerFactory f = new ClassSourceFileComposerFactory(packageName, subclassName);
		f.setSuperclass(userType.getQualifiedSourceName());
		final PrintWriter pw = context.tryCreate(logger, packageName, subclassName);
		if (pw != null) {
			final SourceWriter sw = f.createSourceWriter(context, pw);

			// Impls for the getters for id and name.
			emitIdGetter(sw, pageActionId);
			emitNameGetter(sw, name);
			if (null != popup) {
				sw.println("public String getPopup() {");
				sw.println("  return \"" + popup + "\";");
				sw.println("}");
			}

			emitIcons(sw, icons, iconPaths);

			sw.commit(logger);
		}
		return f.getCreatedClassName();
	}

}
