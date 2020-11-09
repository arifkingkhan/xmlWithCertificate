package com.ngb.xml.globals;

import com.ngb.xml.dto.HttpHandlerResponse;
import com.ngb.xml.http.HttpRequestsHandler;

import java.io.IOException;
import java.util.TimerTask;

public class Helper extends TimerTask {

    public void run()
    {
        try {
            HttpHandlerResponse response=HttpRequestsHandler.loginUser(Globals.userName,Globals.password);
            Globals.authToken= response.getMessage();
            System.out.println("Relogin After 25 minutes ,Global authtoken updated"+" "+Globals.userName+Globals.password+" "+Globals.authToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

