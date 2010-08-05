package name.webdizz.clt.crx.client.translation.google;

import name.webdizz.clt.crx.client.ExtConfiguration;
import name.webdizz.clt.crx.client.ExtEventBus;
import name.webdizz.clt.crx.client.event.message.TranslationResultMessage;
import name.webdizz.clt.crx.client.event.message.TranslateTextMessage;

import com.google.gwt.language.client.translation.LangDetCallback;
import com.google.gwt.language.client.translation.LangDetResult;
import com.google.gwt.language.client.translation.Language;
import com.google.gwt.language.client.translation.Translation;
import com.google.gwt.language.client.translation.TranslationCallback;
import com.google.gwt.language.client.translation.TranslationResult;

public final class GoogleTranslator {

	private ExtConfiguration configuration;

	private ExtEventBus eventBus;

	public GoogleTranslator(ExtEventBus eventBus) {
		this.eventBus = eventBus;
		configuration = new ExtConfiguration();
	}

	public void translate(final TranslateTextMessage message) {
		Translation.detect(message.getText(), new LangDetCallback() {
			protected void onCallback(LangDetResult result) {
				translateText(message, result);
			}
		});
	}

	private void translateText(final TranslateTextMessage message, LangDetResult result) {
		if (null == result.getError()) {
			final String src = result.getLanguage();
			final String dest = resolveDestLanguage();
			final String from = message.getText();
			Translation.translate(from, src, dest, new TranslationCallback() {
				protected void onCallback(TranslationResult result) {
					handleTranslationResult(src, dest, from, result);
				}
			});
		}
		if (null != result.getError()) {
			// TODO: display error message
		}
	}

	/**
	 * Resolves destination language from the Browser or user's preferences.
	 * 
	 * @return language code
	 */
	private String resolveDestLanguage() {
		String lang = configuration.getDestLanguage();
		if (null == lang || "".equals(lang)) {
			lang = Language.RUSSIAN.getLangCode();
		}
		return lang;
	}

	private void handleTranslationResult(final String src, final String dest, final String from,
			TranslationResult result) {
		String translation = result.getTranslatedText();
		// TODO: create TranslationResult here
		TranslationResultMessage transTextMsg;
		transTextMsg = TranslationResultMessage.create(null);
		eventBus.showTranslatedText(transTextMsg);
	}
}