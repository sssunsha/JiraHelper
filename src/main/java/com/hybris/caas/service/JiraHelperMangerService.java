package com.hybris.caas.service;

import com.hybris.caas.component.*;
import com.hybris.caas.constant.Constant;

public class JiraHelperMangerService {

    private CommandHelper commandHelper = new CommandHelper();
    private GithubHelper githubHelper = new GithubHelper();
    private TicketHelper ticketHelper = new TicketHelper();
    private ReleaseNoteHelper releaseNoteHelper = new ReleaseNoteHelper();
    private BambooSprintStatusHelper bambooSprintStatusHelper = new BambooSprintStatusHelper();

    public void start(final String[] args) {
        final Constant.Teams teams = this.commandHelper.start(args);
        if (teams == Constant.Teams.NONE) {
            return;
        }

        switch (teams) {
            case MOONCAKE:
                this.githubHelper.start(teams);
                generateReleaseReportForMooncake();
                break;
            case BAMBOO:
                this.githubHelper.start(teams);
                generateReleaseReportForBamboo();
                break;
            case ALL:
                this.githubHelper.start(teams);
                generateReleaseReportForBamboo();
                generateReleaseReportForMooncake();
                break;
            case BAMBOO_SPRINT_STATUS:
                this.bambooSprintStatusHelper.start();
                break;
            default:
                return;
        }
    }

    private void generateReleaseReportForBamboo() {
        this.ticketHelper.start(this.githubHelper.getBambooJiraTicketIDMap(), "Bamboo");
        this.releaseNoteHelper.start(this.ticketHelper.getReleaseNoteMap(), this.githubHelper.getServiceMap(),
                Constant.BAMBOO_RELEASE_REPORT_FILE_LOCATION);
    }

    private void generateReleaseReportForMooncake() {
        this.ticketHelper.start(this.githubHelper.getMooncakeJiraTicketIDMap(), "Mooncake");
        this.releaseNoteHelper.start(this.ticketHelper.getReleaseNoteMap(), this.githubHelper.getServiceMap(),
                Constant.MOONCAKE_RELEASE_REPORT_FILE_LOCATION);
    }
}
