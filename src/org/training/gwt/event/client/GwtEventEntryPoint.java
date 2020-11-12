package org.training.gwt.event.client;

import org.training.gwt.event.client.widget.GwtPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

public class GwtEventEntryPoint implements EntryPoint {
	@Override
	public void onModuleLoad() {
		Composite panel = 
			new GwtPanel.Factory().create();
		
		RootPanel.get().add(panel);
	}
}
