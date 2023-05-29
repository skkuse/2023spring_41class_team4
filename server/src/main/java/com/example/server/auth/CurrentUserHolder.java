package com.example.server.auth;

import com.example.server.exceptions.NotAuthenticated;

public class CurrentUserHolder {

    private static final ThreadLocal<CurrentUser> currentUser = new ThreadLocal<>();

    public static void set(CurrentUser user) {
        currentUser.set(user);
    }

    public static Long getUserId() {
        if (currentUser.get() == null) {
            throw new NotAuthenticated();
        }
        return currentUser.get().getId();
    }

    public static UserType getUserType() {
        if (currentUser.get() == null) {
            throw new NotAuthenticated();
        }
        return currentUser.get().getType();
    }

    public static void clear() {
        currentUser.remove();
    }
}
