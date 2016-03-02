package com.github.roar109.syring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * ApplicationProperty represents ...
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
public @interface ApplicationProperty {

	/**
	 * Types of property retrievals available
	 *
	 */
	enum Types {
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
	 * Types of the property to inject
	 * 
	 */
	enum ValueType {
		/** Types for STRING */
		STRING, 
		/** Types for INTEGER */
		INTEGER,
		/** Types for LONG */
		LONG,
		/** Types for DOUBLE */
		DOUBLE;
	}

	/**
	 * no default meaning a value is mandatory
	 */
	@Nonbinding
	String name();

	/**
	 * no default meaning a value is mandatory
	 */
	@Nonbinding
	Types type();

	/**
	 * use ValueType.STRING as default
	 */
	ValueType valueType() default ValueType.STRING;

}
