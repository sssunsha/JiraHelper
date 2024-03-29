package com.hybris.caas.component;

import com.hybris.caas.constant.Constant;
import com.hybris.caas.model.GithubTicket;
import com.hybris.caas.model.JiraTicketResponse;
import com.hybris.caas.model.ReleaseNote;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class TicketHelper {

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    public Map<String, ReleaseNote> getReleaseNoteMap() {
        return releaseNoteMap;
    }

    // Since there is one ticket in multiple service, so the map key is [ticket key:repository]
    private Map<String, ReleaseNote> releaseNoteMap = new HashMap<>();

    private String team;

    public void start(Map<String, GithubTicket> ticketMap, final String team) {
        System.out.println("Start to get jira tickets for " + "team ...");
        this.releaseNoteMap.clear();
        this.team = team;
        // get the authorization data from local env
        String authorization = System.getenv(Constant.LOCAL_AUTHORIZATION_ENV);
        headers.set("Accept", "application/json");
        headers.set("Authorization", authorization);

        ticketMap.forEach((key, value) -> this.parseJiraTicket(fetchJiraTicketByID(
                parseJiraTicketIDForTicketKey(key)), value.getRepository()));
        System.out.println("Finish to generate release report for " +  team);
    }

    private String parseJiraTicketIDForTicketKey(final String key) {
        return key.substring(0, key.indexOf(":"));
    }

    private void parseJiraTicket(final JiraTicketResponse body, final String repository) {
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
                    default:
                        rn.type = "Task";
                }
                String releaseNoteKey = body.key + ":" + repository;
                this.releaseNoteMap.put(releaseNoteKey, rn);
            }
        }
    }

    private JiraTicketResponse fetchJiraTicketByID(final String id) {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<JiraTicketResponse> exchange = restTemplate.exchange(Constant.JIRA_GET_ISSUE_URL + id,
                    HttpMethod.GET, entity, JiraTicketResponse.class);
            return exchange.getBody();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
