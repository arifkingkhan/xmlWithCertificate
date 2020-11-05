package com.ngb.xml.uiWorker;

import com.google.common.collect.Lists;
import com.ngb.xml.dto.ParsedXmlData;
import com.ngb.xml.http.HttpRequestsHandler;
import com.ngb.xml.parser.XmlParserHandler;
import com.ngb.xml.ui.XmlUploader;
import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.lang3.tuple.Pair;
import utils.MeterMakeCodeMapping;

public class XmlUploaderWorker extends SwingWorker<Void, Pair<AtomicInteger, AtomicInteger>> {
    private static final Integer NO_OF_REQUEST_THREADS = 25;
    private static final Integer PARTITION_SIZE = 50;
    private final String authToken;
    private final JLabel lblSuccessfulUpload;
    private final JLabel lblErrors;
    private final JLabel lblTotalFiles;
    private final JProgressBar uploadProgressbar;
    private final File xmlDirectory;
    private final File outputDirectory;
    private final XmlUploader xmlUploader;
    private final JButton btnUpload;
    private final JButton btnXmlDirectory;
    private final JButton btnOutputDirectory;
    PrintWriter successPrintWriter = null;
    PrintWriter exceptionPrintWriter = null;
    private AtomicInteger processedFiles;
    private AtomicInteger xmlFilesCount;
    private AtomicInteger runningSuccessfulRequestsCount;
    private AtomicInteger runningExceptionsCount;

    public XmlUploaderWorker(String authToken, JLabel lblSuccessfulUpload, JLabel lblErrors, JLabel lblTotalFiles, JProgressBar uploadProgressbar, File xmlDirectory, File outputDirectory, XmlUploader xmlUploader, JButton btnUpload, JButton btnXmlDirectory, JButton btnOutputDirectory) {
        this.authToken = authToken;
        this.lblSuccessfulUpload = lblSuccessfulUpload;
        this.lblErrors = lblErrors;
        this.lblTotalFiles = lblTotalFiles;
        this.uploadProgressbar = uploadProgressbar;
        this.xmlDirectory = xmlDirectory;
        this.outputDirectory = outputDirectory;
        this.xmlUploader = xmlUploader;
        this.btnUpload = btnUpload;
        this.btnXmlDirectory = btnXmlDirectory;
        this.btnOutputDirectory = btnOutputDirectory;
        this.processedFiles = new AtomicInteger();
        MeterMakeCodeMapping.initMeterMakeCodeMap();
    }

    public Void doInBackground() throws Exception {
        File[] allXmlFiles = this.xmlDirectory.listFiles(File::isFile);
        File parsedFilesOutput = null;
        File exceptionsOutput = null;

        try {
            parsedFilesOutput = new File(this.outputDirectory.getAbsolutePath() + "\\Xml_Uploader_Output_" + this.getFormattedTimestamp(LocalDateTime.now()) + ".txt");
            if (!parsedFilesOutput.exists()) {
                parsedFilesOutput.createNewFile();
            }

            exceptionsOutput = new File(this.outputDirectory.getAbsolutePath() + "\\Xml_Uploader_Exceptions_" + this.getFormattedTimestamp(LocalDateTime.now()) + ".txt");
            if (!exceptionsOutput.exists()) {
                exceptionsOutput.createNewFile();
            }

            this.successPrintWriter = new PrintWriter(parsedFilesOutput);
            this.exceptionPrintWriter = new PrintWriter(exceptionsOutput);
        } catch (Exception var17) {
            var17.printStackTrace();
            return null;
        }

        Map<String, Boolean> meterIdentifiers = new HashMap();
        List<ParsedXmlData> parsedXmlFiles = new ArrayList();
        System.out.println("Parsing all xml files in the directory ...");
        File[] var6 = allXmlFiles;
        int var7 = allXmlFiles.length;

        ParsedXmlData parsedXmlData;
        for(int var8 = 0; var8 < var7; ++var8) {
            File xmlFile = var6[var8];
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parsedXmlData = null;
            XmlParserHandler handler = null;

            try {
                handler = new XmlParserHandler();
                parser.parse(xmlFile, handler);
                parsedXmlData = handler.getParsedXmlData();
                if (meterIdentifiers.containsKey(parsedXmlData.getMeterNumber())) {
                    throw new Exception("This meter identifier has already been processed in some other file");
                }

                meterIdentifiers.put(parsedXmlData.getMeterNumber(), true);
                parsedXmlData.setXmlFile(xmlFile);
                System.out.println("Parsed XML data: " + parsedXmlData);
                parsedXmlFiles.add(parsedXmlData);
            } catch (Exception var16) {
                this.logException(this.exceptionPrintWriter, xmlFile, var16.getMessage() + ", Skipping this XML file");
            }
        }

        this.xmlFilesCount = new AtomicInteger(allXmlFiles.length);
        System.out.println("Successfully parsed " + parsedXmlFiles.size() + " XML files");
        List<List<ParsedXmlData>> parsedXmlFilesPartitions = Lists.partition(parsedXmlFiles, PARTITION_SIZE);

        ExecutorService executorService = null;
        Iterator var20 = parsedXmlFilesPartitions.iterator();

        while(var20.hasNext()) {
            List<ParsedXmlData> parsedXmlFilesPartition = (List)var20.next();
            List<Callable<Void>> callables = new ArrayList();
            this.runningExceptionsCount = new AtomicInteger();
            this.runningSuccessfulRequestsCount = new AtomicInteger();
            Iterator var23 = parsedXmlFilesPartition.iterator();

            while(var23.hasNext()) {
                parsedXmlData = (ParsedXmlData)var23.next();
                ParsedXmlData finalParsedXmlData = parsedXmlData;
                Callable<Void> callable = () -> {
                    HttpRequestsHandler httpRequestHandler = null;

                    try {
                        httpRequestHandler = new HttpRequestsHandler();
                        httpRequestHandler.sendParsedDataToServer(finalParsedXmlData, this.authToken);
                        this.logParsedData(this.successPrintWriter, finalParsedXmlData, httpRequestHandler.getConsumerNumber());
                        this.runningSuccessfulRequestsCount.addAndGet(1);
                        System.out.println("Read Successfully sent for meter " + finalParsedXmlData.getMeterNumber());
                    } catch (Exception var4) {
                        this.logException(this.exceptionPrintWriter, finalParsedXmlData, var4.getMessage(), httpRequestHandler.getConsumerNumber());
                        this.runningExceptionsCount.addAndGet(1);
                        System.out.println("Error occurred while sending Read for meter " + finalParsedXmlData.getMeterNumber());
                    }

                    this.processedFiles.addAndGet(1);
                    return null;
                };
                callables.add(callable);
            }

            executorService = Executors.newFixedThreadPool(NO_OF_REQUEST_THREADS);
            List<Future<Void>> futures = executorService.invokeAll(callables);
            Iterator var25 = futures.iterator();

            while(var25.hasNext()) {
                Future future = (Future)var25.next();

                try {
                    future.get();
                } catch (Exception var15) {
                    this.logException(this.exceptionPrintWriter, var15.getMessage() + ", Error occurred while executing get on callable");
                }
            }

            this.publish(new Pair[]{Pair.of(this.runningSuccessfulRequestsCount, this.runningExceptionsCount)});
            this.updateProgress();
        }

        executorService.shutdown();
        this.successPrintWriter.close();
        this.exceptionPrintWriter.close();
        return null;
    }

