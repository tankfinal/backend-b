package com.team.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class WorkloadResponseDto {
    private List<MemberWorkloadDto> members;
    private LocalDateTime cachedAt;
}
