/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.chrome.crx.client.ExtensionScript;
import com.google.gwt.chrome.crx.linker.artifact.ExtensionScriptArtifact;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;

/**
 * @author zinur
 * 
 */
public class ExtentionsScriptEmiterTest {
	@Mock
	private TreeLogger logger;

	@Mock
	private GeneratorContext context;

	@Mock
	private JClassType userType;
	/**
	 * Type name
	 */
	private static final String TYPE_NAME = "com.google.gwt.chrome.BrowserAction";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldTryGetCommitArtifact() throws UnableToCompleteException {
		ExtensionScript.ManifestInfo ext = mock(ExtensionScript.ManifestInfo.class);
		when(ext.path()).thenReturn("google/com/");
		when(ext.script()).thenReturn("jquery");
		when(userType.getAnnotation(ExtensionScript.ManifestInfo.class)).thenReturn(ext);
		context.commitArtifact(logger, new ExtensionScriptArtifact(ext.path(), ext.script()));
		invokeCodeEmition();
	}

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionIfNoExtentions() throws UnableToCompleteException {
		when(userType.getAnnotation(ExtensionScript.ManifestInfo.class)).thenReturn(null);
		invokeCodeEmition();
	}

	/**
	 * @return
	 * @throws UnableToCompleteException
	 */
	protected String invokeCodeEmition() throws UnableToCompleteException {
		Emiter emiter = new ExtentionsScriptEmiter();
		return emiter.emit(logger, context, userType, TYPE_NAME);
	}

}
