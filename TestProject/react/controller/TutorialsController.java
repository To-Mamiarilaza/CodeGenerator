
package react.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import react.exception.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import react.model.Tutorials;
import react.repository.TutorialsRepository;


@RestController
@RequestMapping("/tutorialss")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TutorialsController  {
    
    @Autowired
    private TutorialsRepository tutorialsRepository;
    
    
    
    @GetMapping
    public ResponseEntity<Page<Tutorials>> getAllTutorialss(@RequestParam(name = "page",defaultValue = "0") Integer page, @RequestParam(name = "size",defaultValue = "5") Integer size, HttpServletRequest request) {
        try {
            JWTToken.checkBearer(request);
            Pageable pageable = PageRequest.of(page, size);
            Page<Tutorials> tutorialss = tutorialsRepository.findAll(pageable);
            if (tutorialss.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorialss, HttpStatus.OK);
        }
        catch(NotAuthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Tutorials> getTutorialsById(@PathVariable Integer id, HttpServletRequest request) {
        try {
            JWTToken.checkBearer(request);
            } catch(NotAuthorizedException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<Tutorials> tutorialsData = tutorialsRepository.findById(id);
            
            if (tutorialsData.isPresent()) {
                return new ResponseEntity<>(tutorialsData.get(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        
        @PostMapping
        public ResponseEntity<Tutorials> createTutorials(@RequestBody Tutorials tutorials, HttpServletRequest request) {
            try {
                JWTToken.checkBearer(request);
                Tutorials _tutorials = tutorialsRepository.save(tutorials);
                return new ResponseEntity<>(_tutorials, HttpStatus.CREATED);
            }
            catch(NotAuthorizedException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            catch(Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
        @PutMapping("/{id}")
        public ResponseEntity<Tutorials> updateTutorials(@PathVariable Integer id, @RequestBody Tutorials tutorials, HttpServletRequest request) {
            try{
                JWTToken.checkBearer(request);
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
            catch(NotAuthorizedException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteTutorials(@PathVariable Integer id, HttpServletRequest request) {
            try {
                JWTToken.checkBearer(request);
                tutorialsRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            catch(NotAuthorizedException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            catch(Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
    }
