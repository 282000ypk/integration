package com.phonepe.integration.controller;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.hash.Hashing;
import com.phonepe.integration.model.EncodedPayRequest;
import com.phonepe.integration.model.PayRequest;
import com.phonepe.integration.model.Response;
import io.netty.handler.codec.base64.Base64Encoder;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.view.RedirectView;

import javax.xml.crypto.dsig.DigestMethod;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/pay")
public class PayController {

    @PostMapping("encoded")
    public String encodedBody(@RequestBody PayRequest payRequest) throws JsonProcessingException {
        JsonMapper jsonMapper = new JsonMapper();
        String jsonRequest = jsonMapper.writer().writeValueAsString(payRequest);
        System.out.println(jsonRequest);

        return Base64Util.encode(jsonRequest);
    }

    @PostMapping("headerencode")
    public String HeaderEncode(@RequestParam String payLoad,
                               @RequestParam @DefaultValue("/pg/v1/pay") String url,
                               @RequestParam @DefaultValue("099eb0cd-02cf-4e2a-8aca-3e6c6aff0399") String saltKey,
                               @RequestParam @DefaultValue("1") int saltIndex) {
        return Hashing.sha256()
                .hashString(payLoad + url + saltKey + saltIndex, StandardCharsets.UTF_8)
                .toString();
    }


}
