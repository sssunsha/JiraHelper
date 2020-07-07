package com.hybris.caas.constant;

public class Constant {

    public static enum Teams {
        ALL,
        BAMBOO,
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
    public static final String SPRINT_REPORT_TICKET_REGX = "CAAS-[0-9]+";
    public static final String REPOSITORY_LOCATION = "/Users/i340818/workspace/github";
    public static final String SPRINT_REPORT_SCRIPT_LOCATION = "/Users/i340818/workspace/personal/workspace/sprint-release-report/sprint-release-report.sh";
    public static final String SPRINT_REPORT_FILE_LOCATION ="./release_notes.txt";
    public static final String BAMBOO_RELEASE_REPORT_FILE_LOCATION = "./bamboo_release_report.md";
    public static final String MOONCAKE_RELEASE_REPORT_FILE_LOCATION = "./mooncake_release_report.md";


    public static final String LOCAL_AUTHORIZATION_ENV = "local_authorization";
    public static final String JIRA_URL = "https://cxjira.sap.com/rest/api/2/issue/";
}
