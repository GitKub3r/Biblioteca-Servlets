package servlets;

import entities.Ejemplar;
import entities.Prestamo;
import entities.Usuario;
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
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "LoanSerlvet", value = "/loans")
public class LoanServlet extends HttpServlet {
    private final DAOGeneric<Prestamo, Integer> dao = new DAOGeneric<>(Prestamo.class, Integer.class);
    private final DAOGeneric<Ejemplar, Integer> daoCopy = new DAOGeneric<>(Ejemplar.class, Integer.class);
    private final DAOGeneric<Usuario, Integer> daoUser = new DAOGeneric<>(Usuario.class, Integer.class);
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
                        json = getLoanByID(id);
                    } else {
                        json.put("Error", "ID is invalid" );
                    }
                    break;

                case "get-all":
                    json = getAllLoans();
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
            Integer userID, copyID, id;
            LocalDate startDate, endDate;

            switch (action) {
                case "add-loan":
                    userID = Integer.parseInt(request.getParameter("user-id"));
                    copyID = Integer.parseInt(request.getParameter("copy-id"));
                    startDate = LocalDate.now();
                    endDate = startDate.plusDays(15);

                    if (userID == null || copyID == null) {
                        out.println("<h3>ERROR: Please fill every field</h3>");
                    } else {
                        Usuario user = daoUser.getById(userID);

                        if (user.getPrestamos().size() >= 3) {
                            out.println("<h3>ERROR: User has exceeded loan limit</h3>");
                        } else {

                            Ejemplar copy = daoCopy.getById(copyID);
                            Prestamo newLoan = new Prestamo();
                            newLoan.setUsuario(user);
                            newLoan.setEjemplar(copy);
                            newLoan.setFechaInicio(startDate);
                            newLoan.setFechaDevolucion(endDate);

                            dao.add(newLoan);
                            user.getPrestamos().add(newLoan);
                            daoUser.update(user);

                            out.println("<h3>Success: A new loan has been added</h3>");
                        }
                    }
                    break;

                case "update-loan":
                    id = Integer.parseInt(request.getParameter("id"));
                    startDate = LocalDate.parse(request.getParameter("start-date"));
                    endDate = LocalDate.parse(request.getParameter("end-date"));

                    if (id == null || startDate == null || endDate == null) {
                        out.println("<h3>ERROR: Please fill every field</h3>");
                    } else {
                        Prestamo updatedLoan = dao.getById(id);
                        updatedLoan.setFechaInicio(startDate);
                        updatedLoan.setFechaDevolucion(endDate);
                        dao.update(updatedLoan);

                        out.println("<h3>Success: The loan has been updated</h3>");
                    }
                    break;

                case "delete-loan":
                    id = Integer.parseInt(request.getParameter("id"));

                    if (id == null) {
                        out.println("<h3>ERROR: ID is required</h3>");
                    } else {
                        Prestamo deletedLoan = dao.getById(id);

                        if (deletedLoan != null) {
                            dao.delete(deletedLoan);
                            out.println("<h3>Success: The loan has been deleted</h3>");
                        } else {
                            out.println("<h3>ERROR: Loan not found</h3>");
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

    private JSONObject getLoanByID(Integer id) {
        JSONObject loanJSON = new JSONObject();
        Prestamo loan = dao.getById(id);

        if (loan != null && loan.getId() != null) {
            loanJSON.put("User", loan.getUsuario().getNombre());
            loanJSON.put("Copy", loan.getEjemplar().getIsbn());
            loanJSON.put("StartDate", loan.getFechaInicio());
            loanJSON.put("EndDate", loan.getFechaDevolucion());
        } else {
            loanJSON.put("Error", "Loan not found");
        }

        return loanJSON;
    }

    private JSONObject getAllLoans() {
        JSONObject loanJSON = new JSONObject();
        JSONArray loanJSONArray = new JSONArray();

        List<Prestamo> loans = dao.getAll();

        if (loans != null && !loans.isEmpty()) {
            for (Prestamo loan : loans) {
                JSONObject loanJSONObject = new JSONObject();
                loanJSONObject.put("User", loan.getUsuario().getNombre());
                loanJSONObject.put("Copy", loan.getEjemplar().getIsbn());
                loanJSONObject.put("StartDate", loan.getFechaInicio());
                loanJSONObject.put("EndDate", loan.getFechaDevolucion());
                loanJSONArray.put(loanJSONObject);
            }

            loanJSON.put("copies", loanJSONArray);
        } else {
            loanJSON.put("Error", "No copies found");
        }

        return loanJSON;
    }
}
