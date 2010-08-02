/**
 * 
 */
package name.webdizz.clt.crx.contentscript.client;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import name.webdizz.clt.crx.client.chrome.ChromePort;
import name.webdizz.clt.crx.client.event.message.SelectTextMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event.NativePreviewEvent;

/**
 * @author webdizz
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest({ NativeEvent.class, SelectTextMessage.class, SelectionProvider.class })
@SuppressStaticInitializationFor({ "com.google.gwt.dom.client.NativeEvent" })
public class ContentScriptEventHandlerTest {

	@Mock
	private ChromePort port;

	private NativeEvent event = PowerMockito.mock(NativeEvent.class);

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		PowerMockito.mockStatic(SelectionProvider.class);
		PowerMockito.when(SelectionProvider.getSelection()).thenReturn("word");
		PowerMockito.suppress(event.getClass().getMethod("equals", Object.class));
		PowerMockito.doReturn("mouseup").when(event).getType();
		PowerMockito.doReturn(true).when(event).getCtrlKey();
		PowerMockito.doReturn(true).when(event).getAltKey();
	}

	@Test
	public void shouldNotConsumeNotMouseupEvent() throws Exception {
		ContentScriptEventHandler eventHandler = new ContentScriptEventHandler(port);
		NativePreviewEvent previewEvent = mock(NativePreviewEvent.class);
		PowerMockito.doReturn("notamouseevent").when(event).getType();
		when(previewEvent.getNativeEvent()).thenReturn(event);

		eventHandler.onPreviewNativeEvent(previewEvent);
		verify(previewEvent, never()).consume();
	}

	@Test
	public void shouldNotConsumeEmptySelection() throws Exception {
		PowerMockito.when(SelectionProvider.getSelection()).thenReturn("");
		ContentScriptEventHandler eventHandler = new ContentScriptEventHandler(port);
		NativePreviewEvent previewEvent = mock(NativePreviewEvent.class);
		when(previewEvent.getNativeEvent()).thenReturn(event);

		eventHandler.onPreviewNativeEvent(previewEvent);
		verify(previewEvent, never()).consume();
	}

	@Test
	public void shouldConsumeSelection() throws Exception {
		ContentScriptEventHandler eventHandler = new ContentScriptEventHandler(port);
		NativePreviewEvent previewEvent = mock(NativePreviewEvent.class);
		when(previewEvent.getNativeEvent()).thenReturn(event);
		PowerMockito.mockStatic(SelectTextMessage.class);
		doNothing().when(port).postMessage(any(SelectTextMessage.class));

		eventHandler.onPreviewNativeEvent(previewEvent);
		verify(previewEvent).consume();
	}

}
