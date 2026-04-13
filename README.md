# Backend Team B Dashboard

Jira 工作負載總覽 Dashboard，供 BE Manager 快速掌握 Team B 成員的 ticket 分佈狀況。

## 功能

- 以表格展示各成員 In Progress / To Do / Done ticket 數量
- 以長條圖視覺化工作負載分佈
- 手動刷新 Jira 資料
- 深色主題 UI

## 技術棧

- **Frontend**: React + TypeScript + Vite + Recharts
- **Backend**: Spring Boot 3 + Java 17 + MySQL
- **Infrastructure**: Docker Compose

## 啟動方式

### 環境變數

在根目錄建立 `.env`：

```
JIRA_BASE_URL=https://your-org.atlassian.net
JIRA_API_TOKEN=your-api-token
JIRA_USER_EMAIL=your-email@example.com
```

### 啟動

```bash
docker compose up --build
```

- 前端：http://localhost:3000
- 後端 API：http://localhost:8080
