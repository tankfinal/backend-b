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

---

## OpenSpec 開發工作流

本專案使用 [OpenSpec](https://github.com/openspec) 管理功能規格與開發流程。所有 feature 的提案、設計、任務拆解都以文件形式存放在 `openspec/` 目錄。

### 目錄結構

```
openspec/
├── specs/               # 各 capability 的主規格（長期維護）
│   ├── jira-integration/
│   ├── workload-dashboard/
│   └── dashboard-dark-theme/
└── changes/             # 進行中的變更
    └── archive/         # 已完成並封存的變更
```

### 指令（Claude Code）

| 指令 | 用途 |
|------|------|
| `/opsx:propose` | 提出新功能，自動產生 proposal、design、tasks |
| `/opsx:explore` | 探索問題或需求，不立即進入實作 |
| `/opsx:apply` | 依照 tasks.md 逐步實作，完成後打勾 |
| `/opsx:archive` | 實作完成後封存變更，並同步到 main specs |

### 標準開發流程

```
PM 填需求單  →  /opsx:propose  →  /opsx:apply  →  /opsx:archive
     ↑                                                    ↓
docs/pm-requirement-template.md            封存並同步到 openspec/specs/
```

1. **PM 填需求單**：依照 [`docs/pm-requirement-template.md`](docs/pm-requirement-template.md) 填寫 Why、AC、Out of Scope 等欄位
2. **Propose**：將需求單內容貼給 Claude，執行 `/opsx:propose`，自動產生設計文件與任務清單
3. **Apply**：Claude 依照任務清單逐一實作程式碼，完成一個打勾一個
4. **Archive**：完成後封存變更，將 delta spec 同步回 `openspec/specs/`，留下可追溯的歷史紀錄

### 查看現有 Feature

所有已實作的 capability 規格在 `openspec/specs/` 下，每個子資料夾一個 feature：

```bash
ls openspec/specs/
```

### 查看歷史變更

```bash
ls openspec/changes/archive/
```

封存目錄以 `YYYY-MM-DD-<change-name>` 命名，可直接看到 feature 的開發先後順序。
