package com.team.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberWorkloadDto {
    private Long memberId;
    private String name;
    private int total;
    private ByStatus byStatus;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ByStatus {
        private int inProgress;
        private int toDo;
        private int done;
    }
}
