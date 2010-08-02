/*
 * Copyright 2009 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.chrome.crx.client;

import com.google.gwt.chrome.crx.client.events.ConnectEvent;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * API with all the native methods for Chrome for chrome.extension module.<br/>
 * 
 * Detail information can be found on <a
 * href="http://code.google.com/chrome/extensions/extension.html"
 * >chrome.extension</a><br/>
 * 
 * The chrome.extension module has utilities that can be used by any extension
 * page. It includes support for exchanging messages between an extension and
 * its content scripts or between extensions, as described in detail in <a
 * href="http://code.google.com/chrome/extensions/messaging.html"> Message
 * Passing</a>.
 */
public class Chrome extends JavaScriptObject {

	/**
	 * @return {@link Chrome} - the <a
	 *         href="http://code.google.com/chrome/extensions/extension.html"
	 *         >chrome.extension</a> module.
	 */
	public static final native Chrome getExtension() /*-{
														return chrome.extension;
														}-*/;

	/**
	 * API for chrome.extension.
	 * 
	 * Used mainly for adding listeners to {@link ConnectEvent}.
	 */
	protected Chrome() {
	}

	public final int findIndexOfView(View view) {
		JsArray<View> views = getViews();
		for (int i = 0; i < views.length(); i++) {
			if (views.get(i).equals(view)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the JavaScript 'window' object for the background page running
	 * inside the current extension. Returns null if the extension has no
	 * backround page.
	 * 
	 * @return the extension's {@link BackgroundPage}
	 */
	public final native BackgroundPage getBackgroundPage() /*-{
															return this.getBackgroundPage();
															}-*/;

	/**
	 * Fired when a connection is made from either an extension process or a
	 * content script.
	 * 
	 * @return
	 */
	public final native ConnectEvent getOnConnectEvent() /*-{
															return this.onConnect;
															}-*/;

	/**
	 * Convert a relative path within an extension install directory to a
	 * fully-qualified URL.
	 * 
	 * @param resource
	 *            the resource to get the full URL for
	 * @return fully-qualified URL
	 */
	public final native String getUrl(String resource) /*-{
														return this.getURL(resource);
														}-*/;

	/**
	 * Returns an array of the global JavaScript objects for each of the views
	 * running inside the current extension. This includes background pages and
	 * tabs.
	 * 
	 * @return Array of global objects
	 */
	public final native JsArray<View> getViews() /*-{
													return this.getViews();
													}-*/;

	/**
	 * Attempts to connect to other listeners within the extension (such as the
	 * extension's background page). This is primarily useful for content
	 * scripts connecting to their extension processes. Extensions may connect
	 * to content scripts embedded in tabs via <a href=
	 * "http://code.google.com/chrome/extensions/extension.html#method-connect"
	 * >chrome.extension.connect()</a>.
	 * 
	 * @return {@link Port} through which messages can be sent and received with
	 *         the extension.
	 */
	public final native Port connect() /*-{
										return this.connect();
										}-*/;

	/**
	 * This method attempts to connect to other listeners within extension but
	 * with proper connection name, that will be passed into onConnect for
	 * extension processes that are listening for the connection event.
	 * 
	 * @param connectionName
	 *            the connection name to be passed to connection listener
	 * @return {@link Port} through which messages can be sent and received with
	 *         the extension.
	 */
	public final native Port connect(String connectionName) /*-{
															return this.connect({name:connectionName});
															}-*/;

}
