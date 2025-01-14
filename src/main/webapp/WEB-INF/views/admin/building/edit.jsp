<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/common/taglib.jsp"%>
<%--
  Created by IntelliJ IDEA.
  User: THINKPAD
  Date: 12/13/2024
  Time: 4:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh Sách Tòa Nhà</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
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
                    Thông Tin Tòa Nhà
                    <small>
                        <i class="ace-icon fa fa-angle-double-right"></i>
                        overview &amp; stats
                    </small>
                </h1>
            </div><!-- /.page-header -->
            <div class="row">
                <div class="col-xs-12">
                    <form:form class="form-horizontal" role="form" id = "form-edit" modelAttribute="building">
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Tên Tòa Nhà</label>
                            <div class="col-xs-9">
                                <form:input path="name" cssClass="form-control" id="name"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Quận</label>
                            <div class="col-xs-2">
                                <form:select path="district" cssClass="form-control">
                                    <option value="">--Chọn Quận--</option>
                                    <form:options items="${districts}"></form:options>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Phường</label>
                            <div class="col-xs-9">
                                <form:input path="ward" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Đường</label>
                            <div class="col-xs-9">
                                <form:input path="street" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Kết Cấu</label>
                            <div class="col-xs-9">
                                <form:input path="structure" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Số Tầng Hầm</label>
                            <div class="col-xs-9">
                                <form:input path="numberOfBasement" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Diện Tích Sàn</label>
                            <div class="col-xs-9">
                                <form:input path="floorArea" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Hướng</label>
                            <div class="col-xs-2">
                                <select class="form-control">
                                    <option value="">--Chọn Hướng--</option>
                                    <option value="1">Đông</option>
                                    <option value="2">Tây</option>
                                    <option value="3">Nam</option>
                                    <option value="4">Bắc</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Hạng</label>
                            <div class="col-xs-9">
                                <form:input path="level" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Diện Tích Thuê</label>
                            <div class="col-xs-9">
                                <form:input path="rentArea" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Giá Thuê</label>
                            <div class="col-xs-9">
                                <form:input path="rentPrice" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Mô Tả Giá</label>
                            <div class="col-xs-9">
                                <form:input path="rentPriceDescription" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Phí Dịch Vụ</label>
                            <div class="col-xs-9">
                                <form:input path="serviceFee" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Phí Ô Tô</label>
                            <div class="col-xs-9">
                                <form:input path="carFee" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Phí MôTô</label>
                            <div class="col-xs-9">
                                <form:input path="motoFee" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Phí Ngoài Giờ</label>
                            <div class="col-xs-9">
                                <form:input path="overtimeFee" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Tiền Điện</label>
                            <div class="col-xs-9">
                                <form:input path="electricityFee" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Tiền Nước</label>
                            <div class="col-xs-9">
                                <form:input path="waterFee" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Đặt Cọc</label>
                            <div class="col-xs-9">
                                <form:input path="payment" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Thanh Toán</label>
                            <div class="col-xs-9">
                                <form:input path="deposit" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Thời Hạn Thuê</label>
                            <div class="col-xs-9">
                                <form:input path="rentTime" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Thời Gian Trang Trí</label>
                            <div class="col-xs-9">
                                <form:input path="decorationTime" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Tên Quản Lý</label>
                            <div class="col-xs-9">
                                <form:input path="managerName" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">SDT Quản Lý</label>
                            <div class="col-xs-9">
                                <form:input path="managerPhone" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Loại Tòa Nhà</label>
                            <div class="col-xs-9" class="checkbox-inline">
                                <form:checkboxes path="typeCode" items="${typeCode}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Phí Môi Giới</label>
                            <div class="col-xs-9">
                                <form:input path="brokerageFee" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">Ghi Chú</label>
                            <div class="col-xs-9">
                                <form:input path="note" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label"></label>
                            <div class="col-xs-9">
                                <c:if test="${empty building.id}">
                                    <button type="button" class="btn btn-primary" id="btnAddOrUpdateBuilding">
                                        Thêm Tòa Nhà
                                    </button>
                                </c:if>
                                <c:if test="${not empty building.id}">
                                    <button type="button" class="btn btn-primary" id="btnAddOrUpdateBuilding">
                                        Sửa Tòa Nhà
                                    </button>
                                </c:if>
                                <button type="button" class="btn btn-warning">
                                    Hủy Thao Tác
                                </button>
                            </div>
                        </div>
                        <form:hidden path="id"/>
                    </form:form>

                </div>

            </div>

        </div><!-- /.page-content -->
    </div>
</div><!-- /.main-content -->
<script src="assets/js/jquery.2.1.1.min.js"></script>

<script>
    $('#btnAddOrUpdateBuilding').click(function(e){
        e.preventDefault()
        var formData = $('#form-edit').serializeArray(); // mang cac doi tuong
        var json = {};
        var typeCode = [];
        $.each(formData , function(i , it){
            if(it.name != 'typeCode')json["" + it.name + ""] = it.value;
            else typeCode.push(it.value);
        })
        json['typeCode'] = typeCode;
        if(json['name'] == ''){
            $('#name').after('<span style="color: red">Name Not Null</span>')
        }
        if(json['district'] == ''){
            $('#district').after('<span style="color: red">District Not Null</span>')
        }
        else {
            AddBuilding(json);
        }
    })

    function AddBuilding(data){
        $.ajax({
            url:"/api/buildings",
            type: "POST",
            data : JSON.stringify(data), // convert du lieu tu Object trong js qua cho thanh json
 //           dataType : "JSON", // kieu du lieu server tra ra
            contentType : 'application/json',
            success : function(response){
                console.log('success');
                alert("Thao Tac Thanh Cong");
                window.location.href='<c:url value="/admin/building-list" />';
            },
            error : function(response){
                console.log('failed')
                alert(response.responseJSON);
            }
        })
    }
</script>
</body>
</html>
