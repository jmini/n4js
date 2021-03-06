/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package eu.numberfour.n4js.ui.wizard.classwizard;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import eu.numberfour.n4js.ui.wizard.classwizard.N4JSClassWizardModel.AccessModifier;
import eu.numberfour.n4js.ui.wizard.classwizard.N4JSClassWizardModel.ClassifierReference;

/**
 * Converters for {@link N4JSNewClassWizardPage}s Databinding
 */
public class N4JSNewClassWizardConverters {

	private static abstract class StrategyProvidingConverter extends Converter {
		/**
		 * @param fromType
		 *            source type
		 * @param toType
		 *            target type
		 */
		public StrategyProvidingConverter(Object fromType, Object toType) {
			super(fromType, toType);
			// TODO Auto-generated constructor stub
		}

		public UpdateValueStrategy updatingValueStrategy() {
			UpdateValueStrategy strat = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
			strat.setConverter(this);
			return strat;
		}
	}

	/**
	 * Converter which just adds a slash in the front of given string.
	 * <p>
	 * Note: Empty strings are passed through without addition of the a slash
	 * </p>
	 */
	public static class AddSlashConverter extends StrategyProvidingConverter {

		/**
		 */
		public AddSlashConverter() {
			super(String.class, String.class);
		}

		@Override
		public Object convert(Object fromObject) {
			if (fromObject instanceof String) {
				if (!((String) fromObject).equals("")) {
					return "/" + fromObject;
				}
				return "";
			}
			return null;
		}

	}

	/**
	 * Converts a String to an IPath
	 */
	public static class StringToPathConverter extends StrategyProvidingConverter {

		/**
		 *
		 */
		public StringToPathConverter() {
			super(String.class, IPath.class);
		}

		@Override
		public Object convert(Object fromObject) {
			if (fromObject instanceof String) {
				return new Path(((String) fromObject));
			}
			return null;
		}

	}

	/**
	 * Converts a String to a ClassifierReference leaving the URI field empty.
	 */
	public static class StringToClassifierReferenceConverter extends StrategyProvidingConverter {

		/**
		 *
		 */
		public StringToClassifierReferenceConverter() {
			super(String.class, ClassifierReference.class);
		}

		@Override
		public Object convert(Object fromObject) {
			if (fromObject instanceof String) {
				String str = (String) fromObject;
				return new ClassifierReference(N4JSNewClassWizardUtils.frontDotSegments(str),
						N4JSNewClassWizardUtils.lastDotSegment(str));
			}
			return null;
		}

	}

	/**
	 * Converts a ClassifierReference to a String using its absolute specifier.
	 */
	public static class ClassifierReferenceToStringConverter extends StrategyProvidingConverter {

		/***/
		public ClassifierReferenceToStringConverter() {
			super(ClassifierReference.class, String.class);
		}

		@Override
		public Object convert(Object fromObject) {
			if (fromObject instanceof ClassifierReference) {
				ClassifierReference ref = (ClassifierReference) fromObject;
				return ref.getFullSpecifier();
			}
			return null;
		}

	}

	/**
	 * Converts an arbitrary object to a boolean value depending on the outcome of the abstract validate method.
	 */
	public static abstract class ConditionalConverter extends StrategyProvidingConverter {

		/**
		 */
		public ConditionalConverter() {
			super(Object.class, boolean.class);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Object convert(Object fromObject) {
			if (validate(fromObject)) {
				return true;
			}
			return false;
		}

		/**
		 * Return true if condition is met
		 *
		 * @param object
		 *            Object to examine
		 * @return true on fulfillment
		 */
		public abstract boolean validate(Object object);

	}

	/**
	 * Converts a {@link AccessModifier} value to a boolean value. Converts to true if the access modifier is project or
	 * public, false otherwise.
	 *
	 * <p>
	 * Note: Although this converter does not convert any data value it is used to control the enabled state of a
	 * checkbox which depends on the selected access modifier.
	 * </p>
	 */
	public static class AccessModifierIsVisibleConverter extends StrategyProvidingConverter {

		/**
		 *
		 */
		public AccessModifierIsVisibleConverter() {
			super(AccessModifier.class, boolean.class);
		}

		@Override
		public Object convert(Object fromObject) {
			if (fromObject instanceof AccessModifier) {
				if (((AccessModifier) fromObject) == AccessModifier.PROJECT ||
						((AccessModifier) fromObject) == AccessModifier.PUBLIC) {
					return true;
				} else {
					return false;
				}
			}
			return null;
		}

	}
}
