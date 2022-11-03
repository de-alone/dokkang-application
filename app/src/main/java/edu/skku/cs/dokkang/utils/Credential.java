package edu.skku.cs.dokkang.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

public class Credential {
    private static final String authPref = "AuthorizationPref";
    private final Context context;

    public Credential(Context context) {
        this.context = context;
    }

    public void saveCredentials(Long user_id, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(authPref, Context.MODE_PRIVATE).edit();
        editor.putString("user_id", String.valueOf(user_id));
        editor.putString("token",    token);
        editor.commit();
    }

    public Pair<Long, String> loadCredentials() {
        SharedPreferences sp = context.getSharedPreferences(authPref, Context.MODE_PRIVATE);
        String user_id = sp.getString("user_id", null);
        String token = sp.getString("token", null);

        if (token == null || user_id == null ) {
            return null;
        }
        return Pair.create(Long.parseLong(user_id), token);
    }

    public void resetCredentials() {
        SharedPreferences.Editor editor = context.getSharedPreferences(authPref, Context.MODE_PRIVATE).edit();
        editor.remove("user_id");
        editor.remove("token");
        editor.commit();
    }
}
