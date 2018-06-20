<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
    <title>CRM | 待办事项</title>
 	<%@ include file="../include/includeCSS.jsp" %>
    <style>
        .todo-list > li .text {
            font-weight: normal;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/mainHeader.jsp" %>
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="task"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-8">
                    <div class="box box-solid">
                        <div class="box-body">
                            <div id="calendar"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="box box-danger">
                        <div class="box-header with-border">
                            <h3 class="box-title">已经延期的事项</h3>
                        </div>
                        <div class="box-body">
                            <ul class="todo-list">

                                <c:forEach items="${timeoutTaskList}" var="task">

                                    <li>
                                        <input type="checkbox" rel="${task.id}" class="check">
                                        <span class="text">${task.title}</span>
                                        <div class="tools">
                                            <a href="javascript:;" rel="${task.id}" class="chang"><span class="glyphicon glyphicon-ok"></span></a>&nbsp;&nbsp;&nbsp;&nbsp;
                                            <a href="javascript:;" rel="${task.id}" class="delLink" style="color: red">
                                                <i class="fa fa-trash-o" id="del"></i></a>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                            <%--<table id="dataTable" class="table">--%>
                                <%--<thead>--%>
                                <%--<tr>--%>
                                    <%--<th>h3已经延期的事项</th>--%>
                                <%--</tr>--%>
                                <%--</thead>--%>
                                <%--<tbody>--%>
                                <%--</tbody>--%>
                            <%--</table>--%>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->
<div class="modal fade" id="newModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增待办事项</h4>
            </div>
            <div class="modal-body">

                <form id="newForm" action="/task/new" method="post">

                    <div class="form-group">
                        <p>日程内容：<input type="text" class="input" name="title" id="task_title" style="width:320px"
                                       placeholder="记录你将要做的一件事..."></p>
                    </div>
                    <%--开始时间--%>
                    <div class="form-group">
                        <p>开始时间：<input type="text" name="start" id="start_time"></p>
                    </div>
                    <%--结束时间--%>
                    <div class="form-group">
                        <p id="p_endtime">结束时间：<input type="text" name="end" id="end_time"></p>
                    </div>
                    <%--提醒时间--%>
                    <div class="form-group">
                        <div>
                            <p>提醒时间：<select name="hour">
                                <option value=""></option>
                                <option value="00">00</option>
                                <option value="01">01</option>
                                <option value="02">02</option>
                                <option value="03">03</option>
                                <option value="04">04</option>
                                <option value="05">05</option>
                                <option value="06">06</option>
                                <option value="07">07</option>
                                <option value="08">08</option>
                                <option value="09">09</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                            </select>
                                <b>:</b>
                                <select name="min">
                                    <option value=""></option>
                                    <option value="0">0</option>
                                    <option value="5">5</option>
                                    <option value="10">10</option>
                                    <option value="15">15</option>
                                    <option value="20">20</option>
                                    <option value="25">25</option>
                                    <option value="30">30</option>
                                    <option value="35">35</option>
                                    <option value="40">40</option>
                                    <option value="45">45</option>
                                    <option value="50">50</option>
                                    <option value="55">55</option>
                                </select>
                            </p>
                        </div>
                    </div>
                    <div class="form-group">
                        <p>显示颜色：<input type="text" name="color" id="color" value="#61a5e8"></p>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade" id="eventModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">查看待办事项</h4>
            </div>
            <div class="modal-body">
                <form id="eventForm">
                    <input type="hidden" id="event_id">
                    <div class="form-group">
                        <label>待办内容</label>
                        <div id="event_title"></div>
                    </div>
                    <div class="form-group">
                        <label>开始日期 ~ 结束时间</label>
                        <div><span id="event_start"></span> ~ <span id="event_end"></span></div>
                    </div>
                    <div class="form-group">
                        <label>提醒时间</label>
                        <div id="event_remindtime"></div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <label id="succ" style="display:none" class="label label-success pull-left" data-dismiss="modal"><i
                        class="fa fa-check"></i> 已完成</label>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-danger" id="delBtn"><i class="fa fa-trash"></i> 删除</button>
                <button type="button"  class="btn btn-primary" id="doneBtn"><i class="fa fa-check"></i> 标记为已完成</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%@ include file="../include/includeJS.jsp" %>
<script>
    $(function () {

//        var dataTable = $("#dataTable").dataTable({
//            "bLengthChange":false,
//            "ordering": false,
//            "searching":false,
//            "serverSide": true,
//            "autoWidth": false,
//            "info":false,
//            paging:false,
//
//            "ajax": "/task/outtime",
//            "columns": [ //配置返回的JSON中[data]属性中数据key和表格列的对应关系
//
//                {"data": function(row){
//                    return '<ul class="todo-list"><li><input type="checkbox" rel="'+row.id+'" class="check">' +
//                            '<span class="text">'+row.title+'</span>'+
//                            '<div class="tools"><a href="javascript:;" rel="'+row.id+'" class="delLink" style="color: red">'+
//                            '<i class="fa fa-trash-o" id="del"></i></a></div></li></ul>';
//                }}
//            ],
//            "language": { //定义中文
//                "zeroRecords": "没有匹配的数据",
//                "loadingRecords": "加载中...",
//                "processing": "处理中...",
//            }
//        });



        //日历
        var _event = null;
        var $calendar = $("#calendar");

        $calendar.fullCalendar({

            lang: 'zh-cn',

            //header 定义按钮/文本在日历的顶部,false说明不使用header
            header: {
                left: 'prev,next today prevYear,nextYear',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },

            //定义header中使用的按钮的显示文本
            buttonText: {
                today: '今天',
                month: '月',
                week: '周',
                day: '日',
                prevYear: "上一年",
                nextYear: "下一年"
            },
            firstDay: 1, //设置每星期的第一天是星期几，0 是星期日
            editable: true,//允许拖动

            eventDrop: function (event, dayDelta, minuteDelta, allDay, revertFunc) {    //拖拽事项之后的方法，allDay 是否全天事项，其他参数同上

                var id = event.id;
                var start = event.start.format();
                var end = event.end.format();
                $.get("/task/action", {id: id, start: start, end: end}, function (result) {

                    if (result == "success") {
                        $calendar.fullCalendar('updateEvent', id);
                    }

                });
            },
            eventResize: function (event, delta, revertFunc) {
                var id = event.id;
                var start = event.start.format();
                var end = event.end.format();
                $.get("/task/action", {id: id, start: start, end: end}, function (result) {

                    if (result == "success") {
                        $calendar.fullCalendar('updateEvent', id);
                    }
                });
            },
//          events: "/task/load",//提供一个URL, 该URL返回一个json或数组的日程事件组
             events: function (start, end, callback) {
                var start = start.format();
                var end = end.format();
                $.get("/task/load", {start: start, end: end}, function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var obj = new Object();
                        obj.id = data[i].id;
                        obj.title = data[i].title;
                        obj.color = data[i].color;
                        obj.remindertime = data[i].remindertime;
                        obj.start = data[i].start;
                        obj.end = data[i].end;
                        obj.done = data[i].done;
                        $calendar.fullCalendar('renderEvent', obj, true);
                    }
                });

            },
            //  点击天，弹出添加待办事项的摸态框
            dayClick: function (date) {//date：当前点击到的日期

                $("#newForm")[0].reset();
                $("#start_time").val(date.format());
                $("#end_time").val(date.format());
                $("#newModal").modal({
                    show: true,
                    backdrop: 'static'
                });
            },

            //点击待办事项，弹出查看待办事项的具体状态
            eventClick: function (calEvent) {

                _event = calEvent;
                $("#event_id").val(calEvent.id);
                $("#event_title").text(calEvent.title);
                $("#event_start").text(moment(calEvent.start).format("YYYY-MM-DD"));
                $("#event_end").text(moment(calEvent.end).format("YYYY-MM-DD"));

                if (calEvent.remindertime) {
                    $("#event_remindtime").text(calEvent.remindertime);
                } else {
                    $("#event_remindtime").text('无');
                }
                $("#eventModal").modal({
                    show: true,
                    backdrop: 'static'
                });
                if (calEvent.done == 1) {
                    $("#doneBtn").hide();
                    $("#succ").show();
                } else {
                    $("#doneBtn").show();
                    $("#succ").hide();
                }
            }
        });

        //颜色选择器
        $("#color").colorpicker({
            color: '#61a5e8'
        });

        // 开始时间和结束时间的格式
        $("#start_time,#end_time").datepicker({
            format: 'yyyy-mm-dd ',
            autoclose: true,
            language: 'zh-CN',
            todayHighlight: true
        });

        $("#saveBtn").click(function () {
            if (!$("#task_title").val()) {
                $("#task_title").focus();
                return;
            }
            if (!$("#start_time").val()) {
                $("#start_time").focus();
                return;
            }
            if (!$("#end_time").val()) {
                $("#end_time").focus();
                return;
            }
            if (moment($("#start_time").val()).isAfter(moment($("#end_time").val()))) {
                alert("结束时间必须大于开始时间");
                return;
            }

            $.post("/task/new", $("#newForm").serialize()).done(function (result) {
                if (result.state == "success") {
                    //将返回的日程，渲染到日历控件上
                    $calendar.fullCalendar('renderEvent', result.data);
                    $("#newModal").modal('hide');
                }
            }).fail(function () {
                alert("服务器异常");
            });
        });

        //删除日程
        $("#delBtn").click(function () {
            var id = $("#event_id").val();
            if (confirm("确定要删除吗")) {
                $.get("/task/del/" + id).done(function (data) {
                    if ("success" == data) {
                        $calendar.fullCalendar('removeEvents', id);
                        $("#eventModal").modal('hide');
                    }
                }).fail(function () {
                    alert("服务器异常");
                });
            }
        });

        //将日程标记为已完成
        $("#doneBtn").click(function () {
            var id = $("#event_id").val();
            $.post("/task/" + id + "/done").done(function (result) {
                if (result.state == "success") {
                    _event.color = "#cccccc";
                    $calendar.fullCalendar('updateEvent', _event);
                    window.history.go(0);
                    $("#eventModal").modal('hide');
                }
            }).fail(function () {
                alert("服务器异常");
            });
        });


        //删除延期事项
        var $id = null;
        $(document).delegate(".check", "change", function (){
            if ($(this).prop("checked")) {
                $id=$(this).attr("rel");
            }else {
                $id = null;
            }
        });

        $(document).delegate(".delLink", "click", function () {

            var $taskid = $(this).attr("rel");

                if ($id == $taskid) {
                    if (confirm("确定要删除吗？")) {
                        $.get("/task/del/" + $taskid).done(function (data) {
                            if ("success" == data) {
                                $calendar.fullCalendar('removeEvents', $taskid);
                                $id = null;
                                window.history.go(0);
                            }
                        }).fail(function () {
                            alert("服务器异常");
                        });
                    }
                }

        });

        $(document).delegate(".chang", "click", function () {

            var $taskid = $(this).attr("rel");
            if ($id == $taskid) {
                if (confirm("要标记为完成吗？")) {
                    $.post("/task/" + $id + "/done").done(function (result) {
                        if (result.state == "success") {
                            window.history.go(0);
                        }
                    }).fail(function () {
                        alert("服务器异常");
                    });
                }
            }

        });

    });
</script>
</body>
</html>