    private void logException(PrintWriter printWriter, String exceptionMessage) {
        synchronized(printWriter) {
            printWriter.println(exceptionMessage);
        }
    }

    private void logException(PrintWriter printWriter, File xmlFile, String exceptionMessage) {
        printWriter.print(xmlFile.getName() + "#");
        printWriter.println(exceptionMessage);
    }

    private void updateProgress() {
        this.setProgress(100 * this.processedFiles.get() / this.xmlFilesCount.get());
    }

    private String getFormattedTimestamp(LocalDateTime timestamp) {
        String formattedTimestamp = timestamp.getYear() + "_" + timestamp.getMonth() + "_" + timestamp.getDayOfMonth() + "_" + timestamp.getHour() + "_" + timestamp.getMinute() + "_" + timestamp.getSecond();
        return formattedTimestamp;
    }

    private void logParsedData(PrintWriter printWriter, ParsedXmlData parsedXmlData, String consumerNumber) {
        synchronized(printWriter) {
            printWriter.print(parsedXmlData.getXmlFile().getName() + "#");
            printWriter.print(parsedXmlData.getMeterNumber() + "#");
            printWriter.print((String)MeterMakeCodeMapping.meterMakeCodeMapping.get(parsedXmlData.getMeterMakeCode()) + "#");
            printWriter.print(consumerNumber + "#");
            printWriter.print(parsedXmlData.getReadingDate() + "#");
            printWriter.print(parsedXmlData.getB3Value() + "#");
            printWriter.print(parsedXmlData.getB5Value() + "#");
            printWriter.println(parsedXmlData.getB9Value());
        }
    }

    private void logException(PrintWriter printWriter, ParsedXmlData parsedXmlData, String exceptionMessage, String consumerNumber) {
        synchronized(printWriter) {
            printWriter.print(parsedXmlData.getXmlFile().getName() + "#");
            if (parsedXmlData != null) {
                printWriter.print(parsedXmlData.getMeterNumber() + "#");
            } else {
                printWriter.print("NULL#");
            }

            if (consumerNumber != null) {
                printWriter.print(consumerNumber + "#");
            }

            printWriter.println(exceptionMessage);
        }
    }

    public void process(List<Pair<AtomicInteger, AtomicInteger>> responses) {
        Pair<AtomicInteger, AtomicInteger> latestResponse = (Pair)responses.get(responses.size() - 1);
        AtomicInteger successfulRequests = (AtomicInteger)latestResponse.getKey();
        AtomicInteger exceptions = (AtomicInteger)latestResponse.getValue();
        int curSuccessCount = Integer.parseInt(this.lblSuccessfulUpload.getText());
        int curExceptionsCount = Integer.parseInt(this.lblErrors.getText());
        this.lblSuccessfulUpload.setText(String.valueOf(curSuccessCount + successfulRequests.get()));
        this.lblErrors.setText(String.valueOf(curExceptionsCount + exceptions.get()));
        int runningTotal = Integer.parseInt(this.lblTotalFiles.getText());
        this.lblTotalFiles.setText(String.valueOf(runningTotal + exceptions.get() + successfulRequests.get()));
    }

    protected void done() {
        JOptionPane.showMessageDialog(this.xmlUploader, "Uploading Job completed! Please refer to the logs inside output directory for more details", "NGB XML Uploader", 1);
        this.uploadProgressbar.setValue(0);
        this.btnUpload.setEnabled(true);
        this.btnOutputDirectory.setEnabled(true);
        this.btnXmlDirectory.setEnabled(true);
    }
}
