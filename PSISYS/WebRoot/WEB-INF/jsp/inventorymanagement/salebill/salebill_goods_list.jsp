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
						<form action="salebill/goodslist.do?" method="post" name="Form" id="Form">
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
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
								<td>&nbsp;&nbsp;&nbsp;
									<a class="btn btn-mini btn-success" onclick="selectSome('确定要添加选中的商品?');" title="批量添加" >批量添加</a>
								</td>
							</tr>
						</table>
						<!-- 检索  -->
						<input type="hidden" name="WAREHOUSE_ID" id="WAREHOUSE_ID" value="${pd.WAREHOUSE_ID}"/>
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">商品名称</th>
									<th class="center">商品条码</th>
									<th class="center">商品规格</th>
									<th class="center">商品库存</th>
									<th class="center">计量单位</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' id="checkbox">
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.GOOD_ID},${var.GOODNAME},${var.BARCODE},${var.UNITNAME},${var.GOODCODE},${var.CPRICE}:${var.WAREHOUSE_ID_NAME_STOCK}"  class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>
												${var.GOODNAME}
												<%-- <a onclick="erweima('${var.GOOD_ID}');"><img style="cursor:pointer;" width="15" src="static/images/erwei.png"  title="商品二维码"/></a> --%>
												<a onclick="barcode('${var.GOOD_ID}','${var.BARCODE}');"><img style="cursor:pointer;" width="15" src="static/images/barcode.png"  title="商品条形码"/></a>
											</td>
											<td class='center'>${var.BARCODE}</td>
											<td class='center'>${var.GOODSPECIF}</td>
											<td class='center'>${var.STOCKNUM}</td>
											<td class='center'>${var.UNITNAME}</td>
											<td class="center">
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" onclick="view('${var.GOOD_ID}','${var.GOODNAME}','${var.BARCODE}','${var.UNITNAME}','${var.GOODCODE}','${var.RPRICE}','${var.GOODTYPECODE}','${var.GOODSPECIF}','${var.WAREHOUSE_ID_NAME_STOCK}');" data-toggle="dropdown" data-position="auto">
															选择
														</button>
													</div>
												</div>
											</td>
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
	

	<script src="static/js/jquery.cookie.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		
		//选择商品
		function view(GOOD_ID,GOODNAME,BARCODE,UNITNAME,GOODCODE,RPRICE,GOODTYPECODE,GOODSPECIF,WAREHOUSE_ID_NAME_STOCK){
// 			var WAREHOUSE_ID = $("#WAREHOUSE_ID").val();
			//发送ajax请求到后台核对仓库是否有此商品
// 			$.ajax({
// 				type: "POST",
<%-- 				url: '<%=basePath%>salebill/checkstock.do?tm='+new Date().getTime(), --%>
// 		    	data: {"GOOD_ID":GOODCODE, "WAREHOUSE_ID":WAREHOUSE_ID},
// 				dataType:'json',
// 				cache: false,
// 				success: function(data){
// 					if(data.msg == "success"){//存在商品
// 					}else {
// 						alert("此仓库中没有此商品");
// 						return;}
// 				}
// 			});
			if(!window.localStorage){
		    	 alert("浏览器不支持localstorage");
		    }else{
	            var storage=window.localStorage;
	            localStorage.setItem("GOOD_ID",GOOD_ID);
	            localStorage.setItem("GOODNAME",GOODNAME);
	            localStorage.setItem("BARCODE",BARCODE);
	            localStorage.setItem("UNITNAME",UNITNAME);
	            localStorage.setItem("GOODCODE",GOODCODE);
	            localStorage.setItem("RPRICE",RPRICE);
	            localStorage.setItem("GOODTYPECODE",GOODTYPECODE);
	            localStorage.setItem("GOODSPECIF",GOODSPECIF);
				localStorage.setItem("WAREHOUSE_ID_NAME_STOCK",WAREHOUSE_ID_NAME_STOCK);
			}
			top.Dialog.close();//关闭窗口
		}
		
		//批量选择
		function selectSome(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += '?' + document.getElementsByName('ids')[i].value;
					  }
					  
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						 if(!window.localStorage){
						     alert("浏览器不支持localstorage");
					      }else{
					             var storage=window.localStorage;
					             localStorage.setItem("str",str);
						}
						top.Dialog.close();//关闭窗口
					}
					
				}
			});
		};
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
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>goods/excel.do';
		}
	</script>


</body>
</html>