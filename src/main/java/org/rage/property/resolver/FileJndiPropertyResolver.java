package org.rage.property.resolver;

import javax.inject.Inject;

import org.rage.property.annotation.FileJndiResolver;
import org.rage.property.annotation.FileResolver;
import org.rage.property.annotation.JndiResolver;


/**
 * @author hector.mendoza
 */
@FileJndiResolver
public class FileJndiPropertyResolver implements PropertyResolver {

	@Inject
	@FileResolver
	private PropertyResolver fileResolver;

	@Inject
	@JndiResolver
	private PropertyResolver jndiPropertyResolver;

	/**
	 * Overrides getProperty
	 *
	 * @param key
	 * @return String
	 * @since Aug 26, 2015
	 * @see com.fitness.architecture.property.resolver.PropertyResolver#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty(final String key, final ClassLoader cl) {
		final String propertyValueFromFile = fileResolver.getProperty(key, cl);
		String propertyValue = null;
		if (propertyValueFromFile != null) {
			propertyValue = jndiPropertyResolver.getProperty(propertyValueFromFile, cl);
		}
		return propertyValue;
	}

	/**
	 * @return the fileResolver
	 */
	public PropertyResolver getFileResolver() {
		return fileResolver;
	}

	/**
	 * @param value
	 *            the fileResolver to set
	 */
	public void setFileResolver(final PropertyResolver value) {
		this.fileResolver = value;
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

}
