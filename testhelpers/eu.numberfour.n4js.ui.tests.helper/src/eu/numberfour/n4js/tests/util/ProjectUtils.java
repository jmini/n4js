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
package eu.numberfour.n4js.tests.util;

import static com.google.common.base.Predicates.alwaysTrue;
import static com.google.common.collect.FluentIterable.from;
import static org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil.addNature;
import static org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil.monitor;
import static org.eclipse.xtext.junit4.ui.util.JavaProjectSetupUtil.createSimpleProject;
import static org.eclipse.xtext.junit4.ui.util.JavaProjectSetupUtil.createSubFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.eclipse.xtext.ui.MarkerTypes;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.junit.Assert;

import com.google.common.base.Predicate;

import eu.numberfour.n4js.n4mf.DeclaredVersion;
import eu.numberfour.n4js.n4mf.N4mfFactory;
import eu.numberfour.n4js.n4mf.ProjectDescription;
import eu.numberfour.n4js.n4mf.ProjectType;
import eu.numberfour.n4js.n4mf.SourceFragment;
import eu.numberfour.n4js.n4mf.SourceFragmentType;
import eu.numberfour.n4js.ui.editor.N4JSDirtyStateEditorSupport;
import eu.numberfour.n4js.ui.internal.N4JSActivator;

/**
 */
public class ProjectUtils {

	private static Logger LOGGER = Logger.getLogger(ProjectUtils.class);

	/**
	 * Imports a project into the running JUnit test workspace. Usage:
	 *
	 * <pre>
	 * IProject project = ProjectUtils.importProject(new File(&quot;probands&quot;), &quot;TestProject&quot;);
	 * </pre>
	 *
	 * @param probandsFolder
	 *            the parent folder in which the test project is found
	 * @param projectName
	 *            the name of the test project, must be folder contained in probandsFolder
	 * @return the imported project
	 * @see <a href=
	 *      "http://stackoverflow.com/questions/12484128/how-do-i-import-an-eclipse-project-from-a-zip-file-programmatically">
	 *      stackoverflow: from zip</a>
	 */
	// TODO after java update bring back nullness analysis
	// @Nonnull
	// public static IProject importProject(@Nonnull File probandsFolder, @Nonnull String projectName) throws Exception
	// {
	public static IProject importProject(File probandsFolder, String projectName) throws Exception {
		File projectSourceFolder = new File(probandsFolder, projectName);
		if (!projectSourceFolder.exists()) {
			throw new IllegalArgumentException("proband not found in " + projectSourceFolder);
		}

		prepareDotProject(projectSourceFolder);

		IProgressMonitor monitor = new NullProgressMonitor();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		IProjectDescription newProjectDescription = workspace.newProjectDescription(projectName);
		IProject project = workspace.getRoot().getProject(projectName);
		project.create(newProjectDescription, monitor);
		project.open(monitor);
		if (!project.getLocation().toFile().exists()) {
			throw new IllegalArgumentException("test project correctly created in " + project.getLocation());
		}

		IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
			@Override
			public String queryOverwrite(String file) {
				return ALL;
			}
		};
		ImportOperation importOperation = new ImportOperation(project.getFullPath(), projectSourceFolder,
				FileSystemStructureProvider.INSTANCE, overwriteQuery);
		importOperation.setCreateContainerStructure(false);
		importOperation.run(monitor);

