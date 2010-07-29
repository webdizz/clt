/**
 * 
 */
package name.webdizz.clt.crx.client;

import name.webdizz.clt.crx.client.translation.medeniye.MedeniyeRequestBuilderTest;
import name.webdizz.clt.crx.client.translation.medeniye.MedeniyeTranslatorTest;
import name.webdizz.clt.crx.client.translation.medeniye.TranslationResultCreatorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author webdizz
 * 
 */
@RunWith(value = Suite.class)
@SuiteClasses(value = { 
	MedeniyeRequestBuilderTest.class, 
	MedeniyeTranslatorTest.class, 
	TranslationResultCreatorTest.class 
})
public class AllTests {

}
