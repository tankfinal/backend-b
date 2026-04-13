# PM 需求單模板

> 填寫完畢後，將此文件內容貼給工程師，執行 `/opsx:propose <feature-name>` 開始設計。

---

## Feature Name

`<kebab-case 名稱，例如：export-workload-csv>`

---

## Why（背景與動機）

> 說明這個需求的業務背景、使用者痛點，或為什麼現在要做。

_範例：Manager 目前需要手動截圖表格再貼到 Google Sheet，每週浪費約 30 分鐘。_

---

## What（功能描述）

> 簡述這個功能做什麼，不需要說怎麼做。

_範例：在 Dashboard 加入「匯出 CSV」按鈕，點擊後下載當前工作負載表格資料。_

---

## Acceptance Criteria（驗收條件）

> 每條 AC 必須可以被驗證（測試或目視確認）。至少填 3 條。
> 格式建議：`GIVEN <前提> WHEN <動作> THEN <預期結果>`

- [ ] AC1：
- [ ] AC2：
- [ ] AC3：

_範例：_
_- GIVEN Dashboard 已載入資料 WHEN Manager 點擊「匯出 CSV」THEN 瀏覽器下載一個包含所有成員工作負載資料的 .csv 檔案_
_- GIVEN 資料為空（無成員）WHEN 點擊匯出 THEN 下載的 CSV 只包含 header 列，不報錯_

---

## Out of Scope（明確不做）

> 列出容易被誤解為此需求範圍內、但這次不做的項目。

- 不包含：
- 不包含：

---

## Impact（影響範圍）

> 勾選此需求預計影響的層面（可複選）。

- [ ] Frontend（UI / 樣式）
- [ ] Backend API（新增或修改 endpoint）
- [ ] Database（schema 變更或新增資料）
- [ ] Infra / DevOps（Docker、環境變數、部署）

---

## Priority & Deadline（優先級與期限）

| 欄位 | 填寫 |
|------|------|
| 優先級 | P0 / P1 / P2 |
| 期望上線日 | YYYY-MM-DD |
| Jira Ticket | [PROJ-XXX](https://xrex.atlassian.net/browse/PROJ-XXX) |

---

## 補充說明（選填）

> 任何有助於工程師理解背景的資訊：競品參考、設計稿連結、相關 Slack 討論等。
