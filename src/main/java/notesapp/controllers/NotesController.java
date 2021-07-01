package notesapp.controllers;

import notesapp.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.time.LocalDate;

@Controller
public class NotesController {
    @Resource
    private NoteService noteService;

    @GetMapping
    public String getNotes(Model model) {
        model.addAttribute("notes", noteService.getAllNotes());
        return "home";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam("filter") String filter) {
        model.addAttribute("notes", noteService.getAllNotes(filter));
        return "home";
    }

    @PostMapping("/filter")
    public String filter(Model model, @RequestParam("date_start") String dateStart,
                         @RequestParam("date_end") String dateEnd) {
        if (dateStart.isEmpty() && dateEnd.isEmpty())
            model.addAttribute("notes", noteService.getAllNotes());
        else
            model.addAttribute("notes", noteService.getAllNotes(LocalDate.parse(dateStart), LocalDate.parse(dateEnd)));
        return "home";
    }

    @PostMapping()
    public String update(Model model, @RequestParam("name") String name,
                                         @RequestParam("text") String text,
                                         @RequestParam("id") Long id) {

        noteService.update(id, name, text);
        model.addAttribute("notes", noteService.getAllNotes());
        return "home";
    }

    @PostMapping("/delete")
    public String delete(Model model, @RequestParam("id") Long id) {
        noteService.delete(id);
        model.addAttribute("notes", noteService.getAllNotes());
        return "home";
    }

    @GetMapping("/create")
    public String getCreateForm() {
        return "createNote";
    }

    @PostMapping("/create")
    public String createNote(Model model, @RequestParam("name") String name,
                             @RequestParam("text") String text) {
        noteService.saveNote(name, text);
        model.addAttribute("notes", noteService.getAllNotes());
        return "home";
    }
}
