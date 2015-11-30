package org.rage.syring.util;

import java.lang.annotation.Annotation;

import org.rage.syring.annotation.ApplicationProperty;
import org.rage.syring.annotation.ApplicationProperty.ValueType;
import org.rage.syring.annotation.ApplicationProperty.Types;

public class ObjectFactory {

	public ApplicationPropertyBuilder getApplicationPropertyBuilderInstance() {
		return new ApplicationPropertyBuilder();
	}

	public class ApplicationPropertyBuilder {
		private ValueType valueType = ValueType.STRING;
		private Types types = Types.SYSTEM;
		private String propertyName = "random";
		
		private ApplicationPropertyBuilder(){}

		public ApplicationPropertyBuilder addTypes(final Types types) {
			this.types = types;
			return this;
		}

		public ApplicationPropertyBuilder addValueTypes(final ValueType valueType) {
			this.valueType = valueType;
			return this;
		}

		public ApplicationPropertyBuilder addPropertyName(final String propertyName) {
			this.propertyName = propertyName;
			return this;
		}

		public ApplicationProperty build() {
			final ApplicationProperty applicationProperty = new ApplicationProperty() {

				@Override
				public Class<? extends Annotation> annotationType() {
					return null;
				}

				@Override
				public String name() {
					return propertyName;
				}

				@Override
				public Types type() {
					return types;
				}

				@Override
				public ValueType valueType() {
					return valueType;
				}

			};
			return applicationProperty;
		}

	}
}
