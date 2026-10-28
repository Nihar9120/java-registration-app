<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head><meta charset="UTF-8"><title>Registered</title></head>
<body style="font-family: system-ui, sans-serif; text-align: center; margin-top: 4rem;">
  <h1>Welcome!</h1>
  <p>Account created for <strong><%= com.example.web.RegisterServlet.escape(((com.example.registration.User) request.getAttribute("user")).fullName()) %></strong>.</p>
  <p><a href="${pageContext.request.contextPath}/register">Register another</a></p>
</body>
</html>
