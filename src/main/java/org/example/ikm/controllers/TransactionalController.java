package org.example.ikm.controllers;


import org.example.ikm.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionalController {

    @Autowired
    private TestService testService;


    @PostMapping("/savepoint")
    public ResponseEntity<String> executeWithSavepoint() {
        try {
            testService.executeWithSavepoint();
            return ResponseEntity.ok("Транзакция с точкой сохранения выполнена");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @PostMapping("/commit")
    public ResponseEntity<String> executeSuccessfulTransaction() {
        try {
            testService.executeSuccessfulTransaction();
            return ResponseEntity.ok("Транзакция успешно завершена");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @PostMapping("/rollback")
    public ResponseEntity<String> executeWithRollback() {
        try {
            testService.executeWithRollback();
            return ResponseEntity.ok("Транзакция выполнена");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
}