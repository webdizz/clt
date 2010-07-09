/**
 * 
 */
package com.google.gwt.chrome.crx.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class LocalStorage extends JavaScriptObject {

	protected LocalStorage() {
	}

	public final native void setItem(String key, String value) /*-{
																window.localStorage.setItem(key, value)
																}-*/;

	public final native String getItem(String key) /*-{
													var value;
													try {
													value = window.localStorage.getItem(key);
													}catch(e) {
													value = "null";
													}
													return value;
													}-*/;

	public final native void removeItem(String key) /*-{
													window.localStorage.removeItem(key);
													}-*/;

	public final native void clear() /*-{
										window.localStorage.clear();
										}-*/;
}
