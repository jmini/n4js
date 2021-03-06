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
package eu.numberfour.n4js.transpiler.print;

import static eu.numberfour.n4js.transpiler.utils.TranspilerUtils.isLegalIdentifier;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;

import eu.numberfour.n4js.conversion.N4JSStringValueConverter;
import eu.numberfour.n4js.n4JS.AdditiveExpression;
import eu.numberfour.n4js.n4JS.Annotation;
import eu.numberfour.n4js.n4JS.ArrayElement;
import eu.numberfour.n4js.n4JS.ArrayLiteral;
import eu.numberfour.n4js.n4JS.ArrayPadding;
import eu.numberfour.n4js.n4JS.ArrowFunction;
import eu.numberfour.n4js.n4JS.AssignmentExpression;
import eu.numberfour.n4js.n4JS.BinaryBitwiseExpression;
import eu.numberfour.n4js.n4JS.BinaryLogicalExpression;
import eu.numberfour.n4js.n4JS.Block;
import eu.numberfour.n4js.n4JS.BooleanLiteral;
import eu.numberfour.n4js.n4JS.BreakStatement;
import eu.numberfour.n4js.n4JS.CaseClause;
import eu.numberfour.n4js.n4JS.CastExpression;
import eu.numberfour.n4js.n4JS.CatchBlock;
import eu.numberfour.n4js.n4JS.CatchVariable;
import eu.numberfour.n4js.n4JS.CommaExpression;
import eu.numberfour.n4js.n4JS.ConditionalExpression;
import eu.numberfour.n4js.n4JS.ContinueStatement;
import eu.numberfour.n4js.n4JS.DebuggerStatement;
import eu.numberfour.n4js.n4JS.DefaultClause;
import eu.numberfour.n4js.n4JS.DoStatement;
import eu.numberfour.n4js.n4JS.DoubleLiteral;
import eu.numberfour.n4js.n4JS.EmptyStatement;
import eu.numberfour.n4js.n4JS.EqualityExpression;
import eu.numberfour.n4js.n4JS.ExportDeclaration;
import eu.numberfour.n4js.n4JS.ExportedVariableDeclaration;
import eu.numberfour.n4js.n4JS.ExportedVariableStatement;
import eu.numberfour.n4js.n4JS.Expression;
import eu.numberfour.n4js.n4JS.ExpressionStatement;
import eu.numberfour.n4js.n4JS.FinallyBlock;
import eu.numberfour.n4js.n4JS.ForStatement;
import eu.numberfour.n4js.n4JS.FormalParameter;
import eu.numberfour.n4js.n4JS.FunctionDeclaration;
import eu.numberfour.n4js.n4JS.FunctionExpression;
import eu.numberfour.n4js.n4JS.FunctionOrFieldAccessor;
import eu.numberfour.n4js.n4JS.HexIntLiteral;
import eu.numberfour.n4js.n4JS.IdentifierRef;
import eu.numberfour.n4js.n4JS.IfStatement;
import eu.numberfour.n4js.n4JS.ImportDeclaration;
import eu.numberfour.n4js.n4JS.IndexedAccessExpression;
import eu.numberfour.n4js.n4JS.IntLiteral;
import eu.numberfour.n4js.n4JS.LabelledStatement;
import eu.numberfour.n4js.n4JS.LocalArgumentsVariable;
import eu.numberfour.n4js.n4JS.MultiplicativeExpression;
import eu.numberfour.n4js.n4JS.N4Modifier;
import eu.numberfour.n4js.n4JS.NamedImportSpecifier;
import eu.numberfour.n4js.n4JS.NamespaceImportSpecifier;
import eu.numberfour.n4js.n4JS.NewExpression;
import eu.numberfour.n4js.n4JS.NullLiteral;
import eu.numberfour.n4js.n4JS.NumericLiteral;
import eu.numberfour.n4js.n4JS.ObjectLiteral;
import eu.numberfour.n4js.n4JS.OctalIntLiteral;
import eu.numberfour.n4js.n4JS.ParameterizedCallExpression;
import eu.numberfour.n4js.n4JS.ParameterizedPropertyAccessExpression;
import eu.numberfour.n4js.n4JS.ParenExpression;
import eu.numberfour.n4js.n4JS.PostfixExpression;
import eu.numberfour.n4js.n4JS.PropertyAssignmentAnnotationList;
import eu.numberfour.n4js.n4JS.PropertyGetterDeclaration;
import eu.numberfour.n4js.n4JS.PropertyMethodDeclaration;
import eu.numberfour.n4js.n4JS.PropertyNameOwner;
import eu.numberfour.n4js.n4JS.PropertyNameValuePair;
import eu.numberfour.n4js.n4JS.PropertySetterDeclaration;
import eu.numberfour.n4js.n4JS.RegularExpressionLiteral;
import eu.numberfour.n4js.n4JS.RelationalExpression;
import eu.numberfour.n4js.n4JS.ReturnStatement;
import eu.numberfour.n4js.n4JS.ScientificIntLiteral;
import eu.numberfour.n4js.n4JS.Script;
import eu.numberfour.n4js.n4JS.ShiftExpression;
import eu.numberfour.n4js.n4JS.Statement;
import eu.numberfour.n4js.n4JS.StringLiteral;
import eu.numberfour.n4js.n4JS.SuperLiteral;
import eu.numberfour.n4js.n4JS.SwitchStatement;
import eu.numberfour.n4js.n4JS.ThisLiteral;
import eu.numberfour.n4js.n4JS.ThrowStatement;
import eu.numberfour.n4js.n4JS.TryStatement;
import eu.numberfour.n4js.n4JS.UnaryExpression;
import eu.numberfour.n4js.n4JS.UnaryOperator;
import eu.numberfour.n4js.n4JS.VariableDeclaration;
import eu.numberfour.n4js.n4JS.VariableStatement;
import eu.numberfour.n4js.n4JS.VariableStatementKeyword;
import eu.numberfour.n4js.n4JS.WhileStatement;
import eu.numberfour.n4js.n4JS.WithStatement;
import eu.numberfour.n4js.n4JS.YieldExpression;
import eu.numberfour.n4js.n4JS.util.N4JSSwitch;
import eu.numberfour.n4js.transpiler.TranspilerState;
import eu.numberfour.n4js.transpiler.im.IdentifierRef_IM;
import eu.numberfour.n4js.transpiler.im.ImPackage;
import eu.numberfour.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import eu.numberfour.n4js.transpiler.im.Script_IM;
import eu.numberfour.n4js.transpiler.im.Snippet;
import eu.numberfour.n4js.transpiler.im.SymbolTableEntry;
import eu.numberfour.n4js.utils.N4JSLanguageUtils;
import eu.numberfour.n4js.ts.typeRefs.TypeArgument;
import eu.numberfour.n4js.ts.typeRefs.TypeRef;
import eu.numberfour.n4js.ts.types.TypeVariable;

