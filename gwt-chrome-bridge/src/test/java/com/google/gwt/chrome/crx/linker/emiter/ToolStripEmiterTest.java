/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;

/**
 * @author zinur
 * 
 */
public class ToolStripEmiterTest {
	@Mock
	private TreeLogger logger;

	@Mock
	private GeneratorContext context;

	@Mock
	private JClassType userType;

	private static final String TYPE_NAME = "com.google.gwt.chrome.BrowserAction";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(userType.getSimpleSourceName()).thenReturn(TYPE_NAME);

	}

	@Test
	public void shouldTryCreateResource() throws UnableToCompleteException {

		String path = userType.getSimpleSourceName() + ".html";
		OutputStream stream = mock(OutputStream.class);
		when(context.tryCreateResource(logger, path)).thenReturn(stream);
		JPackage jpackage = mock(JPackage.class);
		when(jpackage.getName()).thenReturn(TYPE_NAME);
		when(userType.getPackage()).thenReturn(jpackage);
		invokeCodeEmition();
		verify(context).tryCreateResource(logger, path);
		verify(context).commitResource(logger, stream);

	}

	/**
	 * @return
	 * @throws UnableToCompleteException
	 */
	protected String invokeCodeEmition() throws UnableToCompleteException {
		Emiter emiter = new ToolStripEmiter();
		return emiter.emit(logger, context, userType, TYPE_NAME);
	}
}
