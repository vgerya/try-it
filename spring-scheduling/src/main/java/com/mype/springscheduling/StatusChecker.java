package com.mype.springscheduling;


import org.springframework.stereotype.Component;

/**
 * @author Vitaliy Gerya
 */
@Component
public class StatusChecker implements com.codahale.metrics.Gauge<String> {
    private String lastStatus = "UNDEFINED";

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(final String lastStatus) {
        this.lastStatus = lastStatus;
    }

    @Override
    public String getValue() {
        return getLastStatus();
    }
}
