package com.ngb.xml.parser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlParser {
    public XmlParser() {
    }

    public static void main(String[] args) throws Exception {
        String xmlFilesDirectory = "C:\\Users\\Napster\\Downloads\\NGB\\NGB";
        File directory = new File(xmlFilesDirectory);
        int exceptionCount = 0;
        File[] allXmlFiles = directory.listFiles();
        File outputFile = new File("C:\\Users\\Napster\\Downloads\\NGB\\Output\\parserOutput.txt");
        if (outputFile.exists()) {
            outputFile.delete();
        }

        outputFile.createNewFile();
        PrintWriter printWriter = new PrintWriter(outputFile);
        System.out.println("Parsing all xml files in the directory ...");
        File[] var7 = allXmlFiles;
        int var8 = allXmlFiles.length;

        for(int var9 = 0; var9 < var8; ++var9) {
            File xmlFile = var7[var9];
            if (xmlFile.isFile()) {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();

                try {
                    logFileName(printWriter, xmlFile);
                    parser.parse(xmlFile, new XmlParserHandler());
                } catch (IOException var14) {
                    logException(printWriter, var14.getMessage());
                    ++exceptionCount;
                }
            }
        }

        printWriter.close();
        System.out.println("\nParsing complete!");
        System.out.println("Total files found in the directory: " + allXmlFiles.length);
        System.out.println("Number of files successfully parsed: " + (allXmlFiles.length - exceptionCount));
        System.out.println("Number of files which couldn't be parsed: " + exceptionCount);
        System.out.println("\nRefer to the log file for more details");
    }

    private static void logFileName(PrintWriter printWriter, File xmlFile) {
        printWriter.println("File Name: " + xmlFile.getName());
        printWriter.println("-----------------------------------------------------");
    }

    private static void logException(PrintWriter printWriter, String exceptionMessage) {
        printWriter.println("Couldn't parse this xml file");
        printWriter.println("Error: " + exceptionMessage + "\n");
    }
}