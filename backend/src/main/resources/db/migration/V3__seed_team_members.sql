-- Team B members - replace PLACEHOLDER_* with real Jira Account IDs
-- Find account IDs via: GET /rest/api/3/user/search?query=<email>
INSERT INTO team_members (name, jira_account_id) VALUES
    ('Jerry Lin',    '712020:89df3f9b-de2c-4825-a7a9-383c52d88f25'),
    ('Sunny Huang',  '6241551df3824d006a5688aa'),
    ('James Peng',   '61bb0455817d530069ac7c81'),
    ('Harry Liao',   '712020:d2edfa36-d0c3-4c55-9a23-79951d4ae41c'),
    ('Ted Yen',      '712020:5ba2c17b-7339-47fa-a29e-02877125f993'),
    ('John Lin',     '5e718e562354a30c3ba2c528');
