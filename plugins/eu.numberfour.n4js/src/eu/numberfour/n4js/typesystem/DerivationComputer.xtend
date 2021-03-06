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
package eu.numberfour.n4js.typesystem

import com.google.inject.Inject
import eu.numberfour.n4js.typeinference.N4JSTypeInferencer
import eu.numberfour.n4js.xsemantics.N4JSTypeSystem
import eu.numberfour.n4js.ts.typeRefs.FunctionTypeExprOrRef
import eu.numberfour.n4js.ts.typeRefs.FunctionTypeExpression
import eu.numberfour.n4js.ts.typeRefs.TypeRef
import eu.numberfour.n4js.ts.typeRefs.TypeRefsFactory
import eu.numberfour.n4js.ts.types.TypeVariable
import eu.numberfour.n4js.ts.types.TypesFactory
import eu.numberfour.n4js.ts.utils.TypeUtils
import it.xsemantics.runtime.RuleEnvironment

/**
 * Type System Helper Strategy for deriving a new, slightly modified TypeRef from an existing TypeRef.
 * For example, creating a FunctionTypeExpression representing the upper/lower bound of an existing
 * FunctionTypeExprOrRef.
 * <p>
 * Main reason for factoring out the below code from n4js.xsemantics is to avoid code duplication and
 * to have such code closer together that is similar and has to be kept aligned over time.
 * <p>
 * If you change one method, check the others if the same change might be required there as well!
 */
class DerivationComputer extends TypeSystemHelperStrategy {

	@Inject private N4JSTypeSystem ts;
	@Inject private N4JSTypeInferencer typeInferencer;

	private enum BoundType { UPPER, LOWER }


	def FunctionTypeExpression createSubstitutionOfFunctionTypeExprOrRef(RuleEnvironment G, FunctionTypeExprOrRef F) {
		val result = TypeRefsFactory.eINSTANCE.createFunctionTypeExpression

		// let posterity know that the newly created FunctionTypeExpression
		// represents the binding of another FunctionTypeExprOrRef
		result.binding = true

		// if the original 'typeRef' was a FunctionTypeRef, then retain the
		// pointer to its declared type in the copied FunctionTypeExpression
		// (see API doc of FunctionTypeExpression#declaredType for more info)
		result.declaredType = F.functionType

		for(currTV : F.typeVars) {
			if(G.get(currTV)===null) {
				// unbound type variable -> add to 'unboundTypeVars'
				result.unboundTypeVars += currTV;
				// substitution on upper bounds (if required)
				performSubstitutionOnUpperBounds(G, F, currTV, result);
			}
		}

		// substitution on this type
		if (F.declaredThisType !== null) {
			val TypeRef resultDeclaredThisType = typeInferencer.substituteTypeVariables(G,F.declaredThisType);
			result.declaredThisType = TypeUtils.copy(resultDeclaredThisType)
		}

		// substitution on return type
		if (F.returnTypeRef !== null) {
			val TypeRef resultReturnTypeRef = typeInferencer.substituteTypeVariables(G,F.returnTypeRef);
			result.returnTypeRef = TypeUtils.copyIfContained(resultReturnTypeRef)
		}

		// substitution on parameter types
		for (fpar : F.fpars) {
			if(fpar !== null) {
				val newPar = TypesFactory.eINSTANCE.createTFormalParameter
				newPar.name = fpar.name
				newPar.variadic = fpar.variadic

				if(fpar.typeRef !== null) {
					val TypeRef resultParTypeRef = typeInferencer.substituteTypeVariables(G,fpar.typeRef);
					newPar.typeRef = TypeUtils.copyIfContained(resultParTypeRef)
				}

				result.fpars += newPar
			}
			else {
				result.fpars.add(null);
			}
		}

		copyTypeModifiers(result, F)

		return result
	}


