package com.mobilesorcery.sdk.product.intro;

import java.io.File;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Arrays;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.internal.ide.ChooseWorkspaceData;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.intro.config.IIntroContentProviderSite;
import org.eclipse.ui.intro.config.IIntroXHTMLContentProvider;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.mobilesorcery.sdk.core.MoSyncTool;

public class RecentProjectsContentProvider extends LinkContentProvider {

	private static final int MAX_WS_COUNT = 3;

	public void createContent(String id, Element parent) {
		ChooseWorkspaceData data = new ChooseWorkspaceData("");
		String[] recentWS = data.getRecentWorkspaces();
		int wsCount = 0;

		addBreak(parent);
		
		for (int i = 0; i < recentWS.length; i++) {
			if (recentWS[i] != null && wsCount < MAX_WS_COUNT && !isExampleWorkspace(recentWS[i])) {
				createWorkspaceLink(recentWS[i], parent);
				wsCount++;
			}
		}

		// Always add the example workspace at the bottom.
		if (wsCount > 0) {
			addBreak(parent);
		}
		createWorkspaceLink(getExampleWorkspace().getAbsolutePath(), parent);

	}

	private void addBreak(Element parent) {
		Element br = parent.getOwnerDocument().createElement("br");
		parent.appendChild(br);	
	}
	
	private void createWorkspaceLink(String ws, Element parent) {
		createActionLink("com.mobilesorcery.sdk.product.intro.actions.SwitchWorkspaceAction", ws, getWorkspaceName(ws), parent, 1);		
	}


	private String getWorkspaceName(String ws) {
		if (isExampleWorkspace(ws)) {
			return "Example Workspace";
		} else {
			return new Path(ws).lastSegment();
		}
	}

	private File getExampleWorkspace() {
		return MoSyncTool.getDefault().getMoSyncHome().append("examples").toFile();	
	}
	
	private boolean isExampleWorkspace(String ws) {
		File wsFile = new File(ws);
		File exampleWSFile = getExampleWorkspace(); 	
		return wsFile.equals(exampleWSFile);
	}

	public void createContent(String id, PrintWriter out) {
	}

	public void dispose() {
	}

	public void init(IIntroContentProviderSite site) {
	}

	public void createContent(String id, Composite parent, FormToolkit toolkit) {
	}

}
