package com.disqo.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.disqo.notes.model.Note;
import com.disqo.notes.repository.NoteRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class NotesServiceImpl implements NotesService {

	@Autowired
    private NoteRepository noteRepository;

    @Override
    public Note createNote(Note note) {
        noteRepository.save(note);
        return note;
    }

    @Override
    public Note updateNote(Note note) {
    	log.info(" Updating the Notes for the id: {}", note.getNoteId());
        Note currentNote = noteRepository.findById(note.getNoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Note is not found for noteId: "+ note.getNoteId()));
        if (currentNote != null) {
            currentNote = noteRepository.save(note);
        }
        return currentNote;
    }

    @Override
    public Note getNoteById(Long noteId) {
    	log.info(" Retriving the Notes for the id: {}", noteId);
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note is not found for noteId: "+noteId));
        return note;

    }

    @Override
    public void deleteNote(Long noteId) {
    	log.info(" Deleting the Notes for the id: {}", noteId);
        Note note = noteRepository.findById((noteId))
                .orElseThrow(() -> new ResourceNotFoundException("Note is not found for noteId: "+ noteId));
        if (note != null) {
            noteRepository.delete(note);
        }
    }
}
