<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Register</title>
  <style>
    body { font-family: system-ui, sans-serif; background: #f4f5f7; display: grid; place-items: center; min-height: 100vh; margin: 0; }
    form { background: #fff; padding: 2rem; border-radius: 8px; width: 100%; max-width: 360px; box-shadow: 0 2px 8px rgba(0,0,0,.1); }
    h1 { margin-top: 0; font-size: 1.4rem; }
    label { display: block; margin-top: 1rem; font-size: .9rem; }
    input { width: 100%; box-sizing: border-box; padding: .5rem; margin-top: .25rem; border: 1px solid #bbb; border-radius: 4px; }
    .err { color: #b00020; font-size: .8rem; margin-top: .25rem; }
    button { margin-top: 1.5rem; width: 100%; padding: .6rem; background: #1a56db; color: #fff; border: 0; border-radius: 4px; font-size: 1rem; cursor: pointer; }
  </style>
</head>
<body>
  <form method="post" action="${pageContext.request.contextPath}/register">
    <h1>Create an account</h1>

    <label>Full name
      <input name="fullName" value="${fullName}" required>
    </label>
    <div class="err">${errors.fullName}</div>

    <label>Email
      <input type="email" name="email" value="${email}" required>
    </label>
    <div class="err">${errors.email}</div>

    <label>Password
      <input type="password" name="password" minlength="8" required>
    </label>
    <div class="err">${errors.password}</div>

    <label>Confirm password
      <input type="password" name="confirmPassword" required>
    </label>
    <div class="err">${errors.confirmPassword}</div>

    <button type="submit">Register</button>
  </form>
</body>
</html>
