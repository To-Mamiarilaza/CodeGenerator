
package thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import thymeleaf.model.Fiche;
import thymeleaf.repository.FicheRepository;


@RestController
@RequestMapping("/fiches")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FicheController  {
    
    @Autowired
    private FicheRepository ficheRepository;
    
    
    
    @GetMapping
    public ResponseEntity<Page<Fiche>> getAllFiches(@RequestParam(name = "page",defaultValue = "0") Integer page, @RequestParam(name = "size",defaultValue = "5") Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Fiche> fiches = ficheRepository.findAll(pageable);
            if (fiches.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(fiches, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{idFiche}")
    public ResponseEntity<Fiche> getFicheById(@PathVariable Integer idFiche) {
        Optional<Fiche> ficheData = ficheRepository.findById(idFiche);
        
        if (ficheData.isPresent()) {
            return new ResponseEntity<>(ficheData.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<Fiche> createFiche(@RequestBody Fiche fiche) {
        try {
            Fiche _fiche = ficheRepository.save(fiche);
            return new ResponseEntity<>(_fiche, HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{idFiche}")
    public ResponseEntity<Fiche> updateFiche(@PathVariable Integer idFiche, @RequestBody Fiche fiche) {
        try{
            if (idFiche != fiche.getIdFiche()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Optional<Fiche> ficheData = ficheRepository.findById(idFiche);
            if (ficheData.isPresent()) {
                return new ResponseEntity<>(ficheRepository.save(fiche), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{idFiche}")
    public ResponseEntity<Void> deleteFiche(@PathVariable Integer idFiche) {
        try {
            ficheRepository.deleteById(idFiche);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
