package com.team.dashboard.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.dashboard.dto.MemberWorkloadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class JiraService {

    private final RestTemplate jiraRestTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${jira.base-url}")
    private String jiraBaseUrl;

    public MemberWorkloadDto.ByStatus getMemberTicketCounts(String jiraAccountId) {
        String jql = String.format(
                "assignee = \"%s\" AND statusCategory in (\"In Progress\", \"To Do\") ORDER BY updated DESC",
                jiraAccountId
        );

        String url = UriComponentsBuilder
                .fromHttpUrl(jiraBaseUrl + "/rest/api/3/search")
                .queryParam("jql", jql)
                .queryParam("maxResults", 200)
                .queryParam("fields", "status")
                .toUriString();

        try {
            ResponseEntity<String> response = jiraRestTemplate.getForEntity(url, String.class);
            return parseTicketCounts(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("Jira API error for account {}: {} - {}", jiraAccountId, e.getStatusCode(), e.getResponseBodyAsString());
            throw new JiraApiException("Jira API returned " + e.getStatusCode() + " for account " + jiraAccountId);
        } catch (Exception e) {
            log.error("Failed to call Jira API for account {}: {}", jiraAccountId, e.getMessage());
            throw new JiraApiException("Failed to fetch Jira data for account " + jiraAccountId);
        }
    }

    private MemberWorkloadDto.ByStatus parseTicketCounts(String responseBody) {
        int inProgress = 0;
        int toDo = 0;
        int done = 0;

        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode issues = root.path("issues");

            for (JsonNode issue : issues) {
                String statusCategory = issue
                        .path("fields")
                        .path("status")
                        .path("statusCategory")
                        .path("name")
                        .asText("");

                switch (statusCategory) {
                    case "In Progress" -> inProgress++;
                    case "To Do" -> toDo++;
                    case "Done" -> done++;
                    default -> log.debug("Unknown status category: {}", statusCategory);
                }
            }
        } catch (Exception e) {
            log.error("Failed to parse Jira response: {}", e.getMessage());
        }

        return new MemberWorkloadDto.ByStatus(inProgress, toDo, done);
    }

    public static class JiraApiException extends RuntimeException {
        public JiraApiException(String message) {
            super(message);
        }
    }
}
