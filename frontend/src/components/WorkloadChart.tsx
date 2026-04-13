import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts'
import { MemberWorkload } from '../api/workload'

interface Props {
  members: MemberWorkload[]
}

export default function WorkloadChart({ members }: Props) {
  const data = members.map((m) => ({
    name: m.name.split(' ')[0], // 只顯示 first name，避免 X 軸擁擠
    'In Progress': m.byStatus.inProgress,
    'To Do': m.byStatus.toDo,
    Done: m.byStatus.done,
  }))

  return (
    <div style={{ backgroundColor: '#1a1a1a', borderRadius: '4px', padding: '8px 0' }}>
    <ResponsiveContainer width="100%" height={300}>
      <BarChart data={data} margin={{ top: 10, right: 20, left: 0, bottom: 5 }}>
        <CartesianGrid strokeDasharray="3 3" stroke="#333" />
        <XAxis dataKey="name" stroke="#aaa" tick={{ fontSize: 12, fill: '#aaa' }} />
        <YAxis allowDecimals={false} stroke="#aaa" tick={{ fontSize: 12, fill: '#aaa' }} />
        <Tooltip contentStyle={{ backgroundColor: '#1a1a1a', border: '1px solid #333', color: '#fff' }} />
        <Legend wrapperStyle={{ color: '#aaa' }} />
        <Bar dataKey="In Progress" fill="#2563eb" radius={[3, 3, 0, 0]} />
        <Bar dataKey="To Do" fill="#d97706" radius={[3, 3, 0, 0]} />
        <Bar dataKey="Done" fill="#16a34a" radius={[3, 3, 0, 0]} />
      </BarChart>
    </ResponsiveContainer>
    </div>
  )
}
