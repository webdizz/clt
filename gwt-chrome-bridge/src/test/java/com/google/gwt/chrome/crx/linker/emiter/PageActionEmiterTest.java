/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.chrome.crx.client.Icon;
import com.google.gwt.chrome.crx.client.PageAction;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author webdizz
 * 
 */
public class PageActionEmiterTest {

	@Mock
	private TreeLogger logger;

	@Mock
	private GeneratorContext context;

	@Mock
	private JClassType userType;

	private static final String TYPE_NAME = "com.google.gwt.chrome.PageAction";
	private static final String SUBPACKEG_NAME = TYPE_NAME.replace(".", "_") + "_generated";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		when(userType.getSimpleSourceName()).thenReturn(TYPE_NAME);
		JPackage jpackage = mock(JPackage.class);
		when(userType.getPackage()).thenReturn(jpackage);
		PageAction.ManifestInfo spec = mock(PageAction.ManifestInfo.class);
		when(spec.name()).thenReturn("actionName");
		when(userType.getAnnotation(PageAction.ManifestInfo.class)).thenReturn(spec);
	}

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionIfNoSpec() throws UnableToCompleteException {
		when(userType.getAnnotation(PageAction.ManifestInfo.class)).thenReturn(null);

		invokeCodeEmition();
	}

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionIfNoMethodReturningIcon() throws UnableToCompleteException {
		mockIconMethodsWithoutIcon("SomeOtherType");

		invokeCodeEmition();
	}

	@Test
	public void shouldResolveIconFromMethodNameIfIconMethodWithoutSourceAnnotation() throws UnableToCompleteException {
		JMethod jmethod = mockIconMethodsWithoutIcon(Emiter.ICON_USER_TYPE);

		invokeCodeEmition();
		verify(jmethod, times(2)).getName();
	}

	@Test
	public void shouldResolveIconFromMethodSourceAnnotation() throws UnableToCompleteException {
		JMethod jmethod = mockIconMethodsWithoutIcon(Emiter.ICON_USER_TYPE);

		Icon.Source iconSource = mock(Icon.Source.class);
		when(jmethod.getAnnotation(Icon.Source.class)).thenReturn(iconSource);

		invokeCodeEmition();
		verify(iconSource).value();
		verify(jmethod, times(1)).getName();
	}

	@Test
	public void shouldCreateNotAPopupPageAction() throws UnableToCompleteException {
		mockIconMethodsWithIcon();
		mockJPackage();
		// remock spec
		PageAction.ManifestInfo spec = mock(PageAction.ManifestInfo.class);
		when(spec.name()).thenReturn("actionName");
		when(spec.popup()).thenReturn("");
		when(userType.getAnnotation(PageAction.ManifestInfo.class)).thenReturn(spec);

		invokeCodeEmition();
		verify(spec, times(2)).popup();
	}

	@Test
	public void shouldCreateAPopupPageAction() throws UnableToCompleteException {
		mockIconMethodsWithIcon();
		mockJPackage();
		// remock spec
		PageAction.ManifestInfo spec = mock(PageAction.ManifestInfo.class);
		when(spec.name()).thenReturn("actionName");
		when(spec.popup()).thenReturn("popup");
		when(userType.getAnnotation(PageAction.ManifestInfo.class)).thenReturn(spec);

		invokeCodeEmition();
		verify(spec, times(3)).popup();
	}

	@Test
	public void shouldTryToCreateProperWriter() throws UnableToCompleteException {
		mockIconMethodsWithIcon();
		mockJPackage();

		invokeCodeEmition();
		verify(context).tryCreate(logger, TYPE_NAME, SUBPACKEG_NAME);
	}

	@Test
	public void shouldCreateProperWriter() throws UnableToCompleteException {
		mockIconMethodsWithIcon();
		mockJPackage();

		final ClassSourceFileComposerFactory sourceFileComposerFactoryImpl = new ClassSourceFileComposerFactory(
				TYPE_NAME, SUBPACKEG_NAME);

		StringWriter stringWriter = new StringWriter();
		SourceWriter sw = sourceFileComposerFactoryImpl.createSourceWriter(context, new PrintWriter(stringWriter));
		PrintWriter pw = new PrintWriter(stringWriter);

		ClassSourceFileComposerFactory sourceFileComposerFactoryMock = mock(ClassSourceFileComposerFactory.class);

		when(sourceFileComposerFactoryMock.createSourceWriter(context, pw)).thenReturn(sw);

		when(context.tryCreate(logger, TYPE_NAME, SUBPACKEG_NAME)).thenReturn(pw);

		assertEquals(TYPE_NAME + '.' + SUBPACKEG_NAME, invokeCodeEmition());
	}

	private JMethod mockIconMethodsWithoutIcon(String qualifiedSourceName) {
		JMethod jmethod = mock(JMethod.class);
		JType jtype = mock(JType.class);
		when(jtype.getQualifiedSourceName()).thenReturn(qualifiedSourceName);
		when(jmethod.getReturnType()).thenReturn(jtype);
		when(userType.getMethods()).thenReturn(new JMethod[] { jmethod });
		return jmethod;
	}

	private void mockIconMethodsWithIcon() {
		JMethod jmethod = mock(JMethod.class);
		JType jtype = mock(JType.class);
		when(jtype.getQualifiedSourceName()).thenReturn(Emiter.ICON_USER_TYPE);
		when(jtype.getSimpleSourceName()).thenReturn(TYPE_NAME);
		when(jmethod.getReturnType()).thenReturn(jtype);
		when(jmethod.getName()).thenReturn("methodName");
		Icon.Source iconSource = mock(Icon.Source.class);
		when(iconSource.value()).thenReturn("filename");
		when(jmethod.getAnnotation(Icon.Source.class)).thenReturn(iconSource);
		when(userType.getMethods()).thenReturn(new JMethod[] { jmethod });
	}

	private void mockJPackage() {
		JPackage jpackage = mock(JPackage.class);
		when(jpackage.getName()).thenReturn(TYPE_NAME);
		when(userType.getPackage()).thenReturn(jpackage);
	}

	protected String invokeCodeEmition() throws UnableToCompleteException {
		Emiter emiter = new PageActionEmiter();
		return emiter.emit(logger, context, userType, TYPE_NAME);
	}
}
