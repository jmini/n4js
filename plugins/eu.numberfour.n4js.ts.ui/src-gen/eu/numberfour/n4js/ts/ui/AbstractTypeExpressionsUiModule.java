
/*
 * generated by Xtext
 */
package eu.numberfour.n4js.ts.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Manual modifications go to {eu.numberfour.n4js.ts.ui.TypeExpressionsUiModule}
 */
@SuppressWarnings("all")
public abstract class AbstractTypeExpressionsUiModule extends org.eclipse.xtext.ui.DefaultUiModule {
	
	public AbstractTypeExpressionsUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	
	// contributed by org.eclipse.xtext.ui.generator.ImplicitUiFragment
	public com.google.inject.Provider<org.eclipse.xtext.resource.containers.IAllContainersState> provideIAllContainersState() {
		return org.eclipse.xtext.ui.shared.Access.getJavaProjectsState();
	}

	// contributed by org.eclipse.xtext.ui.generator.contentAssist.ContentAssistFragment
	public Class<? extends org.eclipse.xtext.ui.editor.contentassist.IContentProposalProvider> bindIContentProposalProvider() {
		return eu.numberfour.n4js.ts.ui.contentassist.TypeExpressionsProposalProvider.class;
	}


}
