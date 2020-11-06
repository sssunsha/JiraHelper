package com.hybris.caas.controller;

import com.hybris.caas.component.SprintStatusHelper;
import com.hybris.caas.model.SprintStatusReport;
import com.hybris.caas.service.JiraHelperMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    private JiraHelperMangerService jiraHelperMangerService;

    @Autowired
    private SprintStatusHelper sprintStatusHelper;

    @GetMapping(path = "/release-report/{team}")
    public ResponseEntity<Object> GetReleaseReport(@PathVariable String team) {
        System.out.println("release report for team: " + team);
        return ResponseEntity.ok(this.jiraHelperMangerService.startFromController(team));
    }

    @GetMapping(path = "/release-status/{team}")
    public ResponseEntity<Object> GetReleaseStatus(@PathVariable String team) {
        System.out.println("release status for team: " + team);
        return ResponseEntity.ok(null);
    }

    @GetMapping(path = "/sprint-status/{team}")
    public ResponseEntity<SprintStatusReport>  getSprintStatus(@PathVariable String team, @RequestParam String sprintNumber) {
        return ResponseEntity.ok(sprintStatusHelper.start(team, sprintNumber));
    }
}
