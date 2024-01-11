package com.curso.microservicio;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("EmailServiceDummy")
public class EmailsServiceDummy implements EmailsService {
    @Override
    public void mandarUnEmail(String asunto, String cuerpo) {
    }
}
