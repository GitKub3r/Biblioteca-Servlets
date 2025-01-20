package servlets;

import entities.Prestamo;
import entities.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.DAOGeneric;
import modelo.DAOUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "UserServlet", value = "/login")
public class UserServlet extends HttpServlet {
    private final DAOUser dao = new DAOUser(Usuario.class, String.class);


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Usuario user = dao.getUserByEmail(email);

        if (user == null) {
            out.println("<h3>ERROR: User doesn't exist</h3>");
        } else {
            if (!user.getPassword().equals(password)) {
                out.println("<h3>ERROR: Email or Password is incorrect</h3>");
            } else {
                out.println("<h3>Welcome back " + user.getNombre() + "</h3>");
            }
        }
    }
}
