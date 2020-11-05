package com.hybris.caas.component;

import com.hybris.caas.constant.Constant;
import com.hybris.caas.model.JiraTicket;
import com.hybris.caas.model.JiraTicketSearchResponse;
import com.hybris.caas.model.SprintStatusReport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/*
query jql:
project = "Commerce as a Service" AND component in ("Bamboo - Customer, Clienteling, Order Export") AND assignee in (EMPTY, "will.sun@sap.com", "simon.zhong@sap.com", "scarlett.song@sap.com", "jonathan.wang01@sap.com", "derry.dai@sap.com", "kevin.su@sap.com", "jerify.zhang@sap.com", "terry.tan01@sap.com", "maggie.lin@sap.com", "lealhom.li@sap.com", "j.qian@sap.com", "owen.liu02@sap.com", "allen.wu02@sap.com") AND Sprint in openSprints() ORDER BY Rank ASC
 */

@Component
public class SprintStatusHelper {

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private SprintStatusReport sprintStatusReport = null;

    public void start(String team) {
        // search the current sprint all tickets for Bamboo
        switch (team) {
            case "BAMBOO":
                fetchBambooCurrentSprintAllTickets();
                break;
        }
    }

    private void fetchBambooCurrentSprintAllTickets() {
        System.out.println("Start to fetch current sprint all tickets for Bamboo:");
        String authorization = System.getenv(Constant.LOCAL_AUTHORIZATION_ENV);
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", authorization);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("jql", Constant.JIRA_CURRENT_SPRINT_TICKETS_JQL);
        paramMap.put("maxResults", Constant.JIRA_SEARCH_ISSUE_MAXRESULTS);
        ResponseEntity<JiraTicketSearchResponse> exchange = restTemplate.exchange(Constant.JIRA_SEARCH_ISSUES_URL,
                HttpMethod.GET, entity, JiraTicketSearchResponse.class, paramMap);
        System.out.println("Finish fetch current sprint all tickets for Bamboo:");
        // TODO: continue to fetch current sprint
//        getBoardInformation();
        parseBambooCurrentSprintReport(exchange.getBody());
    }

    private void getBoardInformation() {
        String authorization = System.getenv(Constant.LOCAL_AUTHORIZATION_ENV);
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", authorization);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        Map<String, String> paramMap = new HashMap<String, String>();
        ResponseEntity<Object> exchange = restTemplate.exchange(Constant.JIRA_GET_BOARD_URL,
                HttpMethod.GET, entity, Object.class, paramMap);
        System.out.println(exchange.getBody());
    }

    private void parseBambooCurrentSprintReport(final JiraTicketSearchResponse jiraTicketSearchResponse) {
        System.out.println("Start to parse current sprint all tickets for Bamboo:");
        sprintStatusReport = new SprintStatusReport();
        sprintStatusReport.total = jiraTicketSearchResponse.total;
        List<JiraTicket> jiraTickets = jiraTicketSearchResponse.issues.stream().map(issue -> {
            JiraTicket ticket = new JiraTicket();
            ticket.key = issue.key;
            ticket.id = issue.id;
            ticket.name = issue.fields.summary;
            ticket.type = issue.fields.issuetype.name;
            ticket.assignee = issue.fields.assignee != null ? issue.fields.assignee.displayName : null;
            ticket.reporter = issue.fields.reporter.displayName;
            ticket.status = issue.fields.status.name;
            ticket.priority = issue.fields.priority.name;
            ticket.components = issue.fields.components.stream().map(c -> c.name).collect(Collectors.toList());
            ticket.parent = issue.fields.parent != null ? issue.fields.parent.key : null;
            ticket.subTasks = issue.fields.subtasks.stream().map(st -> st.key).collect(Collectors.toList());
            return ticket;
        }).collect(Collectors.toList());
        System.out.println("Finish parse current sprint all tickets for Bamboo:");
        sprintStatusReport.tasks = jiraTickets.stream().filter(t -> t.type.compareTo("Task") == 0).collect(Collectors.toList());
        sprintStatusReport.bugs = jiraTickets.stream().filter(t -> t.type.compareTo("Bug") == 0).collect(Collectors.toList());
        sprintStatusReport.stories = jiraTickets.stream().filter(t -> t.type.compareTo("Story") == 0).collect(Collectors.toList());

    }

}
