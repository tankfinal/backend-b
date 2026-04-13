package com.team.dashboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "workload_cache")
@Getter
@Setter
@NoArgsConstructor
public class WorkloadCache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private TeamMember member;

    @Column(name = "total_tickets")
    private int totalTickets;

    @Column(name = "in_progress")
    private int inProgress;

    @Column(name = "to_do")
    private int toDo;

    @Column(name = "done_count")
    private int doneCount;

    @Column(name = "cached_at", nullable = false)
    private LocalDateTime cachedAt;

    @Column(name = "raw_data", columnDefinition = "JSON")
    private String rawData;
}
