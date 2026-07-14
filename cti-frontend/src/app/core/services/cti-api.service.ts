import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Call } from '../models/call.model';
import { Agent } from '../models/agent.model';
import { HealthStatus } from '../models/health.model';

@Injectable({
  providedIn: 'root'
})
export class CtiApiService {
  private baseUrl = 'http://localhost:8080/api/cti';

  constructor(private http: HttpClient) {}

  getHealth(): Observable<HealthStatus> {
    return this.http.get<HealthStatus>(`${this.baseUrl}/health`)
      .pipe(
        retry(2),
        catchError(this.handleError)
      );
  }

  getActiveCalls(): Observable<Call[]> {
    return this.http.get<Call[]>(`${this.baseUrl}/calls/active`)
      .pipe(
        retry(2),
        catchError(this.handleError)
      );
  }

  getAgents(): Observable<Agent[]> {
    return this.http.get<Agent[]>(`${this.baseUrl}/agents`)
      .pipe(
        retry(2),
        catchError(this.handleError)
      );
  }

  getExtensions(): Observable<Record<string, string>> {
    return this.http.get<Record<string, string>>(`${this.baseUrl}/extensions`)
      .pipe(
        retry(2),
        catchError(this.handleError)
      );
  }

  holdCall(callId: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/calls/${callId}/hold`, {})
      .pipe(catchError(this.handleError));
  }

  resumeCall(callId: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/calls/${callId}/resume`, {})
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Error desconocido';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Código ${error.status}: ${error.message}`;
    }
    console.error('❌ API Error:', errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}