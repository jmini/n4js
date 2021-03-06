/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.n4JS;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameterized Call Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.n4JS.ParameterizedCallExpression#getTarget <em>Target</em>}</li>
 *   <li>{@link eu.numberfour.n4js.n4JS.ParameterizedCallExpression#getArguments <em>Arguments</em>}</li>
 *   <li>{@link eu.numberfour.n4js.n4JS.ParameterizedCallExpression#isSpread <em>Spread</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getParameterizedCallExpression()
 * @model
 * @generated
 */
public interface ParameterizedCallExpression extends Expression, ParameterizedAccess {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The callee or target of the call, e.g. the name of the called function. This is not the receiver (which may be null).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(Expression)
	 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getParameterizedCallExpression_Target()
	 * @model containment="true"
	 * @generated
	 */
	Expression getTarget();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.n4JS.ParameterizedCallExpression#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Expression value);

	/**
	 * Returns the value of the '<em><b>Arguments</b></em>' containment reference list.
	 * The list contents are of type {@link eu.numberfour.n4js.n4JS.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arguments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arguments</em>' containment reference list.
	 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getParameterizedCallExpression_Arguments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getArguments();

	/**
	 * Returns the value of the '<em><b>Spread</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Spread</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Spread</em>' attribute.
	 * @see #setSpread(boolean)
	 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getParameterizedCallExpression_Spread()
	 * @model unique="false"
	 * @generated
	 */
	boolean isSpread();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.n4JS.ParameterizedCallExpression#isSpread <em>Spread</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Spread</em>' attribute.
	 * @see #isSpread()
	 * @generated
	 */
	void setSpread(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method returning the receiver expression or null, if call has no receiver.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%eu.numberfour.n4js.n4JS.Expression%> _xifexpression = null;\n<%eu.numberfour.n4js.n4JS.Expression%> _target = this.getTarget();\nif ((_target instanceof <%eu.numberfour.n4js.n4JS.ParameterizedPropertyAccessExpression%>))\n{\n\t<%eu.numberfour.n4js.n4JS.Expression%> _target_1 = this.getTarget();\n\t_xifexpression = ((<%eu.numberfour.n4js.n4JS.ParameterizedPropertyAccessExpression%>) _target_1).getTarget();\n}\nelse\n{\n\t_xifexpression = null;\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	Expression getReceiver();

} // ParameterizedCallExpression
