package com.hcwins.vehicle.evs.ta.apitest;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by tommy on 3/20/15.
 */
public class DummyAT {
    @Test
    public void test() {
        assertThat("1", is(equalTo("1")));
    }

}
