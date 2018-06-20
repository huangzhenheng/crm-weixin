<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html>
<head>
    <title>CRM|销售机会</title>
	<%@ include file="../include/includeCSS.jsp" %>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="../include/mainHeader.jsp" %>
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="sales"/>
    </jsp:include>


    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="font-family: 宋体">

        <!-- Main content -->
        <section class="content">
            <div class="box box-default collapsed-box">
                <div class="box-header with-border">
                    <h3 class="box-title"><i class="fa fa-search"></i>搜索</h3>
                    <div class="box-tools">
                        <button class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"><i
                                class="fa fa-plus"></i></button>
                    </div>
                </div>
                <div class="box-body">
                    <form method="get" class="form-inline">
                        <input type="hidden" id="search_start_time">
                        <input type="hidden" id="search_end_time">
                        <input type="text" class="form-control" placeholder="机会名称" name="name" id="s_name">
                        <select name="progress" class="form-control" class="form-control" id="s_progress">
                            <option value="">-机会进度-</option>
                            <option value="初次接触">初次接触</option>
                            <option value="发送报价">发送报价</option>
                            <option value="确认意向">确认意向</option>
                            <option value="提供合同">提供合同</option>
                            <option value="完成交易">完成交易</option>
                            <option value="交易搁置">交易搁置</option>
                        </select>
                        <input type="text" id="getTime" name="getTime" class="form-control" style="width:200px;"
                               placeholder="跟进时间">
                        <button type="button" id="searchBtn" class="btn btn-default"><i class="fa fa-search"></i> 搜索
                        </button>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">机会列表</h3>
                            <a href="javascript:;" class="btn btn-xs btn-success pull-right" id="newSaleBtn"><i
                                    class="fa fa-plus"></i> 新增机会</a>
                        </div>
                        <div class="box-body">
                            <table id="dataTable" class="table">
                                <thead>
                                <tr>
                                    <th>机会名称</th>
                                    <th>关联客户</th>
                                    <th>价值</th>
                                    <th>当前进度</th>
                                    <th>最后跟进时间</th>
                                    <shiro:hasRole name="经理">
                                        <th>所属员工</th>
                                    </shiro:hasRole>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- Control Sidebar -->
</div>

<!-- Modal ,添加新机会-->
<div class="modal fade" id="newSaleModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增机会</h4>
            </div>
            <div class="modal-body">
                <form id="newSaleForm" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">机会名称</label>
                        <div class="col-sm-5">
                            <input type="text" autofocus class="form-control" name="name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">关联客户</label>
                        <div class="col-sm-5">
                            <select name="customer.id" class="form-control" id="customerList"></select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">价值</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="price">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">当前进度</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="progress">
                                <option value="">-请选择进度-</option>
                                <option value="初次接触">初次接触</option>
                                <option value="发送报价">发送报价</option>
                                <option value="确认意向">确认意向</option>
                                <option value="提供合同">提供合同</option>
                                <option value="完成交易">完成交易</option>
                                <option value="交易搁置">交易搁置</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBtn">添加</button>
            </div>
        </div>
    </div>
