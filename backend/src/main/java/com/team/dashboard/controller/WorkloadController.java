package com.team.dashboard.controller;

import com.team.dashboard.dto.WorkloadResponseDto;
import com.team.dashboard.service.WorkloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workload")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@RequiredArgsConstructor
public class WorkloadController {

    private final WorkloadService workloadService;

    @GetMapping
    public ResponseEntity<WorkloadResponseDto> getWorkload() {
        WorkloadResponseDto response = workloadService.getAllMembersWorkload();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<WorkloadResponseDto> refresh() {
        WorkloadResponseDto response = workloadService.forceRefresh();
        return ResponseEntity.ok(response);
    }
}
