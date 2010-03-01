/**
 * 
 */
package name.webdizz.clt.crx.client.presenter;

import name.webdizz.clt.crx.client.ActivationKeysHolder;
import name.webdizz.clt.crx.client.Alert;
import name.webdizz.clt.crx.client.ExtEventBus;
import name.webdizz.clt.crx.client.event.message.SelectTextMessage;
import name.webdizz.clt.crx.client.event.message.TranslateTextMessage;
import name.webdizz.clt.crx.client.view.BackgroundPageView;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

/**
 * @author Izzet_Mustafayev
 * 
 */
@Presenter(view = BackgroundPageView.class)
public class BackgroundPagePresenter extends
		BasePresenter<BackgroundPagePresenter.IBackgroundPageView, ExtEventBus> {

	/**
	 * The interface for background page.
	 * 
	 * @author Izzet_Mustafayev
	 * 
	 */
	public interface IBackgroundPageView {

	}

	public void onStart() {
		// currently doing nothing, but should perform some initializations
	}

	public void onSelectText(SelectTextMessage message) {
		Alert.info("I recieve message: " + message.getText());
		if (isTranslatable(message.getKeys())) {
			Alert.info(message.getText() + " should be translated");
			TranslateTextMessage transTextMessage;
			transTextMessage = TranslateTextMessage.create(message.getText());
			eventBus.translateText(transTextMessage);
		}
	}

	/**
	 * Perform validation whether message is able to be translated.
	 * 
	 * @param keys
	 *            the array of pressed keyboard keys
	 * @return true if right keyboard key was pressed
	 */
	private boolean isTranslatable(final String[] keys) {
		boolean result = false;
		for (String key : keys) {
			if (ActivationKeysHolder.CTRL.equals(key)) {
				result = true;
				break;
			}
		}
		return result;
	}
}
