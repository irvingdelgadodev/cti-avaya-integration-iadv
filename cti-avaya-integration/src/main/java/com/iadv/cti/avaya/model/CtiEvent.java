package com.iadv.cti.avaya.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CtiEvent {
    private String eventType;
    private String callId;
    private String extension;
    private String agentId;
    private String phoneNumber;
    private String timestamp;
}
