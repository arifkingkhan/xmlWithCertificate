package com.ngb.global;
import com.ngb.xml.uiWorker.LoginWorker;



public class Constants {
   public static LoginWorker loginWorker;
   public static String userName;
   public static String passWord;

    public LoginWorker getLoginWorker() {
        return loginWorker;
    }

    public void setLoginWorker(LoginWorker loginWorker) {
        this.loginWorker = loginWorker;
    }
}
