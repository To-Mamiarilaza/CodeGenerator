
package thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import thymeleaf.model.Pays;
import thymeleaf.repository.PaysRepository;


@RestController
@RequestMapping("/pays")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaysController  {
    
    @Autowired
    private PaysRepository paysRepository;
    
    
    
    @GetMapping
    public ResponseEntity<List<Pays>> getAllPayss() {
        try {
            List<Pays> payss = paysRepository.findAll();
            if (payss.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payss, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{idPays}")
    public ResponseEntity<Pays> getPaysById(@PathVariable Integer idPays) {
        Optional<Pays> paysData = paysRepository.findById(idPays);
        
        if (paysData.isPresent()) {
            return new ResponseEntity<>(paysData.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<Pays> createPays(@RequestBody Pays pays) {
        try {
            Pays _pays = paysRepository.save(pays);
            return new ResponseEntity<>(_pays, HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{idPays}")
    public ResponseEntity<Pays> updatePays(@PathVariable Integer idPays, @RequestBody Pays pays) {
        try{
            if (idPays != pays.getIdPays()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Optional<Pays> paysData = paysRepository.findById(idPays);
            if (paysData.isPresent()) {
                return new ResponseEntity<>(paysRepository.save(pays), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{idPays}")
    public ResponseEntity<Void> deletePays(@PathVariable Integer idPays) {
        try {
            paysRepository.deleteById(idPays);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
