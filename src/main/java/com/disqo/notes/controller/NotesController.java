package com.disqo.notes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.disqo.notes.model.Note;
import com.disqo.notes.service.NotesService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Validated
@RequestMapping("/")
@Slf4j
public class NotesController {

    @Autowired
    private NotesService noteService;

    @PostMapping
    public ResponseEntity<?> createNote(@Valid @RequestBody Note note) {
        Note newNote = noteService.createNote(note);
        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{noteId}")
                .buildAndExpand(newNote.getNoteId()).toUri());
        log.info("Created Note: {}", newNote);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    @GetMapping("/{noteId}")
    public ResponseEntity<?> retrieveNote(@PathVariable Long noteId) {
        log.info("Retrieving note with id: {}", noteId);
        return new ResponseEntity<>(noteService.getNoteById(noteId), HttpStatus.OK);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable Long noteId, @Valid @RequestBody Note note) {
        log.info("Updating Note ID {} with Note data {}", noteId, note);

        if (!noteId.equals(note.getNoteId())) {
            String message = String.format("Id from URL [%s] doesn't match id from the entity [%s]",
                            noteId, note.getNoteId());
            throw new ResourceNotFoundException(message);
        }

        Note updateNote = noteService.updateNote(note);
        return new ResponseEntity<>(updateNote, HttpStatus.OK);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<?> deleteActity(@PathVariable Long noteId) {
        log.info("Deleting Note with id: {}", noteId);

        noteService.deleteNote(noteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
