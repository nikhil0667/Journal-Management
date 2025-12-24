package com.example.Journal.controller;

import com.example.Journal.Response.ApiResponse;
import com.example.Journal.entity.Journal;
import com.example.Journal.service.JournalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")

public class JournalController {
    private final JournalService journalService;

    JournalController(JournalService journalService) {
        this.journalService = journalService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Journal>>> getAll() {

        return  ResponseEntity.status(200).body(new ApiResponse<>(true, "Request Successfully", journalService.getAllJournal()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Journal>> addJournal(
           @Valid @RequestBody Journal journal) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Journal Created", journalService.createJournal(journal)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Journal>> updateJournal(
    @PathVariable Long id,
    @Valid   @RequestBody Journal journal) {

        Journal updated = journalService.updateJournal(id, journal);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Journal Updated", updated)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteJournal(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Journal Deleted", journalService.deleteJournal(id)));

    }
}
