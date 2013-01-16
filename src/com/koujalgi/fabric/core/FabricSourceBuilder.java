package com.koujalgi.fabric.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FabricSourceBuilder {
	private ArrayList<Module> modules;
	private String text = "", pemKeyfile = "", username = "", password = "",
			remoteHostAddress = "";

	public FabricSourceBuilder() {
		text = "";
		this.modules = new ArrayList<Module>();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRemoteHostAddress(String remoteHostAddress) {
		this.remoteHostAddress = remoteHostAddress;
	}

	public void addModule(Module module) {
		this.modules.add(module);
	}

	public void setPemKeyfile(String pemKeyfile) {
		this.pemKeyfile = pemKeyfile.replace("\\", "/");
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGeneratedFabicSourceText() {
		text = "";
		text += generateImports();
		text += generateEnvVars();
		text += generateModules();
		return this.text;
	}

	private String generateImports() {
		String imports[] = { "from fabric.api import *",
				"from fabric.operations import sudo, run, put, get" };
		String temp = "";
		for (String importcmd : imports) {
			temp += importcmd + newLine();
		}
		temp += newLine();
		return temp;
	}

	private String generateEnvVars() {
		String temp = "";
		temp += "# These are the env vars for fabric" + newLine();
		temp += "env.user = '" + this.username + "'" + newLine();
		temp += "env.key_filename = '" + this.pemKeyfile + "'" + newLine();
		temp += "env.roledefs = {'server': ['" + this.username + "@"
				+ this.remoteHostAddress + "']}" + newLine();
		return temp;
	}

	private String generateModules() {
		String temp = "";
		for (Module m : modules) {
			temp += newLine() + "def " + m.getModuleName() + "():"
					+ newLineAndIndent();
			temp += "# " + m.getRemarks() + newLineAndIndent();
			if (m.isContinueWithWarnings()) {
				temp += "env.warn_only = True" + newLineAndIndent();
			}
			for (String cmd : m.getCommands()) {
				temp += cmd + newLineAndIndent();
			}
		}
		return temp;
	}

	private String newLine() {
		return "\n";
	}

	@SuppressWarnings("unused")
	private String indent() {
		return "\t";
	}

	private String newLineAndIndent() {
		return "\n\t";
	}

	public void generatePythonSourceFile(File destinationFilePath)
			throws IOException {
		if (!destinationFilePath.getAbsolutePath().endsWith(".py")) {
			destinationFilePath = new File(destinationFilePath + ".py");
		}
		writeFile(destinationFilePath, getGeneratedFabicSourceText());
	}

	public void generateWindowsLaunchScript(File destinationFilePath,
			File executableFile) throws IOException {
		if (!destinationFilePath.getAbsolutePath().endsWith(".bat")) {
			destinationFilePath = new File(destinationFilePath + ".bat");
		}
		String batchFileText = "cd %~dp0" + newLine();
		if (password.equals("")) {
			for (Module m : this.modules) {
				batchFileText += "fab -f " + executableFile.getAbsolutePath()
						+ " -R server " + m.getModuleName() + newLine();
			}
		} else {
			for (Module m : this.modules) {
				batchFileText += "fab -f " + executableFile.getAbsolutePath()
						+ " -p " + this.password + " -R server "
						+ m.getModuleName() + newLine();
			}
		}
		writeFile(destinationFilePath, batchFileText);
	}

	public void generateLinuxLaunchScript(File destinationFilePath,
			File executableFile) throws IOException {
		if (!destinationFilePath.getAbsolutePath().endsWith(".sh")) {
			destinationFilePath = new File(destinationFilePath + ".sh");
		}
		String shellScriptFileText = "#!/bin/sh" + newLine();
		if (password.equals("")) {
			for (Module m : this.modules) {
				shellScriptFileText += "fab -f "
						+ executableFile.getAbsolutePath() + " -R server "
						+ m.getModuleName() + newLine();
			}
		} else {
			for (Module m : this.modules) {
				shellScriptFileText += "fab -f "
						+ executableFile.getAbsolutePath() + " -p "
						+ this.password + " -R server " + m.getModuleName()
						+ newLine();
			}
		}
		writeFile(destinationFilePath, shellScriptFileText);
	}

	private void writeFile(File destinationFilePath, String content)
			throws IOException {
		FileWriter fw = new FileWriter(destinationFilePath.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
		fw.close();
		// System.out.println(">>>Saved to file: ["
		// + destinationFilePath.getAbsolutePath() + "]");
	}
}
