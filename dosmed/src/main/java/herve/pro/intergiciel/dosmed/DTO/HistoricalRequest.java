package herve.pro.intergiciel.dosmed.DTO;

import lombok.Data;

import java.util.List;

@Data
public class HistoricalRequest {

    private List<String> allergy;
    private List<String> antecedent;
    private List<String> malEncours;
    private List<String> documents;
    private Long patient;

}
