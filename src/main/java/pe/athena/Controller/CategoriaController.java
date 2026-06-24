package pe.athena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import pe.athena.model.Categoria;
import pe.athena.model.Usuario;
import pe.athena.repository.ICategoriaRepository;

@Controller
public class CategoriaController {

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

	@GetMapping("/categorias")
	public String cargarPagina(HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		model.addAttribute("categoria", new Categoria());
		model.addAttribute("lstCategorias", repoCategoria.findByEstado(1));
		return "categoria";
	}

	@PostMapping("/categorias/grabar")
	public String grabar(@ModelAttribute Categoria categoria, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		try {
			String descripcion = categoria.getDescripcion() != null ? categoria.getDescripcion().trim() : "";

			// EDICION
			if (categoria.getIdCategoria() != null) {
				Categoria existente = repoCategoria.findByDescripcion(descripcion);

				// si existe otra categoria activa/inactiva con esa descripcion y NO es la misma
				if (existente != null && !existente.getIdCategoria().equals(categoria.getIdCategoria())) {
					model.addAttribute("mensaje", "La categoría ya existe. Ingrese una descripción diferente.");
					model.addAttribute("cssmensaje", "alert alert-danger");
					model.addAttribute("categoria", categoria);
					model.addAttribute("lstCategorias", repoCategoria.findByEstado(1));
					return "categoria";
				}

				Categoria actual = repoCategoria.findById(categoria.getIdCategoria()).orElse(null);
				if (actual != null) {
					actual.setDescripcion(descripcion);
					if (actual.getEstado() == null) {
						actual.setEstado(1);
					}
					repoCategoria.save(actual);
				}

				return "redirect:/categorias";
			}

			// NUEVO
			Categoria existente = repoCategoria.findByDescripcion(descripcion);

			if (existente != null) {
				if (existente.getEstado() != null && existente.getEstado() == 0) {
					// REACTIVAR
					existente.setDescripcion(descripcion);
					existente.setEstado(1);
					repoCategoria.save(existente);
					return "redirect:/categorias";
				} else {
					// YA EXISTE ACTIVA
					model.addAttribute("mensaje", "La categoría ya existe. Ingrese una descripción diferente.");
					model.addAttribute("cssmensaje", "alert alert-danger");
					model.addAttribute("categoria", new Categoria());
					model.addAttribute("lstCategorias", repoCategoria.findByEstado(1));
					return "categoria";
				}
			}

			// INSERTAR NUEVA
			categoria.setDescripcion(descripcion);
			if (categoria.getEstado() == null) {
				categoria.setEstado(1);
			}
			repoCategoria.save(categoria);

			return "redirect:/categorias";

		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
			model.addAttribute("cssmensaje", "alert alert-danger");
			model.addAttribute("categoria", categoria);
			model.addAttribute("lstCategorias", repoCategoria.findByEstado(1));
			return "categoria";
		}
	}

	@GetMapping("/categorias/editar/{id}")
	public String editar(@PathVariable Integer id, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		Categoria c = repoCategoria.findById(id).get();
		model.addAttribute("categoria", c);
		model.addAttribute("lstCategorias", repoCategoria.findByEstado(1));
		return "categoria";
	}

	@GetMapping("/categorias/eliminar/{id}")
	public String eliminar(@PathVariable Integer id, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1);
		if (acceso != null)
			return acceso;

		Categoria c = repoCategoria.findById(id).get();
		c.setEstado(0);
		repoCategoria.save(c);
		return "redirect:/categorias";
	}
}