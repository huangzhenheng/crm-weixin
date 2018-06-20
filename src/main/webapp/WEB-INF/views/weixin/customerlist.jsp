<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的客户</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
	<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">
    
    
    
</head>
<body>

<div class="weui-search-bar" id="searchBar">
  <form class="weui-search-bar__form">
    <div class="weui-search-bar__box">
      <i class="weui-icon-search"></i>
      <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required="">
      <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
    </div>
    <label class="weui-search-bar__label" id="searchText">
      <i class="weui-icon-search"></i>
      <span>搜索</span>
    </label>
  </form>
  <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
</div>

<div class="weui_cells weui_cells_access">
    <c:forEach items="${customerList}" var="cust">
        <a class="weui_cell" href="/wx/customer/${cust.id}">
            <div class="weui_cell_bd weui_cell_primary">
                <p>${cust.custname}</p>
            </div>
            <div class="weui_cell_ft"></div>
        </a>
    </c:forEach>
</div>

<script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>
</body>
</html>