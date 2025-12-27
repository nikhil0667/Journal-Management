package com.example.Journal.repository;

import com.example.Journal.entity.Journal;
import com.example.Journal.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    Optional<Journal> findByTitle(String title);

    Optional<Journal> findByTitleAndUser(@NotBlank(message = "Title is required") @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters") String title, User user);

    Optional<Journal> findByIdAndUser(Long id, User user);
}
