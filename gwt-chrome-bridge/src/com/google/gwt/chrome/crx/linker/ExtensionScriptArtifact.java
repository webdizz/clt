package com.google.gwt.chrome.crx.linker;

import com.google.gwt.core.ext.linker.Artifact;

/**
 * Artifacts for ExtensionScripts.
 */
public class ExtensionScriptArtifact extends Artifact<ExtensionScriptArtifact> {
	private static final long serialVersionUID = -12162576874320761L;

	private final String path;

	private final String script;

	public ExtensionScriptArtifact(String path, String script) {
		super(ExtensionLinker.class);
		this.path = path;
		this.script = script;
	}

	public String getPath() {
		return path;
	}

	public String getScript() {
		return script;
	}

	@Override
	public int hashCode() {
		return path.hashCode();
	}

	@Override
	protected int compareToComparableArtifact(ExtensionScriptArtifact o) {
		assert o != null;
		return path.compareTo(o.path);
	}

	@Override
	protected Class<ExtensionScriptArtifact> getComparableArtifactType() {
		return ExtensionScriptArtifact.class;
	}
}
