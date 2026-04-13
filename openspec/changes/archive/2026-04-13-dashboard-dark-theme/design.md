## Context

Dashboard 前端目前使用 Vite + React + TypeScript 建構，樣式以 CSS 檔案（`index.css`、`App.css`）為主，部分元件使用 inline style 或 Recharts 內建屬性設定顏色。此次變更目標是將整體視覺切換為深色主題，範圍限於前端樣式調整，不涉及後端邏輯。

## Goals / Non-Goals

**Goals:**
- 將頁面背景、卡片、表格改為深色配色
- 確保文字在深色背景上有足夠對比度（符合基本可讀性）
- 更新 Recharts BarChart 圖表的背景色與文字標籤顏色
- 保持元件結構與互動邏輯不變

**Non-Goals:**
- 不實作明暗主題切換開關（toggling）
- 不引入新的 CSS framework 或 design system
- 不修改後端 API 或資料結構

## Decisions

### 決策 1：使用 CSS 變數統一管理配色

**選擇**：在 `index.css` 中定義 CSS custom properties（`--color-bg`, `--color-surface`, `--color-text`, `--color-border` 等），元件中引用變數而非寫死顏色值。

**理由**：相較於直接在各元件 inline style 中寫死顏色，CSS 變數集中管理更易維護，未來若需要支援主題切換也有明確的擴充路徑。

**替代方案**：直接 hardcode 顏色在各元件 → 短期快但維護困難，捨棄。

---

### 決策 2：Recharts 圖表使用 props 傳遞顏色

**選擇**：Recharts 元件（`CartesianGrid`、`XAxis`、`YAxis`、`Tooltip`、`Legend`）透過各自的 `stroke`、`fill`、`contentStyle` 等 props 設定深色顏色，不依賴全域 CSS。

**理由**：Recharts 渲染於 SVG，部分全域 CSS 無法直接影響其內部元素，需透過 props 明確指定。

## Risks / Trade-offs

- [Risk] 部分 inline style 可能遺漏未更新 → Mitigation：逐一審閱 `WorkloadTable.tsx`、`WorkloadChart.tsx`、`App.tsx` 中所有顏色相關 style
- [Risk] Recharts Tooltip 背景為白色預設 → Mitigation：透過 `contentStyle` prop 覆蓋背景色與文字色
- [Trade-off] 未支援主題切換，所有使用者一律深色體驗 → 此為本次範圍限制，可接受
