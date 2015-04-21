package com.hcwins.vehicle.ta.acp.sampler.data;

import org.testng.annotations.Test;

/**
 * Created by tommy on 4/20/15.
 */
public class ACPLocationReceiveMessageDataTest {
    @Test
    public void test() {
        ACPLocationReceiveMessageData vo = ACPLocationReceiveMessageData.getSampleMessageData();

        vo.generateMessageData();
        String hexStr = vo.getCurrentMessageDataAsByteString();

        // assertThat(hexStr, equalTo("48435A44020001E0B40E50002020313235343876696E3132333420202020303030303030303030303030306F696C436172640000000000000000004150AFD200503468041504201508211309000000000000000003178A870100EA00"));

        // System.out.println(new LocationReceiveMessage(Utils.getByteArray(hexStr)));
    }
}
