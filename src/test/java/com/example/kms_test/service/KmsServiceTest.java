package com.example.kms_test.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;
import com.amazonaws.services.kms.model.EncryptionAlgorithmSpec;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class KmsServiceTest {

    private static final String KEY_ID = "KEY_ID";

    @Test
    void encrypt() {
        final String plaintext = "Hello, KMS";

        try {
            AWSKMS kmsClient = AWSKMSClientBuilder.standard()
                    .withRegion(Regions.AP_NORTHEAST_2)
                    .build();

            EncryptRequest request = new EncryptRequest();
            request.withKeyId(KEY_ID);
            request.withPlaintext(ByteBuffer.wrap(plaintext.getBytes(StandardCharsets.UTF_8)));
            request.withEncryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256);

            EncryptResult result = kmsClient.encrypt(request);
            ByteBuffer ciphertextBlob = result.getCiphertextBlob();

            System.out.println("ciphertextBlob: " + new String(Base64.encodeBase64(ciphertextBlob.array())));
        } catch (Exception e) {
            System.out.println("encrypt fail: " + e.getMessage());
        }
    }

    @Test
    void decrypt() {
        final String encriptedText = "XkJcKtmn60fTliK8xpJ3m6qyhq/FJ6iHWPNADAw+ScL4r8/Yb4DR/GEGabyZfp+jD5PLuNg5qwfNH4W3TcOMoN9eu/Vj2FEYrsbCSrmOL0aCo+Iu3xpKJDAqHWrZCtbytKy+B8iRryeIDhccUiYSd6bveuG+zWSVxLMzCbV/+7K3AlQHZ0UfcOc4rvkwck+A59df76EZG2iRo+A9a0cTyDt4uw9eyNc5EOhPvUm1LLYVTSHv2NZO0JNyaopkEd4WKiKoJxk6hkJ+SV45A/M521U0C3hkCt36zrTyKg8P3SkQS7eLVXdQEICHTsxZxd8ppVnzW2XwoAXnlX1XnGlx8RAgCzumlpwz2uyLKXB8mr+JPBQ4uDYi+IKKqmWiBjCpVuNiOpDlwbRiK5GchTV51q54cRkJlrBTakBL2DMjYD66+hHJPD9Hk6xpgLAIxuimYaGpij+gJpwB1d+2DJFw6rPxy3JEqnbIiBzGTMgzE/TOG4KcFHjgQU2ESCOfYK3lZdGUCwjvpQmLHpB17d9rPN2+JsoLRL1jHG2kdn7DDLj+zzycDP9jOqeJk3K6ETxe8pwVRSTI4A4fjBHGr/XdREioMaasiVrpvUuLTIwDl4ewGdLmrISPHPadqTLFfcxnXcnWRwLsEVqSRoSNnGwtKOivGqnmTEJnw48yjjQy8+A=";

        try {
            AWSKMS kmsClient = AWSKMSClientBuilder.standard()
                    .withRegion(Regions.AP_NORTHEAST_2)
                    .build();

            DecryptRequest request = new DecryptRequest();
            request.withCiphertextBlob(ByteBuffer.wrap(Base64.decodeBase64(encriptedText)));
            request.withKeyId(KEY_ID);
            request.withEncryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256);
            ByteBuffer plainText = kmsClient.decrypt(request).getPlaintext();

            System.out.println("plainText: " + new String(plainText.array()));
        } catch (Exception e) {
            System.out.println("decrypt fail: " + e.getMessage());
        }
    }
}