package notesapp.dao;

import notesapp.entitie.Note;

import java.time.LocalDate;
import java.util.List;

public interface NoteDAO {
    void save(Note note);

    List<Note> findAll();

    List<Note> findByNameAndText(String filter);

    List<Note> findByDate(LocalDate dateFilterBegin, LocalDate dateFilterEnd);

    void update(Note note);

    void delete(Long id);

    Note findById(Long id);
}
