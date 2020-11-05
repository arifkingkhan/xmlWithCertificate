package com.ngb.xml.http;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ngb.global.Constants;
import com.ngb.xml.dto.HttpHandlerResponse;
import com.ngb.xml.dto.LoginBody;
import com.ngb.xml.dto.ParsedXmlData;
import com.ngb.xml.dto.PingResponse;
import com.ngb.xml.dto.Read;
import com.ngb.xml.dto.ReadMasterKW;
import com.ngb.xml.dto.ReadMasterPF;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.PKIXParameters;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.ngb.xml.ui.LoginForm;
import com.ngb.xml.uiWorker.LoginWorker;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.OkHttpClient.Builder;
import org.apache.commons.lang3.StringUtils;
import utils.MeterMakeCodeMapping;

public class HttpRequestsHandler {
    private static OkHttpClient httpClient;
    private static final String NGB_HOST = "ngbtest.mpwin.co.in";
    private static final String USER_AGENT_HEADER = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36";
    private static final String LOGIN_API = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/authentication/login";
    private static final String CONSUMER_METER_MAPPING_API = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/mapping/identifier/$meterIdentifier$/status/$status$";
    private static final String LATEST_READ_API = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/read/latest/consumer-no/$consumerNo$";
    private static final String READ_MASTER_API = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/read";
    private final SimpleDateFormat oldDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static List<X509Certificate> trustedCertificates;
    private String consumerNumber;

    public HttpRequestsHandler() {
    }

    public static boolean initializeHttpRequestHandler() {
        try {
            if (loadCertificateAuthorities()) {
                TrustManager trustManager = getTrustManager();
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init((KeyManager[])null, new TrustManager[]{trustManager}, new SecureRandom());
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                httpClient = (new Builder()).sslSocketFactory(sslSocketFactory, (X509TrustManager)trustManager).build();
                return true;
            }
        } catch (NoSuchAlgorithmException | KeyManagementException var3) {
            var3.printStackTrace();
        }

        return false;
    }

