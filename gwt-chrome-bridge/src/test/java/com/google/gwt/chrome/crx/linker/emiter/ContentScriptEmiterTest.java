/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

import com.google.gwt.chrome.crx.client.ContentScript;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;

/**
 * @author webdizz
 * 
 */
public class ContentScriptEmiterTest {

	@Mock
	private TreeLogger logger;

	@Mock
	private GeneratorContext context;

	@Mock
	private JClassType userType;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		when(userType.getSimpleSourceName()).thenReturn(Emiter.CONTENTSCRIPT_USER_TYPE);
		JPackage jpackage = mock(JPackage.class);
		when(userType.getPackage()).thenReturn(jpackage);
	}

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionArtifactWithoutManifestAnnotation()
			throws UnableToCompleteException {
		when(userType.getAnnotation(ContentScript.ManifestInfo.class)).thenReturn(null);

		invokeCodeEmition();
	}

	@Test
	public void shouldGatherContentScriptFromSpec() throws UnableToCompleteException {
		ContentScript.ManifestInfo spec = mock(ContentScript.ManifestInfo.class);
		when(userType.getAnnotation(ContentScript.ManifestInfo.class)).thenReturn(spec);
		
		invokeCodeEmition();
		
		verify(spec).path();
		verify(spec).runAt();
		verify(spec).whiteList();
	}

	protected void invokeCodeEmition() throws UnableToCompleteException {
		Emiter emiter = new ContentScriptEmiter();
		emiter.emit(logger, context, userType, Emiter.CONTENTSCRIPT_USER_TYPE);
	}
}
