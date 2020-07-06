package com.hybris.caas.service;

import com.hybris.caas.component.GithubHelper;
import com.hybris.caas.component.ReleaseNoteHelper;
import com.hybris.caas.component.TicketHelper;
import com.hybris.caas.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class JiraHelperMangerService {

    private GithubHelper githubHelper = new GithubHelper();
    private TicketHelper ticketHelper = new TicketHelper();
    private ReleaseNoteHelper releaseNoteHelper = new ReleaseNoteHelper();

    public void start() {
        // start to fetch and parse PR data from github
//        this.githubHelper.start();
        this.githubHelper.readReleaseReportFile();

        // start to fetch ticket data from Jira based on github pr data
        // first for Bamboo
        this.ticketHelper.start(this.githubHelper.getBambooJiraTicketIDMap(), "Bamboo");

        // start to generate the release report file for Bamboo
        this.releaseNoteHelper.start(this.ticketHelper.getReleaseNoteMap(),
                Constant.BAMBOO_RELEASE_REPORT_FILE_LOCATION);

        // then for Mooncake
        this.ticketHelper.start(this.githubHelper.getMooncakeJiraTicketIDMap(), "Mooncake");

        // start to generate release report file for Mooncake
        this.releaseNoteHelper.start(this.ticketHelper.getReleaseNoteMap(),
                Constant.MOONCAKE_RELEASE_REPORT_FILE_LOCATION);
    }
}
