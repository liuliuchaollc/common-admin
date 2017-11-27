<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE 2 | Dashboard</title>
    <base href="<%=basePath%>">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- css -->
    <jsp:include page="../common/css-resource.jsp"/>
    <link rel="stylesheet" href="static/plugins/datatables/dataTables.bootstrap.css">
    <!-- iCheck for checkboxes and radio inputs -->
    <link rel="stylesheet" href="static/plugins/iCheck/all.css">
    <%--
        <link rel="stylesheet" href="static/plugins/ztree/css/demo.css" type="text/css">--%>
    <link rel="stylesheet" href="static/plugins/ztree/css/metroStyle/metroStyle.css" type="text/css">

<style>
.bar {
    height: 18px;
    background: green;
    
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="../common/header.jsp"/>
    <!-- Left side column. contains the logo and sidebar -->
    <jsp:include page="../common/sidebar.jsp"/>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                资料列表
                <%--<small>Control panel</small>--%>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active">资料列表</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header" style="text-align: center;">
                        	<div class="box-title">
                                 <label style="height: 40px;padding-right: 30px;color: #3c8dbc;font-size: 30px;font-weight:bolder;">一  键  搜 索</label>
                            </div>
                            <div>
                            <h3 class="box-title" style="height: 40px;">
 								<form id="searchKeys">
	                                 <input type="text" onkeydown='if(event.keyCode==13){submitForm();return false;}' name="keys" style="float:left;width: 450px;height: 40px;border-color: #3c8dbc;" >
	                            	<button type="button" id="searchbutton" style="width: 150px;height:40px;margin-left:30px;float:left;color: #3c8dbc;font-size: 20px;font-weight:bold;" title="搜索一下">搜索一下</button>
 								</form>
                            </h3>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div  class="box-body container" style="border-top:1px solid #3c8dbc">
                           <div id="searchResultDiv" ></div>
                             <!-- 	<div style="width: 1000px;font-size: 13px;line-height: 2.54;word-wrap: break-word;word-break: break-word;">
                             		<h3 style="font-weight:700;line-height: 1.54;font-size:large;"><a style="text-decoration:underline;" href="#" target="_blank">一键搜索_百度百科</a></h3>
	                             	<div style="line-height: 1.54"> 
	                             	这款软件能够利用当今最流行的搜索引擎的搜索功能。
	                             	这是一个一站式的搜索解决方案。只需要输入您的搜索和查看结果。
	                             	搜索平台主要包括:谷歌、雅虎、百度、维基百科。这款软件能够利用当今最流行的搜索引擎的搜索功能。
	                             	这是一个一站式的搜索解决方案。只需要输入您的搜索和查看结果。
	                             	搜索平台主要包括:谷歌、雅虎、百度、维基百科。这款软件能够利用当今最流行的搜索引擎的搜索功能。
	                             	这是一个一站式的搜索解决方案。只需要输入您的搜索和查看结果。
	                             	搜索平台主要包括:谷歌、雅虎、百度、维基百科。</div>
	                             	<div style="line-height: 1.54">https://baike.baidu.com/item/一...  - 百度快照</div>
                             	</div> -->
		                    <div id="pageDiv" style="width: 1000px;text-align: center;display: none;margin-top: 30px;">
		                    	
		                    	<label id="searchpre" style="width: 150px;height:40px;color: #3c8dbc;font-size: 15px;font-weight:bold;" title="上一页">上一页</label>
		 						<label  id="searchnext" style="width: 150px;height:40px; color: #3c8dbc;font-size: 15px;font-weight:bold;" title="下一页">下一页</label>
		                    </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
<!--warn modal-->
<div class="modal modal-danger fade" id="warn_modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">警告</h4>
            </div>
            <div class="modal-body" id="text">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" data-dismiss="modal">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
    <jsp:include page="../common/footer.jsp"/>
    <div class="control-sidebar-bg"></div>
</div>
<jsp:include page="../common/script-resource.jsp"/>
<script src="static/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="static/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- iCheck 1.0.1 -->
<script src="static/plugins/iCheck/icheck.min.js"></script>

<script type="text/javascript" src="static/plugins/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="static/plugins/ztree/js/jquery.ztree.excheck.js"></script>


<script type="text/javascript" src="static/plugins/jQueryFileUpload/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="static/plugins/jQueryFileUpload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="static/plugins/jQueryFileUpload/jquery.fileupload.js"></script>
<script>
	function submitForm(){
		$("#searchbutton").click();
		
	}

    $(function () {
        
    	//全部查询（没有分页）
       /*  $("#searchbutton").click(function () {
            console.log("submit...");
            $.ajax({
                type: "POST",
                url: "ycsearch/ycsearch",
                data: getFormJson("#searchKeys"),
                dataType: "json",
                success: function (data) {
                    if (data.meta.success) {
                        //添加成功
                        var contents = data.data.list;
                        var i = 0;
                        $("#searchResultDiv").empty();
                        for(i = 0;i < contents.length;i++){
                        	
                        	title = contents[i].title;
                        	content = contents[i].content;
                        $("#searchResultDiv").append('<div style="width: 1000px;font-size: 13px;line-height: 2.54;word-wrap: break-word;word-break: break-word;">'    
                       +'<h3 style="font-weight:700;line-height: 1.54;font-size:large;">'
                       +'<a style="text-decoration:underline;" href="ycsearch/'+encodeURI(encodeURI(title))+'/downloadfile" target="_blank">'+title+'</a>'
                       +'</h3>'
                       +'<div style="line-height: 1.54">'+content+'</div> '  	
                       +'<div style="line-height: 1.54">http://www.allasset.com.cn/一...  -永诚资管所有</div>'
                       +'</div>');
                        
                       }
                        
                        $("#pageDiv").css('display','block');
                    } else {
                        modalShow("#warn_modal", data.meta.message);
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }); */
        
        
        var pageIndex = 1;
        var pageSize = 5;
        $("#searchbutton").click(function () {
            console.log("submit...");
            $.ajax({
                type: "POST",
                url: "ycsearch/"+pageSize+"/"+pageIndex+"/ycsearch",
                data: getFormJson("#searchKeys"),
                dataType: "json",
                success: function (data) {
                    if (data.meta.success) {
                        //添加成功
                        var contents = data.data.list;
                        var i = 0;
                        $("#searchResultDiv").empty();
                        for(i = 0;i < contents.length;i++){
                        	
                        	title = contents[i].title;
                        	content = contents[i].content;
                        $("#searchResultDiv").append('<div style="width: 1000px;font-size: 13px;line-height: 2.54;word-wrap: break-word;word-break: break-word;">'    
                       +'<h3 style="font-weight:700;line-height: 1.54;font-size:large;">'
                       +'<a style="text-decoration:underline;" href="ycsearch/'+encodeURI(encodeURI(title))+'/downloadfile" target="_blank">'+title+'</a>'
                       +'</h3>'
                       +'<div style="line-height: 1.54">'+content+'</div> '  	
                       +'<div style="line-height: 1.54">http://www.allasset.com.cn/一...  -永诚资管所有</div>'
                       +'</div>');
                        
                       }
                        
                        $("#pageDiv").css('display','block');
                    } else {
                        modalShow("#warn_modal", data.meta.message);
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        });
        
        $("#searchpre").click(function () {
            console.log("submit...");
            if(pageIndex-1 <= 0){
            	modalShow("#warn_modal", "已经是首页");
            	return;
            }
            pageIndex = pageIndex - 1;
            $.ajax({
                type: "POST",
                url: "ycsearch/"+pageSize+"/"+pageIndex+"/ycsearch",
                data: getFormJson("#searchKeys"),
                dataType: "json",
                success: function (data) {
                    if (data.meta.success) {
                        //添加成功
                        var contents = data.data.list;
                        var i = 0;
                        $("#searchResultDiv").empty();
                        for(i = 0;i < contents.length;i++){
                        	
                        	title = contents[i].title;
                        	content = contents[i].content;
                        $("#searchResultDiv").append('<div style="width: 1000px;font-size: 13px;line-height: 2.54;word-wrap: break-word;word-break: break-word;">'    
                       +'<h3 style="font-weight:700;line-height: 1.54;font-size:large;">'
                       +'<a style="text-decoration:underline;" href="ycsearch/'+encodeURI(encodeURI(title))+'/downloadfile" target="_blank">'+title+'</a>'
                       +'</h3>'
                       +'<div style="line-height: 1.54">'+content+'</div> '  	
                       +'<div style="line-height: 1.54">http://www.allasset.com.cn/一...  -永诚资管所有</div>'
                       +'</div>');
                        
                       }
                        
                        $("#pageDiv").css('display','block');
                    } else {
                    	pageIndex = pageIndex + 1;
                        modalShow("#warn_modal", "已经是首页");
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        });
        
        
        $("#searchnext").click(function () {
            console.log("submit...");
             
            pageIndex = pageIndex +1;
            $.ajax({
                type: "POST",
                url: "ycsearch/"+pageSize+"/"+pageIndex+"/ycsearch",
                data: getFormJson("#searchKeys"),
                dataType: "json",
                success: function (data) {
                    if (data.meta.success) {
                        //添加成功
                        var contents = data.data.list;
                        if(contents.length == 0){
                        	pageIndex = pageIndex - 1;
                        	 modalShow("#warn_modal", "已经到尾页");
                        	return;
                        }
                        var i = 0;
                        $("#searchResultDiv").empty();
                        for(i = 0;i < contents.length;i++){
                        	
                        	title = contents[i].title;
                        	content = contents[i].content;
                        $("#searchResultDiv").append('<div style="width: 1000px;font-size: 13px;line-height: 2.54;word-wrap: break-word;word-break: break-word;">'    
                       +'<h3 style="font-weight:700;line-height: 1.54;font-size:large;">'
                       +'<a style="text-decoration:underline;" href="ycsearch/'+encodeURI(encodeURI(title))+'/downloadfile" target="_blank">'+title+'</a>'
                       +'</h3>'
                       +'<div style="line-height: 1.54">'+content+'</div> '  	
                       +'<div style="line-height: 1.54">http://www.allasset.com.cn/一...  -永诚资管所有</div>'
                       +'</div>');
                        
                       }
                        
                        $("#pageDiv").css('display','block');
                    } else {
                    	pageIndex = pageIndex - 1;
                        modalShow("#warn_modal", "已经到尾页");
                    	return;
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        });
    });
    
    function modalShow(id, content) {
        $("#text").html(content);
        $(id).modal('show');
    }
</SCRIPT>
</body>
</html>
