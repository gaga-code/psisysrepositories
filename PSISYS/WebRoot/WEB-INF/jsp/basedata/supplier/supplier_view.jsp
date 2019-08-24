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
					
					<form action="supplier/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="SUPPLIER_ID" id="SUPPLIER_ID" value="${pd.SUPPLIER_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">经手人:</td>
								<td style="padding-top: 13px;">${pd.PSI_NAME}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">供应商编号:</td>
								<td style="padding-top: 13px;">${pd.SUPPLIERCODE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">姓名:</td>
								<td style="padding-top: 13px;">${pd.SUPPLIERNAME}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">简称:</td>
								<td style="padding-top: 13px;">${pd.SIMPLENAME}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">拼音编码:</td>
								<td style="padding-top: 13px;">${pd.YICODE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机:</td>
								<td style="padding-top: 13px;">${pd.PHONE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">公司名称:</td>
								<td style="padding-top: 13px;">${pd.COMPANY}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td style="padding-top: 13px;">${pd.ADDRESS}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td style="padding-top: 13px;">${pd.TELEPHONE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">传真:</td>
								<td style="padding-top: 13px;">${pd.FAX}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">传呼:</td>
								<td style="padding-top: 13px;">${pd.PAGING}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">信誉程度:</td>
								<td style="padding-top: 13px;">${pd.CREDITDEGREE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系人:</td>
								<td style="padding-top: 13px;">${pd.LINKMAN}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">经销方式:</td>
								<td>
									<select name="DISTRIBUTIONMODE" id="DISTRIBUTIONMODE" placeholder="请选择" title="经销方式" style="width:98%;background-color:#EBEBEB" disabled="disabled">
									<c:forEach items="${varListL}" var="var">
										<option value="${var.LEVEL_ID }" <c:if test="${var.LEVEL_ID == pd.DISTRIBUTIONMODE }">selected</c:if>>${var.TITLE }</option>
									</c:forEach>
									</select>
								</td>
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
								<td style="width:75px;text-align: right;padding-top: 13px;">邮编:</td>
								<td style="padding-top: 13px;">${pd.MAILCODE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电子邮箱:</td>
								<td style="padding-top: 13px;">${pd.EMAIL}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">网址:</td>
								<td style="padding-top: 13px;">${pd.NETADDR}</td>
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