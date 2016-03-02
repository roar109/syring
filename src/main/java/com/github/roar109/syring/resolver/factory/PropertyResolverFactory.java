package com.github.roar109.syring.resolver.factory;

import java.util.concurrent.ConcurrentHashMap;

import com.github.roar109.syring.resolver.PropertyResolver;
import com.github.roar109.syring.resolver.util.LoggerHelper;

public final class PropertyResolverFactory {

	private static final PropertyResolverFactory instance = new PropertyResolverFactory();
	private final ConcurrentHashMap<String, PropertyResolver> cachePropResolverInstances = new ConcurrentHashMap<String, PropertyResolver>(
			5);

	private PropertyResolverFactory() {}

	public static PropertyResolverFactory instance() {
		return instance;
	}

	@SuppressWarnings({ "rawtypes" })
	public PropertyResolver instanciateResolver(Class clazz) {
		LoggerHelper.log("calling instanciateResolver with clazz " + clazz.getName());
		if (PropertyResolver.class.isAssignableFrom(clazz)) {
			if (cachePropResolverInstances.containsKey(clazz.getName())) {
				return cachePropResolverInstances.get(clazz.getName());
			}
			try {
				final PropertyResolver instance = (PropertyResolver) clazz.newInstance();
				LoggerHelper.log("Adding " + clazz.getName());
				cachePropResolverInstances.put(clazz.getName(), instance);
				return instance;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		throw new IllegalArgumentException("Class is not implementing PropertyResolver");
	}
}