/**
 * Traverses an intermediate model and serializes it to a {@link SourceMapAwareAppendable}. Client code should only use
 * the static method {@link #append(SourceMapAwareAppendable, TranspilerState)}.
 */
/* package */ final class PrettyPrinterSwitch extends N4JSSwitch<Boolean> {

	/**
	 * Appends the given transpiler state's intermediate model to the given {@link SourceMapAwareAppendable}.
	 */
	public static void append(SourceMapAwareAppendable out, TranspilerState state) {
		final PrettyPrinterSwitch theSwitch = new PrettyPrinterSwitch(out);
		theSwitch.doSwitch(state.im);
	}

	/** Value to be returned from case-methods to indicate that processing is completed and should not be continued. */
	private static final Boolean DONE = Boolean.TRUE;

	private final SourceMapAwareAppendable out;

	private final N4JSStringValueConverter stringConverter;

	private PrettyPrinterSwitch(SourceMapAwareAppendable out) {
		this.out = out;
		this.stringConverter = new N4JSStringValueConverter();
	}

	@Override
	protected Boolean doSwitch(EClass eClass, EObject eObject) {
		// here we can check for entities of IM.xcore that do not have a super-class in n4js.xcore
		if (eClass == ImPackage.eINSTANCE.getSnippet()) {
			return caseSnippet((Snippet) eObject);
		}
		return super.doSwitch(eClass, eObject);
	}

	@Override
	protected Boolean doSwitch(int classifierID, EObject elemInIM) {
		out.openRegion(elemInIM);
		try {
			final Boolean result = super.doSwitch(classifierID, elemInIM);
			return result;
		} finally {
			out.closeRegion(elemInIM);
		}
	}

	@Override
	public Boolean defaultCase(EObject object) {
		throw new IllegalStateException(
				"PrettyPrinterSwitch missing a case for objects of type " + object.eClass().getName());
	}

	@Override
	public Boolean caseScript(Script original) {
		final Script_IM original_IM = (Script_IM) original;
		processAnnotations(original_IM.getAnnotations());
		process(original_IM.getScriptElements(), () -> {
			newLine();
		});
		return DONE;
	}

	@Override
	public Boolean caseExportDeclaration(ExportDeclaration original) {
		throwUnsupportedSyntax();
		processAnnotations(original.getAnnotations());
		write("export ");
		process(original.getExportedElement());
		return DONE;
	}

	@Override
	public Boolean caseImportDeclaration(ImportDeclaration original) {
		throwUnsupportedSyntax();
		processAnnotations(original.getAnnotations());
		write("import ");
		process(original.getImportSpecifiers(), ", ");
		write(" from ");
		write(quote(original.getModule().getQualifiedName()));
		newLine();
		return DONE;
	}

	@Override
	public Boolean caseNamedImportSpecifier(NamedImportSpecifier original) {
		throwUnsupportedSyntax();
		write(original.getImportedElement().getName()); // need to use symbol table entry here???
		final String alias = original.getAlias();
		if (alias != null) {
			write(" as ");
			write(alias);
		}
		return DONE;
	}

	@Override
	public Boolean caseNamespaceImportSpecifier(NamespaceImportSpecifier original) {
		throwUnsupportedSyntax();
		write("* as ");
		write(original.getAlias());
		return DONE;
	}

	@Override
	public Boolean caseFunctionDeclaration(FunctionDeclaration original) {
		processAnnotations(original.getAnnotations());
		if (!original.getDeclaredModifiers().isEmpty()) {
			processModifiers(original.getDeclaredModifiers());
			write(' ');
		}
		write("function ");
		if (!original.getTypeVars().isEmpty()) {
			processTypeParams(original.getTypeVars());
			write(' ');
		}
		write(original.getName());
		write('(');
		process(original.getFpars(), ", ");
		write(") ");
		if (original.getReturnTypeRef() != null) {
			processReturnTypeRef(original.getReturnTypeRef());
			write(' ');
		}
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean caseFunctionExpression(FunctionExpression original) {
		processAnnotations(original.getAnnotations());
		write("function");
		if (!original.getTypeVars().isEmpty()) {
			write(' ');
			processTypeParams(original.getTypeVars());
		}
		if (original.isGenerator()) {
			write('*');
		}
		if (original.getName() != null) {
			write(' ');
			write(original.getName());
		}
		write('(');
		process(original.getFpars(), ", ");
		write(") ");
		if (original.getReturnTypeRef() != null) {
			processReturnTypeRef(original.getReturnTypeRef());
			write(' ');
		}
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean caseArrowFunction(ArrowFunction original) {
		throwUnsupportedSyntax();
		if (original.isAsync()) {
			write("async");
		}
		write('(');
		process(original.getFpars(), ", ");
		write(')');
		processReturnTypeRef(original.getReturnTypeRef());
		write("=>");
		if (original.isHasBracesAroundBody()) {
			process(original.getBody());
		} else {
			process(original.getBody().getStatements().get(0));
		}
		return DONE;
	}

	@Override
	public Boolean caseLocalArgumentsVariable(LocalArgumentsVariable original) {
		// ignore
		return DONE;
	}

	@Override
	public Boolean caseFormalParameter(FormalParameter original) {
		processAnnotations(original.getAnnotations(), false);
		write(original.getName());
		processTypeRef(original.getDeclaredTypeRef());
		return DONE;
	}

	@Override
	public Boolean caseBlock(Block original) {
		processBlock(original.getStatements());
		return DONE;
	}

	@Override
	public Boolean caseVariableStatement(VariableStatement original) {
		write(keyword(original.getVarStmtKeyword()));
		write(' ');
		process(original.getVarDeclsOrBindings(), ", ");
		// alternative to previous line would be:
		// out.indent();
		// process(original.getVarDeclsOrBindings(), () -> {
		// write(',');
		// newLine();
		// });
		// out.undent();
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseExportedVariableStatement(ExportedVariableStatement original) {
		if (!original.getDeclaredModifiers().isEmpty()) {
			processModifiers(original.getDeclaredModifiers());
			write(' ');
		}
		write("export ");
		caseVariableStatement(original);
		return DONE;
	}

	private String keyword(VariableStatementKeyword varStmtKeyword) {
		switch (varStmtKeyword) {
		case LET:
			return "let";
		case CONST:
			return "const";
		case VAR:
			return "var";
		default:
			throw new UnsupportedOperationException("unsupported variable statement keyword");
		}
	}

	@Override
	public Boolean caseVariableDeclaration(VariableDeclaration original) {
		processAnnotations(original.getAnnotations());
		write(original.getName());
		processTypeRef(original.getDeclaredTypeRef());
		if (original.getExpression() != null) {
			write(" = ");
			process(original.getExpression());
		}
		return DONE;
	}

	@Override
	public Boolean caseExportedVariableDeclaration(ExportedVariableDeclaration original) {
		caseVariableDeclaration(original);
		return DONE;
	}

	@Override
	public Boolean caseEmptyStatement(EmptyStatement original) {
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseExpressionStatement(ExpressionStatement original) {
		process(original.getExpression());
		if (!(original.getExpression() instanceof Snippet))
			write(';');
		return DONE;
	}

	@Override
	public Boolean caseIfStatement(IfStatement original) {
		write("if (");
		process(original.getExpression());
		write(") ");
		final Statement ifStmnt = original.getIfStmt();
		processInBlock(ifStmnt);
		final Statement elseStmnt = original.getElseStmt();
		if (elseStmnt != null) {
			write(" else ");
			if (elseStmnt instanceof IfStatement) {
				process(elseStmnt); // don't enforce block in this case to better support "else if"
			} else {
				processInBlock(elseStmnt);
			}
		}
		return DONE;
	}

	@Override
	public Boolean caseDoStatement(DoStatement original) {
		write("do ");
		processInBlock(original.getStatement());
		write(" while(");
		process(original.getExpression());
		write(");");
		return DONE;
	}

	@Override
	public Boolean caseWhileStatement(WhileStatement original) {
		write("while(");
		process(original.getExpression());
		write(") ");
		processInBlock(original.getStatement());
		return DONE;
	}

	@Override
	public Boolean caseForStatement(ForStatement original) {
		write("for(");
		if (!original.getVarDeclsOrBindings().isEmpty()) {
			write(keyword(original.getVarStmtKeyword()));
			write(' ');
			process(original.getVarDeclsOrBindings(), ", ");
		} else if (original.getInitExpr() != null) {
			process(original.getInitExpr());
		}
		if (original.isForPlain()) {
			write(';');
			processIfNonNull(original.getExpression());
			write(';');
			processIfNonNull(original.getUpdateExpr());
		} else {
			write(original.isForOf() ? " of " : " in ");
			process(original.getExpression());
		}
		write(") ");
		processInBlock(original.getStatement());
		return DONE;
	}

	@Override
	public Boolean caseContinueStatement(ContinueStatement original) {
		write("continue");
		if (original.getLabel() != null) {
			write(' ');
			write(original.getLabel().getName());
		}
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseBreakStatement(BreakStatement original) {
		write("break");
		if (original.getLabel() != null) {
			write(' ');
			write(original.getLabel().getName());
		}
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseReturnStatement(ReturnStatement original) {
		write("return");
		if (original.getExpression() != null) {
			write(' ');
			process(original.getExpression());
		}
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseWithStatement(WithStatement original) {
		write("with (");
		process(original.getExpression());
		write(") ");
		processInBlock(original.getStatement());
		return DONE;
	}

	@Override
	public Boolean caseSwitchStatement(SwitchStatement original) {
		write("switch(");
		process(original.getExpression());
		write(") ");
		processBlockLike(original.getCases(), '{', null, null, '}');
		return DONE;
	}

	@Override
	public Boolean caseCaseClause(CaseClause original) {
		write("case ");
		process(original.getExpression());
		write(':');
		final boolean isFallthrough = original.getStatements().isEmpty();
		if (!isFallthrough) {
			out.indent();
			newLine();
			process(original.getStatements(), () -> {
				newLine();
			});
			out.undent(); // don't add a NL here!
		}
		return DONE;
	}

	@Override
	public Boolean caseDefaultClause(DefaultClause original) {
		write("default:");
		if (!original.getStatements().isEmpty()) {
			out.indent();
			newLine();
			process(original.getStatements(), () -> {
				newLine();
			});
			out.undent(); // don't add a NL here!
		}
		return DONE;
	}

	@Override
	public Boolean caseLabelledStatement(LabelledStatement original) {
		write(original.getName());
		write(": ");
		process(original.getStatement());
		return DONE;
	}

	@Override
	public Boolean caseThrowStatement(ThrowStatement original) {
		write("throw ");
		process(original.getExpression());
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseTryStatement(TryStatement original) {
		write("try ");
		process(original.getBlock());
		processIfNonNull(original.getCatch());
		processIfNonNull(original.getFinally());
		return DONE;
	}

	@Override
	public Boolean caseCatchBlock(CatchBlock original) {
		write(" catch(");
		process(original.getCatchVariable());
		write(") ");
		process(original.getBlock());
		return DONE;
	}

	@Override
	public Boolean caseCatchVariable(CatchVariable original) {
		write(original.getName());
		return DONE;
	}

	@Override
	public Boolean caseFinallyBlock(FinallyBlock original) {
		write(" finally ");
		process(original.getBlock());
		return DONE;
	}

	@Override
	public Boolean caseDebuggerStatement(DebuggerStatement original) {
		write("debugger");
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseParenExpression(ParenExpression original) {
		write('(');
		process(original.getExpression());
		write(')');
		return DONE;
	}

	@Override
	public Boolean caseIdentifierRef(IdentifierRef original) {
		final IdentifierRef_IM original_IM = (IdentifierRef_IM) original;
		final SymbolTableEntry ste = original_IM.getId_IM();
		write(ste.getName());
		return DONE;
	}

	@Override
	public Boolean caseSuperLiteral(SuperLiteral original) {
		write("super");
		return DONE;
	}

	@Override
	public Boolean caseThisLiteral(ThisLiteral original) {
		write("this");
		return DONE;
	}

	@Override
	public Boolean caseArrayLiteral(ArrayLiteral original) {
		processBlockLike(original.getElements(), '[', ",", null, ']');
		return DONE;
	}

	@Override
	public Boolean caseArrayElement(ArrayElement original) {
		if (original.isSpread())
			write("...");
		process(original.getExpression());
		return DONE;
	}

	@Override
	public Boolean caseArrayPadding(ArrayPadding original) {
		// nothing to emit here (separators are taken care of in #caseArrayLiteral())
		return DONE;
	}

	@Override
	public Boolean caseObjectLiteral(ObjectLiteral original) {
		processBlockLike(original.getPropertyAssignments(), '{', ",", null, '}');
		return DONE;
	}

	@Override
	public Boolean casePropertyAssignmentAnnotationList(PropertyAssignmentAnnotationList original) {
		processAnnotations(original.getAnnotations());
		return DONE;
	}

	@Override
	public Boolean casePropertyNameValuePair(PropertyNameValuePair original) {
		processPropertyName(original);
		write(": ");
		process(original.getExpression());
		return DONE;
	}

	@Override
	public Boolean casePropertyGetterDeclaration(PropertyGetterDeclaration original) {
		write("get ");
		processPropertyName(original);
		write("() ");
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean casePropertySetterDeclaration(PropertySetterDeclaration original) {
		write("set ");
		processPropertyName(original);
		write('(');
		process(original.getFpar());
		write(") ");
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean casePropertyMethodDeclaration(PropertyMethodDeclaration original) {
		processPropertyName(original);
		write('(');
		process(original.getFpars(), ", ");
		write(") ");
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean caseNewExpression(NewExpression original) {
		write("new ");
		process(original.getCallee());
		processTypeArgs(original.getTypeArgs());
		write('(');
		process(original.getArguments(), ", ");
		write(')');
		return DONE;
	}

	@Override
	public Boolean caseParameterizedCallExpression(ParameterizedCallExpression original) {
		processTypeArgs(original.getTypeArgs());
		process(original.getTarget());
		write('(');
		process(original.getArguments(), ", ");
		write(')');
		return DONE;
	}

	@Override
	public Boolean caseIndexedAccessExpression(IndexedAccessExpression original) {
		process(original.getTarget());
		write('[');
		process(original.getIndex());
		write(']');
		return DONE;
	}

	@Override
	public Boolean caseParameterizedPropertyAccessExpression(ParameterizedPropertyAccessExpression original) {
		final ParameterizedPropertyAccessExpression_IM original_IM = (ParameterizedPropertyAccessExpression_IM) original;
		final String propName = original_IM.getPropertyName();
		process(original_IM.getTarget());
		if (isLegalIdentifier(propName)) {
			write('.');
			processTypeArgs(original.getTypeArgs());
			write(propName);
		} else {
			// NOTE: re-writing a property access into an index access, here, is not 100% clean; instead, we could
			// throw an exception here and require an additional (late) AST transformation that transforms all property
			// access expression without legal identifier into access expressions; but this would be overkill.
			write('[');
			writeQuoted(propName);
			write(']');
		}
		return DONE;
	}

	@Override
	public Boolean caseYieldExpression(YieldExpression original) {
		// IDE-2004: parenthesis added w.r.t Firefox-implementation, where yield - used as subexpression e.g. in a list
		// of formal parameters - requires parenthesis around the whole yield-expression.
		write('(');

		write("yield ");
		if (original.isMany())
			write("* ");
		process(original.getExpression());

		write(')');
		return DONE;
	}

	@Override
	public Boolean caseNullLiteral(NullLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseBooleanLiteral(BooleanLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseDoubleLiteral(DoubleLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseIntLiteral(IntLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseOctalIntLiteral(OctalIntLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseHexIntLiteral(HexIntLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseScientificIntLiteral(ScientificIntLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseNumericLiteral(NumericLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseStringLiteral(StringLiteral original) {
		if (original.getRawValue() != null) {
			write(original.getRawValue());
		} else {
			write(quote(original.getValueAsString()));
		}
		return DONE;
	}

	@Override
	public Boolean caseRegularExpressionLiteral(RegularExpressionLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean casePostfixExpression(PostfixExpression original) {
		process(original.getExpression());
		write(original.getOp().getLiteral());
		return DONE;
	}

	@Override
	public Boolean caseUnaryExpression(UnaryExpression original) {
		final UnaryOperator op = original.getOp();
		write(op.getLiteral());
		// the following 3 unary operators require an additional space between operator and operand
		if (op == UnaryOperator.TYPEOF || op == UnaryOperator.DELETE || op == UnaryOperator.VOID) {
			write(' ');
		}
		process(original.getExpression());
		return DONE;
	}

	@Override
	public Boolean caseCastExpression(CastExpression original) {
		process(original.getExpression());
		write(" as ");
		write(original.getTargetTypeRef().getTypeRefAsString());
		return DONE;
	}

	@Override
	public Boolean caseMultiplicativeExpression(MultiplicativeExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseAdditiveExpression(AdditiveExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseShiftExpression(ShiftExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseRelationalExpression(RelationalExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseEqualityExpression(EqualityExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseBinaryBitwiseExpression(BinaryBitwiseExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseBinaryLogicalExpression(BinaryLogicalExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseAssignmentExpression(AssignmentExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseConditionalExpression(ConditionalExpression original) {
		process(original.getExpression());
		write(" ? ");
		process(original.getTrueExpression());
		write(" : ");
		process(original.getFalseExpression());
		return DONE;
	}

	@Override
	public Boolean caseCommaExpression(CommaExpression original) {
		process(original.getExprs(), ", ");
		return DONE;
	}

	public Boolean caseSnippet(Snippet original) {
		String code = original.getCode();
		if (code.endsWith("\n"))
			code = code.substring(0, code.length() - 1);
		write(code);
		return DONE;
	}

	// ###############################################################################################################
	// UTILITY AND CONVENIENCE METHODS

	private void write(char c) {
		try {
			out.append(c);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	private void write(CharSequence csq) {
		try {
			out.append(csq);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	private void writeQuoted(String csq) {
		write(quote(csq));
	}

	private void writeQuotedIfNonIdentifier(String csq) {
		if (!isLegalIdentifier(csq)) {
			writeQuoted(csq);
		} else {
			write(csq);
		}
	}

	private void newLine() {
		try {
			out.newLine();
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	private void process(Iterable<? extends EObject> elemsInIM, String separator) {
		final Iterator<? extends EObject> iter = elemsInIM.iterator();
		while (iter.hasNext()) {
			doSwitch(iter.next());
			if (separator != null && iter.hasNext()) {
				write(separator);
			}
		}
	}

	private void process(Iterable<? extends EObject> elemsInIM, Runnable separator) {
		final Iterator<? extends EObject> iter = elemsInIM.iterator();
		while (iter.hasNext()) {
			process(iter.next());
			if (separator != null && iter.hasNext()) {
				separator.run();
			}
		}
	}

	private void processIfNonNull(EObject elemInIM) {
		if (elemInIM != null) {
			doSwitch(elemInIM);
		}
	}

	private void process(EObject elemInIM) {
		if (elemInIM == null) {
			throw new IllegalArgumentException("element to process may not be null");
		}
		doSwitch(elemInIM);
	}

	private void processAnnotations(Iterable<? extends Annotation> annotations) {
		processAnnotations(annotations, true);
	}

	private void processAnnotations(@SuppressWarnings("unused") Iterable<? extends Annotation> annotations,
			@SuppressWarnings("unused") boolean multiLine) {
		// throw exception if
		// if (annotations.iterator().hasNext()) {
		// throw new IllegalStateException("Annotations left in the code: " + Joiner.on(",").join(annotations));
		// }
	}

	private void processPropertyName(PropertyNameOwner owner) {
		final String propName = owner.getName();
		if (propName.startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX)) {
			// we have a name like "#iterator" that represents a Symbol --> emit as: "[Symbol.iterator]"
			// (note: we have to do this special handling here in the pretty printer because there is, at the moment,
			// no way to represent a property assignment with a Symbol as name other than using a name starting with
			// the SYMBOL_IDENTIFIER_PREFIX)
			write("[Symbol.");
			write(propName.substring(1));
			write(']');
		} else {
			// standard case:
			writeQuotedIfNonIdentifier(owner.getName());
		}

	}

	private void processModifiers(EList<N4Modifier> modifiers) {
		final int len = modifiers.size();
		for (int idx = 0; idx < len; idx++) {
			if (idx > 0) {
				write(' ');
			}
			write(modifiers.get(idx).getName());
		}
	}

	private void processReturnTypeRef(TypeRef returnTypeRef) {
		if (returnTypeRef == null)
			return;

		// In case of plain-JS output no types will be written
		throw new IllegalStateException("Return type reference still left in code. typeref=" + returnTypeRef + " in "
				+ EcoreUtil2.getContainerOfType(returnTypeRef, FunctionOrFieldAccessor.class));

		// if(returnTypeRef!=null) {
		// write(" : ");
		// process(returnTypeRef);
		// write(' ');
		// }
	}

	private void processTypeRef(TypeRef declaredTypeRef) {
		if (declaredTypeRef == null)
			return;

		// In case of plain-JS output no types will be written
		throw new IllegalStateException("Type reference still left in code. typeRef=" + declaredTypeRef);
	}

	private void processTypeParams(EList<TypeVariable> typeParams) {
		if (typeParams.isEmpty())
			return;

		// In case of plain-JS output no types will be written
		throw new IllegalStateException("Type reference still left in code. typeParams=" + typeParams);
	}

	private void processTypeArgs(EList<? extends TypeArgument> typeArgs) {
		if (typeArgs.isEmpty())
			return;

		// In case of plain-JS output no types will be written
		throw new IllegalStateException("Type arguments still left in code. typeArgs=" + typeArgs);
	}

	private void processInBlock(Statement statement) {
		if (statement instanceof Block) {
			processBlock(((Block) statement).getStatements());
		} else {
			processBlock(Collections.singletonList(statement));
		}
	}

	private void processBlock(Collection<? extends Statement> statements) {
		processBlockLike(statements, '{', null, null, '}');
	}

	/**
	 * Process and indent the given elements in the same way as blocks are indented but using the given characters for
	 * opening and closing the code section.
	 */
	private void processBlockLike(Collection<? extends EObject> elemsInIM, char open, String lineEnd,
			String lastLineEnd, char close) {
		if (elemsInIM.isEmpty()) {
			write(open);
			write(close);
			return;
		}
		write(open);
		out.indent();
		newLine();
		process(elemsInIM, () -> {
			if (lineEnd != null)
				write(lineEnd);
			newLine();
		});
		if (lastLineEnd != null)
			write(lineEnd);
		out.undent();
		newLine();
		write(close);
	}

	private void processBinaryExpression(Expression lhs, String op, Expression rhs) {
		process(lhs);
		write(' ');
		write(op);
		write(' ');
		process(rhs);
	}

	private String quote(String txt) {
		return '\'' + stringConverter.convertToJSString(txt, false) + '\'';
	}

	/**
	 * We call this method in methods that we do not want to delete but aren't used and tests for now.
	 */
	private void throwUnsupportedSyntax() {
		throw new UnsupportedOperationException("syntax not supported by pretty printer");
	}
}
