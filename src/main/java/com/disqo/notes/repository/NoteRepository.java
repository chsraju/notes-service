package com.disqo.notes.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.disqo.notes.model.Note;


@Repository
public interface NoteRepository extends PagingAndSortingRepository<Note, Long>, JpaSpecificationExecutor<Note> {

}