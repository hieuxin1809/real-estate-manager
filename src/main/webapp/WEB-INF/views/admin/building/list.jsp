
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8" />
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
                    Danh Sách Tòa Nhà
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
                            <form:form id = "listForm" action="/admin/building-list" method="get" modelAttribute="modelSearch">
                              <div class="row">
                                <div class="col-xs-12">
                                    <div class="col-xs-6">
                                        <label>Tên Tòa Nhà</label>
                                            <form:input path="name" class="form-control" />
                                    </div>
                                    <div class="col-xs-6">
                                        <label>Diện Tích Sàn</label>
                                        <form:input path="floorArea" class="form-control" />
                                    </div>
                                </div>
                                <div class="col-xs-12">
                                    <div class="col-xs-2">
                                        <label>Chọn Quận</label>

                                        <form:select path="district" cssClass="form-control">
                                        <option value="">--Chọn Quận--</option>
                                            <form:options items="${districts}"></form:options>
                                        </form:select>
                                    </div>
                                    <div class="col-xs-5">
                                        <label >Phường</label>
                                        <form:input path="ward" class="form-control" />
                                    </div>
                                    <div class="col-xs-5">
                                        <label>Đường</label>
                                        <form:input path="street" class="form-control" />
                                    </div>
                                </div>
                                <div class="col-xs-12">
                                    <div class="col-xs-4">
                                        <label>Số Tầng Hầm</label>
                                        <form:input path="numberOfBasement" class="form-control" />
                                    </div>
                                    <div class="col-xs-4">
                                        <label>Hướng</label>
                                        <form:input path="direction" class="form-control" />
                                    </div>
                                    <div class="col-xs-4">
                                        <label >Hạng</label>
                                        <form:input path="level" class="form-control" />
                                    </div>
                                </div>
                                <div class="col-xs-12">
                                    <div class="col-xs-3">
                                        <label >Diện Tích Từ</label>
                                        <form:input path="areaFrom" class="form-control" />
                                    </div>
                                    <div class="col-xs-3">
                                        <label >Diện Tích Đến</label>
                                        <form:input path="areaTo" class="form-control" />
                                    </div>
                                    <div class="col-xs-3">
                                        <label >Giá Thuê Từ</label>
                                        <form:input path="rentPriceFrom" class="form-control" />
                                    </div>
                                    <div class="col-xs-3">
                                        <label >Giá Thuê Đến</label>
                                        <form:input path="rentPriceTo" class="form-control" />
                                    </div>
                                </div>
                                <div class="col-xs-12">
                                    <div class="col-xs-5">
                                        <label >Tên Quản Lý</label>
                                        <form:input path="managerName" class="form-control" />
                                    </div>
                                    <div class="col-xs-5">
                                        <label >SDT Quản Lý</label>
                                        <form:input path="managerPhoneNumber" class="form-control" />
                                    </div>
                                    <security:authorize access="hasRole('MANAGER')">
                                        <div class="col-xs-2">
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
                                        <form:checkboxes path="typeCode" items="${typeCode}" />
                                    </div>
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
                    <a href="/admin/building-edit">
                        <button class="btn btn-app btn-success btn-sm" title="Thêm Tòa Nhà">
                            <i class="ace-icon fa fa-home"></i>
                        </button>
                    </a>
                    <security:authorize access="hasRole('MANAGER')">
                        <button class="btn btn-app btn-danger btn-sm" title="Xóa Tòa Nhà" id = "btn-deleteBuilding">
                            <i class="ace-icon fa fa-trash-o bigger-200"></i>
                        </button>
                    </security:authorize>
                </div>

            </div>
            <div class="hr hr-18 dotted hr-double"></div>
            <div class="row">
                <div class="col-xs-12">
                    <table id="building-list" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th>Tên Tòa Nhà</th>
                            <th>Địa Chỉ</th>
                            <th>Số Tầng Hầm</th>
                            <th>Tên Quản Lý</th>
                            <th>SDT Quản Lý</th>
                            <th>Diện Tích Sàn</th>
                            <th>Diện Tích Thuê</th>
                            <th>Giá Thuê</th>
                            <th>Phí Dịch Vụ</th>
                            <th>Phí Môi Giới</th>
                            <th>Thao Tác</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="item" items="${buildingList}">
                            <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace" value="${item.id}">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <td>${item.name}</td>
                            <td>${item.address}</td>
                            <td>${item.numberOfBasement}</td>
                            <td>${item.managerName}</td>
                            <td>${item.managerPhone}</td>
                            <td>${item.floorArea}</td>
                            <td>${item.rentArea}</td>
                            <td>${item.rentPrice}</td>
                            <td>${item.serviceFee}</td>
                            <td>${item.brokerageFee}</td>

                            <td>
                                <div class="hidden-sm hidden-xs btn-group">
                                <security:authorize access="hasRole('MANAGER')">
                                    <button class="btn btn-xs btn-success" onclick="assingmentBuilding(${item.id})" title="Giao Tòa Nhà">
                                        <i class="ace-icon fa fa-users "></i>
                                    </button>
                                </security:authorize>
                                    <a class="btn btn-xs btn-info" title="Sửa Tòa Nhà" href="/admin/building-edit-${item.id}">
                                        <i class="ace-icon fa fa-pencil bigger-120"></i>
                                    </a>
                                <security:authorize access="hasRole('MANAGER')">
                                    <button class="btn btn-xs btn-danger" title="Xóa Tòa Nhà"
                                        onclick="deleteBuilding(${item.id})">
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

