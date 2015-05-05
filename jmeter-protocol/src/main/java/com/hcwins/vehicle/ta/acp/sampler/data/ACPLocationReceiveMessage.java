package com.hcwins.vehicle.ta.acp.sampler.data;

import com.google.gson.GsonBuilder;
import com.hcwins.vehicle.protocol.hs.LocationReceiveMessage;

import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * Created by tommy on 4/18/15.
 */
public class ACPLocationReceiveMessage extends AbstractACPMessage {
    private String companyId;//公司ID
    private String oilCardNumber;//加油卡卡号
    private LocationReceiveMessage.PackageType packageType;//标记
    private RandomDouble oilBalance;//加油卡余额
    private boolean gpsValidate;//GPS 数据是否有效  A 有效 V 无效
    private RandomDouble longitude;//经度
    private RandomDouble latitude;//纬度
    private FixedStepTimestamp timestamp;//GPS 时间,BCD 码
    private RandomDouble speed;
    private RandomDouble totalOilConsumption; //累积油耗
    private RandomInt totalMileage;  //累积里程
    private LocationReceiveMessage.AccStatus accStatus;
    private LocationReceiveMessage.OilCardStatus oilCardStatus;
    private int satelliteNumber;//卫星数
    private float altitute; //海拔高度
    private float directionAngel;//方向角

    public ACPLocationReceiveMessage() {
        super();

        //
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOilCardNumber() {
        return oilCardNumber;
    }

    public void setOilCardNumber(String oilCardNumber) {
        this.oilCardNumber = oilCardNumber;
    }

    public LocationReceiveMessage.PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(LocationReceiveMessage.PackageType packageType) {
        this.packageType = packageType;
    }

    public RandomDouble getOilBalance() {
        return oilBalance;
    }

    public void setOilBalance(RandomDouble oilBalance) {
        this.oilBalance = oilBalance;
    }

    public boolean isGpsValidate() {
        return gpsValidate;
    }

    public void setGpsValidate(boolean gpsValidate) {
        this.gpsValidate = gpsValidate;
    }

    public RandomDouble getLongitude() {
        return longitude;
    }

    public void setLongitude(RandomDouble longitude) {
        this.longitude = longitude;
    }

    public RandomDouble getLatitude() {
        return latitude;
    }

    public void setLatitude(RandomDouble latitude) {
        this.latitude = latitude;
    }

    public FixedStepTimestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(FixedStepTimestamp timestamp) {
        this.timestamp = timestamp;
    }

    public RandomDouble getSpeed() {
        return speed;
    }

    public void setSpeed(RandomDouble speed) {
        this.speed = speed;
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

    public LocationReceiveMessage.AccStatus getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(LocationReceiveMessage.AccStatus accStatus) {
        this.accStatus = accStatus;
    }

    public LocationReceiveMessage.OilCardStatus getOilCardStatus() {
        return oilCardStatus;
    }

    public void setOilCardStatus(LocationReceiveMessage.OilCardStatus oilCardStatus) {
        this.oilCardStatus = oilCardStatus;
    }

    public int getSatelliteNumber() {
        return satelliteNumber;
    }

    public void setSatelliteNumber(int satelliteNumber) {
        this.satelliteNumber = satelliteNumber;
    }

    public float getAltitute() {
        return altitute;
    }

    public void setAltitute(float altitute) {
        this.altitute = altitute;
    }

    public float getDirectionAngel() {
        return directionAngel;
    }

    public void setDirectionAngel(float directionAngel) {
        this.directionAngel = directionAngel;
    }

    @Override
    public byte[] generateMessage() {
        increaseIndex();

        LocationReceiveMessage vo = new LocationReceiveMessage();

        vo.setDeviceId(this.getDeviceId());
        vo.setVehicleId(this.getVehicleId());
        vo.setCompanyId(this.getCompanyId());
        vo.setOilCardNumber(this.getOilCardNumber());
        vo.setPackageType(this.getPackageType());
        vo.setOilBalance(this.getOilBalance().nextFloat());
        vo.setGpsValidate(this.isGpsValidate());
        vo.setLatitude(this.getLatitude().nextFloat());
        vo.setLongitude(this.getLongitude().nextFloat());
        vo.setTimestamp(this.getTimestamp().next());
        vo.setSpeed(this.getSpeed().nextFloat());
        vo.setTotalOilConsumption(this.getTotalOilConsumption().nextFloat());
        vo.setTotalMileage(this.getTotalMileage().next());
        vo.setAccStatus(this.getAccStatus());
        vo.setOilCardStatus(this.getOilCardStatus());
        vo.setSatelliteNumber(this.getSatelliteNumber());
        vo.setAltitute(this.getAltitute());
        vo.setDirectionAngel(this.getDirectionAngel());

        setInternalMessage(vo);

        return getCurrentMessage();
    }

    public static ACPLocationReceiveMessage getSampleMessageData() {
        ACPLocationReceiveMessage vo = new ACPLocationReceiveMessage();

        vo.setDeviceId("12548");
        vo.setVehicleId("vin1234");
        vo.setCompanyId("com8");
        vo.setOilCardNumber("oilCard");
        vo.setPackageType(LocationReceiveMessage.PackageType.ADD);
        vo.setOilBalance(new RandomDouble(100., 0., 0., 2));
        vo.setGpsValidate(true);
        vo.setLatitude(new RandomDouble(123.23, 0., 0., 4));
        vo.setLongitude(new RandomDouble(23.0124, 0., 0., 4));
        vo.setTimestamp(new FixedStepTimestamp(new Date(1429513701040L), -1));
        vo.setSpeed(new RandomDouble(23.23, 0., 0., 2));
        vo.setTotalOilConsumption(new RandomDouble(1090.04, 0., 0., 2));
        vo.setTotalMileage(new RandomInt(108000));
        vo.setAccStatus(LocationReceiveMessage.AccStatus.IGNITION);
        vo.setOilCardStatus(LocationReceiveMessage.OilCardStatus.ON_LINE);
        vo.setSatelliteNumber(23);
        vo.setAltitute(23.4f);
        vo.setDirectionAngel(23.4f);

        return vo;
    }

    public static String getDefaultRequestData() {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(getSampleMessageData());
    }
}
