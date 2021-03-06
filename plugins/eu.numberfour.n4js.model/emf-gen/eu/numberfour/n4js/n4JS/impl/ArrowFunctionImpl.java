/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.n4JS.impl;

import com.google.common.base.Objects;

import eu.numberfour.n4js.n4JS.ArrowFunction;
import eu.numberfour.n4js.n4JS.Block;
import eu.numberfour.n4js.n4JS.Expression;
import eu.numberfour.n4js.n4JS.ExpressionStatement;
import eu.numberfour.n4js.n4JS.FunctionExpression;
import eu.numberfour.n4js.n4JS.N4JSPackage;
import eu.numberfour.n4js.n4JS.Statement;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Arrow Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.n4JS.impl.ArrowFunctionImpl#isHasBracesAroundBody <em>Has Braces Around Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArrowFunctionImpl extends FunctionExpressionImpl implements ArrowFunction {
	/**
	 * The default value of the '{@link #isHasBracesAroundBody() <em>Has Braces Around Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasBracesAroundBody()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_BRACES_AROUND_BODY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasBracesAroundBody() <em>Has Braces Around Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasBracesAroundBody()
	 * @generated
	 * @ordered
	 */
	protected boolean hasBracesAroundBody = HAS_BRACES_AROUND_BODY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArrowFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.ARROW_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasBracesAroundBody() {
		return hasBracesAroundBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasBracesAroundBody(boolean newHasBracesAroundBody) {
		boolean oldHasBracesAroundBody = hasBracesAroundBody;
		hasBracesAroundBody = newHasBracesAroundBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.ARROW_FUNCTION__HAS_BRACES_AROUND_BODY, oldHasBracesAroundBody, hasBracesAroundBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isArrowFunction() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSingleExprImplicitReturn() {
		boolean _and = false;
		boolean _and_1 = false;
		boolean _and_2 = false;
		boolean _and_3 = false;
		boolean _isArrowFunction = this.isArrowFunction();
		if (!_isArrowFunction) {
			_and_3 = false;
		} else {
			boolean _isHasBracesAroundBody = this.isHasBracesAroundBody();
			boolean _not = (!_isHasBracesAroundBody);
			_and_3 = _not;
		}
		if (!_and_3) {
			_and_2 = false;
		} else {
			Block _body = this.getBody();
			boolean _notEquals = (!Objects.equal(_body, null));
			_and_2 = _notEquals;
		}
		if (!_and_2) {
			_and_1 = false;
		} else {
			Block _body_1 = this.getBody();
			EList<Statement> _statements = _body_1.getStatements();
			boolean _isEmpty = _statements.isEmpty();
			boolean _not_1 = (!_isEmpty);
			_and_1 = _not_1;
		}
		if (!_and_1) {
			_and = false;
		} else {
			Block _body_2 = this.getBody();
			EList<Statement> _statements_1 = _body_2.getStatements();
			Statement _get = _statements_1.get(0);
			_and = (_get instanceof ExpressionStatement);
		}
		return _and;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression implicitReturnExpr() {
		Expression _xifexpression = null;
		boolean _isSingleExprImplicitReturn = this.isSingleExprImplicitReturn();
		if (_isSingleExprImplicitReturn) {
			Block _body = this.getBody();
			EList<Statement> _statements = _body.getStatements();
			Statement _get = _statements.get(0);
			_xifexpression = ((ExpressionStatement) _get).getExpression();
		}
		else {
			_xifexpression = null;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.ARROW_FUNCTION__HAS_BRACES_AROUND_BODY:
				return isHasBracesAroundBody();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case N4JSPackage.ARROW_FUNCTION__HAS_BRACES_AROUND_BODY:
				setHasBracesAroundBody((Boolean)newValue);
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
			case N4JSPackage.ARROW_FUNCTION__HAS_BRACES_AROUND_BODY:
				setHasBracesAroundBody(HAS_BRACES_AROUND_BODY_EDEFAULT);
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
			case N4JSPackage.ARROW_FUNCTION__HAS_BRACES_AROUND_BODY:
				return hasBracesAroundBody != HAS_BRACES_AROUND_BODY_EDEFAULT;
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
		if (baseClass == FunctionExpression.class) {
			switch (baseOperationID) {
				case N4JSPackage.FUNCTION_EXPRESSION___IS_ARROW_FUNCTION: return N4JSPackage.ARROW_FUNCTION___IS_ARROW_FUNCTION;
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
			case N4JSPackage.ARROW_FUNCTION___IS_ARROW_FUNCTION:
				return isArrowFunction();
			case N4JSPackage.ARROW_FUNCTION___IS_SINGLE_EXPR_IMPLICIT_RETURN:
				return isSingleExprImplicitReturn();
			case N4JSPackage.ARROW_FUNCTION___IMPLICIT_RETURN_EXPR:
				return implicitReturnExpr();
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (hasBracesAroundBody: ");
		result.append(hasBracesAroundBody);
		result.append(')');
		return result.toString();
	}

} //ArrowFunctionImpl
