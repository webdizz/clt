/**
 * 
 */
package name.webdizz.clt.crx.client.translation.medeniye;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import name.webdizz.clt.crx.client.translation.Language;
import name.webdizz.clt.crx.client.translation.TranslationResult;

import org.junit.Before;
import org.junit.Test;

/**
 * @author webdizz
 * 
 */
public class TranslationResultCreatorTest {

	private TranslationResultCreator creator;

	private static final String RESPONSE_RU2CT = "<dl style=\"margin:0 0 1em 0\">"
			+ "<div style=\"font-size:xx-small;\">Russian - Crimean Tatar</div>" + "<dt><b>воспользоваться</b></dt>"
			+ "<dd style=\"margin:0 0 0 .5em\"><div style=\"\">sebeplenmek</div></dd>" + "</dl>";

	private static final String RESPONSE_CT2R = "<dl style=\"margin:0 0 1em 0\">"
			+ "<div style=\"font-size:xx-small;\">Crimean Tatar - Russian</div>" + "<dt><b>ğaye</b></dt>"
			+ "<dd style=\"margin:0 0 0 .5em\"><div style=\"\">идея</div><div style=\"\">ср. mefküre</div></dd>"
			+ "</dl>";

	@Before
	public void setUp() {
		creator = new TranslationResultCreator();
	}

	@Test
	public void shouldNotHaveNullAsAResult() {
		TranslationResult result = creator.parse(RESPONSE_RU2CT);
		assertNotNull("TranslationResult is null", result);
	}

	@Test
	public void shouldResolveRussianAsSource() {
		TranslationResult result = creator.parse(RESPONSE_RU2CT);
		assertEquals("Unable to resolve source language", Language.RUSSIAN, result.getSrcLang());
	}

	@Test
	public void shouldResolveCrimeanTatarAsDest() {
		TranslationResult result = creator.parse(RESPONSE_RU2CT);
		assertEquals("Unable to resolve dest language", Language.CRIMEAN_TATAR, result.getDestLang());
	}

	@Test
	public void shouldResolveCrimeanTatarAsSource() {
		TranslationResult result = creator.parse(RESPONSE_CT2R);
		assertEquals("Unable to resolve source language", Language.CRIMEAN_TATAR, result.getSrcLang());
	}

	@Test
	public void shouldResolveRussianAsDest() {
		TranslationResult result = creator.parse(RESPONSE_CT2R);
		assertEquals("Unable to resolve dest language", Language.RUSSIAN, result.getDestLang());
	}

	@Test
	public void shouldResolveSourceWord() {
		TranslationResult result = creator.parse(RESPONSE_CT2R);
		assertEquals("Unable to resolve source word", "ğaye", result.getSrc());
	}
}
