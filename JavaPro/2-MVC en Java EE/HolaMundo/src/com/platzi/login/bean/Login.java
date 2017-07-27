package com.platzi.login.bean;

import java.io.Serializable;

public class Login implements Serializable {
	
	private String name;
	private String password;
	
	
	public Login() {
		
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
	
	public boolean autenticacion(){
		return (password.equals("holaplatzi"))?true:false;
	}

}
