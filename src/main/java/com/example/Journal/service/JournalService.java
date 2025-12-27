package com.example.Journal.service;

import com.example.Journal.entity.Journal;
import com.example.Journal.entity.User;
import com.example.Journal.exception.JournalAlreadyExistsException;
import com.example.Journal.exception.JournalNotFoundException;
import com.example.Journal.repository.JournalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    private final JournalRepository journalRepository;
    private final UserService userService;

    public JournalService(JournalRepository journalRepository, UserService userService) {
        this.journalRepository = journalRepository;
        this.userService = userService;
    }

    public List<Journal> getAllJournal() {
        return journalRepository.findAll();
    }

    public Journal getJournalById(Long id) {
        return journalRepository.findById(id).orElseThrow(() -> new JournalNotFoundException("Journal not found with id " + id));

    }

    public Journal createJournal(Journal journal, String userName) {

        if (journal == null) {
            throw new IllegalArgumentException("Journal data must not be null");
        }

        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("Username must be provided");
        }

        User user = userService.findByUserName(userName);

        boolean exists = journalRepository
                .findByTitleAndUser(journal.getTitle(), user)
                .isPresent();

        if (exists) {
            throw new JournalAlreadyExistsException(
                    "Journal with title '" + journal.getTitle() + "' already exists for this user"
            );
        }

        journal.setUser(user);                 // set owning side
        Journal saved = journalRepository.save(journal);

        return saved;  // no need to manually add to user.getJournals()
    }


    public Journal updateJournal(Long id, Journal journal, String userName) {

        User user = userService.findByUserName(userName);

        Journal existing = journalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new JournalNotFoundException("Journal not found for this user"));

        existing.setTitle(journal.getTitle());
        existing.setContent(journal.getContent());

        return journalRepository.save(existing);
    }

    public String deleteJournal(Long id, String userName) {

        User user = userService.findByUserName(userName);

        Journal existing = journalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new JournalNotFoundException("Journal not found for this user"));

        journalRepository.delete(existing);
        return "Journal deleted successfully";
    }

}
