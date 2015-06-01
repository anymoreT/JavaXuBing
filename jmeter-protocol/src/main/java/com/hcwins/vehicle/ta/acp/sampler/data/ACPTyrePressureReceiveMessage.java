package com.hcwins.vehicle.ta.acp.sampler.data;

import com.google.gson.GsonBuilder;
import com.hcwins.vehicle.protocol.hs.TyrePressureInfo;
import com.hcwins.vehicle.protocol.hs.TyrePressureReceiveMessage;
import com.hcwins.vehicle.protocol.hs.TyrePressureSendMessage;
import com.hcwins.vehicle.ta.acp.sampler.sampler.ACPException;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by xiangzhai on 29/05/15.
 */
public class ACPTyrePressureReceiveMessage extends AbstractACPMessage {
    private int serial;                                     //消息序号
    private FixedStepTimestamp sendTime;                    //发送时间
    private String companyId;                               //公司ID
    private int tyreNumber;                                 //轮胎信息数量
    private List<TyrePressureInfo> tyrePressureInfoList;

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public FixedStepTimestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(FixedStepTimestamp sendTime) {
        this.sendTime = sendTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getTyreNumber() {
        return tyreNumber;
    }

    public void setTyreNumber(int tyreNumber) {
        this.tyreNumber = tyreNumber;
    }

    public List<TyrePressureInfo> getTyrePressureInfoList() {
        return tyrePressureInfoList;
    }

    public void setTyrePressureInfoList(List<TyrePressureInfo> tyrePressureInfoList) {
        this.tyrePressureInfoList = tyrePressureInfoList;
    }

    @Override
    public byte[] generateMessage() {
        increaseIndex();

        TyrePressureReceiveMessage vo = new TyrePressureReceiveMessage();

        vo.setDeviceId(this.getDeviceId());
        vo.setVehicleId(this.getVehicleId());
        vo.setSerial(index);
        vo.setSendTime(this.getSendTime().next());
        vo.setCompanyId(this.getCompanyId());
        vo.setTyreNumber(this.getTyreNumber());
        vo.setTyrePressureInfoList(this.getTyrePressureInfoList());

        setInternalMessage(vo);

        return getCurrentMessage();
    }

    @Override
    public String handleReturnMessage(byte[] msg) throws ACPException {
        return new TyrePressureSendMessage(msg).toString();
    }

    public static ACPTyrePressureReceiveMessage getSampleMessageData() {
        ACPTyrePressureReceiveMessage vo = new ACPTyrePressureReceiveMessage();

        vo.setDeviceId(deviceValue);
        vo.setVehicleId(vehicleValue);
        vo.setSerial(0);
        vo.setSendTime(new FixedStepTimestamp(new Date(1429513701040L), -1));
        vo.setCompanyId(companyValue);
        vo.setTyreNumber(new Random().nextInt(7)+1);
        vo.setTyrePressureInfoList(getPressureInfoList(vo.getTyreNumber()));

        return vo;
    }

    private static List<TyrePressureInfo> getPressureInfoList(int items) {
        List<TyrePressureInfo> list = new ArrayList<TyrePressureInfo>();

        for (int i=0;i<items;i++) {
            TyrePressureInfo tp = new TyrePressureInfo();

            tp.setLp(getBoolean());
            tp.setHp(getAgainstBoolean(tp.isLp()));
            tp.setHt(getBoolean());
            tp.setLb(getBoolean());
            tp.setLk(getBoolean());
            tp.setNs(getBoolean());
            tp.setPressureValue(new Random().nextInt(1301));
            tp.setTemperature(new Random().nextInt(166)-40);
            tp.setTyreIndex(new Random().nextInt(8)+1);

            list.add(tp);
        }

        return list;
    }

    private static boolean getBoolean() {
        boolean[] booleanArray = {true, false};
        return booleanArray[new Random().nextInt(booleanArray.length)];
    }

    private static boolean getAgainstBoolean(boolean b) {
        boolean[] booleanArray = {true, false};
        boolean c;

        do {
            c = booleanArray[new Random().nextInt(booleanArray.length)];
        }while(c==b);

        return c;

    }

    public static String getDefaultRequestData() {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(getSampleMessageData());
    }
}
