package pe.athena.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pe.athena.model.Editorial;
import pe.athena.service.editorialService;

@RestController
@RequestMapping("/api/editoriales")
public class editorialController {

    @Autowired
    private editorialService editorialService;

    @GetMapping
    public List<Editorial> listar() {
        return editorialService.listar();
    }

    @PostMapping
    public Editorial agregar(@RequestBody Editorial editorial) {
        return editorialService.guardar(editorial);
    }
}