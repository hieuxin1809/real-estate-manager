<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
<!-- Font Awesome để giữ biểu tượng -->
</head>
<body style="background: linear-gradient(to bottom, #e0f7fa, #80deea); font-family: Arial, sans-serif; margin: 0; padding: 0; height: 100%; width: 100%; position: relative; min-height: 100vh;">
<div style="width: 500px; background: #ffffff; border-radius: 15px; box-shadow: 0 8px 16px rgba(0,0,0,0.2); padding: 40px; text-align: center; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);">

    <!-- Tiêu đề -->
    <h2 style="color: #0277bd; font-size: 28px; margin-bottom: 10px;">Đăng Kí</h2>
    <p style="color: #555; font-size: 14px; margin-bottom: 20px;">Điền thông tin đăng kí tài khoản</p>

    <!-- Form đăng ký -->
    <form id="registerForm">
        <div style="margin-bottom: 20px;">
            <label for="fullname" style="display: block; color: #0277bd; font-weight: bold; margin-bottom: 5px;">Họ và Tên</label>
            <input type="text" id="fullname" name="fullname" placeholder="Điền họ và tên"
                   style="width: 100%; padding: 10px; border: 2px solid #b0bec5; border-radius: 5px; font-size: 14px; outline: none;"
                   onfocus="this.style.borderColor='#0277bd';" onblur="this.style.borderColor='#b0bec5';" required>
        </div>

        <div style="margin-bottom: 20px;">
            <label for="username" style="display: block; color: #0277bd; font-weight: bold; margin-bottom: 5px;">Tên tài khoản</label>
            <input type="text" id="username" name="username" placeholder="Điền tên tài khoản"
                   style="width: 100%; padding: 10px; border: 2px solid #b0bec5; border-radius: 5px; font-size: 14px; outline: none;"
                   onfocus="this.style.borderColor='#0277bd';" onblur="this.style.borderColor='#b0bec5';" required>
        </div>

        <div style="margin-bottom: 20px;">
            <label for="password" style="display: block; color: #0277bd; font-weight: bold; margin-bottom: 5px;">Mật Khẩu</label>
            <input type="password" id="password" name="password" placeholder="Điền mật khẩu"
                   style="width: 100%; padding: 10px; border: 2px solid #b0bec5; border-radius: 5px; font-size: 14px; outline: none;"
                   onfocus="this.style.borderColor='#0277bd';" onblur="this.style.borderColor='#b0bec5';" required>
        </div>

        <div style="margin-bottom: 20px;">
            <label for="confirm-password" style="display: block; color: #0277bd; font-weight: bold; margin-bottom: 5px;">Xác Nhận Mật Khẩu</label>
            <input type="password" id="confirm-password" name="confirm-password" placeholder="Xác nhận mật khẩu"
                   style="width: 100%; padding: 10px; border: 2px solid #b0bec5; border-radius: 5px; font-size: 14px; outline: none;"
                   onfocus="this.style.borderColor='#0277bd';" onblur="this.style.borderColor='#b0bec5';" required>
        </div>

        <button type="submit" id="registerBtn"
                style="width: 100%; padding: 12px; background: #0277bd; color: white; border: none; border-radius: 25px; font-size: 16px; font-weight: bold; cursor: pointer; transition: background 0.3s;"
                onmouseover="this.style.background='#01579b';" onmouseout="this.style.background='#0277bd';">
            Đăng Kí
        </button>
    </form>
    <!-- Chuyển đến trang đăng nhập -->
    <p style="margin-top: 20px; color: #555; font-size: 14px;">
        Bạn đã có tài khoản
        <a href="/login" style="color: #0277bd; font-weight: bold; text-decoration: none;" onmouseover="this.style.textDecoration='underline';" onmouseout="this.style.textDecoration='none';">Đăng Nhập</a>
    </p>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- JavaScript thêm hiệu ứng -->
<script>
    $('#registerBtn').click(function (e){
        e.preventDefault();
        $(".error-message").remove();
        var formData = {
            fullName: $("#fullname").val(),
            userName: $("#username").val(),
            password: $("#password").val(),
            roleCode : "STAFF"
        };
        if(formData.fullName == ""){
            $('#fullname').after('<span class="error-message" style=" color: red">FullName Not Null</span>')
        }
        else if(formData.userName == ""){
            $('#username').after('<span class="error-message" style="color: red">Username Not Null</span>')
        }
        else if(formData.password == ""){
            $('#password').after('<span class="error-message" style="color: red">Password Not Null</span>')
        }
        else if(formData.password !== $("#confirm-password").val()){
            $('#confirm-password').after('<span class="error-message" style="color: red">Password do not match </span>')
        }
        else{
            registerUser(formData);
        }
    });
    function registerUser(data){
        $.ajax({
            url:"/api/user",
            type:"POST",
            data: JSON.stringify(data),
            contentType : 'application/json',
            success: function (response){
                console.log("success");
                alert("Đăng Kí Thành Công");
                window.location.href='<c:url value="/login"/>';
            },
            error : function (response){
                console.log("Đăng Kí Thất Bại");
            }
        });
    }

    // Hiệu ứng nút đăng ký khi nhấn
    const registerBtn = document.getElementById('registerBtn');
    registerBtn.addEventListener('mousedown', () => {
        registerBtn.style.transform = 'scale(0.95)';
    });
    registerBtn.addEventListener('mouseup', () => {
        registerBtn.style.transform = 'scale(1)';
    });

    // Hiển thị lỗi (nếu có)
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

    // Căn giữa form khi resize
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
