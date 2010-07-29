/**
 * 
 */
package name.webdizz.clt.crx.client.translation.medeniye;

import java.util.ArrayList;
import java.util.List;

import name.webdizz.clt.crx.client.translation.Language;
import name.webdizz.clt.crx.client.translation.TranslationResult;
import name.webdizz.clt.crx.client.translation.TranslationResult.Explanation;
import name.webdizz.clt.crx.client.translation.TranslationResult.Translation;

/**
 * Parses response and create from response {@link TranslationResult}.
 * 
 * @author webdizz
 * 
 */
public class TranslationResultCreator {

	private static final int ZERO = 0;

	private static final int NOTHING_FOUND = -1;

	private static final String RUSSIAN_WORD = "Russian";

	private static final String CRIMEAN_TATAR_WORD = "Crimean Tatar";

	private static final String SOURCE_WORD_START_SMB = "<dt><b>";
	private static final String SOURCE_WORD_END_SMB = "</b></dt>";

	private static final String TRANSLATIONS_START_SMB = "<dd style=\"margin:0 0 0 .5em\">";
	private static final String TRANSLATIONS_END_SMB = "</dd>";

	private static final String TRANSLATION_START_SMB = "<div style=\"\">";
	private static final String EXPLAINATION_START_SMB = "<div style=\"margin-left:.5em;color:#666;\">";
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
		int transStart = text.indexOf(TRANSLATIONS_START_SMB);
		int transEnd = text.indexOf(TRANSLATIONS_END_SMB);
		boolean isParsable = transStart > NOTHING_FOUND;
		String transTxt = isParsable ? text.substring(transStart + TRANSLATIONS_START_SMB.length(), transEnd) : "";
		List<Translation> translations = new ArrayList<Translation>();
		if (!transTxt.isEmpty()) {
			processTranslations(translationResult, transTxt, translations);
		}
		if (!translations.isEmpty()) {
			translationResult.setTranslations(translations);
		}
	}

	private void processTranslations(final TranslationResult translationResult, final String translationTxt,
			final List<Translation> translations) {
		String transTxt = translationTxt;
		int pos = transTxt.indexOf(TRANSLATION_START_SMB);
		while (pos > NOTHING_FOUND) {
			int start = pos + TRANSLATION_START_SMB.length();
			int end = transTxt.substring(start).indexOf(DIV_END_SMB) + start;
			Translation translation = createAndAddTranslation(translationResult, transTxt, translations, start, end);
			// handle explanations
			transTxt = processExplanations(translationResult, transTxt, translation, end);
			pos = transTxt.indexOf(TRANSLATION_START_SMB);
		}
	}

	private Translation createAndAddTranslation(final TranslationResult translationResult, String transTxt,
			List<Translation> translations, int start, int end) {
		String translationText = transTxt.substring(start, end);
		Translation translation = translationResult.new Translation();
		translation.setTranslation(translationText);
		translations.add(translation);
		return translation;
	}

	private String processExplanations(final TranslationResult translationResult, final String translationTxt,
			final Translation translation, final int endPosition) {
		String transTxt = new String(translationTxt);
		int end = endPosition;
		int explStart = transTxt.indexOf(EXPLAINATION_START_SMB);
		int explSmbLength = EXPLAINATION_START_SMB.length();
		List<Explanation> explanations = new ArrayList<TranslationResult.Explanation>();
		if (explStart > NOTHING_FOUND) {
			boolean workWithExplanations = true;
			while (workWithExplanations) {
				explStart = explStart + explSmbLength;
				transTxt = transTxt.substring(explStart);
				String explanationTxt = transTxt.substring(ZERO, transTxt.indexOf(DIV_END_SMB));
				createAndAddExplanation(translationResult, explanations, explanationTxt);
				workWithExplanations = nextExplanationExists(transTxt, explanationTxt);

				// reset start point
				transTxt = transTxt.substring(explanationTxt.length() + DIV_END_SMB.length());
				explStart = ZERO;
				end = ZERO;
			}
		}
		if (!explanations.isEmpty()) {
			translation.setExplanations(explanations);
		}
		// prepare data for next iteration
		return transTxt.substring(end);
	}

	private boolean nextExplanationExists(final String transTxt, final String explanationTxt) {
		boolean workWithExplanations;
		int explSmbLength = EXPLAINATION_START_SMB.length();
		int subStart = explanationTxt.length() + DIV_END_SMB.length();
		int subEnd = subStart + explSmbLength;
		workWithExplanations = EXPLAINATION_START_SMB.equals(transTxt.substring(subStart, subEnd));
		return workWithExplanations;
	}

	private void createAndAddExplanation(final TranslationResult translationResult,
			final List<Explanation> explanations, final String explanationTxt) {
		Explanation explanation = translationResult.new Explanation();
		explanation.setExplanation(explanationTxt);
		explanations.add(explanation);
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
