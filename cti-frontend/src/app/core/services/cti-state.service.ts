import { Injectable, signal, computed, effect } from '@angular/core';
import { Call } from '../models/call.model';
import { Agent } from '../models/agent.model';

@Injectable({
  providedIn: 'root'
})
export class CtiStateService {
  // Signals para estado reactivo
  private callsSignal = signal<Call[]>([]);
  private agentsSignal = signal<Agent[]>([]);
  private loadingSignal = signal<boolean>(false);
  private errorSignal = signal<string | null>(null);
  private connectedSignal = signal<boolean>(true);

  // Valores computados (readonly para el resto de la app)
  readonly calls = this.callsSignal.asReadonly();
  readonly agents = this.agentsSignal.asReadonly();
  readonly loading = this.loadingSignal.asReadonly();
  readonly error = this.errorSignal.asReadonly();
  readonly connected = this.connectedSignal.asReadonly();

  // Métricas computadas
  readonly activeCallsCount = computed(() => this.callsSignal().length);
  readonly availableAgents = computed(() =>
    this.agentsSignal().filter(a => a.status === 'AVAILABLE').length
  );
  readonly busyAgents = computed(() =>
    this.agentsSignal().filter(a => a.status === 'BUSY').length
  );

  constructor() {
    effect(() => {
      console.log(`📊 Estado actualizado: ${this.callsSignal().length} llamadas, ${this.agentsSignal().length} agentes`);
    });
  }

  updateCalls(calls: Call[]) {
    this.callsSignal.set(calls);
    this.clearError();
  }

  updateAgents(agents: Agent[]) {
    this.agentsSignal.set(agents);
    this.clearError();
  }

  setLoading(loading: boolean) {
    this.loadingSignal.set(loading);
  }

  setConnected(connected: boolean) {
    this.connectedSignal.set(connected);
  }

  setError(error: string) {
    this.errorSignal.set(error);
    this.loadingSignal.set(false);
  }

  clearError() {
    this.errorSignal.set(null);
  }

  clearAll() {
    this.callsSignal.set([]);
    this.agentsSignal.set([]);
    this.errorSignal.set(null);
    this.loadingSignal.set(false);
  }
}