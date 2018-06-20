<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
    <title>CRM|员工管理</title>
	<%@ include file="../include/includeCSS.jsp" %>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="../include/mainHeader.jsp" %>
    <jsp:include page="../include/leftSide.jsp">
    	<jsp:param name="parentMenu" value="admin"/>
        <jsp:param name="menu" value="employee"/>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">用户列表</h3>
                            <a href="/admin/export" class="btn btn-xs btn-success pull-right"><i
                                    class="fa fa-arrow-down"></i> 导出用户</a>
                            <span class="pull-right">&nbsp;&nbsp;</span>
                            <a href="javascript:;" class="btn btn-xs btn-success pull-right" id="importBtn"><i
                                    class="fa fa-arrow-up"></i> 批量添加</a>
                            <span class="pull-right">&nbsp;&nbsp;</span>
                            <a href="javascript:;" class="btn btn-xs btn-success pull-right" id="saveBtn"><i
                                    class="fa fa-user-plus"></i> 新增用户</a>
                        </div>
                        <div class="box-body">
                            <table id="dataTable" class="table table table-bordered">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>用户名</th>
                                    <th>真实姓名</th>
                                    <th>微信号</th>
                                    <th>电话</th>
                                    <th>邮箱</th>
                                    <th>身份</th>
                                    <th>状态</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
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
    </div>
</div>

<!-- Modal ,添加新用户-->
<div class="modal fade" id="newUserModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel" >添加新用户</h4>
            </div>
            <div class="modal-body">
                <form id="newUserForm" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><em class="form-req">*</em>用户名</label>
                        <div class="col-sm-5">
                            <input type="text" autofocus class="form-control" placeholder="请输入用户名" name="username">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label"><em class="form-req">*</em>默认密码</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="请输入密码" name="password" value="000000">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><em class="form-req">*</em>真实姓名</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="请输入真实姓名" name="realname">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">微信号</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="请输入微信号" name="weixin">
                        </div>
                    </div>
  					<div class="form-group">
                        <label class="col-sm-4 control-label">电话</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="请输入电话" name="tel">
                        </div>
                    </div>
                      <div class="form-group">
                        <label class="col-sm-4 control-label">邮箱</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="请输入邮箱" name="email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><em class="form-req">*</em>身份</label>
                        <div class="col-sm-5">
	                       	<c:forEach items="${role}" var="rol">
	                       		<label class="checkbox-inline"><input type="checkbox" name="roleId" value="${rol.id}">${rol.rolename}</label>
	                        </c:forEach>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="status" id="status">
                                <option value="1">正常</option>
                                <option value="0">禁用</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBt">添加</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal ,修改用户-->
<div class="modal fade" id="editUserModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改用户</h4>
            </div>
            <div class="modal-body">
                <form id="editBookForm" class="form-horizontal">
                    <input type="hidden" name="id" id="e_userid">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户名</label>
                        <div class="col-sm-5">
                            <input type="text" disabled class="form-control" placeholder="请输入用户名" id="e_username"
                                   name="username">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">真实姓名</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="请输入真实姓名" id="e_realname"
                                   name="realname">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">微信号</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="请输入微信号" id="e_weixin" name="weixin">
                        </div>
                    </div>
					<div class="form-group">
                        <label class="col-sm-4 control-label">电话</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="请输入电话" name="tel">
                        </div>
                    </div>
                      <div class="form-group">
                        <label class="col-sm-4 control-label">邮箱</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="请输入邮箱" name="email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">身份</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="roleid" id="e_roleid">
                                <option value="">-请选择类别-</option>
                                <c:forEach items="${role}" var="rol">
                                    <option value="${rol.id}">${rol.rolename}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">身份</label>
                        <div class="col-sm-5">
	                       	<c:forEach items="${role}" var="rol">
	                       		<label class="checkbox-inline"><input type="checkbox" name="roleId" value="${rol.id}">${rol.rolename}</label>
	                        </c:forEach>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="status" id="e_status">
                                <option value="true">正常</option>
                                <option value="false">禁用</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="editBtn">修改</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal ,批量导入-->
<div class="modal fade" id="importUsers">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">批量添加用户</h4>
            </div>
            <div class="modal-body">
                <form id="users" action="/admin/import" method="post" class="form-horizontal"  enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">请选择Excel文件</label>
                        <div class="col-sm-5">
                            <input type="file" class="form-control" name="file">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveUsers">添加</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="../include/includeJS.jsp"%>
<script src="/static/serviceJs/user.js"></script>
</body>
</html>
