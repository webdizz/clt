package com.google.gwt.chrome.crx.linker.emiter;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.dev.cfg.ModuleDef;
import com.google.gwt.dev.cfg.ModuleDefLoader;

public class ModuleDefinitionLoader {
	public ModuleDef loadModule(TreeLogger logger, String moduleName) throws UnableToCompleteException {
		ModuleDef moduleDef = ModuleDefLoader.loadFromClassPath(logger, moduleName);
		return moduleDef;
	}
}