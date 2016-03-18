package com.github.roar109.syring.producer;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import com.github.roar109.syring.annotation.ApplicationProperty;
import com.github.roar109.syring.annotation.ApplicationProperty.Types;
import com.github.roar109.syring.resolver.FileJndiPropertyResolver;
import com.github.roar109.syring.resolver.JNDIPropertyResolver;
import com.github.roar109.syring.resolver.PropertyFileResolver;
import com.github.roar109.syring.resolver.PropertyResolver;
import com.github.roar109.syring.resolver.factory.PropertyResolverFactory;
import com.github.roar109.syring.resolver.util.LoggerHelper;

/**
 * ApplicationPropertyProducerImpl
 * @author hector.mendoza, alberto.saito
 * @since Aug 13, 2015
 *
 */
public class ApplicationPropertyProducerImpl implements ApplicationPropertyProducer {

	private PropertyResolver propertyFileResolver = PropertyResolverFactory.instance().instanciateResolver(PropertyFileResolver.class);
	private PropertyResolver jndiPropertyResolver = PropertyResolverFactory.instance().instanciateResolver(JNDIPropertyResolver.class);
	private PropertyResolver fileJndiResolver = PropertyResolverFactory.instance().instanciateResolver(FileJndiPropertyResolver.class);

	public ApplicationPropertyProducerImpl(){
		LoggerHelper.log("ApplicationPropertyProducerImpl.new");
	}
	
	/**
	 * Represents getPropertyAsString
	 * Is used as a base from the other type methods
	 *
	 * @param injectionPoint
	 * @return String
	 * @since Aug 13, 2015
	 *
	 */
	@Override
	@Produces
	@ApplicationProperty(name = "", type = ApplicationProperty.Types.FILE)
	public String getPropertyAsString(final InjectionPoint injectionPoint) {
		final ClassLoader cl = injectionPoint.getMember().getDeclaringClass().getClassLoader();
		final String propertyName = injectionPoint.getAnnotated().getAnnotation(ApplicationProperty.class).name();
		final Types propertyType = injectionPoint.getAnnotated().getAnnotation(ApplicationProperty.class).type();
		String value = null;
		
		if (ApplicationProperty.Types.FILE == propertyType) {
			value = propertyFileResolver.getProperty(propertyName, cl);
		} else if (ApplicationProperty.Types.SYSTEM == propertyType) {
			value = System.getProperty(propertyName);
		} else if (ApplicationProperty.Types.JNDI == propertyType) {
			value = jndiPropertyResolver.getProperty(propertyName, cl);
		} else if (ApplicationProperty.Types.FILE_JNDI == propertyType) {
			value = fileJndiResolver.getProperty(propertyName, cl);
		}

		if ((value == null) || (propertyName.trim().length() == 0)) {
			System.err.println("No property found with name " + propertyName);
		}

		return value;
	}

	/**
	 * Inject the property value to a Integer field
	 * 
	 * @param injectionPoint
	 * @return Integer
	 */
	@Produces
	@ApplicationProperty(name = "", type = ApplicationProperty.Types.FILE, valueType=ApplicationProperty.ValueType.INTEGER)
	public Integer getPropertyAsInteger(final InjectionPoint injectionPoint) {
		return Integer.parseInt(getPropertyAsString(injectionPoint));
	}

	/**
	 * Inject the property value to a Long field
	 * 
	 * @param injectionPoint
	 * @return Long
	 */
	@Produces
	@ApplicationProperty(name = "", type = ApplicationProperty.Types.FILE, valueType=ApplicationProperty.ValueType.LONG)
	public Long getPropertyAsLong(InjectionPoint injectionPoint) {
		return Long.parseLong(getPropertyAsString(injectionPoint));
	}
	
	/**
	 * Inject the property value to a Double field
	 * 
	 * @param injectionPoint
	 * @return Double
	 */
	@Produces
	@ApplicationProperty(name = "", type = ApplicationProperty.Types.FILE, valueType=ApplicationProperty.ValueType.DOUBLE)
	public Double getPropertyAsDouble(InjectionPoint injectionPoint) {
		return Double.parseDouble(getPropertyAsString(injectionPoint));
	}

}
