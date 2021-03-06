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
package eu.numberfour.n4js.transpiler;

import java.io.Writer;
import java.nio.file.Path;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import eu.numberfour.n4js.n4JS.Script;
import eu.numberfour.n4js.naming.QualifiedNameComputer;
import eu.numberfour.n4js.resource.N4JSResource;
import eu.numberfour.n4js.transpiler.utils.TranspilerDebugUtils;
import eu.numberfour.n4js.utils.di.scopes.ScopeManager;
import eu.numberfour.n4js.utils.di.scopes.TransformationScoped;

/**
 * Base implementation of all (new) transpilers driving the main work-flow during transpilation, which consists of the
 * following steps<sup>1</sup>:
 * <ol>
 * <li>create initial transpiler state (i.e. create intermediate model, etc.)
 * <li>execute all transformations on the transpiler state
 * <ol type="a">
 * <li>ask transformation manager for transformations to execute
 * <li>give each transformation a chance to perform early analysis on the initial (unchanged!) state
 * <li>actually perform the transformations (in the order defined by transformation manager)
 * </ol>
 * <li>pretty-print the intermediate model + emit source maps (optional)
 * </ol>
 * <p>
 * <sup>1</sup> because we use the term "phases" for something else, we refer to the above as "steps".
 */
public abstract class AbstractTranspiler {

	private static final boolean DEBUG_DUMP_STATE = false;
	private static final boolean DEBUG_DRAW_STATE = false;
	private static final boolean DEBUG_PERFORM_ASSERTIONS = true;
	private static final boolean DEBUG_PERFORM_VALIDATIONS = true;

	@Inject
	private PreparationStep preparationStep;
	@Inject
	private PrettyPrintingStep prettyPrintingStep;
	@Inject
	private ScopeManager scopeManager;

	@Inject
	private QualifiedNameComputer qnComputer;

	/**
	 * To customize the transpilation process, subclasses should here provide the concrete AST transformations to be
	 * executed for the given initial transpiler state in the order they should be executed.
	 */
	protected abstract Transformation[] computeTransformationsToBeExecuted(TranspilerState state);

	/**
	 * Internal class for providing all necessary information about source-map generation.
	 */
	public class SourceMapInfo {

		/** pure file extension for source map, e.g. "map" */
		public String sourceMapFileExtension;

		/** Name of the file which is generated. "A.js" */
		public String simpleCompiledFileName;

		/** Name of the source-map file, e.g. "A.map" */
		public String simpleSourceMapFileName;

		/** Path to the original input-file,relative to output-location. e.g. "A.n4js" */
		public String n4jsFilePath;

		/** */
		public Writer sourceMapBuff;

		/**
		 * if true indicates the lookup in absolute form "/sources/Project-0.0.1/a/b/M.n4js" else lookup relative to
		 * source-map in compile-result file "../../../src/a/b/M.n4js"
		 */
		public boolean isExplicitSourceRef;
		/**
		 * in case of {@code isExplicitSourceRef == true} this path is used as the prefix (e.g. "/sources") for the
		 * Full-Module-Path (e.g."Project-0.0.1/a/b/M.n4js")
		 */
		public Path explicitNavigationToSrc;

		/**
		 * Depending on {@link #isExplicitSourceRef} calculate the Path
		 *
		 * @param eResource
		 *            Module to point to
		 * @return path to the Module.
		 */
		public String resolve(N4JSResource eResource) {
			if (isExplicitSourceRef) {
				//
				String completeSpecifier = qnComputer.getCompleteModuleSpecifier(eResource.getModule());
				String fileExtension = eResource.getURI().fileExtension();
				String specifierAsFile = fileExtension == null ? completeSpecifier
						: completeSpecifier + "." + fileExtension;
				return explicitNavigationToSrc.resolve(specifierAsFile).toString();
			} else {
				// relative resolution, we do not use the actual eResource since we computed the path to the original
				// name.
				return n4jsFilePath;
			}
		}

	}

	/**
	 * Transpile the given resource to the given writers.
	 *
	 * @param resource
	 *            the resource to transpile.
	 * @param outCode
	 *            the writer where the generated output code should be sent.
	 * @param optSourceMapInfo
	 *            meta-info including the writer to create source-maps. If absent do not create source-maps.
	 */
	public void transpile(N4JSResource resource, Writer outCode, Optional<SourceMapInfo> optSourceMapInfo) {

		// step 1: create initial transpiler state (i.e. create intermediate model, etc.)
		final TranspilerState state = prepare(resource);

		// step 2: execute all transformations on the transpiler state
		transform(state);

		// step 3: pretty-print the intermediate model + emit source maps (optional)
		prettyPrint(state, outCode, optSourceMapInfo);
	}

	/**
	 * First step during transpilation. Creates initial transpiler state.
	 */
	protected TranspilerState prepare(N4JSResource resource) {
		final Script script = resource.getScript();
		if (script == null || script.eIsProxy()) {
			throw new IllegalArgumentException("given N4JSResource does not contain a script or script is a proxy");
		}
		return preparationStep.prepare(script);
	}

	/**
	 * Second and main step during transpilation, where the transformations are executed on the intermediate model.
	 */
	protected void transform(TranspilerState state) {
		scopeManager.enter(TransformationScoped.class);
		try {
			scopeManager.bind(TransformationScoped.class, TranspilerState.class, state);

			if (DEBUG_DUMP_STATE) {
				System.out.println("============================== RESOURCE: " + state.resource.getURI()
						+ " ==============================");
			}

			// step 1: ask transformation manager for transformations to execute
			final Transformation[] transformations = computeTransformationsToBeExecuted(state);
			TransformationDependency.assertDependencies(transformations);

			// step 2: give each transformation a chance to perform early analysis on the initial (unchanged!) state
			for (Transformation currT : transformations) {
				currT.analyze();
			}

			// step 3: actually perform the transformations (in the order defined by transformation manager)
			for (Transformation currT : transformations) {

				if (DEBUG_DUMP_STATE) {
					System.out.println("============================== PRE " + currT.getClass().getSimpleName());
					TranspilerDebugUtils.dump(state);
				}
				if (DEBUG_DRAW_STATE) {
					TranspilerDebugUtils.dumpGraph(state, "PRE " + currT.getClass().getSimpleName());
				}

				if (DEBUG_PERFORM_ASSERTIONS) {
					currT.assertPreConditions();
				}

				currT.transform();

				if (DEBUG_PERFORM_ASSERTIONS) {
					currT.assertPostConditions();
				}

				if (DEBUG_PERFORM_VALIDATIONS) {
					TranspilerDebugUtils.validateState(state, true);
				}
			}

			if (DEBUG_DUMP_STATE) {
				System.out.println("============================== POST all transformations");
				TranspilerDebugUtils.dump(state);
			}
			if (DEBUG_DRAW_STATE) {
				TranspilerDebugUtils.dumpGraph(state, "POST all transformations");
			}

		} finally {
			scopeManager.exit(TransformationScoped.class);
		}
	}

	/**
	 * Third and last step during transpilation. Generating output code and source maps (optional).
	 */
	protected void prettyPrint(TranspilerState state, Writer outCode, Optional<SourceMapInfo> optSourceMapInfo) {
		prettyPrintingStep.print(state, outCode, optSourceMapInfo);
	}

	/**
	 * Performs preparation step. Only intended for testing.
	 */
	public TranspilerState testPrepare(N4JSResource resource) {
		return prepare(resource);
	}

	/**
	 * Performs transformation step. Only intended for testing.
	 */
	public void testTransform(TranspilerState state) {
		transform(state);
	}
}
