package uk.gov.cabinetoffice.di.ipv.genericatpservice.config;

import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Configuration
public class ATPConfig {

    private final String privateSigningKeyFileName;

    public ATPConfig(@Value("${signing.private-key}") String privateSigningKeyFileName) {
        this.privateSigningKeyFileName = privateSigningKeyFileName;
    }

    @Bean("atp-signing-key")
    @Primary
    Key signingKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        var factory = KeyFactory.getInstance("RSA");
        var cpr = new ClassPathResource(privateSigningKeyFileName);

        try (var reader = new BufferedReader(new InputStreamReader(cpr.getURL().openStream()));
            var pemReader = new PemReader(reader)
        ) {
            var privKeySpec = new PKCS8EncodedKeySpec(pemReader.readPemObject().getContent());
            return factory.generatePrivate(privKeySpec);
        }
    }

    @Bean("ipv-subject-urn")
    String ipvSubject() {
        return "urn:di:ipv:ipv-atp-playbox";
    }

    @Bean("atp-issuer-urn")
    String atpIssuer() {
        return "urn:di:ipv:generic-atp-service";
    }
}
