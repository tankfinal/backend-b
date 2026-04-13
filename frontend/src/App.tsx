import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { fetchWorkload, refreshWorkload } from './api/workload'
import WorkloadTable from './components/WorkloadTable'
import WorkloadChart from './components/WorkloadChart'

export default function App() {
  const queryClient = useQueryClient()
  const [isRefreshing, setIsRefreshing] = useState(false)

  const { data, isLoading, isError } = useQuery({
    queryKey: ['workload'],
    queryFn: fetchWorkload,
  })

  const refreshMutation = useMutation({
    mutationFn: refreshWorkload,
    onMutate: () => setIsRefreshing(true),
    onSuccess: (freshData) => {
      queryClient.setQueryData(['workload'], freshData)
      setIsRefreshing(false)
    },
    onError: () => setIsRefreshing(false),
  })

  const formatDateTime = (iso: string) => {
    const d = new Date(iso)
    return d.toLocaleString('zh-TW', { hour12: false })
  }

  return (
    <div style={styles.page}>
      <header style={styles.header}>
        <div>
          <h1 style={styles.title}>Backend Team B Dashboard</h1>
          <p style={styles.subtitle}>Jira 工作負載總覽</p>
        </div>
        <button
          style={{ ...styles.button, opacity: isRefreshing ? 0.6 : 1 }}
          onMouseEnter={(e) => { (e.currentTarget as HTMLButtonElement).style.backgroundColor = '#2563eb' }}
          onMouseLeave={(e) => { (e.currentTarget as HTMLButtonElement).style.backgroundColor = '#3b82f6' }}
          onClick={() => refreshMutation.mutate()}
          disabled={isRefreshing}
        >
          {isRefreshing ? '更新中...' : '重新整理'}
        </button>
      </header>

      <main style={styles.main}>
        {isLoading && <p style={styles.hint}>載入中...</p>}
        {isError && <p style={{ ...styles.hint, color: '#dc2626' }}>無法取得資料，請確認後端服務是否正常。</p>}

        {data && (
          <>
            <section style={styles.card}>
              <h2 style={styles.sectionTitle}>工作負載圖表</h2>
              <WorkloadChart members={data.members} />
            </section>

            <section style={styles.card}>
              <h2 style={styles.sectionTitle}>成員明細</h2>
              <WorkloadTable members={data.members} />
            </section>

            <p style={styles.timestamp}>
              資料更新時間：{formatDateTime(data.cachedAt)}
            </p>
          </>
        )}
      </main>
    </div>
  )
}

const styles: Record<string, React.CSSProperties> = {
  page: {
    minHeight: '100vh',
    backgroundColor: '#0f0f0f',
    fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif',
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '20px 32px',
    backgroundColor: '#1e293b',
    color: '#fff',
  },
  title: {
    margin: 0,
    fontSize: '22px',
    fontWeight: 700,
  },
  subtitle: {
    margin: '4px 0 0',
    fontSize: '13px',
    color: '#94a3b8',
  },
  button: {
    padding: '8px 18px',
    backgroundColor: '#3b82f6',
    color: '#fff',
    border: 'none',
    borderRadius: '6px',
    cursor: 'pointer',
    fontSize: '14px',
    fontWeight: 600,
  },
  main: {
    maxWidth: '960px',
    margin: '0 auto',
    padding: '24px 16px',
  },
  card: {
    backgroundColor: '#1a1a1a',
    borderRadius: '8px',
    padding: '20px 24px',
    marginBottom: '20px',
    boxShadow: '0 1px 3px rgba(0,0,0,0.3)',
  },
  sectionTitle: {
    margin: '0 0 16px',
    fontSize: '16px',
    fontWeight: 600,
    color: '#e2e8f0',
  },
  hint: {
    textAlign: 'center',
    color: '#64748b',
    padding: '40px 0',
  },
  timestamp: {
    textAlign: 'center',
    color: '#94a3b8',
    fontSize: '12px',
    marginTop: '8px',
  },
}
