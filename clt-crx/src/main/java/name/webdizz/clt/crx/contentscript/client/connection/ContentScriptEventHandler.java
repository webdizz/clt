package name.webdizz.clt.crx.contentscript.client.connection;

import name.webdizz.clt.crx.client.ActivationKeysHolder;
import name.webdizz.clt.crx.client.chrome.ChromePort;
import name.webdizz.clt.crx.client.event.message.SelectTextMessage;
import name.webdizz.clt.crx.contentscript.client.SelectionProvider;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

/**
 * Consumes events from hosted page content script was injected into.
 * 
 * @author webdizz
 * 
 */
public final class ContentScriptEventHandler implements NativePreviewHandler {

	private ChromePort port;

	/**
	 * @param port
	 *            the {@link ChromePort}
	 */
	public ContentScriptEventHandler(final ChromePort port) {
		super();
		this.port = port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.Event.NativePreviewHandler#onPreviewNativeEvent
	 * (com.google.gwt.user.client.Event.NativePreviewEvent)
	 */
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

	private boolean isConsumed(final NativeEvent event, final String selection) {
		return "mouseup".equals(event.getType()) && ActivationKeysHolder.isKeysAllowed(event) && !selection.isEmpty();
	}
}