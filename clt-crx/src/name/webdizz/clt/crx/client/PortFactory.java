/**
 * 
 */
package name.webdizz.clt.crx.client;

import com.google.gwt.chrome.crx.client.Port;

/**
 * This class holds reference to a {@link Port}. This is requires to hold one
 * connection and communicate using it within extension. Seems not good solution
 * but it works.
 * 
 * @author Izzet_Mustafayev
 * 
 */
public class PortFactory {

	private static Port port;

	private PortFactory() {
	}

	public static void createPort(Port port) {
		if (null == PortFactory.port) {
			PortFactory.port = port;
		}
	}

	public static Port instance() {
		if (null == PortFactory.port) {
			throw new RuntimeException("I have no Port to return.");
		}
		return PortFactory.port;
	}

}
