package com.devsenior.cdiaz.bibliokeep.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsenior.cdiaz.bibliokeep.mapper.BookMapper;
import com.devsenior.cdiaz.bibliokeep.model.dto.BookRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.BookResponseDTO;
import com.devsenior.cdiaz.bibliokeep.model.vo.JwtUser;
import com.devsenior.cdiaz.bibliokeep.repository.BookRepository;
import com.devsenior.cdiaz.bibliokeep.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
        var book = bookMapper.toEntity(bookRequestDTO);
        book.setOwnerId(getUserId());

        var savedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDTO getBookById(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));

        if (!book.getOwnerId().equals(getUserId())) {
            throw new RuntimeException("No tienes permiso para acceder a este libro");
        }

        return bookMapper.toResponseDTO(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> getAllBooksByUser() {
        var books = bookRepository.findByOwnerId(getUserId());
        return books.stream()
                .map(bookMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));

        if (!book.getOwnerId().equals(getUserId())) {
            throw new RuntimeException("No tienes permiso para modificar este libro");
        }

        bookMapper.updateEntityFromDTO(bookRequestDTO, book);
        var updatedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));

        if (!book.getOwnerId().equals(getUserId())) {
            throw new RuntimeException("No tienes permiso para eliminar este libro");
        }

        if (book.getIsLent()) {
            throw new RuntimeException("No se puede eliminar un libro que está prestado");
        }

        bookRepository.delete(book);
    }

    private UUID getUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof JwtUser jwt) {
            return UUID.fromString(jwt.getUserId());
        }

        return null;
    }
}
