package pe.athena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.athena.model.Editorial;
import pe.athena.repositoy.editorialRepository;

@Service
public class editorialService {

    @Autowired
    private editorialRepository repo;

    public List<Editorial> listar() {
        return repo.findAll();
    }

    public Editorial guardar(Editorial editorial) {
        return repo.save(editorial);
    }

    public Editorial buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
