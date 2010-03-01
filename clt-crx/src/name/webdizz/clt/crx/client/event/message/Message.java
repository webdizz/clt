/**
 * 
 */
package name.webdizz.clt.crx.client.event.message;

/**
 * Base message to communicate within extension.
 * 
 * @author Izzet_Mustafayev
 * 
 */
public class Message extends
		com.google.gwt.chrome.crx.client.events.MessageEvent.Message {

	protected Message() {
	}

	public final native String getType() /*-{
		return this.type;
	}-*/;
}
