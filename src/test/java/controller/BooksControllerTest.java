package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.express4j.core.Express4J.delete;
import static org.express4j.core.Express4J.get;
import static org.express4j.core.Express4J.put;

/**
 * Created by Song on 2016/3/18.
 */
public class BooksControllerTest {

    private static Map<String, Book> books = new HashMap<String, Book>();

    public static void main(String[] args) {
        final Random random = new Random();

        get("/books", (request, response) -> {
            String author = request.param("author");
            String title = request.param("title");
            Book book = new Book(author, title);
            int id = random.nextInt(Integer.MAX_VALUE);
            books.put(String.valueOf(id), book);
            response.status(201).json(id);
        });


        get("/book/{id}",(request, response) -> {
           Book book = books.get(request.pathParam("id"));
            if(book != null){
                response.json(book);
            }else {
                response.status(404).renderHtml("<h1>404</h1>");
            }
        });

        put("/book/{id}",(request, response) -> {
            String id = request.pathParam("id");
            Book book = books.get(id);
            if(book != null){
                String title = request.param("title");
                String author = request.param("author");
                if(title != null && !title.isEmpty()){
                    book.setTitle(title);
                }
                if(author != null && !author.isEmpty()){
                    book.setAuthor(author);
                }
                response.renderHtml("Book with id " + id+" updated");
            }else {
                response.status(404).renderText("404");
            }

        });


        delete("/book/{id}",(request, response) -> {
            String id = request.pathParam("id");
            Book book = books.remove(id);
            if(book != null){
                response.renderText("Book with id "+ id +" deleted !");
            }else {
                response.status(404).renderText("404");
            }
        });
    }

    public static class Book {

        public String author, title;

        public Book(String author, String title) {
            this.author = author;
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

