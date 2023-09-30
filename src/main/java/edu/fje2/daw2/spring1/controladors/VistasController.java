package edu.fje2.daw2.spring1.controladors;

import edu.fje2.daw2.spring1.model.Ciutat;
import edu.fje2.daw2.spring1.repositoris.CiutatRepository;
import edu.fje2.daw2.spring1.repositoris.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
public class VistasController {
    @Autowired
    private CiutatRepository ciutatRepository;
    @Autowired
    private UsuariRepository usuariRepository;

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        List<Ciutat> llistaCiutats = ciutatRepository.findAll();
        model.addAttribute("ciutats", llistaCiutats);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString()
                .substring(authentication.getPrincipal().toString().indexOf("username=") + 9);

        model.addAttribute("idUsuari", username);
        return "dashboard";
    }
    @RequestMapping("/errorForm")
    public String errorForm() {
        return "error";
    }
    @RequestMapping("/afegirCiutat")
    public String crearCiutat() {
        return "afegirCiutat";
    }
}
