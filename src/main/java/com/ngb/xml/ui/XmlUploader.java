package com.ngb.xml.ui;

import com.ngb.xml.uiWorker.PropertyChangeListenerImpl;
import com.ngb.xml.uiWorker.XmlUploaderWorker;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;

public class XmlUploader extends JFrame {
    private final String username;
    private final String authToken;
    private File xmlDirectory;
    private File outputDirectory;
    private JButton btnChooseOutputDirectory;
    private JButton btnChooseXmlDirectory;
    private JButton btnUpload;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JLabel lblChooseDirectory;
    private JLabel lblErrors;
    private JLabel lblSuccessfulUpload;
    private JLabel lblTotalFiles;
    private JLabel lblUploaderOutput;
    private JLabel lblUsername;
    private JLabel msgNoOfFilesUploaded;
    private JLabel msgNoOfFilesUploaded1;
    private JLabel msgNoOfFilesUploaded2;
    private JFileChooser outputDirectoryChooser;
    private JProgressBar uploadProgressbar;
    private JFileChooser xmlDirectoryChooser;

    public XmlUploader(String username, String authToken) {
        this.initComponents();
        this.username = username;
        this.authToken = authToken;
        this.lblUsername.setText("HELLO " + this.username + " !");
        this.xmlDirectoryChooser.setFileSelectionMode(1);
        this.outputDirectoryChooser.setFileSelectionMode(1);
        this.uploadProgressbar.setStringPainted(true);
        this.uploadProgressbar.setFont(new Font("roboto", 1, 20));
        System.out.println("Username: " + this.username);
    }

