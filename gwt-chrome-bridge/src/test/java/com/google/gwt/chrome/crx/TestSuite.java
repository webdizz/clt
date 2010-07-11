/**
 * 
 */
package com.google.gwt.chrome.crx;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.google.gwt.chrome.crx.linker.GwtContentScriptGeneratorTest;
import com.google.gwt.chrome.crx.linker.GwtContentScriptLinkerTest;
import com.google.gwt.chrome.crx.linker.emiter.BrowserActionEmiterTest;
import com.google.gwt.chrome.crx.linker.emiter.ContentScriptEmiterTest;
import com.google.gwt.chrome.crx.linker.emiter.GwtContentScriptEmiterTest;
import com.google.gwt.chrome.crx.linker.emiter.PageActionEmiterTest;
import com.google.gwt.chrome.crx.linker.emiter.ValidatorTest;

/**
 * @author webdizz
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  BrowserActionEmiterTest.class,
  PageActionEmiterTest.class,
  ValidatorTest.class,
  GwtContentScriptEmiterTest.class,
  ContentScriptEmiterTest.class,
  GwtContentScriptGeneratorTest.class,
  GwtContentScriptLinkerTest.class
})
public class TestSuite {

}
