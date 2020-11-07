package com.ngb.xml.ui;

import com.ngb.global.Constants;
import com.ngb.xml.http.HttpRequestsHandler;
import com.ngb.xml.uiWorker.LoginWorker;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.UIManager.LookAndFeelInfo;
import org.apache.commons.lang3.StringUtils;

public class LoginForm extends JFrame {
    static  Constants cons = new Constants();
    private LoginWorker loginWorker;
    private JButton btnLogin;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    private JLabel lblPassword;
    private JLabel lblUsername;
    private JPasswordField txtPassword;
    private JTextField txtUsername;

    public static Constants getCons() {
        return cons;
    }

    public static void setCons(Constants cons) {
        LoginForm.cons = cons;
    }

    public LoginForm() {
        this.initComponents();
        if (!HttpRequestsHandler.initializeHttpRequestHandler()) {
            JOptionPane.showMessageDialog(this, "Couldn't initialize the HTTP client! Terminating application", "NGB XML Uploader Login", 0);
            System.exit(1);
        }

    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.jTextArea1 = new JTextArea();
        this.jPanel1 = new JPanel();
        this.jPanel3 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jPanel2 = new JPanel();
        this.lblUsername = new JLabel();
        this.lblPassword = new JLabel();
        this.txtUsername = new JTextField();
        this.btnLogin = new JButton();
        this.txtPassword = new JPasswordField();
        this.jTextArea1.setColumns(20);
        this.jTextArea1.setRows(5);
        this.jScrollPane1.setViewportView(this.jTextArea1);
        this.setDefaultCloseOperation(3);
        this.setTitle("NGB XML Uploader Login");
        this.setBackground(new Color(59, 63, 66));
        this.setResizable(false);
        this.jPanel1.setBackground(new Color(59, 63, 66));
        this.jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
        this.jPanel3.setBackground(new Color(59, 63, 66));
        this.jLabel1.setFont(new Font("Dialog", 1, 36));
        this.jLabel1.setForeground(new Color(204, 204, 0));
        this.jLabel1.setText("Welcome to NGB XML Uploader");
        GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.jLabel1, -2, 558, -2).addContainerGap()));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGap(18, 18, 18).addComponent(this.jLabel1).addContainerGap(21, 32767)));
        this.lblUsername.setFont(new Font("Dialog", 0, 20));
        this.lblUsername.setText("Username");
        this.lblPassword.setFont(new Font("Dialog", 0, 20));
        this.lblPassword.setText("Password");
        this.txtUsername.setFont(new Font("Dialog", 1, 20));
        this.btnLogin.setFont(new Font("Dialog", 1, 20));
        this.btnLogin.setText("Login");
        this.btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                LoginForm.this.btnLoginActionPerformed(evt);
            }
        });
        this.txtPassword.setFont(new Font("Dialog", 1, 20));
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.lblUsername).addGap(18, 18, 18).addComponent(this.txtUsername, -2, 204, -2)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.lblPassword).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING, false).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.btnLogin, -2, 146, -2).addGap(58, 58, 58)).addComponent(this.txtPassword)))).addGap(124, 124, 124)));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(33, 33, 33).addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.lblUsername).addComponent(this.txtUsername, -2, 43, -2)).addGap(44, 44, 44).addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.lblPassword).addComponent(this.txtPassword, -2, 43, -2)).addGap(38, 38, 38).addComponent(this.btnLogin, -2, 48, -2).addContainerGap(55, 32767)));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false).addComponent(this.jPanel2, -1, -1, 32767).addComponent(this.jPanel3, -1, -1, 32767)).addContainerGap(12, 32767)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel3, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jPanel2, -1, -1, 32767).addContainerGap()));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -1, -1, 32767).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -1, -1, 32767).addContainerGap()));
        this.pack();
        this.setLocationRelativeTo((Component)null);
    }

    private void btnLoginActionPerformed(ActionEvent evt) {
        String username = this.txtUsername.getText();
        String password = new String(this.txtPassword.getPassword());
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            JOptionPane.showMessageDialog(this, "Username/Password can't be blank", "NGB XML Uploader Login", 0);
        }

        this.loginWorker = new LoginWorker(username, password, this);
        Constants.userName=username;
        Constants.passWord=password;
        this.loginWorker.execute();

        cons.setLoginWorker(this.loginWorker);
    }

    public static void run() {
        try {
            LookAndFeelInfo[] var1 = UIManager.getInstalledLookAndFeels();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                LookAndFeelInfo info = var1[var3];
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException var5) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, (String)null, var5);
        } catch (InstantiationException var6) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, (String)null, var6);
        } catch (IllegalAccessException var7) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, (String)null, var7);
        } catch (UnsupportedLookAndFeelException var8) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, (String)null, var8);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new LoginForm()).setVisible(true);
            }
        });
    }
}