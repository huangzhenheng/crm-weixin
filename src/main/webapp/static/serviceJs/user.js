$(function () {
    	
        var dataTable = $("#dataTable").DataTable({
            "lengthMenu": [5, 10, 25, 50],
            "ordering": false,
            "serverSide": true,
            "autoWidth": false,
            "ajax": "/admin/data.json",
            "columns": [ //配置返回的JSON中[data]属性中数据key和表格列的对应关系
                {"data": "id"},
                {"data": "username"},
                {"data": "realname"},
                {"data": "weixin"},
                {"data": "tel"},
                {"data": "email"},
                
                {"data":function(row){
                    var roleArray = row.roleList;
                    var str = "";
                    for(var i = 0;i < roleArray.length;i++) {
                        str += roleArray[i].rolename + " ";
                    }
                    return str;
                }},
                {
                    "data": function (row) {
                        if (row.status == 1) {
                            return "<span class='label label-success'>正常</span>";
                        } else {
                            return "<span class='label label-danger'>禁用</span>";
                        }
                    }
                },
                {"data":function(row){
                    var timestamp = row.createtime;
                    var day = moment(timestamp);
                    return day.format("YYYY-MM-DD HH:mm");
                }},
                {
                    "data": function (row) {
//                     	if (row.username != "king") {
                        if (row.username != "") {
                            return '<a href="javascript:;" rel="' + row.id + '" class="btn btn-success btn-xs editLink">修改</a>&nbsp;<a href="javascript:;" rel="' + row.id + '" class="btn btn-info btn-xs resetLink">密码重置</a>';
                        }else {
                            return "";
                        }
                    }
                }
            ],
            "columnDefs": [ //定义列的特征
                {targets: [0], visible: false}
            ],
            "language": { //定义中文
                "search": "搜索:",
                "zeroRecords": "没有匹配的数据",
                "lengthMenu": "显示 _MENU_ 条数据",
                "info": "显示第_START_至  _END_项结果，共 _TOTAL_项",
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

        $("#saveBtn").click(function () {
            $("#newUserForm")[0].reset(); //让表单进行重置
            $("#newUserModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });
        $("#saveBt").click(function () {
            $("#newUserForm").submit();
        });
        $("#newUserForm").validate({
            errorElement: 'span',
            errorClass: 'text-danger',
            rules: {
                username: {
                    required: true,
                    rangelength: [3, 18],
                    remote: "/admin/checkuser"
                },
                password: {
                    required: true,
                    rangelength: [6, 18]
                },
                realname: {
                    required: true,
                    rangelength: [2, 18]
                }
            },
            messages: {
                username: {
                    required: "*请输入用户名",
                    rangelength: "*用户名长度6~18位",
                    remote: "*该用户名已被占用"
                },
                password: {
                    required: "*请输入用密码",
                    rangelength: "*密码长度6~18位"
                },
                realname: {
                    required: "*请真实姓名",
                    rangelength: "*真实姓名长度6~18位"
                }
            },
            submitHandler: function (form) {
                $.post("/admin/new", $(form).serialize()).done(function (data) {
                    if (data == "success") {
                        $("#newUserModal").modal('hide');
                        dataTable.ajax.reload();
                    }
                }).fail(function () {
                    alert("服务器请求失败！");
                });
            }
        });

        $(document).delegate(".editLink", "click", function (date) {
            var id = $(this).attr("rel");
            $.get("/admin/" + id + ".json").done(function (result) {
                //将JSON数据显示在表单上
                if(result.state == "success"){
                    $("#e_userid").val(result.data.id);
                    $("#e_username").val(result.data.username);
                    $("#e_realname").val(result.data.realname);
                    $("#e_weixin").val(result.data.weixin);
                    $("#e_roleid").val(result.data.roleid);
                    $("#e_status").val(result.data.status.toString());
                    $("#editUserModal").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });

                }else {}

            }).fail(function () {
                alert("服务器请求异常！");
            });
        });
        $("#editBtn").click(function () {
            $("#editBookForm").submit();
        });

        $("#editBookForm").validate({
            errorElement: 'span',
            errorClass: 'text-danger',
            rules: {
                username: {
                    required: true,
                    rangelength: [3, 18]
                },
                realname: {
                    required: true,
                    rangelength: [2, 18]
                }
            },
            messages: {
                username: {
                    required: "*请输入用户名",
                    rangelength: "*用户名长度6~18位"
                },
                realname: {
                    required: "*请真实姓名",
                    rangelength: "*真实姓名长度6~18位"
                }
            },
            submitHandler: function (form) {
                $.post("/admin/edit", $(form).serialize()).done(function (data) {
                    if (data == "success") {
                        $("#editUserModal").modal('hide');
                        dataTable.ajax.reload();
                    }
                }).fail(function () {
                    alert("服务器请求失败！");
                });
            }
        });

        $(document).delegate(".resetLink", "click", function () {

            if (confirm("是否确定重置密码为：000000？")) {
                $.get("/admin/" + $(this).attr("rel") + "/resetpassword").done(function (data) {
                    if (data == "success") {
                        alert("密码重置成功！");
                    }
                }).fail(function () {
                    alert("服务器请求异常！");
                });

            }
        });


        $("#importBtn").click(function () {
            $("#importUsers").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });
        $("#saveUsers").click(function () {
            $("#users").submit();
        });
    });