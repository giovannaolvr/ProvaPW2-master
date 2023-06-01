package ufrn.edu.loja.prova.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.edu.loja.prova.model.CamisaModel;



public interface CamisaRepository extends JpaRepository<CamisaModel, Long>{
    List<CamisaModel> findCamisaById(Long id);

    List<CamisaModel> findBySoftDeleteIsNull();
}
    

