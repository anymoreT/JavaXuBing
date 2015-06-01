package com.hcwins.vehicle.ta.acp.sampler.data;

import com.google.gson.GsonBuilder;
import com.hcwins.vehicle.protocol.hs.HeartbeatReceiveMessage;
import com.hcwins.vehicle.protocol.hs.HeartbeatSendMessage;
import com.hcwins.vehicle.ta.acp.sampler.sampler.ACPException;

import java.lang.reflect.Modifier;

/**
 * Created by xiangzhai on 20/05/15.
 */
public class ACPHeartbeatReceiveMessage extends AbstractACPMessage {
    private int serial; //消息序号

    public ACPHeartbeatReceiveMessage() {
        super();
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    @Override
    public byte[] generateMessage() {
        increaseIndex();

        HeartbeatReceiveMessage vo = new HeartbeatReceiveMessage();

        vo.setDeviceId(this.getDeviceId());
        vo.setVehicleId(this.getVehicleId());
        vo.setSerial(index);

        setInternalMessage(vo);

        return getCurrentMessage();
    }

    @Override
    public String handleReturnMessage(byte[] msg) throws ACPException {
        HeartbeatSendMessage hsm = new HeartbeatSendMessage(msg);
        return hsm.toString();
    }

    public static ACPHeartbeatReceiveMessage getSampleMessageData() {
        ACPHeartbeatReceiveMessage vo = new ACPHeartbeatReceiveMessage();

        vo.setDeviceId(deviceValue);
        vo.setVehicleId(vehicleValue);
        vo.setSerial(0);

        return vo;
    }

    public static String getDefaultRequestData() {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(getSampleMessageData());
    }
}
