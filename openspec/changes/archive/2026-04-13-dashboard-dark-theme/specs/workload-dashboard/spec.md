## MODIFIED Requirements

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
