package org.rage.property.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;


/**
 * JndiResolver represents ...
 *
 * @since Aug 18, 2015
 *
 */
@Qualifier
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.FIELD, ElementType.TYPE})
public @interface JndiResolver
{
   // Mark Interface

}
