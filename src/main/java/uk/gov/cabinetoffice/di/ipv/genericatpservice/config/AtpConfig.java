package uk.gov.cabinetoffice.di.ipv.genericatpservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uk.gov.cabinetoffice.di.ipv.genericatpservice.utils.KeyReader;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Configuration
public class AtpConfig {

    private final String signingKeyString;

    public AtpConfig(@Value("${signing.key}") String signingKeyString) {
        this.signingKeyString = signingKeyString;
    }

    @Bean("atp-signing-key")
    @Primary
    Key signingKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyReader.loadKey(signingKeyString);
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
