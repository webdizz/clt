/**
 * 
 */
package com.google.gwt.chrome.crx.client;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link ExtensionScript} is a peace of <code>head</code> HTML tag within
 * extension background page. Allows to load external scripts.
 * 
 * @author Izzet_Mustafayev
 * 
 */
public class ExtensionScript implements Component {

	/**
	 * ExtensionScript Specification annotation for defining the fields that go
	 * in the manifest.
	 */
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface ManifestInfo {

		/**
		 * The path to JavaScript file.
		 * 
		 * @return
		 */
		String path() default "";

		String script() default "";

	}
}
