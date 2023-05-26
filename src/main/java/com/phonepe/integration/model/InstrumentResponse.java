package com.phonepe.integration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentResponse {
    private String type;
    private RedirectInfo redirectInfo;
}
