/**
 * 
 */
package name.webdizz.clt.crx.client.translation.medeniye;

import name.webdizz.clt.crx.client.translation.Language;
import name.webdizz.clt.crx.client.translation.TranslationResult;

/**
 * Parses response and create from response {@link TranslationResult}.
 * 
 * @author webdizz
 * 
 */
public class TranslationResultCreator {

	private static final String RUSSIAN_WORD = "Russian";

	private static final String CRIMEAN_TATAR_WORD = "Crimean Tatar";

	private static final String SOURCE_WORD_START_SMB = "<dt><b>";
	private static final String SOURCE_WORD_END_SMB = "</b></dt>";

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
		}
		return translationResult;
	}

	protected void resolveSourceWord(final String responseText, final TranslationResult translationResult) {
		int sourceStartPos = responseText.indexOf(SOURCE_WORD_START_SMB) + SOURCE_WORD_START_SMB.length();
		int sourceEndPos = responseText.indexOf(SOURCE_WORD_END_SMB);
		translationResult.setSrc(responseText.substring(sourceStartPos, sourceEndPos));
	}

	protected void resolveLanguage(final String responseText, final TranslationResult translationResult) {
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
