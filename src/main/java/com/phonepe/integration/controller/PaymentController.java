package com.phonepe.integration.controller;

import com.google.common.hash.Hashing;
import com.phonepe.integration.model.EncodedPayRequest;
import com.phonepe.integration.model.Response;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.view.RedirectView;

import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("phonepe")
public class PaymentController {
    private String payUrl = "https://api-preprod.phonepe.com/apis/merchant-simulator/pg/v1/pay";

    WebClient webClient = WebClient.builder().build();

    @RequestMapping("/pay")
    public String handleFoo() {
        String request = "{\n" +
                "  \"merchantId\": \"PGTESTPAYUAT\",\n" +
                "  \"merchantTransactionId\": \"MT78505900681881049999\",\n" +
                "  \"merchantUserId\": \"MUID1239999\",\n" +
                "  \"amount\": 10000,\n" +
                "  \"redirectUrl\": \"http://localhost:8080\",\n" +
                "  \"redirectMode\": \"GET\",\n" +
                "  \"callbackUrl\": \"http://localhost:8080/phonepe/callback\",\n" +
                "  \"mobileNumber\": \"9999999999\",\n" +
                "  \"paymentInstrument\": {\n" +
                "    \"type\": \"PAY_PAGE\"\n" +
                "   }\n" +
                "}";
        request = Base64Util.encode(request);
        EncodedPayRequest encodedPayRequest = new EncodedPayRequest();
        encodedPayRequest.setRequest(request);

        String xverify = Hashing.sha256()
                .hashString(encodedPayRequest.getRequest() + "/pg/v1/pay" + "099eb0cd-02cf-4e2a-8aca-3e6c6aff0399", StandardCharsets.UTF_8)
                .toString().concat("###1");

        Response response = webClient.post()
                .uri(payUrl)
                .body(BodyInserters.fromValue(encodedPayRequest))
                .header("X-VERIFY", xverify)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        return "redirect:" + response.getData().getInstrumentResponse().getRedirectInfo().getUrl();
    }

    @RequestMapping("callback")
    public void print() {
        System.out.println("callback called");
    }
}
