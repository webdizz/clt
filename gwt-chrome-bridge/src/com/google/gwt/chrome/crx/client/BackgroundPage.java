/**
 * 
 */
package com.google.gwt.chrome.crx.client;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class BackgroundPage extends View {

	protected BackgroundPage() {
	}

	/**
	 * Retrieves the instance of the Chrome's implementation of the HTML5 <a
	 * href="http://dev.w3.org/html5/webstorage/">Web Storage</a>.
	 * 
	 * @return current extension's local storage
	 */
	public final native LocalStorage getLocalStorage() /*-{
		return this.localStorage;
	}-*/;
}
