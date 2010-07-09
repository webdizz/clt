/**
 * 
 */
package com.google.gwt.chrome.crx.linker;

/**
 * @author webdizz
 * 
 */
public enum UserType {

	ICON_USER_TYPE("com.google.gwt.chrome.crx.client.Icon");

	UserType(String type) {
		this.type = type;
	}

	private final String type;

	public String type() {
		return type;
	}
}
