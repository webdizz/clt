/**
 * 
 */
package name.webdizz.clt.crx.client.translation.medeniye;

import name.webdizz.clt.crx.client.translation.TranslationException;
import name.webdizz.clt.crx.client.translation.TranslationHandler;
import name.webdizz.clt.crx.client.translation.Translator;

import com.google.gwt.http.client.RequestException;

/**
 * @author webdizz
 * 
 */
public class MedeniyeTranslator implements Translator {

	private MedeniyeRequestBuilder requestBuilder;

	public MedeniyeTranslator(final MedeniyeRequestBuilder builder) {
		requestBuilder = builder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * name.webdizz.clt.crx.client.translation.Translator#translate(java.lang
	 * .String, name.webdizz.clt.crx.client.translation.TranslationHandler)
	 */
	public void translate(final String word, final TranslationHandler handler) throws TranslationException {
		if (null == requestBuilder) {
			throw new TranslationException("RequestBuilder is null.");
		}
		if (null != word && word.trim().length() > 0) {
			try {
				String requestData = "word=" + word;
				requestBuilder.send(requestData, handler);
			} catch (RequestException exc) {
				throw new TranslationException(exc.getMessage(), exc);
			}
		}
	}

}
