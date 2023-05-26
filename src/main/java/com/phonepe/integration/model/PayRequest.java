package com.phonepe.integration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayRequest {
    private String merchantId;
    private String merchantTransactionId;
    private String merchantUserId;
    private long amount;
    private String redirectUrl;
    private RedirectMode redirectMode;
    private String callbackUrl;
    private String mobileNumber;
    private PaymentInstrument paymentInstrument;

}
