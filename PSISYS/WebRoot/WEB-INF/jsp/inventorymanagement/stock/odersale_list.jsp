﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- 自定義CSS文件 -->
<link rel="stylesheet" href="static/myCSS/style.css"/>
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- <script type="text/javascript" src="static/js/jquery/jquery-1.7.2.js"></script> -->
<script type="text/javascript" src="static/js/myjs/head.js"></script>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<input type="hidden" name="goodslist" id="goodslist" value="${sessionScope.goodslist}"/>
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							
						<!-- 检索  -->
						<form action="goods/goInorderSale.do" method="post" name="Form" id="Form">
							<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
								<thead>
									<tr>
										<th class="center" style="width:50px;">序号</th>
										<th class="center">单据编号</th>
										<th class="center">单据类型</th>
										<th class="center">商品</th>
										<th class="center">商品编号</th>
										<th class="center">单价</th>
										<th class="center">数量</th>
										<th class="center">时间</th>
										<th class="center">经手人</th>
										
									</tr>
								</thead>
														
								<tbody>
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty varlist}">
										<c:if test="${QX.cha == 1 }">
										<c:forEach items="${varlist}" var="var" varStatus="vs">
											<tr >
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class='center'>${var.BILLCODE}</td>
												<c:if test="${var.BILLTYPE=='1'}">
													<td class='center'>进货单</td>
												</c:if>
												<c:if test="${var.BILLTYPE=='2'}">
													<td class='center'>销售单</td>
												</c:if>
												<c:if test="${var.BILLTYPE=='8'}">
													<c:if test="${var.bill=='4'}">
														<td class='center'>供应商退货单</td>
													</c:if>
													<c:if test="${var.bill=='1'}">
														<td class='center'>客户退货单</td>
													</c:if>
												</c:if>
												
												<td class='center'>${var.GOODNAME}</td>
												
												<td class='center'>${var.GOODCODE}</td>
												<td class='center'>${var.UNITPRICE_ID}</td>
												<td class='center'>${var.PNUMBER}</td>
												<td class='center'>${var.LASTTIME}</td>
												<td class='center'>${var.USERNAME}</td>
											
											</tr>
										
										</c:forEach>
										</c:if>
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
							<div class="page-header position-relative">
								<table style="width:100%;">
									<tr>
	
										<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
									</tr>
								</table>
							</div>
						
						</form>
						<iframe id="rfFrame" name="rfFrame" src="about:blank" style="display:none;"></iframe>
						<form action="#" method="post" name="actionForm" id="actionForm"></form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态

		
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
			
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
		
		});
	</script>


</body>
</html>