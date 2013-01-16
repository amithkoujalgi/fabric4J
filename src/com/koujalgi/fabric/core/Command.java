package com.koujalgi.fabric.core;

public class Command {
	public static String exec(String cmd) {
		return "run('" + cmd + "')";
	}

	/**
	 * Run commands on local machine
	 * 
	 * @param cmd
	 * @param capture
	 * @return
	 */
	public static String local(String cmd, boolean capture) {
		if (capture) {
			return "local('" + cmd + "', capture=True)";
		}
		return "local('" + cmd + "', capture=False)";
	}

	public static String sudo(String cmd) {
		return "sudo('" + cmd + "')";
	}

	/**
	 * Some remote systems may be configured to disallow sudo access unless a
	 * terminal or pseudoterminal is being used (e.g. when Defaults requiretty
	 * exists in /etc/sudoers.) If updating the remote system’s sudoers
	 * configuration is not possible or desired, you may pass pty=True to sudo
	 * to force allocation of a pseudo tty on the remote end.
	 * 
	 * @see <a
	 *      href="http://fabric.readthedocs.org/en/0.9.0/api/core/operations.html">Fabric
	 *      operations</a>
	 * @param cmd
	 * @return
	 */
	public static String sudoNoPTY(String cmd) {
		return "sudo('" + cmd + "', pty=False)";
	}

	public static String upload(String localPath, String remotePath) {
		return "put('" + localPath + "', '" + remotePath + "/')";
	}

	public static String download(String remotePath, String localPath) {
		return "get('" + remotePath + "', '" + localPath + "')";
	}

	public static String copy(String sourcePath, String destinationPath) {
		return "sudo('cp -r " + sourcePath + " " + destinationPath + "')";
	}

	public static String delete(String targetDirectory) {
		return "sudo('rm -r " + targetDirectory + "')";
	}

	public static String setDirPermsRecursive(String targetDirectory) {
		return "sudo('find " + targetDirectory
				+ " -type d -print0 | xargs -0 chmod 777" + "')";
	}

	public static String setFilePermsRecursive(String targetDirectory) {
		return "sudo('find " + targetDirectory
				+ " -type f -print0 | xargs -0 chmod 777" + "')";
	}
}
