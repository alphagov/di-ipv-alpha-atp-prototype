package uk.gov.cabinetoffice.di.ipv.genericatpservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import uk.gov.cabinetoffice.di.ipv.genericatpservice.services.SigningService;

@Controller
@Slf4j
public class AtpController {

    private final SigningService signingService;

    @Autowired
    public AtpController(SigningService signingService) {
        this.signingService = signingService;
    }

    @PostMapping("/process")
    public Mono<ResponseEntity<String>> process(@RequestBody String jsonData) {
        log.info("Processing data");
        var signedData = signingService.signData(jsonData);

        return signedData
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
