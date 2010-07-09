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

import com.google.gwt.chrome.crx.client.events.TabAttachedEvent;
import com.google.gwt.chrome.crx.client.events.TabDetachedEvent;
import com.google.gwt.chrome.crx.client.events.TabEvent;
import com.google.gwt.chrome.crx.client.events.TabRemovedEvent;
import com.google.gwt.chrome.crx.client.events.TabSelectionChangedEvent;
import com.google.gwt.chrome.crx.client.events.TabUpdatedEvent;
import com.google.gwt.chrome.crx.client.events.MessageEvent.Message;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * Wraps the chrome.tabs API.
 * 
 * See documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/tabs-api"
 * >Tabs</a>
 * 
 * The API is described in JSON form in the Chrome source:
 * src/chrome/common/extensions/api/extension_api.json
 */
public class Tabs extends JavaScriptObject {
	/**
	 * Callback that returns a single Tab as an argument.
	 */
	public interface OnTabCallback {
		void onTab(Tab tab);
	}

	/**
	 * Callback for events that return an array of tabs in the first argument.
	 */
	public interface OnTabsCallback {
		void onTabs(JsArray<Tab> tabs);
	}

	/**
	 * Callback that returns no arguments. Used to notify when Tabs.update()
	 * completes.
	 */
	public interface OnUpdateCallback {
		void onUpdate();
	}

	public interface OnDetectLanguageCallback {
		void onDetect(String languageCode);
	}

	/**
	 * Represents a tab in the browser.
	 */
	public static class Tab extends JavaScriptObject {
		protected Tab() {
		}

		public final native String getFavIconUrl() /*-{
													return this.favIconUrl;
													}-*/;

		public final native int getId() /*-{
										return this.id;
										}-*/;

		public final native int getIndex() /*-{
											return this.index;
											}-*/;

		public final native String getStatus() /*-{
												return this.status;
												}-*/;

		public final native String getTitle() /*-{
												return this.title;
												}-*/;

		public final native String getUrl() /*-{
											return this.url;
											}-*/;

		public final native int getWindowId() /*-{
												return this.windowId;
												}-*/;

		public final native boolean isSelected() /*-{
													return this.selected;
													}-*/;
	}

	/**
	 * An overload of
	 * {@link Tabs#create(String, int, int, boolean, OnTabCallback)} takes no
	 * parameters.
	 * 
	 */
	public static native void create() /*-{
										chrome.tabs.create();
										}-*/;

	/**
	 * An overload of
	 * {@link Tabs#create(String, int, int, boolean, OnTabCallback)} that only
	 * takes a url.
	 * 
	 * @param url
	 *            the starting url of the tab you want to open
	 */
	public static native void create(String url) /*-{
													chrome.tabs.create({'url':url});
													}-*/;

	/**
	 * An overload of
	 * {@link Tabs#create(String, int, int, boolean, OnTabCallback)} that omits
	 * the optional callback.
	 * 
	 * @param url
	 *            the starting url of the tab you want to open
	 * @param windowId
	 *            the id of the window that will own the tab
	 * @param index
	 *            the index in the order list of tabs you would like to insert
	 *            the tab
	 * @param selected
	 *            whether or not the tab will be selected (have focus)
	 */
	public static native void create(String url, int windowId, int index, boolean selected) /*-{
																							chrome.tabs.create({
																							'url':url,
																							'windowId':windowId,
																							'index':index,
																							'selected':selected
																							});
																							}-*/;

	/**
	 * Creates a new tab in the specified window and calls you back with the new
	 * Tab's information.
	 * 
	 * @param url
	 *            the starting url of the tab you want to open
	 * @param windowId
	 *            the id of the window that will own the tab
	 * @param index
	 *            the index in the order list of tabs you would like to insert
	 *            the tab
	 * @param selected
	 *            whether or not the tab will be selected (have focus)
	 * @param callback
	 *            a callback that gets called when the tab has been created
	 */
	public static native void create(String url, int windowId, int index, boolean selected, OnTabCallback callback) /*-{
																													chrome.tabs.create({
																													'url':url,
																													'windowId':windowId,
																													'index':index,
																													'selected':selected
																													}, function(tab) {
																													callback.
																													@com.google.gwt.chrome.crx.client.Tabs.OnTabCallback::onTab(Lcom/google/gwt/chrome/crx/client/Tabs$Tab;)
																													(tab);
																													});
																													}-*/;

