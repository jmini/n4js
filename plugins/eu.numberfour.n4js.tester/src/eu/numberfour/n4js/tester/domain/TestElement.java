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
package eu.numberfour.n4js.tester.domain;

/**
 * Super type of all test model elements. {@link TestResult} does not inherit from this because it is intended to appear
 * only as attribute of a TestElement.
 */
public interface TestElement extends Cloneable {
	// nothing
}
