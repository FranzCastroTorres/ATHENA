package pe.athena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.athena.model.Autor;
import pe.athena.repositoy.autorRepository;

@Service
public class autorService {

    @Autowired
    private autorRepository repo;

    public List<Autor> listar() {
        return repo.findAll();
    }

    public Autor guardar(Autor autor) {
        return repo.save(autor);
    }

    public Autor buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
