/**
 * 
 */
package name.webdizz.clt.crx.client;

import name.webdizz.clt.crx.client.event.message.PrepareTranslatedTextDisplayMessage;
import name.webdizz.clt.crx.client.event.message.SelectTextMessage;
import name.webdizz.clt.crx.client.event.message.StoreTranslationMessage;
import name.webdizz.clt.crx.client.event.message.TranslateTextMessage;
import name.webdizz.clt.crx.client.presenter.BackgroundPagePresenter;
import name.webdizz.clt.crx.client.presenter.MonitorPresenter;
import name.webdizz.clt.crx.client.presenter.StorePresenter;
import name.webdizz.clt.crx.client.presenter.TranslationPresenter;
import name.webdizz.clt.crx.client.view.BackgroundPageView;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;

/**
 * @author Izzet_Mustafayev
 * 
 */
@Events(startView = BackgroundPageView.class, debug = true)
public interface ExtEventBus extends EventBus {

	@Start
	@Event(handlers = { BackgroundPagePresenter.class })
	void start();

	@Event(handlers = BackgroundPagePresenter.class)
	void selectText(SelectTextMessage message);

	@Event(handlers = TranslationPresenter.class)
	void translateText(TranslateTextMessage message);

	@Event(handlers = TranslationPresenter.class, calledMethod = "onTranslatedText")
	void handleTranslatedText(PrepareTranslatedTextDisplayMessage message);

	@Event(handlers = BackgroundPagePresenter.class)
	void showTranslatedText(Widget widget);

	@Event(handlers = StorePresenter.class)
	void storeTranslation(StoreTranslationMessage message);

	@Event(handlers = MonitorPresenter.class)
	void error(String message);

	@Event(handlers = MonitorPresenter.class)
	void info(String message);
	
	@Event(handlers = MonitorPresenter.class)
	void trace(String message);
}
