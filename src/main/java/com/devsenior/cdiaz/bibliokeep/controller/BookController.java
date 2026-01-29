package com.devsenior.cdiaz.bibliokeep.controller;

import com.devsenior.cdiaz.bibliokeep.model.dto.BookRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.BookResponseDTO;
import com.devsenior.cdiaz.bibliokeep.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDTO createBook(
            @Valid @RequestBody BookRequestDTO bookRequestDTO,
            @RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        return bookService.createBook(bookRequestDTO, userId);
    }

    @GetMapping("/{id}")
    public BookResponseDTO getBookById(
            @PathVariable Long id,
            @RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        return bookService.getBookById(id, userId);
    }

    @GetMapping
    public List<BookResponseDTO> getAllBooks(@RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        return bookService.getAllBooksByUser(userId);
    }

    @PutMapping("/{id}")
    public BookResponseDTO updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDTO bookRequestDTO,
            @RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        return bookService.updateBook(id, bookRequestDTO, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(
            @PathVariable Long id,
            @RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        bookService.deleteBook(id, userId);
    }
}
