/**
 * 
 */
package com.google.gwt.chrome.crx.linker;

import java.util.SortedSet;

import com.google.gwt.chrome.crx.client.GwtContentScript;
import com.google.gwt.chrome.crx.linker.artifact.GwtContentScriptArtifact;
import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.AbstractLinker;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.CompilationResult;
import com.google.gwt.core.ext.linker.LinkerOrder;
import com.google.gwt.core.ext.linker.LinkerOrder.Order;

/**
 * Linker for {@link GwtContentScript} for Chrome Extension
 * 
 * @author Izzet_Mustafaiev
 * 
 */
@LinkerOrder(Order.PRIMARY)
public class GwtContentScriptLinker extends AbstractLinker {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.ext.Linker#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Chrome Extension GwtContentScript Linker";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.core.ext.Linker#link(com.google.gwt.core.ext.TreeLogger,
	 * com.google.gwt.core.ext.LinkerContext,
	 * com.google.gwt.core.ext.linker.ArtifactSet)
	 */
	@Override
	public ArtifactSet link(TreeLogger logger, LinkerContext context, ArtifactSet artifacts)
			throws UnableToCompleteException {
		String scriptFileName = context.getModuleName() + ".js";
		// find GwtContentScriptArtifacts
		final SortedSet<GwtContentScriptArtifact> gwtContentScripts = artifacts.find(GwtContentScriptArtifact.class);
		ensureModuleHasGwtContentScript(logger, gwtContentScripts);
		final SortedSet<CompilationResult> compilations = artifacts.find(CompilationResult.class);
		ensureOnlyOneCompilationResult(logger, compilations);
		CompilationResult compilationResult = compilations.first();
		String javascript = compilationResult.getJavaScript()[0];
		ensureJavaScriptPresent(logger, javascript);
		StringBuffer contentScript = new StringBuffer(javascript.length() + 16);
		contentScript.append(javascript);
		ArtifactSet resultArtifactSet = new ArtifactSet(artifacts);
		resultArtifactSet.add(emitString(logger, contentScript.toString(), scriptFileName));
		return resultArtifactSet;
	}

	protected void ensureJavaScriptPresent(TreeLogger logger, String javascript) throws UnableToCompleteException {
		if (null == javascript || (null != javascript && javascript.length() < 1)) {
			logger.log(TreeLogger.ERROR,
					"JavaScript is empty, please provide some code or remove GwtContentScript definition.");
			throw new UnableToCompleteException();
		}
	}

	protected void ensureOnlyOneCompilationResult(TreeLogger logger, final SortedSet<CompilationResult> compilations)
			throws UnableToCompleteException {
		if (compilations.size() > 1) {
			logger.log(TreeLogger.ERROR,
					"One permutation per module, please. Seriously, you changed something you weren't supposed to.");
			throw new UnableToCompleteException();
		}
	}

	protected void ensureModuleHasGwtContentScript(TreeLogger logger,
			final SortedSet<GwtContentScriptArtifact> gwtContentScripts) throws UnableToCompleteException {
		if (gwtContentScripts.isEmpty()) {
			logger.log(TreeLogger.ERROR, "There is no GwtContentScript, please provide atleast one.");
			throw new UnableToCompleteException();
		}
	}
}
