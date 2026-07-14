export interface Agent {
  agentId: string;
  status: 'AVAILABLE' | 'BUSY' | 'OFF_HOOK';
  currentCallId: string | null;
  lastUpdate: string;
}