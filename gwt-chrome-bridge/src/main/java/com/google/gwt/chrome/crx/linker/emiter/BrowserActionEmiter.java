/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.chrome.crx.client.BrowserAction;
import com.google.gwt.chrome.crx.client.Icon;
import com.google.gwt.chrome.crx.linker.UserType;
import com.google.gwt.chrome.crx.linker.artifact.BrowserActionArtifact;
import com.google.gwt.core.ext.Generator;
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
public class BrowserActionEmiter implements Emiter {

	@Override
	public String emit(TreeLogger logger, GeneratorContext context, JClassType userType, String typeName)
			throws UnableToCompleteException {
		BrowserAction.ManifestInfo spec = userType.getAnnotation(BrowserAction.ManifestInfo.class);
		ensureSpecPresent(logger, typeName, spec);
		JMethod[] methods = userType.getMethods();
		List<String> iconFiles = new ArrayList<String>();
		List<String> iconMethods = new ArrayList<String>();

		// TODO(jaimeyap): Do something smarter about verifying that the files
		// actually exist on disk, and then coming up with something sane for
		// the path information. May even consider strong names. See what
		// ClientBundle/ImageResource does.
		for (JMethod method : methods) {
			if (method.getReturnType().getQualifiedSourceName().equals(UserType.ICON_USER_TYPE.type())) {
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
		ensureActionHasIcon(logger, typeName, iconFiles);
		BrowserActionArtifact artifact = new BrowserActionArtifact(spec.name(), iconFiles.toArray(new String[0]),
				spec.defaultIcon());
		context.commitArtifact(logger, artifact);
		return emitCode(logger, context, userType, spec.name(), iconMethods, iconFiles);
	}

	private void ensureActionHasIcon(TreeLogger logger, String typeName, List<String> iconFiles)
			throws UnableToCompleteException {
		if (iconFiles.isEmpty()) {
			logger.log(TreeLogger.ERROR, "BrowserActions must have at least one Icon (" + typeName + ")");
			throw new UnableToCompleteException();
		}
	}

	private void ensureSpecPresent(TreeLogger logger, String typeName, BrowserAction.ManifestInfo spec)
			throws UnableToCompleteException {
		if (spec == null) {
			logger.log(TreeLogger.ERROR, "BrowserAction (" + typeName + ") must be annotated with a Specificaiton.");
			throw new UnableToCompleteException();
		}
	}

	private static String emitCode(TreeLogger logger, GeneratorContext context, JClassType userType, String name,
			List<String> icons, List<String> iconPaths) {
		final String subclassName = userType.getSimpleSourceName().replace('.', '_') + "_generated";
		final String packageName = userType.getPackage().getName();
		final ClassSourceFileComposerFactory f = new ClassSourceFileComposerFactory(packageName, subclassName);
		f.setSuperclass(userType.getQualifiedSourceName());
		final PrintWriter pw = context.tryCreate(logger, packageName, subclassName);
		if (pw != null) {
			final SourceWriter sw = f.createSourceWriter(context, pw);

			// Impl for the getter for name.
			sw.println("public String getName() {");
			// TODO(jaimeyap): Use proper string escaping from generator libs.
			sw.println("  return \"" + name + "\";");
			sw.println("}");

			emitIcons(icons, iconPaths, sw);

			sw.commit(logger);
		}
		return f.getCreatedClassName();
	}

	private static void emitIcons(List<String> iconNames, List<String> iconPaths, SourceWriter sw) {
		// Fill in the methods for kicking back the BrowserAction Icons.
		for (int i = 0; i < iconNames.size(); i++) {
			String iconName = Generator.escape(iconNames.get(i));
			String iconField = Generator.escape(iconName) + "_field";
			sw.println("private " + UserType.ICON_USER_TYPE + " " + iconField + " = null;");
			sw.println("public " + UserType.ICON_USER_TYPE + " " + iconName + "() {");
			sw.println("  if (" + iconField + " == null) {");
			sw.println("    " + iconField + " = new " + UserType.ICON_USER_TYPE + "(" + i + ", \""
					+ Generator.escape(iconPaths.get(i)) + "\");");
			sw.println("  }");
			sw.println("  return " + iconField + ";");
			sw.println("}");
		}
	}

}
