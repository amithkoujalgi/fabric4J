package com.koujalgi.fabric.core;


import java.util.ArrayList;

public class Module {
	private String moduleName;
	private String remarks = "Comment";
	private ArrayList<String> commands;
	private boolean continueWithWarnings = true;

	public Module(String moduleName) {
		this.moduleName = moduleName;
		this.commands = new ArrayList<String>();
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void addCommand(String cmd) {
		this.commands.add(cmd);
	}

	public ArrayList<String> getCommands() {
		return this.commands;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setContinueWithWarnings(boolean continueWithWarnings) {
		this.continueWithWarnings = continueWithWarnings;
	}

	public boolean isContinueWithWarnings() {
		return continueWithWarnings;
	}
}
