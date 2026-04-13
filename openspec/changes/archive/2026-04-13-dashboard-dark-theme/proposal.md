## Why

目前 Dashboard 採用預設的淺色背景，在長時間使用或低光環境下對眼睛造成負擔。導入深色主題可提升 Manager 使用舒適度，並與現代開發工具的視覺習慣一致。

## What Changes

- 將 Dashboard 前端整體背景色改為黑色（深色系）
- 調整文字顏色以確保在深色背景上的可讀性
- 更新表格（WorkloadTable）的背景、邊框、row hover 配色
- 更新長條圖（WorkloadChart）的背景與文字標籤顏色
- 調整「重新整理」按鈕樣式以符合深色主題
- 更新資料更新時間文字的顏色

## Capabilities

### New Capabilities
- `dashboard-dark-theme`: Dashboard 前端深色主題，涵蓋背景、文字、表格、圖表、按鈕的配色規範

### Modified Capabilities
- `workload-dashboard`: Dashboard 視覺呈現的需求調整，加入深色主題下的 UI 顯示規範

## Impact

- 影響範圍：`frontend/src/` 下的 CSS / 樣式相關檔案（`App.css`、`index.css` 或 inline styles）、`WorkloadTable.tsx`、`WorkloadChart.tsx`、`App.tsx`
- 無 API 變更，無後端影響
- 無 breaking changes
