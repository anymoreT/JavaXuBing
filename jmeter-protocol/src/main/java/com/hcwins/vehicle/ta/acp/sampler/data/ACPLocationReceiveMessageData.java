package com.hcwins.vehicle.ta.acp.sampler.data;

import com.hcwins.vehicle.protocol.obd.LocationReceiveMessage;

import java.util.Date;

/**
 * Created by tommy on 4/18/15.
 */
public class ACPLocationReceiveMessageData extends AbstractACPMessageData {
    private String deviceId;//车载设备终端号
    private String vehicleId;//汽车识别号
    private String oilCardNumber;//加油卡卡号
    private LocationReceiveMessage.PackageType packageType;//标记
    private Date timestamp;//GPS 时间,BCD 码
    private boolean gpsValidate;//GPS 数据是否有效  A 有效 V 无效
    private float speed;
    private float latitude;//纬度
    private float longitude;//经度
    private LocationReceiveMessage.AccStatus accStatus;
    private LocationReceiveMessage.OilCardStatus oilCardStatus;
    private int satelliteNumber;//卫星数
    private float altitute; //海拔高度
    private float directionAngel;//方向角

    public static String getDefaultRequestData() {
        return "this is ACPLocationReceiveMessageData";
    }
}
