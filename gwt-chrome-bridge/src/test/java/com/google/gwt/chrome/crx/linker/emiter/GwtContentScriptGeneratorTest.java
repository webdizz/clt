/**
 * 
 */
package com.google.gwt.chrome.crx.linker.emiter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.chrome.crx.client.GwtContentScript;
import com.google.gwt.chrome.crx.linker.GwtContentScriptGenerator;
import com.google.gwt.chrome.crx.linker.artifact.GwtContentScriptArtifact;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;
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

	@Mock
	private GwtContentScript.ManifestInfo spec;

	private static final String TYPE_NAME = Emiter.GWT_CONTENTSCRIPT_USER_TYPE;

	private static final String PATH = TYPE_NAME.replace('.', '_') + "_generated";

	private static final String PACKAGE_NAME = TYPE_NAME.substring(0, TYPE_NAME.lastIndexOf('.'));

	@Before
	public void setUp() throws NotFoundException {
		MockitoAnnotations.initMocks(this);

		TypeOracle typeOracle = mock(TypeOracle.class);
		when(userType.getAnnotation(GwtContentScript.ManifestInfo.class)).thenReturn(spec);
		when(userType.getSimpleSourceName()).thenReturn(PATH);
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

	@Test(expected = UnableToCompleteException.class)
	public void shouldThrowUnableToCompleteExceptionIfNoManifestAnnotation() throws UnableToCompleteException {
		when(userType.getAnnotation(GwtContentScript.ManifestInfo.class)).thenReturn(null);

		invokeCodeGeneration();
	}

	@Test
	public void shouldCreateArtifactFromSpec() throws UnableToCompleteException {
		invokeCodeGeneration();

		verify(spec).allFrames();
		verify(spec).runAt();
	}

	@Test
	public void shouldCommitCreatedArtifact() throws UnableToCompleteException {
		GwtContentScriptArtifact artifact;
		artifact = new GwtContentScriptArtifact(PATH, spec.matches(), spec.runAt(), spec.allFrames());

		invokeCodeGeneration();

		verify(context).commitArtifact(logger, artifact);
	}

//	@Test
//	public void shouldGenerateNameForCreatingArtifactType() throws UnableToCompleteException {
//
//		invokeCodeGeneration();
//
//		verify(userType, times(2)).getSimpleSourceName();
//	}
//
//	@Test
//	public void shouldResolvePackageName() throws UnableToCompleteException {
//		invokeCodeGeneration();
//
//		verify(userType).getPackage();
//	}

//	@Test
//	public void shouldObtainQualifiedSourceName() throws UnableToCompleteException {
//		invokeCodeGeneration();
//
//		verify(userType).getQualifiedSourceName();
//	}
//
//	@Test
//	public void shouldCalltryCreateOnContext() throws UnableToCompleteException {
//		invokeCodeGeneration();
//
//		verify(context).tryCreate(logger, PACKAGE_NAME, PATH + "_generated");
//	}
//
//	@Test
//	public void shouldReturnCreatedClassName() throws UnableToCompleteException {
//		String createdClass = invokeCodeGeneration();
//
//		assertEquals(PACKAGE_NAME + '.' + PATH + "_generated", createdClass);
//	}

	protected String invokeCodeGeneration() throws UnableToCompleteException {
		Generator generator = new GwtContentScriptGenerator();
		return generator.generate(logger, context, TYPE_NAME);
	}
}
