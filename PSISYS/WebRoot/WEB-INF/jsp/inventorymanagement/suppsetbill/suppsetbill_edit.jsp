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
						<form action="suppsetbill/${msg }.do" method="post" name="Form" id="Form">
						<!-- 检索  -->
						<table style="margin-top:5px;">
							<tr>
								<td style="vertical-align:top;padding-left:2px;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="savesuppsetbill();">保存供应商结算单</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if>
								</td>
								
							</tr>
						</table>
						
						<!-- 表头  -->
						<table id="billhead" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">供应商:</td>
								<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="select_suppsetbill" id="select_suppsetbill" data-placeholder="请选择供应商" style="vertical-align:top;width: 120px;"  >
									<option value=""></option>
								  	</select>
								</td>
								<c:if test="${msg == edit }">
								<td style="width:79px;text-align: right;padding-top: 13px;">录单日期:</td>
								<td style="padding-left:2px;">${pd.LDATE}</td>
								</c:if>
								<td style="width:79px;text-align: right;padding-top: 13px;">单据编号:</td>
								<td><input type="text" name="BILLCODE" id="BILLCODE" value="${pd.BILLCODE }" maxlength="32" placeholder="这里输入单据编号" title="单据编号" style="width:98%;"  /></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">经手人:</td>
								<td><input type="text" name="PSI_NAME" id="PSI_NAME" value="${pd.PSI_NAME }" maxlength="32" placeholder="这里输入经手人" title="经手人" style="width:98%;" /></td>
								<input type="hidden" id="FROMUNIT" name="FROMUNIT" value="${pd.FROMUNIT}"   /> 
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">经销方式:</td>
								<td>
									<select name="DISTRIBUTIONMODE" id="DISTRIBUTIONMODE" placeholder="请选择" title="经销方式" style="width:98%;background-color:#EBEBEB"  >
									<c:forEach items="${varListL}" var="var">
										<option value="${var.LEVEL_ID }" <c:if test="${var.LEVEL_ID == pd.DISTRIBUTIONMODE }">selected</c:if>>${var.TITLE }</option>
									</c:forEach>
									</select>
								</td>
								<td style="width:79px;text-align: right;padding-top: 13px;">发票类型:</td>
								<td><input type="text" name="INVOICETYPE" id="INVOICETYPE" value="${pd.INVOICETYPE }" maxlength="32" placeholder="这里输入发票类型" title="发票类型" style="width:98%;" /></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">票号:</td>
								<td><input type="text" name="BILLNO" id="BILLNO" value="${pd.BILLNO }" maxlength="32" placeholder="这里输入票号" title="票号" style="width:98%;"  /></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">付款方式:</td>
								<td><input type="text" name="PAYMETHOD" id="PAYMETHOD" value="${pd.PAYMETHOD }" maxlength="32" placeholder="这里输入付款方式" title="付款方式" style="width:98%;"  /></td>
								
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">可用预收款:</td>
								<td><input type="number" name="APAYAMOUNT" id="APAYAMOUNT" value="${pd.APAYAMOUNT }" maxlength="32" placeholder="这里输入可用预收款" title="可用预收款" style="width:98%;" /></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">本次预付金额:</td>
								<td><input type="number" name="THISPAYAMOUNT" id="THISPAYAMOUNT" value="${pd.THISPAYAMOUNT }" maxlength="32" placeholder="这里输入本次预付金额" title="本次预付金额" style="width:98%;" /></td>
								
								<td style="width:79px;text-align: right;padding-top: 13px;">免付金额:</td>
								<td><input type="number" name="EXEMPAMOUNT" id="EXEMPAMOUNT" value="${pd.EXEMPAMOUNT }" maxlength="32" placeholder="这里输入免付金额" title="免付金额" style="width:98%;" /></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">收供应商费用1:</td>
								<td><input type="number" name="SUPPCOST1" id="SUPPCOST1" value="${pd.SUPPCOST1 }" maxlength="32" placeholder="这里输入收供应商费用1" title="收供应商费用1" style="width:98%;" /></td>
								
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">收供应商费用2:</td>
								<td><input type="number" name="SUPPCOST2" id="SUPPCOST2" value="${pd.SUPPCOST2 }" maxlength="32" placeholder="这里输入收供应商费用2" title="收供应商费用2" style="width:98%;" /></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">应付金额:</td>
								<td><input type="number" name="THISPAYAMOUNT" id="THISPAYAMOUNT" value="${pd.THISPAYAMOUNT }" maxlength="32" placeholder="这里输入应付金额" title="应付金额" style="width:98%;" /></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">实付金额:</td>
								<td><input type="number" name="PAYMENTAMOUNT" id="PAYMENTAMOUNT" value="${pd.PAYMENTAMOUNT }" maxlength="32" placeholder="这里输入实付金额" title="实付金额" style="width:98%;" /></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">应付总金额:</td>
								<td><input type="number" name="PAYABLEALLAM" id="PAYABLEALLAM" value="${pd.PAYABLEALLAM }" maxlength="32" placeholder="这里输入应付总金额" title="应付总金额" style="width:98%;" /></td>
								
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="NOTE" id="NOTE" value="${pd.NOTE }" maxlength="32" placeholder="这里输入备注" title="备注" style="width:98%;" /></td>
							</tr>
						</table>
						</form>
						<form action="suppsetbill/list.do" method="post" name="ListForm" id="ListForm">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">单据编号</th>
									<th class="center">供应商</th>
									<th class="center">总金额</th>
									<th class="center">未付金额</th>
									<th class="center">已付金额</th>
									<th class="center">本次付款</th>
									<th class="center">是否结算</th>
									<th class="center">经手人</th>
									<th class="center">备注</th>
									<th class="center" style="width:110px;">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.INORDER_ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.BILLCODE}</td>
											<td class='center'>${var.SUPPLIERNAME}</td>
											<td class='center'>${var.ALLAMOUNT}</td>
											<td class='center'>${var.UNPAIDAMOUNT}</td>
											<td class='center'>${var.PAIDAMOUNT}</td>
											<td class='center'>${var.THISPAY}</td>
											<td class='center'>
												<c:if test="${var.ISSETTLEMENTED == 1}">
													已结算
												</c:if>
												<c:if test="${var.ISSETTLEMENTED == 0}">
													未结算
												</c:if>
											</td>
											<td class='center'>${var.PSI_NAME}</td>
											<td class='center'>${var.NOTE}</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<a class="btn btn-xs btn-success" title="查看" onclick="view('${var.INORDER_ID}');">
														<i class="ace-icon fa fa-eye bigger-120" title="查看"></i>
													</a>
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.INORDER_ID}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" onclick="del('${var.INORDER_ID}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>
													</c:if>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<li>
																<a style="cursor:pointer;" onclick="view('${var.INORDER_ID}');" class="tooltip-success" data-rel="tooltip" title="查看">
																	<span class="green">
																		<i class="ace-icon fa fa-eye bigger-120"></i>
																	</span>
																</a>
															</li>
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.INORDER_ID}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.INORDER_ID}');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
														</ul>
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
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(window).on('load', function () {
			var sid = $("#FROMUNIT").val();
		    $.ajax({
		        method:'POST',
		        url:'supplier/listNameAndID',
		        dataType:'json',
		        success: function (res) {
		            var html="<option value='0'>请选择供应商</option>";
		            console.log(res);
		            for (var i = 0; i < res.varList.length; i++) {
		                if (sid == res.varList[i].SUPPLIER_ID) {
		                    html += "<option  value='" + res.varList[i].SUPPLIER_ID + "' selected='selected' data-name='"+res.varList[i].SUPPLIERNAME+"'>" + res.varList[i].SUPPLIERNAME + "</option>";
		                } else {
		                    html += "<option  value='" + res.varList[i].SUPPLIER_ID + "' data-name='"+res.varList[i].SUPPLIERNAME+"'>"+ res.varList[i].SUPPLIERNAME + "</option>";
		                }
		            }
		            $("#select_suppsetbill").html(html);
		            $('#select_suppsetbill').trigger("chosen:updated");//重置下拉框  
		            $('#select_suppsetbill').chosen();//下拉框检索框架
		        }
		    }); 
		});
		$("#select_suppsetbill").on('change',function(e,params){
			var sid = $("#select_suppsetbill").val();
			if(sid == 0){
				var list = <%=session.getAttribute("varList")%>
				alert(list);
			}else{
				$.ajax({
			        method:'POST',
			        url:'supplier/listNameAndID',
			        dataType:'json',
			        success: function (res) {
			            var html="<option value='0'>请选择供应商</option>";
			            console.log(res);
			            for (var i = 0; i < res.varList.length; i++) {
			                if (sid == res.varList[i].SUPPLIER_ID) {
			                    html += "<option  value='" + res.varList[i].SUPPLIER_ID + "' selected='selected' data-name='"+res.varList[i].SUPPLIERNAME+"'>" + res.varList[i].SUPPLIERNAME + "</option>";
			                } else {
			                    html += "<option  value='" + res.varList[i].SUPPLIER_ID + "' data-name='"+res.varList[i].SUPPLIERNAME+"'>"+ res.varList[i].SUPPLIERNAME + "</option>";
			                }
			            }
			            $("#select_suppsetbill").html(html);
			            $('#select_suppsetbill').trigger("chosen:updated");//重置下拉框  
			            $('#select_suppsetbill').chosen();//下拉框检索框架
			        }
			    }); 
			}
		    alert(sid);
		    /* $.ajax({
		    	 	method:'POST',
			        url:'inorder/listNameAndID',
			        dataType:'json',
			        success: function (res) {
			        	
			        }
		    }); */
		});
		function changeSupplier(){
			var sid = $("#select_suppsetbill").val();
		    alert(sid);
		} 
		
		$(function() {
			$("#LDATE").attr("disabled","disabled");
		
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
			 diag.URL = '<%=basePath%>suppsetbill/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 555;
			 diag.Modal = false;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 tosearch();
					 }else{
						 tosearch();
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		//新增供应商
		function addsuppsetbill(){
			var url = "suppsetbill/goAdd.do";
			siMenu('z211','lm211','添加供应商',url);
		}
				
		//跟踪记录
		function chaImg(SUPPLIER_ID){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="跟踪记录";
			 diag.URL = '<%=basePath%>supplierimg/list.do?SUPPLIER_ID='+SUPPLIER_ID;
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.Modal = false;			//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//消费记录
		function consume(SUPPLIER_ID){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="消费记录";
			 diag.URL = '<%=basePath%>outku/list.do?SUPPLIER_ID='+SUPPLIER_ID;
			 diag.Width = 800;
			 diag.Height = 600;
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
					var url = "<%=basePath%>suppsetbill/delete.do?SUPPLIER_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
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
			 diag.URL = '<%=basePath%>suppsetbill/goEdit.do?SUPPLIER_ID='+Id;
			 diag.Width = 450;
			 diag.Height = 555;
			 diag.Modal = false;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//查看
		function view(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="查看";
			 diag.URL = '<%=basePath%>suppsetbill/goView.do?SUPPLIER_ID='+Id;
			 diag.Width = 450;
			 diag.Height = 650;
			 diag.Modal = false;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
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
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>suppsetbill/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											tosearch();
									 });
								}
							});
						}
					}
				}
			});
		};
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>suppsetbill/excel.do';
		}
	</script>


</body>
</html>