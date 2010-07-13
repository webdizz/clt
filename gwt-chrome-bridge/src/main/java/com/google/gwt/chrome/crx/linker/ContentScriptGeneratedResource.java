/**
 * 
 */
package com.google.gwt.chrome.crx.linker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.GeneratedResource;
import com.google.gwt.core.ext.linker.impl.StandardLinkerContext;
import com.google.gwt.dev.util.DiskCache;

/**
 * @author webdizz
 * 
 */
public class ContentScriptGeneratedResource extends GeneratedResource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5009602681961304736L;

	private static final DiskCache diskCache = new DiskCache();

	private final long lastModified = System.currentTimeMillis();

	private transient long token;

	public ContentScriptGeneratedResource(Class<? extends Generator> generatorType, String partialPath, byte[] data) {
		super(StandardLinkerContext.class, generatorType, partialPath);
		this.token = diskCache.writeByteArray(data);
	}

	@Override
	public InputStream getContents(TreeLogger logger) throws UnableToCompleteException {
		return new ByteArrayInputStream(diskCache.readByteArray(token));
	}

	@Override
	public long getLastModified() {
		return lastModified;
	}

	@Override
	public void writeTo(TreeLogger logger, OutputStream out) throws UnableToCompleteException {
		diskCache.transferToStream(token, out);
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		token = diskCache.transferFromStream(stream);
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		diskCache.transferToStream(token, stream);
	}

}
