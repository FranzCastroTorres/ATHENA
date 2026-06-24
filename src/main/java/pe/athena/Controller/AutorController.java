package pe.athena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import pe.athena.model.Autor;
import pe.athena.repository.IAutorRepository;

@Controller
public class AutorController {

	// repository directo, patron del profesor
	@Autowired
	private IAutorRepository repoAutor;

	// abrir / cargar la pagina (form vacio + tabla)
	@GetMapping("/autores")
	public String cargarPagina(Model model) {
		model.addAttribute("autor", new Autor());
		model.addAttribute("lstAutores", repoAutor.findByEstado(1));
		return "autor";
	}

	// grabar (insertar o actualizar)
	@PostMapping("/autores/grabar")
	public String grabar(@ModelAttribute Autor autor, Model model) {
		try {
			// si el estado viene null (registro nuevo), por defecto activo
			if (autor.getEstado() == null) {
				autor.setEstado(1);
			}
			repoAutor.save(autor);
			model.addAttribute("mensaje", "Registro guardado correctamente");
			model.addAttribute("cssmensaje", "alert alert-success");
		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
			model.addAttribute("cssmensaje", "alert alert-danger");
		}

		model.addAttribute("autor", new Autor());
		model.addAttribute("lstAutores", repoAutor.findByEstado(1));
		return "autor";
	}

	// editar: cargar un autor en el formulario
	@GetMapping("/autores/editar/{id}")
	public String editar(@PathVariable Integer id, Model model) {
		Autor a = repoAutor.findById(id).get();
		model.addAttribute("autor", a);
		model.addAttribute("lstAutores", repoAutor.findByEstado(1));
		return "autor";
	}

	// eliminar (borrado logico: estado = 0)
	@GetMapping("/autores/eliminar/{id}")
	public String eliminar(@PathVariable Integer id, Model model) {
		Autor a = repoAutor.findById(id).get();
		a.setEstado(0);
		repoAutor.save(a);
		return "redirect:/autores";
	}
}