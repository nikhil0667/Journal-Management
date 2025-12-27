package com.example.Journal.controller;

import com.example.Journal.Response.ApiResponse;
import com.example.Journal.entity.Journal;
import com.example.Journal.entity.User;
import com.example.Journal.service.JournalService;
import com.example.Journal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")

public class JournalController {
    private final JournalService journalService;
    private final UserService userService;

    JournalController(JournalService journalService, UserService userService) {
        this.journalService = journalService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Journal>>> GetAll() {


        return ResponseEntity.status(200).body(new ApiResponse<>(true, "Request Successfully", journalService.getAllJournal()));


    }


    @GetMapping("/{userName}")
    public ResponseEntity<ApiResponse<List<Journal>>> getAllJournalsEntriesUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<Journal> all = user.getJournals();
        if (all != null && !all.isEmpty()) {
            return ResponseEntity.status(200).body(new ApiResponse<>(true, "Request Successfully", all));

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(true, "Journal Not Found", all));
    }

    @PostMapping("/{userName}")
    public ResponseEntity<ApiResponse<Journal>> addJournal(
            @Valid @RequestBody Journal journal, @PathVariable String userName) {
        try {

            return ResponseEntity.ok(new ApiResponse<>(true, "Journal Created", journalService.createJournal(journal, userName)));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false, e.getMessage(), journal), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userName}/{id}")
    public ResponseEntity<ApiResponse<Journal>> updateJournal(
            @PathVariable String userName,
            @PathVariable Long id,
            @Valid @RequestBody Journal journal) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Journal Updated", journalService.updateJournal(id, journal, userName))
        );
    }


    @DeleteMapping("/{userName}/{id}")
    public ResponseEntity<ApiResponse<String>> deleteJournal(    @PathVariable String userName,
                                                                 @PathVariable Long id,
                                                                 @Valid @RequestBody Journal journal) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Journal Deleted", journalService.deleteJournal(id,userName)));

    }
}
