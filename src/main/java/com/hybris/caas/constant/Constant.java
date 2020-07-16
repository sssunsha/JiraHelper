package com.hybris.caas.constant;

public class Constant {

    public static enum Teams {
        ALL,
        BAMBOO,
        BAMBOO_SPRINT_STATUS,
        MOONCAKE,
        NONE
    };

    public static final String[] BAMBOO_REPOSITORIES = {
        "caas2-uaa-configuration",
        "approuter",
        "continuity-engine",
        "customer-service",
        "job-scheduler-service",
        "order-streaming",
        "standing-order-service",
        "caas2-idp-service",
        "payment-service"};

    public static final String[] MOONCAKE_REPOSITORIES = {
        "order-broker",
        "order-export",
        "shipping-service",
        "tax-service",
        "configuration-service",
        "gbaas-service"};

    public static final String SPRINT_REPORT_MARK = "----------------------------------------------------------------------------";
    public static final String SPRINT_REPORT_TICKET_REGX = "(CAAS|caas)-[0-9]+";
    public static final String SPRINT_REPORT_SCRIPT_LOCATION = "./sprint-release-report.sh";
    public static final String SPRINT_REPORT_FILE_LOCATION ="./release_notes.txt";
    public static final String BAMBOO_RELEASE_REPORT_FILE_LOCATION = "./bamboo_release_report.md";
    public static final String MOONCAKE_RELEASE_REPORT_FILE_LOCATION = "./mooncake_release_report.md";


    public static final String LOCAL_AUTHORIZATION_ENV = "local_authorization";
    public static final String JIRA_GET_ISSUE_URL = "https://cxjira.sap.com/rest/api/2/issue/";

    public static final String JIRA_SEARCH_ISSUES_URL = "https://cxjira.sap.com/rest/api/2/search?maxResults={maxResults}&jql={jql}";
    public static final String JIRA_SEARCH_ISSUES_JQL = "project = \"Commerce as a Service\" AND component in " +
            "(\"Bamboo - Customer, Clienteling, Order Export\") AND assignee in (EMPTY, \"will.sun@sap.com\", " +
            "\"simon.zhong@sap.com\", \"scarlett.song@sap.com\", \"jonathan.wang01@sap.com\", \"derry.dai@sap.com\", " +
            "\"kevin.su@sap.com\", \"jerify.zhang@sap.com\", \"terry.tan01@sap.com\", \"maggie.lin@sap.com\", " +
            "\"lealhom.li@sap.com\", \"j.qian@sap.com\", \"owen.liu02@sap.com\", \"allen.wu02@sap.com\") " +
            "AND Sprint in openSprints() ORDER BY Rank ASC";
    public static final String JIRA_SEARCH_ISSUE_MAXRESULTS = "500";
}
