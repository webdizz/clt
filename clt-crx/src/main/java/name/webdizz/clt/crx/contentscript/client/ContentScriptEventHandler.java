package name.webdizz.clt.crx.contentscript.client;

import name.webdizz.clt.crx.client.ActivationKeysHolder;
import name.webdizz.clt.crx.client.chrome.ChromePort;
import name.webdizz.clt.crx.client.event.message.SelectTextMessage;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

final class ContentScriptEventHandler implements NativePreviewHandler {

	private ChromePort port;

	public ContentScriptEventHandler(final ChromePort port) {
		super();
		this.port = port;
	}

	public void onPreviewNativeEvent(final NativePreviewEvent preview) {
		NativeEvent event = preview.getNativeEvent();
		String selection = SelectionProvider.getSelection();
		if (isConsumed(event, selection)) {
			// Send event to background page
			port.postMessage(SelectTextMessage.create(selection, event));
			// Tell the event handler that this event has been consumed
			preview.consume();
		}
	}

	private boolean isConsumed(NativeEvent event, String selection) {
		return "mouseup".equals(event.getType()) && ActivationKeysHolder.isKeysAllowed(event) && !selection.isEmpty();
	}
}