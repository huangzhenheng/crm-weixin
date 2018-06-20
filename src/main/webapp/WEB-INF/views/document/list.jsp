<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html>
<head>
    <title>CRM|文档管理</title>
    <%@ include file="../include/includeCSS.jsp" %>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="../include/mainHeader.jsp" %>
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="document"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="font-family: 宋体">
        <!-- Main content -->
        <section class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">文档管理</h3>

                    <c:if test="${fid != 0}">
                        <a href="${url}" class="btn btn-xs btn-success"><i class="fa  fa-level-up"></i> 返回</a>
                    </c:if>

                    <div class="box-tools pull-right">
                        <span id="picker"><span class="text btn btn-xs btn-success"><i class="fa fa-upload"></i> 上传文件</span></span>
                        <a id="newDir" href="#" class="btn btn-xs btn-bitbucket"><i class="fa fa-folder"></i> 新建文件夹</a>
                    </div>
                </div>

                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>名称</th>
                            <th>大小</th>
                            <th>创建人</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty documents}">
                            <tr>
                                <td colspan="5">暂时没有任何文件</td>
                            </tr>
                        </c:if>
                        <c:forEach var="doc" items="${documents}">

                            <tr>
                                <c:choose>
                                    <c:when test="${doc.type == 'dir'}">
                                        <td><i class="fa fa-folder"></i></td>
                                        <td><a href="/document?fid=${doc.id}">${doc.name}</a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><i class="fa  fa-file-text-o"></i></td>
                                        <td><a href="/document/download/${doc.id}">${doc.name}</a></td>
                                    </c:otherwise>
                                </c:choose>
                                <td>${doc.size}</td>
                                <td>${doc.createuser.realname}</td>
                                <td><fmt:formatDate value="${doc.createtime}" pattern="y-M-d H:m"/></td>
                                <td><c:if test="${doc.type == 'doc'}"><a href="/document/del/${fid}/${doc.id}" class="btn btn-xs btn-danger delLink">删除</a></c:if></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

            </div>

        </section>
    </div>
    <!-- Control Sidebar -->
</div>

<div class="modal fade" id="dirModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新建文件夹</h4>
            </div>
            <div class="modal-body">
                <form action="/document/dir/new" method="post" id="saveDirForm">
                    <input type="hidden" name="fid" value="${fid}">
                    <div class="form-group">
                        <label>文件夹名称</label>
                        <input type="text" class="form-control" name="name" id="dirname">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveDirBtn">保存</button>
            </div>
        </div>
    </div>
</div>
<%@ include file="../include/includeJS.jsp" %>

<script>
    $(function () {

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
