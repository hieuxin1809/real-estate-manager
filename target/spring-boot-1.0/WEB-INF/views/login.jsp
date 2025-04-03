<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Đăng nhập</title>
<!-- Nhúng Font Awesome để giữ biểu tượng -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body style="background: linear-gradient(to bottom, #e0f7fa, #80deea); font-family: Arial, sans-serif; margin: 0; padding: 0; height: 100%; width: 100%; position: relative; min-height: 100vh;">
<div style="width: 500px; background: #ffffff; border-radius: 15px; box-shadow: 0 8px 16px rgba(0,0,0,0.2); padding: 40px; text-align: center; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);">
    <!-- Thông báo lỗi -->
    <c:if test="${param.incorrectAccount != null}">
        <div style="background: #ffebee; color: #d32f2f; padding: 10px; border-radius: 5px; margin-bottom: 15px;">
            Username or password incorrect
        </div>
    </c:if>
    <c:if test="${param.accessDenied != null}">
        <div style="background: #ffebee; color: #d32f2f; padding: 10px; border-radius: 5px; margin-bottom: 15px;">
            You are not authorized
        </div>
    </c:if>
    <c:if test="${param.sessionTimeout != null}">
        <div style="background: #ffebee; color: #d32f2f; padding: 10px; border-radius: 5px; margin-bottom: 15px;">
            Session Timeout
        </div>
    </c:if>

    <!-- Tiêu đề -->
    <h2 style="color: #0277bd; font-size: 28px; margin-bottom: 10px;">Đăng Nhập</h2>
    <p style="color: #555; font-size: 14px; margin-bottom: 20px;">Vui lòng nhập thông tin của bạn</p>

    <!-- Form đăng nhập -->
    <form action="j_spring_security_check" id="formLogin" method="post">
        <div style="margin-bottom: 20px;">
            <label for="userName" style="display: block; color: #0277bd; font-weight: bold; margin-bottom: 5px;">Email</label>
            <input type="text" id="userName" name="j_username" placeholder="Tên đăng nhập"
                   style="width: 100%; padding: 10px; border: 2px solid #b0bec5; border-radius: 5px; font-size: 14px; outline: none;"
                   onfocus="this.style.borderColor='#0277bd';" onblur="this.style.borderColor='#b0bec5';">
        </div>

        <div style="margin-bottom: 20px;">
            <label for="password" style="display: block; color: #0277bd; font-weight: bold; margin-bottom: 5px;">Mật khẩu</label>
            <input type="password" id="password" name="j_password" placeholder="Mật khẩu"
                   style="width: 100%; padding: 10px; border: 2px solid #b0bec5; border-radius: 5px; font-size: 14px; outline: none;"
                   onfocus="this.style.borderColor='#0277bd';" onblur="this.style.borderColor='#b0bec5';">
        </div>

        <div style="display: flex; justify-content: center; align-items: center; margin-bottom: 20px;">
            <input type="checkbox" id="form2Example3cg" style="margin-right: 5px;">
            <label for="form2Example3cg" style="color: #555; font-size: 14px;">Ghi nhớ mật khẩu</label>
        </div>

        <button type="submit" id="loginBtn"
                style="width: 100%; padding: 12px; background: #0277bd; color: white; border: none; border-radius: 25px; font-size: 16px; font-weight: bold; cursor: pointer; transition: background 0.3s;"
                onmouseover="this.style.background='#01579b';" onmouseout="this.style.background='#0277bd';">
            Đăng nhập
        </button>
    </form>

    <!-- Quên mật khẩu -->
    <p style="margin: 15px 0; font-size: 12px;">
        <a href="#!" style="color: #0277bd; text-decoration: none;" onmouseover="this.style.textDecoration='underline';" onmouseout="this.style.textDecoration='none';">Quên mật khẩu?</a>
    </p>

    <!-- Đăng nhập bằng mạng xã hội -->
    <div style="display: flex; justify-content: center; margin-top: 20px;">
        <a href="#!" style="margin: 0 10px; color: #3b5998; font-size: 20px;" onmouseover="this.style.color='#2a406e';" onmouseout="this.style.color='#3b5998';"><i class="fab fa-facebook-f"></i></a>
        <a href="#!" style="margin: 0 10px; color: #1da1f2; font-size: 20px;" onmouseover="this.style.color='#0d8ddb';" onmouseout="this.style.color='#1da1f2';"><i class="fab fa-twitter"></i></a>
        <a href="#!" style="margin: 0 10px; color: #db4437; font-size: 20px;" onmouseover="this.style.color='#c1351d';" onmouseout="this.style.color='#db4437';"><i class="fab fa-google"></i></a>
    </div>

    <!-- Đăng ký -->
    <p style="margin-top: 20px; color: #555; font-size: 14px;">
        Chưa có tài khoản?
        <a href="register" style="color: #0277bd; font-weight: bold; text-decoration: none;" onmouseover="this.style.textDecoration='underline';" onmouseout="this.style.textDecoration='none';">Đăng ký</a>
    </p>
</div>

<!-- JavaScript để thêm hiệu ứng và đảm bảo căn giữa -->
<script>
    // Hiệu ứng nút đăng nhập khi nhấn
    const loginBtn = document.getElementById('loginBtn');
    loginBtn.addEventListener('mousedown', () => {
        loginBtn.style.transform = 'scale(0.95)';
    });
    loginBtn.addEventListener('mouseup', () => {
        loginBtn.style.transform = 'scale(1)';
    });

    // Hiệu ứng khi nhập sai thông tin (nếu có thông báo lỗi)
    const errorDivs = document.querySelectorAll('div[style*="background: #ffebee"]');
    if (errorDivs.length > 0) {
        errorDivs.forEach(div => {
            let opacity = 1;
            setInterval(() => {
                opacity = opacity === 1 ? 0.7 : 1;
                div.style.opacity = opacity;
            }, 500);
        });
    }

    // Đảm bảo container luôn ở giữa khi resize
    const container = document.querySelector('div[style*="position: absolute"]');
    function centerContainer() {
        container.style.top = '50%';
        container.style.left = '50%';
        container.style.transform = 'translate(-50%, -50%)';
    }
    centerContainer();
    window.addEventListener('resize', centerContainer);
</script>
</body>
</html>