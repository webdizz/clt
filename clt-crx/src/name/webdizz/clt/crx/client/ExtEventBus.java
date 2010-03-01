/**
 * 
 */
package name.webdizz.clt.crx.client;

import name.webdizz.clt.crx.client.event.message.SelectTextMessage;
import name.webdizz.clt.crx.client.event.message.ShowTranslatedTextMessage;
import name.webdizz.clt.crx.client.event.message.TranslateTextMessage;
import name.webdizz.clt.crx.client.presenter.BackgroundPagePresenter;
import name.webdizz.clt.crx.client.presenter.TranslationPresenter;
import name.webdizz.clt.crx.client.view.BackgroundPageView;

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

	@Event(handlers = TranslationPresenter.class)
	void showTranslatedText(ShowTranslatedTextMessage message);
}
