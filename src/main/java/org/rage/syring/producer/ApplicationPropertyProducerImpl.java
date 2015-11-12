package org.rage.syring.producer;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.rage.syring.annotation.ApplicationProperty;
import org.rage.syring.annotation.FileJndiResolver;
import org.rage.syring.annotation.FileResolver;
import org.rage.syring.annotation.JndiResolver;
import org.rage.syring.annotation.ApplicationProperty.Types;
import org.rage.syring.resolver.PropertyResolver;

/**
 * ApplicationPropertyProducer represents ...
 *
 * @since Aug 13, 2015
 *
 */
public class ApplicationPropertyProducerImpl implements ApplicationPropertyProducer {

	@Inject
	@FileResolver
	private Instance<PropertyResolver> propertyFileResolver;

	@Inject
	@JndiResolver
	private Instance<PropertyResolver> jndiPropertyResolver;

	@Inject
	@FileJndiResolver
	private Instance<PropertyResolver> fileJndiResolver;

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
			value = propertyFileResolver.get().getProperty(propertyName, cl);
		} else if (ApplicationProperty.Types.SYSTEM == propertyType) {
			value = System.getProperty(propertyName);
		} else if (ApplicationProperty.Types.JNDI == propertyType) {
			value = jndiPropertyResolver.get().getProperty(propertyName, cl);
		} else if (ApplicationProperty.Types.FILE_JNDI == propertyType) {
			value = fileJndiResolver.get().getProperty(propertyName, cl);
		}

		if ((value == null) || (propertyName.trim().length() == 0)) {
			throw new IllegalArgumentException("No property found with name " + propertyName);
		}

		return value;
	}

	/**
	 * Inject the property value to a Integer field
	 * 
	 * @param injectionPoint
	 * @return Integer
	 */
	@Override
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
	@Override
	@Produces
	@ApplicationProperty(name = "", type = ApplicationProperty.Types.FILE, valueType=ApplicationProperty.ValueType.LONG)
	public Long getPropertyAsLong(InjectionPoint injectionPoint) {
		return Long.parseLong(getPropertyAsString(injectionPoint));
	}

}
