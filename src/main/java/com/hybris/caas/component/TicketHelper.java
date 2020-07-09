package com.hybris.caas.component;

import com.hybris.caas.constant.Constant;
import com.hybris.caas.model.GithubTicket;
import com.hybris.caas.model.JiraTicket;
import com.hybris.caas.model.ReleaseNote;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class TicketHelper {

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    public Map<String, ReleaseNote> getReleaseNoteMap() {
        return releaseNoteMap;
    }

    private Map<String, ReleaseNote> releaseNoteMap = new HashMap<>();
    private String team;

    public void start(Map<String, GithubTicket> ticketMap, final String team) {
        System.out.println("Start to get jira tickets for " + "team ...");
        this.team = team;
        // get the authorization data from local env
        String authorization = System.getenv(Constant.LOCAL_AUTHORIZATION_ENV);
        headers.set("Accept", "application/json");
        headers.set("Authorization", authorization);

        ticketMap.forEach((key, value) -> this.parseJiraTicket(fetchJiraTicketByID(key), value.getRepository()));
        System.out.println("Finish to generate release report for " +  team);
    }

    private void parseJiraTicket(final JiraTicket body, final String repository) {
        if (body != null) {
            // when it has the parent, get the parent one instead
            if (body.fields.parent != null) {
                this.parseJiraTicket(this.fetchJiraTicketByID(body.fields.parent.key), repository);
                return;
            } else {
                ReleaseNote rn = new ReleaseNote();
                rn.repository = repository;
                rn.description = body.key +  " " + body.fields.summary;
                rn.team = this.team;
                switch (body.fields.issuetype.name) {
                    case "Story":
                        rn.type = "User story";
                        break;
                    case "Bug":
                        rn.type = "Bugfix";
                        break;
                    case "Task":
                        rn.type = "Task";
                }
                this.releaseNoteMap.put(body.key, rn);
            }
        }
    }

    private JiraTicket fetchJiraTicketByID(final String id) {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<JiraTicket> exchange = restTemplate.exchange(Constant.JIRA_GET_ISSUE_URL + id,
                HttpMethod.GET, entity, JiraTicket.class);
        return exchange.getBody();
    }
}
