## Why

Manager 需要快速掌握 Backend Team B 各成員目前的 Jira 工作負載，目前只能手動登入 Jira 逐一查看，缺乏整合視圖，效率低且無法即時比較各成員負載分佈。

## What Changes

- 建立 Java Spring Boot 後端服務，整合 Jira API，查詢 Team B 成員的 Jira ticket 資料，並儲存至 MySQL
- 提供 REST API 讓前端 Dashboard 查詢工作負載資料
- 建立 React 前端 Dashboard，以視覺化方式呈現每位成員的 ticket 數量、狀態分佈與優先級

## Capabilities

### New Capabilities

- `jira-integration`: 透過 Jira REST API 抓取指定成員的 assigned tickets，支援篩選 status、priority、sprint
- `workload-dashboard`: Web Dashboard UI，展示 Team B 各成員工作負載概覽，包含 ticket 數量、狀態分佈圖表

### Modified Capabilities

<!-- None -->

## Impact

- **新依賴**: Jira REST API (需要 API Token)、Java 17+、Spring Boot 3、MySQL 8、React 18、Node.js（前端 build）
- **新服務**: Spring Boot 後端 API server、React 前端 SPA、MySQL 資料庫
- **環境變數**: JIRA_BASE_URL, JIRA_API_TOKEN, JIRA_USER_EMAIL, DB_URL, DB_USERNAME, DB_PASSWORD
- **團隊成員設定**: Jerry Lin, Sunny Huang, James Peng, Harry Liao, Ted Yen, John Lin（需對應至 Jira account ID，儲存於 MySQL）
