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

import com.google.gwt.chrome.crx.client.events.DevToolsPageEvent;
import com.google.gwt.chrome.crx.client.events.DevToolsTabCloseEvent;
import com.google.gwt.chrome.crx.client.events.DevToolsTabUrlChangeEvent;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Experimental devtools/timeline api. Gives extensions access to performance
 * data for the web pages being hosted within a tab.
 */
public class DevTools {

  /**
   * An object which allows subscription to different event types on a
   * particular tab.
   */
  public static class TabEvents extends JavaScriptObject {
    protected TabEvents() {
    }

    public final native DevToolsPageEvent getPageEvent() /*-{
      return this.onPageEvent;
    }-*/;

    public final native DevToolsTabCloseEvent getTabCloseEvent() /*-{
      return this.onTabCloseEvent;
    }-*/;

    public final native DevToolsTabUrlChangeEvent getTabUrlChangeEvent() /*-{
      return this.onTabUrlChangeEvent;
    }-*/;
  }

  /**
   * Returns an object that allows subscription to different event types for a
   * particular tab.
   * 
   * @param tabId the tab's id
   * @return
   */
  public static TabEvents getTabEvents(int tabId) {
    // Clients should check isEnabled() before invoking this.
    assert isEnabled() : "devtools api is not enabled.";
    return getTabEventsImpl(tabId);
  }

  public static native boolean isEnabled() /*-{
    return !!chrome.devtools;
  }-*/;

  public static native void setProfilingOptions(int tabId,
      boolean enableStackTraces, boolean enableCpuProfiling) /*-{
    chrome.devtools.setProfilingOptions(tabId, 
        {
          enableStackTraces: enableStackTraces,
          enableCPUProfiling: enableCpuProfiling
        });
  }-*/;

  private static native TabEvents getTabEventsImpl(int tabId) /*-{
    return chrome.devtools.getTabEvents(tabId);
  }-*/;
}