    private void initComponents() {
        this.xmlDirectoryChooser = new JFileChooser();
        this.outputDirectoryChooser = new JFileChooser();
        this.jPanel3 = new JPanel();
        this.lblUsername = new JLabel();
        this.jPanel1 = new JPanel();
        this.btnChooseXmlDirectory = new JButton();
        this.lblChooseDirectory = new JLabel();
        this.lblUploaderOutput = new JLabel();
        this.btnChooseOutputDirectory = new JButton();
        this.jPanel2 = new JPanel();
        this.msgNoOfFilesUploaded = new JLabel();
        this.uploadProgressbar = new JProgressBar();
        this.lblSuccessfulUpload = new JLabel();
        this.msgNoOfFilesUploaded1 = new JLabel();
        this.lblErrors = new JLabel();
        this.msgNoOfFilesUploaded2 = new JLabel();
        this.lblTotalFiles = new JLabel();
        this.jPanel4 = new JPanel();
        this.btnUpload = new JButton();
        this.xmlDirectoryChooser.setMinimumSize(new Dimension(900, 500));
        this.xmlDirectoryChooser.setPreferredSize(new Dimension(900, 500));
        this.outputDirectoryChooser.setMinimumSize(new Dimension(900, 500));
        this.outputDirectoryChooser.setPreferredSize(new Dimension(900, 500));
        this.setDefaultCloseOperation(3);
        this.setTitle("NGB XML Uploader Form");
        this.setResizable(false);
        this.jPanel3.setBackground(new Color(59, 63, 66));
        this.jPanel3.setToolTipText("");
        this.lblUsername.setFont(new Font("Dialog", 1, 36));
        this.lblUsername.setForeground(new Color(204, 204, 0));
        this.lblUsername.setHorizontalAlignment(0);
        GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(this.lblUsername, -1, -1, 32767).addContainerGap()));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addContainerGap(22, 32767).addComponent(this.lblUsername, -2, 54, -2).addGap(18, 18, 18)));
        this.jPanel1.setBorder(BorderFactory.createTitledBorder((Border)null, "Directories", 0, 0, new Font("Dialog", 1, 12), new Color(51, 51, 51)));
        this.btnChooseXmlDirectory.setFont(new Font("Dialog", 1, 18));
        this.btnChooseXmlDirectory.setText("Choose");
        this.btnChooseXmlDirectory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                XmlUploader.this.btnChooseXmlDirectoryActionPerformed(evt);
            }
        });
        this.lblChooseDirectory.setFont(new Font("Dialog", 0, 18));
        this.lblChooseDirectory.setText("XML files directory");
        this.lblChooseDirectory.setToolTipText("");
        this.lblUploaderOutput.setFont(new Font("Dialog", 0, 18));
        this.lblUploaderOutput.setText("Uploader output directory");
        this.lblUploaderOutput.setToolTipText("");
        this.btnChooseOutputDirectory.setFont(new Font("Dialog", 1, 18));
        this.btnChooseOutputDirectory.setText("Choose");
        this.btnChooseOutputDirectory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                XmlUploader.this.btnChooseOutputDirectoryActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.lblUploaderOutput).addGroup(jPanel1Layout.createSequentialGroup().addGap(34, 34, 34).addComponent(this.lblChooseDirectory))).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.btnChooseXmlDirectory, -2, 143, -2).addComponent(this.btnChooseOutputDirectory, -2, 143, -2)).addContainerGap(-1, 32767)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(14, 14, 14).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.lblChooseDirectory, -2, 40, -2).addComponent(this.btnChooseXmlDirectory, -2, 40, -2)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.lblUploaderOutput, -2, 40, -2).addComponent(this.btnChooseOutputDirectory, -2, 40, -2)).addContainerGap(19, 32767)));
        this.jPanel2.setBorder(BorderFactory.createTitledBorder((Border)null, "Output", 0, 0, new Font("Dialog", 1, 12), new Color(51, 51, 51)));
        this.msgNoOfFilesUploaded.setFont(new Font("Dialog", 0, 18));
        this.msgNoOfFilesUploaded.setText("Files uploaded successfully");
        this.msgNoOfFilesUploaded.setToolTipText("");
        this.lblSuccessfulUpload.setFont(new Font("Dialog", 1, 22));
        this.lblSuccessfulUpload.setForeground(new Color(0, 153, 0));
        this.lblSuccessfulUpload.setText("0");
        this.msgNoOfFilesUploaded1.setFont(new Font("Dialog", 0, 18));
        this.msgNoOfFilesUploaded1.setText("Errors");
        this.msgNoOfFilesUploaded1.setToolTipText("");
        this.lblErrors.setFont(new Font("Dialog", 1, 22));
        this.lblErrors.setForeground(new Color(204, 0, 0));
        this.lblErrors.setText("0");
        this.msgNoOfFilesUploaded2.setFont(new Font("Dialog", 0, 18));
        this.msgNoOfFilesUploaded2.setText("Total Files scanned");
        this.msgNoOfFilesUploaded2.setToolTipText("");
        this.lblTotalFiles.setFont(new Font("Dialog", 1, 22));
        this.lblTotalFiles.setForeground(new Color(0, 153, 153));
        this.lblTotalFiles.setText("0");
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(this.uploadProgressbar, -1, -1, 32767)).addGroup(jPanel2Layout.createSequentialGroup().addGap(125, 125, 125).addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addComponent(this.msgNoOfFilesUploaded).addGroup(jPanel2Layout.createSequentialGroup().addGap(86, 86, 86).addComponent(this.msgNoOfFilesUploaded1)).addGroup(Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addComponent(this.msgNoOfFilesUploaded2).addGap(31, 31, 31))).addGap(55, 55, 55).addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addComponent(this.lblTotalFiles).addComponent(this.lblErrors).addComponent(this.lblSuccessfulUpload)).addGap(0, 0, 32767))).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(17, 17, 17).addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.msgNoOfFilesUploaded, -2, 43, -2).addComponent(this.lblSuccessfulUpload)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.msgNoOfFilesUploaded1, -2, 43, -2).addComponent(this.lblErrors)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.msgNoOfFilesUploaded2, -2, 43, -2).addComponent(this.lblTotalFiles)).addGap(18, 18, 18).addComponent(this.uploadProgressbar, -2, 42, -2).addContainerGap(10, 32767)));
        this.btnUpload.setFont(new Font("Dialog", 1, 28));
        this.btnUpload.setForeground(new Color(0, 153, 0));
        this.btnUpload.setText("UPLOAD");
        this.btnUpload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                XmlUploader.this.btnUploadActionPerformed(evt);
            }
        });
        GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
        this.jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(this.btnUpload, -2, 197, -2).addContainerGap(-1, 32767)));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addGap(16, 16, 16).addComponent(this.btnUpload, -2, 64, -2).addContainerGap(65, 32767)));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jPanel2, -1, -1, 32767).addComponent(this.jPanel3, -1, -1, 32767).addGroup(layout.createSequentialGroup().addComponent(this.jPanel1, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jPanel4, -2, -1, -2).addGap(0, 0, 32767))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.jPanel3, -2, -1, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(this.jPanel1, -2, -1, -2).addComponent(this.jPanel4, -2, -1, -2)).addGap(18, 18, 18).addComponent(this.jPanel2, -2, -1, -2).addGap(8, 8, 8)));
        this.pack();
        this.setLocationRelativeTo((Component)null);
    }

    private void btnChooseXmlDirectoryActionPerformed(ActionEvent evt) {
        int returnVal = this.xmlDirectoryChooser.showOpenDialog(this);
        if (returnVal == 0) {
            File selectedDirectory = this.xmlDirectoryChooser.getSelectedFile();
            this.xmlDirectory = selectedDirectory;
        }

    }

    private void btnChooseOutputDirectoryActionPerformed(ActionEvent evt) {
        int returnVal = this.outputDirectoryChooser.showOpenDialog(this);
        if (returnVal == 0) {
            File selectedDirectory = this.outputDirectoryChooser.getSelectedFile();
            this.outputDirectory = selectedDirectory;
        }

    }

    private void btnUploadActionPerformed(ActionEvent evt) {
        if (this.xmlDirectory == null) {
            JOptionPane.showMessageDialog(this, "Please choose the directory containing required XML files", "NGB XML Uploader", 0);
        } else if (this.outputDirectory == null) {
            JOptionPane.showMessageDialog(this, "Please choose the output files directory", "NGB XML Uploader", 0);
        } else {
            this.resetLabels();
            this.disableAll();
            this.uploadProgressbar.setValue(0);
            this.uploadFiles();
        }
    }

    private void resetLabels() {
        this.lblSuccessfulUpload.setText("0");
        this.lblErrors.setText("0");
        this.lblTotalFiles.setText("0");
    }

    private void disableAll() {
        this.btnUpload.setEnabled(false);
        this.btnChooseOutputDirectory.setEnabled(false);
        this.btnChooseXmlDirectory.setEnabled(false);
    }

    private void uploadFiles() {
        XmlUploaderWorker uploaderWorker = new XmlUploaderWorker(this.authToken, this.lblSuccessfulUpload, this.lblErrors, this.lblTotalFiles, this.uploadProgressbar, this.xmlDirectory, this.outputDirectory, this, this.btnUpload, this.btnChooseXmlDirectory, this.btnChooseOutputDirectory);
        uploaderWorker.addPropertyChangeListener(new PropertyChangeListenerImpl(this.uploadProgressbar));
        uploaderWorker.execute();
    }
}