package com.hcwins.vehicle.ta.acp.sampler.data;

import com.hcwins.vehicle.protocol.hs.AbstractMessage;
import com.hcwins.vehicle.protocol.util.Utils;
import org.apache.jmeter.util.ThreadLocalRandom;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by tommy on 4/18/15.
 */
public abstract class AbstractACPMessage implements ACPMessage {
    private String deviceId;//车载设备终端号
    private String vehicleId;//汽车识别号

    protected static int index = 0;
    protected AbstractMessage internalMessage;

    public AbstractACPMessage() {
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

    public int getIndex() {
        return index;
    }

    public static void increaseIndex() {
        index++;
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

    public static class FixedStepTimestamp {
        protected GregorianCalendar currentValue = null;
        private int stepInSeconds = 0;

        public FixedStepTimestamp() {
            currentValue = new GregorianCalendar();
            currentValue.setTime(new Date());
        }

        public FixedStepTimestamp(Date currentValue, int stepInSeconds) {
            this.currentValue = new GregorianCalendar();
            this.currentValue.setTime(currentValue);
            this.stepInSeconds = stepInSeconds;
        }

        public GregorianCalendar getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(GregorianCalendar currentValue) {
            this.currentValue = currentValue;
        }

        public int getStepInSeconds() {
            return stepInSeconds;
        }

        public void setStepInSeconds(int stepInSeconds) {
            this.stepInSeconds = stepInSeconds;
        }

        public Date next() {
            if (-1 == stepInSeconds) {
                //
            } else if (0 == stepInSeconds) {
                currentValue.setTime(new Date());
            } else {
                currentValue.add(GregorianCalendar.SECOND, stepInSeconds);
            }
            return currentValue.getTime();
        }
    }

    public static class RandomInt {
        private int currentValue = 0;
        private int minStep = 0;
        private int maxStep = 0;

        public RandomInt() {
        }

        public RandomInt(int currentValue) {
            this.currentValue = currentValue;
        }

        public int getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(int currentValue) {
            this.currentValue = currentValue;
        }

        public int getMinStep() {
            return minStep;
        }

        public void setMinStep(int minStep) {
            this.minStep = minStep;
        }

        public int getMaxStep() {
            return maxStep;
        }

        public void setMaxStep(int maxStep) {
            this.maxStep = maxStep;
        }

        public int next() {
            if (0 != minStep || 0 != maxStep) {
                currentValue += ThreadLocalRandom.current().nextInt(minStep, maxStep);
            }
            return currentValue;
        }
    }

    public static class RandomLong {
        private Long currentValue = 0L;
        private Long minStep = 0L;
        private Long maxStep = 0L;

        public RandomLong() {
        }

        public RandomLong(Long currentValue) {
            this.currentValue = currentValue;
        }

        public Long getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(Long currentValue) {
            this.currentValue = currentValue;
        }

        public Long getMinStep() {
            return minStep;
        }

        public void setMinStep(Long minStep) {
            this.minStep = minStep;
        }

        public Long getMaxStep() {
            return maxStep;
        }

        public void setMaxStep(Long maxStep) {
            this.maxStep = maxStep;
        }

        public Long next() {
            if (0 != minStep || 0 != maxStep) {
                currentValue += ThreadLocalRandom.current().nextLong(minStep, maxStep);
            }
            return currentValue;
        }
    }

    public static class RandomDouble {
        private Double currentValue = 0.;
        private Double minStep = 0.;
        private Double maxStep = 0.;
        private int scale = 10;

        public RandomDouble() {
        }

        public RandomDouble(Double currentValue, Double minStep, Double maxStep, int scale) {
            this.currentValue = currentValue;
            this.minStep = minStep;
            this.maxStep = maxStep;
            this.scale = scale;
        }

        public Double getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(Double currentValue) {
            this.currentValue = currentValue;
        }

        public Double getMinStep() {
            return minStep;
        }

        public void setMinStep(Double minStep) {
            this.minStep = minStep;
        }

        public Double getMaxStep() {
            return maxStep;
        }

        public void setMaxStep(Double maxStep) {
            this.maxStep = maxStep;
        }

        public int getScale() {
            return scale;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

        private Double next() {
            if (0 != minStep || 0 != maxStep) {
                currentValue += ThreadLocalRandom.current().nextDouble(minStep, maxStep);
            }
            return currentValue;
        }

        public float nextFloat() {
            BigDecimal bd = new BigDecimal(next());
            return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
        }

        public Double nextDouble() {
            BigDecimal bd = new BigDecimal(next());
            return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }
}
