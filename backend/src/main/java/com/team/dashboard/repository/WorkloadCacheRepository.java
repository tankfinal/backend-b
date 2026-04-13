package com.team.dashboard.repository;

import com.team.dashboard.entity.WorkloadCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WorkloadCacheRepository extends JpaRepository<WorkloadCache, Long> {

    Optional<WorkloadCache> findTopByMemberIdAndCachedAtAfterOrderByCachedAtDesc(
            Long memberId, LocalDateTime after);

    @Modifying
    @Query("DELETE FROM WorkloadCache w")
    void deleteAllCaches();
}
