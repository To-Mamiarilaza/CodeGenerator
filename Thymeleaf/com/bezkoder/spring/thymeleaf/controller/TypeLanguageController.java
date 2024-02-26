
package com.bezkoder.spring.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.ArrayList;
import com.bezkoder.spring.thymeleaf.entity.TypeLanguage;
import com.bezkoder.spring.thymeleaf.repository.TypeLanguageRepository;


@Controller
public class TypeLanguageController  {
    
    @Autowired
    private TypeLanguageRepository typeLanguageRepository;
    
    
    
    @GetMapping("/typeLanguages")
    public String getAll(Model model) {
        try {
            List<TypeLanguage> typeLanguages = new ArrayList<TypeLanguage>();
            typeLanguageRepository.findAll().forEach(typeLanguages::add);
            
            model.addAttribute("typeLanguages", typeLanguages);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "typeLanguage/typeLanguages";
    }
    
    @GetMapping("/typeLanguages/{idTypeLanguage}")
    public String editTypeLanguage(@PathVariable("idTypeLanguage") Integer idTypeLanguage, Model model, RedirectAttributes redirectAttributes) {
        try {
            TypeLanguage typeLanguage = typeLanguageRepository.findById(idTypeLanguage).get();
            
            model.addAttribute("typeLanguage", typeLanguage);
            model.addAttribute("pageTitle", "Edit TypeLanguage");
            
            return "typeLanguage/typeLanguage_form";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/typeLanguages";
        }
    }
    
    @GetMapping("/typeLanguages/new")
    public String addTypeLanguage(Model model) {
        TypeLanguage typeLanguage = new TypeLanguage();
        
        model.addAttribute("typeLanguage", typeLanguage);
        model.addAttribute("pageTitle", "Create new TypeLanguage");
        
        return "typeLanguage/typeLanguage_form";
    }
    
    @PostMapping("/typeLanguages/save")
    public String saveTypeLanguage(TypeLanguage typeLanguage, Model model) {
        try {
            typeLanguageRepository.save(typeLanguage);
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Update typeLanguage");
            model.addAttribute("typeLanguage", typeLanguage);
            return "typeLanguage/typeLanguage_form";
        }
        
        return "redirect:/typeLanguages";
    }
    
    @GetMapping("/typeLanguages/delete/{idTypeLanguage}")
    public String deleteTypeLanguage(@PathVariable("idTypeLanguage") Integer idTypeLanguage, Model model, RedirectAttributes redirectAttributes) {
        try {
            typeLanguageRepository.deleteById(idTypeLanguage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/typeLanguages";
    }
    
}
