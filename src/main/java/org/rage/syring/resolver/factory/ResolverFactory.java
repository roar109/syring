package org.rage.syring.resolver.factory;

import java.util.concurrent.ConcurrentHashMap;

import org.rage.syring.resolver.PropertyResolver;
import org.rage.syring.resolver.util.LoggerHelper;

public final class ResolverFactory {

	private static final ResolverFactory instance = new ResolverFactory();
	private final ConcurrentHashMap<String, PropertyResolver> cachePropResolverInstances = new ConcurrentHashMap<String, PropertyResolver>(5);

	private ResolverFactory() {	}

	public static ResolverFactory instance() {
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
