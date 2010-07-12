/**
 * 
 */
package com.google.gwt.chrome.crx.linker;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.chrome.crx.client.GwtContentScript;
import com.google.gwt.chrome.crx.linker.artifact.ExtensionArtifact;
import com.google.gwt.chrome.crx.linker.artifact.ExtensionArtifact.IconInfo;
import com.google.gwt.chrome.crx.linker.artifact.GwtContentScriptArtifact;
import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.StatementRanges;
import com.google.gwt.core.ext.linker.impl.StandardCompilationResult;

/**
 * @author Izzet_Mustafaiev
 * 
 */
public class GwtContentScriptLinkerTest {

	@Mock
	private TreeLogger logger;

	@Mock
	private LinkerContext context;

	private ArtifactSet artifacts;

	private StandardCompilationResult compilationResult;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		artifacts = new ArtifactSet();
		GwtContentScriptArtifact artifact;
		String[] matches = new String[] { "http://google.com/*" };
		artifact = new GwtContentScriptArtifact("path", matches, GwtContentScript.DOCUMENT_END, false);
		ExtensionArtifact extensionArtifact;
		extensionArtifact = new ExtensionArtifact("name", "desc", "version", new String[] { "tabs" }, "http://url.com",
				new IconInfo[] {});
		artifacts.add(artifact);
		artifacts.add(extensionArtifact);
		compilationResult = new StandardCompilationResult("", new byte[][] { { 1 } }, new byte[] { 1 },
				new StatementRanges[] {}, 0);
		artifacts.add(compilationResult);
	}

	@Test
	public void shouldGetModuleNameFromContext() throws UnableToCompleteException {
		invokeLinker();

		verify(context).getModuleName();
	}

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionIfThereAreMoreThenOneCompilationUnit()
			throws UnableToCompleteException {
		StandardCompilationResult oneMorecompilationResult;
		oneMorecompilationResult = new StandardCompilationResult("oneMore", new byte[][] { {} }, new byte[] {},
				new StatementRanges[] {}, 0);
		artifacts.add(oneMorecompilationResult);
		invokeLinker();

		verify(context).getModuleName();
	}

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionIfJavascriptIsEmpty() throws UnableToCompleteException {

		artifacts.remove(compilationResult);
		compilationResult = new StandardCompilationResult("", new byte[][] { {} }, new byte[] {},
				new StatementRanges[] {}, 0);
		artifacts.add(compilationResult);

		invokeLinker();
	}

	protected void invokeLinker() throws UnableToCompleteException {
		GwtContentScriptLinker linker = new GwtContentScriptLinker();
		linker.getDescription();
		linker.link(logger, context, artifacts);
	}
}
