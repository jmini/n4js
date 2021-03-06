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

import eu.numberfour.n4js.n4JS.ParameterizedCallExpression
import eu.numberfour.n4js.n4JS.ExpressionStatement
import eu.numberfour.n4js.n4JS.FunctionDeclaration
import eu.numberfour.n4js.n4JS.IdentifierRef
import org.junit.Test
import eu.numberfour.n4js.n4JS.ParenExpression
import eu.numberfour.n4js.n4JS.FunctionExpression
import eu.numberfour.n4js.n4JS.StringLiteral
import eu.numberfour.n4js.n4JS.VariableStatement

class ES_13_FunctionDefinitionEsprimaTest extends AbstractParserTest {

	@Test
	def void testFunctionDefinition() {
		val script = 'function hello() { sayHi(); }'.parseSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("hello", funcDecl.name)

		val block = funcDecl.body
		assertEquals(1, block.statements.size())

		val callExpr0 = (block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("sayHi", (callExpr0.target as IdentifierRef).text)
		assertTrue(callExpr0.arguments.empty)
	}

	@Test
	def void testFunctionDefinitionEval() {
		val script = 'function eval() { }'.parseSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("eval", funcDecl.name)

		val block = funcDecl.body
		assertEquals(0, block.statements.size())
	}

	@Test
	def void testFunctionDefinitionArguments() {
		val script = 'function arguments() { }'.parseSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("arguments", funcDecl.name)

		val block = funcDecl.body
		assertEquals(0, block.statements.size())
	}

	@Test
	def void testFunctionDefinitionWithFpars() {
		val script = 'function test(t, t) { }'.parseSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("test", funcDecl.name)

		assertEquals(2, funcDecl.fpars.size())
		assertEquals("t", funcDecl.fpars.get(0).name)
		assertEquals("t", funcDecl.fpars.get(1).name)

		val block = funcDecl.body
		assertEquals(0, block.statements.size())
	}

	@Test
	def void testFunctionExpressionWithFpars() {
		val script = '(function test(t, t) { })'.parseSuccessfully
		assertEquals(1, script.scriptElements.size())
		val parenExpr = (script.scriptElements.head as ExpressionStatement).expression as ParenExpression
		val funcExpr = parenExpr.expression as FunctionExpression
		assertEquals("test", funcExpr.name)

		assertEquals(2, funcExpr.fpars.size())
		assertEquals("t", funcExpr.fpars.get(0).name)
		assertEquals("t", funcExpr.fpars.get(1).name)

		val block = funcExpr.body
		assertEquals(0, block.statements.size())
	}

	@Test
	def void testFunctionDefinitionEvalWithInner() {
		val script = 'function eval() { function inner() { "use strict" } }'.parseSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("eval", funcDecl.name)

		val block = funcDecl.body
		assertEquals(1, block.statements.size())

		val innerFunc = block.statements.head as FunctionDeclaration
		assertEquals("inner", innerFunc.name)
		val blockInner = innerFunc.body
		assertEquals(1, blockInner.statements.size())
		assertEquals('"use strict"', ((blockInner.statements.head as ExpressionStatement).expression as StringLiteral).text)
	}

	@Test
	def void testFunctionDefinition1() {
		val script = 'function hello(a) { sayHi(); }'.parseSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("hello", funcDecl.name)

		assertEquals(1, funcDecl.fpars.size())
		assertEquals("a", funcDecl.fpars.get(0).name)

		val block = funcDecl.body
		assertEquals(1, block.statements.size())

		val callExpr0 = (block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("sayHi", (callExpr0.target as IdentifierRef).text)
		assertTrue(callExpr0.arguments.empty)
	}

	@Test
	def void testFunctionDefinition2() {
		val script = 'function hello(a, b) { sayHi(); }'.parseSuccessfully
		assertEquals(1, script.scriptElements.size())
		val funcDecl = script.scriptElements.head as FunctionDeclaration
		assertEquals("hello", funcDecl.name)

		assertEquals(2, funcDecl.fpars.size())
		assertEquals("a", funcDecl.fpars.get(0).name)
		assertEquals("b", funcDecl.fpars.get(1).name)

		val block = funcDecl.body
		assertEquals(1, block.statements.size())

		val callExpr0 = (block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("sayHi", (callExpr0.target as IdentifierRef).text)
		assertTrue(callExpr0.arguments.empty)
	}

	@Test
	def void testFunctionDefinitionVar() {
		val script = 'var hi = function() { sayHi() };'.parseSuccessfully
		assertEquals(1, script.scriptElements.size())
		val varDecl = (script.scriptElements.head as VariableStatement).varDecl.get(0)
		assertEquals("hi", varDecl.name);
		val funcExpr = varDecl.expression as FunctionExpression

		val block = funcExpr.body
		assertEquals(1, block.statements.size())

		val callExpr0 = (block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("sayHi", (callExpr0.target as IdentifierRef).text)
		assertTrue(callExpr0.arguments.empty)
	}

	@Test
	def void testFunctionDefinitionVar2() {
		val script = 'var hi = function eval() { };'.parseSuccessfully
		assertEquals(1, script.scriptElements.size())
		val varDecl = (script.scriptElements.head as VariableStatement).varDecl.get(0)
		assertEquals("hi", varDecl.name);
		val funcExpr = varDecl.expression as FunctionExpression

		val block = funcExpr.body
		assertEquals(0, block.statements.size())
		assertEquals("eval", funcExpr.name)
	}

	@Test
	def void testFunctionDefinitionVar3() {
		val script = 'var hi = function arguments() { };'.parseSuccessfully
		assertEquals(1, script.scriptElements.size())
		val varDecl = (script.scriptElements.head as VariableStatement).varDecl.get(0)
		assertEquals("hi", varDecl.name);
		val funcExpr = varDecl.expression as FunctionExpression

		val block = funcExpr.body
		assertEquals(0, block.statements.size())
		assertEquals("arguments", funcExpr.name)
	}


}
