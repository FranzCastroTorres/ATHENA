package pe.athena.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pe.athena.model.Categoria;
import pe.athena.service.categoriaService;

@RestController
@RequestMapping("/api/categorias")
public class categoriaController {

    @Autowired
    private categoriaService categoriaService;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.listar();
    }

    @PostMapping
    public Categoria agregar(@RequestBody Categoria categoria) {
        return categoriaService.guardar(categoria);
    }
}