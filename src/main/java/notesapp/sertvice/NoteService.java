package notesapp.sertvice;

import notesapp.dao.NoteDAO;
import notesapp.entitie.Note;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class NoteService {
    @Resource
    private NoteDAO noteDAO;

    public void saveNote(String name, String text){
        Note note = new Note();
        note.setDateWrite(LocalDate.now());
        note.setNameNote(name);
        note.setText(text);
        noteDAO.save(note);
    }

    public List<Note> getAllNotes() {
        return noteDAO.findAll();
    }

    public List<Note> getAllNotes(String filter) {
        if (filter == null || filter.isEmpty())
            return noteDAO.findAll();
        else
            return noteDAO.findByNameAndText(filter);
    }

    public List<Note> getAllNotes(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null)
            return noteDAO.findAll();
        else
            return noteDAO.findByDate(startDate, endDate);
    }

    public void update(Long id, String name, String text) {
        Note note = noteDAO.findById(id);
        note.setDateWrite(LocalDate.now());
        note.setNameNote(name);
        note.setText(text);
        noteDAO.update(note);
    }

    public void delete(Long id) {
        noteDAO.delete(id);
    }

}