<div class="modal " id = "assingmentBuildingModal" style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif ">
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
                <button type="button" class="btn btn-primary" id = "btn-assingmentBuilding">Giao Tòa Nhà</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id = "buildingId" value="">
</div><!-- /.main-container -->
<script src="assets/js/jquery.2.1.1.min.js"></script>
<script>
    $('#btn-search').click(function (e){
        e.preventDefault();
        $('#listForm').submit();
    })
    function assingmentBuilding(id){
        $('#buildingId').val(id);
        $('#assingmentBuildingModal').modal();
        loadStaff(id);
    }
    function loadStaff(id){
        $.ajax({
            url:"/api/buildings/" + id + "/staffs",
            type: "Get",
          //  data : JSON.stringify(data), // convert du lieu tu Object trong js qua cho thanh json
            dataType : "JSON", // kieu du lieu server tra ra
          //  contentType : 'application/json',
            success : function(response){
                console.log(response.message);
                var row = '';
                $.each(response.data,function (index , item){
                    row += '<tr>';
                    row += '<td class="center"> <input type="checkbox" value=' + item.staffId + ' id=checkbox_' + item.staffId +  ' ' + item.checked + '> </td>';
                    row += '<td class="center">' + item.fullName + '</td> ';
                    row += '</tr>';
                });
                $('#staff-list tbody').html(row);
            },
            error : function(response){
                console.log('failed')
            }
        })
    }

    $('#btn-assingmentBuilding').click(function(e){
        e.preventDefault();
        var json = {};
        json['buildingId'] = $('#buildingId').val();
        var staffIds = $('#staff-list').find('tbody input[type=checkbox]:checked').map(function(){
            return $(this).val();
        }).get();
        json['staffIds'] = staffIds;
        if(json['buildingId'] != '' ){
            updateAssingment(json);
        }
        else{
            alert("BuildingId Not Null");
        }

    });
    $('#btn-deleteBuilding').click(function(e){
        e.preventDefault();
        var data = {};
        var ids = $('#building-list').find('tbody input[type=checkbox]:checked').map(function(){
            return $(this).val();
        }).get();
        data['ids'] = ids;
        if(data['ids'] != ''){
            deleteBuilding(data['ids'])
        }
        else{
            alert("No Building choose")
        }

    });
    // begin Ajax
    function deleteBuilding(data){
        //console.log("http://localhost:8084/api/building/" + data)
        $.ajax({
            url:"/api/buildings/" + data,
            type: "DELETE",
            //	data : JSON.stringify(data), // convert du lieu tu Object trong js qua cho thanh json
            dataType : "JSON", // kieu du lieu server tra ra
            //	contentType : 'application/json',
            success : function(response){
                console.log('success');
                alert(response.message);
                window.location.href='<c:url value="/admin/building-list" />'
            },
            error : function(response){
                console.log('failed')
                alert(response.message);
            }
        })
    }
    function updateAssingment(data){
        $.ajax({
            url:"/api/assingments/building", // url cần gửi yêu cầu
            type: "POST",
            data : JSON.stringify(data), // convert du lieu tu Object trong js qua cho thanh json
            dataType : "JSON", // kieu du lieu server tra ra
            contentType : 'application/json', // chỉ định dữ liệu được gửi đi ở dạng json
            success : function(response){ // hàm callback khi thành công
                console.log('success');
                alert("Assingment Success");
                window.location.href='<c:url value="/admin/building-list" />'
            },
            error : function(response){
                console.log('failed');
                alert("Assingment Failed");
                window.location.href='<c:url value="/admin/building-list" />'
            }
        })
    }
    // end ajax
    // phần checkbox
    $(document).ready(function() {
        // Chọn tất cả checkbox khi checkbox ở thead được bấm
        $('thead input[type="checkbox"]').change(function() {
            let isChecked = $(this).prop('checked');
            $('tbody input[type="checkbox"]').prop('checked', isChecked);
        });

        // Kiểm tra khi checkbox trong tbody được thay đổi
        $('tbody input[type="checkbox"]').change(function() {
            let allChecked = $('tbody input[type="checkbox"]').length === $('tbody input[type="checkbox"]:checked').length;
            $('thead input[type="checkbox"]').prop('checked', allChecked);
        });
    });
</script>
</body>
</html>
