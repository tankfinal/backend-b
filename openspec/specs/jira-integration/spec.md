## ADDED Requirements

### Requirement: 查詢成員 assigned tickets

系統 SHALL 透過 Jira REST API 查詢設定檔中每位成員目前 assigned 的 tickets，並回傳結構化資料。

#### Scenario: 成功取得成員 tickets

- **WHEN** API 收到查詢特定成員工作負載的請求
- **THEN** 系統回傳該成員的 assigned tickets 列表，包含 ticket key、summary、status、priority、sprint 名稱

#### Scenario: 成員無 assigned tickets

- **WHEN** 查詢某成員的 tickets，該成員目前無 assigned tickets
- **THEN** 系統回傳空列表，HTTP 200

#### Scenario: Jira API 回應錯誤

- **WHEN** Jira API 回應非 2xx 狀態（如 401 未授權、429 rate limit）
- **THEN** 系統回傳錯誤訊息，並記錄錯誤 log，HTTP 502

---

### Requirement: 查詢全體成員工作負載彙總

系統 SHALL 提供一個 API endpoint，一次回傳 team.yaml 中所有成員的 ticket 數量彙總。

#### Scenario: 成功取得所有成員彙總

- **WHEN** API 收到查詢全體成員工作負載的請求
- **THEN** 系統回傳每位成員的姓名、assigned ticket 總數、依 status 分類的數量

---

### Requirement: 快取 Jira 查詢結果

系統 SHALL 將 Jira API 查詢結果快取在記憶體中，TTL 為 5 分鐘，避免頻繁呼叫 Jira API。

#### Scenario: 快取命中

- **WHEN** 同一查詢在 5 分鐘內再次發生
- **THEN** 系統從記憶體快取回傳資料，不呼叫 Jira API

#### Scenario: 快取過期

- **WHEN** 查詢距上次快取超過 5 分鐘
- **THEN** 系統重新呼叫 Jira API 更新快取，並回傳最新資料

---

### Requirement: 以設定檔管理成員 Jira Account ID

系統 SHALL 從 `config/team.yaml` 讀取成員名稱與對應的 Jira Account ID，不得硬編碼在程式碼中。

#### Scenario: 設定檔存在且格式正確

- **WHEN** 服務啟動時
- **THEN** 系統成功載入成員設定，所有成員資訊可用

#### Scenario: 設定檔缺少或格式錯誤

- **WHEN** 服務啟動，設定檔不存在或 YAML 格式錯誤
- **THEN** 服務啟動失敗，並顯示明確的錯誤訊息說明原因
