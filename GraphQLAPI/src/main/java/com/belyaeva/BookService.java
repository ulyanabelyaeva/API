package com.belyaeva;

import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import java.net.http.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

import org.json.*;

@Service
public class BookService {

        String baseURL = "http://127.0.0.1:1234/graphql";

    public List<Book> getAllBook() throws URISyntaxException, IOException, InterruptedException, JSONException {

        String query = "{books{id name genre author}}";
        
        List <Book> books = new ArrayList<>();

        URI uri = new URIBuilder(baseURL)
                .addParameter("query", query)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject1.getJSONArray("books");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            Book book = Book.builder()
                    .id(object.getInt("id"))
                    .name(object.getString("name"))
                    .genre(object.getString("genre"))
                    .author(object.getString("author"))
                    .build();
            books.add(book);
        }

        return books;
    }

    public Book getBookByName(String name) throws URISyntaxException, IOException, InterruptedException, JSONException {

        String query = "{book(name: \"%s\"){id name genre author}}";
        query = String.format(query, name);

        URI uri = new URIBuilder(baseURL)
                .addParameter("query", query)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONObject object = jsonObject1.getJSONObject("book");

        Book book = Book.builder()
                .id(object.getInt("id"))
                .name(object.getString("name"))
                .genre(object.getString("genre"))
                .author(object.getString("author"))
                .build();

        return book;
    }

    public void addNewBook(String name, String genre, String author) throws URISyntaxException, IOException, InterruptedException, JSONException {

        int id = (int) (Math.random()*100);
        String query = "mutation{addbook(id: %s name: \"%s\" genre: \"%s\" author: \"%s\"){id name genre author}}";
        query = String.format(query, id, name, genre, author);

        URI uri = new URIBuilder(baseURL)
                .addParameter("query", query)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(new HttpRequest.BodyPublisher() {
                    @Override
                    public long contentLength() {
                        return 0;
                    }

                    @Override
                    public void subscribe(Flow.Subscriber<? super ByteBuffer> subscriber) {
                    }
                })
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
