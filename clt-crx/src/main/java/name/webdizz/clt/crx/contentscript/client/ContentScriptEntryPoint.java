/**
 * 
 */
package name.webdizz.clt.crx.contentscript.client;

import name.webdizz.clt.crx.client.ActivationKeysHolder;
import name.webdizz.clt.crx.client.event.message.SelectTextMessage;

import com.google.gwt.chrome.crx.client.GwtContentScriptEntryPoint;
import com.google.gwt.chrome.crx.client.Port;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

/**
 * @author webdizz
 * 
 */
public class ContentScriptEntryPoint extends GwtContentScriptEntryPoint {

	private static class SelectionProvider extends JavaScriptObject {
		protected SelectionProvider() {
		}

		final static native String getSelection() /*-{
			var selection = $wnd.getSelection();
			return selection?new String(selection.toString()).trim():'';
		}-*/;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.chrome.crx.client.GwtContentScriptEntryPoint#onScriptLoad
	 * ()
	 */
	@Override
	public void onScriptLoad() {
		Event.addNativePreviewHandler(new NativePreviewHandler() {

			public void onPreviewNativeEvent(final NativePreviewEvent preview) {
				NativeEvent event = preview.getNativeEvent();
				String selection = SelectionProvider.getSelection();
				if (isConsumed(event, selection)) {
					// Send event to background page
					Port port = connect("liner");
					port.postMessage(SelectTextMessage.create(selection, event));
					// Tell the event handler that this event has been consumed
					preview.consume();
				}
			}

			private boolean isConsumed(NativeEvent event, String selection) {
				return "mouseup".equals(event.getType()) && ActivationKeysHolder.isKeysAllowed(event)
						&& !selection.isEmpty();
			}
		});

	}

}
