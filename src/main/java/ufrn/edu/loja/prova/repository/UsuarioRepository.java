package ufrn.edu.loja.prova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.edu.loja.prova.model.UsuarioModel;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, String> {
    Optional<UsuarioModel> findByUsername(String username);
}