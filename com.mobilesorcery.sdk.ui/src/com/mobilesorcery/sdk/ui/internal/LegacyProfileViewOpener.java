package com.mobilesorcery.sdk.ui.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mobilesorcery.sdk.core.CoreMoSyncPlugin;
import com.mobilesorcery.sdk.core.MoSyncProject;
import com.mobilesorcery.sdk.core.MoSyncTool;
import com.mobilesorcery.sdk.ui.MosyncUIPlugin;
import com.mobilesorcery.sdk.ui.internal.properties.MoSyncProjectPropertyPage;

public class LegacyProfileViewOpener implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		final MoSyncProject[] project = new MoSyncProject[1];

		if (event.getPropertyName() == MoSyncProject.PROFILE_MANAGER_TYPE_KEY) {
			project[0] = (MoSyncProject) event.getSource();
		}

		if (event.getPropertyName() == MosyncUIPlugin.CURRENT_PROJECT_CHANGED || project[0] != null) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					IWorkbenchWindow wWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					MoSyncProject currentProject = project[0];
					if (currentProject == null) {
						currentProject = MosyncUIPlugin.getDefault().getCurrentlySelectedProject(wWindow);
					}
					if (currentProject.getProfileManagerType() == MoSyncTool.LEGACY_PROFILE_TYPE) {
						try {
					        wWindow.getActivePage().showView("com.mobilesorcery.sdk.finalizer.ui.view");
							wWindow.getActivePage().showView("com.mobilesorcery.sdk.profiles.ui.view");
						} catch (PartInitException e) {
							CoreMoSyncPlugin.getDefault().log(e);
						}
						MosyncUIPlugin.getDefault().removeListener(LegacyProfileViewOpener.this);
					}
				}
			});
		}
	}

}