	/**
	 * An overload of
	 * {@link Tabs#create(String, int, int, boolean, OnTabCallback)} that only
	 * takes a url and a callback.
	 * 
	 * @param url
	 *            the starting url of the tab you want to open
	 * @param callback
	 *            a callback that gets called when the tab has been created
	 */
	public static native void create(String url, OnTabCallback callback) /*-{
																			chrome.tabs.create({'url':url}, function(tab) {
																			callback.
																			@com.google.gwt.chrome.crx.client.Tabs.OnTabCallback::onTab(Lcom/google/gwt/chrome/crx/client/Tabs$Tab;)
																			(tab);
																			});
																			}-*/;

	public static native void get(int tabId, OnTabCallback callback) /*-{
																		chrome.tabs.get(tabId, function(tab) {
																		callback.
																		@com.google.gwt.chrome.crx.client.Tabs.OnTabCallback::onTab(Lcom/google/gwt/chrome/crx/client/Tabs$Tab;)
																		(tab);
																		});
																		}-*/;

	/**
	 * Retrieve all tabs in the specified window.
	 * 
	 * @param windowId
	 *            window to query.
	 * @param callback
	 *            returns the tabs asynchronously through this callback.
	 */
	public static native void getAllInWindow(int windowId, OnTabsCallback callback) /*-{
																					chrome.tabs.getAllInWindow(id, function(tabs) {
																					callback.
																					@com.google.gwt.chrome.crx.client.Tabs.OnTabsCallback::onTabs(Lcom/google/gwt/core/client/JsArray;)
																					(tabs);
																					});
																					}-*/;

	/**
	 * Retrieve all tabs in the current window context.
	 * 
	 * @param callback
	 *            returns the tabs asynchronously through this callback.
	 */
	public static native void getAllInWindow(OnTabsCallback callback) /*-{
																		chrome.tabs.getAllInWindow(undefine, function(tabs) {
																		callback.
																		@com.google.gwt.chrome.crx.client.Tabs.OnTabsCallback::onTabs(Lcom/google/gwt/core/client/JsArray;)
																		(tabs);
																		});
																		}-*/;

	public static native TabEvent getOnCreatedEvent() /*-{
														return chrome.tabs.onCreated;
														}-*/;

	public static native TabSelectionChangedEvent getOnSelectionChangedEvent() /*-{
																				return chrome.tabs.onSelectionChanged;
																				}-*/;

	public static native TabAttachedEvent getOnTabAttachedEvent() /*-{
																	return chrome.tabs.onAttached;
																	}-*/;

	public static native TabDetachedEvent getOnTabDetachedEvent() /*-{
																	return chrome.tabs.onDetached;
																	}-*/;

	public static native TabRemovedEvent getOnTabRemovedEvent() /*-{
																return chrome.tabs.onRemoved();
																}-*/;

	public static native TabUpdatedEvent getOnUpdatedEvent() /*-{
																return chrome.tabs.onUpdated;
																}-*/;

	/**
	 * Retrieve the currently selected tab in the specified window.
	 * 
	 * @param windowId
	 *            window to query for selected tab.
	 * @param callback
	 *            returns the selected tab asynchronously through this callback.
	 */
	public static native void getSelected(int windowId, OnTabCallback callback) /*-{
																				chrome.tabs.getSelected(windowId, function(tab) {
																				callback.
																				@com.google.gwt.chrome.crx.client.Tabs.OnTabCallback::onTab(Lcom/google/gwt/chrome/crx/client/Tabs$Tab;)
																				(tab);
																				});
																				}-*/;

	/**
	 * Retrieve the currently selected tab in the current window context.
	 * 
	 * @param callback
	 *            returns the selected tab asynchronously through this callback.
	 */
	public static native void getSelected(OnTabCallback callback) /*-{
																	chrome.tabs.getSelected(undefined, function(tab) {
																	callback.
																	@com.google.gwt.chrome.crx.client.Tabs.OnTabCallback::onTab(Lcom/google/gwt/chrome/crx/client/Tabs$Tab;)
																	(tab);
																	});
																	}-*/;

