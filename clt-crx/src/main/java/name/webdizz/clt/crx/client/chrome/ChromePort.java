/**
 * 
 */
package name.webdizz.clt.crx.client.chrome;

import name.webdizz.clt.crx.client.event.message.Message;

import com.google.gwt.chrome.crx.client.Port;

/**
 * This class is a wrapper for {@link com.google.gwt.chrome.crx.client.Port}.
 * Currently the main aim is abilities for testing methods using
 * {@link com.google.gwt.chrome.crx.client.Port}.
 * 
 * @author webdizz
 * 
 */
public class ChromePort {

	private Port port;

	public ChromePort(final Port port) {
		super();
		this.port = port;
	}

	public void postMessage(final Message message) {
		port.postMessage(message);
	}

}
