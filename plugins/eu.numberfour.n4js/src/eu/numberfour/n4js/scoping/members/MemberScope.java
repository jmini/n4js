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
package eu.numberfour.n4js.scoping.members;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.inject.Inject;

import eu.numberfour.n4js.scoping.utils.RestrictedUsageDescription;
import eu.numberfour.n4js.ts.types.ContainerType;
import eu.numberfour.n4js.ts.types.NameAndAccess;
import eu.numberfour.n4js.ts.types.TMember;
import eu.numberfour.n4js.ts.types.VirtualBaseType;
import eu.numberfour.n4js.ts.types.internal.MemberByNameAndAccessMap;
import eu.numberfour.n4js.utils.ContainerTypesHelper;
import eu.numberfour.n4js.validation.JavaScriptVariant;

/**
 * A scope implementation that wraps a type and allows to access its members as scope content.
 */
public class MemberScope extends AbstractMemberScope {

	/**
	 * Emulates a (injector) factory w/o FactoryModuleBuilder
	 */
	public static class MemberScopeFactory {

		@Inject
		ContainerTypesHelper containerTypesHelper;

		/**
		 * Factory method to produce a {@link MemberScope} with the members of the given ContainerType.
		 */
		public IScope create(IScope parent, ContainerType<?> type,
				EObject context, boolean staticAccess) {
			return new MemberScope(containerTypesHelper, parent, type, context, staticAccess);
		}

		/**
		 * Factory method to produce a {@link MemberScope} with the members of the given ContainerType without a parent.
		 */
		public IScope create(ContainerType<?> type, EObject context, boolean staticAccess) {
			return new MemberScope(containerTypesHelper, type, context, staticAccess);
		}

		/**
		 * Factory method to produce a {@link MemberScope} with the members provided in list 'members'.
		 */
		public IScope create(IScope parent,
				List<? extends TMember> members, EObject context, boolean staticAccess) {
			return new MemberScope(containerTypesHelper, parent, members, context, staticAccess);
		}
	}

	final ContainerType<?> type;
	final List<TMember> members;

	MemberByNameAndAccessMap membersByNameAndAccess;

	// @Inject -- too bad that we have no @Assisted features available...
	final ContainerTypesHelper containerTypesHelper;

	MemberScope(ContainerTypesHelper containerTypesHelper, IScope parent,
			List<? extends TMember> members, EObject context,
			boolean staticAccess) {
		super(parent, context, staticAccess);
		this.containerTypesHelper = containerTypesHelper;
		this.type = null;
		this.members = new ArrayList<>(members);
	}

	MemberScope(ContainerTypesHelper containerTypesHelper, IScope parent, ContainerType<?> type,
			EObject context,
			boolean staticAccess) {
		super(parent, context, staticAccess);
		this.containerTypesHelper = containerTypesHelper;
		this.type = type;
		this.members = null;
	}

	MemberScope(ContainerTypesHelper containerTypesHelper, ContainerType<?> type,
			EObject context,
			boolean staticAccess) {
		super(IScope.NULLSCOPE, context, staticAccess);
		this.containerTypesHelper = containerTypesHelper;
		this.type = type;
		this.members = null;
	}

	/**
	 * Returns the member for the given name and access. Subclasses may override to create members lazily, but should
	 * then also override {@link #getMembers()}.
	 */
	@Override
	protected TMember findMember(String name, boolean writeAccess, @SuppressWarnings("hiding") boolean staticAccess) {
		if (type != null) {
			return containerTypesHelper.fromContext(contextResource).findMember(type, name, writeAccess, staticAccess);
		} else {
			if (membersByNameAndAccess == null)
				membersByNameAndAccess = new MemberByNameAndAccessMap(members);
			final NameAndAccess nameAndAccess = new NameAndAccess(name, writeAccess, staticAccess);
			return membersByNameAndAccess.get(nameAndAccess);
		}
	}

	/**
	 * Returns members in this scope. Subclasses may override to create members lazily, but should then also override
	 * {@link #findMember(String, boolean, boolean)}.
	 */
	@Override
	protected Collection<? extends TMember> getMembers() {
		if (type != null) {
			return containerTypesHelper.fromContext(contextResource).members(type);
		} else {
			return members;
		}
	}

	@Override
	protected IEObjectDescription createSingleElementDescription(TMember existingMember) {
		IEObjectDescription description = super.createSingleElementDescription(existingMember);
		// #hack for IDE-662 restriction on arguments.callee in all modes except for unrestricted Javascript.
		if (type instanceof VirtualBaseType) {
			if (type.getName().equals("ArgumentsType") && existingMember.getName().equals("callee")) {
				JavaScriptVariant jsVariant = JavaScriptVariant.getVariant(context);
				if (jsVariant != JavaScriptVariant.unrestricted) {
					return new RestrictedUsageDescription(description, jsVariant);
				}
			}
		}

		return description;
	}
}
