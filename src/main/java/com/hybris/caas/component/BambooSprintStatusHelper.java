package com.hybris.caas.component;

import com.hybris.caas.constant.Constant;
import com.hybris.caas.model.JIraTicketSearchResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/*
query jql:
project = "Commerce as a Service" AND component in ("Bamboo - Customer, Clienteling, Order Export") AND assignee in (EMPTY, "will.sun@sap.com", "simon.zhong@sap.com", "scarlett.song@sap.com", "jonathan.wang01@sap.com", "derry.dai@sap.com", "kevin.su@sap.com", "jerify.zhang@sap.com", "terry.tan01@sap.com", "maggie.lin@sap.com", "lealhom.li@sap.com", "j.qian@sap.com", "owen.liu02@sap.com", "allen.wu02@sap.com") AND Sprint in openSprints() ORDER BY Rank ASC
 */

public class BambooSprintStatusHelper {

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private JIraTicketSearchResponse jIraTicketSearchResponse = null;

    public void start() {
        // search the current sprint all tickets for Bamboo
        fetchBambooCurrentSprintAllTickets();
    }

    private void fetchBambooCurrentSprintAllTickets() {
        String authorization = System.getenv(Constant.LOCAL_AUTHORIZATION_ENV);
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", authorization);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("jql", Constant.JIRA_SEARCH_ISSUES_JQL);
        paramMap.put("maxResults", Constant.JIRA_SEARCH_ISSUE_MAXRESULTS);
        ResponseEntity<JIraTicketSearchResponse> exchange = restTemplate.exchange(Constant.JIRA_SEARCH_ISSUES_URL,
                HttpMethod.GET, entity, JIraTicketSearchResponse.class, paramMap);
        this.jIraTicketSearchResponse = exchange.getBody();
    }
}
