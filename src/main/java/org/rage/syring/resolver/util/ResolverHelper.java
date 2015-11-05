package org.rage.syring.resolver.util;

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
			System.err.println("ResolverHelper error: "+e.getMessage());
		}
		return properties;
	}
}
