package com.hcwins.vehicle.ta.acp.sampler.data;

import com.google.gson.GsonBuilder;
import com.hcwins.vehicle.protocol.hs.LocationReceiveMessage;

import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * Created by tommy on 4/18/15.
 */
public class ACPLocationReceiveMessage extends AbstractACPMessage {
    public String deviceId;//车载设备终端号
    public String vehicleId;//汽车识别号
    public String oilCardNumber;//加油卡卡号
    public LocationReceiveMessage.PackageType packageType;//标记
    public Date timestamp;//GPS 时间,BCD 码
    public boolean gpsValidate;//GPS 数据是否有效  A 有效 V 无效
    public float speed;
    public float latitude;//纬度
    public float longitude;//经度
    public LocationReceiveMessage.AccStatus accStatus;
    public LocationReceiveMessage.OilCardStatus oilCardStatus;
    public int satelliteNumber;//卫星数
    public float altitute; //海拔高度
    public float directionAngel;//方向角

    public ACPLocationReceiveMessage() {
        super();

        //
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isGpsValidate() {
        return gpsValidate;
    }

    public void setGpsValidate(boolean gpsValidate) {
        this.gpsValidate = gpsValidate;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
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
        vo.setOilCardNumber(this.getOilCardNumber());
        vo.setPackageType(this.getPackageType());
        vo.setTimestamp(this.getTimestamp());
        vo.setGpsValidate(this.isGpsValidate());
        vo.setSpeed(this.getSpeed());
        vo.setLatitude(this.getLatitude());
        vo.setLongitude(this.getLongitude());
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
        vo.setOilCardNumber("oilCard");
        vo.setPackageType(LocationReceiveMessage.PackageType.ADD);
        vo.setTimestamp(new Date(1429513701040L));
        vo.setGpsValidate(true);
        vo.setSpeed(23.23f);
        vo.setLatitude(123.23f);
        vo.setLongitude(23.0124f);
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
