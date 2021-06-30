package uk.gov.cabinetoffice.di.ipv.genericatpservice.services;

import reactor.core.publisher.Mono;

public interface SigningService {

    Mono<String> signData(String jsonData);
}
