package pe.athena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import pe.athena.model.Autor;
import pe.athena.model.Usuario;
import pe.athena.repository.IAutorRepository;

@Controller
public class AutorController {

	@Autowired
	private IAutorRepository repoAutor;

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

	@GetMapping("/autores")
	public String cargarPagina(HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		model.addAttribute("autor", new Autor());
		model.addAttribute("lstAutores", repoAutor.findByEstado(1));
		return "autor";
	}

	@PostMapping("/autores/grabar")
	public String grabar(@ModelAttribute Autor autor, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		try {
			String nombre = autor.getNombre() != null ? autor.getNombre().trim() : "";

			// EDICION
			if (autor.getIdAutor() != null) {
				Autor existente = repoAutor.findByNombre(nombre);

				// si existe otro autor con ese nombre y no es el mismo registro
				if (existente != null && !existente.getIdAutor().equals(autor.getIdAutor())) {
					model.addAttribute("mensaje", "El autor ya existe. Ingrese un nombre diferente.");
					model.addAttribute("cssmensaje", "alert alert-danger");
					model.addAttribute("autor", autor);
					model.addAttribute("lstAutores", repoAutor.findByEstado(1));
					return "autor";
				}

				Autor actual = repoAutor.findById(autor.getIdAutor()).orElse(null);
				if (actual != null) {
					actual.setNombre(nombre);
					actual.setNacionalidad(autor.getNacionalidad());
					actual.setFechaNacimiento(autor.getFechaNacimiento());
					actual.setBiografia(autor.getBiografia());
					if (actual.getEstado() == null) {
						actual.setEstado(1);
					}
					repoAutor.save(actual);
				}

				return "redirect:/autores";
			}

			// NUEVO
			Autor existente = repoAutor.findByNombre(nombre);

			if (existente != null) {
				if (existente.getEstado() != null && existente.getEstado() == 0) {
					// REACTIVAR
					existente.setNombre(nombre);
					existente.setNacionalidad(autor.getNacionalidad());
					existente.setFechaNacimiento(autor.getFechaNacimiento());
					existente.setBiografia(autor.getBiografia());
					existente.setEstado(1);
					repoAutor.save(existente);
					return "redirect:/autores";
				} else {
					model.addAttribute("mensaje", "El autor ya existe. Ingrese un nombre diferente.");
					model.addAttribute("cssmensaje", "alert alert-danger");
					model.addAttribute("autor", new Autor());
					model.addAttribute("lstAutores", repoAutor.findByEstado(1));
					return "autor";
				}
			}

			// INSERTAR NUEVO
			autor.setNombre(nombre);
			if (autor.getEstado() == null) {
				autor.setEstado(1);
			}
			repoAutor.save(autor);

			return "redirect:/autores";

		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
			model.addAttribute("cssmensaje", "alert alert-danger");
			model.addAttribute("autor", autor);
			model.addAttribute("lstAutores", repoAutor.findByEstado(1));
			return "autor";
		}
	}

	@GetMapping("/autores/editar/{id}")
	public String editar(@PathVariable Integer id, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		Autor a = repoAutor.findById(id).get();
		model.addAttribute("autor", a);
		model.addAttribute("lstAutores", repoAutor.findByEstado(1));
		return "autor";
	}

	@GetMapping("/autores/eliminar/{id}")
	public String eliminar(@PathVariable Integer id, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		Autor a = repoAutor.findById(id).get();
		a.setEstado(0);
		repoAutor.save(a);
		return "redirect:/autores";
	}
}