package pe.athena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import pe.athena.model.Editorial;
import pe.athena.repository.IEditorialRepository;

@Controller
public class EditorialController {

	@Autowired
	private IEditorialRepository repoEditorial;

	// cargar la pagina (form vacio + tabla)
	@GetMapping("/editoriales")
	public String cargarPagina(Model model) {
		model.addAttribute("editorial", new Editorial());
		model.addAttribute("lstEditoriales", repoEditorial.findByEstado(1));
		return "editorial";
	}

	// grabar (insertar o actualizar)
	@PostMapping("/editoriales/grabar")
	public String grabar(@ModelAttribute Editorial editorial, Model model) {
		try {
			if (editorial.getEstado() == null) {
				editorial.setEstado(1);
			}
			repoEditorial.save(editorial);
			model.addAttribute("mensaje", "Registro guardado correctamente");
			model.addAttribute("cssmensaje", "alert alert-success");
		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
			model.addAttribute("cssmensaje", "alert alert-danger");
		}

		model.addAttribute("editorial", new Editorial());
		model.addAttribute("lstEditoriales", repoEditorial.findByEstado(1));
		return "editorial";
	}

	// editar: cargar una editorial en el formulario
	@GetMapping("/editoriales/editar/{id}")
	public String editar(@PathVariable Integer id, Model model) {
		Editorial e = repoEditorial.findById(id).get();
		model.addAttribute("editorial", e);
		model.addAttribute("lstEditoriales", repoEditorial.findByEstado(1));
		return "editorial";
	}

	// eliminar (borrado logico: estado = 0)
	@GetMapping("/editoriales/eliminar/{id}")
	public String eliminar(@PathVariable Integer id, Model model) {
		Editorial e = repoEditorial.findById(id).get();
		e.setEstado(0);
		repoEditorial.save(e);
		return "redirect:/editoriales";
	}
}