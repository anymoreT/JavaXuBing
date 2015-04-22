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
        ACPLocationReceiveMessage vo = ACPLocationReceiveMessage.getSampleMessageData();

        vo.generateMessage();
        String hexStr = vo.getCurrentMessageAsByteString();

        assertThat(hexStr, equalTo("48435A44020001E0B40E50002020313235343876696E3132333420202020303030303030303030303030306F696C436172640000000000000000004150AFD200503468041504201508211309000000000000000003178A870100EA00"));

        System.out.println(new LocationReceiveMessage(Utils.getByteArray(hexStr)));
    }
}