	public static native void move(int tabId, int index, int windowId) /*-{
																		chrome.tabs.move(tabId, {'index': index, 'windowId': windowId});
																		}-*/;

	public static native void remove(int tabId) /*-{
												chrome.tabs.remove(tabId);
												}-*/;

	public static native void update(int tabId, String url, boolean selected, OnUpdateCallback callback) /*-{
																											chrome.tabs.update(tabId, {'url':url, 'selected':selected}, function(tab) {
																											callback.@com.google.gwt.chrome.crx.client.Tabs.OnUpdateCallback::onUpdate()();
																											});
																											}-*/;

	/**
	 * Detects the primary language of the content in the current selected tab.
	 * 
	 * @param callback
	 *            The callback parameter that will be called after language
	 *            detection
	 */
	public static final native void detectLanguage(OnDetectLanguageCallback callback) /*-{
																						chrome.tabs.detectLanguage(null, function(language) {
																						callback.@com.google.gwt.chrome.crx.client.Tabs.OnDetectLanguageCallback::onDetect(Ljava/lang/String;)(language);
																						});
																						}-*/;

	/**
	 * Connects to the content script(s) in the current selected tab. The <a
	 * href=
	 * "http://code.google.com/chrome/extensions/extension.html#event-onConnect"
	 * >chrome.extension.onConnect</a> event is fired in each content script
	 * running in the specified tab for the current extension. For more details,
	 * see <a href=
	 * "http://code.google.com/chrome/extensions/content_scripts.html#messaging"
	 * >Content Script Messaging</a>.
	 * 
	 * @return {@link Port}
	 */
	public static final native Port connect()/*-{
												return chrome.tabs.connect(-1);
												}-*/;

	/**
	 * Connects to the content script(s) in the given tab. The <a href=
	 * "http://code.google.com/chrome/extensions/extension.html#event-onConnect"
	 * >chrome.extension.onConnect</a> event is fired in each content script
	 * running in the specified tab for the current extension. For more details,
	 * see <a href=
	 * "http://code.google.com/chrome/extensions/content_scripts.html#messaging"
	 * >Content Script Messaging</a>.
	 * 
	 * @param tabId
	 *            the tab id to connect to
	 * @return {@link Port}
	 */
	public static final native Port connect(int tabId)/*-{
														return chrome.tabs.connect(tabId);
														}-*/;

	/**
	 * Connects to the content script(s) in the given tab. The <a href=
	 * "http://code.google.com/chrome/extensions/extension.html#event-onConnect"
	 * >chrome.extension.onConnect</a> event is fired in each content script
	 * running in the specified tab for the current extension. For more details,
	 * see <a href=
	 * "http://code.google.com/chrome/extensions/content_scripts.html#messaging"
	 * >Content Script Messaging</a>.
	 * 
	 * @param tabId
	 *            the tab id to connect to
	 * @param portName
	 *            the port name to connect to
	 * @return {@link Port}
	 */
	public static final native Port connect(int tabId, String portName)/*-{
																		return chrome.tabs.connect(tabId, {name: portName});
																		}-*/;

	public static final native void sendRequest(int tabId, Message message)/*-{
																			chrome.tabs.sendRequest(tabId, message);
																			}-*/;

	public final static class ExecutionDetails extends JavaScriptObject {
		protected ExecutionDetails() {
		}

		public static final native ExecutionDetails forFile(String file, boolean allFrames)/*-{
																							return {file: file, allFrames: allFrames};
																							}-*/;

		public static final native ExecutionDetails forCode(String jsCode, boolean allFrames)/*-{
																								return {code: jsCode, allFrames: allFrames};
																								}-*/;
	}

	/**
	 * @param tabId
	 *            The id of tab which run the script, default to selected tab of
	 *            current window.
	 * @param details
	 *            The {@link ExecutionDetails} of the script to run. Either the
	 *            code or file property must be set, but both may not be set at
	 *            the same time.
	 */
	public static final native void executeScript(int tabId, ExecutionDetails details)/*-{
																						//callback function can be as a 3d parameter
																						chrome.tabs.executeScript(tabId, details);
																						}-*/;

	protected Tabs() {
	}
}
