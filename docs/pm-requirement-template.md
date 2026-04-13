# PM 需求單模板

> 填寫完畢後，將此文件內容貼給工程師，執行 `/opsx:propose <feature-name>` 開始設計。

---

## User Story

> 一句話描述這個功能的使用者視角。
> 格式：`身為 <角色>，我希望 <做某件事>，以便 <達到某個目標>`

**範例：**
身為 BE Manager，我希望能一鍵匯出工作負載表格為 CSV，以便每週製作報告時不需要手動截圖。

---

## Why（背景與動機）

> 說明為什麼要做這個需求：業務背景、使用者痛點、或觸發這個需求的事件。

**範例：**
目前 Manager 每週需要將 Dashboard 表格手動截圖或逐筆複製到 Google Sheet，過程耗時約 30 分鐘，且容易出錯。隨著成員增加，這個問題會更加明顯。匯出 CSV 功能可以直接消除這個手動步驟。

---

## Acceptance Criteria（驗收條件）

> 每條 AC 必須是可被驗證的條件（目視確認或自動測試皆可）。
> 格式：`GIVEN <前提狀態> WHEN <使用者動作> THEN <系統預期反應>`
> **至少填 3 條，不得模糊帶過（如「功能正常運作」不算有效 AC）**

**範例：**

- [ ] AC1：GIVEN Dashboard 已載入成員工作負載資料 WHEN Manager 點擊「匯出 CSV」按鈕 THEN 瀏覽器自動下載一個 `.csv` 檔案，內含所有成員的姓名、總數、In Progress、To Do、Done 欄位
- [ ] AC2：GIVEN 目前顯示的資料有 6 位成員 WHEN 下載 CSV 後開啟 THEN 檔案包含 1 列 header + 6 列資料，欄位順序與畫面一致
- [ ] AC3：GIVEN 後端資料尚未載入完成 WHEN 點擊匯出按鈕 THEN 按鈕為 disabled 狀態，不觸發下載
- [ ] AC4：GIVEN 匯出成功 THEN 檔案命名格式為 `workload-YYYY-MM-DD.csv`，使用當天日期

---

## Out of Scope（明確不做）

> 列出容易被誤解為此需求範圍但這次不做的項目，避免工程師過度實作。

**範例：**
- 不包含：匯出 Excel（.xlsx）格式
- 不包含：排程自動寄送 CSV 到 Email
- 不包含：依成員或日期篩選後再匯出
- 不包含：後端新增匯出 API endpoint（前端直接從現有資料產生）

---

## Impact（影響範圍）

> 勾選此需求預計影響的層面，協助工程師評估工作量。

- [x] Frontend（新增匯出按鈕與 CSV 產生邏輯）
- [ ] Backend API（新增或修改 endpoint）
- [ ] Database（schema 變更或新增資料）
- [ ] Infra / DevOps（Docker、環境變數、部署設定）

---

## Jira Tickets

| 欄位 | 填寫 |
|------|------|
| 主要 Ticket | [PROJ-XXX](https://xrex.atlassian.net/browse/PROJ-XXX) |
| 相關 Ticket | [PROJ-YYY](https://xrex.atlassian.net/browse/PROJ-YYY)（選填） |
| 優先級 | P0 / P1 / P2 |
| 期望上線日 | YYYY-MM-DD |

---

## 補充說明（選填）

> 任何有助於工程師理解背景的資訊：設計稿連結、競品參考、相關 Slack 討論、截圖等。

**範例：**
- 設計稿：Figma 連結
- 參考：Google Sheet 目前手動流程截圖（附件）
- Slack 討論：#be-team 2026-04-10 的討論串
