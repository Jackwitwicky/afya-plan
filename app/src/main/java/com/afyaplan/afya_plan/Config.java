package com.afyaplan.afya_plan;

/**
 * Created by sefu on 1/22/2017.
 */

public class Config {
    //URL to our login.php file
    public static final String LOGIN_URL = " http://ethy.co.ke/Afya-Plan/api/Login.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String PHONE_SHARED_PREF = "phone";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    public static final String TWITTER_KEY = "HMBD9lbejDkFtutX35X9TMMAu";
    public static final String TWITTER_SECRET = "qrK7hKcQXsG7Hk2ObjZ41Etcg5nakgxhqCxrf308H8yWSZ6tqP";
}