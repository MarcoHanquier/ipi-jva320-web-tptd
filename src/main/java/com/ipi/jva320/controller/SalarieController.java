package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class SalarieController {


    @Autowired
    SalarieAideADomicileService salarieAideADomicileService;

    @GetMapping(value = "/salaries/{id}")
    public String getSalarie(final ModelMap model,
                             @PathVariable Long id) {
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        model.put("salarie", salarie);
        return "detail_Salarie";
    }

    @GetMapping("/salaries")
    public String searchSalaries(@RequestParam(name = "nom", required = false) String nom, ModelMap model) {
        List<SalarieAideADomicile> salaries;
//        if (!nom.isEmpty()) {
             if (nom != null && !nom.isEmpty()) {

            salaries = salarieAideADomicileService.getSalaries(nom);
            if (salaries.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun salarié trouvé pour le nom : " + nom);
            }

        } else {
            salaries = salarieAideADomicileService.getSalaries();
        }

        model.addAttribute("salaries", salaries);
        return "list";
    }

    @PostMapping("/salaries/{id}")
    public String updateSalarieAideADomicile(@PathVariable Long id, @ModelAttribute SalarieAideADomicile salarie, Model model) {
        try {
            salarie.setId(id);
            salarieAideADomicileService.updateSalarieAideADomicile(salarie);
            return "redirect:/salaries";
        } catch (SalarieException | EntityExistsException e) {
            model.addAttribute("error", "Erreur lors de la mise à jour : " + e.getMessage());
            model.addAttribute("salarie", salarie);
            return "detail_Salarie";
        }
    }

    @GetMapping("/salaries/{id}/delete")
    public String deleteSalarieAideADomicile(@PathVariable Long id, @ModelAttribute SalarieAideADomicile salarie, Model model) {
        try {
            salarie.setId(id);
            salarieAideADomicileService.deleteSalarieAideADomicile(id);
            return "redirect:/salaries";
        } catch (SalarieException | EntityExistsException e) {
            model.addAttribute("error", "Erreur lors de la mise à jour : " + e.getMessage());
            model.addAttribute("salarie", salarie);
            return "detail_Salarie";
        }
    }




    @GetMapping (value = "/salaries/new")
    public String getEmptyForm(final ModelMap model) {
       SalarieAideADomicile salarie = new SalarieAideADomicile();
        model.put("salarie", salarie);
//        model.addAttribute("isNew", true);

        return "detail_Salarie";
    }

//    @GetMapping (value = "/salaries")
//    public String getSalariesList(final ModelMap model) {
//        List<SalarieAideADomicile> salaries = salarieAideADomicileService.getSalaries();
//        model.put("salaries", salaries);
//
//        return "list";
//    }

    @PostMapping (value = "/salaries/save")
    public String postNewSalarie(@ModelAttribute("salarie") SalarieAideADomicile salarie) throws SalarieException {
        salarieAideADomicileService.creerSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

}
