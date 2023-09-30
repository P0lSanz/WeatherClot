package edu.fje2.daw2.spring1.controladors;

import edu.fje2.daw2.spring1.model.Usuari;
import edu.fje2.daw2.spring1.repositoris.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class UsuariController {
    /**
     Repositori per gestionar les entitats d'Usuari
     */
    @Autowired
    private UsuariRepository repositori;
    /**
     Mètode que s'encarrega de guardar les ciutats seleccionades per l'usuari
     @param ciutatsSeleccionades Llista de noms de ciutats seleccionades per l'usuari
     @param session Sessió HTTP actual
     @return Retorna la vista "previsio"
     */
    @PostMapping("/guardarCiutats")
    public String guardarCiutats(@RequestParam("ciutatsSeleccionades") List<String> ciutatsSeleccionades, HttpSession session) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString()
                .substring(authentication.getPrincipal().toString().indexOf("username=") + 9);
        System.out.println("username: " + username);
        System.out.println("Ciutats seleccionades: " + ciutatsSeleccionades);

        Usuari usuari = new Usuari(username,ciutatsSeleccionades);

        repositori.save(usuari);

        return new ModelAndView("redirect:/ciutats/previsio").getViewName();
    }
}