		return project;
	}

	/**
	 * For Test-mocks copies over "_project"-files to ".project" files and supplies a delete-on-exit shutdown hook for
	 * them.
	 *
	 * @param projectSourceFolder
	 *            folder potentially containing "_project", that is a potential workspace-project
	 */
	public static void prepareDotProject(File projectSourceFolder) {
		File dotProject = new File(projectSourceFolder, ".project");
		File dotProjectTemplate = new File(projectSourceFolder, "_project");

		if (dotProject.exists()) {
			if (!dotProjectTemplate.exists()) {
				// err if giving direct .project files cluttering the developers workspace
				throw new IllegalArgumentException("proband must rename the '.project' file to '_project'");
			} // else assume crashed VM leftover of .project, will be overwritten below.
		}
		if (dotProjectTemplate.exists()) {
			try {
				Files.copy(dotProjectTemplate.toPath(), dotProject.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new IllegalStateException("unable to prepare .project-file", e);
			}
			dotProject.deleteOnExit();
		}
	}

	/***/
	public static IProject createJSProject(String projectName) throws CoreException {
		return createJSProject(projectName, "src", "src-gen", null);
	}

	/**
	 * @param manifestAdjustments
	 *            for details see method {@link #createManifestN4MFFile(IProject, String, String, Consumer)}.
	 */
	@SuppressWarnings("restriction")
	public static IProject createJSProject(String projectName, String sourceFolder, String outputFolder,
			Consumer<ProjectDescription> manifestAdjustments) throws CoreException {
		IProject result = createSimpleProject(projectName);
		createSubFolder(result.getProject(), sourceFolder);
		createSubFolder(result.getProject(), outputFolder);
		createManifestN4MFFile(result.getProject(), sourceFolder, outputFolder, manifestAdjustments);
		return result;
	}

	/***/
	public static void createManifestN4MFFile(IProject project) throws CoreException {
		createManifestN4MFFile(project, "src", "src-gen", null);
	}

	/**
	 * @param manifestAdjustments
	 *            before saving the manifest this procedure will be called to allow adjustments to the manifest's
	 *            properties (the ProjectDescription object passed to the procedure will already contain all default
	 *            values). May be <code>null</code> if no adjustments are required.
	 */
	@SuppressWarnings("restriction")
	public static void createManifestN4MFFile(IProject project, String sourceFolder, String outputFolder,
			Consumer<ProjectDescription> manifestAdjustments) throws CoreException {
		IFile config = project.getFile("manifest.n4mf");
		URI uri = URI.createPlatformResourceURI(config.getFullPath().toString(), true);
		ProjectDescription projectDesc = N4mfFactory.eINSTANCE.createProjectDescription();
		projectDesc.setProjectDependencies(N4mfFactory.eINSTANCE.createProjectDependencies());
		projectDesc.setDeclaredVendorId("eu.numberfour");
		projectDesc.setVendorName("NumberFour AG");
		projectDesc.setArtifactId(project.getName());
		projectDesc.setProjectName(project.getName());
		projectDesc.setProjectType(ProjectType.SYSTEM);
		DeclaredVersion projectVersion = N4mfFactory.eINSTANCE.createDeclaredVersion();
		projectVersion.setMajor(0);
		projectVersion.setMinor(0);
		projectVersion.setMicro(1);
		projectDesc.setProjectVersion(projectVersion);
		projectDesc.setOutputPath(outputFolder);
		SourceFragment sourceProjectPath = N4mfFactory.eINSTANCE.createSourceFragment();
		sourceProjectPath.setSourceFragmentType(SourceFragmentType.SOURCE);
		sourceProjectPath.getPaths().add(sourceFolder);
		projectDesc.getSourceFragment().add(sourceProjectPath);
		if (manifestAdjustments != null)
			manifestAdjustments.accept(projectDesc);
		ResourceSet rs = createResourceSet(project);
		Resource res = rs.createResource(uri);
		res.getContents().add(projectDesc);
		try {
			res.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor());
		waitForAutoBuild();
		Assert.assertTrue("manifest.n4mf should have been created", config.exists());
	}

	// moved here from AbstractBuilderParticipantTest:
	/** Applies the Xtext nature to the project and creates (if necessary) and returns the source folder "src". */
	public static IFolder configureProjectWithXtext(IProject project) throws CoreException {
		return configureProjectWithXtext(project, "src");
	}

	// moved here from AbstractBuilderParticipantTest:
	/** Applies the Xtext nature to the project and creates (if necessary) and returns the source folder. */
	@SuppressWarnings("restriction")
	public static IFolder configureProjectWithXtext(IProject project, String sourceFolder) throws CoreException {
		addNature(project.getProject(), XtextProjectHelper.NATURE_ID);
		IFolder folder = project.getProject().getFolder(sourceFolder);
		if (!folder.exists()) {
			folder.create(true, true, null);
		}
		return folder;
	}

	private static ResourceSet createResourceSet(IProject project) {
		return N4JSActivator.getInstance().getInjector(N4JSActivator.EU_NUMBERFOUR_N4JS_N4JS)
				.getInstance(IResourceSetProvider.class).get(project);
	}

	/***/
	public static void waitForAutoBuild() {
		int maxWait = 100 * 60 * 2;
		long start = System.currentTimeMillis();
		long end = start;
		boolean wasInterrupted = false;
		boolean foundJob = false;
		do {
			try {
				Job[] foundJobs = Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_BUILD);
				if (foundJobs.length > 0) {
					foundJob = true;
					Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD,
							null);
				}
				wasInterrupted = false;
				end = System.currentTimeMillis();
			} catch (OperationCanceledException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				wasInterrupted = true;
			}
		} while (wasInterrupted && (end - start) < maxWait);
		if (!foundJob) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Auto build job hasn't been found, but maybe already run.");
			}
		}
	}

	/***/
	/**
	 * Waits for N4JSDirtyStateEditorSupport job to be run
	 */
	public static void waitForUpdateEditorJob() {
		int maxWait = 100 * 60 * 2;
		long start = System.currentTimeMillis();
		long end = start;
		boolean wasInterrupted = false;
		boolean foundJob = false;
		do {
			try {
				Job[] foundJobs = Job.getJobManager().find(N4JSDirtyStateEditorSupport.FAMILY_UPDATE_JOB);
				if (foundJobs.length > 0) {
					foundJob = true;
					Job.getJobManager().join(N4JSDirtyStateEditorSupport.FAMILY_UPDATE_JOB, null);
				}
				wasInterrupted = false;
				end = System.currentTimeMillis();
			} catch (OperationCanceledException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				wasInterrupted = true;
			}
		} while (wasInterrupted && (end - start) < maxWait);
		if (!foundJob) {
			LOGGER.warn("Update editor job hasn't been found, but maybe already run.");
		}
	}

	/***/
	public static IMarker[] assertMarkers(String assertMessage, final IProject project, int count)
			throws CoreException {

		return assertMarkers(assertMessage, project, MarkerTypes.ANY_VALIDATION, count);
	}

	/***/
	public static IMarker[] assertMarkers(String assertMessage, final IResource resource, int count)
			throws CoreException {
		return assertMarkers(assertMessage, resource, MarkerTypes.ANY_VALIDATION, count);
	}

	/***/
	public static IMarker[] assertMarkers(String assertMessage, final IResource resource, int count,
			final Predicate<IMarker> markerPredicate) throws CoreException {

		return assertMarkers(assertMessage, resource, MarkerTypes.ANY_VALIDATION, count, markerPredicate);
	}

	/***/
	public static IMarker[] assertMarkers(String assertMessage, final IResource resource, String markerType, int count)
			throws CoreException {
		return assertMarkers(assertMessage, resource, markerType, count, alwaysTrue());
	}

	/***/
	public static IMarker[] assertMarkers(String assertMessage, final IResource resource, String markerType, int count,
			final Predicate<IMarker> markerPredicate) throws CoreException {
		IMarker[] markers = resource.findMarkers(markerType, true, IResource.DEPTH_INFINITE);
		List<IMarker> markerList = from(Arrays.asList(markers)).filter(markerPredicate).toList();
		if (markerList.size() != count) {
			StringBuilder message = new StringBuilder(assertMessage);
			message.append("\nbut was:");
			for (IMarker marker : markerList) {
				message.append("\n");
				message.append("line " + MarkerUtilities.getLineNumber(marker) + ": ");
				message.append(marker.getAttribute(IMarker.MESSAGE, "<no message>"));
			}
			Assert.assertEquals(message.toString(), count, markers.length);
		}
		return markers;
	}

	/***/
	public static void deleteProject(IProject project) throws CoreException {
		project.delete(true, true, new NullProgressMonitor());
	}
}
