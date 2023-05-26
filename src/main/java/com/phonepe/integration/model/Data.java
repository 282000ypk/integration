package com.phonepe.integration.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private String merchantId;
    private String merchantTransactionId;
    private InstrumentResponse instrumentResponse;
}
