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
 * A representation of the model object '<em><b>Super Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getSuperLiteral()
 * @model
 * @generated
 */
public interface SuperLiteral extends PrimaryExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns true if super literal is directly contained in a call expression.
	 * This is a call to the super constructor of a class, which is only allowed in a constructor of a subclass.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.ecore.EObject%> _eContainer = this.eContainer();\nreturn (_eContainer instanceof <%eu.numberfour.n4js.n4JS.ParameterizedCallExpression%>);'"
	 * @generated
	 */
	boolean isSuperConstructorAccess();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns true if super literal is directly contained in a property or index access expression.
	 * This is a call to a super's member, e.g., via "super.foo()".
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _or = false;\n<%org.eclipse.emf.ecore.EObject%> _eContainer = this.eContainer();\nif ((_eContainer instanceof <%eu.numberfour.n4js.n4JS.ParameterizedPropertyAccessExpression%>))\n{\n\t_or = true;\n} else\n{\n\t<%org.eclipse.emf.ecore.EObject%> _eContainer_1 = this.eContainer();\n\t_or = (_eContainer_1 instanceof <%eu.numberfour.n4js.n4JS.IndexedAccessExpression%>);\n}\nreturn _or;'"
	 * @generated
	 */
	boolean isSuperMemberAccess();

} // SuperLiteral
