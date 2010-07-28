/**
 * 
 */
package name.webdizz.clt.crx.client.presenter;

import java.util.ArrayList;
import java.util.List;

import name.webdizz.clt.crx.client.ExtEventBus;
import name.webdizz.clt.crx.client.db.LocalDataService;
import name.webdizz.clt.crx.client.db.Translation;
import name.webdizz.clt.crx.client.event.message.LoadWordsMessage;
import name.webdizz.clt.crx.client.event.message.ShowWordMessage;
import name.webdizz.clt.crx.client.event.message.StoreTranslationMessage;

import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.ListCallback;
import com.google.code.gwt.database.client.service.RowIdListCallback;
import com.google.code.gwt.database.client.service.VoidCallback;
import com.google.gwt.chrome.crx.client.Tabs;
import com.google.gwt.chrome.crx.client.Tabs.OnTabCallback;
import com.google.gwt.chrome.crx.client.Tabs.Tab;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

/**
 * @author Izzet_Mustafayev
 * 
 */
@Presenter(view = StorePresenter.StorePresenterView.class)
public class StorePresenter extends
		BasePresenter<StorePresenter.IStorePresenterView, ExtEventBus> {

	private final class LoadWordsHandler implements ListCallback<Translation> {
		public void onFailure(DataServiceException error) {
			eventBus.error("StorePresenter.onLoadWords() - "
					+ error.getMessage());
		}

		public void onSuccess(List<Translation> result) {
			if (null != result && !result.isEmpty()) {
				translations = result;
				isDirty = false;
				fireShowWord();
			}
		}
	}

	interface IStorePresenterView {
	}

	public static class StorePresenterView implements IStorePresenterView {
	}

	private LocalDataService service = GWT.create(LocalDataService.class);

	private List<Translation> translations = new ArrayList<Translation>();

	private boolean isDirty = false;

	public StorePresenter() {
		service.initDatabase(new VoidCallback() {
			public void onFailure(DataServiceException error) {
				eventBus.error("StorePresenter.initDatabase() - "
						+ error.getMessage());
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
		Translation translation = Translation.instance();
		translation.setText(message.getTranslateable());
		translation.setTranslation(message.getTranslation());
		// TODO: those attributes should be resolved from app
		translation.setDest("ua");
		translation.setSrc("en");
		service.storeTranslation(translation, new RowIdListCallback() {

			public void onFailure(DataServiceException error) {
				eventBus.error("StorePresenter.storeTranslation() - "
						+ error.getMessage());
			}

			public void onSuccess(List<Integer> rowIds) {
				eventBus.info("Translation was stored. " + rowIds.size());
				isDirty = true;
			}
		});
	}

	public void onLoadWords(LoadWordsMessage message) {
		eventBus.trace("StorePresenter.onLoadWords()");
		if (isDirty || translations.isEmpty()) {
			service.getRandTranslation(new LoadWordsHandler());
		} else {
			fireShowWord();
		}
	}

	/**
	 */
	private void fireShowWord() {
		// should return random translation
		int size = translations.size() - 1;
		int index = Random.nextInt(size);
		Translation translation = null;
		while (null == translation) {
			try {
				translation = translations.get(index);
			} catch (IndexOutOfBoundsException e) {
				index = Random.nextInt(size);
			}
		}
		final ShowWordMessage message = ShowWordMessage.create(translation
				.getText());
		Tabs.getSelected(new OnTabCallback() {
			public void onTab(Tab tab) {
				Tabs.sendRequest(tab.getId(), message);
			}
		});
	}
}
