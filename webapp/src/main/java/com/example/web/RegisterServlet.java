package com.example.web;

import com.example.registration.InMemoryUserRepository;
import com.example.registration.RegistrationRequest;
import com.example.registration.RegistrationResult;
import com.example.registration.RegistrationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

  private RegistrationService service;

  @Override
  public void init() {
    service = new RegistrationService(new InMemoryUserRepository());
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    RegistrationResult result = service.register(new RegistrationRequest(
        req.getParameter("fullName"), req.getParameter("email"),
        req.getParameter("password"), req.getParameter("confirmPassword")));

    if (result.isSuccess()) {
      req.setAttribute("user", result.user());
      req.getRequestDispatcher("/WEB-INF/views/success.jsp").forward(req, resp);
    } else {
      resp.setStatus(HttpServletResponse.SC_UNPROCESSABLE_ENTITY);
      req.setAttribute("errors", result.errors());
      req.setAttribute("fullName", escape(req.getParameter("fullName")));
      req.setAttribute("email", escape(req.getParameter("email")));
      req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }
  }

  /** HTML-escape user input before echoing it back into the JSP (avoids XSS without needing JSTL). */
  public static String escape(String s) {
    if (s == null) {
      return "";
    }
    return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
        .replace("\"", "&quot;").replace("'", "&#39;");
  }
}
