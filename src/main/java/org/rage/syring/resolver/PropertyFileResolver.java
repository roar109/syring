package org.rage.syring.resolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.rage.syring.constant.Constants;
import org.rage.syring.resolver.util.LoggerHelper;

/**
 * PropertyFileResolver represents
 *
 * @since Aug 13, 2015
 *
 */
public class PropertyFileResolver extends AbstractProvider implements PropertyResolver {

	private final ConcurrentHashMap<String, String> propertiesMap = new ConcurrentHashMap<>();
	private final String propertyFile = System.getProperty(Constants.DEFAULT_PROJECT_FILE_NAME_PROPERTY);

	/**
	 * Represents getProperty
	 *
	 * @param key
	 * @return String
	 * @since Aug 13, 2015
	 *
	 */
	@Override
	public String getProperty(final String key, final ClassLoader cl) {
		LoggerHelper.log("PropertyFileResolver.getProperty");
		checkIfPropertiesAreInitialized(cl);
		return propertiesMap.get(key);
	}
	
	/**
	 * Validate that the properties loading ocure only 1 time - or only if the
	 * file have properties.
	 *
	 */
	private void checkIfPropertiesAreInitialized(ClassLoader cl) {
		if (propertiesMap.size() == 0) {
			init(cl);
		}
	}
	
	/**
	 * Read properties from file and store them in the global Properties object.
	 */
	private void init(final ClassLoader cl) {
		LoggerHelper.log("PropertyFileResolver.init");
		propertiesMap.clear();

		final Properties properties = new Properties();

		try {
			final Properties fileProperties = getResourceResolverHelper().readDefaultPropertiesFile(cl);

			final String propertiesVariableNames = String
					.valueOf(fileProperties.get(Constants.DEFAULT_VARIABLE_PROPERTY));

			if (!"null".equals(propertiesVariableNames)) {
				final String[] variableNames = propertiesVariableNames.split(",");
				for (final String variable : variableNames) {
					getResourceResolverHelper().loadPropertiesFromFileName(System.getProperty(variable), cl, properties);
				}
			} else {
				tryToRetrieveFromFile(properties);
			}
		} catch (final IOException e) {
			LoggerHelper.logError(e);
			tryToRetrieveFromFile(properties);
		}

		for (final String name : properties.stringPropertyNames()) {
			propertiesMap.put(name, properties.getProperty(name));
		}
	}

	/**
	 * Try to retrieve properties from a default variable This variable has the
	 * path to a valid properties file.
	 * 
	 * @param properties
	 */
	private void tryToRetrieveFromFile(Properties properties) {
		try (InputStream is = new FileInputStream(new File(propertyFile))) {
			properties.load(is);
		} catch (final Exception ex) {
			LoggerHelper.logError(ex);
		}
	}

}
