CREATE TABLE notes
(
 id Integer NOT NULL,
 name_note varchar(255) DEFAULT NULL,
 text_note varchar(255) DEFAULT NULL,
 date_note DATE DEFAULT NULL,
 PRIMARY KEY (id)
);

CREATE SEQUENCE note_id_seq
    START 3
    INCREMENT 1
    MINVALUE 1
    OWNED BY notes.id;