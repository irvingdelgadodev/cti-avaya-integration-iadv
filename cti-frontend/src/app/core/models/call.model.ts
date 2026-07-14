export interface Call {
  callId: string;
  extension: string;
  agentId: string;
  phoneNumber: string;
  status: 'RECEIVED' | 'ANSWERED' | 'HOLD' | 'RESUME' | 'TRANSFER' | 'ENDED';
  timestamp: string;
  lastUpdate: string;
}