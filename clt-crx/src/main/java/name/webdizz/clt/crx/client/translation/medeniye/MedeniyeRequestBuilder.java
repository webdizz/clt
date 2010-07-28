/**
 * 
 */
package name.webdizz.clt.crx.client.translation.medeniye;

import name.webdizz.clt.crx.client.translation.TranslationHandler;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

/**
 * @author webdizz
 * 
 */
public class MedeniyeRequestBuilder {

	private static final String URL = "http://qlugat.appspot.com/lugat/get";

	private RequestBuilder requestBuilder;

	public MedeniyeRequestBuilder(final Method httpMethod) {
		requestBuilder = new RequestBuilder(httpMethod, URL);
	}

	public Request send(final String requestData, final TranslationHandler handler) throws RequestException {
		requestBuilder.setCallback(new RequestCallback() {

			public void onResponseReceived(final Request request, final Response response) {
				handler.onTranslate(null);
			}

			public void onError(final Request request, final Throwable exception) {

			}
		});
		return requestBuilder.send();
	}

}
