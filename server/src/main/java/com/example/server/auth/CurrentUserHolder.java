package com.example.server.auth;

public class CurrentUserHolder {

    private static final ThreadLocal<CurrentUser> currentUser = new ThreadLocal<>();

    public static void set(CurrentUser user) {
        currentUser.set(user);
    }

    public static Long getUserId() {
        return currentUser.get().getId();
    }

    public static UserType getUserType() {
        return currentUser.get().getType();
    }
}
