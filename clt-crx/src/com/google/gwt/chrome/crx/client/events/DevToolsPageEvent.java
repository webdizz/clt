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
package com.google.gwt.chrome.crx.client.events;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Part of the experimental timeline api. An event object that makes it possible
 * to listen for events from the devtools timeline agent.
 */
public class DevToolsPageEvent extends Event {
  /**
   * The record that gets passed back onPageEvent.
   */
  public static class PageEvent extends JavaScriptObject {
    protected PageEvent() {
    }

    public final native String getMethod() /*-{
      return this[0];
    }-*/;
  }

  /**
   * Called when a page event is received.
   */
  public interface Listener {
    void onPageEvent(PageEvent event);
  }

  protected DevToolsPageEvent() {
  }

  public final ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(event) {
      listener.@com.google.gwt.chrome.crx.client.events.DevToolsPageEvent$Listener::onPageEvent(Lcom/google/gwt/chrome/crx/client/events/DevToolsPageEvent$PageEvent;)(event);
    };

    this.addListener(handle);
    return handle;
  }-*/;
}
