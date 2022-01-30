package com.disqo.notes.filter;

public class AuthContext {

	private static ThreadLocal<String> userEmailInstance = new ThreadLocal<>();

	public static void setEmail(String email) {
		userEmailInstance.set(email);
	}

	public static String getEmail() {
		return userEmailInstance.get();
	}

	public static void removeEmail() {
		userEmailInstance.remove();
	}
}
