package com.github.hykes;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author hehaiyangwork@gmail.com
 * @date 2018-12-20 23:05:00
 */
@Configuration
@ComponentScan({"com.github.hykes"})
public class BugReportAutoConfiguration {

    @Bean
    @ConfigurationProperties(ignoreUnknownFields = false, prefix = "bug-report" )
    public BugReportProperties bugReportProperties(){
        return new BugReportProperties();
    }

    @Bean
    @ConditionalOnBean(BugReportProperties.class)
    public Reporter reporter(BugReportProperties bugReportProperties) {
        return new Reporter(bugReportProperties);
    }

    @Bean
    @ConditionalOnBean(Reporter.class)
    @ConditionalOnMissingBean(BugReport.class)
    public BugReport bugReport(Reporter reporter) {
        return new BugReport(reporter);
    }

}
