/**
 * 
 */
package name.webdizz.clt.crx.client.presenter;

import name.webdizz.clt.crx.client.Alert;
import name.webdizz.clt.crx.client.event.message.StoreTranslationMessage;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;

/**
 * @author Izzet_Mustafayev
 * 
 */
@Presenter(view = StorePresenter.StorePresenterView.class)
public class StorePresenter extends
		BasePresenter<StorePresenter.IStorePresenterView, EventBus> {

	interface IStorePresenterView {
	}

	public static class StorePresenterView implements IStorePresenterView {
	}

	/**
	 * Is invoked on storing translation.
	 * 
	 * @param message
	 *            the {@link StoreTranslationMessage} contains data to store
	 */
	public void onStoreTranslation(StoreTranslationMessage message) {
		Alert.info(message.getTranslateable());
	}
}
