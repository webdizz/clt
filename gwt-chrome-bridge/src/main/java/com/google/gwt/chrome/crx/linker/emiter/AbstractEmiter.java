/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import java.util.List;

import com.google.gwt.chrome.crx.client.BrowserAction;
import com.google.gwt.core.ext.Generator;
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

}