	def FunctionTypeExpression createUpperBoundOfFunctionTypeExprOrRef(RuleEnvironment G, FunctionTypeExprOrRef F) {
		createBoundOfFunctionTypeExprOrRef(G,F,BoundType.UPPER);
	}
	def FunctionTypeExpression createLowerBoundOfFunctionTypeExprOrRef(RuleEnvironment G, FunctionTypeExprOrRef F) {
		createBoundOfFunctionTypeExprOrRef(G,F,BoundType.LOWER);
	}
	private def FunctionTypeExpression createBoundOfFunctionTypeExprOrRef(RuleEnvironment G, FunctionTypeExprOrRef F, BoundType boundType) {
		val result = TypeRefsFactory.eINSTANCE.createFunctionTypeExpression

		// let posterity know that the newly created FunctionTypeExpression
		// represents the binding of another FunctionTypeExprOrRef
		result.binding = true

		// retain the pointer to declared type of original FunctionTypeExprOrRef (if any)
		// (see API doc of FunctionTypeExpression#declaredType for more info)
		result.declaredType = F.functionType

		result.unboundTypeVars += F.typeVars;
		if(F instanceof FunctionTypeExpression) {
			result.unboundTypeVarsUpperBounds += TypeUtils.copyAll(F.unboundTypeVarsUpperBounds);
		}

		if (F.declaredThisType !== null) {
			result.declaredThisType =
				TypeUtils.copy(F.declaredThisType);
		}

		// upper/lower bound of return type
		if (F.returnTypeRef !== null) {
			val resultReturnTypeRef = switch(boundType) {
				case UPPER: ts.upperBound(G,F.returnTypeRef).value
				case LOWER: ts.lowerBound(G,F.returnTypeRef).value
			};
			result.returnTypeRef =
				TypeUtils.copyIfContained(resultReturnTypeRef);
		}

		// lower/upper bounds of parameter types
		for (fpar : F.fpars) {
			if(fpar !== null) {
				val newPar = TypesFactory.eINSTANCE.createTFormalParameter
				newPar.name = fpar.name
				newPar.variadic = fpar.variadic

				if(fpar.typeRef !== null) {
					val resultParTypeRef = switch(boundType) {
						case UPPER: ts.lowerBound(G,fpar.typeRef).value
						case LOWER: ts.upperBound(G,fpar.typeRef).value
					};
					newPar.typeRef = TypeUtils.copyIfContained(resultParTypeRef)
				}

				result.fpars += newPar;
			}
			else {
				result.fpars.add(null);
			}
		}

		copyTypeModifiers(result, F);

		return result;
	}


	/**
	 * Performing substitution on the upper bounds of an unbound(!) type variable is non-trivial, because we aren't
	 * allowed to copy the type variable and change its upper bounds (short version: a type variable is a type and
	 * therefore needs to be contained in a Resource; but our new FunctionTypeExpression 'result' is a TypeRef which
	 * may not be contained in any Resource).
	 * <p>
	 * If type variable substitution on any of <code>currTV</code>'s upper bounds leads to a change of that upper
	 * bound (and only then), the modified upper bound will be stored in property 'unboundTypeVarsUpperBounds' of
	 * <code>result</code>.
	 * <p>
	 * This has to be carefully aligned with {@link FunctionTypeExpression#getUnboundTypeVarsUpperBounds()} and
	 * {@link FunctionTypeExpression#getTypeVarUpperBounds(TypeVariable)}.
	 */
	def private void performSubstitutionOnUpperBounds(RuleEnvironment G, FunctionTypeExprOrRef F, TypeVariable currTV,
		FunctionTypeExpression result) {

		if(!currTV.declaredUpperBounds.empty) {
			val oldUBs = F.getTypeVarUpperBounds(currTV);
			val newUBs = oldUBs.map[typeInferencer.substituteTypeVariables(G,it)].toList;
			val unchanged = (newUBs == currTV.declaredUpperBounds); // note: identity compare for the elements is what we want
			if(!unchanged) {
				val idx = result.unboundTypeVars.indexOf(currTV);
				while(result.unboundTypeVarsUpperBounds.size<idx) {
					result.unboundTypeVarsUpperBounds.add(null); // add 'null' as padding entry
				}
				val ubSubst = if(newUBs.size===1) {
					newUBs.get(0)
				} else {
					createIntersectionType(G, newUBs) // in case of several upper bounds -> create intersection
				};
				result.unboundTypeVarsUpperBounds += ubSubst;
			} else {
				// upper bound after substitution is identical to the one stored in type variable; no need to copy it
				// over to 'result' because operation FunctionTypeExpression#getTypeVarUpperBounds() will use currTV's
				// original upper bound if 'unboundTypeVarsUpperBounds' doesn't contain an upper bound for currTV
			}
		}
	}
}
