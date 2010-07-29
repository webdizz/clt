/**
 * 
 */
package name.webdizz.clt.crx.client.translation.medeniye;

import java.util.ArrayList;
import java.util.List;

import name.webdizz.clt.crx.client.translation.Language;
import name.webdizz.clt.crx.client.translation.TranslationResult;
import name.webdizz.clt.crx.client.translation.TranslationResult.Translation;

/**
 * Parses response and create from response {@link TranslationResult}.
 * 
 * @author webdizz
 * 
 */
public class TranslationResultCreator {

	private static final int NOTHING_FOUND = -1;

	private static final String RUSSIAN_WORD = "Russian";

	private static final String CRIMEAN_TATAR_WORD = "Crimean Tatar";

	private static final String SOURCE_WORD_START_SMB = "<dt><b>";
	private static final String SOURCE_WORD_END_SMB = "</b></dt>";

	private static final String TRANSLATIONS_START_SMB = "<dd style=\"margin:0 0 0 .5em\">";
	private static final String TRANSLATIONS_END_SMB = "</dd>";

	private static final String TRANSLATION_START_SMB = "<div style=\"\">";
	private static final String DIV_END_SMB = "</div>";

	/**
	 * Performes parsing the response from remote service and creates
	 * {@link TranslationResult} as a result.
	 * 
	 * @param responseText
	 *            the response text
	 * @return the {@link TranslationResult}
	 */
	public TranslationResult parse(final String responseText) {
		TranslationResult translationResult = null;
		if (null != responseText && !responseText.isEmpty()) {
			translationResult = new TranslationResult();
			resolveLanguage(responseText, translationResult);
			resolveSourceWord(responseText, translationResult);
			resolveTranslations(responseText, translationResult);
		}
		return translationResult;
	}

	private void resolveTranslations(final String text, final TranslationResult translationResult) {
		int translationsStart = text.indexOf(TRANSLATIONS_START_SMB);
		int translationsEnd = text.indexOf(TRANSLATIONS_END_SMB);
		String translationsTxt = translationsStart > NOTHING_FOUND ? text.substring(translationsStart
				+ TRANSLATIONS_START_SMB.length(), translationsEnd) : "";
		List<Translation> translations = new ArrayList<Translation>();
		if (!translationsTxt.isEmpty()) {
			boolean work = true;
			while (work) {
				int pos = translationsTxt.indexOf(TRANSLATION_START_SMB);
				work = pos > NOTHING_FOUND;
				if (work) {
					Translation translation = translationResult.new Translation();
					int start = pos + TRANSLATION_START_SMB.length();
					int end = translationsTxt.substring(start).indexOf(DIV_END_SMB) + start;
					String translationText = translationsTxt.substring(start, end);
					translation.setTranslation(translationText);
					translations.add(translation);
					translationsTxt = translationsTxt
							.substring(start + translationText.length() + DIV_END_SMB.length());
				}
			}
		}
		if (!translations.isEmpty()) {
			translationResult.setTranslations(translations);
		}
	}

	private void resolveSourceWord(final String responseText, final TranslationResult translationResult) {
		int sourceStart = responseText.indexOf(SOURCE_WORD_START_SMB);
		int sourceEnd = responseText.indexOf(SOURCE_WORD_END_SMB);
		if (sourceStart > NOTHING_FOUND && sourceEnd > NOTHING_FOUND) {
			translationResult.setSrc(responseText.substring(sourceStart + SOURCE_WORD_START_SMB.length(), sourceEnd));
		}
	}

	private void resolveLanguage(final String responseText, final TranslationResult translationResult) {
		int russianPos = responseText.indexOf(RUSSIAN_WORD);
		int crimeanTatarPos = responseText.indexOf(CRIMEAN_TATAR_WORD);
		if (russianPos < crimeanTatarPos) {
			translationResult.setSrcLang(Language.RUSSIAN);
			translationResult.setDestLang(Language.CRIMEAN_TATAR);
		} else {
			translationResult.setDestLang(Language.RUSSIAN);
			translationResult.setSrcLang(Language.CRIMEAN_TATAR);
		}
	}

}
