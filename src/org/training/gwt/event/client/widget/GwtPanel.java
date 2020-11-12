package org.training.gwt.event.client.widget;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class GwtPanel extends Composite {
	@UiTemplate("GwtPanel.ui.xml")
	interface Binder extends UiBinder<HTMLPanel, GwtPanel> {}
	private static final Binder binder = GWT.create(Binder.class);

   public static class Factory{
	   public Composite create(){
		   GwtPanel panel = new GwtPanel();
		   panel.createWidget();
		   
		   return panel;
	   }
   }
   
   private boolean widgetCreated = false;
	private boolean descriptionDragStarted = false;
	private boolean dataSaved = false;

	private GwtPanel(){		
	}
	
	private void createWidget() {
		if(!widgetCreated){
			HTMLPanel panel = binder.createAndBindUi(this);
			initWidget(panel);
			
			widgetCreated = true;
		}
	}

	@UiField ListBox salutationListBox;
	@UiField TextBox usernameTextBox;
	@UiField TextArea eventTrace;
	@UiField RadioButton femaleOption;
	@UiField RadioButton maleOption;
	@UiField CheckBox premiumMemberCheckBox;
	@UiField ListBox userRoleListBox;
	@UiField TextArea descriptionTextArea;
	@UiField Hidden uniqueId;

	@UiHandler("salutationListBox")
	void onSalutationClick(ClickEvent event) {
		addEventMessage("Salutation ListBox ClickEvent: "+
			((ListBox)event.getSource()).getSelectedValue()
		);
	}

	@UiHandler("salutationListBox")
	void onSalutationChange(ChangeEvent event) {
		addEventMessage("Salutation ListBox ChangeEvent: "+
			((ListBox)event.getSource()).getSelectedValue()
		);
	}

	@UiHandler("usernameTextBox")
	void onUsernameFocus(FocusEvent event) {
		addEventMessage("Username textbox FocusEvent: "+
			((TextBox)event.getSource()).getValue()
		);
	}

	@UiHandler("usernameTextBox")
	void onUsernameBlur(BlurEvent event) {
		addEventMessage("Username textbox BlurEvent: "+
			((TextBox)event.getSource()).getValue()
		);
	}

	@UiHandler("passwordTextBox")
	void onPasswordFocus(FocusEvent event) {
		addEventMessage("Password textbox FocusEvent");
	}

	@UiHandler("passwordTextBox")
	void onPasswordBlur(BlurEvent event) {
		addEventMessage("Password textbox BlurEvent");
	}

	@UiHandler("femaleOption")
	void onFemaleOptionClick(ClickEvent event) {
		addEventMessage("FemaleOption ClickEvent");
	}

	@UiHandler("maleOption")
	void onMaleOptionClick(ClickEvent event) {
		addEventMessage("MaleOption ClickEvent");
	}

	@UiHandler("premiumMemberCheckBox")
	void onPremiumChange(ValueChangeEvent<Boolean> event) {
		addEventMessage("Premium member CheckBox ChangeEvent: " +
			((CheckBox)event.getSource()).getValue()
		);
	}

	@UiHandler("userRoleListBox")
	void onUserRoleClick(ClickEvent event) {
		addEventMessage("User Role ListBox ClickEvent: "+
			retrieveUserRole()
		);
	}

	@UiHandler("userRoleListBox")
	void onUserRoleChange(ChangeEvent event) {
		addEventMessage("User Role ListBox ChangeEvent: " +
			retrieveUserRole()
		);
	}


	@UiHandler("descriptionTextArea")
	void onDescriptionDragStart(DragStartEvent event) {
		if (!descriptionDragStarted) {			
			String data = ((TextArea) event.getSource()).getValue();
			event.setData("description", data);
			addEventMessage("Description TextArea DragStartEvent:"+data);
			descriptionDragStarted = true;
		}
	}

	@UiHandler("descriptionTextArea")
	void onDescriptionDragEnd(DragEndEvent event) {
		addEventMessage("Description TextArea DragEndEvent");
		
		descriptionDragStarted = false;
	}
    
	@UiHandler("eventTrace")
	void onEventTraceDrop(DropEvent event) {
		event.preventDefault();
		String data = event.getData("description");
		addEventMessage("Event Trace TextArea DropEvent: "+data);
	}

	@UiHandler("saveButton")
	void onSaveClick(ClickEvent event) {
		clearEventMessage();
		addEventMessage("Save button ClickEvent: \n" +
			"Salutation: " + salutationListBox.getSelectedValue() + "\n" + 
			"Username: " + usernameTextBox.getValue() + "\n" +
			"Gender: " + retrieveGender() + "\n" +
			"Premium member: "+premiumMemberCheckBox.getValue()+"\n"+
			"User role: " + retrieveUserRole()+"\n"+
			"Description: "+descriptionTextArea.getValue()+"\n"+
			"UniqueId(hidden field): "+ uniqueId.getValue()
		);
		
		dataSaved = true;
	}
	
	private String retrieveGender(){
		String data="";
		
		if(femaleOption.getValue()){
			data = "Female";
		}
		else{
			data = "Male";
		}
		
		return data;
	}
	
	private String retrieveUserRole(){
		int itemNumber = userRoleListBox.getItemCount();
		int index = 0;
		String data = "";
		
		while(index<itemNumber){
			if(userRoleListBox.isItemSelected(index)){
				if(data.equals(""))
					data = userRoleListBox.getValue(index);
				else
					data = data +", " +userRoleListBox.getValue(index);
			}
			
			index++;
		}		
		return data;
	}
	
	@UiHandler("clearTraceButton")
	void onClearTraceClick(ClickEvent event) {
		clearEventMessage();
		addEventMessage("Clear trace button ClickEvent");
	}

	private void clearEventMessage() {
		eventTrace.setText("");
	}

	private void addEventMessage(String data) {
		if(dataSaved){
			clearEventMessage();
			dataSaved = false;
		}
		
		String text = eventTrace.getText();
		text += new Date() + " :" + data;
		eventTrace.setText(text + "\n");
	}
}
