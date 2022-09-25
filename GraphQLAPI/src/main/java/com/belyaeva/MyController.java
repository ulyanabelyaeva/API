package com.belyaeva;

import org.apache.hc.core5.http.ParseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class MyController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String book(){
        return "index";
    }

    @GetMapping("/getAll")
    public String getAll(Model model) throws URISyntaxException, IOException, InterruptedException, JSONException {
        model.addAttribute("books", bookService.getAllBook());
        return "response";
    }

    @PostMapping("/getByName")
    public String getByName(@RequestParam("name") String name, Model model) throws JSONException, URISyntaxException, IOException, InterruptedException {
        model.addAttribute("book", bookService.getBookByName(name));
        return "response";
    }

    @PostMapping("/addNew")
    public String addNew(@RequestParam("name") String name,
                         @RequestParam("genre") String genre,
                         @RequestParam("author") String author, Model model) throws URISyntaxException, IOException, InterruptedException, JSONException {
        bookService.addNewBook(name, genre, author);
        model.addAttribute("books", bookService.getAllBook());
        return "response";
    }
}
