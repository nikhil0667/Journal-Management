package com.example.Journal.service;

import com.example.Journal.entity.Journal;
import com.example.Journal.exception.JournalAlreadyExistsException;
import com.example.Journal.exception.JournalNotFoundException;
import com.example.Journal.repository.JournalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    private final JournalRepository journalRepository;

    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public List<Journal> getAllJournal() {
        return journalRepository.findAll();
    }

    public Journal getJournalById(Long id) {
        return journalRepository.findById(id).orElseThrow(() ->    new JournalNotFoundException("Journal not found with id " + id));

    }

    public Journal createJournal(Journal journal) {
Optional<Journal> newJournal = journalRepository.findByTitle(journal.getTitle());
        if (newJournal.isPresent()) {
            throw new JournalAlreadyExistsException("Journal already exists");
        }
        return journalRepository.save(journal);
    }

    public Journal updateJournal(Long id, Journal journal) {
        Journal existing = getJournalById(id);
        if (journalRepository.existsById(id)) {
            existing.setTitle(journal.getTitle());
            existing.setContent(journal.getContent());

            return journalRepository.save(existing);
        }
        throw new JournalNotFoundException("Journal not found");
    }

    public String deleteJournal(Long id) {
        if (journalRepository.existsById(id)) {
            journalRepository.deleteById(id);
            return "Journal deleted successfully";
        }
        return "Journal not found";
    }

}
