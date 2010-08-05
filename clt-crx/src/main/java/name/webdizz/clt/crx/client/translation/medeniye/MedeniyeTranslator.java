/**
 * 
 */
package name.webdizz.clt.crx.client.translation.medeniye;

import name.webdizz.clt.crx.client.translation.TranslationException;
import name.webdizz.clt.crx.client.translation.ITranslationHandler;
import name.webdizz.clt.crx.client.translation.ITranslator;

import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;

/**
 * @author webdizz
 * 
 */
public class MedeniyeTranslator implements ITranslator {

	private MedeniyeRequestBuilder requestBuilder;

	/**
	 * Creates translator for Medeniye.org dictionary.
	 * 
	 * @param builder
	 *            the {@link MedeniyeRequestBuilder}
	 */
	public MedeniyeTranslator(final MedeniyeRequestBuilder builder) {
		requestBuilder = builder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * name.webdizz.clt.crx.client.translation.ITranslator#translate(java.lang
	 * .String, name.webdizz.clt.crx.client.translation.ITranslationHandler)
	 */
	public void translate(final String word, final ITranslationHandler handler) throws TranslationException {
		if (null == requestBuilder) {
			throw new TranslationException("RequestBuilder is null.");
		}
		if (null != word && word.trim().length() > 0) {
			try {
				String requestData = URL.encode("word") + "=" + URL.encode(word);
				requestBuilder.send(requestData, handler);
			} catch (RequestException exc) {
				throw new TranslationException(exc.getMessage(), exc);
			}
		}
	}

}
