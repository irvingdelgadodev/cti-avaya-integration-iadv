package com.iadv.cti.avaya.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent {
    private String agentId;
    private String status; // AVAILABLE, BUSY, OFF_HOOK
    private String currentCallId;
    private LocalDateTime lastUpdate;
}
