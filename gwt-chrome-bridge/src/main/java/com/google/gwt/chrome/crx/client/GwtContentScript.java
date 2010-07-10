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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * {@link GwtContentScript} is a {@link ContentScript} the main difference is it
 * is generated by GWT compiler instead of simple JavaScript file. Details at <a
 * href="http://code.google.com/chrome/extensions/content_scripts.html" >Content
 * Scripts</a>
 */
public abstract class GwtContentScript implements Component {

	/**
	 * In the case of "document_start", the files are injected after any files
	 * from css, but before any other DOM is constructed or any other script is
	 * run.
	 */
	public static final String DOCUMENT_START = "document_start";

	/**
	 * In the case of "document_end", the files are injected immediately after
	 * the DOM is complete, but before subresources like images and frames have
	 * loaded.
	 */
	public static final String DOCUMENT_END = "document_end";

	/**
	 * In the case of "document_idle", the browser chooses a time to inject
	 * scripts between "document_end" and immediately after the window.onload
	 * event fires. The exact moment of injection depends on how complex the
	 * document is and how long it is taking to load, and is optimized for page
	 * load speed.
	 * 
	 * Note: With "document_idle", content scripts may not necessarily receive
	 * the window.onload event, because they may run after it has already fired.
	 * In most cases, listening for the onload event is unnecessary for content
	 * scripts running at "document_idle" because they are guaranteed to run
	 * after the DOM is complete. If your script definitely needs to run after
	 * window.onload, you can check if onload has already fired by using the
	 * document.readyState property.
	 */
	public static final String DOCUMENT_IDLE = "document_idle";

	/**
	 * {@link GwtContentScript} Specification annotation for defining the fields
	 * that go in the manifest.
	 */
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface ManifestInfo {

		/**
		 * Controls the pages this content script will be injected into. See <a
		 * href="http://code.google.com/chrome/extensions/match_patterns.html">
		 * Match Patterns</a> for more details on the syntax of these strings.
		 * 
		 * @return array of strings
		 */
		String[] whiteList();

		/**
		 * Controls when the files in js are injected. Can be
		 * <strong>document_start</strong>, <strong>document_end</strong>, or
		 * <strong>document_idle</strong>. Defaults to
		 * <strong>document_idle</strong>
		 * 
		 * @return
		 */
		String runAt();

		/**
		 * Controls whether the content script runs in all frames of the
		 * matching page, or only the top frame.
		 * 
		 * Defaults to false, meaning that only the top frame is matched.
		 * 
		 * @return
		 */
		boolean allFrames() default false;
	}
}
