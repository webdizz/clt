/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import java.util.List;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author webdizz
 * 
 */
public abstract class AbstractEmiter implements Emiter {

	protected void emitIcons(List<String> iconNames, List<String> iconPaths, SourceWriter sw) {
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

}
