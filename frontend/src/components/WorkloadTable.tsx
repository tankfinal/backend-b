import React from 'react'
import { MemberWorkload } from '../api/workload'

interface Props {
  members: MemberWorkload[]
}

export default function WorkloadTable({ members }: Props) {
  return (
    <>
      <style>{`
        .workload-row:hover { background-color: #2f2f2f; }
      `}</style>
    <table style={styles.table}>
      <thead>
        <tr style={styles.headerRow}>
          <th style={styles.th}>成員</th>
          <th style={styles.th}>總數</th>
          <th style={{ ...styles.th, color: '#2563eb' }}>In Progress</th>
          <th style={{ ...styles.th, color: '#d97706' }}>To Do</th>
          <th style={{ ...styles.th, color: '#16a34a' }}>Done</th>
        </tr>
      </thead>
      <tbody>
        {members.map((m) => (
          <tr key={m.memberId} className="workload-row" style={styles.row}>
            <td style={styles.td}>{m.name}</td>
            <td style={{ ...styles.td, fontWeight: 700 }}>{m.total}</td>
            <td style={{ ...styles.td, color: '#2563eb' }}>{m.byStatus.inProgress}</td>
            <td style={{ ...styles.td, color: '#d97706' }}>{m.byStatus.toDo}</td>
            <td style={{ ...styles.td, color: '#16a34a' }}>{m.byStatus.done}</td>
          </tr>
        ))}
      </tbody>
    </table>
    </>
  )
}

const styles: Record<string, React.CSSProperties> = {
  table: {
    width: '100%',
    borderCollapse: 'collapse',
    fontSize: '14px',
    backgroundColor: '#1a1a1a',
  },
  headerRow: {
    backgroundColor: '#2a2a2a',
  },
  th: {
    padding: '10px 16px',
    textAlign: 'left',
    borderBottom: '2px solid #333',
    fontWeight: 600,
    color: '#e2e8f0',
  },
  row: {
    borderBottom: '1px solid #333',
  },
  td: {
    padding: '10px 16px',
    color: '#e2e8f0',
  },
}
