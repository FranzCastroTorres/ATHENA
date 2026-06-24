package pe.athena.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pe.athena.model.SolicitudPrestamo;
import pe.athena.model.Usuario;
import pe.athena.repository.ILibroRepository;
import pe.athena.repository.ISolicitudPrestamoRepository;

@Controller
public class SolicitudPrestamoController {

	@Autowired
	private ISolicitudPrestamoRepository repoSolicitud;

	@Autowired
	private ILibroRepository repoLibro;

	// validacion de acceso por rol (mismo patron del blindaje 6.5)
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

	// cargar la pagina de solicitudes del usuario
	@GetMapping("/solicitudes")
	public String cargarPagina(HttpSession session, Model model) {
		String acceso = validarAcceso(session, 3); // solo Usuario
		if (acceso != null)
			return acceso;

		Usuario u = (Usuario) session.getAttribute("usuario");

		// combo de libros activos
		model.addAttribute("lstLibros", repoLibro.findByEstado(1));
		// tabla: solo las solicitudes de este usuario
		model.addAttribute("lstSolicitudes", repoSolicitud.findByIdUsuario(u.getIdUsuario()));
		return "mis_solicitudes";
	}

	@PostMapping("/solicitudes/grabar")
	public String grabar(
			@RequestParam("idLibro") Integer idLibro,
			@RequestParam(value = "observacion", required = false) String observacion,
			HttpSession session,
			Model model) {

		String acceso = validarAcceso(session, 3);
		if (acceso != null)
			return acceso;

		Usuario u = (Usuario) session.getAttribute("usuario");

		try {
			SolicitudPrestamo sp = new SolicitudPrestamo();
			sp.setIdUsuario(u.getIdUsuario());
			sp.setIdLibro(idLibro);
			sp.setFechaSolicitud(LocalDate.now());
			sp.setFechaRespuesta(null);
			sp.setObservacion(observacion);
			sp.setEstado(1);

			repoSolicitud.save(sp);
		} catch (Exception e) {
			// si falla, mostramos el error en la misma vista
			model.addAttribute("mensaje", "Error al enviar: " + e.getMessage());
			model.addAttribute("cssmensaje", "alert alert-danger");
			model.addAttribute("lstLibros", repoLibro.findByEstado(1));
			model.addAttribute("lstSolicitudes", repoSolicitud.findByIdUsuario(u.getIdUsuario()));
			return "mis_solicitudes";
		}

		// PRG: redirigir tras guardar evita el duplicado por F5
		return "redirect:/solicitudes";
	}
}