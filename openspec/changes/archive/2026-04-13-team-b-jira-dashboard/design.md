## Context

Backend Team B 目前由 6 位成員組成（Jerry Lin, Sunny Huang, James Peng, Harry Liao, Ted Yen, John Lin），Manager 需要快速了解各成員的 Jira 工作負載。本系統為新建服務，無現有程式碼需遷移。

Tech stack：Java Spring Boot（後端）、React（前端）、MySQL（資料儲存）。

## Goals / Non-Goals

**Goals:**
- 提供 Spring Boot REST API，整合 Jira REST API 查詢指定成員的 assigned tickets
- 將成員資訊與快取的工作負載資料持久化至 MySQL
- 提供 React 前端 Dashboard，視覺化呈現各成員 ticket 數量、狀態與 sprint 分佈
- 支援透過 MySQL 管理團隊成員名單

**Non-Goals:**
- 不提供 ticket 的建立、更新、刪除功能（唯讀）
- 不處理多個 team 的管理（僅限 Team B）
- 不提供即時推播通知
- 不建置使用者認證系統（假設內部使用，無需登入）

## Decisions

### D1: 使用 Spring Boot 3 作為後端框架

**選擇**: Spring Boot 3 + Java 17

**Alternatives**:
- Quarkus：較新，生態系統較小
- Micronaut：輕量但與 Spring 生態相容性差

**Rationale**: Spring Boot 生態成熟，JPA/Hibernate 整合 MySQL 開箱即用，RestTemplate/WebClient 可直接呼叫 Jira API，team 熟悉度高。

---

### D2: 前端使用 React SPA

**選擇**: React 18 + Vite + Recharts

**Alternatives**:
- Next.js：SSR 過度複雜，此 Dashboard 為內部工具
- Vue.js：功能相近，但 React 更普及

**Rationale**: React SPA 部署彈性高，Recharts 提供豐富圖表元件，Vite 開發體驗佳。前後端分離，API 呼叫 Spring Boot 後端。

---

### D3: 使用 MySQL 儲存成員資訊與快取資料

**選擇**: MySQL 8 + Spring Data JPA

**Alternatives**:
- 純記憶體快取：服務重啟資料消失，且跨實例無法共享
- Redis：需額外維護另一個服務

**Rationale**: MySQL 兼作成員設定儲存（`team_members` table）與工作負載快取（`workload_cache` table），一個資料庫解決兩個需求，架構單純。

---

### D4: Jira 資料快取策略（DB-based TTL）

**選擇**: 快取資料寫入 MySQL `workload_cache`，TTL 5 分鐘

**Alternatives**:
- 每次 real-time 查詢：Jira API 有 rate limit
- 純記憶體快取：無持久化，重啟遺失

**Rationale**: 以 `cached_at` timestamp 判斷 TTL，避免頻打 Jira API，且資料在服務重啟後仍保留。

---

### D5: 架構圖

```
React SPA (前端)
      ↓ REST API
Spring Boot (後端)
      ↓                    ↓
 Jira REST API         MySQL 8
                     ┌──────────────────┐
                     │ team_members     │  成員名單 & Jira Account ID
                     │ workload_cache   │  快取資料 + cached_at
                     └──────────────────┘
```

**服務結構:**

```
backend-b/
├── backend/                          # Spring Boot
│   ├── src/main/java/com/team/dashboard/
│   │   ├── DashboardApplication.java
│   │   ├── controller/
│   │   │   └── WorkloadController.java
│   │   ├── service/
│   │   │   ├── JiraService.java      # Jira API 整合
│   │   │   └── WorkloadService.java  # 快取邏輯
│   │   ├── repository/
│   │   │   ├── TeamMemberRepository.java
│   │   │   └── WorkloadCacheRepository.java
│   │   ├── entity/
│   │   │   ├── TeamMember.java
│   │   │   └── WorkloadCache.java
│   │   └── config/
│   │       └── AppConfig.java        # RestTemplate Bean, CORS
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── db/migration/             # Flyway migration SQL
│   └── pom.xml
│
├── frontend/                         # React
│   ├── src/
│   │   ├── App.tsx
│   │   ├── components/
│   │   │   ├── WorkloadTable.tsx
│   │   │   └── WorkloadChart.tsx
│   │   └── api/
│   │       └── workload.ts           # API client
│   ├── package.json
│   └── vite.config.ts
│
└── docker-compose.yml                # MySQL + 後端 + 前端
```

**資料庫 Schema:**

```sql
-- 成員設定
CREATE TABLE team_members (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  jira_account_id VARCHAR(100) NOT NULL UNIQUE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 工作負載快取
CREATE TABLE workload_cache (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  member_id BIGINT NOT NULL REFERENCES team_members(id),
  total_tickets INT NOT NULL DEFAULT 0,
  in_progress INT NOT NULL DEFAULT 0,
  to_do INT NOT NULL DEFAULT 0,
  done INT NOT NULL DEFAULT 0,
  cached_at DATETIME NOT NULL,
  raw_data JSON
);
```

## Risks / Trade-offs

- **Jira API Token 安全性** → 透過環境變數傳入（application.yml 讀 `${JIRA_API_TOKEN}`），不得 commit 至 repo
- **Jira Rate Limit** → DB 快取 TTL 5 分鐘緩解，若超限則顯示舊快取資料並標示時間
- **CORS 設定** → Spring Boot 需設定 CORS 允許 React 開發伺服器 (localhost:5173)
- **Jira Account ID 對應** → 初始資料需手動 insert 至 `team_members`，提供 seed SQL
- **MySQL 依賴** → 開發環境提供 docker-compose.yml 快速啟動 MySQL
