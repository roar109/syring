package org.rage.syring.resolver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.rage.syring.constant.Constants;

/**
 * ResolverHelper represents ...
 *
 * @since Aug 26, 2015
 *
 */
public class ResolverHelper {

	/**
	 * 
	 * @param cl
	 * @return properties from the default file.
	 * 
	 */
	public Properties readDefaultPropertiesFile(ClassLoader cl) {
		final Properties properties = new Properties();

		try {
			URL url = null;
			final Enumeration<URL> em = cl.getResources(Constants.CDI_PROP_FILENAME);
			// Get all the resources, if you have shared dependencies it will
			// take as many as you have.
			// TODO Need to use a better solution for this
			while (em.hasMoreElements()) {
				url = em.nextElement();
			}
			final InputStream is = url.openStream();
			properties.load(is);
			is.close();
		} catch (final Exception e) {
			System.err.println("ResolverHelper error: " + e.getMessage());
		}
		return properties;
	}

	/**
	 * Try to load properties from the given filename first from a complete path
	 * then look for a file in the passed class loader.
	 * 
	 * @param fileName
	 * @param cl
	 * @param properties
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void loadPropertiesFromFileName(final String fileName, final ClassLoader cl, final Properties properties)
			throws FileNotFoundException, IOException {
		final File file = new File(fileName);
		if (file != null && file.exists()) {
			try (InputStream is = new FileInputStream(file)){
				properties.load(is);	
			}
		} else {
			final InputStream is = cl.getResourceAsStream(fileName);
			if (is != null) {
				properties.load(is);
			}
		}
	}
}
