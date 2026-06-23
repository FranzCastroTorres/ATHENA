package pe.athena.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pe.athena.model.Usuario;
import pe.athena.repository.IUsuarioRepository;

@Controller
public class LoginController {

	// repository directo, igual que el profesor
	@Autowired
	private IUsuarioRepository repoUsu;

	// abrir la pagina de login
	@GetMapping("/")
	public String index() {
		return "login";
	}

	@GetMapping("/login")
	public String abrirLogin() {
		return "login";
	}

	// procesar el login y abrir sesion
	@PostMapping("/login")
	public String validarAcceso(
			@RequestParam("txtCorreo") String correo,
			@RequestParam("txtClave") String clave,
			HttpSession session,
			Model model) {

		Usuario u = repoUsu.findByCorreoAndClave(correo, clave);

		if (u != null) {
			// guardar en sesion
			session.setAttribute("usuario", u);
			session.setAttribute("rol", u.getIdRol());
			return "redirect:/inicio";
		} else {
			model.addAttribute("mensaje", "Correo o clave incorrectos");
			model.addAttribute("cssmensaje", "alert alert-danger");
			return "login";
		}
	}

	// pagina principal segun rol
	@GetMapping("/inicio")
	public String inicio(HttpSession session, Model model) {
		// si no hay sesion, volver al login
		Usuario u = (Usuario) session.getAttribute("usuario");
		if (u == null) {
			return "redirect:/login";
		}
		model.addAttribute("usuario", u);
		return "inicio";
	}

	// cerrar sesion
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
