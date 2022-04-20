package com.example.kms_test.controller;

import com.example.kms_test.service.KmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class KmsController {

    private final KmsService kmsService;

    @GetMapping("/encrypt")
    public String encrypt(@RequestParam String plainText) {
        return kmsService.encrypt(plainText);
    }

    @GetMapping("/decrypt")
    public String decrypt(@RequestParam String encryptedText) {
        return kmsService.decrypt(encryptedText);
    }
}
