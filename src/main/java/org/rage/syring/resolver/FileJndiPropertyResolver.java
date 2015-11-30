package org.rage.syring.resolver;

import org.rage.syring.resolver.factory.ResolverFactory;
import org.rage.syring.resolver.util.LoggerHelper;


/**
 * @author hector.mendoza
 */
public class FileJndiPropertyResolver implements PropertyResolver {

	private PropertyResolver fileResolver = ResolverFactory.instance().instanciateResolver(PropertyFileResolver.class);
	private PropertyResolver jndiPropertyResolver = ResolverFactory.instance().instanciateResolver(JNDIPropertyResolver.class);

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
		LoggerHelper.log("FileJndiPropertyResolver.init");
		String propertyValue = null;
		final String propertyValueFromFile = fileResolver.getProperty(key, cl);
		
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
