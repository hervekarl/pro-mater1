package herve.com.pro.rdv.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import herve.com.pro.rdv.Modele.Rendezvous;
import herve.com.pro.rdv.Services.RdvServices;
import lombok.Data;

@RestController
@RequestMapping("/rdv")
@Data
public class RdvController {
    private final RdvServices rdvServices;

    // Afficher les rendez-vous
    @GetMapping
    public ResponseEntity<List<Rendezvous>> getRdv() {
        List<Rendezvous> rendezvous = rdvServices.getAllRendezvous();
        if (rendezvous.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rendezvous);
    }

    // creer new rendezvous
    @PostMapping
    public ResponseEntity<Rendezvous> createRdv(@RequestBody Rendezvous rendezvous) {

        return ResponseEntity.ok(rdvServices.createRendezvous(rendezvous));
    }

}
