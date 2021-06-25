package notesapp.dao;

import notesapp.entitie.Note;
import notesapp.mapper.NoteRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class NoteDAOImpl implements NoteDAO {

    NamedParameterJdbcTemplate template;

    public NoteDAOImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public void save(Note note) {
        final String sql = "insert into notes(id, name_note, text_note, date_note) values(nextval('note_id_seq'),:name_note,:text_note,:date_note)";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name_note", note.getNameNote())
                .addValue("text_note", note.getText())
                .addValue("date_note", note.getDateWrite());
        template.update(sql, param, holder);
    }

    public List<Note> findAll() {
        return template.query("select * from notes order by id", new NoteRowMapper());
    }

    public List<Note> findByNameAndText(String filter) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("filter", filter);
        return template.query("select * from notes where lower(concat(name_note, text_note)) like lower(concat('%', :filter, '%'))", param, new NoteRowMapper());
    }

    @Override
    public List<Note> findByDate(LocalDate dateFilterBeg, LocalDate dateFilterEnd) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("date_begin", dateFilterBeg)
                .addValue("date_end", dateFilterEnd);
        return template.query("select * from notes where EXTRACT(EPOCH FROM date_note) >= EXTRACT(EPOCH FROM :date_begin) and EXTRACT(EPOCH FROM date_note) <= EXTRACT(EPOCH FROM :date_end)", param, new NoteRowMapper());
    }

    @Override
    public void update(Note note) {
        final String sql = "update notes set name_note=:name_note, text_note=:text_note, date_note=:date_note where id=:id";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", note.getId())
                .addValue("name_note", note.getNameNote())
                .addValue("text_note", note.getText())
                .addValue("date_note", note.getDateWrite());
        template.update(sql,param, holder);
    }

    @Override
    public void delete(Long id) {
        final String sql = "delete from notes where id=:id";

        Map<String,Object> map=new HashMap<>();
        map.put("id", id);

        template.execute(sql,map,new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {
                return ps.executeUpdate();
            }
        });
    }

    @Override
    public Note findById(Long id) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);
        return template.query("select * from notes where id = :id", param, new NoteRowMapper()).stream().findFirst().get();
    }
}
