package pe.athena.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pe.athena.model.Rol;
import pe.athena.service.rolService;

@RestController
@RequestMapping("/api/roles")
public class rolController {

    @Autowired
    private rolService rolService;

    @GetMapping
    public List<Rol> listar() {
        return rolService.listar();
    }

    @PostMapping
    public Rol agregar(@RequestBody Rol rol) {
        return rolService.guardar(rol);
    }
}