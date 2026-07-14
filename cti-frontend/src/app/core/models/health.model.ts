export interface HealthStatus {
  status: string;
  timestamp: string;
  calls: number;
  agents: number;
  uptime: number;
}