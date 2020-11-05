package com.ngb.xml.uiWorker;

import com.ngb.xml.dto.HttpHandlerResponse;
import com.ngb.xml.http.HttpRequestsHandler;
import com.ngb.xml.ui.XmlUploader;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class LoginWorker extends SwingWorker<Void, HttpHandlerResponse> {
    private String username;
    private String password;
    private JFrame loginForm;

    public LoginWorker(String username, String password, JFrame loginForm) {
        this.username = username;
        this.password = password;
        this.loginForm = loginForm;
    }

    public Void doInBackground() throws Exception {
        HttpHandlerResponse response = HttpRequestsHandler.loginUser(this.username, this.password);
        this.publish(new HttpHandlerResponse[]{response});
        return null;
    }

    public void process(List<HttpHandlerResponse> responseList) {
        HttpHandlerResponse response = (HttpHandlerResponse)responseList.get(responseList.size() - 1);
        if (response.getIsSuccessful()) {
            this.loginForm.dispose();
            (new XmlUploader(this.username, response.getMessage())).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this.loginForm, response.getMessage(), "NGB XML Uploader Login", 0);
        }

    }
}
