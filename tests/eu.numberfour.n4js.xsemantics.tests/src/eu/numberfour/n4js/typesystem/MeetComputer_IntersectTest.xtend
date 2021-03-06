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
package eu.numberfour.n4js.typesystem

import com.google.inject.Inject
import eu.numberfour.n4js.N4JSInjectorProvider
import eu.numberfour.n4js.ts.typeRefs.TypeRef
import eu.numberfour.n4js.ts.utils.TypeUtils
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/*
 * Tests for {@link TypeSystemHelper#intersect(RuleEnvironment, TypeRef...)} method.
 */
// TODO to be enhanced, see IDE-142/IDE-385
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class MeetComputer_IntersectTest extends AbstractTypeSystemHelperTests {

	@Inject MeetComputer meetComputer

	@Before
	def void prepareTypeDefs() {
		setDefaultTypeDefinitions()
	}

	/**
	 * Asserts that intersection of given type expressions equals a given expected type, for comparison
	 * {@link TypeRef#getTypeRefAsString()} is used.
	 */
	def void assertIntersectRelaxed(String expectedType, String... typeExpressionsToBeIntersected) {
		val G = assembler.prepareScriptAndCreateRuleEnvironment(typeExpressionsToBeIntersected)
		var typeRefs = typeExpressionsToBeIntersected.map[TypeUtils.copy(assembler.getTypeRef(it))]
		val intersection = TypeUtils.copy(meetComputer.intersectRelaxed(G, typeRefs));
		intersection.sorted();
		assertEquals(expectedType, intersection.typeRefAsString);
	}

	@Test
	def void testIntersectReleaxed() {
		assertIntersectRelaxed("A", "A", "A");
		assertIntersectRelaxed("B", "A", "B");
		assertIntersectRelaxed("B", "B", "A");
		assertIntersectRelaxed("intersection{A,D}", "A", "D");
		assertIntersectRelaxed("intersection{A,D}", "D", "A");
		assertIntersectRelaxed("intersection{B,D}", "B", "D");
		assertIntersectRelaxed("intersection{B,D}", "D", "B");
		assertIntersectRelaxed("intersection{B,D}", "A", "B", "D");
 		assertIntersectRelaxed("intersection{B,D}", "A", "D", "B");
 		assertIntersectRelaxed("intersection{B,D}", "B", "A", "D");
 		assertIntersectRelaxed("intersection{B,D}", "B", "D", "A");
 		assertIntersectRelaxed("intersection{B,D}", "D", "A", "B");
 		assertIntersectRelaxed("intersection{B,D}", "D", "B", "A");
	}



	@Test
	def void testIntersectReleaxedWithIntersections() {
		assertIntersectRelaxed("intersection{A,D}", "intersection{A,D}", "intersection{A,D}");
		assertIntersectRelaxed("intersection{A,D}", "intersection{A,D}", "A");
		assertIntersectRelaxed("intersection{A,D}", "intersection{A,D}", "D");
		assertIntersectRelaxed("intersection{B,D}", "intersection{A,D}", "B");
		assertIntersectRelaxed("intersection{B,D}", "intersection{B,D}", "A");
	}

}
