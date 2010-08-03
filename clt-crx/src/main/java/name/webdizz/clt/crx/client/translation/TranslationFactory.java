/**
 * 
 */
package name.webdizz.clt.crx.client.translation;

import name.webdizz.clt.crx.client.translation.medeniye.MedeniyeRequestBuilder;
import name.webdizz.clt.crx.client.translation.medeniye.MedeniyeTranslator;

/**
 * @author webdizz
 * 
 */
public final class TranslationFactory {

	protected TranslationFactory() {
	}

	public static ITranslator instance() {
		return new MedeniyeTranslator(new MedeniyeRequestBuilder());
	}
}
