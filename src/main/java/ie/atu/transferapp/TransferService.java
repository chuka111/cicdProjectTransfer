package ie.atu.transferapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
class TransferService {

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private NotificationClient notificationClient;

    @PostMapping("/funds")
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequest transferRequest) {
        String fromAccount = transferRequest.getFromAccount();
        String toAccount = transferRequest.getToAccount();
        Double amount = transferRequest.getAmount();
        Double fromBalance = accountClient.getBalance(fromAccount);
        if (fromBalance == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sender account not found");
        }
        if (fromBalance < amount) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds");
        }
        accountClient.updateBalance(fromAccount, - amount);
        try {
            accountClient.updateBalance(toAccount, amount);
        } catch (Exception e) {
            accountClient.updateBalance(fromAccount, amount);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Receiver account not found");
        }

        String notificationMessage = "Transfer of " + amount + " from " + fromAccount + " to " + toAccount + " completed successfully.";
        notificationClient.sendNotification(notificationMessage);

        return ResponseEntity.ok("Transfer successful");
    }

}
