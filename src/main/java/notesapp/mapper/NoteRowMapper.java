package notesapp.mapper;

import notesapp.entity.Note;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteRowMapper implements RowMapper<Note> {

    @Override
    public Note mapRow(ResultSet rs, int arg1) throws SQLException {
        Note note = new Note();
        note.setId(rs.getLong("id"));
        note.setNameNote(rs.getString("name_note"));
        note.setText(rs.getString("text_note"));
        note.setDateWrite(rs.getDate("date_note").toLocalDate());
        return note;
    }
}
