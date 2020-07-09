package com.hybris.caas.model;

import java.util.List;

public class JIraTicketSearchResponse {
    public String expand;
    public int startAt;
    public int maxResults;
    public int total;
    public List<JiraTicket> issues;
}
