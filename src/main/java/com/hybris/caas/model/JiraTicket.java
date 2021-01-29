package com.hybris.caas.model;

import java.util.ArrayList;
import java.util.List;

public class JiraTicket {
    public String id;
    public String key; // CAAS-xxxxxx
    public String name;
    public String type;
    public String assignee;
    public String reporter;
    public String status;
    public String priority;
    public List<String> components;
    public String sprint;
    public List<JiraTicket> subTasks = new ArrayList<>(); // ticket key collection
    public String parent; // store parent ticket key here
}
