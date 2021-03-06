/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.n4JS;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>For Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.n4JS.ForStatement#getInitExpr <em>Init Expr</em>}</li>
 *   <li>{@link eu.numberfour.n4js.n4JS.ForStatement#getUpdateExpr <em>Update Expr</em>}</li>
 *   <li>{@link eu.numberfour.n4js.n4JS.ForStatement#isForIn <em>For In</em>}</li>
 *   <li>{@link eu.numberfour.n4js.n4JS.ForStatement#isForOf <em>For Of</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getForStatement()
 * @model
 * @generated
 */
public interface ForStatement extends VariableDeclarationContainer, IterationStatement, VariableEnvironmentElement {
	/**
	 * Returns the value of the '<em><b>Init Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Expr</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Expr</em>' containment reference.
	 * @see #setInitExpr(Expression)
	 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getForStatement_InitExpr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getInitExpr();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.n4JS.ForStatement#getInitExpr <em>Init Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Init Expr</em>' containment reference.
	 * @see #getInitExpr()
	 * @generated
	 */
	void setInitExpr(Expression value);

	/**
	 * Returns the value of the '<em><b>Update Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Update Expr</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Update Expr</em>' containment reference.
	 * @see #setUpdateExpr(Expression)
	 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getForStatement_UpdateExpr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getUpdateExpr();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.n4JS.ForStatement#getUpdateExpr <em>Update Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Update Expr</em>' containment reference.
	 * @see #getUpdateExpr()
	 * @generated
	 */
	void setUpdateExpr(Expression value);

	/**
	 * Returns the value of the '<em><b>For In</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>For In</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>For In</em>' attribute.
	 * @see #setForIn(boolean)
	 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getForStatement_ForIn()
	 * @model unique="false"
	 * @generated
	 */
	boolean isForIn();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.n4JS.ForStatement#isForIn <em>For In</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>For In</em>' attribute.
	 * @see #isForIn()
	 * @generated
	 */
	void setForIn(boolean value);

	/**
	 * Returns the value of the '<em><b>For Of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>For Of</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>For Of</em>' attribute.
	 * @see #setForOf(boolean)
	 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getForStatement_ForOf()
	 * @model unique="false"
	 * @generated
	 */
	boolean isForOf();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.n4JS.ForStatement#isForOf <em>For Of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>For Of</em>' attribute.
	 * @see #isForOf()
	 * @generated
	 */
	void setForOf(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _and = false;\nboolean _isForIn = this.isForIn();\nboolean _not = (!_isForIn);\nif (!_not)\n{\n\t_and = false;\n} else\n{\n\tboolean _isForOf = this.isForOf();\n\tboolean _not_1 = (!_isForOf);\n\t_and = _not_1;\n}\nreturn _and;'"
	 * @generated
	 */
	boolean isForPlain();

} // ForStatement
