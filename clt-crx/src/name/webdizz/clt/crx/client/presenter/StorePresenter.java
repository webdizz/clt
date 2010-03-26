/**
 * 
 */
package name.webdizz.clt.crx.client.presenter;

import java.util.List;

import name.webdizz.clt.crx.client.Alert;
import name.webdizz.clt.crx.client.db.LocalDataService;
import name.webdizz.clt.crx.client.db.Translation;
import name.webdizz.clt.crx.client.event.message.StoreTranslationMessage;

import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.RowIdListCallback;
import com.google.code.gwt.database.client.service.VoidCallback;
import com.google.gwt.core.client.GWT;
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

	private LocalDataService service = GWT.create(LocalDataService.class);

	public StorePresenter() {
		service.initDatabase(new VoidCallback() {
			public void onFailure(DataServiceException error) {
				// TODO: should have more suitable error reporting approach
				Alert.info(error.getMessage());
			}

			public void onSuccess() {
				// currently do nothing
			}
		});
	}

	/**
	 * Is invoked on storing translation.
	 * 
	 * @param message
	 *            the {@link StoreTranslationMessage} contains data to store
	 */
	public void onStoreTranslation(StoreTranslationMessage message) {
		Alert.info(message.getTranslateable());
		Translation translation = Translation.instance();
		translation.setText(message.getTranslateable());
		translation.setTranslation(message.getTranslation());
		// TODO: those attributes should be resolved from app
		translation.setDest("ua");
		translation.setSrc("en");
		Alert.info("te be stored");
		service.storeTranslation(translation, new RowIdListCallback() {

			public void onFailure(DataServiceException error) {
				// TODO: should have more suitable error reporting approach
				Alert.info(error.getMessage());
			}

			public void onSuccess(List<Integer> rowIds) {
				Alert.info("Translation was stored. " + rowIds.size());
			}
		});
	}
}
