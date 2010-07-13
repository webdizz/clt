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

import com.google.gwt.chrome.crx.client.Component;
import com.google.gwt.chrome.crx.linker.emiter.BrowserActionEmiter;
import com.google.gwt.chrome.crx.linker.emiter.ContentScriptEmiter;
import com.google.gwt.chrome.crx.linker.emiter.Emiter;
import com.google.gwt.chrome.crx.linker.emiter.ExtentionsScriptEmiter;
import com.google.gwt.chrome.crx.linker.emiter.GwtContentScriptEmiter;
import com.google.gwt.chrome.crx.linker.emiter.ModuleDefinitionLoader;
import com.google.gwt.chrome.crx.linker.emiter.PageActionEmiter;
import com.google.gwt.chrome.crx.linker.emiter.PageEmiter;
import com.google.gwt.chrome.crx.linker.emiter.PluginEmiter;
import com.google.gwt.chrome.crx.linker.emiter.ToolStripEmiter;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

/**
 * Generator for extension {@link Component}s.
 */
public class ComponentGenerator extends Generator {
	private static final String BROWSERACTION_USER_TYPE = "com.google.gwt.chrome.crx.client.BrowserAction";
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

		final JClassType contentScriptType = typeOracle.findType(Emiter.CONTENTSCRIPT_USER_TYPE);
		assert contentScriptType != null;

		final JClassType gwtContentScriptType = typeOracle.findType(Emiter.GWT_CONTENTSCRIPT_USER_TYPE);
		assert gwtContentScriptType != null;

		final JClassType extensionScriptType = typeOracle.findType(EXTSCRIPT_USER_TYPE);
		assert extensionScriptType != null;

		final JClassType pluginType = typeOracle.findType(PLUGIN_USER_TYPE);
		assert pluginType != null;

		try {
			final JClassType classType = typeOracle.getType(typeName);
			if (classType.isAssignableTo(toolStripType)) {
				return new ToolStripEmiter().emit(logger, context, classType, null);
			} else if (classType.isAssignableTo(pageType)) {
				return new PageEmiter().emit(logger, context, classType, typeName);
			} else if (classType.isAssignableTo(contentScriptType)) {
				return new ContentScriptEmiter().emit(logger, context, classType, typeName);
			} else if (classType.isAssignableTo(gwtContentScriptType)) {
				return new GwtContentScriptEmiter(new ModuleDefinitionLoader()).emit(logger, context, classType,
						typeName);
			} else if (classType.isAssignableTo(extensionScriptType)) {
				return new ExtentionsScriptEmiter().emit(logger, context, classType, typeName);
			} else if (classType.isAssignableTo(pluginType)) {
				return new PluginEmiter().emit(logger, context, classType, typeName);
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

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName)
			throws UnableToCompleteException {
		return emitComponent(logger, context, typeName);
	}
}