</div>
<%@ include file="../include/includeJS.jsp" %>
<script>

    $(function () {

        var dataTable = $("#dataTable").DataTable({
            "lengthMenu": [5, 10, 25, 50],
            "ordering": false,
            "serverSide": true,
            "searching": false,
            "autoWidth": false,

            "ajax": {
                url: "/sales/load",
                data: function (dataSouce) {
                    dataSouce.name = $("#s_name").val();
                    dataSouce.progress = $("#s_progress").val();
                    dataSouce.startdate = $("#search_start_time").val();
                    dataSouce.enddate = $("#search_end_time").val();
                }
            },
            "columns": [
                {
                    "data": function (row) {
                        return "<a href='/sales/" + row.id + "'>" + row.name + "</a>";
                    }
                },

                {
                    "data": function (row) {
                        if (row.type == 'company') {
                            return "<a href='/customer/" + row.customer.id + "'>" + "<i class='fa fa-bank'></i> " + row.customer.custname + "</a>";
                        }
                        //alert(row.customer.companyname);
//                        return "<a href='/customer/" + row.custid + "'><i class='fa fa-user'></i> " + row.custname + "—" + row.customer.companyname + "</a>";
                        return "<a href='/customer/" + row.customer.id + "'><i class='fa fa-user'></i> " + row.customer.custname + "</a>";

                    }
                },
                {
                    "data": function (row) {
                        return "￥" + row.price;
                    }
                },
                {
                    "data": function (row) {
                        if (row.progress == "交易搁置") {
                            return '<span class="label label-danger">' + row.progress + '</span>';
                        } else if (row.progress == "完成交易") {
                            return '<span class="label label-success">' + row.progress + '</span>';
                        } else {
                            return row.progress;
                        }
                    }
                },
                {"data": "lasttime"},
                <shiro:hasRole name="经理">
                {"data": "customer.createUser.realname"}
                </shiro:hasRole>
            ],
            "language": {
                "search": "搜索：",
                "zeroRecords": "未匹配到相关数据",
                "lengthMenu": "显示 _MENU_ 条数据",
                "info": "从第 _START_ 条到 _END_ 条， 共 _TOTAL_ 条数据",
                "infoFiltered": "(源数据共 _MAX_ 条)",
                "loadingRecords": "加载中...",
                "processing": "处理中...",
                "paginate": {
                    "first": "首页",
                    "last": "末页",
                    "next": "下一页",
                    "previous": "上一页"
                }
            }
        });

        //  添加新机会
        $("#newSaleBtn").click(function () {

            $("#newSaleForm")[0].reset();

            $.get("/customer/customerList").done(function (data) {
                var $select = $("#customerList");
                $select.html("");
                $select.append("<option value=''>-请关联客户-</option>");
                if (data && data.length) {
                    for (var i = 0; i < data.length; i++) {
                        var customer = data[i];
                        var option = "<option value='" + customer.id + "'>" + customer.custname + "</option>";
                        $select.append(option);
                    }
                }
            }).fail(function () {
                alert("服务器异常");
            });

            $("#newSaleModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });

        $("#saveBtn").click(function () {

            $("#newSaleForm").submit();
        });

        $("#newSaleForm").validate({
            errorElement: 'span',
            errorClass: 'text-danger',
            rules: {
                name: {
                    required: true
                },
                price: {
                    required: true,
                    number: true
                },
                custid: {
                    required: true
                },
                progress: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "*请输入机会名称"
                },
                price: {
                    required: "*请输入价值",
                    number: "*价值为数字"
                },
                custid: {
                    required: "*请关联客户"
                },
                progress: {
                    required: "*请输入当前进度"
                }
            },
            submitHandler: function (form) {
                $.post("/sales/new", $(form).serialize()).done(function (data) {
                    if (data == "success") {
                        $("#newSaleModal").modal('hide');
                        dataTable.ajax.reload();
                    }
                }).fail(function () {
                    alert("服务器请求失败！");
                });
            }
        });

        //搜索
        $("#searchBtn").click(function () {

            if(!$("#getTime").val()){
                $("#search_start_time").val("");
                $("#search_end_time").val("");
            }
            dataTable.ajax.reload();
        });

        $("#getTime").daterangepicker({
            format: "YYYY-MM-DD",
            separator: "/",
            opens: 'left',
            locale: {
                "applyLabel": "选择",
                "cancelLabel": "取消",
                "fromLabel": "从",
                "toLabel": "到",
                "customRangeLabel": "自定义",
                "weekLabel": "周",
                "daysOfWeek": [
                    "一",
                    "二",
                    "三",
                    "四",
                    "五",
                    "六",
                    "日"
                ],
                "monthNames": [
                    "一月",
                    "二月",
                    "三月",
                    "四月",
                    "五月",
                    "六月",
                    "七月",
                    "八月",
                    "九月",
                    "十月",
                    "十一月",
                    "十二月"
                ],
                "firstDay": 1
            },
            ranges: {
                '今天': [moment(), moment()],
                '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                '最近7天': [moment().subtract(6, 'days'), moment()],
                '最近30天': [moment().subtract(29, 'days'), moment()],
                '本月': [moment().startOf('month'), moment().endOf('month')],
                '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            }
        });
        $('#getTime').on('apply.daterangepicker', function (ev, picker) {
            $("#search_start_time").val(picker.startDate.format('YYYY-MM-DD'));
            $("#search_end_time").val(picker.endDate.format('YYYY-MM-DD'));
        });


    });
</script>
</body>
</html>
