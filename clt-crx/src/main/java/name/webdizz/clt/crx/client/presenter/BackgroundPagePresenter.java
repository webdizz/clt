/**
 * 
 */
package name.webdizz.clt.crx.client.presenter;

import name.webdizz.clt.crx.client.ExtConfiguration;
import name.webdizz.clt.crx.client.ExtEventBus;
import name.webdizz.clt.crx.client.event.message.PrepareTranslatedTextDisplayMessage;
import name.webdizz.clt.crx.client.event.message.SelectTextMessage;
import name.webdizz.clt.crx.client.event.message.ShowTranslatedTextMessage;
import name.webdizz.clt.crx.client.event.message.TranslateTextMessage;
import name.webdizz.clt.crx.client.translation.google.GoogleTranslator;
import name.webdizz.clt.crx.client.view.BackgroundPageView;

import com.google.gwt.chrome.crx.client.Tabs;
import com.google.gwt.chrome.crx.client.Tabs.OnDetectLanguageCallback;
import com.google.gwt.chrome.crx.client.Tabs.OnTabCallback;
import com.google.gwt.chrome.crx.client.Tabs.Tab;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

/**
 * @author Izzet_Mustafayev
 * 
 */
@Presenter(view = BackgroundPageView.class)
public class BackgroundPagePresenter extends BasePresenter<BackgroundPagePresenter.IBackgroundPageView, ExtEventBus> {

	/**
	 * Holds reference to the last received {@link SelectTextMessage} to be able
	 * to display translation in the appropriate place.
	 */
	private SelectTextMessage selectTextMessage;

	private ExtConfiguration configuration;

	public BackgroundPagePresenter() {
		configuration = new ExtConfiguration();
	}

	/**
	 * The interface for background page.
	 * 
	 * @author Izzet_Mustafayev
	 * 
	 */
	public interface IBackgroundPageView {

	}

	public void onStart() {
		// detect language
		detectLanguage();
	}

	private void detectLanguage() {
		if ("".equals(configuration.getDestLanguage())) {
			Tabs.detectLanguage(new OnDetectLanguageCallback() {
				public void onDetect(String languageCode) {
					eventBus.trace("BackgroundPagePresenter.detectLanguage(): Detected lang - " + languageCode);
					configuration.setDestLanguage(languageCode);
				}
			});
		}
	}

	/**
	 * Is called by {@link ExtEventBus} when a "selectText" event triggered.
	 * 
	 * @param message
	 *            the {@link SelectTextMessage} contains text to translate
	 */
	public void onSelectText(final SelectTextMessage message) {
		eventBus.trace("BackgroundPagePresenter.onSelectText() :" + message.getText() + " should be translated");
		TranslateTextMessage transTextMessage;
		transTextMessage = TranslateTextMessage.create(message.getText());
		eventBus.translateText(transTextMessage);
		selectTextMessage = message;
	}

	/**
	 * Performs translation of the given {@link TranslateTextMessage}.
	 * 
	 * @param message
	 *            the message to translate
	 */
	public void onTranslateText(final TranslateTextMessage message) {
		new GoogleTranslator(eventBus).translate(message);
	}

	/**
	 * Performs visualization of the given translated
	 * {@link ShowTranslatedTextMessage}.
	 * 
	 * @param message
	 *            the translated message to display
	 */
	public void onTranslatedText(final PrepareTranslatedTextDisplayMessage message) {
		eventBus.showTranslatedText(message);
	}

	/**
	 * Send message to the extension script to display translated text within a
	 * window.
	 * 
	 * @param widget
	 *            a {@link Widget} to show as a translation
	 */
	public void onShowTranslatedText(final PrepareTranslatedTextDisplayMessage translation) {
		Tabs.getSelected(new OnTabCallback() {
			public void onTab(Tab tab) {
				ShowTranslatedTextMessage message;
				message = ShowTranslatedTextMessage.create(selectTextMessage, translation);
				Tabs.sendRequest(tab.getId(), message);
			}
		});
	}
}
