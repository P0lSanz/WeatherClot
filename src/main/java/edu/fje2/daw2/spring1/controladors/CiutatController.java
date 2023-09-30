package edu.fje2.daw2.spring1.controladors;


import edu.fje2.daw2.spring1.model.Ciutat;

import edu.fje2.daw2.spring1.model.Usuari;
import edu.fje2.daw2.spring1.repositoris.CiutatRepository;
import edu.fje2.daw2.spring1.repositoris.UsuariRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ciutats")
/**
 Controlador per a la gestió de ciutats.
 */
public class CiutatController {

    @Autowired
    private CiutatService ciutatService;

    @Autowired
    private CiutatRepository ciutatRepository;

    @Autowired
    private UsuariRepository usuariRepository;

    /**
     Mostra la previsió meteorològica per a les ciutats que té l'usuari logat.
     @param model El model per a afegir les dades a la vista.
     @return La vista amb les previsions.
     */
    @GetMapping("/previsio")
    public String previsioCiutats(Model model) {
        ciutatService.actualitzarPrevisioCiutats();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString()
                .substring(authentication.getPrincipal().toString().indexOf("username=") + 9);

        List<Ciutat> llistaCiutats = new ArrayList<>();
        Usuari usuari = usuariRepository.findByOauthID(username);
        List<String> ciutatsUsuari = usuari.getCiutats();

        ciutatRepository.findAll().forEach(ciutat -> {
            if (ciutatsUsuari.contains(ciutat.getNom())) {
                llistaCiutats.add(ciutat);
            }
        });

        List<List<String>> llistaPrevisions = new ArrayList<>();
        for (Ciutat ciutat : llistaCiutats) {
            try {
                List<String> previsions = ciutat.getPrevisionsFromJSON();
                llistaPrevisions.add(previsions);
            } catch (JSONException e) {
                llistaPrevisions.add(new ArrayList<>());
                e.printStackTrace();
            }
        }

        model.addAttribute("ciutats", llistaCiutats);
        model.addAttribute("previsions", llistaPrevisions);

        return "previsio";
    }

    @GetMapping("/llistatCiutats")
    public String llistatCiutats(Model model) {
        List<Ciutat> llistaCiutats = ciutatRepository.findAll();
        model.addAttribute("ciutats", llistaCiutats);
        return "llistatCiutats";
    }

   /* @GetMapping("")
    public List<Ciutat> getCiudades() {
        return ciutatRepository.findAll();
    }*/

   @GetMapping("")
   public String getCiudades(Model model) {
       List<Ciutat> ciutats = ciutatRepository.findAll();
       model.addAttribute("ciutats", ciutats);
       return "llistatCiutats";
   }

    @PostMapping("/crearCiutat")
    public String crearCiutat(@RequestParam("nom") String nom, @RequestParam("consultaFetch") String consultaFetch) {
        Ciutat novaCiutat = new Ciutat(nom, consultaFetch,null);
        ciutatRepository.save(novaCiutat);

        return new ModelAndView("redirect:/ciutats/llistatCiutats").getViewName();
    }

    /*@PostMapping("/modificarCiutat")
    public String modificarCiutat(@RequestParam("nom") String nom,@RequestParam("consultaFetch") String novaConsultaFetch) {
        Ciutat ciutat = ciutatRepository.findByNom(nom);

        if (ciutat != null) {
            ciutat.setConsultaFetch(novaConsultaFetch);
            ciutatRepository.save(ciutat);
        }

        return new ModelAndView("redirect:/ciutats/llistatCiutats").getViewName();
    }*/

    @PostMapping("/{id}/editar")
    public String editarCiutat(@PathVariable("id") String id, @RequestParam("consultaFetch") String nuevaConsultaFetch, RedirectAttributes redirectAttributes) {
        Ciutat ciutat = ciutatRepository.findByNom(id);
        ciutat.setConsultaFetch(nuevaConsultaFetch);
        ciutatRepository.save(ciutat);
        redirectAttributes.addFlashAttribute("missatge", "Ciutat modificada correctament");
        return new ModelAndView ("redirect:/ciutats/llistatCiutats").getViewName();
    }

    @PostMapping("/{id}")
    public String eliminarCiutat(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        ciutatRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("missatge", "Ciutat esborrada correctament");
        return new ModelAndView ("redirect:/ciutats/llistatCiutats").getViewName();
    }
}

