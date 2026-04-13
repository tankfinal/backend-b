## 1. CSS 變數與全域樣式

- [x] 1.1 在 `frontend/src/index.css` 中定義深色主題 CSS 變數（`--color-bg`、`--color-surface`、`--color-text`、`--color-border` 等）
- [x] 1.2 將 `body` 背景色設定為 `--color-bg`（黑色系 `#0f0f0f`），文字顏色設定為 `--color-text`（淺色）
- [x] 1.3 確認 `App.css` 中無覆蓋全域背景色的設定，若有則更新為深色

## 2. WorkloadTable 深色化

- [x] 2.1 更新 `WorkloadTable.tsx` 表格容器背景為 `--color-surface`（深灰 `#1a1a1a`）
- [x] 2.2 更新表格 header（`<th>`）背景為深色（`#2a2a2a`），文字為淺色
- [x] 2.3 更新表格 row hover 樣式為深灰 highlight（`#2f2f2f`）
- [x] 2.4 更新表格邊框（`border`）顏色為低對比深色（`#333`）

## 3. WorkloadChart 深色化

- [x] 3.1 更新 `WorkloadChart.tsx` 圖表容器背景為深色
- [x] 3.2 設定 `CartesianGrid` 的 `stroke` 為深灰色（如 `#333`）
- [x] 3.3 設定 `XAxis` 和 `YAxis` 的 `stroke` 與 `tick.fill` 為淺灰色（如 `#aaa`）
- [x] 3.4 設定 `Tooltip` 的 `contentStyle` 為深色背景（`#1a1a1a`）、白色文字

## 4. App.tsx 按鈕與整體佈局

- [x] 4.1 更新「重新整理」按鈕樣式，使其在深色背景上有足夠對比度
- [x] 4.2 為按鈕加入 hover 樣式（CSS class 或 inline style）
- [x] 4.3 更新「資料更新時間」文字顏色為淺灰色，確保在深色背景上可讀

## 5. 驗收確認

- [x] 5.1 啟動前端開發伺服器，目視確認頁面背景為黑色系深色
- [x] 5.2 確認表格文字、邊框、header、row hover 在深色背景下清晰可讀
- [x] 5.3 確認長條圖軸線、標籤、Tooltip 均為深色主題相容樣式
- [x] 5.4 確認「重新整理」按鈕清晰可見且 hover 有視覺回饋
- [x] 5.5 確認「資料更新時間」文字在深色背景上可讀
