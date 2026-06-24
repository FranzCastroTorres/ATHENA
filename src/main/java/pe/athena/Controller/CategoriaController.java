package pe.athena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import pe.athena.model.Categoria;
import pe.athena.repository.ICategoriaRepository;

@Controller
public class CategoriaController {

	@Autowired
	private ICategoriaRepository repoCategoria;

	// cargar la pagina (form vacio + tabla)
	@GetMapping("/categorias")
	public String cargarPagina(Model model) {
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("lstCategorias", repoCategoria.findByEstado(1));
		return "categoria";
	}

	// grabar (insertar o actualizar)
	@PostMapping("/categorias/grabar")
	public String grabar(@ModelAttribute Categoria categoria, Model model) {
		try {
			if (categoria.getEstado() == null) {
				categoria.setEstado(1);
			}
			repoCategoria.save(categoria);
			model.addAttribute("mensaje", "Registro guardado correctamente");
			model.addAttribute("cssmensaje", "alert alert-success");
		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
			model.addAttribute("cssmensaje", "alert alert-danger");
		}

		model.addAttribute("categoria", new Categoria());
		model.addAttribute("lstCategorias", repoCategoria.findByEstado(1));
		return "categoria";
	}

	// editar: cargar una categoria en el formulario
	@GetMapping("/categorias/editar/{id}")
	public String editar(@PathVariable Integer id, Model model) {
		Categoria c = repoCategoria.findById(id).get();
		model.addAttribute("categoria", c);
		model.addAttribute("lstCategorias", repoCategoria.findByEstado(1));
		return "categoria";
	}

	// eliminar (borrado logico: estado = 0)
	@GetMapping("/categorias/eliminar/{id}")
	public String eliminar(@PathVariable Integer id, Model model) {
		Categoria c = repoCategoria.findById(id).get();
		c.setEstado(0);
		repoCategoria.save(c);
		return "redirect:/categorias";
	}
}