<%@include file="/common/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Danh sách Khách Hàng</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {
                }
            </script>

            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Home</a>
                </li>
                <li class="active">Dashboard</li>
            </ul><!-- /.breadcrumb -->


        </div>

        <div class="page-content">
            <div class="page-header">
                <h1>
                    Thông tin tòa nhà
                    <small>
                        <i class="ace-icon fa fa-angle-double-right"></i>
                        overview &amp; stats
                    </small>
                </h1>
            </div><!-- /.page-header -->

            <div class="row" style="font-family: Arial, Helvetica, sans-serif;">
                <div class="col-xs-12">
                    <form:form id="form-edit" class="form-horizontal" role="form" modelAttribute="customer">
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Tên khách hàng</label>
                            <div class="col-xs-9">
                                <form:input path="fullName" class="form-control" id="fullName"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 control-label">Số điện thoại</label>
                            <div class="col-xs-9">
                                <form:input path="phone" class="form-control" id="phone"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 control-label">Email</label>
                            <div class="col-xs-9">
                                <form:input path="email" class="form-control" id="email"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 control-label">Tên công ty</label>
                            <div class="col-xs-9">
                                <form:input path="companyName" class="form-control" id="companyName"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 control-label">Nhu cầu</label>
                            <div class="col-xs-9">
                                <form:input path="demand" class="form-control" id="demand"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 control-label">Trạng thái xử lý</label>
                            <div class="col-xs-9">
                                <form:select path="status" class="form-control">
                                    <form:options items="${StatusList}"></form:options>
                                </form:select>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-xs-3 control-label"></label>
                            <div class="col-xs-9">
                                <c:if test="${empty customer.id}">
                                    <button type="button" class="btn btn-primary" id="btnAddOrUpdateCustomer">
                                        Thêm khách hàng
                                    </button>
                                </c:if>
                                <c:if test="${not empty customer.id}">
                                    <button type="button" class="btn btn-primary" id="btnAddOrUpdateCustomer">
                                        Sửa khách hàng
                                    </button>
                                </c:if>

                                <button type="button" class="btn btn-warning" id="btnCancel">
                                    Hủy thao tác
                                </button>
                            </div>
                        </div>
                        <form:hidden path="id"/>
                    </form:form>
                </div>
            </div>

            <c:if test="${not empty customer.id}">
                <c:forEach var="item" items="${transactions}">
                    <div class="col-xs-12">
                        <h2 class="smaller lighter blue">
                                ${item.value}
                            <button class="btn btn-md btn-success pull-right" title="Thêm giao dịch"
                                    onclick="addTransaction('${item.key}', '${customer.id}')">
                                <i class="ace-icon glyphicon glyphicon-plus smaller-80"></i>Thêm giao dịch
                            </button>
                        </h2>
                        <div class="hr hr-16 dotted hr-dotted"></div>
                    </div>
                    <c:if test="${item.key == 'CSKH'}">
                        <div class="col-xs-12">
                            <table id="tableList-CSKH" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr class="center">
                                    <th class="center">Ngày tạo</th>
                                    <th class="center">Người tạo</th>
                                    <th class="center">Ngày sửa</th>
                                    <th class="center">Người sửa</th>
                                    <th class="center">Chi tiết giao dịch</th>
                                    <th class="center">Thao tác</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach var="transaction" items="${CSKH}">
                                    <tr class="center">
                                        <td>${transaction.createdDate}</td>
                                        <td>${transaction.createdBy}</td>
                                        <td>${transaction.modifiedDate}</td>
                                        <td>${transaction.modifiedBy}</td>
                                        <td>${transaction.note}</td>
                                        <td class="center">
                                            <div class="hidden-sm hidden-xs center">
                                                <button class="btn btn-sm btn-info"
                                                        title="Chỉnh sửa giao dịch" type="button"
                                                        onClick="updateTransaction(${transaction.id},'${transaction.code}','${transaction.customerId}')"
                                                >
                                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                                </button>

<%--                                                <security:authorize access="hasRole('MANAGER')">--%>
<%--                                                    <button class="btn btn-sm btn-danger"--%>
<%--                                                            title="Xóa giao dịch" type="button"--%>
<%--                                                            onclick="deleteTransaction(${transaction.id})"--%>
<%--                                                    >--%>
<%--                                                        <i class="ace-icon glyphicon glyphicon-trash"></i>--%>
<%--                                                    </button>--%>
<%--                                                </security:authorize>--%>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${item.key == 'DDX'}">
                        <div class="col-xs-12">
                            <table id="tableList-DDX" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr class="center">
                                    <th class="center">Ngày tạo</th>
                                    <th class="center">Người tạo</th>
                                    <th class="center">Ngày sửa</th>
                                    <th class="center">Người sửa</th>
                                    <th class="center">Chi tiết giao dịch</th>
                                    <th class="center">Thao tác</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach var="transaction" items="${DDX}">
                                    <tr class="center">
                                        <td>${transaction.createdDate}</td>
                                        <td>${transaction.createdBy}</td>
                                        <td>${transaction.modifiedDate}</td>
                                        <td>${transaction.modifiedBy}</td>
                                        <td>${transaction.note}</td>
                                        <td class="center">
                                            <div class="hidden-sm hidden-xs center">
                                                <button class="btn btn-sm btn-info"
                                                        title="Chỉnh sửa giao dịch" type="button"
                                                        onClick="updateTransaction(${transaction.id},'${transaction.code}','${transaction.customerId}')"
                                                >
                                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                                </button>

