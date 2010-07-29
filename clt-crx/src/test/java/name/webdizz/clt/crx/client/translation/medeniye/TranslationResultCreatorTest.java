/**
 * 
 */
package name.webdizz.clt.crx.client.translation.medeniye;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;

import name.webdizz.clt.crx.client.translation.Language;
import name.webdizz.clt.crx.client.translation.TranslationResult;
import name.webdizz.clt.crx.client.translation.TranslationResult.Translation;

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

	private static final String RESPONSE_WITH_NO_TRANS = "<dl style=\"margin:0 0 1em 0\">"
			+ "<div style=\"font-size:xx-small;\">Crimean Tatar - Russian</div>" + "<dt><b>ğaye</b></dt>" + "</dl>";

	private static final String RESPONSE_WITH_TRANS = "<dl>"
			+ "<div>Crimean Tatar - Russian</div>"
			+ "<dt><b>cik</b></dt>"
			+ "<dd style=\"margin:0 0 0 .5em\"><div style=\"\">1) пробор</div><div style=\"margin-left:.5em;color:#666;\">doğru cik - прямой пробор (посередине)</div><div style=\"margin-left:.5em;color:#666;\">qıya cik - косой пробор (боковой)</div>"
			+ "<div style=\"\">2) мн. стрелки на брюках</div></dd>" + "</dl>";

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

	@Test
	public void shouldResolveTranslations() {
		TranslationResult result = creator.parse(RESPONSE_CT2R);
		assertNotNull("Unable to resolve translations", result.getTranslations());
	}

	@Test
	public void shouldHandleResponseAbsence() {
		TranslationResult result = creator.parse("");
		assertNull("We do not want to have ", result);
	}

	@Test
	public void shouldHandleWordNotFound() {
		TranslationResult result = creator.parse("Word not found");
		assertTrue("We've found out something in the word not found", result.isEmpty());
	}

	@Test
	public void shouldHandleResponseWithoutTranslations() {
		TranslationResult result = creator.parse(RESPONSE_WITH_NO_TRANS);
		assertTrue("We've found out something in the response without translations", result.isEmpty());
	}

	@Test
	public void shouldHandleResponseWithSeveralTranslations() {
		TranslationResult result = creator.parse(RESPONSE_WITH_TRANS);
		assertTrue("Unable to find translations", result.getTranslations().size() > 1);
	}

	@Test
	public void shouldHandleResponseWithTranslation() {
		TranslationResult result = creator.parse(RESPONSE_RU2CT);
		assertTrue("Unable to find translation", result.getTranslations().size() == 1);
	}

	@Test
	public void shouldResolveTranslationExplanationIfExists() {
		TranslationResult result = creator.parse(RESPONSE_WITH_TRANS);
		List<Translation> list = result.getTranslations();
		assertFalse("Unable to find translation explanations", list.get(0).getExplanations().isEmpty());
	}

}
