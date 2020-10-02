package edu.escuelaing.arep.app.model;

public class Usuario {
	private String name;
	private String password;
	
	public Usuario(String newName, String newPassword) {
		name = newName;
		password = newPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
