package com.github.roar109.syring.resolver;

/**
 * @author hector.mendoza, alberto.saito
 * */
public interface PropertyResolver {

    public String getProperty (final String key, final ClassLoader cl);
}
