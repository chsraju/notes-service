package com.disqo.notes.filter;

public class AuthContext {

	private static ThreadLocal<Long> userIdInstance = new ThreadLocal<>();

	public static void setUserId(Long userId) {
		userIdInstance.set(userId);
	}

	public static Long getUserId() {
		return userIdInstance.get();
	}

	public static void removeUserId() {
		userIdInstance.remove();
	}
}
