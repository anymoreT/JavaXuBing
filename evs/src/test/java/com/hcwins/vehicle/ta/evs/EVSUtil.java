package com.hcwins.vehicle.ta.evs;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.logging.SLF4JLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;

import java.io.FileReader;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by tommy on 3/23/15.
 */
public class EVSUtil {
    static final Logger logger = LoggerFactory.getLogger(EVSUtil.class);

    static final String testBedYaml = System.getProperty("TB", EVSUtil.class.getResource("/FuncTestTB.yml").getFile());
    static final String apiSetYaml = System.getProperty("API", EVSUtil.class.getResource("/APISet.yml").getFile());
    static final String dataSetYaml = System.getProperty("DATA", EVSUtil.class.getResource("/DataSet.yml").getFile());

    private TestBed testBed;
    private APISet apiSet;
    private DataSet dataSet;
    private DBI dbi;
    private Handle handle;

    private Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create();

    private static EVSUtil evsUtil = new EVSUtil();

    private EVSUtil() {
        this.testBed = loadTestBed();
        this.apiSet = loadAPISet();
        this.dataSet = loadDataSet();
        this.handle = initDBHandle();
        this.testBed.setUp();
    }

    private TestBed loadTestBed() {
        TestBed testBed = null;
        try {
            logger.debug("trying to load testbed configuration with {}", testBedYaml);
            YamlReader reader = new YamlReader(new FileReader(testBedYaml));
            testBed = reader.read(TestBed.class);
            logger.debug("success to load testbed - {}", testBed);
        } catch (Exception e) {
            logger.error("failed to load testbed", e);
        }
        return testBed;
    }

    private APISet loadAPISet() {
        APISet apiSet = null;
        try {
            logger.debug("trying to load api set configuration with {}", apiSetYaml);
            YamlReader reader = new YamlReader(new FileReader(apiSetYaml));
            apiSet = reader.read(APISet.class);
            logger.debug("success to load api set - {}", apiSet);
        } catch (Exception e) {
            logger.error("failed to load api set", e);
        }
        return apiSet;
    }

    private DataSet loadDataSet() {
        DataSet dataSet = null;
        try {
            logger.debug("trying to load data set configuration with {}", dataSetYaml);
            YamlReader reader = new YamlReader(new FileReader(dataSetYaml));
            dataSet = reader.read(DataSet.class);
            logger.debug("success to load data set - {}", dataSet);
        } catch (Exception e) {
            logger.error("failed to load data set", e);
        }
        return dataSet;
    }

    private Handle initDBHandle() {
        Handle handle = null;
        try {
            logger.debug("trying to connect to db with {}", testBed.jdbcConnectionString);
            dbi = new DBI(testBed.jdbcConnectionString);
            dbi.setSQLLog(new SLF4JLog());
            handle = dbi.open();
            logger.debug("success to connect to db");
        } catch (Exception e) {
            logger.error("failed to connect to db");
        }
        return handle;
    }

    private static EVSUtil getInstance() {
        return evsUtil;
    }

    public static TestBed getTestBed() {
        if (null == getInstance().testBed) {
            throw new SkipException("missing testbed");
        } else {
            return getInstance().testBed;
        }
    }

    public static APISet getAPISet() {
        if (null == getInstance().apiSet) {
            throw new SkipException("missing api set");
        } else {
            return getInstance().apiSet;
        }
    }

    public static DataSet getDataSet() {
        if (null == getInstance().dataSet) {
            throw new SkipException("missing data set");
        } else {
            return getInstance().dataSet;
        }
    }

    public static Handle getDBHandle() {
        if (null == getInstance().handle) {
            throw new SkipException("missing db handle");
        } else {
            return getInstance().handle;
        }
    }

    public static <SqlObjectType> SqlObjectType getDAO(Class<SqlObjectType> sqlObjectType) {
        if (null == getInstance().handle) {
            throw new SkipException("missing db handle");
        } else {
            return getInstance().handle.attach(sqlObjectType);
        }
    }

    public static Gson getGson() {
        return getInstance().gson;
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    public static void sleep(String msg, int seconds) {
        try {
            logger.debug("sleeping {} seconds: {}", seconds, msg);
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            //
        }
    }

    public static String getLoggerId() {
        String loggerId = "";

        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        for (StackTraceElement e : stack) {
            if (e.getMethodName().startsWith("test")) {
                loggerId = e.getMethodName();
            }
        }

        loggerId += "_" + getTimeStamp();

        return loggerId;
    }

    public static void updateHeader(Map<String, String> header) {
//
    }

    public static Response callPost(String url, RequestSpecification requestSpec, int expectedHttpStatusCode) {
        Response response = requestSpec.post(url);
        logger.debug("status code: {} content: {}", response.getStatusCode(), response.asString());

        if (0 < expectedHttpStatusCode) {
//            assertThat(response.getStatusCode(), equalTo(expectedHttpStatusCode));
        }

        return response;
    }

    public static Response callPostJson(String api, String json, Map<String, String> headers, int expectedHttpStatusCode) {
        String url = getTestBed().apiBaseUrl + api;

        updateHeader(headers);

        logger.debug("trying to call {} with {}", url, json);
        RequestSpecification request = given();
        request = request.contentType("application/json");
        request = null != headers ? request.headers(headers) : request;
        request = request.body(json);
        Response response = callPost(url, request, expectedHttpStatusCode);

        return response;
    }

    public static Response callPostJson(String api, String json, Map<String, String> headers) {
        return callPostJson(api, json, headers, 200);
    }

    public static Response callPostJson(String api, String json, int expectedHttpStatusCode) {
        return callPostJson(api, json, null, expectedHttpStatusCode);
    }

    public static Response callPostJson(String api, String json) {
        return callPostJson(api, json, 200);
    }
}