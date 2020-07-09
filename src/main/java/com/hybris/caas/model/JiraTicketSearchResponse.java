package com.hybris.caas.model;

import java.util.List;

public class JiraTicketSearchResponse {
    public String expand;
    public int startAt;
    public int maxResults;
    public int total;
    public List<JiraTicketResponse> issues;
}
