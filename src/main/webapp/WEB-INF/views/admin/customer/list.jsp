
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8" />
    <title>Danh Sách Khách Hàng</title>
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
                    Danh Sách Khách Hàng
                </h1>
            </div><!-- /.page-header -->
            <div class="row">
                <div class="widget-box" style="font-family: 'Courier New', Courier, monospace ;">
                    <div class="widget-header">
                        <h4 class="widget-title">Tìm Kiếm</h4>

                        <span class="widget-toolbar">

										<a href="#" data-action="reload">
											<i class="ace-icon fa fa-refresh"></i>
										</a>

										<a href="#" data-action="collapse">
											<i class="ace-icon fa fa-chevron-up"></i>
										</a>

										<a href="#" data-action="close">
											<i class="ace-icon fa fa-times"></i>
										</a>
									</span>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main">
                            <form:form id = "listForm" action="/admin/customer-list" method="get" modelAttribute="modelSearch">
                              <div class="row">
                              <!-- hang 1  -->
                                <div class="col-xs-12">
                                    <div class="col-xs-6">
                                        <label>Tên Khách Hàng</label>
                                            <form:input path="fullName" class="form-control" />
                                    </div>
                                </div>
                                <!-- hang 2  -->
                                <div class="col-xs-12">
                                    <div class="col-xs-6">
                                        <label>Số điện thoại</label>
                                        <form:input path="phone" class="form-control" />
                                    </div>
                                    <div class="col-xs-6">
                                        <label >Email</label>
                                        <form:input path="email" class="form-control" />
                                    </div>
                                </div>
                                <!-- hang 3  -->
                                <div class="col-xs-12">
                                    <div class="col-xs-6">
                                        <label>Chọn tình trạng</label>
                                        <form:select path="status" cssClass="form-control">
                                        <option value="">--Chọn tình trạng--</option>
                                            <form:options items="${status}"></form:options>
                                        </form:select>
                                    </div>
                                    <security:authorize access="hasRole('MANAGER')">
                                        <div class="col-xs-6">
                                            <label>Tên Nhân Viên</label>
                                            <form:select path="staffId" cssClass="form-control">
                                            <option value="">--Chọn Nhân Viên--</option>
                                                <form:options items="${staffs}"></form:options>
                                            </form:select>
                                        </div>
                                    </security:authorize>
                                </div>
                                <div class="col-xs-12">
                                    <div class="col-xs-6">
                                        <button class="btn btn-primary" id="btn-search">
                                            <i class="ace-icon glyphicon glyphicon-search"></i>
                                            Tìm Kiếm
                                        </button>
                                    </div>
                                </div>
                            </div>
                            </form:form>
                        </div>
                    </div>
                </div>
                <div class="pull-right">
                    <a href="/admin/customer-edit">
                        <button class="btn btn-app btn-success btn-sm" title="Thêm Khách Hàng">
                            <i class="ace-icon fa fa-user-plus"></i>
                        </button>
                    </a>
                    <security:authorize access="hasRole('MANAGER')">
                        <button class="btn btn-app btn-danger btn-sm" title="Xóa Khách Hàng" id = "btn-deleteCustomer">
                            <i class="ace-icon fa fa-trash-o bigger-200"></i>
                        </button>
                    </security:authorize>
                </div>
            </div>
            <div class="hr hr-18 dotted hr-double"></div>
            <div class="row">
                <div class="col-xs-12">
                    <table id="customer-list" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th>Tên Khách Hàng</th>
                            <th>Số Điện Thoại</th>
                            <th>Email</th>
                            <th>Nhu Cầu</th>
                            <th>Người Tạo</th>
                            <th>Ngày Tạo</th>
                            <th>Tình Trạng</th>
                            <th>Thao tác</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${customerList}">
                            <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace" value="${item.id}">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <td>${item.fullName}</td>
                            <td>${item.phone}</td>
                            <td>${item.email}</td>
                            <td>${item.demand}</td>
                            <td>${item.createdBy}</td>
                            <td>${item.createdDate}</td>
                            <td>${item.status}</td>

                            <td>
                                <div class="hidden-sm hidden-xs btn-group">
                                <security:authorize access="hasRole('MANAGER')">
                                    <button class="btn btn-xs btn-success" onclick="assingmentCustomer(${item.id})" title="Giao Khách Hàng">
                                        <i class="ace-icon fa fa-users "></i>
                                    </button>
                                </security:authorize>
                                    <a class="btn btn-xs btn-info" title="Sửa Thông Tin Khách Hàng" href="/admin/customer-edit-${item.id}">
                                        <i class="ace-icon fa fa-pencil bigger-120"></i>
                                    </a>
                                <security:authorize access="hasRole('MANAGER')">
                                    <button class="btn btn-xs btn-danger" title="Xóa Khách Hàng"
                                        onclick="deleteCustomer(${item.id})">
                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                    </button>
                                </security:authorize>
                                </div>
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div><!-- /.span -->
            </div>
        </div><!-- /.page-content -->
    </div>
