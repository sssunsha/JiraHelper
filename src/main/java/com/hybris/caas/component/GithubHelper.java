package com.hybris.caas.component;

import com.hybris.caas.constant.Constant;
import com.hybris.caas.model.GithubTicket;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GithubHelper {

    private StringBuffer reportBuffer = new StringBuffer();

    public Map<String, GithubTicket> getBambooJiraTicketIDMap() {
        return BambooJiraTicketIDMap;
    }

    public Map<String, GithubTicket> getMooncakeJiraTicketIDMap() {
        return MooncakeJiraTicketIDMap;
    }

    private Map<String, GithubTicket> BambooJiraTicketIDMap = new HashMap<>();

    private Map<String, GithubTicket> MooncakeJiraTicketIDMap = new HashMap<>();

    // start to trigger and get the need to release pr list
    public void start(Constant.Teams team) {

        // trigger the sprint report script
        Process process = null;
        try {
            String subCommand = "";
            switch (team) {
                case BAMBOO:
                    subCommand = "-b";
                    break;
                case MOONCAKE:
                    subCommand = "-m";
                    break;
                case ALL:
                    subCommand = "-a";
            }
            process = Runtime.getRuntime().exec("/bin/sh " + Constant.SPRINT_REPORT_SCRIPT_LOCATION + " " + subCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Start to generate sprint release report for " + team.name() + " ...");
        int status = 0;
        try {
            status = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (status != 0) {
            System.err.println("Failed to trigger release report script ...");
        } else {
            System.out.println("Finish to generate release report ...");
            this.readReleaseReportFile(team);
        }
    }

    public void readReleaseReportFile(Constant.Teams team) {
        try {
            System.out.println("Start to read sprint release report ...");
            InputStream f = new FileInputStream(Constant.SPRINT_REPORT_FILE_LOCATION);
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader((f)));
            try {
                line = reader.readLine();
                while (line != null) {
                    reportBuffer.append(line);
                    line = reader.readLine();
                }
                reader.close();
                f.close();
                System.out.println("Finish to read sprint release report ...");
                this.parseSprintReport(team);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void parseSprintReport(Constant.Teams team) {
        String report = this.reportBuffer.toString();
        switch (team) {
            case BAMBOO:
                System.out.println("Start to parse Bamboo services ...");
                this.parseSprintReportHelp(report, Constant.BAMBOO_REPOSITORIES, this.BambooJiraTicketIDMap);
                System.out.println("Finish to parse Bamboo services ...");
                return;
            case MOONCAKE:
                System.out.println("Start to parse Mooncake services ...");
                this.parseSprintReportHelp(report, Constant.MOONCAKE_REPOSITORIES, this.MooncakeJiraTicketIDMap);
                System.out.println("Finish to parse Mooncake services ...");
                return;
            case ALL:
                System.out.println("Start to parse Bamboo services ...");
                this.parseSprintReportHelp(report, Constant.BAMBOO_REPOSITORIES, this.BambooJiraTicketIDMap);
                System.out.println("Finish to parse Bamboo services ...");
                System.out.println("Start to parse Mooncake services ...");
                this.parseSprintReportHelp(report, Constant.MOONCAKE_REPOSITORIES, this.MooncakeJiraTicketIDMap);
                System.out.println("Finish to parse Mooncake services ...");
                return;
        }
    }

    private void parseSprintReportHelp(final String report,  final String[] repositories, Map<String, GithubTicket> map) {
        map.clear();
        for (String repository : repositories) {
            int head = report.indexOf(repository);
            int end = report.indexOf(Constant.SPRINT_REPORT_MARK, head);
            String subStr = report.substring(head, end);
            // get all the jira tickets IDs
            map.putAll(this.getAllJiraTicketIDs(repository, subStr));
        }
    }

    private Map<String, GithubTicket> getAllJiraTicketIDs(final String repository, final String str) {
        Pattern p = Pattern.compile(Constant.SPRINT_REPORT_TICKET_REGX);
        Matcher m = p.matcher(str);
        Map<String, GithubTicket> map = new HashMap<>();
        while (m.find()) {
            GithubTicket gt = new GithubTicket();
            gt.setRepository(repository);
            gt.setTicketID(m.group());
            map.put(m.group(), gt);
        }
        return map;
    }
}
