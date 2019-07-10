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
					
					<form action="accountset/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="SOBOOKS_ID" id="SOBOOKS_ID" value="${pd.SOBOOKS_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">企业名称:</td>
								<td><input type="text" name="ENTERPRISENAME" id="ENTERPRISENAME" value="${pd.ENTERPRISENAME}" maxlength="100" placeholder="这里输入企业名称" title="企业名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td><input type="text" name="PHONE" id="PHONE" value="${pd.PHONE}" maxlength="10" placeholder="这里输入电话" title="电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">传真:</td>
								<td><input type="text" name="FAX" id="FAX" value="${pd.FAX}" maxlength="32" placeholder="这里输入传真" title="传真" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">邮编:</td>
								<td><input type="text" name="MAILCODE" id="MAILCODE" value="${pd.MAILCODE}" maxlength="1000" placeholder="这里输入邮编" title="邮编" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">法人代表:</td>
								<td><input type="text" name="LEGALREP" id="LEGALREP" value="${pd.LEGALREP}" maxlength="32" placeholder="这里输入法人代表" title="法人代表" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电子邮件:</td>
								<td><input type="text" name="EMAIL" id="EMAIL" value="${pd.EMAIL}" maxlength="100" placeholder="这里输入电子邮件" title="电子邮件" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">网址:</td>
								<td><input type="text" name="NETADDRESS" id="NETADDRESS" value="${pd.NETADDRESS}" maxlength="32" placeholder="这里输入网址" title="网址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开户银行:</td>
								<td><input type="text" name="OPENBANK" id="OPENBANK" value="${pd.OPENBANK}" maxlength="1000" placeholder="这里输入开户银行" title="开户银行" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">银行账号:</td>
								<td><input type="text" name="BANKACCOUNT" id="BANKACCOUNT" value="${pd.BANKACCOUNT}" maxlength="1000" placeholder="这里输入银行账号" title="银行账号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td><input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" maxlength="1000" placeholder="这里输入地址" title="地址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属分支:</td>
								<td><input type="text" name="SUBBRANCH" id="SUBBRANCH" value="${pd.SUBBRANCH}" maxlength="1000" placeholder="这里输入所属分支" title="所属分支" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属总部:</td>
								<td><input type="text" name="SUBHEADQUARTER" id="SUBHEADQUARTER" value="${pd.SUBHEADQUARTER}" maxlength="1000" placeholder="这里输入所属总部" title="所属总部" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="NOTE" id="NOTE" value="${pd.NOTE}" maxlength="1000" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
		//保存
		function save(){
			if($("#ENTERPRISENAME").val()==""){
				$("#ENTERPRISENAME").tips({
					side:3,
		            msg:'请输入企业名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ENTERPRISENAME").focus();
			return false;
			}
			/* if($("#MONEY").val()==""){
				$("#MONEY").val(0);
			} */
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		</script>
</body>
</html>