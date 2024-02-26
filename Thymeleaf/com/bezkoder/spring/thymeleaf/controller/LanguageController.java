
package com.bezkoder.spring.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.ArrayList;
import com.bezkoder.spring.thymeleaf.entity.Language;
import com.bezkoder.spring.thymeleaf.repository.LanguageRepository;
import com.bezkoder.spring.thymeleaf.repository.TypeLanguageRepository;
import com.bezkoder.spring.thymeleaf.repository.PaysRepository;


@Controller
public class LanguageController  {
    
    @Autowired
    private LanguageRepository languageRepository;
    
    @Autowired
    private TypeLanguageRepository typeLanguageRepository;
    
    @Autowired
    private PaysRepository paysRepository;
    
    
    
    @GetMapping("/languages")
    public String getAll(Model model) {
        try {
            List<Language> languages = new ArrayList<Language>();
            languageRepository.findAll().forEach(languages::add);
            
            model.addAttribute("languages", languages);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "language/languages";
    }
    
    @GetMapping("/languages/{idLanguage}")
    public String editLanguage(@PathVariable("idLanguage") Integer idLanguage, Model model, RedirectAttributes redirectAttributes) {
        try {
            Language language = languageRepository.findById(idLanguage).get();
            
            model.addAttribute("typeLanguages", typeLanguageRepository.findAll());
            model.addAttribute("payss", paysRepository.findAll());
            model.addAttribute("language", language);
            model.addAttribute("pageTitle", "Edit Language");
            
            return "language/language_form";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/languages";
        }
    }
    
    @GetMapping("/languages/new")
    public String addLanguage(Model model) {
        Language language = new Language();
        
        model.addAttribute("typeLanguages", typeLanguageRepository.findAll());
        model.addAttribute("payss", paysRepository.findAll());
        model.addAttribute("language", language);
        model.addAttribute("pageTitle", "Create new Language");
        
        return "language/language_form";
    }
    
    @PostMapping("/languages/save")
    public String saveLanguage(Language language, Model model) {
        try {
            languageRepository.save(language);
        }
        catch (Exception e) {
            model.addAttribute("typeLanguages", typeLanguageRepository.findAll());
            model.addAttribute("payss", paysRepository.findAll());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Update language");
            model.addAttribute("language", language);
            return "language/language_form";
        }
        
        return "redirect:/languages";
    }
    
    @GetMapping("/languages/delete/{idLanguage}")
    public String deleteLanguage(@PathVariable("idLanguage") Integer idLanguage, Model model, RedirectAttributes redirectAttributes) {
        try {
            languageRepository.deleteById(idLanguage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/languages";
    }
    
}
