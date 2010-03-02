/**
 * 
 */
package name.webdizz.clt.crx.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class Alert extends JavaScriptObject {
	
	protected Alert() {
	}

	public static final native void info(String message) /*-{
		alert(message);
	}-*/;
}
