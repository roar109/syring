package org.rage.syring.resolver;

import org.rage.syring.resolver.util.LoggerHelper;


/**
 * @author hector.mendoza
 */
public class FileJndiPropertyResolver extends AbstractProvider implements PropertyResolver {

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
		final String propertyValueFromFile = getPropertyFileResolver().getProperty(key, cl);
		
		if (propertyValueFromFile != null) {
			propertyValue = getJNDIResolver().getProperty(propertyValueFromFile, cl);
		}
		return propertyValue;
	}

}
