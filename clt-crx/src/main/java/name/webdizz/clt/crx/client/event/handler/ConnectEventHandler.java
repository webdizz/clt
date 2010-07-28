/**
 * 
 */
package name.webdizz.clt.crx.client.event.handler;

import name.webdizz.clt.crx.client.ExtEventBus;

import com.google.gwt.chrome.crx.client.Chrome;
import com.google.gwt.chrome.crx.client.Console;
import com.google.gwt.chrome.crx.client.Port;

public final class ConnectEventHandler implements
		com.google.gwt.chrome.crx.client.events.ConnectEvent.Listener {

	private static final Console CONSOLE = Chrome.getExtension()
			.getBackgroundPage().getConsole();

	private ExtEventBus eventBus;

	public ConnectEventHandler(final ExtEventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void onConnect(final Port port) {
		CONSOLE.log("Enter into onConnect handler");
		port.getOnMessageEvent().addListener(new MessageEventHandler(eventBus));
	}
}