package org.rage.syring;


import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rage.syring.annotation.ApplicationProperty;
import org.rage.syring.producer.ApplicationPropertyProducerImpl;
import org.rage.syring.resolver.PropertyFileResolver;

@RunWith(CdiRunner.class)
@AdditionalClasses({ApplicationPropertyProducerImpl.class, PropertyFileResolver.class})
public class ApplicationPropertyProducerTest {

	public ApplicationPropertyProducerTest(){
		System.setProperty("my.properties.file.path", "mypropsfile.properties");
	}
	
	@Inject
	@ApplicationProperty(name="os.name",type=ApplicationProperty.Types.SYSTEM)
	private String systemProperty;
	
	@Inject
	@ApplicationProperty(name="myproperty",type=ApplicationProperty.Types.FILE)
	private String myProperty;
	
	@Inject
	@ApplicationProperty(name="my.int.value",type=ApplicationProperty.Types.FILE, valueType=ApplicationProperty.ValueType.INTEGER)
	private Integer myIntValue;
	
	@Inject
	@ApplicationProperty(name="my.long.value",type=ApplicationProperty.Types.FILE, valueType=ApplicationProperty.ValueType.LONG)
	private Long myLongValue;
	
	@Test
	public void isNotNull() {
		System.out.println("systemProperty="+systemProperty);
		Assert.assertNotNull(systemProperty);
	}
	
	@Test
	public void propertyFileIsNotNull(){
		System.out.println("myProperty="+myProperty);
		Assert.assertNotNull(myProperty);
		Assert.assertEquals("myvalue", myProperty);
	}
	
	@Test
	public void testInteger(){
		Assert.assertNotNull(myIntValue);
		Assert.assertEquals(new Integer(100), myIntValue);
	}
	
	@Test
	public void testLong(){
		Assert.assertNotNull(myLongValue);
		Assert.assertEquals(new Long(10011234), myLongValue);
	}
	
}
