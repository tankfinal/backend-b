## ADDED Requirements

### Requirement: 展示各成員工作負載概覽

系統 SHALL 提供 Web Dashboard 頁面，以表格形式展示 Team B 各成員目前的 assigned ticket 數量與 status 分佈。頁面 SHALL 以深色主題呈現，背景為黑色系，文字為淺色系。

#### Scenario: Dashboard 頁面正常載入

- **WHEN** Manager 開啟 Dashboard 首頁（`GET /`）
- **THEN** 頁面顯示所有成員（Jerry Lin, Sunny Huang, James Peng, Harry Liao, Ted Yen, John Lin）的工作負載表格，包含欄位：姓名、總 ticket 數、In Progress 數量、To Do 數量、Done 數量，且整體頁面以深色主題呈現

#### Scenario: 某成員無 tickets

- **WHEN** 某成員目前無 assigned tickets
- **THEN** 該成員列顯示 0，不顯示錯誤

---

### Requirement: 以圖表視覺化工作負載分佈

系統 SHALL 在 Dashboard 中使用長條圖（Bar Chart）呈現各成員 ticket 數量比較，以利 Manager 快速識別負載偏高的成員。圖表 SHALL 採用深色主題配色，軸線、標籤、Tooltip 均為深色相容樣式。

#### Scenario: 圖表正確渲染

- **WHEN** Dashboard 頁面載入完成
- **THEN** 頁面顯示長條圖，X 軸為成員姓名，Y 軸為 ticket 總數，各成員以不同顏色區分，圖表背景為深色

---

### Requirement: 顯示資料更新時間

系統 SHALL 在 Dashboard 頁面顯示資料的最後更新時間（快取時間），讓 Manager 知道資料新鮮度。

#### Scenario: 顯示快取時間

- **WHEN** Dashboard 頁面載入
- **THEN** 頁面底部顯示「資料更新時間: YYYY-MM-DD HH:MM:SS」

---

### Requirement: 手動刷新資料

系統 SHALL 提供「重新整理」按鈕，讓 Manager 可以強制清除快取並重新從 Jira 拉取最新資料。

#### Scenario: 點擊重新整理

- **WHEN** Manager 點擊「重新整理」按鈕
- **THEN** 系統清除對應快取，重新呼叫 Jira API，頁面顯示最新資料與新的更新時間

---

### Requirement: 提供 REST API endpoint

系統 SHALL 提供 `GET /api/workload` endpoint，回傳 JSON 格式的全體成員工作負載資料，供未來擴充使用。

#### Scenario: API 回傳正確格式

- **WHEN** 呼叫 `GET /api/workload`
- **THEN** 回傳 HTTP 200，JSON body 包含 `members` 陣列，每個元素有 `name`、`total`、`by_status` 欄位，以及 `cached_at` 時間戳
