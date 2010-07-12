package com.google.gwt.chrome.crx.linker.emiter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;

import com.google.gwt.chrome.crx.client.Plugin;
import com.google.gwt.chrome.crx.linker.artifact.PluginArtifact;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;

/**
 * @author zinur
 * 
 */
public class PluginEmiterTest {

	@Mock
	private TreeLogger logger;

	@Mock
	private GeneratorContext context;

	@Mock
	private JClassType userType;

	private static final String TYPE_NAME = Emiter.PAGEACTION_USER_TYPE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Plugin.ManifestInfo spec = mock(Plugin.ManifestInfo.class);
		when(spec.path()).thenReturn("google/com/");
		when(spec.isPublic()).thenReturn(true);
		when(userType.getAnnotation(Plugin.ManifestInfo.class)).thenReturn(spec);
	}

	@Test
	public void shouldCommitArtifact() throws UnableToCompleteException {

		Plugin.ManifestInfo spec = userType.getAnnotation(Plugin.ManifestInfo.class);
		invokeCodeEmition();
		verify(context).commitArtifact(logger, new PluginArtifact(spec.path(), spec.isPublic()));

	}

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionIfNoPlugin() throws UnableToCompleteException {
		when(userType.getAnnotation(Plugin.ManifestInfo.class)).thenReturn(null);
		invokeCodeEmition();
	}

	protected String invokeCodeEmition() throws UnableToCompleteException {
		Emiter emiter = new PluginEmiter();
		return emiter.emit(logger, context, userType, TYPE_NAME);
	}

}
