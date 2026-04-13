CREATE TABLE workload_cache (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    total_tickets INT NOT NULL DEFAULT 0,
    in_progress INT NOT NULL DEFAULT 0,
    to_do INT NOT NULL DEFAULT 0,
    done_count INT NOT NULL DEFAULT 0,
    cached_at DATETIME NOT NULL,
    raw_data JSON,
    CONSTRAINT fk_workload_member FOREIGN KEY (member_id) REFERENCES team_members(id) ON DELETE CASCADE
);

CREATE INDEX idx_workload_member_cached ON workload_cache (member_id, cached_at);
