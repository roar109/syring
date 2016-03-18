package com.github.roar109.syring.resolver;

import com.github.roar109.syring.resolver.factory.PropertyResolverFactory;
import com.github.roar109.syring.resolver.util.ResourceResolverHelper;

/**
 * @author hector.mendoza
 * */
public abstract class AbstractProvider {
	
	protected PropertyResolver getFileJndiResolver(){
		return PropertyResolverFactory.instance().instanciateResolver(FileJndiPropertyResolver.class);
	}

	protected PropertyResolver getPropertyFileResolver(){
		return PropertyResolverFactory.instance().instanciateResolver(PropertyFileResolver.class);
	}
	
	protected PropertyResolver getJNDIResolver(){
		return PropertyResolverFactory.instance().instanciateResolver(JNDIPropertyResolver.class);
	}
	
	protected ResourceResolverHelper getResourceResolverHelper(){
		return ResourceResolverHelper.instance();
	}
}
