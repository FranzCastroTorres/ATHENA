package pe.athena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import pe.athena.model.Libro;
import pe.athena.model.Usuario;
import pe.athena.repository.IAutorRepository;
import pe.athena.repository.ICategoriaRepository;
import pe.athena.repository.IEditorialRepository;
import pe.athena.repository.ILibroRepository;

@Controller
public class LibroController {

	@Autowired
	private ILibroRepository repoLibro;

	@Autowired
	private IAutorRepository repoAutor;

	@Autowired
	private IEditorialRepository repoEditorial;

	@Autowired
	private ICategoriaRepository repoCategoria;

	// validacion de acceso por rol
	private String validarAcceso(HttpSession session, Integer... rolesPermitidos) {
		Usuario u = (Usuario) session.getAttribute("usuario");
		if (u == null) {
			return "redirect:/login";
		}
		for (Integer rol : rolesPermitidos) {
			if (u.getIdRol().equals(rol)) {
				return null;
			}
		}
		return "redirect:/inicio";
	}

	// metodo auxiliar para cargar los 3 combos
	private void cargarCombos(Model model) {
		model.addAttribute("lstAutores", repoAutor.findByEstado(1));
		model.addAttribute("lstEditoriales", repoEditorial.findByEstado(1));
		model.addAttribute("lstCategorias", repoCategoria.findByEstado(1));
	}

	// cargar la pagina
	@GetMapping("/libros")
	public String cargarPagina(HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1, 2); // Admin y Bibliotecario
		if (acceso != null)
			return acceso;

		model.addAttribute("libro", new Libro());
		cargarCombos(model);
		model.addAttribute("lstLibros", repoLibro.findByEstado(1));
		return "libro";
	}

	@PostMapping("/libros/grabar")
	public String grabar(@ModelAttribute Libro libro, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1, 2);
		if (acceso != null)
			return acceso;

		try {
			if (libro.getEstado() == null) {
				libro.setEstado(1);
			}
			repoLibro.save(libro);
		} catch (Exception e) {
			// si falla, mostrar error en la misma vista
			model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
			model.addAttribute("cssmensaje", "alert alert-danger");
			model.addAttribute("libro", new Libro());
			cargarCombos(model);
			model.addAttribute("lstLibros", repoLibro.findByEstado(1));
			return "libro";
		}

		// PRG: redirigir tras guardar -> recarga limpia con objX completos, sin F5 duplicado
		return "redirect:/libros";
	}

	// editar
	@GetMapping("/libros/editar/{id}")
	public String editar(@PathVariable Integer id, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1, 2);
		if (acceso != null)
			return acceso;

		Libro l = repoLibro.findById(id).get();
		model.addAttribute("libro", l);
		cargarCombos(model);
		model.addAttribute("lstLibros", repoLibro.findByEstado(1));
		return "libro";
	}

	// eliminar
	@GetMapping("/libros/eliminar/{id}")
	public String eliminar(@PathVariable Integer id, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1, 2);
		if (acceso != null)
			return acceso;

		Libro l = repoLibro.findById(id).get();
		l.setEstado(0);
		repoLibro.save(l);
		return "redirect:/libros";
	}
}