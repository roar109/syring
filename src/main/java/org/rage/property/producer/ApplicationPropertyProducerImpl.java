package org.rage.property.producer;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.rage.property.annotation.ApplicationProperty;
import org.rage.property.annotation.ApplicationProperty.Types;
import org.rage.property.annotation.FileJndiResolver;
import org.rage.property.annotation.FileResolver;
import org.rage.property.annotation.JndiResolver;
import org.rage.property.resolver.PropertyResolver;

/**
 * ApplicationPropertyProducer represents ...
 *
 * @since Aug 13, 2015
 *
 */
public class ApplicationPropertyProducerImpl implements ApplicationPropertyProducer {

	@Inject
	@FileResolver
	private PropertyResolver propertyFileResolver;

	@Inject
	@JndiResolver
	private PropertyResolver jndiPropertyResolver;

	@Inject
	@FileJndiResolver
	private PropertyResolver fileJndiResolver;

	/**
	 * Represents getPropertyAsString
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
			throw new IllegalArgumentException("No property found with name " + propertyName);
		}

		return value;
	}

	/**
	 * @return the propertyFileResolver
	 */
	public PropertyResolver getPropertyFileResolver() {
		return propertyFileResolver;
	}

	/**
	 * @param value
	 *            the propertyFileResolver to set
	 */
	public void setPropertyFileResolver(final PropertyResolver value) {
		this.propertyFileResolver = value;
	}

	/**
	 * @return the jndiPropertyResolver
	 */
	public PropertyResolver getJndiPropertyResolver() {
		return jndiPropertyResolver;
	}

	/**
	 * @param value
	 *            the jndiPropertyResolver to set
	 */
	public void setJndiPropertyResolver(final PropertyResolver value) {
		this.jndiPropertyResolver = value;
	}

	/**
	 * @return the fileJndiResolver
	 */
	public PropertyResolver getFileJndiResolver() {
		return fileJndiResolver;
	}

	/**
	 * @param value
	 *            the fileJndiResolver to set
	 */
	public void setFileJndiResolver(final PropertyResolver value) {
		this.fileJndiResolver = value;
	}

}
