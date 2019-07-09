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
					
						<div style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 10px;">订单编号:</td>
								<td style="padding-top: 10px;">${pd.ORDER_NUMBER}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 10px;">商品名称:</td>
								<td style="padding-top: 10px;">${pd.GOODS_NAME}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 10px;">出库时间:</td>
								<td style="padding-top: 10px;">${pd.OUTTIME}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 10px;">数量:</td>
								<td style="padding-top: 10px;">${pd.INCOUNT}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 10px;">单价:</td>
								<td style="padding-top: 10px;">${pd.PRICE}&nbsp;元</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 10px;">总价:</td>
								<td style="padding-top: 10px;">${pd.ZPRICE}&nbsp;元</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 10px;">备注:</td>
								<td style="padding-top: 10px;">${pd.BZ}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 10px;">客户:</td>
								<td style="padding-top: 10px;">${pd.CUSTOMER_NAME}</td>
							</tr>
						</table>
						</div>
						
						<table id="table_report" class="table table-striped table-bordered table-hover">
						<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary"  onClick="window.print();">打印</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
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


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		</script>
</body>
</html>