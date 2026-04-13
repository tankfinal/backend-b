import axios from 'axios'

export interface ByStatus {
  inProgress: number
  toDo: number
  done: number
}

export interface MemberWorkload {
  memberId: number
  name: string
  total: number
  byStatus: ByStatus
}

export interface WorkloadResponse {
  members: MemberWorkload[]
  cachedAt: string
}

export const fetchWorkload = async (): Promise<WorkloadResponse> => {
  const { data } = await axios.get<WorkloadResponse>('/api/workload')
  return data
}

export const refreshWorkload = async (): Promise<WorkloadResponse> => {
  const { data } = await axios.post<WorkloadResponse>('/api/workload/refresh')
  return data
}
