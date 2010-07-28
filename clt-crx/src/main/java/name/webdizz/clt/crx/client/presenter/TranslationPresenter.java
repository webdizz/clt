/**
 * 
 */
package name.webdizz.clt.crx.client.presenter;

import name.webdizz.clt.crx.client.ExtConfiguration;
import name.webdizz.clt.crx.client.ExtEventBus;
import name.webdizz.clt.crx.client.event.message.PrepareTranslatedTextDisplayMessage;
import name.webdizz.clt.crx.client.event.message.ShowTranslatedTextMessage;
import name.webdizz.clt.crx.client.event.message.TranslateTextMessage;
import name.webdizz.clt.crx.client.view.TranslationView;

import com.google.gwt.language.client.translation.LangDetCallback;
import com.google.gwt.language.client.translation.LangDetResult;
import com.google.gwt.language.client.translation.Language;
import com.google.gwt.language.client.translation.Translation;
import com.google.gwt.language.client.translation.TranslationCallback;
import com.google.gwt.language.client.translation.TranslationResult;
import com.google.gwt.user.client.ui.Widget;
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
		
		void setTranslatedText(String text);
		
		void setTranslateableText(final String text);

		String widgetAsString();

		Widget asWidget();
		
	}

	private ExtConfiguration configuration;

	public TranslationPresenter() {
		configuration = new ExtConfiguration();
	}

	/**
	 * Performs translation of the given {@link TranslateTextMessage}.
	 * 
	 * @param message
	 *            the message to translate
	 */
	public void onTranslateText(final TranslateTextMessage message) {
		detectSrcLanguage(message);
	}

	/**
	 * Performs visualization of the given translated
	 * {@link ShowTranslatedTextMessage}.
	 * 
	 * @param message
	 *            the translated message to display
	 */
	public void onTranslatedText(
			final PrepareTranslatedTextDisplayMessage message) {
		view.setTranslatedText(message.getTranslation());
		view.setTranslateableText(message.getTextFrom());
		eventBus.showTranslatedText(view.asWidget());
	}

	private void detectSrcLanguage(final TranslateTextMessage message) {
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
		PrepareTranslatedTextDisplayMessage transTextMsg;
		transTextMsg = PrepareTranslatedTextDisplayMessage.create(from,
				translation, src, dest);
		eventBus.handleTranslatedText(transTextMsg);
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
}
