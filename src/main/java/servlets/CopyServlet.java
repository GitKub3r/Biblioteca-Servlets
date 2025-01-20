package servlets;

import entities.Ejemplar;
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

@WebServlet(name = "CopySerlvet", value = "/copies")
public class CopyServlet extends HttpServlet {
    private final DAOGeneric<Ejemplar, Integer> dao = new DAOGeneric<>(Ejemplar.class, Integer.class);
    private final DAOGeneric<Libro, String> daoBook = new DAOGeneric<>(Libro.class, String.class);

    private JSONObject json = new JSONObject();

    private PrintWriter out = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "get-specific":
                    Integer id = Integer.parseInt(request.getParameter("id"));

                    if (id != null) {
                        json = getCopyByID(id);
                    } else {
                        json.put("Error", "ID is invalid" );
                    }
                    break;

                case "get-all":
                    json = getAllCopies();
                    break;

                default:
                    out.println("<h3>ERROR: Invalid action</h3>");
            }

            out.println(json.toString());
        } else {
            out.println("<h3>ERROR: No action found</h3>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        String action = request.getParameter("action");

        if (action != null) {
            Integer id;
            String state;

            switch (action) {
                case "add-copy":
                    id = Integer.parseInt(request.getParameter("isbn"));
                    state = request.getParameter("state");

                    if (id == null || state == null || state.isBlank()) {
                        out.println("<h3>ERROR: Please fill every field</h3>");
                    } else {
                        Ejemplar newCopy = new Ejemplar();
                        Libro book = daoBook.getById(id.toString());

                        newCopy.setIsbn(book);
                        newCopy.setEstado(state);

                        dao.add(newCopy);

                        out.println("<h3>Success: A new copy has been added</h3>");
                    }
                    break;

                case "update-copy":
                    id = Integer.parseInt(request.getParameter("id"));
                    state = request.getParameter("state");

                    if (id == null || state == null || state.isBlank()) {
                        out.println("<h3>ERROR: Please fill every field</h3>");
                    } else {
                        Ejemplar newCopy = dao.getById(id);

                        newCopy.setEstado(state);

                        dao.update(newCopy);

                        out.println("<h3>Success: The copy has been updated</h3>");
                    }
                    break;

                case "delete-copy":
                    id = Integer.parseInt(request.getParameter("id"));

                    if (id == null) {
                        out.println("<h3>ERROR: ID is required</h3>");
                    } else {
                        Ejemplar deletedCopy = dao.getById(id);
                        if (deletedCopy != null) {
                            dao.delete(deletedCopy);
                            out.println("<h3>Success: The copy has been deleted</h3>");
                        } else {
                            out.println("<h3>ERROR: Copy not found</h3>");
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

    private JSONObject getCopyByID(Integer id) {
        JSONObject copyJSON = new JSONObject();
        Ejemplar copy = dao.getById(id);

        if (copy != null && copy.getIsbn() != null) {
            copyJSON.put("ISBN", copy.getIsbn().getIsbn());
            copyJSON.put("State", copy.getEstado());
        } else {
            copyJSON.put("Error", "Copy not found");
        }

        return copyJSON;
    }

    private JSONObject getAllCopies() {
        JSONObject copyJSON = new JSONObject();
        JSONArray copyJSONArray = new JSONArray();

        List<Ejemplar> copies = dao.getAll();

        if (copies != null && !copies.isEmpty()) {
            for (Ejemplar copy : copies) {
                JSONObject copyJSONObject = new JSONObject();
                copyJSONObject.put("ISBN", copy.getIsbn().getIsbn());
                copyJSONObject.put("State", copy.getEstado());
                copyJSONArray.put(copyJSONObject);
            }

            copyJSON.put("copies", copyJSONArray);
        } else {
            copyJSON.put("Error", "No copies found");
        }

        return copyJSON;
    }
}
