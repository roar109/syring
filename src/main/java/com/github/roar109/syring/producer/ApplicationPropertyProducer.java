package com.github.roar109.syring.producer;

import javax.enterprise.inject.spi.InjectionPoint;


/**
 * @author hector.mendoza, alberto.saito
 *
 */
public interface ApplicationPropertyProducer
{

   /**
    * Represents getPropertyAsString
    *
    * @param injectionPoint
    * @return String
    * @since Aug 20, 2015
    *
    */
   String getPropertyAsString (final InjectionPoint injectionPoint);
   Integer getPropertyAsInteger (final InjectionPoint injectionPoint);
   Long getPropertyAsLong(final InjectionPoint injectionPoint);
   Double getPropertyAsDouble(InjectionPoint injectionPoint);
}
