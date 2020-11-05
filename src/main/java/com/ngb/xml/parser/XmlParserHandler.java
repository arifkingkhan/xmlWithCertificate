package com.ngb.xml.parser;

import com.ngb.xml.dto.ParsedXmlData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParserHandler extends DefaultHandler {
    private boolean insideD3;
    private boolean insideD301;
    private boolean insideD1;
    private final List<String> b3ParamCodes = new ArrayList();
    private final List<String> b9ParamCodes = new ArrayList();
    private final List<String> b5ParamCodes = new ArrayList();
    private String meterNumber;
    private String meterMakeCode;
    private String readingDate;
    private Map<String, String> b3Values;
    private Map<String, String> b5Values;
    private Map<String, String> b9Values;
    private ParsedXmlData parsedXmlData;
    private final Stack<String> elementStack = new Stack();

    public void startDocument() throws SAXException {
        this.insideD1 = false;
        this.insideD3 = false;
        this.insideD301 = false;
        this.b3Values = new HashMap();
        this.b5Values = new HashMap();
        this.b9Values = new HashMap();
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.elementStack.push(qName);
        if ("D1".equals(qName)) {
            this.insideD1 = true;
        }

        if ("D3".equals(qName)) {
            this.insideD3 = true;
        }

        if ("D3-01".equals(qName)) {
            this.insideD301 = true;
        }

        String curValue;
        if (this.insideD3 && this.insideD301) {
            if ("B3".equals(qName) && this.b3ParamCodes.contains(attributes.getValue("PARAMCODE"))) {
                curValue = attributes.getValue("VALUE");
                if (StringUtils.isBlank(curValue)) {
                    throw new SAXException("B3 value is null/blank");
                }

                this.b3Values.put(attributes.getValue("PARAMCODE"), attributes.getValue("VALUE"));
            } else if ("B5".equals(qName) && this.b5ParamCodes.contains(attributes.getValue("PARAMCODE"))) {
                curValue = attributes.getValue("VALUE");
                if (StringUtils.isBlank(curValue)) {
                    throw new SAXException("B3 value is null/blank");
                }

                this.b5Values.put(attributes.getValue("PARAMCODE"), attributes.getValue("VALUE"));
            } else if ("B9".equals(qName) && this.b9ParamCodes.contains(attributes.getValue("PARAMCODE"))) {
                curValue = attributes.getValue("VALUE");
                if (StringUtils.isBlank(curValue)) {
                    throw new SAXException("B3 value is null/blank");
                }

                this.b9Values.put(attributes.getValue("PARAMCODE"), attributes.getValue("VALUE"));
            }
        }

        if (this.insideD1 && "G22".equals(qName)) {
            this.meterMakeCode = attributes.getValue("CODE");
            if (StringUtils.isBlank(this.meterMakeCode)) {
                throw new SAXException("Meter make code is null/blank");
            }
        }

        if ("D3-01".equals(qName)) {
            curValue = attributes.getValue("DATETIME");
            if (StringUtils.isBlank(curValue)) {
                throw new SAXException("Date field value is null/blank");
            }

            this.readingDate = curValue.split(" ", 2)[0];
        }

    }

    public void endElement(String uri, String localName, String qName) {
        if ("D1".equals(qName)) {
            this.insideD1 = false;
        }

        if ("D3".equals(qName)) {
            this.insideD3 = false;
        }

        if ("D3-01".equals(qName)) {
            this.insideD301 = false;
        }

        this.elementStack.pop();
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length);
        if (this.insideD1 && "G1".equals(this.currentElement())) {
            if (StringUtils.isBlank(value)) {
                throw new SAXException("Meter number is null/blank");
            }

            this.meterNumber = value;
        }

    }

    public void endDocument() throws SAXException {
        this.parsedXmlData = new ParsedXmlData(this.meterNumber, this.meterMakeCode, this.readingDate, this.getHighestPrecedenceValue(this.b3Values, this.b3ParamCodes), this.getHighestPrecedenceValue(this.b5Values, this.b5ParamCodes), this.getHighestPrecedenceValue(this.b9Values, this.b9ParamCodes));
    }

    private String getHighestPrecedenceValue(Map<String, String> values, List<String> paramCodes) {
        Iterator var3 = paramCodes.iterator();

        String paramCode;
        do {
            if (!var3.hasNext()) {
                return "";
            }

            paramCode = (String)var3.next();
        } while(!values.containsKey(paramCode));

        return String.valueOf(Math.abs(Double.parseDouble((String)values.get(paramCode))));
    }

    public ParsedXmlData getParsedXmlData() {
        return this.parsedXmlData;
    }

    private String currentElement() {
        return (String)this.elementStack.peek();
    }

    public XmlParserHandler() {
        this.addB3ParamCodes(this.b3ParamCodes);
        this.addb5ParamCodes(this.b5ParamCodes);
        this.addb9ParamCodes(this.b9ParamCodes);
    }

    private void addB3ParamCodes(List<String> b3ParamCodes) {
        b3ParamCodes.add("P7-1-5-1-0");
        b3ParamCodes.add("P7-1-5-2-0");
        b3ParamCodes.add("P7-1-13-1-0");
        b3ParamCodes.add("P7-1-13-2-0");
        b3ParamCodes.add("P7-1-18-0-0");
        b3ParamCodes.add("P7-1-18-1-0");
        b3ParamCodes.add("P7-1-18-2-0");
    }

    private void addb5ParamCodes(List<String> b5ParamCodes) {
        b5ParamCodes.add("P7-1-18-0-0");
        b5ParamCodes.add("P7-4-5-1-0");
        b5ParamCodes.add("P7-4-5-2-0");
        b5ParamCodes.add("P7-4-13-2-0");
        b5ParamCodes.add("P7-4-18-1-0");
        b5ParamCodes.add("P7-4-18-2-0");
        b5ParamCodes.add("P7-4-18-0-0");
    }

    private void addb9ParamCodes(List<String> b9ParamCodes) {
        b9ParamCodes.add("P4-4-4-0-0");
        b9ParamCodes.add("P4-4-4-1-0");
    }
}
