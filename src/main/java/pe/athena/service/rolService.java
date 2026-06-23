package pe.athena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.athena.model.Rol;
import pe.athena.repositoy.rolRepository;

@Service
public class rolService {

    @Autowired
    private rolRepository repo;

    public List<Rol> listar() {
        return repo.findAll();
    }

    public Rol guardar(Rol rol) {
        return repo.save(rol);
    }

    public Rol buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
