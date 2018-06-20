<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <title>CRM|发表新公告</title>
    <%@ include file="../include/includeCSS.jsp" %>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="../include/mainHeader.jsp" %>
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="notice"/>
    </jsp:include>


    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="font-family: 宋体">
        <!-- Main content -->
        <section class="content">

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">新增公告</h3>
                </div>
                <div class="box-body">
                    <form method="post" id="newForm">
                        <div class="form-group">
                            <label>标题</label>
                            <input placeholder="请输入公告标题" type="text" name="title" class="form-control" id="title" autofocus>
                        </div>
                        <div class="form-group">
                            <label>公告内容</label>
                            <textarea placeholder="请输入公告内容" name="context" id="context" rows="10" class="form-control"></textarea>
                        </div>
                    </form>
                    <pre>
 	<code class="java">
 	 public String findAllUser(Model model) {
        model.addAttribute("role", roleMapper.findAllRole());
        return "admin/users";
    }
 	</code>
</pre>
                </div>
                <div class="modal-footer">
                    <button id="cancelBtn" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="saveBtn"  type="button" class="btn btn-primary">发表</button>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- Control Sidebar -->

</div>

<%@ include file="../include/includeJS.jsp" %>

	 	<link href="http://cdn.bootcss.com/highlight.js/8.0/styles/monokai_sublime.min.css" rel="stylesheet">
	    <script src="http://cdn.bootcss.com/highlight.js/8.0/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>

<script>
    $(function(){
        var edit = new Simditor({
            textarea:$("#context"),
            upload:{
                url:"/notice/img/upload",
                fileKey:"file"
            }
        });

        $("#saveBtn").click(function(){
            if(!$("#title").val()) {
                $("#title").focus();
                return;
            }
            if(!$("#context").val()) {
                $("#context").focus();
                return;
            }
            $("#newForm").submit();
        });

        $("#cancelBtn").click(function(){

            if (confirm("您确定要取消？")) {
                window.location.href = "/notice";
            }
        });

    });
</script>
</body>
</html>
