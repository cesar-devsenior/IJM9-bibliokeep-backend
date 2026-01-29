package com.devsenior.cdiaz.bibliokeep.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsenior.cdiaz.bibliokeep.mapper.BookMapper;
import com.devsenior.cdiaz.bibliokeep.model.dto.BookRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.BookResponseDTO;
import com.devsenior.cdiaz.bibliokeep.repository.BookRepository;
import com.devsenior.cdiaz.bibliokeep.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO, UUID userId) {
        var book = bookMapper.toEntity(bookRequestDTO);
        book.setOwnerId(userId);
        
        var savedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDTO getBookById(Long id, UUID userId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));

        if (!book.getOwnerId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para acceder a este libro");
        }

        return bookMapper.toResponseDTO(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> getAllBooksByUser(UUID userId) {
        var books = bookRepository.findByOwnerId(userId);
        return books.stream()
                .map(bookMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO, UUID userId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));

        if (!book.getOwnerId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para modificar este libro");
        }

        bookMapper.updateEntityFromDTO(bookRequestDTO, book);
        var updatedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id, UUID userId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));

        if (!book.getOwnerId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para eliminar este libro");
        }

        if (book.getIsLent()) {
            throw new RuntimeException("No se puede eliminar un libro que está prestado");
        }

        bookRepository.delete(book);
    }
}
