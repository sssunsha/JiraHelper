package com.hybris.caas.service;

import com.hybris.caas.component.*;
import com.hybris.caas.constant.Constant;
import org.springframework.stereotype.Service;

@Service
public class JiraHelperMangerService {

    private CommandHelper commandHelper = new CommandHelper();
    private GithubHelper githubHelper = new GithubHelper();
    private TicketHelper ticketHelper = new TicketHelper();
    private ReleaseNoteHelper releaseNoteHelper = new ReleaseNoteHelper();
    private SprintStatusHelper sprintStatusHelper = new SprintStatusHelper();
    private StringBuilder releaseReportFromControllerBuilder = null;

    public String startFromController(final String team) {
        handleSprintReportGeneration(Constant.Teams.valueOf(team));
        return releaseReportFromControllerBuilder.toString();
    }

    public void start(final String[] args) {
        final Constant.Teams teams = this.commandHelper.start(args);
        if (teams == Constant.Teams.NONE) {
            return;
        }
        this.handleSprintReportGeneration(teams);
    }

    private void handleSprintReportGeneration(final Constant.Teams teams) {
        this.releaseReportFromControllerBuilder = new StringBuilder();
        switch (teams) {
            case MOONCAKE:
                this.githubHelper.start(teams);
                generateReleaseReportForMooncake(releaseReportFromControllerBuilder);
                break;
            case BAMBOO:
                this.githubHelper.start(teams);
                generateReleaseReportForBamboo(releaseReportFromControllerBuilder);
                break;
            case ALL:
                this.githubHelper.start(teams);
                generateReleaseReportForBamboo(releaseReportFromControllerBuilder);
                generateReleaseReportForMooncake(releaseReportFromControllerBuilder);
                break;
            case BAMBOO_SPRINT_STATUS:
                this.sprintStatusHelper.start(Constant.Teams.BAMBOO.name());
                break;
            default:
                return;
        }
    }

    private void generateReleaseReportForBamboo(StringBuilder sb) {
        this.ticketHelper.start(this.githubHelper.getBambooJiraTicketIDMap(), "Bamboo");
        this.releaseNoteHelper.start(this.ticketHelper.getReleaseNoteMap(), this.githubHelper.getServiceMap(),
                Constant.BAMBOO_RELEASE_REPORT_FILE_LOCATION, sb);
    }

    private void generateReleaseReportForMooncake(StringBuilder sb) {
        this.ticketHelper.start(this.githubHelper.getMooncakeJiraTicketIDMap(), "Mooncake");
        this.releaseNoteHelper.start(this.ticketHelper.getReleaseNoteMap(), this.githubHelper.getServiceMap(),
                Constant.MOONCAKE_RELEASE_REPORT_FILE_LOCATION, sb);
    }
}
