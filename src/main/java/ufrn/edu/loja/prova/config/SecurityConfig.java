package ufrn.edu.loja.prova.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/cadastrarPage").hasRole("ADMIN");
                    auth.requestMatchers("/editarPage/**").hasRole("ADMIN");
                    auth.requestMatchers("/deletar/**").hasRole("ADMIN");
                    auth.requestMatchers("/adminPage").hasRole("ADMIN");
                    auth.requestMatchers("/verCarrinho").hasRole("USER");
                    auth.requestMatchers("/adicionarCarrinho/**").hasRole("USER");
                    auth.requestMatchers("/finalizarCompra").hasRole("USER");
                    auth.anyRequest().permitAll();
                })
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .build();
    }

    @Bean
    BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
