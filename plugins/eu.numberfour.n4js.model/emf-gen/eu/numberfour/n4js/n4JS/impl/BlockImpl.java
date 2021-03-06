/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.n4JS.impl;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;

import com.google.common.collect.Iterators;

import eu.numberfour.n4js.n4JS.Block;
import eu.numberfour.n4js.n4JS.Expression;
import eu.numberfour.n4js.n4JS.FunctionDefinition;
import eu.numberfour.n4js.n4JS.N4JSPackage;
import eu.numberfour.n4js.n4JS.ReturnStatement;
import eu.numberfour.n4js.n4JS.Statement;

import eu.numberfour.n4js.utils.EcoreUtilN4;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IteratorExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.n4JS.impl.BlockImpl#getStatements <em>Statements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockImpl extends StatementImpl implements Block {
	/**
	 * The cached value of the '{@link #getStatements() <em>Statements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatements()
	 * @generated
	 * @ordered
	 */
	protected EList<Statement> statements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.BLOCK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Statement> getStatements() {
		if (statements == null) {
			statements = new EObjectContainmentEList<Statement>(Statement.class, this, N4JSPackage.BLOCK__STATEMENTS);
		}
		return statements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iterator<Statement> getAllStatements() {
		final Predicate<EObject> _function = new Predicate<EObject>() {
			public boolean apply(final EObject it) {
				return (!((it instanceof Expression) || (it instanceof FunctionDefinition)));
			}
		};
		TreeIterator<EObject> _allContentsFiltered = EcoreUtilN4.getAllContentsFiltered(this, _function);
		return Iterators.<Statement>filter(_allContentsFiltered, Statement.class);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iterator<ReturnStatement> getAllReturnStatements() {
		Iterator<Statement> _allStatements = this.getAllStatements();
		return Iterators.<ReturnStatement>filter(_allStatements, ReturnStatement.class);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iterator<ReturnStatement> getAllNonVoidReturnStatements() {
		Iterator<ReturnStatement> _allReturnStatements = this.getAllReturnStatements();
		final Function1<ReturnStatement, Boolean> _function = new Function1<ReturnStatement, Boolean>() {
			public Boolean apply(final ReturnStatement it) {
				Expression _expression = it.getExpression();
				return Boolean.valueOf((!Objects.equal(_expression, null)));
			}
		};
		return IteratorExtensions.<ReturnStatement>filter(_allReturnStatements, _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iterator<ReturnStatement> getAllVoidReturnStatements() {
		Iterator<ReturnStatement> _allReturnStatements = this.getAllReturnStatements();
		final Function1<ReturnStatement, Boolean> _function = new Function1<ReturnStatement, Boolean>() {
			public Boolean apply(final ReturnStatement it) {
				Expression _expression = it.getExpression();
				return Boolean.valueOf(Objects.equal(_expression, null));
			}
		};
		return IteratorExtensions.<ReturnStatement>filter(_allReturnStatements, _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean hasNonVoidReturn() {
		Iterator<ReturnStatement> _allNonVoidReturnStatements = this.getAllNonVoidReturnStatements();
		boolean _isEmpty = IteratorExtensions.isEmpty(_allNonVoidReturnStatements);
		return (!_isEmpty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.BLOCK__STATEMENTS:
				return ((InternalEList<?>)getStatements()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.BLOCK__STATEMENTS:
				return getStatements();
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
			case N4JSPackage.BLOCK__STATEMENTS:
				getStatements().clear();
				getStatements().addAll((Collection<? extends Statement>)newValue);
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
			case N4JSPackage.BLOCK__STATEMENTS:
				getStatements().clear();
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
			case N4JSPackage.BLOCK__STATEMENTS:
				return statements != null && !statements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case N4JSPackage.BLOCK___GET_ALL_STATEMENTS:
				return getAllStatements();
			case N4JSPackage.BLOCK___GET_ALL_RETURN_STATEMENTS:
				return getAllReturnStatements();
			case N4JSPackage.BLOCK___GET_ALL_NON_VOID_RETURN_STATEMENTS:
				return getAllNonVoidReturnStatements();
			case N4JSPackage.BLOCK___GET_ALL_VOID_RETURN_STATEMENTS:
				return getAllVoidReturnStatements();
			case N4JSPackage.BLOCK___HAS_NON_VOID_RETURN:
				return hasNonVoidReturn();
		}
		return super.eInvoke(operationID, arguments);
	}

} //BlockImpl
