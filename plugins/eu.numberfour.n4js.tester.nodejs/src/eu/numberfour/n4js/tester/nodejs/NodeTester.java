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
package eu.numberfour.n4js.tester.nodejs;

import static eu.numberfour.n4js.runner.extension.RuntimeEnvironment.NODEJS_MANGELHAFT;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.numberfour.n4js.runner.IExecutor;
import eu.numberfour.n4js.runner.RunnerFrontEnd;
import eu.numberfour.n4js.runner.nodejs.NodeRunner;
import eu.numberfour.n4js.tester.ITester;
import eu.numberfour.n4js.tester.TestConfiguration;
import eu.numberfour.n4js.tester.extension.ITesterDescriptor;
import eu.numberfour.n4js.tester.extension.TesterDescriptorImpl;

/**
 */
public class NodeTester implements ITester {

	/** ID of the Node.js tester as defined in the plugin.xml. */
	public static final String ID = "eu.numberfour.n4js.tester.nodejs";

	/**
	 * Class for providing descriptors for the Node.js tester with the same information as defined in the plugin.xml
	 * (used only by N4jsc). Supports instance supplying with injection.
	 */
	public static final class NodeTesterDescriptorProvider implements Provider<ITesterDescriptor> {

		@Inject
		private Provider<NodeTester> nodeTesterProvider;

		@Override
		public ITesterDescriptor get() {
			return new TesterDescriptorImpl(ID, "Node.js Tester", NODEJS_MANGELHAFT, nodeTesterProvider.get());
		}

	}

	@Override
	public TestConfiguration createConfiguration() {
		return new TestConfiguration();
	}

	@Override
	public void prepareConfiguration(TestConfiguration config) {
		// no special values to be prepared here
	}

	@Override
	public String getRunnerIdForTesting() {
		return NodeRunner.ID;
	}

	@Override
	public Process test(TestConfiguration config, IExecutor executor, RunnerFrontEnd runnerFrontEnd) {
		// FIXME launch test by doing special set-up and then delegating to NodeRunner
		return runnerFrontEnd.run(config, executor);
	}
}
