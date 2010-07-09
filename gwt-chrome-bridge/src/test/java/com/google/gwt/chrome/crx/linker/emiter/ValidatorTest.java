/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.chrome.crx.client.BrowserAction;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;

/**
 * @author webdizz
 * 
 */
public class ValidatorTest {

	@Mock
	private TreeLogger logger;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteException() throws UnableToCompleteException {
		Validator<BrowserAction.ManifestInfo> validator = new Validator<BrowserAction.ManifestInfo>(logger,
				BrowserAction.class.getSimpleName(), "typeName");
		validator.ensureAnnotatedWithManifest(null);
	}
	
	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionForIcons() throws UnableToCompleteException {
		Validator<BrowserAction.ManifestInfo> validator = new Validator<BrowserAction.ManifestInfo>(logger,
				BrowserAction.class.getSimpleName(), "typeName");
		validator.ensureActionHasIcon(new ArrayList<String>());
	}

}
