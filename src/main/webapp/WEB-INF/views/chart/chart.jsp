<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html>
<head>
    <title>CRM|统计</title>
    <%@ include file="../include/includeCSS.jsp" %>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="../include/mainHeader.jsp"%>
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="chart"/>
    </jsp:include>


    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="font-family: 宋体">
        <!-- Main content -->
        <section class="content">
            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">统计区间</h3>
                </div>
                <div class="box-body">
                    <form method="get" class="form-inline" id="searForm">
                        <input type="hidden" id="search_start_time" name="start">
                        <input type="hidden" id="search_end_time" name="end">
                        <input type="text" id="getTime"class="form-control" style="width:200px;"
                               placeholder="统计时间段">
                        <button type="button" id="searchBtn" class="btn btn-default">统计</button>
                    </form>
                </div>
            </div>

            <div class="row">
                <div class="col-md-4">
                    <div class="info-box">
                        <span class="info-box-icon bg-aqua"><i class="fa fa-plus"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text">${not empty msg ? msg:"本月"}新增客户数量</span>
                            <span class="info-box-number">${addCustCount}</span>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="info-box">
                        <span class="info-box-icon bg-green"><i class="fa fa-flag"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text">${not empty msg ? msg:"本月"}完成交易次数</span>
                            <span class="info-box-number">${successCount}</span>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="info-box">
                        <span class="info-box-icon bg-red"><i class="fa fa-money"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text">${not empty msg ? msg:"本月"}交易额</span>
                            <span class="info-box-number">￥<fmt:formatNumber value="${saleMoney}"/></span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">销售机会统计</h3>
                </div>
                <div class="box-body">
                    <div id="pieChart" style="width: 100%;height: 300px"></div>
                </div>
            </div>
            <shiro:hasRole name="经理">
                <div class="box box-primary">
                    <div class="box-header">
                        <h3 class="box-title">员工业绩图</h3>
                    </div>
                    <div class="box-body">
                        <div id="barChart" style="width: 100%;height: 300px"></div>
                    </div>
                </div>
            </shiro:hasRole>
        </section>
        <!-- /.content -->
    </div>
    <!-- Control Sidebar -->

</div>


<%@ include file="../include/includeJS.jsp" %>
<script>
    $(function(){

        //饼图，销售机会统计
        var pieChart = echarts.init($("#pieChart")[0]);
        pieChart.setOption({
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series : [
                {
                    name: '销售机会',
                    type: 'pie',
                    radius: '75%',
                    data:[],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        });
        // 异步加载数据
        $.get("/chart/progress/data").done(function (data) {
            // 填入数据
            pieChart.setOption({
                series: [{
                    // 根据名字对应到相应的系列
                    data: data
                }]
            });
        });


        <shiro:hasRole name="经理">
        //柱图，员工业绩图
        var barChart = echarts.init($("#barChart")[0]);
        // 指定图表的配置项和数据
        var option = {
            tooltip: {},
            xAxis: {
                data: []
            },
            yAxis: {},
            series: [{
                name: '业绩',
                type: 'bar',
                data: []
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        barChart.setOption(option);
        $.get("/chart/user/money",function(data){
            barChart.setOption({
                xAxis: {
                    data: data.names
                },
                series: [{
                    data: data.values
                }]
            });
        });
        </shiro:hasRole>


        $("#searchBtn").click(function () {

          $("#searForm").submit();
        });
        //统计时间段的设置
        $("#getTime").daterangepicker({
            format: "YYYY-MM-DD",
            separator: "～",
            opens: 'right',
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
