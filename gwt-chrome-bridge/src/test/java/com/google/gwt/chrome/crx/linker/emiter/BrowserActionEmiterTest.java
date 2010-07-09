/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.chrome.crx.client.BrowserAction;
import com.google.gwt.chrome.crx.client.Icon;
import com.google.gwt.chrome.crx.linker.UserType;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.JType;

/**
 * @author webdizz
 * 
 */
public class BrowserActionEmiterTest {

	@Mock
	private TreeLogger logger;

	@Mock
	private GeneratorContext context;

	@Mock
	private JClassType userType;

	private static final String CLASS_NAME = "BrowserAction";
	private static final String TYPE_NAME = "com.google.gwt.chrome." + CLASS_NAME;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		when(userType.getSimpleSourceName()).thenReturn(TYPE_NAME);
		JPackage jpackage = mock(JPackage.class);
		when(userType.getPackage()).thenReturn(jpackage);
		BrowserAction.ManifestInfo spec = mock(BrowserAction.ManifestInfo.class);
		when(userType.getAnnotation(BrowserAction.ManifestInfo.class)).thenReturn(spec);
	}

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionIfNoSpec() throws UnableToCompleteException {
		when(userType.getAnnotation(BrowserAction.ManifestInfo.class)).thenReturn(null);

		Emiter emiter = new BrowserActionEmiter();
		emiter.emit(logger, context, userType, TYPE_NAME);
	}

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionIfNoMethodReturningIcon() throws UnableToCompleteException {
		JMethod jmethod = mock(JMethod.class);
		JType jtype = mock(JType.class);
		when(jtype.getQualifiedSourceName()).thenReturn("SomeOtherType");

		when(jmethod.getReturnType()).thenReturn(jtype);
		when(userType.getMethods()).thenReturn(new JMethod[] { jmethod });

		Emiter emiter = new BrowserActionEmiter();
		emiter.emit(logger, context, userType, TYPE_NAME);
	}

	@Test
	public void shouldResolveIconFromMethodNameIfIconMethodWithoutSourceAnnotation() throws UnableToCompleteException {
		JMethod jmethod = mock(JMethod.class);
		JType jtype = mock(JType.class);
		when(jtype.getQualifiedSourceName()).thenReturn(UserType.ICON_USER_TYPE.type());
		when(jmethod.getReturnType()).thenReturn(jtype);
		when(userType.getMethods()).thenReturn(new JMethod[] { jmethod });

		Emiter emiter = new BrowserActionEmiter();
		emiter.emit(logger, context, userType, TYPE_NAME);
		verify(jmethod, times(2)).getName();
	}

	@Test
	public void shouldResolveIconFromMethodSourceAnnotation() throws UnableToCompleteException {
		JMethod jmethod = mock(JMethod.class);
		JType jtype = mock(JType.class);
		when(jtype.getQualifiedSourceName()).thenReturn(UserType.ICON_USER_TYPE.type());
		Icon.Source iconSource = mock(Icon.Source.class);
		when(jmethod.getAnnotation(Icon.Source.class)).thenReturn(iconSource);
		when(jmethod.getReturnType()).thenReturn(jtype);
		when(userType.getMethods()).thenReturn(new JMethod[] { jmethod });

		Emiter emiter = new BrowserActionEmiter();
		emiter.emit(logger, context, userType, TYPE_NAME);
		verify(iconSource).value();
		verify(jmethod, times(1)).getName();
	}

	@Test
	public void shouldCreateProperWriter() throws UnableToCompleteException {
		JMethod jmethod = mock(JMethod.class);
		JType jtype = mock(JType.class);
		when(jtype.getQualifiedSourceName()).thenReturn(UserType.ICON_USER_TYPE.type());
		when(jtype.getSimpleSourceName()).thenReturn(TYPE_NAME);
		when(jmethod.getReturnType()).thenReturn(jtype);
		when(userType.getMethods()).thenReturn(new JMethod[] { jmethod });
		
		JPackage jpackage = mock(JPackage.class);
		when(jpackage.getName()).thenReturn(TYPE_NAME);
		when(userType.getPackage()).thenReturn(jpackage);

		Icon.Source iconSource = mock(Icon.Source.class);
		when(jmethod.getAnnotation(Icon.Source.class)).thenReturn(iconSource);
		
		Emiter emiter = new BrowserActionEmiter();
		emiter.emit(logger, context, userType, TYPE_NAME);
		verify(context).tryCreate(logger, TYPE_NAME, TYPE_NAME.replace(".", "_") + "_generated");
	}

}
