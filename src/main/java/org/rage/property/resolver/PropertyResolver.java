package org.rage.property.resolver;

/**
 * @author hector.mendoza
 * */
public interface PropertyResolver {

    /**
     * Get a property value by key.
     * 
     * @param key
     * @return string
     * */
    public String getProperty (final String key, final ClassLoader cl);
}
