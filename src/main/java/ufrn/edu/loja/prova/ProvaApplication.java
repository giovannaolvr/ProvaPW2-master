package ufrn.edu.loja.prova;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import ufrn.edu.loja.prova.repository.UsuarioRepository;
import ufrn.edu.loja.prova.model.UsuarioModel;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ProvaApplication {

	@Bean
    CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        return args -> {

            List<UsuarioModel> users = Arrays.asList(
				new UsuarioModel(null, "João", "admin", "admin@admin.com", "123.456.789-10", true),
				new UsuarioModel(null, "Maria", "user1", "maria@gmail.com", "444.456.789-10", false),
				new UsuarioModel(null, "Pedro", "user2", "pedro@hotmail.com", "555.456.789-10", false)
			);


            for (var e : users) {
                System.out.println(e);
            }
            usuarioRepository.saveAll(users);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ProvaApplication.class, args);
    }

}
