/**
 * 
 */
package name.webdizz.clt.crx.client.translation.medeniye;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import name.webdizz.clt.crx.client.translation.ITranslationHandler;
import name.webdizz.clt.crx.client.translation.TranslationResult;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

/**
 * @author webdizz
 * 
 */
public class MedeniyeRequestBuilderTest {

	private static final String RESPONSE_WITH_TRANS = "<dl>"
			+ "<div>Crimean Tatar - Russian</div>"
			+ "<dt><b>cik</b></dt>"
			+ "<dd style=\"margin:0 0 0 .5em\"><div style=\"\">1) пробор</div><div style=\"margin-left:.5em;color:#666;\">doğru cik - прямой пробор (посередине)</div><div style=\"margin-left:.5em;color:#666;\">qıya cik - косой пробор (боковой)</div>"
			+ "<div style=\"\">2) мн. стрелки на брюках</div></dd>" + "</dl>";

	private MedeniyeRequestBuilder medeniyeRequestBuilder;

	@Mock
	private Response response;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		medeniyeRequestBuilder = new MedeniyeRequestBuilder();
	}

	@Test(expected = UnsatisfiedLinkError.class)
	public void shouldCreateDefaultRequestBuilder() throws Exception {
		medeniyeRequestBuilder.send("some data", null);
	}

	@Test
	public void shouldUseAssignedRequestBuilder() throws Exception {
		ITranslationHandler handler = mock(ITranslationHandler.class);
		RequestBuilder requestBuilder = mock(RequestBuilder.class);
		medeniyeRequestBuilder.setRequestBuilder(requestBuilder);
		medeniyeRequestBuilder.send("some data", handler);

		verify(requestBuilder).send();
	}

	@Test
	public void shouldCallTranslationHandle() throws Exception {
		ITranslationHandler handler = mock(ITranslationHandler.class);
		RequestBuilder requestBuilder = new JustCallOnResponseReceivedRequestBuilder("URL");
		medeniyeRequestBuilder.setRequestBuilder(requestBuilder);

		when(response.getText()).thenReturn(RESPONSE_WITH_TRANS);

		medeniyeRequestBuilder.send("some data", handler);
		TranslationResult result = new TranslationResultCreator().parse(RESPONSE_WITH_TRANS);
		verify(handler).onTranslate(result);
	}

	@Test
	public void shouldHandleTranslationRequestError() throws Exception {
		ITranslationHandler handler = mock(ITranslationHandler.class);
		RequestBuilder requestBuilder = new JustCallOnErrorRequestBuilder("URL");
		medeniyeRequestBuilder.setRequestBuilder(requestBuilder);

		medeniyeRequestBuilder.send("some data", handler);
		verify(handler, never()).onTranslate(null);
	}

	class JustCallOnResponseReceivedRequestBuilder extends RequestBuilder {

		public JustCallOnResponseReceivedRequestBuilder(String url) {
			super(RequestBuilder.GET, url);
		}

		@Override
		public Request send() throws RequestException {
			Request request = null;
			getCallback().onResponseReceived(request, response);
			return null;
		}
	}

	class JustCallOnErrorRequestBuilder extends JustCallOnResponseReceivedRequestBuilder {

		public JustCallOnErrorRequestBuilder(String url) {
			super(url);
		}

		@Override
		public Request send() throws RequestException {
			Request request = null;
			getCallback().onError(request, new RequestException("error"));
			return null;
		}
	}
}
