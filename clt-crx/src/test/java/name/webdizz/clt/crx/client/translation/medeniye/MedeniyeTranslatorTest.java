/**
 * 
 */
package name.webdizz.clt.crx.client.translation.medeniye;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import name.webdizz.clt.crx.client.translation.TranslationException;
import name.webdizz.clt.crx.client.translation.TranslationHandler;
import name.webdizz.clt.crx.client.translation.TranslationResult;
import name.webdizz.clt.crx.client.translation.Translator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author webdizz
 * 
 */
public class MedeniyeTranslatorTest {

	private Translator translator;

	@Mock
	private MedeniyeRequestBuilder builder;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		translator = new MedeniyeTranslator(builder);
	}

	@Test
	public void shouldNotTranslateEmptyWord() throws Exception {
		TranslationHandler handler = mock(TranslationHandler.class);
		translator.translate("", handler);

		verify(handler, never()).onTranslate(any(TranslationResult.class));
	}

	@Test
	public void shouldNotTranslateWordWithSpaces() throws Exception {
		TranslationHandler handler = mock(TranslationHandler.class);
		translator.translate("  ", handler);

		verify(handler, never()).onTranslate(any(TranslationResult.class));
	}

	@Test(expected = TranslationException.class)
	public void shouldFailIfRequestBuilderIsNull() throws Exception {
		TranslationHandler handler = mock(TranslationHandler.class);
		translator = new MedeniyeTranslator(null);
		translator.translate("word", handler);
	}

	@Test
	public void shouldSendTranslationRequest() throws Exception {
		TranslationHandler handler = mock(TranslationHandler.class);
		translator.translate("word", handler);

		verify(builder).send("word=word", handler);
	}

}