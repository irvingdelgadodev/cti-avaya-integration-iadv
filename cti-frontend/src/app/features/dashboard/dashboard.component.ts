import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { interval, Subscription, switchMap, catchError, of, timer } from 'rxjs';

// Angular Material Imports
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatBadgeModule } from '@angular/material/badge';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatProgressBarModule } from '@angular/material/progress-bar';

import { CtiApiService } from '../../core/services/cti-api.service';
import { CtiStateService } from '../../core/services/cti-state.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    MatToolbarModule,
    MatCardModule,
    MatTableModule,
    MatChipsModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatBadgeModule,
    MatButtonModule,
    MatDividerModule,
    MatGridListModule,
    MatProgressBarModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {
  private apiService = inject(CtiApiService);
  public stateService = inject(CtiStateService);

  // Exponer signals al template
  calls = this.stateService.calls;
  agents = this.stateService.agents;
  loading = this.stateService.loading;
  error = this.stateService.error;
  connected = this.stateService.connected;
  activeCallsCount = this.stateService.activeCallsCount;
  availableAgents = this.stateService.availableAgents;
  busyAgents = this.stateService.busyAgents;

  // Columnas para la tabla de llamadas
  displayedColumns: string[] = ['callId', 'extension', 'agentId', 'phoneNumber', 'status'];

  private subscriptions: Subscription[] = [];
  private pollingInterval = 2000;

  ngOnInit() {
    console.log('🚀 Iniciando Dashboard CTI con Material...');
    this.loadInitialData();
    this.startPolling();
    this.checkHealth();
  }

  private loadInitialData() {
    this.stateService.setLoading(true);

    this.apiService.getActiveCalls().subscribe({
      next: (calls) => {
        this.stateService.updateCalls(calls);
        console.log(`📞 Cargadas ${calls.length} llamadas activas`);
      },
      error: (err) => {
        this.stateService.setError('Error cargando llamadas: ' + err.message);
      }
    });

    this.apiService.getAgents().subscribe({
      next: (agents) => {
        this.stateService.updateAgents(agents);
        console.log(`👤 Cargados ${agents.length} agentes`);
      },
      error: (err) => {
        this.stateService.setError('Error cargando agentes: ' + err.message);
      }
    });

    this.stateService.setLoading(false);
  }

  private startPolling() {
    const callPoll = interval(this.pollingInterval).pipe(
      switchMap(() => {
        this.stateService.setLoading(true);
        return this.apiService.getActiveCalls().pipe(
          catchError(err => {
            this.stateService.setError('Error en polling: ' + err.message);
            this.stateService.setConnected(false);
            return of([]);
          })
        );
      })
    ).subscribe({
      next: (calls) => {
        this.stateService.updateCalls(calls);
        this.stateService.setConnected(true);
        this.stateService.setLoading(false);
      }
    });

    const agentPoll = interval(3000).pipe(
      switchMap(() => {
        return this.apiService.getAgents().pipe(
          catchError(err => {
            console.error('Error polling agentes:', err);
            return of([]);
          })
        );
      })
    ).subscribe({
      next: (agents) => {
        if (agents.length > 0 || this.agents().length > 0) {
          this.stateService.updateAgents(agents);
        }
      }
    });

    this.subscriptions.push(callPoll, agentPoll);
  }

  private checkHealth() {
    const healthCheck = timer(5000, 10000).pipe(
      switchMap(() => {
        return this.apiService.getHealth().pipe(
          catchError(() => {
            this.stateService.setConnected(false);
            return of(null);
          })
        );
      })
    ).subscribe({
      next: (health) => {
        if (health) {
          this.stateService.setConnected(true);
          this.stateService.clearError();
        }
      }
    });

    this.subscriptions.push(healthCheck);
  }

  // ============================================
  // MÉTODOS PARA COLORES DE ESTADOS
  // ============================================

  // Paleta de colores oscuros para mejor contraste
  getStatusBadgeColor(status: string): string {
    const colorMap: Record<string, string> = {
      'RECEIVED': '#e65100',     // Naranja oscuro
      'ANSWERED': '#2e7d32',     // Verde oscuro
      'HOLD': '#bf360c',         // Naranja rojizo oscuro
      'RESUME': '#0d47a1',       // Azul oscuro
      'TRANSFER': '#4a148c',     // Morado oscuro
      'ENDED': '#b71c1c',        // Rojo oscuro
      'AVAILABLE': '#1b5e20',    // Verde oscuro
      'BUSY': '#b71c1c',         // Rojo oscuro
      'OFF_HOOK': '#e65100'      // Naranja oscuro
    };
    return colorMap[status] || '#616161';
  }

  // Texto SIEMPRE en blanco para mejor contraste
  getStatusTextColor(status: string): string {
    return '#ffffff';
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
    console.log('🧹 Dashboard limpiado');
  }
}