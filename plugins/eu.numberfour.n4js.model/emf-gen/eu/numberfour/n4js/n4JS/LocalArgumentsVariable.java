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
 * A representation of the model object '<em><b>Local Arguments Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *  Implicit variable in function and method bodies referencing all passed in argument values.
 * <!-- end-model-doc -->
 *
 *
 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getLocalArgumentsVariable()
 * @model
 * @generated
 */
public interface LocalArgumentsVariable extends Variable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return \"arguments\";'"
	 * @generated
	 */
	String getName();

} // LocalArgumentsVariable
