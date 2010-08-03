/**
 * 
 */
package name.webdizz.clt.crx.client.presenter;

import name.webdizz.clt.crx.client.ExtEventBus;
import name.webdizz.clt.crx.client.event.message.PrepareTranslatedTextDisplayMessage;
import name.webdizz.clt.crx.client.event.message.ShowTranslatedTextMessage;
import name.webdizz.clt.crx.client.event.message.TranslateTextMessage;
import name.webdizz.clt.crx.client.translation.google.GoogleTranslator;
import name.webdizz.clt.crx.client.view.TranslationView;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

/**
 * @author Izzet_Mustafayev
 * 
 */
@Presenter(view = TranslationView.class)
public class TranslationPresenter extends BasePresenter<TranslationPresenter.ITranslationView, ExtEventBus> {

	public interface ITranslationView {

		void setTranslatedText(String text);

		void setTranslateableText(final String text);

		String widgetAsString();

		Widget asWidget();

	}

	public TranslationPresenter() {
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
		view.setTranslatedText(message.getTranslation());
		view.setTranslateableText(message.getTextFrom());
		eventBus.showTranslatedText(view.asWidget());
	}

}
