/**
 * 
 */
package com.google.gwt.chrome.crx.client.events;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wraps event from chrome.extension.onRequest.
 * 
 * See documentation at: <a href=
 * "http://code.google.com/chrome/extensions/extension.html#event-onConnect"
 * >Content Script Messaging</a>
 * 
 */
public final class RequestEvent extends Event {

	/**
	 * The {@link RequestEvent} listener.
	 * 
	 * @author webdizz
	 * 
	 */
	public interface Listener {
		/**
		 * This is a callback method will be called when message from extension
		 * or extension' content script will be received.
		 * 
		 * @param message
		 *            the {@link Message} with data
		 */
		void onRequest(Message message);
	}

	protected RequestEvent() {
	}

	/**
	 * Add a {@link Listener} to handle event.
	 * 
	 * @param listener
	 *            the {@link Listener}
	 * @return {@link ListenerHandle}
	 */
	public ListenerHandle addListener(Listener listener) {
		return new ListenerHandle(this, addListenerImpl(listener));
	}

	private static void onRequestImpl(Listener listener, Message message) {
		UncaughtExceptionHandler ueh = GWT.getUncaughtExceptionHandler();
		if (ueh != null) {
			try {
				listener.onRequest(message);
			} catch (Exception ex) {
				ueh.onUncaughtException(ex);
			}
		} else {
			listener.onRequest(message);
		}
	}

	private native JavaScriptObject addListenerImpl(Listener listener) /*-{
																		var handle = function(message, sender, response) {
																		//TODO: do something with sender
																		@com.google.gwt.chrome.crx.client.events.RequestEvent::onRequestImpl(Lcom/google/gwt/chrome/crx/client/events/RequestEvent$Listener;Lcom/google/gwt/chrome/crx/client/events/Message;)
																		(listener, message);
																		}
																		
																		this.addListener(handle);
																		return handle;
																		}-*/;

}
