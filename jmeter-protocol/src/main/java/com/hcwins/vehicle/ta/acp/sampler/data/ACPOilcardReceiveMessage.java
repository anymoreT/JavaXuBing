package com.hcwins.vehicle.ta.acp.sampler.data;

import com.google.gson.GsonBuilder;
import com.hcwins.vehicle.protocol.hs.OilcardReceiveMessage;
import com.hcwins.vehicle.protocol.hs.OilcardSendMessage;
import com.hcwins.vehicle.protocol.hs.TradeInfo;
import com.hcwins.vehicle.ta.acp.sampler.sampler.ACPException;

import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xiangzhai on 27/05/15.
 */
public class ACPOilcardReceiveMessage extends AbstractACPMessage{

    private int serial;                                             //消息序号
    private FixedStepTimestamp sendTime;                            //发送时间
    private String companyId;                                       //公司ID
    private OilcardReceiveMessage.OilCardType oilcardType;          //加油卡类型
    private String oilCardNumber;                                   //加油卡卡号
    private int tradeNumber;                                        //交易记录数
    private List<TradeInfo> tradeInfoList;                          //交易信息

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

    public OilcardReceiveMessage.OilCardType getOilcardType() {
        return oilcardType;
    }

    public void setOilcardType(OilcardReceiveMessage.OilCardType oilcardType) {
        this.oilcardType = oilcardType;
    }

    public String getOilCardNumber() {
        return oilCardNumber;
    }

    public void setOilCardNumber(String oilCardNumber) {
        this.oilCardNumber = oilCardNumber;
    }

    public int getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(int tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public List<TradeInfo> getTradeInfoList() {
        return tradeInfoList;
    }

    public void setTradeInfoList(List<TradeInfo> tradeInfoList) {
        this.tradeInfoList = tradeInfoList;
    }

    public ACPOilcardReceiveMessage(){
        super();
    }

    @Override
    public byte[] generateMessage() {
        increaseIndex();

        OilcardReceiveMessage vo = new OilcardReceiveMessage();

        vo.setDeviceId(this.getDeviceId());
        vo.setVehicleId(this.getVehicleId());
        vo.setSerial(index);
        vo.setSendTime(this.getSendTime().next());
        vo.setCompanyId(this.getCompanyId());
        vo.setOilcardType(this.getOilcardType());
        vo.setOilCardNumber(this.getOilCardNumber());
        vo.setTradeInfoList(this.getTradeInfoList());

        setInternalMessage(vo);

        return getCurrentMessage();
    }

    @Override
    public String handleReturnMessage(byte[] msg) throws ACPException {
        OilcardSendMessage resmsg = new OilcardSendMessage(msg);
        return resmsg.toString();
    }

    public static ACPOilcardReceiveMessage getSampleMessageData() {
        ACPOilcardReceiveMessage vo = new ACPOilcardReceiveMessage();

        vo.setDeviceId(deviceValue);
        vo.setVehicleId(vehicleValue);
        vo.setSerial(0);
        vo.setSendTime(new FixedStepTimestamp(new Date(1429513701040L), -1));
        vo.setCompanyId(companyValue);
        vo.setOilcardType(OilcardReceiveMessage.OilCardType.findBy("01"));
        vo.setOilCardNumber(oilcardValue);
        vo.setTradeInfoList(getTradeInfoList(new Random().nextInt(8)+1));

        return vo;
    }

    private static List<TradeInfo> getTradeInfoList(int items) {
        List<TradeInfo> list = new ArrayList<TradeInfo>();
        for(int i=0;i<items;i++) {
            TradeInfo tf = new TradeInfo();
            tf.setTerminalNumber("terminal01");
            tf.setTradeMoney(new Random().nextFloat()+236);
            tf.setTradeTime(getDate(new Random().nextInt(60 * 60 * 1000) + 24 * 60 * 60 * 1000));
            tf.setTradeType(TradeInfo.TradeType.findBy(getValue()));
            list.add(tf);
        }
        return list;
    }

    private static Date getDate(int sub) {
        return new Date(Calendar.getInstance().getTimeInMillis()-sub);
    }

    private static String getValue() {
        String[] s = {"01","02","03","04","05","06","07","93"};
        return s[new Random().nextInt(s.length)];
    }

    public static String getDefaultRequestData() {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(getSampleMessageData());
    }
}
