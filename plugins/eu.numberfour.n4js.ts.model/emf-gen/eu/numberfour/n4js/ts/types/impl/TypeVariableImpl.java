/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.ts.types.impl;

import eu.numberfour.n4js.ts.typeRefs.ParameterizedTypeRef;
import eu.numberfour.n4js.ts.typeRefs.TypeRef;

import eu.numberfour.n4js.ts.types.Type;
import eu.numberfour.n4js.ts.types.TypeVariable;
import eu.numberfour.n4js.ts.types.TypesPackage;

import java.lang.Iterable;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TypeVariableImpl#getDeclaredUpperBounds <em>Declared Upper Bounds</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeVariableImpl extends TypeImpl implements TypeVariable {
	/**
	 * The cached value of the '{@link #getDeclaredUpperBounds() <em>Declared Upper Bounds</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredUpperBounds()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterizedTypeRef> declaredUpperBounds;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TYPE_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParameterizedTypeRef> getDeclaredUpperBounds() {
		if (declaredUpperBounds == null) {
			declaredUpperBounds = new EObjectContainmentEList<ParameterizedTypeRef>(ParameterizedTypeRef.class, this, TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUNDS);
		}
		return declaredUpperBounds;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeVariable> getTypeVars() {
		return XcoreCollectionLiterals.<TypeVariable>emptyEList();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeAsString() {
		EList<ParameterizedTypeRef> _declaredUpperBounds = this.getDeclaredUpperBounds();
		return this.getTypeVariableAsString(_declaredUpperBounds);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeVariableAsString(final Collection<? extends TypeRef> upperBounds) {
		String _name = this.getName();
		String _xifexpression = null;
		boolean _isEmpty = upperBounds.isEmpty();
		boolean _not = (!_isEmpty);
		if (_not) {
			final Function1<TypeRef, String> _function = new Function1<TypeRef, String>() {
				public String apply(final TypeRef it) {
					return it.getTypeRefAsString();
				}
			};
			Iterable<String> _map = IterableExtensions.map(upperBounds, _function);
			String _join = IterableExtensions.join(_map, " & ");
			_xifexpression = (" extends " + _join);
		}
		else {
			_xifexpression = "";
		}
		return (_name + _xifexpression);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUNDS:
				return ((InternalEList<?>)getDeclaredUpperBounds()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUNDS:
				return getDeclaredUpperBounds();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUNDS:
				getDeclaredUpperBounds().clear();
				getDeclaredUpperBounds().addAll((Collection<? extends ParameterizedTypeRef>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUNDS:
				getDeclaredUpperBounds().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUNDS:
				return declaredUpperBounds != null && !declaredUpperBounds.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == Type.class) {
			switch (baseOperationID) {
				case TypesPackage.TYPE___GET_TYPE_VARS: return TypesPackage.TYPE_VARIABLE___GET_TYPE_VARS;
				case TypesPackage.TYPE___GET_TYPE_AS_STRING: return TypesPackage.TYPE_VARIABLE___GET_TYPE_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypesPackage.TYPE_VARIABLE___GET_TYPE_VARS:
				return getTypeVars();
			case TypesPackage.TYPE_VARIABLE___GET_TYPE_AS_STRING:
				return getTypeAsString();
			case TypesPackage.TYPE_VARIABLE___GET_TYPE_VARIABLE_AS_STRING__COLLECTION:
				return getTypeVariableAsString((Collection<? extends TypeRef>)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //TypeVariableImpl
