package name.webdizz.clt.crx.contentscript.client.connection;

import name.webdizz.clt.crx.client.event.message.ShowTranslatedTextMessage;
import name.webdizz.clt.crx.contentscript.client.translation.view.ITranslationView;
import name.webdizz.clt.crx.contentscript.client.translation.view.TranslationView;

import com.google.gwt.chrome.crx.client.events.Message;
import com.google.gwt.chrome.crx.client.events.RequestEvent.Listener;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Listener for request communications from extension or another content script.
 * 
 * @author webdizz
 * 
 */
public final class ContentScriptRequestListener implements Listener {

	private static class TranslationResultPanel extends PopupPanel {
		public TranslationResultPanel(ShowTranslatedTextMessage message) {
			// enable autohide
			super(true);
			setAnimationEnabled(true);
			setGlassEnabled(true);
			ITranslationView view = new TranslationView();
			view.setTranslateableText(message.getTranslation().getTextFrom());
			view.setTranslatedText(message.getTranslation().getTranslation());
			setWidget((TranslationView) view);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.chrome.crx.client.events.RequestEvent.Listener#onRequest
	 * (com.google.gwt.chrome.crx.client.events.Message)
	 */
	public void onRequest(final Message message) {
		if (null != message) {
			final name.webdizz.clt.crx.client.event.message.Message castedMessage = message.cast();
			if (ShowTranslatedTextMessage.TYPE.equals(castedMessage.getType())) {
				final ShowTranslatedTextMessage translation = castedMessage.cast();
				final TranslationResultPanel resultPanel = new TranslationResultPanel(translation);
				resultPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
					public void setPosition(int offsetWidth, int offsetHeight) {
						int selectionOffsetX = translation.getMessage().getOffsetX();
						int selectionOffsetY = translation.getMessage().getOffsetY();
						resultPanel.setPopupPosition(selectionOffsetX + 30, selectionOffsetY - 60);
					}
				});
			}
		}
	}
}