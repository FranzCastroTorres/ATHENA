package pe.athena.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pe.athena.model.Autor;
import pe.athena.service.autorService;

@RestController
@RequestMapping("/api/autores")
public class autorController {

    @Autowired
    private autorService service;

    @GetMapping
    public List<Autor> listar() {
        return service.listar();
    }

    @PostMapping
    public Autor agregar(@RequestBody Autor autor) {
        return service.guardar(autor);
    }

    @GetMapping("/{id}")
    public Autor buscar(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
