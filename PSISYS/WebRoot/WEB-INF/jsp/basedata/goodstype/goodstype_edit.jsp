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
					
					<form action="goodstype/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="GOODTYPE_ID" id="GOODTYPE_ID" value="${pd.GOODTYPE_ID}"/>
						<input type="hidden" name="PARENTS" id="PARENTS" value="${null == pd.PARENTS ? GOODTYPE_ID:pd.PARENTS}"/>
						<div id="zhongxin">
						<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top:15px;">
							<tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">上级:</td>
								<td>
									<div class="col-xs-4 label label-lg label-light arrowed-in arrowed-right">
										<b>${null == pds.TYPENAME ?'(无) 此项为顶级':pds.TYPENAME}</b>
									</div>
								</td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">商品分类名称:</td>
								<td><input type="text" name="TYPENAME" id="TYPENAME" value="${pd.TYPENAME}" maxlength="50" placeholder="这里输入商品分类名称" title="商品分类名称" style="width:98%;"/></td>
							</tr>
							<%-- <tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">英文:</td>
								<td><input type="text" name="TYPENAME_EN" id="TYPENAME_EN" value="${pd.TYPENAME_EN}" maxlength="50" placeholder="这里输入英文" title="英文" style="width:98%;"/></td>
							</tr> --%>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">商品分类编号:</td>
								<td><input type="text" name="TYPECODE" id="TYPECODE" value="${pd.TYPECODE}" maxlength="32" placeholder="这里输入商品分类编号 (不重复, 禁止修改)" title="商品分类编号" style="width:76%;" <c:if test="${null != pd.TYPECODE}">readonly="readonly"</c:if> <c:if test="${null == pd.TYPECODE}">onblur="hasBianma();"</c:if> /></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">经手人:</td>
								<td><input type="text" name="TYPECODE" id="TYPECODE" value="${pd.PSI_NAME}" maxlength="32" placeholder="这里输入经手人(禁止修改)" title="经手人" style="width:76%;" readonly="readonly" /></td>
							</tr>
							<%-- <tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">排序:</td>
								<td><input type="number" name="ORDER_BY" id="ORDER_BY" value="${pd.ORDER_BY}" maxlength="32" placeholder="这里输入排序" title="排序"/></td>
							</tr>
							<tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">备注:</td>
								<td>
									<textarea rows="3" cols="46" name="BZ" id="BZ" placeholder="这里输入备注" title="备注"  style="width:98%;">${pd.BZ}</textarea>
								</td>
							</tr> --%>
							<%-- <tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">排查表:</td>
								<td><input type="text" name="TBSTYPENAME" id="TBSTYPENAME" value="${pd.TBSTYPENAME}" maxlength="100" placeholder="这里输入表名, 多个用逗号分隔(非必录)" title="排查表" style="width:98%;"/></td>
							</tr>
							<tr>
								<td colspan="10" class="center"><p class="text-warning bigger-110 orange">排查表：删除此条数据时会去此表查询是否被占用(是:禁止删除)</p></td>
							</tr> --%>
							<tr>
								<td class="center" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						
					</form>
	
					<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
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
			if($("#TYPENAME").val()==""){
				$("#TYPENAME").tips({
					side:3,
		            msg:'请输入名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TYPENAME").focus();
			return false;
		}
			if($("#TYPENAME_EN").val()==""){
				$("#TYPENAME_EN").tips({
					side:3,
		            msg:'请输入英文',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TYPENAME_EN").focus();
			return false;
		}
			if($("#TYPECODE").val()==""){
				$("#TYPECODE").tips({
					side:3,
		            msg:'请输入编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TYPECODE").focus();
			return false;
		}
			if($("#ORDER_BY").val()==""){
				$("#ORDER_BY").tips({
					side:3,
		            msg:'请输入数字',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ORDER_BY").focus();
			return false;
		}
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
		}
		
		//判断编码是否存在
		function hasBianma(){
			var TYPECODE = $.trim($("#TYPECODE").val());
			if("" == TYPECODE)return;
			$.ajax({
				type: "POST",
				url: '<%=basePath%>goodstype/hasBianma.do',
		    	data: {TYPECODE:TYPECODE,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" == data.result){
					 }else{
						$("#TYPECODE").tips({
							side:1,
				            msg:'编码'+TYPECODE+'已存在,重新输入',
				            bg:'#AE81FF',
				            time:5
				        });
						$('#TYPECODE').val('');
					 }
				}
			});
		}
		</script>
</body>
</html>