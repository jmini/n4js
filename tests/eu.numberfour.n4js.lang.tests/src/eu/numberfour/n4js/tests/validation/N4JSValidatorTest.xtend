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
package eu.numberfour.n4js.tests.validation

import com.google.inject.Inject
import eu.numberfour.n4js.N4JSInjectorProvider
import eu.numberfour.n4js.n4JS.N4JSPackage
import eu.numberfour.n4js.n4JS.Script
import eu.numberfour.n4js.validation.IssueCodes
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Includes acceptance test for IDE_551, more tests regarding overridden members in spec tests.
 * @see eu.numberfour.n4js.validation.validators.N4JSMemberRedefinitionValidator
 */
@InjectWith(N4JSInjectorProvider) // for UI: JSUiIn...., is then a plugin test
@RunWith(XtextRunner)
//@Ignore("N4JSValidator.checkUniqueMemberNames is currently commented out because of things to be implemented for binding")
class N4JSValidatorTest {
	@Inject
	extension ParseHelper<Script>

	@Inject
	extension ValidationTestHelper

	@Test
	def void testCircularInit() {
		val program =
		'''
			var v = v
		'''.parse
		assertTrue(program.eResource.errors.empty)
		program.assertWarning(N4JSPackage.Literals.IDENTIFIER_REF, IssueCodes.AST_VAR_DECL_RECURSIVE)
	}

	@Test
	def void testDuplicateNamesOwnedMembersTwoFields() {
		val program =
		'''
		class X {
		/* 2 */	a: any;
		/* 3 */	a: any;
		}
		'''.parse // only parsed, not linked
		// syntax ok?
		assertTrue(program.eResource.errors.empty)

		val expectedErrorMsgs = #[
			'The field a (line 2) duplicates field a (line 3).',
			'The field a (line 3) duplicates field a (line 2).'
		]

		expectedErrorMsgs.forEach[program.assertError(N4JSPackage.Literals.N4_MEMBER_DECLARATION,	IssueCodes.CLF_DUP_MEMBER, it)]
	}

	@Test
	def void testDuplicateNamesOwnedMembers() {
		val program =
		'''
		class X {
		/* 2 */	a: any;
		/* 3 */	b: any;
		/* 4 */	a: any;
		/* 5 */	d: any;
		/* 6 */	c(x: number): string { return "" }
		/* 7 */	c(v: string): string { return ""}
		/* 8 */	get b(): string { return "" }
		/* 9 */	set d(u: string) {}
		}
		'''.parse // only parsed, not linked
		// syntax ok?
		assertTrue(program.eResource.errors.empty)

		val expectedErrorMsgs = #[
			'The field a (line 2) duplicates field a (line 4).',
			'The field b (line 3) duplicates getter b (line 8).',
			'The field a (line 4) duplicates field a (line 2).',
			'The field d (line 5) duplicates setter d (line 9).',
			'The method c (line 6) duplicates method c (line 7).',
			'The method c (line 7) duplicates method c (line 6).',
			'The getter b (line 8) duplicates field b (line 3).',
			'The setter d (line 9) duplicates field d (line 5).'
		]

		expectedErrorMsgs.forEach[program.assertError(N4JSPackage.Literals.N4_MEMBER_DECLARATION,	IssueCodes.CLF_DUP_MEMBER, it)]
	}


	@Test
	def void testNoDuplicateNamesWhenAccessors() {
		val program =
		'''
		class A {
			private ___a: string;

			get a(): string {  return this.___a;  }
			set a(aParam: string) {   this.___a = aParam;   }
		}
		'''.parse // only parsed, not linked

		// syntax ok?
		assertTrue(program.eResource.errors.empty)

		// bindings ok?
		 _validationTestHelper.assertNoErrors(program)
		 _validationTestHelper.assertNoWarnings(program, N4JSPackage.Literals.N4_MEMBER_DECLARATION, IssueCodes.CLF_DUP_MEMBER)
	}

	@Test
	def void testNoDuplicateNamesWhenOverride() {
		val program =
		'''
		class A {
			private ___a: string;

			getA(): string {  return this.___a;  }
		}
		class B extends A {
			private ___b: string;

			@Override
			getA(): string {  return this.___b;  }
		}
		'''.parse // only parsed, not linked

		// syntax ok?
		assertTrue(program.eResource.errors.empty)

		// bindings ok?
		 _validationTestHelper.assertNoErrors(program)
		 _validationTestHelper.assertNoWarnings(program, N4JSPackage.Literals.N4_MEMBER_DECLARATION, IssueCodes.CLF_OVERRIDE_ANNOTATION)
	}
}
