package ufrn.edu.loja.prova.service;

import org.springframework.stereotype.Service;
import ufrn.edu.loja.prova.model.CamisaModel;
import ufrn.edu.loja.prova.repository.CamisaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CamisaService {

    private CamisaRepository repository;

    public CamisaService(CamisaRepository repository) {
        this.repository = repository;
    }

    public void save(CamisaModel p){
        repository.save(p);
    }

    public List<CamisaModel> findAll(){
        return repository.findAll();
    }

    public Optional<CamisaModel> findById(Long id){
        return repository.findById(id);
    }

    public void delete(Optional<CamisaModel> camisa){
        camisa.get().setSoftDelete();
        repository.save(camisa.get()); 
    }

    public List<CamisaModel> findBySoftDeleteIsNull() {
        return repository.findBySoftDeleteIsNull();
    } 

}
