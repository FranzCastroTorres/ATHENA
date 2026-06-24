package pe.athena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import pe.athena.model.Editorial;
import pe.athena.model.Usuario;
import pe.athena.repository.IEditorialRepository;

@Controller
public class EditorialController {

	@Autowired
	private IEditorialRepository repoEditorial;

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

	@GetMapping("/editoriales")
	public String cargarPagina(HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		model.addAttribute("editorial", new Editorial());
		model.addAttribute("lstEditoriales", repoEditorial.findByEstado(1));
		return "editorial";
	}

	@PostMapping("/editoriales/grabar")
	public String grabar(@ModelAttribute Editorial editorial, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		try {
			String nombre = editorial.getNombre() != null ? editorial.getNombre().trim() : "";

			// EDICION
			if (editorial.getIdEditorial() != null) {
				Editorial existente = repoEditorial.findByNombre(nombre);

				if (existente != null && !existente.getIdEditorial().equals(editorial.getIdEditorial())) {
					model.addAttribute("mensaje", "La editorial ya existe. Ingrese un nombre diferente.");
					model.addAttribute("cssmensaje", "alert alert-danger");
					model.addAttribute("editorial", editorial);
					model.addAttribute("lstEditoriales", repoEditorial.findByEstado(1));
					return "editorial";
				}

				Editorial actual = repoEditorial.findById(editorial.getIdEditorial()).orElse(null);
				if (actual != null) {
					actual.setNombre(nombre);
					actual.setTelefono(editorial.getTelefono());
					actual.setEmail(editorial.getEmail());
					actual.setDireccion(editorial.getDireccion());
					actual.setSitioWeb(editorial.getSitioWeb());
					if (actual.getEstado() == null) {
						actual.setEstado(1);
					}
					repoEditorial.save(actual);
				}

				return "redirect:/editoriales";
			}

			// NUEVO
			Editorial existente = repoEditorial.findByNombre(nombre);

			if (existente != null) {
				if (existente.getEstado() != null && existente.getEstado() == 0) {
					// REACTIVAR
					existente.setNombre(nombre);
					existente.setTelefono(editorial.getTelefono());
					existente.setEmail(editorial.getEmail());
					existente.setDireccion(editorial.getDireccion());
					existente.setSitioWeb(editorial.getSitioWeb());
					existente.setEstado(1);
					repoEditorial.save(existente);
					return "redirect:/editoriales";
				} else {
					model.addAttribute("mensaje", "La editorial ya existe. Ingrese un nombre diferente.");
					model.addAttribute("cssmensaje", "alert alert-danger");
					model.addAttribute("editorial", new Editorial());
					model.addAttribute("lstEditoriales", repoEditorial.findByEstado(1));
					return "editorial";
				}
			}

			// INSERTAR NUEVA
			editorial.setNombre(nombre);
			if (editorial.getEstado() == null) {
				editorial.setEstado(1);
			}
			repoEditorial.save(editorial);

			return "redirect:/editoriales";

		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
			model.addAttribute("cssmensaje", "alert alert-danger");
			model.addAttribute("editorial", editorial);
			model.addAttribute("lstEditoriales", repoEditorial.findByEstado(1));
			return "editorial";
		}
	}

	@GetMapping("/editoriales/editar/{id}")
	public String editar(@PathVariable Integer id, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		Editorial e = repoEditorial.findById(id).get();
		model.addAttribute("editorial", e);
		model.addAttribute("lstEditoriales", repoEditorial.findByEstado(1));
		return "editorial";
	}

	@GetMapping("/editoriales/eliminar/{id}")
	public String eliminar(@PathVariable Integer id, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		Editorial e = repoEditorial.findById(id).get();
		e.setEstado(0);
		repoEditorial.save(e);
		return "redirect:/editoriales";
	}
}