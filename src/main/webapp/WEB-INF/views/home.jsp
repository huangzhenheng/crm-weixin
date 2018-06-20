<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>CRM|主页</title>
  	<%@ include file="include/includeCSS.jsp" %>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="include/mainHeader.jsp"%>
    <jsp:include page="include/leftSide.jsp">
        <jsp:param name="menu" value="home"/>
    </jsp:include>


    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="font-family: 宋体">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div id="weatherBox"> </div>
            <div class="box-tools pull-right">
              	<span id="picker"><span class="text btn btn-xs btn-success"><i class="fa fa-upload"></i> 上传文件</span></span>
        	</div>
        </section>

        <!-- Main content -->
        <section class="content">

           <div class="container">
			    <div class="page-header">
			    	<h3>欢迎[<shiro:principal property="username"/>]登录 </h3>
			        <h3>电子词典</h3>
			        <shiro:user>  
		
					</shiro:user> 
			    </div>
			    <div class="form-group">
			        <input type="text" id="keyword" class="col-sm-4 " placeholder="请输入要翻译单词">
			        <button id="btn">翻译</button>
			    </div>
    			<p id="result"></p>
			
			</div>
  
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">新建邮件</h3>
                </div>
                <div class="box-body">
                    <form id="newEmail">
                        <div class="form-group">
                            <label>收件人</label>
                            <input placeholder="请输入收件人" type="text" name="roUser" class="form-control" id="title" autofocus>
                        </div>
                        <div class="form-group">
                            <label>主题</label>
                            <input placeholder="请输入主题" type="text" name="subject" class="form-control" >
                        </div>
                        <div class="form-group">
                            <label>邮件内容</label>
                            <textarea placeholder="请输入公告内容" name="context" id="context" rows="10" class="form-control"></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button id="cancelBtn" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="sendEmail"  type="button" class="btn btn-primary">发送</button>
                </div>
            </div>
            
            
        </section>
        <!-- /.content -->
    </div>
</div>

<%@ include file="include/includeJS.jsp" %>
<script>

$(function () {

    $("#btn").click(function(){
        var url = "http://fanyi.youdao.com/openapi.do?keyfrom=kaishengit&key=1587754017&type=data&doctype=jsonp&callback=?&version=1.1";
        var keyword = $("#keyword").val();
        $.getJSON(url,{"q":keyword},function(json){
            var array = json.basic.explains;
            var str = "";
            for(var i = 0;i < array.length;i++) {
                str += array[i] + "<br>";
            }
            $("#result").html(str);
        });
    });
    
    var $weatherBox = $("#weatherBox");
    $weatherBox.html("");
    $("#weatherBox").html("");
    $.get("/weather.xml", function (xml) {
        var city = $(xml).find("city").eq(1);
        var cityname = city.attr("cityname");
        var temNow = city.attr("temNow");
        var stateDetailed = city.attr("stateDetailed");
        var windState = city.attr("windState");
        var time = city.attr("time");
        $weatherBox.append("<div><h4>" + cityname + ":  " + temNow + "°C," + stateDetailed + "," + windState + "　发布时间：" + time + "</h4></div>");
    });
    
    var edit = new Simditor({
        textarea:$("#context"),
        upload:{
            url:"/notice/img/upload",
            fileKey:"file"
        }
    });
    
    
    $("#newEmail").validate({
        errorElement: 'span',
        errorClass: 'text-danger',
        rules: {
        	roUser: {
                required: true
            },
            subject: {
                required: true
            }
        },
        messages: {
        	roUser: {
                required: "请输入收件人地址"
            },
            subject: {
                required: "请输入主题"
            }
        },
        submitHandler: function (form) {
            $.post("/user/newEmail", $(form).serialize()).done(function (data) {
                if (data == "success") {
                   alert("");
                }
            }).fail(function () {
                alert("服务器请求失败！");
            });
        }
    });

    $("#sendEmail").click(function () {
    	$("#newEmail").submit();
    });
    
    
//  新建文件夹
    $("#newDir").click(function () {
        $("#saveDirForm")[0].reset();
        $("#dirModal").modal({
            show: true,
            backdrop: 'static',
            keyboard: false
        });
    });

    //  保存文件夹
    $("#saveDirBtn").click(function () {
        if (!$("#dirname").val()) {
            $("#dirname").focus();
            return;
        }
        $("#saveDirForm").submit();
    });

    //上传文件
    var uploader = WebUploader.create({
        swf: "/static/plugins/webuploader/Uploader.swf",
        server: "/document/savefile",
        pick: "#picker",
        fileVal: "file",
        formData:{"fid":"${fid}"},
        auto:true
    });


    uploader.on("startUpload",function(){
        $("#picker .text").html('<i class="fa fa-spinner fa-spin"></i> 上传中...');
    });

    uploader.on( 'uploadSuccess', function( file,data ) {
        if(data._raw == "success") {
            window.history.go(0);
        }
    });

    uploader.on( 'uploadError', function( file ) {
        alert("文件上传失败");
    });

    uploader.on( 'uploadComplete', function( file ) {
        $("#picker .text").html('<i class="fa fa-upload"></i> 上传文件').removeAttr("disabled");;
    });

});
</script>
</body>
</html>
