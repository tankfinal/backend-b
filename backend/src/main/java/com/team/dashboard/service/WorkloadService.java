package com.team.dashboard.service;

import com.team.dashboard.dto.MemberWorkloadDto;
import com.team.dashboard.dto.WorkloadResponseDto;
import com.team.dashboard.entity.TeamMember;
import com.team.dashboard.entity.WorkloadCache;
import com.team.dashboard.repository.TeamMemberRepository;
import com.team.dashboard.repository.WorkloadCacheRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkloadService {

    private final TeamMemberRepository teamMemberRepository;
    private final WorkloadCacheRepository workloadCacheRepository;
    private final JiraService jiraService;

    @Value("${workload.cache.ttl-minutes:5}")
    private int cacheTtlMinutes;

    public WorkloadResponseDto getAllMembersWorkload() {
        List<TeamMember> members = teamMemberRepository.findAll();
        List<MemberWorkloadDto> result = new ArrayList<>();
        LocalDateTime latestCachedAt = LocalDateTime.now();

        for (TeamMember member : members) {
            LocalDateTime ttlThreshold = LocalDateTime.now().minusMinutes(cacheTtlMinutes);

            Optional<WorkloadCache> cached = workloadCacheRepository
                    .findTopByMemberIdAndCachedAtAfterOrderByCachedAtDesc(member.getId(), ttlThreshold);

            if (cached.isPresent()) {
                log.debug("Cache hit for member: {}", member.getName());
                WorkloadCache cache = cached.get();
                result.add(toDto(member, cache));
                if (cache.getCachedAt().isBefore(latestCachedAt)) {
                    latestCachedAt = cache.getCachedAt();
                }
            } else {
                log.info("Cache miss, fetching from Jira for member: {}", member.getName());
                WorkloadCache freshCache = fetchAndCache(member);
                result.add(toDto(member, freshCache));
                latestCachedAt = freshCache.getCachedAt();
            }
        }

        return new WorkloadResponseDto(result, latestCachedAt);
    }

    @Transactional
    public WorkloadResponseDto forceRefresh() {
        log.info("Force refreshing all workload cache");
        workloadCacheRepository.deleteAllCaches();

        List<TeamMember> members = teamMemberRepository.findAll();
        List<MemberWorkloadDto> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (TeamMember member : members) {
            WorkloadCache freshCache = fetchAndCache(member);
            result.add(toDto(member, freshCache));
        }

        return new WorkloadResponseDto(result, now);
    }

    private WorkloadCache fetchAndCache(TeamMember member) {
        MemberWorkloadDto.ByStatus counts;
        try {
            counts = jiraService.getMemberTicketCounts(member.getJiraAccountId());
        } catch (JiraService.JiraApiException e) {
            log.error("Failed to fetch Jira data for {}: {}", member.getName(), e.getMessage());
            counts = new MemberWorkloadDto.ByStatus(0, 0, 0);
        }

        WorkloadCache cache = new WorkloadCache();
        cache.setMember(member);
        cache.setInProgress(counts.getInProgress());
        cache.setToDo(counts.getToDo());
        cache.setDoneCount(counts.getDone());
        cache.setTotalTickets(counts.getInProgress() + counts.getToDo() + counts.getDone());
        cache.setCachedAt(LocalDateTime.now());

        return workloadCacheRepository.save(cache);
    }

    private MemberWorkloadDto toDto(TeamMember member, WorkloadCache cache) {
        return new MemberWorkloadDto(
                member.getId(),
                member.getName(),
                cache.getTotalTickets(),
                new MemberWorkloadDto.ByStatus(
                        cache.getInProgress(),
                        cache.getToDo(),
                        cache.getDoneCount()
                )
        );
    }
}
