package com.hybris.caas.component;

import com.hybris.caas.constant.Constant;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubHelper {
    // start to trigger and get the need to release pr list
    public void start() throws IOException, InterruptedException {

        // trigger the sprint report script
        Process process = Runtime.getRuntime().exec("/bin/sh " + Constant.SPRINT_REPORT_SCRIPT_LOCATION);
        int status = process.waitFor();
        if (status != 0) {
            System.err.println("Failed to trigger release report script ...");
        }
    }
}
