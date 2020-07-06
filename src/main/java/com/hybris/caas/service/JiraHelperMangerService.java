package com.hybris.caas.service;

import com.hybris.caas.component.GithubHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class JiraHelperMangerService {

    private GithubHelper githubHelper = new GithubHelper();

    public void start() {
//        this.githubHelper.start();
        this.githubHelper.readReleaseReportFile();
    }
}
