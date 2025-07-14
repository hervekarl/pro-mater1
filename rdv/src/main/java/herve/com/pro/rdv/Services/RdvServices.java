package herve.com.pro.rdv.Services;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import herve.com.pro.rdv.DTOS.Patient;
import herve.com.pro.rdv.DTOS.Personnel;
import herve.com.pro.rdv.Exceptions.EmailException;
import herve.com.pro.rdv.FeignClient.PatientClient;
import herve.com.pro.rdv.FeignClient.PersonnelClient;
import herve.com.pro.rdv.Modele.Rendezvous;
import herve.com.pro.rdv.Repository.RdvRepository;
import jakarta.mail.internet.MimeMessage;

@Service
public class RdvServices {

    private final RdvRepository rdvRepository;
    private final PatientClient patientClient;
    private final PersonnelClient personnelClient;
    private final JavaMailSender mailSender;

    public RdvServices(RdvRepository rdvRepository, PatientClient patientClient, PersonnelClient personnelClient,
            JavaMailSender mailSender) {
        this.rdvRepository = rdvRepository;
        this.patientClient = patientClient;
        this.personnelClient = personnelClient;
        this.mailSender = mailSender;
    }

    public List<Rendezvous> getAllRendezvous() {
        return rdvRepository.findAll();
    }

    public Rendezvous createRendezvous(Rendezvous rendezvous) {
        // Logic to create a rendezvous
        Patient patient = patientClient.searchPatientById(rendezvous.getPatient());
        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }
        Personnel personnel = personnelClient.getEmployeById(rendezvous.getPersonnel());
        if (personnel == null) {
            throw new RuntimeException("Personnel not found");
        }
        Rendezvous savedRdv = rdvRepository.save(rendezvous);

        sendEmailToPatient(patient, personnel, savedRdv);
        sendEmailToPersonnel(patient, personnel, savedRdv);

        return savedRdv;
    }

    public void sendEmailToPatient(Patient patient, Personnel personnel, Rendezvous rdv) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(patient.getEmail());
            helper.setSubject("Rendez-vous entre " + patient.getName() + " et " + personnel.getNom());

            String content = String.format(
                    "<h3>Bonjour %s,</h3>" +
                            "<p>Votre rendez-vous avec <strong>%s</strong> est confirmé :</p>" +
                            "<ul>" +
                            "<li>Date: %s</li>" +
                            "<li>Lieu: %s</li>" +
                            "<li>Motif: %s</li>" +
                            "</ul>",
                    patient.getName(),
                    personnel.getNom(),
                    rdv.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    rdv.getLieu(),
                    rdv.getMotif());

            helper.setText(content, true); // true = HTML
            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("Échec d'envoi d'email");
        }
    }

    private void sendEmailToPersonnel(Patient patient, Personnel personnel, Rendezvous rdv) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(personnel.getEmail());
            helper.setSubject("Nouveau rendez-vous avec " + patient.getName());

            String content = String.format(
                    "<h3>Bonjour %s,</h3>" +
                            "<p>Vous avez un nouveau rendez-vous avec <strong>%s</strong> :</p>" +
                            "<ul>" +
                            "<li><strong>Date :</strong> %s</li>" +
                            "<li><strong>Lieu :</strong> %s</li>" +
                            "<li><strong>Motif :</strong> %s</li>" +
                            "<li><strong>Contact patient :</strong> %s</li>" +
                            "</ul>" +
                            "<p>Cordialement,<br>Votre équipe médicale</p>",
                    personnel.getNom(),
                    patient.getName(),
                    rdv.getDateTime()
                            .format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy 'à' HH'h'mm", Locale.FRENCH)),
                    rdv.getLieu(),
                    rdv.getMotif(),
                    patient.getEmail() // ou patient.getPhone() si disponible
            );

            helper.setText(content, true); // true pour indiquer que c'est du HTML
            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("Échec d'envoi de la confirmation au personnel");
        }
    }

}
