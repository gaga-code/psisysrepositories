<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<script type="text/javascript" src="static/js/myjs/head.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />


 
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
							
						<!-- 检索  -->
						<form action="customersetbill/listdetail.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="${pd.lastEnd }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								
								
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
								<c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;">	&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-success" onclick="toExcel();" title="导出到EXCEL">导出EXCEL</a></td></c:if>
					
							</tr>
						</table>
						<!-- 检索  -->
						<!-- 表头  -->
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									
									<th class="center" style="width:50px;">序号</th>
									<th class="center">单据编号</th>
									<th class="center">单据类型</th>
									<th class="center">录单日期</th>
									<th class="center">客户名称</th>
									<th class="center">经手人</th>
									<th class="center">金额</th>
									<th class="center">支付方式</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty customerSetList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${customerSetList}" var="var" varStatus="vs">
										<tr id="${var.CUSTOMERSETBILL_ID}" onclick=clickaction('${var.CUSTOMERSETBILL_ID}') >
											
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.BILLCODE}</td>
											<td class='center'>客户结算单</td>
											<td class='center'>${var.LDATE}</td>
											<td class='center'>${var.CUATOMERNAME}</td>
											<td class='center'>${var.NAME}</td>
											<td class='center'>${var.PAYMENTAMOUNT}</td>
											<td class='center'>${var.PAYMETHODNAME}</td>					
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
						</div>
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
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
		function addcustomersetbill(){
			var url = 'customersetbill/goAdd.do';
			siMenu('z290','lm280','添加客户结算单',url);
		}
		
		function tosearch(){
			top.jzts();
			$("#Form").submit();
			
		}

		$(function() {
			console.log("1");
			$("#billstatus option[text='未审核']").attr("selected", true); 
			
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
			
			
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		
		
		
		//导出excel
		function toExcel(){
			var lastStart =  $("#lastStart").val();
			var lastEnd  = $("#lastEnd").val();
			window.location.href='<%=basePath%>customersetbill/excelDetail.do?lastStart='+lastStart+'&lastEnd='+lastEnd;
		}
		
		
	</script>


</body>
</html>