package com.hybris.caas.model;

import java.util.List;

public class JiraTicketFieldsResponse {
    public JiraTicketParentResponse parent;
    public JiraTicketPropertyResponse issuetype;
    public JiraTicketPropertyResponse status;
    public JiraTicketPropertyResponse priority;
    public JiraTicketPropertyResponse assignee;
    public JiraTicketPropertyResponse reporter;
    public List<JiraTicketPropertyResponse> components;
    public String summary;
    public List<JiraTicketResponse> subtasks;
}