<%--                                                <security:authorize access="hasRole('MANAGER')">--%>
<%--                                                    <button class="btn btn-sm btn-danger"--%>
<%--                                                            title="Xóa giao dịch" type="button"--%>
<%--                                                            onclick="deleteTransaction(${transaction.id})"--%>
<%--                                                    >--%>
<%--                                                        <i class="ace-icon glyphicon glyphicon-trash"></i>--%>
<%--                                                    </button>--%>
<%--                                                </security:authorize>--%>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>


                </c:forEach>
            </c:if>


        </div><!-- /.page-content -->


    </div>
    <div class="modal" id="addOrUpdateTransactionModal" style="font-family: Arial, Helvetica, sans-serif;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Nhập thông tin giao dịch</h5>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right">Chi tiết giao dịch</label>
                        <div class="col-sm-9">
                            <input id="note" value="" type="text" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="btnAddOrUpdateTransaction">Xác nhận</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>
    <input type="hidden" id="customerId" value="">
    <input type="hidden" id="code" value="">
    <input type="hidden" id="transactionId" value="">
</div><!-- /.main-content -->

<script src="assets/js/jquery.2.1.1.min.js"></script>
>

<script>
    function addTransaction(code,customerId){
        $('#addOrUpdateTransactionModal').modal();
        $('#note').val('');
        $('#customerId').val(customerId);
        $('#code').val(code);
    }
    function updateTransaction(transactionId,code , customerId){
        $('#addOrUpdateTransactionModal').modal();
        loadTransaction(transactionId);
        $('#customerId').val(customerId);
        $('#code').val(code);
        $('#transactionId').val(transactionId);
    }
    function loadTransaction(transactionId){
        $.ajax({
            url : "/api/transactions/" + transactionId,
            type : "GET",
          //  dataType : "JSON",
            success : function (response){
                if(response.data){
                    $('#note').val(response.data);
                }
                else {
                    alert(response.message);
                }
            },
            error : function (response){
                alert("Thất Bại")
            }
        });
    }
    $('#btnAddOrUpdateTransaction').click(function (e){
        e.preventDefault();
        let data = {};
        data['id'] = $('#transactionId').val();
        data['code'] = $('#code').val();
        data['note'] = $('#note').val().trim();
        data['customerId'] = $('#customerId').val();
        if(data['note'] !== ""){
            addOrUpdateTransaction(data);
        }
        else {
            $('#note').attr('placeholder', 'Vui lòng điền chi tiết giao dịch');
        }
    });
    function addOrUpdateTransaction(data){
        $.ajax({
            url: "/api/transactions",
            type : "POST",
            data : JSON.stringify(data),
            contentType : 'application/json',
            success : function (response){
                alert("Thành Công");
                location.reload();
            },
            error : function (response){
                alert("Thất Bại");
            }
        });
    }
    $('#btnAddOrUpdateCustomer').click(function(e){
        e.preventDefault()
        $(".error-message").remove();
        var formData = $('#form-edit').serializeArray(); // mang cac doi tuong
        var json = {};
        let emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        $.each(formData , function(i , it){
            json['' + it.name + ''] = it.value.trim();
        })
        if(json['fullName'] === ''){
            $('#fullName').after('<span class="error-message" style=" color: red">Họ tên không được để trống</span>')
        }
        else if(json['phone'] === ''){
            $('#phone').after('<span class="error-message" style=" color: red">Số Điện Thoại không được để trống</span>')
        }
        else if(json['phone'].length !== 10){
            $('#phone').after('<span class="error-message" style=" color: red">Số điện thoại phải 10 số</span>')
        }
        else if(!emailRegex.test(json['email'])){
            $('#email').after('<span class="error-message" style=" color: red">Email không hợp lệ</span>')
        }
        else {
            json["isActive"] = 1;
            AddCustomer(json);
        }
    })
    function AddCustomer(data){
        $.ajax({
            url:"/api/customer",
            type: "POST",
            data : JSON.stringify(data), // convert du lieu tu Object trong js qua cho thanh json
 //           dataType : "JSON", // kieu du lieu server tra ra
            contentType : 'application/json',
            success : function(response){
                console.log('success');
                alert("Thêm Thành Công");
                window.location.href='<c:url value="/admin/customer-list" />';
            },
            error : function(response){
                console.log('Thêm Thất Bại')
            }
        });
    }
</script>
</body>
</html>
