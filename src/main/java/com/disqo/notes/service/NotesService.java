package com.disqo.notes.service;

import java.util.List;

import com.disqo.notes.model.Note;


public interface NotesService {

    public Note createNote(Note note);

    public Note updateNote(Note note);

    public Note getNoteById(Long noteId);

    public void deleteNote(Long noteId);

    public List<Note> getNotes();
}
