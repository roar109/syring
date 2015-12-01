package org.rage.syring.resolver;

import org.rage.syring.resolver.factory.PropertyResolverFactory;
import org.rage.syring.resolver.util.ResourceResolverHelper;

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
