package com.iadv.cti.avaya.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Call {
    private String callId;
    private String extension;
    private String agentId;
    private String phoneNumber;
    private String status; // RECEIVED, ANSWERED, HOLD, RESUME, TRANSFER, ENDED
    private LocalDateTime timestamp;
    private LocalDateTime lastUpdate;
}
