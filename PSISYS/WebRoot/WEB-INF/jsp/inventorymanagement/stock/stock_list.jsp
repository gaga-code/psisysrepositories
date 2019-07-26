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
						<form action="stock/list.do" method="post" name="Form" id="Form">
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
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">商品编号</th>
									<th class="center">商品名称</th>
									<th class="center">条码</th>
									<th class="center">型号</th>
									<th class="center">规格</th>
									<th class="center">商品类别</th>
									<th class="center">单位</th>
									<th class="center">辅助数量</th>
									<th class="center">成本价</th>
									<th class="center">总金额</th>
									<th class="center">所属柜组</th>
									<th class="center">库存数量</th>
									<th class="center">库存上限</th>
									<th class="center">库存下限</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.GOODCODE}</td>
											<td class='center'>${var.GOODNAME}</td>
											<td class='center'>
												${var.BARCODE}
												<a onclick="barcode('${var.GOOD_ID}','${var.BARCODE}');"><img style="cursor:pointer;" width="15" src="static/images/barcode.png"  title="商品条形码"/></a>
											</td>
											<td class='center'>${var.GOODTYPECODE}</td>
											<td class='center'>${var.GOODSPECIF}</td>
											<td class='center'>${var.TYPENAME}</td>
											<td class='center'>${var.CUNITNAME}</td>
											<td class='center'>${var.FZ}</td>
											<td class='center'>${var.CPRICE}</td>
											<td class='center'>${var.ZJE}</td>
											<td class='center'>${var.SUBGZ_ID}</td>
											<td class='center'>${var.STOCK}</td>
											<td class='center'>${var.STOCKUPNUM}</td>
											<td class='center'>${var.STOCKDOWNNUM}</td>
										</tr>
<!-- 											<td class="center"> -->
<%-- 												<c:if test="${QX.edit != 1 && QX.del != 1 }"> --%>
<!-- 												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span> -->
<%-- 												</c:if> --%>
<!-- 												<div class="hidden-sm hidden-xs btn-group"> -->
<%-- 													<a class="btn btn-xs btn-info" title="查看商品信息" onclick="view('${var.GOOD_ID}');"> --%>
<!-- 														<i class="ace-icon fa fa-eye bigger-120" title="查看商品信息"></i> -->
<!-- 													</a> -->
<%-- 													<c:if test="${QX.edit == 1 }"> --%>
<%-- 													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.GOOD_ID}');"> --%>
<!-- 														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i> -->
<!-- 													</a> -->
<%-- 													</c:if> --%>
<%-- 													<c:if test="${QX.del == 1 }"> --%>
<%-- 													<a class="btn btn-xs btn-danger" onclick="del('${var.GOOD_ID}');"> --%>
<!-- 														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i> -->
<!-- 													</a> -->
<%-- 													</c:if> --%>
<!-- 												</div> -->
<!-- 												<div class="hidden-md hidden-lg"> -->
<!-- 													<div class="inline pos-rel"> -->
<!-- 														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto"> -->
<!-- 															<i class="ace-icon fa fa-cog icon-only bigger-110"></i> -->
<!-- 														</button> -->
			
<!-- 														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close"> -->
<!-- 															<li> -->
<%-- 																<a style="cursor:pointer;" onclick="view('${var.GOOD_ID}');" class="tooltip-info" data-rel="tooltip" title="查看商品信息"> --%>
<!-- 																	<span class="blue"> -->
<!-- 																		<i class="ace-icon fa fa-eye bigger-120"></i> -->
<!-- 																	</span> -->
<!-- 																</a> -->
<!-- 															</li> -->
<%-- 															<c:if test="${QX.edit == 1 }"> --%>
<!-- 															<li> -->
<%-- 																<a style="cursor:pointer;" onclick="edit('${var.GOOD_ID}');" class="tooltip-success" data-rel="tooltip" title="修改"> --%>
<!-- 																	<span class="green"> -->
<!-- 																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i> -->
<!-- 																	</span> -->
<!-- 																</a> -->
<!-- 															</li> -->
<%-- 															</c:if> --%>
<%-- 															<c:if test="${QX.del == 1 }"> --%>
<!-- 															<li> -->
<%-- 																<a style="cursor:pointer;" onclick="del('${var.GOOD_ID}');" class="tooltip-error" data-rel="tooltip" title="删除"> --%>
<!-- 																	<span class="red"> -->
<!-- 																		<i class="ace-icon fa fa-trash-o bigger-120"></i> -->
<!-- 																	</span> -->
<!-- 																</a> -->
<!-- 															</li> -->
<%-- 															</c:if> --%>
<!-- 														</ul> -->
<!-- 													</div> -->
<!-- 												</div> -->
<!-- 											</td> -->
										
									
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
<!-- 						<div class="page-header position-relative"> -->
<!-- 						<table style="width:100%;"> -->
<!-- 							<tr> -->
<!-- 								<td style="vertical-align:top;"> -->
<%-- 									<c:if test="${QX.add == 1 }"> --%>
<!-- 									<a class="btn btn-mini btn-success" onclick="add();">新增</a> -->
<%-- 									</c:if> --%>
<!-- 								</td> -->
<%-- 								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td> --%>
<!-- 							</tr> -->
<!-- 						</table> -->
<!-- 						</div> -->
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
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>goods/goAdd.do';
			 diag.Width = 800;
			 diag.Height = 596;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//商品二维码
		function erweima(GOOD_ID){
			 top.jzts();
			 var url = '<%=basePath%>appGoods/goods.do?GOOD_ID='+GOOD_ID;
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="商品二维码";
			 diag.URL = '<%=basePath%>goods/erweima.do?GOOD_ID='+GOOD_ID+'&url='+url;
			 diag.Width = 283;
			 diag.Height = 276;
			 diag.Modal = false;			//有无遮罩窗口
			 diag. ShowMaxButton = false;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//商品条形码
		function barcode(GOOD_ID,BIANMA){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="商品条形码";
			 diag.URL = '<%=basePath%>goods/barcode.do?GOOD_ID='+GOOD_ID+'&BIANMA='+BIANMA;
			 diag.Width = 283;
			 diag.Height = 186;
			 diag.Modal = false;			//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>goods/delete.do?GOOD_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						if("success" == data.result){
							nextPage(${page.currentPage});
						}else if("false" == data.result){
							top.hangge();
							bootbox.dialog({
								message: "<span class='bigger-110'>删除失败,请先删除商品图片或者清空本商品库存!</span>",
								buttons: 			
								{
									"button" :
									{
										"label" : "确定",
										"className" : "btn-sm btn-success"
									}
								}
							});
						}
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>goods/goEdit.do?GOOD_ID='+Id;
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//查看商品信息
		function view(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="查看商品信息";
			 diag.URL = '<%=basePath%>goods/goView.do?GOOD_ID='+Id;
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>goods/excel.do';
		}
	</script>


</body>
</html>