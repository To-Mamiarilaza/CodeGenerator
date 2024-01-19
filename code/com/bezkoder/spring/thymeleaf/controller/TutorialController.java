package com.bezkoder.spring.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.ArrayList;
import com.bezkoder.spring.thymeleaf.entity.Tutorial;
import com.bezkoder.spring.thymeleaf.repository.TutorialRepository;

@Controller
public class TutorialController  {
    
    @Autowired
    private TutorialRepository tutorialRepository;
    
    
    @GetMapping("/tutorials")
    public String getAll(Model model) {
        try {
            List<Tutorial> tutorials = new ArrayList<Tutorial>();
            tutorialRepository.findAll().forEach(tutorials::add);
            
            model.addAttribute("tutorials", tutorials);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "tutorials";
    }
    
    @GetMapping("/tutorials/{id}")
    public String editTutorial(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Tutorial tutorial = tutorialRepository.findById(id).get();
            
            model.addAttribute("tutorial", tutorial);
            model.addAttribute("pageTitle", "Edit Tutorial");
            
            return "tutorial_form";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/tutorials";
        }
    }
    
    @GetMapping("/tutorials/new")
    public String addTutorial(Model model) {
        Tutorial tutorial = new Tutorial();
        
        model.addAttribute("tutorial", tutorial);
        model.addAttribute("pageTitle", "Create new Tutorial");
        
        return "tutorial_form";
    }
    
    @PostMapping("/tutorials/save")
    public String saveTutorial(Tutorial tutorial, RedirectAttributes redirectAttributes) {
        try {
            tutorialRepository.save(tutorial);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/tutorials";
    }
    
    @GetMapping("/tutorials/delete/{id}")
    public String deleteTutorial(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            tutorialRepository.deleteById(id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/tutorials";
    }
    
}
