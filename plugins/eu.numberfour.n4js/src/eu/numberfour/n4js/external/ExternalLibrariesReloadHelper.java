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
package eu.numberfour.n4js.external;

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper class for reloading the registered external libraries by recreating the Xtext index for them.
 */
@Singleton
public class ExternalLibrariesReloadHelper {

	@Inject
	private NpmManager npmManager;

	@Inject
	private ExternalProjectsCollector collector;

	@Inject
	private ExternalLibraryBuilderHelper builderHelper;

	@Inject
	private RebuildWorkspaceProjectsScheduler scheduler;

	/**
	 * Reloads the external libraries by re-indexing all registered external projects that are do not exist in the
	 * workspace.
	 *
	 * @param refreshNpmDefinitions
	 *            if {@code true}, then the type definition files will be reloaded/refreshed for all {@code npm}
	 *            packages.
	 * @param monitor
	 *            the monitor for the process.
	 * @throws InvocationTargetException
	 *             if any unexpected error occurs during the refresh process.
	 */
	public void reloadLibraries(final boolean refreshNpmDefinitions, final IProgressMonitor monitor)
			throws InvocationTargetException {

		final SubMonitor subMonitor = SubMonitor.convert(monitor, refreshNpmDefinitions ? 2 : 1);

		// Refresh the type definitions for the NPM packages if required.
		if (refreshNpmDefinitions) {
			final IStatus refreshStatus = npmManager.refreshInstalledNpmPackages(subMonitor.newChild(1));
			if (!refreshStatus.isOK()) {
				throw new InvocationTargetException(new CoreException(refreshStatus));
			}
		}

		// Make sure to rebuild only those external ones that are not in the workspace.
		// Get all accessible workspace projects...
		final Collection<String> workspaceProjectNames = from(asList(getWorkspace().getRoot().getProjects()))
				.filter(p -> p.isAccessible())
				.transform(p -> p.getName())
				.toSet();

		// And build all those externals that has no corresponding workspace project.
		final Iterable<IProject> toBuild = from(collector.collectExternalProjects())
				.filter(p -> !workspaceProjectNames.contains(p.getName()))
				.filter(IProject.class);

		final Iterable<IProject> workspaceProjectsToRebuild = collector
				.collectProjectsWithDirectExternalDependencies(toBuild);

		builderHelper.build(toBuild, subMonitor.newChild(1));
		scheduler.scheduleBuildIfNecessary(workspaceProjectsToRebuild);
	}

}
