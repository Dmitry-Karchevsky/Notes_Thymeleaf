package notesapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.time.LocalDate;

@Table(name = "notes")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Note {

    private Long id;
    @Column(name = "name_note")
    private String nameNote;
    @Column(name = "text_note", length = 20000)
    private String text;
    @Column(name = "date_note")
    private LocalDate dateWrite;
}
