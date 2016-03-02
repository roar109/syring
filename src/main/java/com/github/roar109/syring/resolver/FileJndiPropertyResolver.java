package com.github.roar109.syring.resolver;

import com.github.roar109.syring.resolver.util.LoggerHelper;


/**
 * @author hector.mendoza
 */
public class FileJndiPropertyResolver extends AbstractProvider implements PropertyResolver {

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
