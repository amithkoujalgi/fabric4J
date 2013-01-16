package com.koujalgi.fabric.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Execute a native OS command in runtime
 */
public class CommandExecutor {
	String command;

	public void setCommand(String command) {
		this.command = command;
	}

	/*
	 * Execute the given command in native OS environment
	 */
	public String execute() throws IOException, InterruptedException {
		// long timeNow = System.currentTimeMillis();
		Process p = Runtime.getRuntime().exec(command);
		p.waitFor();
		InputStreamReader is = new InputStreamReader(p.getInputStream());
		BufferedReader reader = new BufferedReader(is);
		String line = "", result = "";
		while ((line = reader.readLine()) != null) {
			result += line + "\n";
		}
		is.close();
		p.destroy();
		// result += "Time taken: " + (System.currentTimeMillis() - timeNow)
		// / 1000 + " seconds.";
		return result;
	}
}
