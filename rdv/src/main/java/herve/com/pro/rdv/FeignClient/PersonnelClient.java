package herve.com.pro.rdv.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import herve.com.pro.rdv.DTOS.Personnel;

@FeignClient(name = "personnel-service", url = "${personnel.service.url}")
public interface PersonnelClient {

   @GetMapping("/rh/employe/{id}")
    Personnel getEmployeById(@PathVariable Long id);

    
    @GetMapping("/rh/employe/exists/{id}")
    boolean employeExists(@PathVariable Long id); 

}
