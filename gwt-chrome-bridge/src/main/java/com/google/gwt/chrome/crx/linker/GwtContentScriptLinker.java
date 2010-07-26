/**
 * 
 */
package com.google.gwt.chrome.crx.linker;

import java.util.SortedSet;

import com.google.gwt.chrome.crx.client.GwtContentScript;
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
		final SortedSet<CompilationResult> compilations = artifacts.find(CompilationResult.class);
		ensureOnlyOneCompilationResult(logger, compilations);
		CompilationResult compilationResult = compilations.first();
		String javascript = compilationResult.getJavaScript()[0];
		ensureJavaScriptPresent(logger, javascript);
		String contentScript = enhanceJavaScript(javascript);
		ArtifactSet resultArtifactSet = new ArtifactSet(artifacts);
		resultArtifactSet.add(emitString(logger, contentScript, scriptFileName));
		return resultArtifactSet;
	}

	private String enhanceJavaScript(final String javascript) {
		StringBuffer contentScript = new StringBuffer(javascript.length() + 16);
		contentScript.append("(function() {\n");
		contentScript.append("$wnd=window,");
		contentScript.append("$doc=document,");
		contentScript.append("$stats=$wnd.__gwtStatsEvent ? function(a) {return $wnd.__gwtStatsEvent(a);}:null;\n");
		contentScript.append(javascript);
		contentScript.append("gwtOnLoad();\n}());");
		return contentScript.toString();
	}

	private void ensureJavaScriptPresent(TreeLogger logger, String javascript) throws UnableToCompleteException {
		if (null == javascript || (null != javascript && javascript.length() < 1)) {
			logger.log(TreeLogger.ERROR,
					"JavaScript is empty, please provide some code or remove GwtContentScript definition.");
			throw new UnableToCompleteException();
		}
	}

	private void ensureOnlyOneCompilationResult(TreeLogger logger, final SortedSet<CompilationResult> compilations)
			throws UnableToCompleteException {
		if (compilations.size() > 1) {
			logger.log(TreeLogger.ERROR,
					"One permutation per module, please. Seriously, you changed something you weren't supposed to.");
			throw new UnableToCompleteException();
		}
	}
}
