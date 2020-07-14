package com.app.sitaramswami

import android.content.Context
import android.content.SharedPreferences
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

/**
 * Created by kartik on 11-May-18.
 */

class SessionManager(var context: Context) {
    val USER_DATA = "user_data"
    val LOGIN_USER = "user_login"
    val Id_DATA = "id_data"
    val Id = "id"

    var pref: SharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
    var prefs: SharedPreferences = context.getSharedPreferences(Id_DATA, Context.MODE_PRIVATE)

    fun saveUser(loginResponse: String) {
        pref.edit().putString(LOGIN_USER, loginResponse).commit()
    }
    fun saveIds(AdsIdResponse: String) {
        prefs.edit().putString(Id, AdsIdResponse).commit()
    }

    fun getIds(): AdsIdResponse? {

        var IdString = prefs.getString(Id, "")

        return if (!IdString.equals(""))
            Gson().fromJson(IdString, AdsIdResponse::class.java)
        else
            null
    }
    fun getUser(): LoginResponse? {

        var userString = pref.getString(LOGIN_USER, "")

        return if (!userString.equals(""))
            Gson().fromJson(userString, LoginResponse::class.java)
        else
            null
    }

    fun logout() {
        pref.edit().putString(LOGIN_USER, null).commit()
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }
}
