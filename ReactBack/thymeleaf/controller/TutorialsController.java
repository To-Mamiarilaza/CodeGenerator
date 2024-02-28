
package thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import thymeleaf.model.Tutorials;
import thymeleaf.repository.TutorialsRepository;


@RestController
@RequestMapping("/tutorials")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TutorialsController  {
    
    @Autowired
    private TutorialsRepository tutorialsRepository;
    
    
    
    @GetMapping
    public ResponseEntity<List<Tutorials>> getAllTutorialss() {
        try {
            List<Tutorials> tutorialss = tutorialsRepository.findAll();
            if (tutorialss.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorialss, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Tutorials> getTutorialsById(@PathVariable Integer id) {
        Optional<Tutorials> tutorialsData = tutorialsRepository.findById(id);
        
        if (tutorialsData.isPresent()) {
            return new ResponseEntity<>(tutorialsData.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<Tutorials> createTutorials(@RequestBody Tutorials tutorials) {
        try {
            Tutorials _tutorials = tutorialsRepository.save(tutorials);
            return new ResponseEntity<>(_tutorials, HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Tutorials> updateTutorials(@PathVariable Integer id, @RequestBody Tutorials tutorials) {
        try{
            if (id != tutorials.getId()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Optional<Tutorials> tutorialsData = tutorialsRepository.findById(id);
            if (tutorialsData.isPresent()) {
                return new ResponseEntity<>(tutorialsRepository.save(tutorials), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorials(@PathVariable Integer id) {
        try {
            tutorialsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
