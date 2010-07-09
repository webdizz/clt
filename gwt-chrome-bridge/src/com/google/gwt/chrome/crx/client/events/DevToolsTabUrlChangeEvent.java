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
 * Part of the experimental timeline api. An event object this is notified when
 * a monitored tab navigates to a new url.
 */
public class DevToolsTabUrlChangeEvent extends Event {

  /**
   * Invoked when the monitored tab navigates to a new url.
   */
  public interface Listener {
    // TODO(knorton): This should be changed to take a String URL.
    void onTabUrlChanged(JavaScriptObject object);
  }

  protected DevToolsTabUrlChangeEvent() {
  }

  public final ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(object) {
      listener.
          @com.google.gwt.chrome.crx.client.events.DevToolsTabUrlChangeEvent$Listener::onTabUrlChanged(Lcom/google/gwt/core/client/JavaScriptObject;)
          (object);
    }
    this.addListener(handle);
    return handle;
  }-*/;
}
