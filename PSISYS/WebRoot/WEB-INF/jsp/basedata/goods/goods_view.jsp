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
					
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品名称:</td>
								<td colspan="10" style="padding-top: 13px;">${pd.GOODNAME}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">经手人:</td>
								<td colspan="10" style="padding-top: 13px;">${pd.PSI_NAME}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品编号:</td>
								<td style="padding-top: 13px;">${pd.GOODCODE}</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">简称:</td>
								<td style="padding-top: 13px;">${pd.SIMPLENAME}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品规格:</td>
								<td style="padding-top: 13px;">${pd.GOODSPECIF}</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属柜组:</td>
								<td style="padding-top: 13px;">${pd.SUBGZ_ID}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品型号:</td>
								<td style="padding-top: 13px;">${pd.GOODTYPECODE}</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">拼音编码:</td>
								<td style="padding-top: 13px;">${pd.YICODE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">计量单位:</td>
								<td style="padding-top: 13px;">${pd.UNITNAME}</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">辅助单位:</td>
								<td style="padding-top: 13px;">${pd.FZUNITNAME}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单位比例:</td>
								<td style="padding-top: 13px;">${pd.UNITPROP}</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品分类编号:</td>
								<td style="padding-top: 13px;">${pd.GOODTYPE_CODE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">供应商:</td>
								<td colspan="10" style="padding-top: 13px;"><div>${pd.SUPPLIER_NAME}</div></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td colspan="10" style="padding-top: 13px;"><div>${pd.NOTE}</div></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">最后进价:</td>
								<td style="padding-top: 13px;">${pd.LASTPPRICE}</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">成本价:</td>
								<td style="padding-top: 13px;">${pd.CPRICE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">零售价:</td>
								<td style="padding-top: 13px;">${pd.RPRICE}</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">最后辅助单位进价:</td>
								<td style="padding-top: 13px;">${pd.LFZUPPRICE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">辅助单位零售价:</td>
								<td style="padding-top: 13px;">${pd.FZUCPRICE}</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">会员价:</td>
								<td style="padding-top: 13px;">${pd.MPRICE}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">库存数量:</td>
								<td colspan="10" style="padding-top: 13px;">${pd.STOCKNUM}</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">库存上限:</td>
								<td style="padding-top: 13px;">${pd.STOCKUPNUM}</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">库存下限:</td>
								<td style="padding-top: 13px;">${pd.STOCKDOWNNUM}</td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 1px;" id="select_stock_name">仓库:</td>
								<input type="hidden" id="WAREHOUSE_IDs" name="WAREHOUSE_IDs" value="${pd.WAREHOUSE_IDs}"   />
								<td style="vertical-align:top;" disabled="disabled">
								 	<select class="chosen-select form-control" disabled="disabled" name="Select_WAREHOUSE_IDs" id="Select_WAREHOUSE_IDs" data-placeholder="请选择仓库" multiple="multiple"  style="vertical-align:top;width: 98%;"  >
									<option value=""></option>
								  	</select>
								</td>
							</tr>
						</table>
						</div>
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

<c:if test="${'edit' == msg }">
	<div>
		<iframe name="treeFrame" id="treeFrame" frameborder="0" src="<%=basePath%>/goodsmx/viewList.do?MASTER_ID=${pd.GOOD_ID}" style="margin:0 auto;width:805px;height:368px;;"></iframe>
	</div>
</c:if>

	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		$(window).on('load', function () {
			//获取仓库列表
			var sid = $("#WAREHOUSE_IDs").val();
		    $.ajax({
		        method:'POST',
		        url:'goods/listNameAndID',
		        dataType:'json',
		        success: function (res) {
		            var html="<option value='0'>请选择仓库</option>";
		            console.log(res);
		            for (var i = 0; i < res.varList.length; i++) {
		                if (sid.match(res.varList[i].WAREHOUSE_ID)) {
		                    html += "<option  value='" + res.varList[i].WAREHOUSE_ID + "' selected='selected' data-name='"+res.varList[i].WHNAME+"'>" + res.varList[i].WHNAME + "</option>";
		                } else {
		                    html += "<option  value='" + res.varList[i].WAREHOUSE_ID + "' data-name='"+res.varList[i].WHNAME+"'>"+ res.varList[i].WHNAME + "</option>";
		                }
		            }
		            $("#Select_WAREHOUSE_IDs").html(html);
		            $('#Select_WAREHOUSE_IDs').trigger("chosen:updated");//重置下拉框  
		            $('#Select_WAREHOUSE_IDs').chosen();//下拉框检索框架
		        }
		    }); 
		});
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
		</script>
</body>
</html>