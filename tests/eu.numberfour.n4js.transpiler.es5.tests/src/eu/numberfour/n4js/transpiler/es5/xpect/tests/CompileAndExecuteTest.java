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
package eu.numberfour.n4js.transpiler.es5.xpect.tests;

import org.junit.runner.RunWith;
import org.xpect.runner.XpectRunner;
import org.xpect.runner.XpectSuiteClasses;
import org.xpect.runner.XpectTestFiles;
import org.xpect.runner.XpectTestFiles.FileRoot;
import org.xpect.xtext.lib.tests.ValidationTest;

import eu.numberfour.n4js.xpect.OutputXpectMethod;
import eu.numberfour.n4js.xpect.validation.NoerrorsXpectMethod;

/**
 */
@XpectSuiteClasses({
	ValidationTest.class,
	NoerrorsXpectMethod.class,
	OutputXpectMethod.class
})
@RunWith(XpectRunner.class)
@XpectTestFiles(relativeTo = FileRoot.PROJECT, baseDir = "testdata", fileExtensions = { "xt" })
public class CompileAndExecuteTest {
	// nop
}
