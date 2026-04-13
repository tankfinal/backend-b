## 1. 專案初始化

- [x] 1.1 建立 `backend/` 目錄，用 Spring Initializr 建立 Spring Boot 3 專案（依賴：Spring Web, Spring Data JPA, MySQL Driver, Flyway, Lombok）
- [x] 1.2 建立 `frontend/` 目錄，用 Vite 建立 React + TypeScript 專案
- [x] 1.3 建立 `docker-compose.yml`，包含 MySQL 8 服務（設定 database, user, password）
- [x] 1.4 建立 `backend/src/main/resources/application.yml`，設定 datasource、Jira API 相關環境變數（`${JIRA_BASE_URL}`, `${JIRA_API_TOKEN}`, `${JIRA_USER_EMAIL}`）
- [x] 1.5 建立 `.env.example`，定義所有必要環境變數

## 2. 資料庫設計與初始化

- [x] 2.1 建立 Flyway migration `V1__create_team_members.sql`，建立 `team_members` table
- [x] 2.2 建立 Flyway migration `V2__create_workload_cache.sql`，建立 `workload_cache` table（含 JSON 欄位 `raw_data`）
- [x] 2.3 建立 Flyway migration `V3__seed_team_members.sql`，insert 6 位成員（Jerry Lin, Sunny Huang, James Peng, Harry Liao, Ted Yen, John Lin）及其 Jira Account ID（先用 placeholder）
- [x] 2.4 建立 `TeamMember` JPA Entity 與 `TeamMemberRepository`
- [x] 2.5 建立 `WorkloadCache` JPA Entity 與 `WorkloadCacheRepository`（含 `findByMemberIdAndCachedAtAfter` 查詢）

## 3. Jira API 整合（後端）

- [x] 3.1 建立 `AppConfig.java`，定義 `RestTemplate` Bean 並設定 Basic Auth header（Jira API Token）
- [x] 3.2 建立 `JiraService.java`，實作 `getMemberTickets(String jiraAccountId)` 透過 JQL 查詢成員 assigned tickets
- [x] 3.3 JQL 範例：`assignee = "<accountId>" AND statusCategory != Done ORDER BY updated DESC`
- [x] 3.4 解析 Jira API response，將 tickets 依 status 分類（In Progress / To Do / Done）

## 4. 工作負載服務與快取（後端）

- [x] 4.1 建立 `WorkloadService.java`，實作 `getAllMembersWorkload()`
- [x] 4.2 查詢前先檢查 `workload_cache` TTL（5 分鐘內有資料則直接回傳）
- [x] 4.3 快取過期時呼叫 `JiraService` 重新查詢，結果寫入 `workload_cache`
- [x] 4.4 實作 `forceRefresh()`，刪除所有快取紀錄並重新查詢所有成員

## 5. REST API Controller（後端）

- [x] 5.1 建立 `WorkloadController.java`，設定 `@CrossOrigin` 允許 React 開發伺服器
- [x] 5.2 實作 `GET /api/workload`，回傳所有成員工作負載 JSON（含 `cached_at`）
- [x] 5.3 實作 `POST /api/workload/refresh`，呼叫 `forceRefresh()` 並回傳最新資料
- [x] 5.4 確認 API response 格式：`{"members": [...], "cached_at": "..."}`

## 6. React 前端 Dashboard

- [x] 6.1 安裝前端依賴：`recharts`、`axios`、`@tanstack/react-query`
- [x] 6.2 建立 `src/api/workload.ts`，封裝 `GET /api/workload` 與 `POST /api/workload/refresh` 呼叫
- [x] 6.3 建立 `WorkloadTable.tsx`，以表格呈現各成員姓名、總數、In Progress、To Do、Done
- [x] 6.4 建立 `WorkloadChart.tsx`，使用 Recharts `BarChart` 呈現各成員 ticket 數量比較
- [x] 6.5 在頁面底部顯示「資料更新時間」（`cached_at`）
- [x] 6.6 加入「重新整理」按鈕，呼叫 refresh API 並重新 fetch 資料
- [x] 6.7 組裝 `App.tsx`，整合 Table + Chart + 重新整理按鈕
- [x] 6.8 設定 `vite.config.ts` proxy，將 `/api` 請求轉發到 `http://localhost:8080`（開發環境）

## 7. 測試與驗證

- [x] 7.1 啟動 docker-compose MySQL，確認 Spring Boot 服務可正常啟動並完成 Flyway migration
- [x] 7.2 更新 `V3__seed_team_members.sql` 中的真實 Jira Account ID，驗證 Jira API 查詢正確回傳成員 tickets
- [x] 7.3 呼叫 `GET /api/workload`，確認回傳格式正確且包含所有成員資料
- [x] 7.4 呼叫兩次 `GET /api/workload`（間隔 < 5 分鐘），確認第二次未重打 Jira API（`cached_at` 相同）
- [x] 7.5 呼叫 `POST /api/workload/refresh`，確認快取清除並取得最新資料
- [x] 7.6 啟動 React 前端，確認 Dashboard 頁面正確展示表格與圖表
- [x] 7.7 測試「重新整理」按鈕，確認 UI 正確更新資料與時間戳
