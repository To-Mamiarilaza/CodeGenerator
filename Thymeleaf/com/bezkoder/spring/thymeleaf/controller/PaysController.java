
package com.bezkoder.spring.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.ArrayList;
import com.bezkoder.spring.thymeleaf.entity.Pays;
import com.bezkoder.spring.thymeleaf.repository.PaysRepository;


@Controller
public class PaysController  {
    
    @Autowired
    private PaysRepository paysRepository;
    
    
    
    @GetMapping("/payss")
    public String getAll(Model model) {
        try {
            List<Pays> payss = new ArrayList<Pays>();
            paysRepository.findAll().forEach(payss::add);
            
            model.addAttribute("payss", payss);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "pays/payss";
    }
    
    @GetMapping("/payss/{idPays}")
    public String editPays(@PathVariable("idPays") Integer idPays, Model model, RedirectAttributes redirectAttributes) {
        try {
            Pays pays = paysRepository.findById(idPays).get();
            
            model.addAttribute("pays", pays);
            model.addAttribute("pageTitle", "Edit Pays");
            
            return "pays/pays_form";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/payss";
        }
    }
    
    @GetMapping("/payss/new")
    public String addPays(Model model) {
        Pays pays = new Pays();
        
        model.addAttribute("pays", pays);
        model.addAttribute("pageTitle", "Create new Pays");
        
        return "pays/pays_form";
    }
    
    @PostMapping("/payss/save")
    public String savePays(Pays pays, Model model) {
        try {
            paysRepository.save(pays);
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Update pays");
            model.addAttribute("pays", pays);
            return "pays/pays_form";
        }
        
        return "redirect:/payss";
    }
    
    @GetMapping("/payss/delete/{idPays}")
    public String deletePays(@PathVariable("idPays") Integer idPays, Model model, RedirectAttributes redirectAttributes) {
        try {
            paysRepository.deleteById(idPays);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/payss";
    }
    
}
