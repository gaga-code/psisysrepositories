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
					
					<form action="customer/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="SOBOOKS_ID" id="SOBOOKS_ID" value="${pd.SOBOOKS_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">企业名称:</td>
								<td style="padding-top: 13px;">${pd.ENTERPRISENAME}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td style="padding-top: 13px;">${pd.PHONE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">传真:</td>
								<td style="padding-top: 13px;">${pd.FAX}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">邮编:</td>
								<td style="padding-top: 13px;">${pd.MAILCODE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">法人代表:</td>
								<td style="padding-top: 13px;">${pd.LEGALREP}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电子邮件:</td>
								<td style="padding-top: 13px;">${pd.EMAIL}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">网址:</td>
								<td style="padding-top: 13px;">${pd.NETADDRESS}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开户银行:</td>
								<td style="padding-top: 13px;">${pd.OPENBANK}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">银行账号:</td>
								<td style="padding-top: 13px;">${pd.BANKACCOUNT}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td style="padding-top: 13px;">${pd.ADDRESS}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属分支:</td>
								<td style="padding-top: 13px;">${pd.SUBBRANCH}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属总部:</td>
								<td style="padding-top: 13px;">${pd.SUBHEADQUARTER}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td style="padding-top: 13px;">${pd.NOTE}</td>
							</tr>
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


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		</script>
</body>
</html>