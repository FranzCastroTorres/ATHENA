package pe.athena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.athena.model.Categoria;
import pe.athena.repositoy.categoriaRepository;

@Service
public class categoriaService {

    @Autowired
    private categoriaRepository repo;

    public List<Categoria> listar() {
        return repo.findAll();
    }

    public Categoria guardar(Categoria categoria) {
        return repo.save(categoria);
    }
    
    public Categoria buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
