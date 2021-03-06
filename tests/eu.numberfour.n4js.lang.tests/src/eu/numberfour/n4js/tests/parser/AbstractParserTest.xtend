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
package eu.numberfour.n4js.tests.parser

import com.google.inject.Inject
import eu.numberfour.n4js.N4JSInjectorProvider
import eu.numberfour.n4js.N4JSParseHelper
import eu.numberfour.n4js.n4JS.Expression
import eu.numberfour.n4js.n4JS.N4JSPackage
import eu.numberfour.n4js.n4JS.ParameterizedPropertyAccessExpression
import eu.numberfour.n4js.n4JS.ParenExpression
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.junit.Assert
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
abstract class AbstractParserTest extends Assert {
	@Inject
	protected extension N4JSParseHelper

	def parseSuccessfully(CharSequence js) {
		val script = js.parseUnrestricted
		assertTrue(script.eResource.errors.join('\n') [ line + ': ' + message] , script.eResource.errors.empty)
		return script
	}

	def parseWithError(CharSequence js) {
		val script = js.parseUnrestricted
		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
		return script
	}

	/**
	 * Used in tests to eliminate the suspicious paren expression
	 */
	protected def unwrap(Expression it) {
		switch it {
			ParenExpression: return expression
		}
		fail('Expected ParenExpression but got' + it)
		return null;
	}

	protected def getText(Expression it) {
		NodeModelUtils.getTokenText(NodeModelUtils.findActualNodeFor(it))
	}
	protected def getPropertyText(ParameterizedPropertyAccessExpression it) {
		NodeModelUtils.getTokenText(
			NodeModelUtils.findNodesForFeature(it, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY).head
		)
	}

}
