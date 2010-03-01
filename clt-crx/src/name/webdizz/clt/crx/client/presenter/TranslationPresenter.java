/**
 * 
 */
package name.webdizz.clt.crx.client.presenter;

import name.webdizz.clt.crx.client.Alert;
import name.webdizz.clt.crx.client.ExtEventBus;
import name.webdizz.clt.crx.client.event.message.ShowTranslatedTextMessage;
import name.webdizz.clt.crx.client.event.message.TranslateTextMessage;
import name.webdizz.clt.crx.client.view.TranslationView;

import com.google.gwt.language.client.translation.LangDetCallback;
import com.google.gwt.language.client.translation.LangDetResult;
import com.google.gwt.language.client.translation.Language;
import com.google.gwt.language.client.translation.Translation;
import com.google.gwt.language.client.translation.TranslationCallback;
import com.google.gwt.language.client.translation.TranslationResult;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

/**
 * @author Izzet_Mustafayev
 * 
 */
@Presenter(view = TranslationView.class)
public class TranslationPresenter extends
		BasePresenter<TranslationPresenter.ITranslationView, ExtEventBus> {

	public interface ITranslationView {
	}

	/**
	 * Performs translation of the given {@link TranslateTextMessage}.
	 * 
	 * @param message
	 *            the message to translate
	 */
	public void onTranslateText(final TranslateTextMessage message) {
		detectLanguage(message);
	}

	/**
	 * Performs visualization of the given translated
	 * {@link ShowTranslatedTextMessage}.
	 * 
	 * @param message
	 *            the translated message to display
	 */
	public void onShowTranslatedText(final ShowTranslatedTextMessage message) {
		Alert.info("Translated text is: " + message.getTranslation());
	}

	private void detectLanguage(final TranslateTextMessage message) {
		Translation.detect(message.getText(), new LangDetCallback() {

			protected void onCallback(LangDetResult result) {
				translateText(message, result);
			}

		});
	}

	private void translateText(final TranslateTextMessage message,
			LangDetResult result) {
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

	private void handleTranslationResult(final String src, final String dest,
			final String from, TranslationResult result) {
		String translation = result.getTranslatedText();
		ShowTranslatedTextMessage transTextMsg;
		transTextMsg = ShowTranslatedTextMessage.create(from, translation, src,
				dest);
		eventBus.showTranslatedText(transTextMsg);
	}

	/**
	 * Resolves destination language from the Browser or user's preferences.
	 * TODO: should have this logic a bit later
	 * 
	 * @return language code
	 */
	private String resolveDestLanguage() {
		return Language.RUSSIAN.getLangCode();
	}

}
