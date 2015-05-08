package com.hcwins.vehicle.ta.acp.sampler.data;

import com.google.gson.GsonBuilder;
import com.hcwins.vehicle.protocol.hs.ConfiguredLineInfo;
import com.hcwins.vehicle.protocol.hs.RegisterReceiveMessage;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by xiangzhai on 06/05/15.
 */
public class ACPRegisterReceiveMessage extends AbstractACPMessage {
    private int serial;                                         //消息序号
    private String companyId;                                   //公司ID
    private RegisterReceiveMessage.RegisterType registerType = RegisterReceiveMessage.RegisterType.REQUIRE_REGESITER_STATUS;//消息类型
    private int workNumber;                                     //驾驶员工号
    private FixedStepTimestamp timestamp;
    private String hostNumber;                                  //主机号
    private int hostVersion;                                    //主机版本号
    private int reportStationVersion;                           //报站器版本号
    private String simcard;
    private RandomDouble totalOilConsumption;                   //累积油耗
    private RandomInt totalMileage;                             //累积里程
    private String stationId;                                   //站台ID
    private RandomInt stationSerial;                            //站台序号
    private RegisterReceiveMessage.DirectionType directionType; //行车方向
    private List<ConfiguredLineInfo> configuredLineInfoList;    //配置线路

    public ACPRegisterReceiveMessage() {
        super();
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public RegisterReceiveMessage.RegisterType getRegisterType() {
        return registerType;
    }

    public void setRegisterType(RegisterReceiveMessage.RegisterType registerType) {
        this.registerType = registerType;
    }

    public int getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(int workNumber) {
        this.workNumber = workNumber;
    }

    public FixedStepTimestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(FixedStepTimestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getHostNumber() {
        return hostNumber;
    }

    public void setHostNumber(String hostNumber) {
        this.hostNumber = hostNumber;
    }

    public int getHostVersion() {
        return hostVersion;
    }

    public void setHostVersion(int hostVersion) {
        this.hostVersion = hostVersion;
    }

    public int getReportStationVersion() {
        return reportStationVersion;
    }

    public void setReportStationVersion(int reportStationVersion) {
        this.reportStationVersion = reportStationVersion;
    }

    public String getSimcard() {
        return simcard;
    }

    public void setSimcard(String simcard) {
        this.simcard = simcard;
    }

    public RandomDouble getTotalOilConsumption() {
        return totalOilConsumption;
    }

    public void setTotalOilConsumption(RandomDouble totalOilConsumption) {
        this.totalOilConsumption = totalOilConsumption;
    }

    public RandomInt getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(RandomInt totalMileage) {
        this.totalMileage = totalMileage;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public RandomInt getStationSerial() {
        return stationSerial;
    }

    public void setStationSerial(RandomInt stationSerial) {
        this.stationSerial = stationSerial;
    }

    public RegisterReceiveMessage.DirectionType getDirectionType() {
        return directionType;
    }

    public void setDirectionType(RegisterReceiveMessage.DirectionType directionType) {
        this.directionType = directionType;
    }

    public List<ConfiguredLineInfo> getConfiguredLineInfoList() {
        return configuredLineInfoList;
    }

    public void setConfiguredLineInfoList(List<ConfiguredLineInfo> configuredLineInfoList) {
        this.configuredLineInfoList = configuredLineInfoList;
    }

    @Override
    public byte[] generateMessage() {
        increaseIndex();

        RegisterReceiveMessage vo = new RegisterReceiveMessage();
        vo.setDeviceId(this.getDeviceId());
        vo.setVehicleId(this.getVehicleId());
        vo.setSerial(index);
        vo.setCompanyId(this.getCompanyId());
        vo.setRegisterType(this.getRegisterType());
        vo.setWorkNumber(this.getWorkNumber());
        vo.setTimestamp(this.getTimestamp().next());
        vo.setHostNumber(this.getHostNumber());
        vo.setHostVersion(this.getHostVersion());
        vo.setReportStationVersion(this.getReportStationVersion());
        vo.setSimcard(this.getSimcard());
        vo.setTotalOilConsumption(this.getTotalOilConsumption().nextFloat());
        vo.setTotalMileage(this.getTotalMileage().next());
        vo.setStationId(this.getStationId());
        vo.setStationSerial(this.getStationSerial().next());
        vo.setDirectionType(this.getDirectionType());
        vo.setConfiguredLineInfoList(this.getConfiguredLineInfoList());

        setInternalMessage(vo);
        return getCurrentMessage();
    }

    public static ACPRegisterReceiveMessage getSampleMessageData() {
        increaseIndex();

        ACPRegisterReceiveMessage vo = new ACPRegisterReceiveMessage();
        vo.setDeviceId("1404933");
        vo.setVehicleId("OSKP733");
        vo.setSerial(index);
        vo.setCompanyId("com8");
        vo.setRegisterType(RegisterReceiveMessage.RegisterType.REQUIRE_REGESITER_STATUS);
        vo.setWorkNumber(005210);
        vo.setTimestamp(new FixedStepTimestamp(new Date(1429513701040L), -1));
        vo.setHostNumber("HU5400");
        vo.setHostVersion(10001);
        vo.setReportStationVersion(20001);
        vo.setSimcard("card01");
        vo.setTotalOilConsumption(new RandomDouble(1090.04, 0., 0., 2));
        vo.setTotalMileage(new RandomInt(1000));
        vo.setStationId("SQZQ");
        vo.setStationSerial(new RandomInt(9));
        vo.setDirectionType(RegisterReceiveMessage.DirectionType.FORWARD);
        vo.setConfiguredLineInfoList(getLineInfoList(1,1));

        return vo;
    }

    public static String getDefaultRequestData() {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(getSampleMessageData());
    }

    private static List<ConfiguredLineInfo> getLineInfoList(int a, int b) {
        ConfiguredLineInfo lineInfo = new ConfiguredLineInfo();
        lineInfo.setLineNumber(a);
        lineInfo.setVersion(b);

        List<ConfiguredLineInfo> lt = new ArrayList<ConfiguredLineInfo>();
        lt.add(lineInfo);
        return lt;
    }
}
