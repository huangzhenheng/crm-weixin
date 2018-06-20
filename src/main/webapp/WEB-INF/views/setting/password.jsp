<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <title>CRM|修改密码</title>
    <%@ include file="../include/includeCSS.jsp" %>

</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="../include/mainHeader.jsp" %>
    <%@ include file="../include/leftSide.jsp" %>


    <div class="content-wrapper">

        <section class="content">

            <div class="modal-content">

                <div class="row">
                    <div class="col-xs-12">
                        <div class="box box-primary">
                            <div class="modal-header">
                                <h4 class="modal-title" id="myModalLabel">修改密码</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-body">
                    <form id="changePWfrom" class="form-horizontal" method="post" >
                        <div class="form-group">
                            <label class="col-sm-4 control-label">旧密码:</label>
                            <div class="col-sm-5">
                                <input type="password" class="form-control" placeholder="请输入旧密码"  name="oldpassword">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">新密码:</label>
                            <div class="col-sm-5">
                                <input type="password" class="form-control" placeholder="请输入新密码" id="newpassword" name="newpassword">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">确认密码:</label>
                            <div class="col-sm-5">
                                <input type="password" class="form-control" placeholder="请再次输入新密码" name="replypassword">
                            </div>
                        </div>

                    </form>
                </div>

                <div class="modal-footer">
                    <a href="/home"><button type="button" class="btn btn-default" data-dismiss="modal" id="offBtn">取消</button></a>
                    <button type="button" class="btn btn-primary" id="editBtn">修改</button>
                </div>
            </div>
        </section>
    </div>
</div>

<%@ include file="../include/includeJS.jsp" %>
<script>

    $(function () {

        $("#changePWfrom").validate({

            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                oldpassword:{
                    required:true,
                    remote:"/user/validate/password"
                },
                newpassword:{
                    required:true,
                    rangelength:[6,18]
                },
                replypassword:{
                    required:true,
                    rangelength:[6,18],
                    equalTo:"#newpassword"
                }
            },
            messages:{
                oldpassword:{
                    required:"*请输入旧密码",
                    remote:"*旧密码错误"
                },
                newpassword:{
                    required:"*请输入新密码",
                    rangelength:"*密码长度6~18位"
                },
                replypassword:{
                    required:"*请输入确认密码",
                    rangelength:"*密码长度6~18位",
                    equalTo:"*两次密码输入不一致"
                }
            },
            submitHandler:function(form){
                var password = $("#newpassword").val();
                $.post("/user/password",{"password":password}).done(function(data){
                    if(data == 'success') {
                        alert("密码修改成功，点击确定重新登录");
                        window.location.href = "/";
                    }
                }).fail(function(){
                    alert("服务器异常");
                });
            }

        });

        $("#editBtn").click(function(){
            $("#changePWfrom").submit();
        });

    });
</script>
</body>
</html>
