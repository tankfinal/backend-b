## ADDED Requirements

### Requirement: 頁面使用深色背景配色

系統 SHALL 將 Dashboard 頁面的整體背景色設定為深色（`#0f0f0f` 或相近黑色系），所有文字顏色 SHALL 確保與背景有足夠對比度（亮色文字）。

#### Scenario: 頁面背景為深色

- **WHEN** Manager 開啟 Dashboard 頁面
- **THEN** 頁面背景顯示為黑色系深色，主要文字顯示為淺色（白色或接近白色）

#### Scenario: 二級背景區塊（卡片、表格）為深灰色

- **WHEN** 頁面載入完成
- **THEN** 表格與圖表容器背景顯示為深灰色（`#1a1a1a` 或相近），與頁面主背景形成輕微層次區別

---

### Requirement: 表格採用深色配色

系統 SHALL 將 WorkloadTable 的背景、邊框、header、row hover 顏色全部調整為深色主題相容配色。

#### Scenario: 表格 header 為深色

- **WHEN** Dashboard 頁面載入
- **THEN** 表格 header 列背景顯示為深色（如 `#2a2a2a`），文字顯示為淺色

#### Scenario: 表格 row hover 有深色 highlight

- **WHEN** Manager 將滑鼠移至某一列
- **THEN** 該列背景變為較淺的深灰色（如 `#2f2f2f`），文字維持可讀

#### Scenario: 表格邊框為低對比深色線

- **WHEN** Dashboard 頁面載入
- **THEN** 表格格線顯示為深灰色（如 `#333`），不使用預設黑色或白色邊框

---

### Requirement: 圖表採用深色配色

系統 SHALL 將 WorkloadChart（Recharts BarChart）的背景、軸線、標籤、Tooltip 調整為深色主題相容配色。

#### Scenario: 圖表背景為深色

- **WHEN** Dashboard 頁面載入
- **THEN** 圖表容器背景顯示為深色

#### Scenario: 圖表軸線與標籤為淺色

- **WHEN** Dashboard 頁面載入
- **THEN** XAxis、YAxis 的軸線與刻度標籤顯示為淺灰色（如 `#aaa`）

#### Scenario: Tooltip 為深色背景

- **WHEN** Manager 將滑鼠 hover 在長條圖的某一條柱上
- **THEN** Tooltip 背景顯示為深灰色（如 `#1a1a1a`），文字顯示為白色

---

### Requirement: 按鈕與互動元素符合深色主題

系統 SHALL 將「重新整理」按鈕的樣式調整為深色主題相容配色。

#### Scenario: 按鈕在深色背景上清晰可見

- **WHEN** Dashboard 頁面載入
- **THEN** 「重新整理」按鈕顯示有足夠對比度的前景色與背景色，不與頁面背景融合

#### Scenario: 按鈕 hover 狀態有視覺回饋

- **WHEN** Manager 將滑鼠移至「重新整理」按鈕
- **THEN** 按鈕顯示深色主題相容的 hover 樣式（如邊框或背景色輕微變化）
