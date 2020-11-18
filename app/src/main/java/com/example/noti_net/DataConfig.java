package com.example.noti_net;

public class DataConfig
{
    public String urlServer;
    public static String name;
    public static String phoneNumber;
    public static String email;
    public static String password;
    public static String serverURL = "http://192.168.1.128:8080";

    public DataConfig()
    {
        urlServer = "http://192.168.1.128:8080";
    }

    public String getUrlServer()
    {
        return urlServer;
    }

    public static String getName()
    {
        return name;
    }

    public static String getPhoneNumber()
    {
        return phoneNumber;
    }

    public static String getEmail()
    {
        return email;
    }

    public static String getPassword() { return password; }

    public static String getServerURL() { return serverURL; }

    public static void setName(String nameS)
    {
        name = nameS;
    }

    public static void setPhoneNumber(String phoneNumberS)
    {
        phoneNumber = phoneNumberS;
    }

    public static void setEmail(String emailS)
    {
        email = emailS;
    }

    public static void setPassword(String passwordS) { password = passwordS; }
}
