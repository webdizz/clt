/*
 * Copyright 2009 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.chrome.crx.linker;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.google.gwt.chrome.crx.client.Component;
import com.google.gwt.chrome.crx.client.ContentScript;
import com.google.gwt.chrome.crx.client.ContentScript.ManifestInfo;
import com.google.gwt.chrome.crx.client.ExtensionScript;
import com.google.gwt.chrome.crx.client.Plugin;
import com.google.gwt.chrome.crx.linker.artifact.ContentScriptArtifact;
import com.google.gwt.chrome.crx.linker.artifact.ExtensionScriptArtifact;
import com.google.gwt.chrome.crx.linker.artifact.PluginArtifact;
import com.google.gwt.chrome.crx.linker.artifact.ToolStripArtifact;
import com.google.gwt.chrome.crx.linker.emiter.BrowserActionEmiter;
import com.google.gwt.chrome.crx.linker.emiter.Emiter;
import com.google.gwt.chrome.crx.linker.emiter.PageActionEmiter;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * Generator for extension {@link Component}s.
 */
public class ComponentGenerator extends Generator {
	private static final String BROWSERACTION_USER_TYPE = "com.google.gwt.chrome.crx.client.BrowserAction";
	private static final String CONTENTSCRIPT_USER_TYPE = "com.google.gwt.chrome.crx.client.ContentScript";
	private static final String EXTSCRIPT_USER_TYPE = "com.google.gwt.chrome.crx.client.ExtensionScript";
	private static final String PAGE_USER_TYPE = "com.google.gwt.chrome.crx.client.Page";
	private static final String PLUGIN_USER_TYPE = "com.google.gwt.chrome.crx.client.Plugin";
	private static final String TOOLSTRIP_USER_TYPE = "com.google.gwt.chrome.crx.client.ToolStrip";

	private static String emitComponent(TreeLogger logger, GeneratorContext context, String typeName)
			throws UnableToCompleteException {
		final TypeOracle typeOracle = context.getTypeOracle();

		final JClassType toolStripType = typeOracle.findType(TOOLSTRIP_USER_TYPE);
		assert toolStripType != null;

		final JClassType pageType = typeOracle.findType(PAGE_USER_TYPE);
		assert pageType != null;

		final JClassType pageActionType = typeOracle.findType(Emiter.PAGEACTION_USER_TYPE);
		assert pageActionType != null;

		final JClassType browserActionType = typeOracle.findType(BROWSERACTION_USER_TYPE);
		assert browserActionType != null;

		final JClassType contentScriptType = typeOracle.findType(CONTENTSCRIPT_USER_TYPE);
		assert contentScriptType != null;

		final JClassType extensionScriptType = typeOracle.findType(EXTSCRIPT_USER_TYPE);
		assert extensionScriptType != null;

		final JClassType pluginType = typeOracle.findType(PLUGIN_USER_TYPE);
		assert pluginType != null;

		try {
			final JClassType classType = typeOracle.getType(typeName);
			if (classType.isAssignableTo(toolStripType)) {
				return processToolStrip(logger, context, classType);
			} else if (classType.isAssignableTo(pageType)) {
				return processPage(logger, context, classType);
			} else if (classType.isAssignableTo(contentScriptType)) {
				processContentScript(logger, context, classType, typeName);
				return typeName;
			} else if (classType.isAssignableTo(extensionScriptType)) {
				processExtensionScript(logger, context, classType, typeName);
				return typeName;
			} else if (classType.isAssignableTo(pluginType)) {
				processPlugin(logger, context, classType, typeName);
				return typeName;
			} else if (classType.isAssignableTo(pageActionType)) {
				return new PageActionEmiter().emit(logger, context, classType, typeName);
			} else if (classType.isAssignableTo(browserActionType)) {
				return new BrowserActionEmiter().emit(logger, context, classType, typeName);
			}
			// TODO(knorton): Better error message.
			logger.log(TreeLogger.ERROR, "I can't generate one of those (" + typeName + ")");
			throw new UnableToCompleteException();
		} catch (NotFoundException e) {
			// TODO(knorton): Better error message.
			logger.log(TreeLogger.ERROR, "Unknown Type: " + typeName);
			throw new UnableToCompleteException();
		}
	}

	private static void emitComponentPage(TreeLogger logger, GeneratorContext context, String name, String path)
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

	private static String emitComponentPageCode(TreeLogger logger, GeneratorContext context, JClassType userType) {
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

	private static void processContentScript(TreeLogger logger, GeneratorContext context, JClassType userType,
			String typeName) throws UnableToCompleteException {
		ManifestInfo spec = userType.getAnnotation(ContentScript.ManifestInfo.class);
		if (spec == null) {
			logger.log(TreeLogger.ERROR, "ContentScript (" + typeName + ") must be annotated with a Specificaiton.");
			throw new UnableToCompleteException();
		}
		context.commitArtifact(logger, new ContentScriptArtifact(spec.path(), spec.whiteList(), spec.runAt()));
	}

	private static void processExtensionScript(TreeLogger logger, GeneratorContext context, JClassType userType,
			String typeName) throws UnableToCompleteException {
		ExtensionScript.ManifestInfo spec = userType.getAnnotation(ExtensionScript.ManifestInfo.class);
		if (spec == null) {
			logger.log(TreeLogger.ERROR, "ExtensionScript (" + typeName + ") must be annotated with a Specificaiton.");
			throw new UnableToCompleteException();
		}
		context.commitArtifact(logger, new ExtensionScriptArtifact(spec.path(), spec.script()));
	}

	private static String processPage(TreeLogger logger, GeneratorContext context, JClassType userType)
			throws UnableToCompleteException {
		// TODO(knorton): The fact that we use the simple source name is a
		// problem
		// if you ever GWT.create multiple instances of the same component. So
		// we
		// really should generate an id for these.
		String name = userType.getSimpleSourceName();
		String path = name + ".html";
		emitComponentPage(logger, context, name, path);
		// No need to commit any artifact for the linker.
		return emitComponentPageCode(logger, context, userType);
	}

	private static void processPlugin(TreeLogger logger, GeneratorContext context, JClassType userType, String typeName)
			throws UnableToCompleteException {
		Plugin.ManifestInfo spec = userType.getAnnotation(Plugin.ManifestInfo.class);
		if (spec == null) {
			logger.log(TreeLogger.ERROR, "Plugin (" + typeName + ") must be annotated with a Specificaiton.");
			throw new UnableToCompleteException();
		}
		context.commitArtifact(logger, new PluginArtifact(spec.path(), spec.isPublic()));
	}

	private static String processToolStrip(TreeLogger logger, GeneratorContext context, JClassType userType)
			throws UnableToCompleteException {
		String name = userType.getSimpleSourceName();
		String path = name + ".html";
		emitComponentPage(logger, context, name, path);
		context.commitArtifact(logger, new ToolStripArtifact(path));
		return emitComponentPageCode(logger, context, userType);
	}

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName)
			throws UnableToCompleteException {
		return emitComponent(logger, context, typeName);
	}
}
