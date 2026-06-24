package pe.athena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import pe.athena.model.Ejemplar;
import pe.athena.model.Usuario;
import pe.athena.repository.IEjemplarRepository;
import pe.athena.repository.ILibroRepository;

@Controller
public class EjemplarController {

	@Autowired
	private IEjemplarRepository repoEjemplar;

	@Autowired
	private ILibroRepository repoLibro;

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

	// cargar el combo de libros
	private void cargarCombos(Model model) {
		model.addAttribute("lstLibros", repoLibro.findByEstado(1));
	}

	// cargar la pagina
	@GetMapping("/ejemplares")
	public String cargarPagina(HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1, 2); // Admin y Bibliotecario
		if (acceso != null)
			return acceso;

		model.addAttribute("ejemplar", new Ejemplar());
		cargarCombos(model);
		model.addAttribute("lstEjemplares", repoEjemplar.findAll());
		return "ejemplar";
	}

	// grabar
	@PostMapping("/ejemplares/grabar")
	public String grabar(@ModelAttribute Ejemplar ejemplar, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1, 2);
		if (acceso != null)
			return acceso;

		try {
			if (ejemplar.getEstado() == null) {
				ejemplar.setEstado(1);
			}
			repoEjemplar.save(ejemplar);
			model.addAttribute("mensaje", "Registro guardado correctamente");
			model.addAttribute("cssmensaje", "alert alert-success");
		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
			model.addAttribute("cssmensaje", "alert alert-danger");
		}

		model.addAttribute("ejemplar", new Ejemplar());
		cargarCombos(model);
		model.addAttribute("lstEjemplares", repoEjemplar.findAll());
		return "ejemplar";
	}

	// editar
	@GetMapping("/ejemplares/editar/{id}")
	public String editar(@PathVariable Integer id, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1, 2);
		if (acceso != null)
			return acceso;

		Ejemplar e = repoEjemplar.findById(id).get();
		model.addAttribute("ejemplar", e);
		cargarCombos(model);
		model.addAttribute("lstEjemplares", repoEjemplar.findAll());
		return "ejemplar";
	}
}