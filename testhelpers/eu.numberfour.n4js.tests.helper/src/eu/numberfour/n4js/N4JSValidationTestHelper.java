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
package eu.numberfour.n4js;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;

import com.google.common.base.Joiner;

/**
 */
public class N4JSValidationTestHelper extends ValidationTestHelper {

	/**
	 * Asserts that root and the entire object tree below root does not contain any dangling references, i.e.
	 * cross-references to target {@link EObject}s that are not contained in a {@link Resource}.
	 */
	public void assertNoDanglingReferences(EObject root) {
		final List<String> errMsgs = new ArrayList<>();
		final TreeIterator<EObject> iter = root.eAllContents();
		while (iter.hasNext()) {
			final EObject currObj = iter.next();
			if (currObj != null && !currObj.eIsProxy()) {
				for (EReference currRef : currObj.eClass().getEAllReferences()) {
					if (!currRef.isContainment() && currRef.getEOpposite() == null) {
						if (currRef.isMany()) {
							@SuppressWarnings("unchecked")
							final EList<? extends EObject> targets = (EList<? extends EObject>) currObj.eGet(currRef,
									false);
							for (EObject currTarget : targets) {
								if (isDangling(currTarget)) {
									errMsgs.add(getErrorInfoForDanglingEObject(currObj, currRef));
									break;
								}
							}
						}
						else {
							final EObject target = (EObject) currObj.eGet(currRef, false);
							if (isDangling(target))
								errMsgs.add(getErrorInfoForDanglingEObject(currObj, currRef));
						}
					}
				}
			}
		}
		if (!errMsgs.isEmpty())
			fail("Expected no dangling references, but found the following: " + Joiner.on("; ").join(errMsgs) + ".");
	}

	private static final boolean isDangling(EObject target) {
		return target != null && target.eResource() == null;
	}

	private static final String getErrorInfoForDanglingEObject(EObject base, EReference ref) {
		return "in " + base.eClass().getName() + " at " + EcoreUtil.getURI(base) + " via reference " + ref.getName();
	}
}
