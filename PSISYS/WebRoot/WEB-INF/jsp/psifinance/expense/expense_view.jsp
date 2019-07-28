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
					
					<form action="expense/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PK_SOBOOKS" id="PK_SOBOOKS" value="${pd.PK_SOBOOKS}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						
							<tr>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<td style="text-align: center;" colspan="10"><font size="6">费用开支单详情</font></td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<td style="text-align: center;" colspan="10">
<!-- 									<a class="btn btn-mini btn-primary" onclick="save();">保存</a> -->
									<a class="btn btn-mini btn-danger" onclick="returnList();">返回费用开支单列表</a>
								</td>
							</tr>
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单据编号:</td>
								<td><input type="text" name="BILLCODE" id="BILLCODE" value="${pd.BILLCODE}" maxlength="1000"    style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">录入日期:</td>
								<td><input type="text" name="LDATE" id="LDATE" value="${pd.LDATE}" maxlength="1000"  style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">经手人:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.PSI_NAME}" maxlength="1000"   style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">付款来源:</td>
								<td><input type="text" name="PAYORIGIN" id="PAYORIGIN" value="${pd.PAYORIGIN}" maxlength="1000"   style="width:98%;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">付款方式:</td>
								<td><input type="text" name="PAYMETHOD_NAME" id="PAYMETHOD_NAME" value="${pd.PAYMETHOD_NAME}" maxlength="1000"    style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">付款金额:</td>
								<td><input type="text" name="PAYAMOUNT" id="PAYAMOUNT" value="${pd.PAYAMOUNT}" maxlength="1000"  style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">票号:</td>
								<td><input type="text" name="TICKETNUM" id="TICKETNUM" value="${pd.TICKETNUM}" maxlength="1000"   style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="NOTE" id="NOTE" value="${pd.NOTE}" maxlength="1000"   style="width:98%;" readonly="readonly"/></td>
							</tr>
							
							<input id = "expensebodylist" name ="expensebodylist" type="hidden"/>
						</table>
						<table name="goodstable" id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">费用名称</th>
									<th class="center">金额</th>
									<th class="center">备注</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty pd.expensebodylist}">
<%-- 									<c:if test="${QX.cha == 1 }"> --%>
									<c:forEach items="${pd.expensebodylist}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.INOUTCOMETYPE_NAME}</td>
											<td class='center'>${var.AMOUNT}</td>
											<td class='center'>${var.NOTE}</td>
										</tr>
									
									</c:forEach>
<%-- 									</c:if> --%>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
<!-- 						<a class="btn btn-mini btn-primary" onclick="addgoods();">添加商品</a> -->
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
					<form action="#" method="post" name="actionForm" id="actionForm"></form>
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

<!-- 	<script src="https://libs.baidu.com/jquery/1.4.2/jquery.min.js"></script> -->
<!-- 	<script src="static/js/jquery-1.7.2.js" type="text/javascript"></script>  -->
	<script src="static/js/jquery.cookie.js" type="text/javascript"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
	
		function returnList(){
			var url = 'expense/list.do';
			document.forms.actionForm.action=url;
	        document.forms.actionForm.submit();
		}

		
		</script>
</body>
</html>