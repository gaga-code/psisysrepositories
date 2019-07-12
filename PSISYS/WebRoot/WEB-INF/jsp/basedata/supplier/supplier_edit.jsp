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
								<td style="width:79px;text-align: right;padding-top: 13px;">经手人:</td>
								<td><input type="text" name="PSI_NAME" id="PSI_NAME" readonly="readonly" value="${pd.PSI_NAME}" maxlength="30"  title="经手人" style="width:98%;"/></td>
							</tr>
							<c:if test="${msg == 'save' }">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">客户编号:</td>
								<td><input type="text" name="SUPPLIERCODE" id="SUPPLIERCODE" value="${pd.SUPPLIERCODE}" maxlength="100" placeholder="这里输入客户编号" title="客户编号" style="width:98%;"/></td>
							</tr>
							</c:if>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">姓名:</td>
								<td><input type="text" name="SUPPLIERNAME" id="SUPPLIERNAME" value="${pd.SUPPLIERNAME}" maxlength="100" placeholder="这里输入姓名" title="姓名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">简称:</td>
								<td><input type="text" name="SIMPLENAME" id="SIMPLENAME" value="${pd.SIMPLENAME}" maxlength="100" placeholder="这里输入简称" title="简称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">拼音编码:</td>
								<td><input type="text" name="YICODE" id="YICODE" value="${pd.YICODE}" maxlength="100" placeholder="这里输入拼音编码" title="拼音编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机:</td>
								<td><input type="number" name="PHONE" id="PHONE" value="${pd.PHONE}" maxlength="11" placeholder="这里输入手机" title="手机" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">信誉程度:</td>
								<td><input type="number" name="CREDITDEGREE" id="CREDITDEGREE" value="${pd.CREDITDEGREE}" maxlength="11" placeholder="这里输入信誉程度" title="信誉程度" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td><input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" maxlength="1000" placeholder="这里输入地址" title="地址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td><input type="number" name="TELEPHONE" id="TELEPHONE" value="${pd.TELEPHONE}" maxlength="20" placeholder="这里输入电话" title="电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">传真:</td>
								<td><input type="text" name="FAX" id="FAX" value="${pd.FAX}" maxlength="100" placeholder="这里输入传真" title="传真" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">传呼:</td>
								<td><input type="number" name="PAGING" id="PAGING" value="${pd.PAGING}" maxlength="32" placeholder="这里输入传呼" title="传呼" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系人:</td>
								<td><input type="text" name="LINKMAN" id="LINKMAN" value="${pd.LINKMAN}" maxlength="100" placeholder="这里输入联系人" title="联系人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开户银行:</td>
								<td><input type="text" name="OPENBANK" id="OPENBANK" value="${pd.OPENBANK}" maxlength="1000" placeholder="这里输入开户银行" title="开户银行" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">银行账号:</td>
								<td><input type="number" name="BANKACCOUNT" id="BANKACCOUNT" value="${pd.BANKACCOUNT}" maxlength="20" placeholder="这里输入银行账号" title="银行账号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">邮编:</td>
								<td><input type="text" name="MAILCODE" id="MAILCODE" value="${pd.MAILCODE}" maxlength="100" placeholder="这里输入邮编" title="邮编" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电子邮箱:</td>
								<td><input type="number" name="EMAIL" id="EMAIL" value="${pd.EMAIL}" maxlength="32" placeholder="这里输入电子邮箱" title="电子邮箱" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">网址:</td>
								<td><input type="text" name="NETADDR" id="NETADDR" value="${pd.NETADDR}" maxlength="100" placeholder="这里输入网址" title="网址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">经销方式:</td>
								<td>
									<select name="DISTRIBUTIONMODE" id="DISTRIBUTIONMODE" placeholder="请选择" title="经销方式" style="width:98%;background-color:#EBEBEB" >
									<c:forEach items="${varListL}" var="var">
										<option value="${var.LEVEL_ID }" <c:if test="${var.LEVEL_ID == pd.DISTRIBUTIONMODE }">selected</c:if>>${var.TITLE }</option>
									</c:forEach>
								<!-- 	<option value="1" >现金</option>
									<option value="2" >月结</option> -->
									</select>
								</td>
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
			if($("#SUPPLIERNAME").val()==""){
				$("#SUPPLIERNAME").tips({
					side:3,
		            msg:'请输入姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SUPPLIERCODE").tips({
					side:3,
		            msg:'请输入客户编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SUPPLIERNAME").focus();
			return false;
			}
			if($("#CREDITDEGREE").val()==""){
				$("#CREDITDEGREE").val(0);
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		</script>
</body>
</html>