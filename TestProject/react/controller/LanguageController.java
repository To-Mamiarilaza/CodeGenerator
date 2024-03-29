
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
import react.model.Language;
import react.repository.LanguageRepository;
import react.repository.TypeLanguageRepository;
import react.repository.PaysRepository;


@RestController
@RequestMapping("/languages")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LanguageController  {
    
    @Autowired
    private LanguageRepository languageRepository;
    
    @Autowired
    private TypeLanguageRepository typeLanguageRepository;
    
    @Autowired
    private PaysRepository paysRepository;
    
    
    
    @GetMapping
    public ResponseEntity<Page<Language>> getAllLanguages(@RequestParam(name = "page",defaultValue = "0") Integer page, @RequestParam(name = "size",defaultValue = "5") Integer size, HttpServletRequest request) {
        try {
            JWTToken.checkBearer(request);
            Pageable pageable = PageRequest.of(page, size);
            Page<Language> languages = languageRepository.findAll(pageable);
            if (languages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(languages, HttpStatus.OK);
        }
        catch(NotAuthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{idLanguage}")
    public ResponseEntity<Language> getLanguageById(@PathVariable Integer idLanguage, HttpServletRequest request) {
        try {
            JWTToken.checkBearer(request);
            } catch(NotAuthorizedException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<Language> languageData = languageRepository.findById(idLanguage);
            
            if (languageData.isPresent()) {
                return new ResponseEntity<>(languageData.get(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        
        @PostMapping
        public ResponseEntity<Language> createLanguage(@RequestBody Language language, HttpServletRequest request) {
            try {
                JWTToken.checkBearer(request);
                Language _language = languageRepository.save(language);
                return new ResponseEntity<>(_language, HttpStatus.CREATED);
            }
            catch(NotAuthorizedException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            catch(Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
        @PutMapping("/{idLanguage}")
        public ResponseEntity<Language> updateLanguage(@PathVariable Integer idLanguage, @RequestBody Language language, HttpServletRequest request) {
            try{
                JWTToken.checkBearer(request);
                if (idLanguage != language.getIdLanguage()) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
                Optional<Language> languageData = languageRepository.findById(idLanguage);
                if (languageData.isPresent()) {
                    return new ResponseEntity<>(languageRepository.save(language), HttpStatus.OK);
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
        
        @DeleteMapping("/{idLanguage}")
        public ResponseEntity<Void> deleteLanguage(@PathVariable Integer idLanguage, HttpServletRequest request) {
            try {
                JWTToken.checkBearer(request);
                languageRepository.deleteById(idLanguage);
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
