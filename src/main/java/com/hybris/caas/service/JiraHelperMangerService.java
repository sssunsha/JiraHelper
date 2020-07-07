package com.hybris.caas.service;

import com.hybris.caas.component.CommandHelper;
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

    private CommandHelper commandHelper = new CommandHelper();
    private GithubHelper githubHelper = new GithubHelper();
    private TicketHelper ticketHelper = new TicketHelper();
    private ReleaseNoteHelper releaseNoteHelper = new ReleaseNoteHelper();

    public void start(final String[] args) {
        final Constant.Teams teams = this.commandHelper.start(args);
        if (teams == Constant.Teams.NONE) {
            return;
        }
        // start to fetch and parse PR data from github
        this.githubHelper.start();
        this.githubHelper.readReleaseReportFile();

        switch (teams) {
            case MOONCAKE:
                generateReleaseReportForMooncake();
                break;
            case BAMBOO:
                generateReleaseReportForBamboo();
                break;
            case ALL:
                generateReleaseReportForBamboo();
                generateReleaseReportForMooncake();
                break;
            default:
                return;
        }
    }

    private void generateReleaseReportForBamboo() {
        this.ticketHelper.start(this.githubHelper.getBambooJiraTicketIDMap(), "Bamboo");
        this.releaseNoteHelper.start(this.ticketHelper.getReleaseNoteMap(),
                Constant.BAMBOO_RELEASE_REPORT_FILE_LOCATION);
    }

    private void generateReleaseReportForMooncake() {
        this.ticketHelper.start(this.githubHelper.getMooncakeJiraTicketIDMap(), "Mooncake");
        this.releaseNoteHelper.start(this.ticketHelper.getReleaseNoteMap(),
                Constant.MOONCAKE_RELEASE_REPORT_FILE_LOCATION);
    }
}