</div><!-- /.main-content -->

<div class="modal " id = "assingmentCustomerModal" style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif ">
    <div class="modal-dialog" >
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Danh Sách Nhân Viên</h5>
            </div>
            <div class="modal-body">
                <table id="staff-list" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace">
                                <span class="lbl"></span>
                            </label>
                        </th>
                        <th>Tên Nhân Viên</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id = "btn-assingmentCustomer">Giao Khách Hàng</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id = "customerId" value="">
</div><!-- /.main-container -->
<script src="assets/js/jquery.2.1.1.min.js"></script>
<script>
    $('#btn-search').click(function (e){
        e.preventDefault();
        $('#listForm').submit();
    })
    function assingmentCustomer(id){
        $('#customerId').val(id);
        $('#assingmentCustomerModal').modal();
        loadStaff(id);
    }
    function loadStaff(id){
        $.ajax({
            url: "/api/customer/" + id + "/staffs",
            type: "GET",
            dataType: "JSON",
            success : function (response){
                var row = '';
                $.each(response.data,function (index , item){
                    row += '<tr>';
                    row += '<td class="center"> <input type="checkbox" value=' + item.staffId + ' id=checkbox_' + item.staffId +  ' ' + item.checked + '> </td>';
                    row += '<td class="center">' + item.fullName + '</td> ';
                    row += '</tr>';
                });
                $('#staff-list tbody').html(row);
            },
            error : function (response){
                console.log("Thất Bại");
            }
        });
    }

    $('#btn-assingmentCustomer').click(function(e) {
        e.preventDefault();
        var json = {};
        json['customerId'] = $('#customerId').val();
        var staffIds = $('#staff-list').find('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        json['staffIds'] = staffIds;
        console.log('sfd');
        if (json['customerId'] != '') {
            updateAssingment(json);
        } else {
            alert("CustomerID Not Null");
        }
    });
        <%--});--%>
        $('#btn-deleteCustomer').click(function (e) {
            e.preventDefault();
            var data = {};
            var ids = $('#customer-list').find('tbody input[type=checkbox]:checked').map(function () {
                return $(this).val();
            }).get();
            data['ids'] = ids;
            if (data['ids'] != '') {
                deleteCustomer(data['ids'])
            } else {
                alert("No Building choose")
            }

        });

        // begin Ajax
        function deleteCustomer(data) {
            $.ajax({
                url: "/api/customer/" + data,
                type: "DELETE",
                //	data : JSON.stringify(data), // convert du lieu tu Object trong js qua cho thanh json
                dataType: "JSON", // kieu du lieu server tra ra
                //	contentType : 'application/json',
                success: function (response) {
                    console.log('success');
                    alert(response.message);
                    window.location.href = '<c:url value="/admin/customer-list" />'
                },
                error: function (response) {
                    console.log('Thất Bại')
                    alert(response.message);
                }
            })
        }

        function updateAssingment(data) {
            $.ajax({
                url: "/api/assingments/customer", // url cần gửi yêu cầu
                type: "POST",
                data: JSON.stringify(data), // convert du lieu tu Object trong js qua cho thanh json
                dataType: "JSON", // kieu du lieu server tra ra
                contentType: 'application/json', // chỉ định dữ liệu được gửi đi ở dạng json
                success: function (response) { // hàm callback khi thành công
                    console.log('success');
                    alert("Cập Nhật Thành Công");
                    window.location.href = '<c:url value="/admin/customer-list" />'
                },
                error: function (response) {
                    console.log('failed');
                    alert("Cập Nhật Thất Bại");
                    window.location.href = '<c:url value="/admin/customer-list" />'
                }
            })
        }

        // end ajax
        $(document).ready(function () {
            // Chọn tất cả checkbox khi checkbox ở thead được bấm
            $('thead input[type="checkbox"]').change(function () {
                let isChecked = $(this).prop('checked');
                $('tbody input[type="checkbox"]').prop('checked', isChecked);
            });

            // Kiểm tra khi checkbox trong tbody được thay đổi
            $('tbody input[type="checkbox"]').change(function () {
                let allChecked = $('tbody input[type="checkbox"]').length === $('tbody input[type="checkbox"]:checked').length;
                $('thead input[type="checkbox"]').prop('checked', allChecked);
            });
        });
</script>
</body>
</html>
