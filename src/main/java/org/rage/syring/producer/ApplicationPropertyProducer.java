package org.rage.syring.producer;

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
   public String getPropertyAsString (final InjectionPoint injectionPoint);
}
