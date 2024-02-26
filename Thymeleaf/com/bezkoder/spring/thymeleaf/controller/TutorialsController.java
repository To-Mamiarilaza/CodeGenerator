
package com.bezkoder.spring.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.ArrayList;
import com.bezkoder.spring.thymeleaf.entity.Tutorials;
import com.bezkoder.spring.thymeleaf.repository.TutorialsRepository;


@Controller
public class TutorialsController  {
    
    @Autowired
    private TutorialsRepository tutorialsRepository;
    
    
    
    @GetMapping("/tutorialss")
    public String getAll(Model model) {
        try {
            List<Tutorials> tutorialss = new ArrayList<Tutorials>();
            tutorialsRepository.findAll().forEach(tutorialss::add);
            
            model.addAttribute("tutorialss", tutorialss);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "tutorials/tutorialss";
    }
    
    @GetMapping("/tutorialss/{id}")
    public String editTutorials(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Tutorials tutorials = tutorialsRepository.findById(id).get();
            
            model.addAttribute("tutorials", tutorials);
            model.addAttribute("pageTitle", "Edit Tutorials");
            
            return "tutorials/tutorials_form";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/tutorialss";
        }
    }
    
    @GetMapping("/tutorialss/new")
    public String addTutorials(Model model) {
        Tutorials tutorials = new Tutorials();
        
        model.addAttribute("tutorials", tutorials);
        model.addAttribute("pageTitle", "Create new Tutorials");
        
        return "tutorials/tutorials_form";
    }
    
    @PostMapping("/tutorialss/save")
    public String saveTutorials(Tutorials tutorials, Model model) {
        try {
            tutorialsRepository.save(tutorials);
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Update tutorials");
            model.addAttribute("tutorials", tutorials);
            return "tutorials/tutorials_form";
        }
        
        return "redirect:/tutorialss";
    }
    
    @GetMapping("/tutorialss/delete/{id}")
    public String deleteTutorials(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            tutorialsRepository.deleteById(id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/tutorialss";
    }
    
}
