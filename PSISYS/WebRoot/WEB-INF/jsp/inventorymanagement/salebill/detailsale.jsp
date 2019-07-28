<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
	
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="#" name="Form" id="Form" method="post">
						<div id="zhongxin" style="padding-top: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;
							
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:50px;text-align: right;padding-top: 13px;padding-left: 0px;padding-right: 0px;">零售价:</td>
								<td><input type="text" name="RPRICE" id="RPRICE"  maxlength="1000" style="width:98%;"  value="${pdlist[0].RPRICE}" readonly="readonly"/></td>
								<td style="width:50px;text-align: right;padding-top: 13px;padding-left: 0px;padding-right: 0px;">成本价:</td>
								<td><input type="text" name="CPRICE" id="CPRICE"  value="${pdlist[0].CPRICE}" maxlength="1000" style="width:98%;"  readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;padding-left: 0px;padding-right: 0px;">最后进价:</td>
								<td><input type="text" name="LASTPPRICE" id="LASTPPRICE"  value="${pdlist[0].LASTPPRICE}" maxlength="1000" style="width:98%;"  readonly="readonly"/></td>
							</tr>
						
						</table>
						<table name="goodstable" id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">序号</th>
									<th class="center">售价</th>
									<th class="center">时间</th>
									<th class="center">经手人</th>
								</tr>
							</thead>
							
							<tbody id ="tbody">
								<c:forEach items="${pdlist}" var="pd" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>${pd.RPRICE}</td>
										<td>${pd.CREATETIME}</td>
										<td>${pd.PSI_NAME}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
					</form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->




<!-- <script src="common/jquery-1.4.2.js" type="text/javascript"></script> -->
<!--    <script src="common/jquery-1.7.2.js" type="text/javascript"></script> -->

	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	
<!-- 	<script src="https://libs.baidu.com/jquery/1.4.2/jquery.min.js"></script> -->
<!-- 	<script src="static/js/jquery-1.7.2.js" type="text/javascript"></script>  -->
	<script src="static/js/jquery.cookie.js" type="text/javascript"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
		<script type="text/javascript">
		$(top.hangge());
	
		
		function returnList(){
			var url = 'salebill/list.do';
			document.forms.actionForm.action=url;
	        document.forms.actionForm.submit();
		}

	
		</script>
</body>
</html>