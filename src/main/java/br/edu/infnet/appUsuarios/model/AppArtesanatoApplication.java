package br.edu.infnet.appUsuarios.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories("br.edu.infnet.appUsuarios.model.repository")
class AppUsuariosApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppUsuariosApplication.class, args);
    }
}
