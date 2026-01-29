package com.devsenior.cdiaz.bibliokeep.repository;

import com.devsenior.cdiaz.bibliokeep.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    long countByOwnerId(UUID ownerId);
    
    List<Book> findByOwnerId(UUID ownerId);
}
