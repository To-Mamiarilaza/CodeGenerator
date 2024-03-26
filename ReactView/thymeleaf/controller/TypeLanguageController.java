
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
import thymeleaf.model.TypeLanguage;
import thymeleaf.repository.TypeLanguageRepository;


@RestController
@RequestMapping("/typeLanguages")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TypeLanguageController  {
    
    @Autowired
    private TypeLanguageRepository typeLanguageRepository;
    
    
    
    @GetMapping
    public ResponseEntity<Page<TypeLanguage>> getAllTypeLanguages(@RequestParam(name = "page",defaultValue = "0") Integer page, @RequestParam(name = "size",defaultValue = "5") Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TypeLanguage> typeLanguages = typeLanguageRepository.findAll(pageable);
            if (typeLanguages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(typeLanguages, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{idTypeLanguage}")
    public ResponseEntity<TypeLanguage> getTypeLanguageById(@PathVariable Integer idTypeLanguage) {
        Optional<TypeLanguage> typeLanguageData = typeLanguageRepository.findById(idTypeLanguage);
        
        if (typeLanguageData.isPresent()) {
            return new ResponseEntity<>(typeLanguageData.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<TypeLanguage> createTypeLanguage(@RequestBody TypeLanguage typeLanguage) {
        try {
            TypeLanguage _typeLanguage = typeLanguageRepository.save(typeLanguage);
            return new ResponseEntity<>(_typeLanguage, HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{idTypeLanguage}")
    public ResponseEntity<TypeLanguage> updateTypeLanguage(@PathVariable Integer idTypeLanguage, @RequestBody TypeLanguage typeLanguage) {
        try{
            if (idTypeLanguage != typeLanguage.getIdTypeLanguage()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Optional<TypeLanguage> typeLanguageData = typeLanguageRepository.findById(idTypeLanguage);
            if (typeLanguageData.isPresent()) {
                return new ResponseEntity<>(typeLanguageRepository.save(typeLanguage), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{idTypeLanguage}")
    public ResponseEntity<Void> deleteTypeLanguage(@PathVariable Integer idTypeLanguage) {
        try {
            typeLanguageRepository.deleteById(idTypeLanguage);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
