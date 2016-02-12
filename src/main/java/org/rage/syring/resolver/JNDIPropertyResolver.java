package org.rage.syring.resolver;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.rage.syring.constant.Constants;
import org.rage.syring.resolver.util.LoggerHelper;

/**
 * @author hector.mendoza, alberto.saito
 */
public class JNDIPropertyResolver  extends AbstractProvider implements PropertyResolver {
	
	@Override
	public String getProperty(final String key, final ClassLoader cl) {
		try {
			final InitialContext jndiContext = configureAndInstantiateInitialContext(cl);
			return (String) jndiContext.lookup(key);
		} catch (final NamingException e) {
			throw new IllegalStateException("JNDI provider not configured properly");
		}
	}

	private InitialContext configureAndInstantiateInitialContext(final ClassLoader cl) {
		try {
			final Properties properties = getResourceResolverHelper().readDefaultPropertiesFile(cl);
			properties.put(Context.INITIAL_CONTEXT_FACTORY,
					readPropertyFromDefaultFile(properties, Constants.DEFAULT_JNDI_CLASS_NAME_PROPERTY));
			properties.put(Context.PROVIDER_URL,
					readPropertyFromDefaultFile(properties, Constants.DEFAULT_JNDI_PROPERTY));
			// Create a context passing these properties
			return new InitialContext(properties);
		} catch (final NamingException e) {
			LoggerHelper.logError(e);
		}
		return null;
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

}
