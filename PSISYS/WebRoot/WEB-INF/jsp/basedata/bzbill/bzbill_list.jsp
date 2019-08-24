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
					
				
						<input type="hidden" name="PK_SOBOOKS" id="PK_SOBOOKS" value="${pd.PK_SOBOOKS}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标题:</td>
								<td style="padding-top: 13px;">${pd.TITLE}</td>
						
								<td style="width:75px;text-align: right;padding-top: 13px;">公司电话:</td>
								<td style="padding-top: 13px;">${pd.TELEPHONE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">收款人:</td>
								<td style="padding-top: 13px;">${pd.ACCOUNTNAME}</td>
							
								<td style="width:75px;text-align: right;padding-top: 13px;">开户银行:</td>
								<td style="padding-top: 13px;">${pd.ACCOUNTBANK}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">银行账号:</td>
								<td style="padding-top: 13px;">${pd.ACCOUNT}</td>
						
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td style="padding-top: 13px;">${pd.NOTE}</td>
							</tr>
							<tr>
								<td>二维码 ：</td>
								<td> 
									<a href="<%=basePath%>uploadFiles/uploadImgs/${pd.PICTURE}" target="_blank"><img src="<%=basePath%>uploadFiles/uploadImgs/${pd.PICTURE}" width="100"/></a>
								</td>
							</tr>
						</table>
						</div>
						<c:if test="${QX.edit == 1 }">
							<a class="btn btn-mini btn-success" onclick="edit('${pd.PK_SOBOOKS}');">修改</a>
						</c:if>
						<a class="btn btn-mini btn-success" onclick="duizhang()">导出</a>
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
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 页面底部js¨ -->
		<script type="text/javascript">
		$(top.hangge());
		
		
		//修改
		function edit(PK_SOBOOKS){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>bzbill/goEdit.do?PK_SOBOOKS='+PK_SOBOOKS;
			 diag.Width = 400;
			 diag.Height = 400;
			 diag.Modal = false;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//打印
		function duizhang(){
			bootbox.confirm("确定要打印客户对账单吗?", function(result) {
				if(result) {
					window.location.href='<%=basePath%>daying/duizhang.do';
				}
			});
		}
		
		</script>
		
		
		
</body>
</html>