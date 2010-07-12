/**
 * 
 */
package com.google.gwt.chrome.crx.linker;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.chrome.crx.linker.emiter.Emiter;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.JRealClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

/**
 * @author webdizz
 * 
 */
public class GwtContentScriptGeneratorTest {

	@Mock
	private TreeLogger logger;

	@Mock
	private GeneratorContext context;

	@Mock
	private JClassType userType;

	private static final String TYPE_NAME = Emiter.GWT_CONTENTSCRIPT_ENTRYPOINT_USER_TYPE;

	private static final String PATH = TYPE_NAME.replace('.', '_') + "_generated";

	private static final String PACKAGE_NAME = TYPE_NAME.substring(0, TYPE_NAME.lastIndexOf('.'));

	private JClassType[] interfaces;

	@Before
	public void setUp() throws NotFoundException {
		MockitoAnnotations.initMocks(this);
		interfaces = new JClassType[] { new JRealClassType(mock(TypeOracle.class), mock(JPackage.class),
				EntryPoint.class.getCanonicalName(), false, EntryPoint.class.getSimpleName(), true) };
		TypeOracle typeOracle = mock(TypeOracle.class);
		when(userType.getSimpleSourceName()).thenReturn(PATH);
		when(userType.getImplementedInterfaces()).thenReturn(interfaces);
		JPackage jPackage = mock(JPackage.class);
		when(jPackage.getName()).thenReturn(PACKAGE_NAME);
		when(userType.getPackage()).thenReturn(jPackage);
		when(typeOracle.getType(TYPE_NAME)).thenReturn(userType);
		when(context.getTypeOracle()).thenReturn(typeOracle);
	}

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionIfUnsupportedType() throws UnableToCompleteException,
			NotFoundException {

		TypeOracle typeOracle = mock(TypeOracle.class);
		when(typeOracle.getType(TYPE_NAME)).thenThrow(new NotFoundException());
		when(context.getTypeOracle()).thenReturn(typeOracle);

		invokeCodeGeneration();
	}

	protected String invokeCodeGeneration() throws UnableToCompleteException {
		Generator generator = new GwtContentScriptGenerator();
		return generator.generate(logger, context, TYPE_NAME);
	}
}
