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
package com.google.gwt.chrome.crx.linker.artifact;

import com.google.gwt.chrome.crx.linker.ExtensionLinker;
import com.google.gwt.core.ext.linker.Artifact;

public class PageActionArtifact extends Artifact<PageActionArtifact> {
	private static final long serialVersionUID = 4771215346642678631L;

	private final String[] icons;
	private final String id;
	private final String name;
	private final String popup;

	public PageActionArtifact(String id, String name, String[] icons) {
		super(ExtensionLinker.class);
		this.id = id;
		this.name = name;
		this.icons = icons;
		this.popup = null;
	}

	public PageActionArtifact(String id, String name, String[] icons, String popup) {
		super(ExtensionLinker.class);
		this.id = id;
		this.name = name;
		this.icons = icons;
		this.popup = popup;
	}

	public String[] getIcons() {
		return icons;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPopup() {
		return popup;
	}

	@Override
	public int hashCode() {
		return icons.hashCode();
	}

	@Override
	protected int compareToComparableArtifact(PageActionArtifact o) {
		assert o != null;

		// Ordering doesn't really have meaning for us.
		return 0;
	}

	@Override
	protected Class<PageActionArtifact> getComparableArtifactType() {
		return PageActionArtifact.class;
	}
}
