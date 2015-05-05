package com.hcwins.vehicle.ta.acp.sampler.data;

import com.hcwins.vehicle.protocol.hs.LocationReceiveMessage;
import com.hcwins.vehicle.protocol.util.Utils;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by tommy on 4/20/15.
 */
public class ACPLocationReceiveMessageDataTest {
    @Test
    public void test() {
        System.out.println(ACPLocationReceiveMessage.getDefaultRequestData());

        ACPLocationReceiveMessage vo = ACPLocationReceiveMessage.getSampleMessageData();

        vo.generateMessage();
        System.out.println(vo.getCurrentMessageAsReadableString());
        String hexStr = vo.getCurrentMessageAsByteString();
        System.out.println(new LocationReceiveMessage(Utils.getByteArray(hexStr)));

        assertThat(hexStr, equalTo("48435A44020001E0DB1350002020313235343876696E31323334636F6D38303030303030303030303030306F696C4361726400E8030000000000004150AFD200503468041504201508371309B053A600E0A5010003178A870100EA00"));
    }
}
