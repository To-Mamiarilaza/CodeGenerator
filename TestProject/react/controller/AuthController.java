
package react.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import react.model.Utilisateur;
import react.repository.UtilisateurRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur utilisateurConnected  = utilisateurRepository.findByUsernameAndPassword(utilisateur.getUsername(), utilisateur.getPassword());
            String token = JWTToken.create(utilisateurConnected.getIdUtilisateur());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Utilisateur utilisateur) {
        try {
            utilisateurRepository.save(utilisateur);
            return new ResponseEntity<>("tokens", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}