package uk.gov.cabinetoffice.di.ipv.genericatpservice.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SigningServiceImpl implements SigningService {

    private final Key signingKey;
    private final String ipvSubject;
    private final String issuer;

    @Autowired
    public SigningServiceImpl(
        @Qualifier("atp-signing-key") Key signingKey,
        @Qualifier("ipv-subject-urn") String ipvSubject,
        @Qualifier("atp-issuer-urn") String issuer
    ) {
        this.signingKey = signingKey;
        this.ipvSubject = ipvSubject;
        this.issuer = issuer;
    }

    @Override
    public Mono<String> signData(String data) {
        Map<String, Object> attributeMap = new HashMap<>();

        attributeMap.put("originalData", new Gson().fromJson(data, new TypeToken<HashMap<String, Object>>(){}.getType()));
        attributeMap.put("genericDataVerified", "true");

        String jws = Jwts
            .builder()
            .setClaims(attributeMap)
            .setSubject(ipvSubject)
            .setIssuer(issuer)
            .setExpiration(new Date(Instant.now().plus(60, ChronoUnit.MINUTES).toEpochMilli()))
            .setIssuedAt(new Date(Instant.now().toEpochMilli()))
            .signWith(signingKey)
            .compact();

        log.info("Signed data");

        return Mono.just(jws);
    }
}
