/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import com.google.gwt.chrome.crx.client.BrowserAction;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author webdizz
 * 
 */
public abstract class AbstractEmiter implements Emiter {

	/**
	 * Emits icons for the {@link BrowserAction}.
	 * 
	 * @param sw
	 *            the {@link SourceWriter}
	 * @param iconNames
	 * @param iconPaths
	 */
	protected void emitIcons(final SourceWriter sw, final List<String> iconNames, final List<String> iconPaths) {
		// Fill in the methods for kicking back the BrowserAction Icons.
		for (int i = 0; i < iconNames.size(); i++) {
			String iconName = Generator.escape(iconNames.get(i));
			String iconField = Generator.escape(iconName) + "_field";
			sw.println("private " + ICON_USER_TYPE + " " + iconField + " = null;");
			sw.println("public " + ICON_USER_TYPE + " " + iconName + "() {");
			sw.println("  if (" + iconField + " == null) {");
			sw.println("    " + iconField + " = new " + ICON_USER_TYPE + "(" + i + ", \""
					+ Generator.escape(iconPaths.get(i)) + "\");");
			sw.println("  }");
			sw.println("  return " + iconField + ";");
			sw.println("}");
		}
	}

	/**
	 * Implementation for the getter for name
	 * 
	 * @param sw
	 *            the {@link SourceWriter}
	 * @param name
	 *            the name to return
	 */
	protected void emitNameGetter(final SourceWriter sw, final String name) {
		sw.println("public String getName() {");
		// TODO(jaimeyap): Use proper string escaping from generator libs.
		sw.println("  return \"" + name + "\";");
		sw.println("}");
	}

	protected void emitIdGetter(final SourceWriter sw, final String id) {
		sw.println("public String getId() {");
		sw.println("  return \"" + id + "\";");
		sw.println("}");
	}

	/**
	 * Creates subclass name from full simpleSourceName.
	 * 
	 * @param typeName
	 *            {@link String}
	 * @return generated name
	 */
	protected String createSubclassName(final String typeName) {
		return typeName.replace('.', '_') + "_generated";
	}
	
	/**
	 * @param logger
	 * @param context
	 * @param name
	 * @param path
	 * @throws UnableToCompleteException
	 */
	protected static void emitComponentPage(TreeLogger logger, GeneratorContext context, String name, String path)
			throws UnableToCompleteException {
		final OutputStream stream = context.tryCreateResource(logger, path);
		if (stream != null) {
			final PrintWriter writer = new PrintWriter(new OutputStreamWriter(stream));
			writer.println("<html>");
			writer.println("<head></head>");
			writer.println("<body>");
			writer.println("  <script>");
			writer.println("  window.onload = function() {");
			writer.println("    var views = chrome.self.getViews();");
			writer.println("    views[0][\"" + name + "\"](window);");
			writer.println("  };");
			writer.println("  </script>");
			writer.println("</body>");
			writer.println("</html>");
			writer.close();
			context.commitResource(logger, stream);
		}
	}
	protected static String emitComponentPageCode(TreeLogger logger, GeneratorContext context, JClassType userType) {
		final String subclassName = userType.getSimpleSourceName().replace('.', '_') + "_generated";
		final String packageName = userType.getPackage().getName();
		final ClassSourceFileComposerFactory f = new ClassSourceFileComposerFactory(packageName, subclassName);
		f.setSuperclass(userType.getQualifiedSourceName());
		final PrintWriter pw = context.tryCreate(logger, packageName, subclassName);
		if (pw != null) {
			final SourceWriter sw = f.createSourceWriter(context, pw);

			// Write a default constructor that simply calls connect.
			sw.println("public " + subclassName + "() {");
			sw.println("  connect(\"" + userType.getSimpleSourceName() + "\");");
			sw.println("}");

			sw.commit(logger);
		}
		return f.getCreatedClassName();
	}

}
