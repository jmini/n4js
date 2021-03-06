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
package eu.numberfour.n4js.scoping.imports;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;

import com.google.common.base.Predicate;

import eu.numberfour.n4js.ts.scoping.N4TSQualifiedNameProvider;
import eu.numberfour.n4js.ts.types.TModule;

/**
 * Adapts {@link ImportedNamespaceAwareLocalScopeProvider} to filter ArgumentsType & EnumBaseType from globalScobe,
 * since it is a VirtualBaseType. <br />
 */
public class N4JSImportedNamespaceAwareLocalScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {

	@Override
	protected List<ImportNormalizer> getImportedNamespaceResolvers(EObject context, boolean ignoreCase) {
		return Collections.emptyList();
	}

	@Override
	protected IScope getLocalElementsScope(IScope parent, final EObject context, final EReference reference) {
		// nothing to do
		return parent;
	}

	@Override
	protected IScope getResourceScope(Resource res, EReference reference) {
		EObject context = res.getContents().get(0);

		// ||-- changed super-impl here:
		// IDE-662 filtering ArgumentsType & EnumBaseType from globalScobe, since it is a VirtualBaseType.
		Predicate<IEObjectDescription> filter = p -> {
			String name = p.getName().toString();
			return !("ArgumentsType".equals(name) || "EnumBaseType".equals(name));
		};
		IScope globalScope = getGlobalScope(res, reference, filter);
		// -- done change --||

		List<ImportNormalizer> normalizers = getImplicitImports(isIgnoreCase(reference));

		// IDE-1735 adding support for static-polyfills:
		TModule module = (TModule) res.getContents().get(1);
		if (module.isStaticPolyfillModule()) { // limit to situations of resources, that contain at least
			// one @StaticPolyfill
			normalizers.add(createImportedNamespaceResolver(module.getQualifiedName() + ".*", false));
		}

		if (!normalizers.isEmpty()) {
			globalScope = createImportScope(globalScope, normalizers, null, reference.getEReferenceType(),
					isIgnoreCase(reference));
		}
		IScope resScope = getResourceScope(globalScope, context, reference);
		return resScope;
	}

	@Override
	protected List<ImportNormalizer> getImplicitImports(boolean ignoreCase) {
		// "#global#
		List<ImportNormalizer> result = newArrayList();
		result.add(createImportedNamespaceResolver(N4TSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT + ".*",
				ignoreCase));
		return result;
	}

}
