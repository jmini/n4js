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
package eu.numberfour.n4js.tests.findReferences

import com.google.inject.Inject
import com.google.inject.Provider
import eu.numberfour.n4js.N4JSInjectorProvider
import eu.numberfour.n4js.n4JS.N4ClassDeclaration
import eu.numberfour.n4js.n4JS.Script
import eu.numberfour.n4js.n4JS.VariableStatement
import eu.numberfour.n4js.ts.findReferences.SimpleResourceAccess
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.findReferences.IReferenceFinder
import org.eclipse.xtext.findReferences.TargetURICollector
import org.eclipse.xtext.findReferences.TargetURIs
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.resource.IReferenceDescription
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ReferenceFinderTest extends Assert {

	@Inject extension ParseHelper<Script>

	@Inject Provider<TargetURIs> targetURISetProvider
	@Inject IReferenceFinder referenceFinder
	@Inject TargetURICollector collector
	@Inject ResourceDescriptionsProvider resourceDescriptionsProvider

	// more tests in HeadlessReferenceFinderTest
	@Test
	def void testFindReferences_01() {
		val script = '''
			class C {}
			var c: C = null
		'''.parse
		val targets = targetURISetProvider.get
		val c = script.scriptElements.head as N4ClassDeclaration
		collector.add(c, targets)
		val index = resourceDescriptionsProvider.getResourceDescriptions(script.eResource)
		val result = newArrayList
		val IReferenceFinder.Acceptor acceptor = new IReferenceFinder.Acceptor() {

			override accept(IReferenceDescription description) {
				throw new UnsupportedOperationException("Should not be called")
			}

			override accept(EObject source, URI sourceURI, EReference eReference, int index, EObject targetOrProxy, URI targetURI) {
				result.add(source)
			}

		}
		referenceFinder.findAllReferences(targets, new SimpleResourceAccess(script.eResource.resourceSet), index, acceptor, null)
		assertEquals(3, result.size)
		val varStmt = script.scriptElements.last as VariableStatement
		val varDecl = varStmt.varDecl.head
		val typeRef = varDecl.declaredTypeRef
		assertTrue(result.contains(typeRef)) // type in the var decl
		assertTrue(result.contains(c)) // ref to type element
		assertTrue(result.contains(c.definedType)) // ref to ast element
	}

}