    private static TrustManager getTrustManager() {
        return new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return (X509Certificate[])HttpRequestsHandler.trustedCertificates.toArray(new X509Certificate[0]);
            }
        };
    }

    private static boolean loadCertificateAuthorities() {
        try {
            System.out.println(System.getProperty("java.home"));
            String fileName = System.getProperty("java.home") + "\\lib\\security\\cacerts";
            FileInputStream fileInputStream = new FileInputStream(fileName);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            String keyStorePass = "changeit";
            keyStore.load(fileInputStream, keyStorePass.toCharArray());
            PKIXParameters params = new PKIXParameters(keyStore);
            trustedCertificates = (List)params.getTrustAnchors().stream().map((trustAnchor) -> {
                return trustAnchor.getTrustedCert();
            }).collect(Collectors.toList());
            fileInputStream.close();
            return true;
        } catch (InvalidAlgorithmParameterException | KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException var5) {
            System.out.println("Couldn't load the list of CAs from cacerts file!");
            var5.printStackTrace();
            return false;
        }
    }

    public String sendParsedDataToServer(ParsedXmlData parsedXmlData, String authToken) throws Exception {
        if (!MeterMakeCodeMapping.meterMakeCodeMapping.containsKey(parsedXmlData.getMeterMakeCode().trim())) {
            throw new Exception("Couldn't find the meter make code mapping");
        } else {
            String meterIdentifier = ((String)MeterMakeCodeMapping.meterMakeCodeMapping.get(parsedXmlData.getMeterMakeCode())).concat(parsedXmlData.getMeterNumber());
            this.consumerNumber = this.getConsumerNumber(authToken, meterIdentifier);
            if (this.consumerNumber == null) {
                throw new Exception("Couldn't find the consumer number corresponding to meter identifier");
            } else {
                BigDecimal latestReading = this.getLatestReading(authToken);
                if (latestReading.compareTo(new BigDecimal(parsedXmlData.getB3Value())) > 0) {
                    throw new Exception("Reading which has to be inserted can't be less than the latest reading found");
                } else {
                    String readMasterResponse = this.sendReadMasterRequest(authToken, parsedXmlData, latestReading);
                    return readMasterResponse;
                }
            }
        }
    }

    public String sendReadMasterRequest(String authToken, ParsedXmlData parsedXmlData, BigDecimal latestReading) throws Exception {

        System.out.println("Sending request to insert read..");
        String meterIdentifier = ((String)MeterMakeCodeMapping.meterMakeCodeMapping.get(parsedXmlData.getMeterMakeCode())).concat(parsedXmlData.getMeterNumber());
        BigDecimal readingToBeInserted = new BigDecimal(parsedXmlData.getB3Value());
        BigDecimal difference = readingToBeInserted.subtract(latestReading);
        ReadMasterKW readMasterKW = new ReadMasterKW(new BigDecimal(parsedXmlData.getB5Value()), BigDecimal.ZERO, BigDecimal.ZERO);
        ReadMasterPF readMasterPf = new ReadMasterPF(new BigDecimal(parsedXmlData.getB9Value()), BigDecimal.ZERO);
        Read read = new Read(this.consumerNumber, meterIdentifier, this.newDateFormat.format(this.oldDateFormat.parse(parsedXmlData.getReadingDate())), "NORMAL", "WORKING", "NR", "AMR_FILE", readingToBeInserted, difference, new BigDecimal("1"), new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), false, readMasterKW, readMasterPf);
        String readJson = (new Gson()).toJson(read);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), readJson);
        Request request = (new okhttp3.Request.Builder()).url("https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/read").addHeader("Authorization", authToken).post(requestBody).build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        System.out.println("Received response from read master API");
        if (response.code() == 201) {
            String responseJson = response.body().string();
            response.body().close();
            return responseJson;
        }

    /*    else if(response.code() == 401)
        {
            Constants cons = LoginForm.getCons();
            LoginWorker loginworker = cons.getLoginWorker();
            loginworker.doInBackground();
            this.sendReadMasterRequest(authToken,parsedXmlData,latestReading);
            return "hi";

        } */

        else {
            Map<String, String> errorMessage = new HashMap();
            errorMessage.put("Response code", String.valueOf(response.code()));
            errorMessage.put("Message", response.message());
            errorMessage.put("Response body", response.body().string());
            errorMessage.put("Error source", "Read master insertion API");
            response.body().close();
            throw new IOException(errorMessage.toString());
        }
    }

    public String getConsumerNumber() {
        return this.consumerNumber;
    }

    private String getConsumerNumber(String authToken, String meterIdentifier) throws IOException {
        System.out.println("Sending request to get consumer number..");
        String url = this.buildConsumerMeterMappingApiUrl(meterIdentifier);
        Request request = (new okhttp3.Request.Builder()).url(url).addHeader("Authorization", authToken).build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        System.out.println("Received response from consumer number API");
        if (response.code() == 200) {
            JsonArray responseJsonArray = (JsonArray)(new Gson()).fromJson(response.body().string(), JsonArray.class);
            JsonObject consumerMeterMappingJson = responseJsonArray.get(0).getAsJsonObject();
            response.body().close();
            if (consumerMeterMappingJson == null) {
                throw new IOException("Consumer meter mapping JSON was null");
            } else {
                return consumerMeterMappingJson.get("consumerNo").getAsString();
            }
        } else {
            Map<String, String> errorMessage = new HashMap();
            errorMessage.put("Response code", String.valueOf(response.code()));
            errorMessage.put("Message", response.message());
            errorMessage.put("Response body", response.body().string());
            errorMessage.put("Error source", "Consumer Meter Mapping API");
            response.body().close();
            throw new IOException(errorMessage.toString());
        }
    }

    private BigDecimal getLatestReading(String authToken) throws IOException {
        System.out.println("Sending request to get latest reading..");
        String url = this.buildLatestReadApiUrl();
        Request request = (new okhttp3.Request.Builder()).url(url).addHeader("Authorization", authToken).build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        System.out.println("Received response from latest reading API");
        if (response.code() == 200) {
            JsonObject latestReadMasterJson = (JsonObject)(new Gson()).fromJson(response.body().string(), JsonObject.class);
            response.body().close();
            if (latestReadMasterJson == null) {
                throw new IOException("Latest read master JSON was null");
            } else {
                return latestReadMasterJson.get("reading").getAsBigDecimal();
            }
        } else {
            Map<String, String> errorMessage = new HashMap();
            errorMessage.put("Response code", String.valueOf(response.code()));
            errorMessage.put("Message", response.message());
            errorMessage.put("Response body", response.body().string());
            errorMessage.put("Error source", "Latest Read API");
            response.body().close();
            throw new IOException(errorMessage.toString());
        }
    }

    private String buildLatestReadApiUrl() {
        String url = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/read/latest/consumer-no/$consumerNo$".replace("$consumerNo$", this.consumerNumber);
        return url;
    }

    private String buildConsumerMeterMappingApiUrl(String meterIdentifier) {
        String url = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/mapping/identifier/$meterIdentifier$/status/$status$".replace("$meterIdentifier$", meterIdentifier).replace("$status$", "ACTIVE");
        return url;
    }

    public static HttpHandlerResponse loginUser(String username, String password) throws IOException {
        LoginBody loginBody = new LoginBody(username, password);
        String loginBodyJson = (new Gson()).toJson(loginBody);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), loginBodyJson);
        Request request = (new okhttp3.Request.Builder()).url("https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/authentication/login").post(requestBody).addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36").build();
        System.out.println("Sending login request to ngb server..");
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        System.out.println("Received response from login API");
        System.out.println(response.toString());
        response.body().close();
        return response.code() == 200 && !StringUtils.isBlank(response.headers().get("authorization")) ? new HttpHandlerResponse(Boolean.TRUE, response.headers().get("authorization")) : new HttpHandlerResponse(Boolean.FALSE, "Authentication falied! Wrong username/password.");
    }

    public HttpHandlerResponse reloginUser(String username, String password) throws IOException {
        LoginBody loginBody = new LoginBody(username, password);
        String loginBodyJson = (new Gson()).toJson(loginBody);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), loginBodyJson);
        Request request = (new okhttp3.Request.Builder()).url("https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/authentication/login").post(requestBody).addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36").build();
        System.out.println("Sending login request to ngb server..");
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        System.out.println("Received response from login API");
        System.out.println(response.toString());
        response.body().close();
        return response.code() == 200 && !StringUtils.isBlank(response.headers().get("authorization")) ? new HttpHandlerResponse(Boolean.TRUE, response.headers().get("authorization")) : new HttpHandlerResponse(Boolean.FALSE, "Authentication falied! Wrong username/password.");
    }

    private static PingResponse pingHost() {
        PingResponse pingResponse = null;

        try {
            InetAddress.getByName("ngbtest.mpwin.co.in").isReachable(1000);
            pingResponse = new PingResponse(Boolean.TRUE, "Host reachable!");
            return pingResponse;
        } catch (UnknownHostException var6) {
            pingResponse = new PingResponse(Boolean.FALSE, "Couldn't connect to the NGB server! Please check your internet connection.");
            var6.printStackTrace();
            return pingResponse;
        } catch (IOException var7) {
            pingResponse = new PingResponse(Boolean.FALSE, "Some IO exception occurred! Can't process the request.");
            var7.printStackTrace();
            return pingResponse;
        } finally {
            ;
        }
    }
}
