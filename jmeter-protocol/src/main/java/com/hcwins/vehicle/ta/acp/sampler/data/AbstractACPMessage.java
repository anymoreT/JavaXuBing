package com.hcwins.vehicle.ta.acp.sampler.data;

import com.hcwins.vehicle.protocol.hs.AbstractMessage;
import com.hcwins.vehicle.protocol.util.Utils;

/**
 * Created by tommy on 4/18/15.
 */
public abstract class AbstractACPMessage implements ACPMessage {
    protected int index = 0;

    protected AbstractMessage internalMessage;

    public AbstractACPMessage() {
        //
    }

    public int getIndex() {
        return index;
    }

    public void increaseIndex() {
        this.index++;
    }

    protected AbstractMessage getInternalMessage() {
        return internalMessage;
    }

    protected void setInternalMessage(AbstractMessage internalMessage) {
        this.internalMessage = internalMessage;
    }

    @Override
    public byte[] getCurrentMessage() {
        return getInternalMessage().encode();
    }

    @Override
    public String getCurrentMessageAsByteString() {
        return Utils.byteToStr(getCurrentMessage());
    }

    @Override
    public String getCurrentMessageAsReadableString() {
        return getInternalMessage().toString();
    }
}
