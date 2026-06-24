package pe.athena.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pe.athena.model.DetallePrestamo;
import pe.athena.model.Ejemplar;
import pe.athena.model.Prestamo;
import pe.athena.model.SolicitudPrestamo;
import pe.athena.model.Usuario;
import pe.athena.repository.IDetallePrestamoRepository;
import pe.athena.repository.IEjemplarRepository;
import pe.athena.repository.IPrestamoRepository;
import pe.athena.repository.ISolicitudPrestamoRepository;

@Controller
public class PrestamoController {

	@Autowired
	private ISolicitudPrestamoRepository repoSolicitud;

	@Autowired
	private IPrestamoRepository repoPrestamo;

	@Autowired
	private IDetallePrestamoRepository repoDetalle;

	@Autowired
	private IEjemplarRepository repoEjemplar;

	// blindaje por rol (mismo patron 6.5)
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

	// PANTALLA PRINCIPAL: solicitudes pendientes
	@GetMapping("/prestamos")
	public String cargarPagina(HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1, 2); // Admin o Bibliotecario
		if (acceso != null)
			return acceso;

		model.addAttribute("lstPendientes", repoSolicitud.findByEstado(1));
		return "prestamo";
	}

	// RECHAZAR: solo cambia estado de la solicitud
	@GetMapping("/prestamos/rechazar/{idSolicitud}")
	public String rechazar(@PathVariable Integer idSolicitud, HttpSession session) {
		String acceso = validarAcceso(session, 1, 2);
		if (acceso != null)
			return acceso;

		SolicitudPrestamo s = repoSolicitud.findById(idSolicitud).get();
		if (s.getEstado() == 1) { // solo si sigue pendiente
			s.setEstado(3); // Rechazada
			s.setFechaRespuesta(LocalDate.now());
			repoSolicitud.save(s);
		}
		return "redirect:/prestamos";
	}

	// APROBAR PASO A: abrir formulario con ejemplares disponibles
	@GetMapping("/prestamos/aprobar/{idSolicitud}")
	public String formAprobar(@PathVariable Integer idSolicitud, HttpSession session, Model model) {
		String acceso = validarAcceso(session, 1, 2);
		if (acceso != null)
			return acceso;

		SolicitudPrestamo s = repoSolicitud.findById(idSolicitud).get();
		model.addAttribute("solicitud", s);
		// combo: ejemplares disponibles (estado=1) del libro solicitado
		model.addAttribute("lstEjemplares",
				repoEjemplar.findByIdLibroAndEstado(s.getIdLibro(), 1));
		return "aprobar_prestamo";
	}

	// APROBAR PASO B: confirmar y ejecutar la transaccion
	@PostMapping("/prestamos/aprobar")
	public String confirmarAprobar(
			@RequestParam("idSolicitud") Integer idSolicitud,
			@RequestParam("idEjemplar") Integer idEjemplar,
			@RequestParam(value = "observacion", required = false) String observacion,
			HttpSession session,
			Model model) {

		String acceso = validarAcceso(session, 1, 2);
		if (acceso != null)
			return acceso;

		// 1) buscar la solicitud
		SolicitudPrestamo s = repoSolicitud.findById(idSolicitud).get();

		// 2) validar que siga pendiente
		if (s.getEstado() != 1) {
			return "redirect:/prestamos";
		}

		// 3) validar ejemplar disponible y que pertenezca al libro solicitado
		Ejemplar e = repoEjemplar.findById(idEjemplar).get();
		if (e.getEstado() != 1 || !e.getIdLibro().equals(s.getIdLibro())) {
			// ejemplar ya no disponible o no corresponde -> reabrir formulario con aviso
			model.addAttribute("solicitud", s);
			model.addAttribute("lstEjemplares",
					repoEjemplar.findByIdLibroAndEstado(s.getIdLibro(), 1));
			model.addAttribute("mensaje", "El ejemplar ya no está disponible. Elija otro.");
			model.addAttribute("cssmensaje", "alert alert-danger");
			return "aprobar_prestamo";
		}

		// 4) aprobar la solicitud
		s.setEstado(2); // Aprobada
		s.setFechaRespuesta(LocalDate.now());
		repoSolicitud.save(s);

		// 5) crear el prestamo
		Prestamo p = new Prestamo();
		p.setIdSolicitud(s.getIdSolicitud());
		p.setIdUsuario(s.getIdUsuario());
		p.setFechaPrestamo(LocalDate.now());
		p.setFechaLimite(LocalDate.now().plusDays(7));
		p.setFechaDevolucion(null);
		p.setObservacion(observacion);
		p.setEstado(1); // Activo
		repoPrestamo.save(p);

		// 6) crear el detalle
		DetallePrestamo d = new DetallePrestamo();
		d.setIdPrestamo(p.getIdPrestamo());
		d.setIdEjemplar(e.getIdEjemplar());
		d.setObservacion("Asignado en aprobacion de solicitud");
		repoDetalle.save(d);

		// 7) cambiar el ejemplar a Prestado
		e.setEstado(2);
		repoEjemplar.save(e);

		// 8) redirect
		return "redirect:/prestamos";
	}
}