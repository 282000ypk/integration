package com.phonepe.integration.model;

import lombok.*;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private boolean success;
    private String code;
    private String message;
    private Data data;
}
