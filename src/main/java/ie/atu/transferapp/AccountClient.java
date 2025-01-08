package ie.atu.transferapp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service", url = "http://localhost:8081/account")
interface AccountClient {

    @GetMapping("/balance/{accountId}")
    Double getBalance(@PathVariable String accountId);

    @PostMapping("/update")
    void updateBalance(@RequestParam String accountId, @RequestParam Double amount);
}
