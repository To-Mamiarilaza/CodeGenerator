
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
import react.model.Utilisateur;
import react.repository.UtilisateurRepository;


@RestController
@RequestMapping("/utilisateurs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UtilisateurController  {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    
    
    @GetMapping
    public ResponseEntity<Page<Utilisateur>> getAllUtilisateurs(@RequestParam(name = "page",defaultValue = "0") Integer page, @RequestParam(name = "size",defaultValue = "5") Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Utilisateur> utilisateurs = utilisateurRepository.findAll(pageable);
            if (utilisateurs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{idUtilisateur}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Integer idUtilisateur) {
        Optional<Utilisateur> utilisateurData = utilisateurRepository.findById(idUtilisateur);
        
        if (utilisateurData.isPresent()) {
            return new ResponseEntity<>(utilisateurData.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur _utilisateur = utilisateurRepository.save(utilisateur);
            return new ResponseEntity<>(_utilisateur, HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{idUtilisateur}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Integer idUtilisateur, @RequestBody Utilisateur utilisateur) {
        try{
            if (idUtilisateur != utilisateur.getIdUtilisateur()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Optional<Utilisateur> utilisateurData = utilisateurRepository.findById(idUtilisateur);
            if (utilisateurData.isPresent()) {
                return new ResponseEntity<>(utilisateurRepository.save(utilisateur), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{idUtilisateur}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Integer idUtilisateur) {
        try {
            utilisateurRepository.deleteById(idUtilisateur);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
