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

import com.google.gwt.chrome.crx.client.GwtContentScript;
import com.google.gwt.chrome.crx.linker.ExtensionLinker;
import com.google.gwt.core.ext.linker.Artifact;

/**
 * Artifacts for {@link GwtContentScript}.
 */
public class GwtContentScriptArtifact extends Artifact<GwtContentScriptArtifact> {
	private static final long serialVersionUID = -1216244540374320761L;

	private final boolean allFrames;
	private final String runAt;
	private final String[] matches;
	private final String path;

	public GwtContentScriptArtifact(final String path, final String[] matches, final String runAt,
			final boolean allFrames) {
		super(ExtensionLinker.class);
		this.matches = matches;
		this.runAt = runAt;
		this.allFrames = allFrames;
		this.path = path;
	}

	public String getRunAt() {
		return runAt;
	}

	public boolean isAllFrames() {
		return allFrames;
	}

	public String[] getMatches() {
		return matches;
	}

	public String getPath() {
		return path;
	}

	@Override
	public int hashCode() {
		return path.hashCode();
	}

	@Override
	protected int compareToComparableArtifact(GwtContentScriptArtifact o) {
		assert o != null;
		return path.compareTo(o.path);
	}

	@Override
	protected Class<GwtContentScriptArtifact> getComparableArtifactType() {
		return GwtContentScriptArtifact.class;
	}
}
