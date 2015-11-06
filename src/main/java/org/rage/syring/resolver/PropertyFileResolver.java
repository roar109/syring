package org.rage.syring.resolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.rage.syring.annotation.FileResolver;
import org.rage.syring.constant.Constants;
import org.rage.syring.resolver.util.ResolverHelper;

/**
 * PropertyFileResolver represents
 *
 * @since Aug 13, 2015
 *
 */
@FileResolver
public class PropertyFileResolver implements PropertyResolver {

	private final Map<String, String> propertiesMap = new HashMap<>();
	private final String propertyFile = System.getProperty(Constants.DEFAULT_PROJECT_FILE_NAME_PROPERTY);
	
	@Inject
	private ResolverHelper resolver;

	/**
	 * Read properties from file and store them in the global Properties object.
	 */
	private void init(final ClassLoader cl) {
		propertiesMap.clear();
		// if not try to load from system property
		final Properties properties = new Properties();

		try {
			final Properties fileProperties = resolver.readDefaultPropertiesFile(cl);

			final String propertiesVariableNames = String
					.valueOf(fileProperties.get(Constants.DEFAULT_VARIABLE_PROPERTY));

			if (propertiesVariableNames != null) {
				final String[] variableNames = propertiesVariableNames.split(",");
				for (final String variable : variableNames) {
					resolver.loadPropertiesFromFileName(System.getProperty(variable), cl, properties);
				}
			} else {
				tryToRetrieveFromFile(properties);
			}
		} catch (final IOException e) {
			System.err.println("Exception when try to load properties from file.");
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
		try {
			final File file = new File(propertyFile);
			properties.load(new FileInputStream(file));
		} catch (final Exception ex) {
			System.err.println("Could not load properties from properties file");
		}
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
	 * Represents getProperty
	 *
	 * @param key
	 * @return String
	 * @since Aug 13, 2015
	 *
	 */
	@Override
	public String getProperty(final String key, final ClassLoader cl) {
		checkIfPropertiesAreInitialized(cl);
		return propertiesMap.get(key);
	}
}
