/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.ts.typeRefs.impl;

import eu.numberfour.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import eu.numberfour.n4js.ts.typeRefs.TypeArgument;
import eu.numberfour.n4js.ts.typeRefs.TypeRef;
import eu.numberfour.n4js.ts.typeRefs.TypeRefsPackage;

import eu.numberfour.n4js.ts.types.TFormalParameter;
import eu.numberfour.n4js.ts.types.TFunction;
import eu.numberfour.n4js.ts.types.Type;
import eu.numberfour.n4js.ts.types.TypeVariable;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Type Expr Or Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class FunctionTypeExprOrRefImpl extends StaticBaseTypeRefImpl implements FunctionTypeExprOrRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionTypeExprOrRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.FUNCTION_TYPE_EXPR_OR_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef getDeclaredThisType() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TFunction getFunctionType() {
		Type _declaredType = this.getDeclaredType();
		return ((TFunction) _declaredType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeVariable> getTypeVars() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeRef> getTypeVarUpperBounds(TypeVariable typeVar) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TFormalParameter> getFpars() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef getReturnTypeRef() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGeneric() {
		EList<TypeVariable> _typeVars = this.getTypeVars();
		boolean _isEmpty = _typeVars.isEmpty();
		return (!_isEmpty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRaw() {
		boolean _and = false;
		boolean _isGeneric = this.isGeneric();
		if (!_isGeneric) {
			_and = false;
		} else {
			EList<TypeArgument> _typeArgs = this.getTypeArgs();
			int _size = _typeArgs.size();
			EList<TypeVariable> _typeVars = this.getTypeVars();
			int _size_1 = _typeVars.size();
			boolean _lessThan = (_size < _size_1);
			_and = _lessThan;
		}
		return _and;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TFormalParameter getFparForArgIdx(final int argIndex) {
		EList<TFormalParameter> _fpars = this.getFpars();
		final int fparsSize = _fpars.size();
		if (((argIndex >= 0) && (argIndex < fparsSize))) {
			EList<TFormalParameter> _fpars_1 = this.getFpars();
			return _fpars_1.get(argIndex);
		}
		else {
			boolean _and = false;
			if (!((argIndex >= fparsSize) && (fparsSize > 0))) {
				_and = false;
			} else {
				EList<TFormalParameter> _fpars_2 = this.getFpars();
				TFormalParameter _get = _fpars_2.get((fparsSize - 1));
				boolean _isVariadic = _get.isVariadic();
				_and = _isVariadic;
			}
			if (_and) {
				EList<TFormalParameter> _fpars_3 = this.getFpars();
				return _fpars_3.get((fparsSize - 1));
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeRefAsString() {
		String _xifexpression = null;
		TypeRef _declaredThisType = this.getDeclaredThisType();
		boolean _tripleNotEquals = (_declaredThisType != null);
		if (_tripleNotEquals) {
			TypeRef _declaredThisType_1 = this.getDeclaredThisType();
			String _typeRefAsString = _declaredThisType_1.getTypeRefAsString();
			String _plus = ("@This(" + _typeRefAsString);
			_xifexpression = (_plus + ") ");
		}
		else {
			_xifexpression = "";
		}
		String _plus_1 = ("{" + _xifexpression);
		String _plus_2 = (_plus_1 + "function");
		String _xifexpression_1 = null;
		boolean _isGeneric = this.isGeneric();
		if (_isGeneric) {
			EList<TypeVariable> _typeVars = this.getTypeVars();
			final Function1<TypeVariable, String> _function = new Function1<TypeVariable, String>() {
				public String apply(final TypeVariable it) {
					EList<TypeRef> _typeVarUpperBounds = FunctionTypeExprOrRefImpl.this.getTypeVarUpperBounds(it);
					return it.getTypeVariableAsString(_typeVarUpperBounds);
				}
			};
			EList<String> _map = XcoreEListExtensions.<TypeVariable, String>map(_typeVars, _function);
			String _join = IterableExtensions.join(_map, ",");
			String _plus_3 = ("<" + _join);
			_xifexpression_1 = (_plus_3 + ">");
		}
		else {
			_xifexpression_1 = "";
		}
		String _plus_4 = (_plus_2 + _xifexpression_1);
		String _plus_5 = (_plus_4 + "(");
		EList<TFormalParameter> _fpars = this.getFpars();
		final Function1<TFormalParameter, String> _function_1 = new Function1<TFormalParameter, String>() {
			public String apply(final TFormalParameter it) {
				String _xifexpression = null;
				TypeRef _typeRef = it.getTypeRef();
				boolean _tripleNotEquals = (_typeRef != null);
				if (_tripleNotEquals) {
					TypeRef _typeRef_1 = it.getTypeRef();
					_xifexpression = _typeRef_1.getTypeRefAsString();
				}
				else {
					_xifexpression = "null";
				}
				return _xifexpression;
			}
		};
		EList<String> _map_1 = XcoreEListExtensions.<TFormalParameter, String>map(_fpars, _function_1);
		String _join_1 = IterableExtensions.join(_map_1, ",");
		String _plus_6 = (_plus_5 + _join_1);
		String _plus_7 = (_plus_6 + ")");
		String _xifexpression_2 = null;
		TypeRef _returnTypeRef = this.getReturnTypeRef();
		boolean _tripleNotEquals_1 = (_returnTypeRef != null);
		if (_tripleNotEquals_1) {
			TypeRef _returnTypeRef_1 = this.getReturnTypeRef();
			String _typeRefAsString_1 = _returnTypeRef_1.getTypeRefAsString();
			_xifexpression_2 = (":" + _typeRefAsString_1);
		}
		else {
			_xifexpression_2 = "";
		}
		String _plus_8 = (_plus_7 + _xifexpression_2);
		String _plus_9 = (_plus_8 + "}");
		String _modifiersAsString = this.getModifiersAsString();
		return (_plus_9 + _modifiersAsString);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == TypeArgument.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_ARGUMENT___GET_TYPE_REF_AS_STRING: return TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___IS_GENERIC: return TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___IS_GENERIC;
				case TypeRefsPackage.TYPE_REF___IS_RAW: return TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___IS_RAW;
				case TypeRefsPackage.TYPE_REF___GET_TYPE_REF_AS_STRING: return TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_REF_AS_STRING;
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_DECLARED_THIS_TYPE:
				return getDeclaredThisType();
			case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_FUNCTION_TYPE:
				return getFunctionType();
			case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_VARS:
				return getTypeVars();
			case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_VAR_UPPER_BOUNDS__TYPEVARIABLE:
				return getTypeVarUpperBounds((TypeVariable)arguments.get(0));
			case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_FPARS:
				return getFpars();
			case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_RETURN_TYPE_REF:
				return getReturnTypeRef();
			case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___IS_GENERIC:
				return isGeneric();
			case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___IS_RAW:
				return isRaw();
			case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_FPAR_FOR_ARG_IDX__INT:
				return getFparForArgIdx((Integer)arguments.get(0));
			case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_REF_AS_STRING:
				return getTypeRefAsString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //FunctionTypeExprOrRefImpl
