package servlets;

import entities.Libro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.DAOGeneric;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/books")
public class BookServlet extends HttpServlet {
    private final DAOGeneric<Libro, String> dao = new DAOGeneric<>(Libro.class, String.class);
    private JSONObject json = new JSONObject();

    private PrintWriter out = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "get-specific":
                    String id = request.getParameter("isbn");

                    if (id != null && !id.isBlank()) {
                        json = getBookByID(id);
                    } else {
                        json.put("Error", "ISBN is invalid" );
                    }
                    break;

                case "get-all":
                    json = getAllBooks();
                    break;
            }

            out.println(json.toString());
        } else {
            out.println("<h1>ERROR: No action found<    /h1>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if (action != null) {
            String id, title, author;

            switch (action) {
                case "add-book":
                    id = request.getParameter("isbn");
                    title = request.getParameter("title");
                    author = request.getParameter("author");

                    if (id == null || id.isBlank() || title == null || title.isBlank() || author == null || author.isBlank()) {
                        out.println("<h3>ERROR: Please fill every field</h3>");
                    } else {
                        Libro newBook = new Libro();
                        newBook.setIsbn(id);
                        newBook.setTitulo(title);
                        newBook.setAutor(author);

                        dao.add(newBook);

                        out.println("<h3>Success: A new book has been added</h3>");
                    }
                    break;

                case "update-book":
                    id = request.getParameter("isbn");
                    title = request.getParameter("title");
                    author = request.getParameter("author");

                    if (id == null || id.isBlank() || title == null || title.isBlank() || author == null || author.isBlank()) {
                        out.println("<h3>ERROR: Please fill every field</h3>");
                    } else {
                        Libro updatedBook = new Libro();
                        updatedBook.setIsbn(id);
                        updatedBook.setTitulo(title);
                        updatedBook.setAutor(author);

                        dao.update(updatedBook);

                        out.println("<h3>Success: The book has been updated</h3>");
                    }
                    break;

                case "delete-book":
                    id = request.getParameter("isbn");

                    if (id == null || id.isBlank()) {
                        out.println("<h3>ERROR: ISBN is required</h3>");
                    } else {
                        Libro deletedBook = dao.getById(id);
                        if (deletedBook != null) {
                            dao.delete(deletedBook);
                            out.println("<h3>Success: The book has been deleted</h3>");
                        } else {
                            out.println("<h3>ERROR: Book not found</h3>");
                        }
                    }
                    break;

                default:
                    out.println("<h3>ERROR: Invalid action</h3>");
            }
        } else {
            out.println("<h3>ERROR: No action found</h3>");
        }
    }


    private JSONObject getBookByID(String id) {
        JSONObject bookJSON = new JSONObject();
        Libro book = dao.getById(id);

        if (book != null && book.getIsbn() != null) {
            bookJSON.put("isbn", book.getIsbn());
            bookJSON.put("title", book.getTitulo());
            bookJSON.put("author", book.getAutor());
        } else {
            bookJSON.put("Error", "Book not found/does not exist" );
        }
        return bookJSON;
    }

    private JSONObject getAllBooks() {
        JSONObject bookJSON = new JSONObject();
        JSONArray bookJSONArray = new JSONArray();

        List<Libro> books = dao.getAll();

        if (books != null && !books.isEmpty()) {
            for (Libro book : books) {
                JSONObject bookJSONObject = new JSONObject();
                bookJSONObject.put("isbn", book.getIsbn());
                bookJSONObject.put("title", book.getTitulo());
                bookJSONObject.put("author", book.getAutor());

                bookJSONArray.put(bookJSONObject);
            }

            bookJSON.put("books", bookJSONArray);
        } else {
            bookJSON.put("Error", "No books found" );
        }

        return bookJSON;
    }
}
