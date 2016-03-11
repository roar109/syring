package com.github.roar109.syring;

import java.lang.reflect.Member;

import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.github.roar109.syring.annotation.ApplicationProperty.Types;
import com.github.roar109.syring.annotation.ApplicationProperty.ValueType;
import com.github.roar109.syring.producer.ApplicationPropertyProducer;
import com.github.roar109.syring.producer.ApplicationPropertyProducerImpl;
import com.github.roar109.syring.util.ObjectFactory;
import com.github.roar109.syring.util.ObjectFactory.ApplicationPropertyBuilder;

public class ApplicationPropertyProducerNoCDITest extends EasyMockSupport {
	
	private final ApplicationPropertyProducer appPropertyProducer = new ApplicationPropertyProducerImpl();
	
	@Rule
	public EasyMockRule rule = new EasyMockRule(this);

	@Mock
	private InjectionPoint injectionPoint;

	@Mock
	private Member member;

	@Mock
	private Annotated annotated;

	private final ObjectFactory objectFactory = new ObjectFactory();

	public ApplicationPropertyProducerNoCDITest(){
		System.setProperty("my.properties.file.path", "mypropsfile.properties");
	}

	@Test
	public void testNoPropertyFound() {
		mockActions("random", null, null);
		replayAll();
		String value = appPropertyProducer.getPropertyAsString(injectionPoint);
		Assert.assertNull(value);
		verifyAll();
	}

	@Test
	public void testOSPropertyFound() {
		mockActions("os.version", null, null);
		replayAll();
		final String osVersion = appPropertyProducer.getPropertyAsString(injectionPoint);
		verifyAll();
		
		Assert.assertNotNull(osVersion);
	}

	@Test
	public void testDoubleVersion() {
		mockActions("java.specification.version", ValueType.DOUBLE, null);
		replayAll();
		final Double javaVersion = appPropertyProducer.getPropertyAsDouble(injectionPoint);
		verifyAll();
		
		Assert.assertNotNull(javaVersion);
		Assert.assertTrue(javaVersion > 1.5);
	}
	
	@Test
	public void testIntegerVersion() {
		mockActions("my.int.value", ValueType.INTEGER, Types.FILE);
		replayAll();
		final Integer randomIntNumber = appPropertyProducer.getPropertyAsInteger(injectionPoint);
		verifyAll();
		
		Assert.assertNotNull(randomIntNumber);
		Assert.assertTrue(randomIntNumber > 99);
	}
	
	@Test
	public void testLong() {
		mockActions("my.long.value", ValueType.LONG, Types.FILE);
		replayAll();
		final Long randomLongNumber = appPropertyProducer.getPropertyAsLong(injectionPoint);
		verifyAll();
		
		Assert.assertNotNull(randomLongNumber);
		Assert.assertTrue(randomLongNumber > 10011232);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void mockActions(final String propertyName, final ValueType valueType, final Types types) {
		final Class clazz = this.getClass();
		
		EasyMock.expect(member.getDeclaringClass()).andReturn(clazz);
		EasyMock.expect(injectionPoint.getMember()).andReturn(member);

		final ApplicationPropertyBuilder appPropertyBuilder = objectFactory.getApplicationPropertyBuilderInstance();
		appPropertyBuilder.addPropertyName(propertyName);
		if (valueType != null) {
			appPropertyBuilder.addValueTypes(valueType);
		}
		if (types != null) {
			appPropertyBuilder.addTypes(types);
		}
		//We call 2 times this next methods. See times() EasyMock method.
		EasyMock.expect(annotated.getAnnotation(EasyMock.anyObject(Class.class))).andReturn(appPropertyBuilder.build())
				.times(2);
		EasyMock.expect(injectionPoint.getAnnotated()).andReturn(annotated).times(2);
	}

}
