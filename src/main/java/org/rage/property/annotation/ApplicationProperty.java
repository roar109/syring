package org.rage.property.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;


/**
 * ApplicationProperty represents ...
 *
 * @since Aug 13, 2015
 *
 */
@Qualifier
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface ApplicationProperty
{

   /**
    * Types represents ...
    *
    * @since Aug 13, 2015
    *
    */
   enum Types
   {
      /** Types for FILE */
      FILE,
      /** Types for SYSTEM */
      SYSTEM,
      /** Types for JNDI */
      JNDI,
      /** Types for FILE + JNDI */
      FILE_JNDI;

   }


   /**
    * no default meaning a value is mandatory
    */
   @Nonbinding
   String name ();


   /**
    * no default meaning a value is mandatory
    */
   @Nonbinding
   Types type ();

}


