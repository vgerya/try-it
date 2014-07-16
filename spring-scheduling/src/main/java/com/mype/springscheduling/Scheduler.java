package com.mype.springscheduling;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Vitaliy Gerya
 */
@Component
public class Scheduler {
    public StatusChecker getChecker() {
        return checker;
    }

    public void setChecker(final StatusChecker checker) {
        this.checker = checker;
    }

    @Autowired
    private StatusChecker checker;

    @Scheduled(cron = "0/2 * 1/1 * * *") // every second
    public void scheduledOperation() {
        System.out.println("Scheduled task at: " + new Date());

        checker.setLastStatus(check());
    }

    private String check() {
        Random random = new Random();
        int value = random.nextInt(2);
        String result = value == 0 ? "SUCCESS" : "FAIL";

        System.out.println(result);

        return result;
    }
}
