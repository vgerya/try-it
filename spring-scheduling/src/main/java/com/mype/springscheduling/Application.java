package com.mype.springscheduling;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Vitaliy Gerya
 */
public class Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationcontext-springScheduling.xml");

        StatusChecker checker = context.getBean(StatusChecker.class);

        MetricRegistry metricRegistry = new MetricRegistry();
        metricRegistry.register(MetricRegistry.name(StatusChecker.class, "lastStatus"), checker);
        JmxReporter.forRegistry(metricRegistry).build().start();

//        final MetricRegistry registry = new MetricRegistry();
//        registry.register(MetricRegistry.name(Scheduler.class, "status"), new Gauge<String>() {
//            @Override
//            public String getValue() {
//                Random random = new Random();
//                int value = random.nextInt(2);
//                String result = value == 0 ? "SUCCESS" : "FAIL";
//
//                System.out.println(result);
//
//                return result;
//            }
//        });
//        final JmxReporter reporter = JmxReporter.forRegistry(registry).build();
//        reporter.start();

//        final HealthCheckRegistry healthChecks = new HealthCheckRegistry();
//        healthChecks.register(MetricRegistry.name(Scheduler.class, "health"), new HealthCheck() {
//            @Override
//            protected Result check() throws Exception {
//                Random random = new Random();
//                int value = random.nextInt(2);
//                Result result = value == 0 ? Result.healthy("healthy") : Result.unhealthy("unhealthy");
//
//                System.out.println(result);
//
//                return result;
//            }
//        });
//        SortedMap<String, HealthCheck.Result> map = healthChecks.runHealthChecks();
//        for (String s : map.keySet()) {
//            System.out.printf(s);
//        }
    }
}
