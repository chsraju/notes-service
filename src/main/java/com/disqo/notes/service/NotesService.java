package com.disqo.notes.service;

import com.disqo.notes.model.Note;


public interface NotesService {

    public Note createNote(Note note);

    public Note updateNote(Note note);

    public Note getNoteById(Long noteId);

    public void deleteNote(Long noteId);
}
