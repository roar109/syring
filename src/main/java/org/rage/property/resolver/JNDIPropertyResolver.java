package org.rage.property.resolver;

import java.util.Properties;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.rage.property.annotation.JndiResolver;
import org.rage.property.constant.Constants;
import org.rage.property.resolver.util.ResolverHelper;

/**
 * @author hector.mendoza, alberto.saito
 */
@JndiResolver
public class JNDIPropertyResolver implements PropertyResolver {

	private InitialContext jndiContext = null;

	@Inject
	private ResolverHelper resolver;

	private void init(final ClassLoader cl) {
		try {
			final Properties properties = resolver.readDefaultPropertiesFile(cl);
			properties.put(Context.INITIAL_CONTEXT_FACTORY,
					readPropertyFromDefaultFile(properties, Constants.DEFAULT_JNDI_CLASS_NAME_PROPERTY));
			properties.put(Context.PROVIDER_URL,
					readPropertyFromDefaultFile(properties, Constants.DEFAULT_JNDI_PROPERTY));
			// Create a context passing these properties
			jndiContext = new InitialContext(properties);
		} catch (final NamingException e) {
			System.err.println("Error on cdi-property-resolver: " + e.getMessage());
		}
	}

	/**
	 *
	 * @param key
	 * @return value
	 *
	 */
	@Override
	public String getProperty(final String key, final ClassLoader cl) {
		try {
			init(cl);
			return (String) jndiContext.lookup(key);
		} catch (final NamingException e) {
			throw new IllegalStateException("JNDI provider not properly configured");
		}
	}

	private String readPropertyFromDefaultFile(final Properties properties, final String property) {
		String value = properties.getProperty(property);

		if (property.equals(Constants.DEFAULT_JNDI_PROPERTY)) {
			if ((value == null) || "".equals(value)) {
				value = System.getProperty(Constants.DEFAULT_JNDI_LOCATION);
			} else {
				value = System.getProperty(value);
			}
		}

		if (property.equals(Constants.DEFAULT_JNDI_CLASS_NAME_PROPERTY)) {
			if ((value == null) || "".equals(value)) {
				value = Constants.DEFAULT_JNDI_CLASS_NAME;
			}
		}

		return value;
	}

	/**
	 * @return the jndiContext
	 */
	public InitialContext getJndiContext() {
		return jndiContext;
	}

	/**
	 * @param value
	 *            the jndiContext to set
	 */
	public void setJndiContext(final InitialContext value) {
		this.jndiContext = value;
	}
}
