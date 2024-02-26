
package com.bezkoder.spring.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.ArrayList;
import com.bezkoder.spring.thymeleaf.entity.Fiche;
import com.bezkoder.spring.thymeleaf.repository.FicheRepository;


@Controller
public class FicheController  {
    
    @Autowired
    private FicheRepository ficheRepository;
    
    
    
    @GetMapping("/fiches")
    public String getAll(Model model) {
        try {
            List<Fiche> fiches = new ArrayList<Fiche>();
            ficheRepository.findAll().forEach(fiches::add);
            
            model.addAttribute("fiches", fiches);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "fiche/fiches";
    }
    
    @GetMapping("/fiches/{idFiche}")
    public String editFiche(@PathVariable("idFiche") Integer idFiche, Model model, RedirectAttributes redirectAttributes) {
        try {
            Fiche fiche = ficheRepository.findById(idFiche).get();
            
            model.addAttribute("fiche", fiche);
            model.addAttribute("pageTitle", "Edit Fiche");
            
            return "fiche/fiche_form";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/fiches";
        }
    }
    
    @GetMapping("/fiches/new")
    public String addFiche(Model model) {
        Fiche fiche = new Fiche();
        
        model.addAttribute("fiche", fiche);
        model.addAttribute("pageTitle", "Create new Fiche");
        
        return "fiche/fiche_form";
    }
    
    @PostMapping("/fiches/save")
    public String saveFiche(Fiche fiche, Model model) {
        try {
            ficheRepository.save(fiche);
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Update fiche");
            model.addAttribute("fiche", fiche);
            return "fiche/fiche_form";
        }
        
        return "redirect:/fiches";
    }
    
    @GetMapping("/fiches/delete/{idFiche}")
    public String deleteFiche(@PathVariable("idFiche") Integer idFiche, Model model, RedirectAttributes redirectAttributes) {
        try {
            ficheRepository.deleteById(idFiche);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/fiches";
    }
    
}
