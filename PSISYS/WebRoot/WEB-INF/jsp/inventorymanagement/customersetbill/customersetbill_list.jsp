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
						<form action="customersetbill/list.do" method="post" name="Form" id="Form">
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
								<td style="vertical-align:top;padding-left:2px;">
								 	<select  name="billstatus" id="billstatus" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value="">全部</option>								 	
									<option value="1" >未审核</option>
									<option value="2">已审核</option>
									<option value="3">作废</option>
								  	</select>
								</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="${pd.lastEnd }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								
								
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
								<c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;">	&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-success" onclick="toExcel();" title="导出到EXCEL">导出EXCEL</a></td></c:if>
							
								
								<td style="vertical-align:top;padding-left:2px;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add();">新增客户结算单</a>
									</c:if>
									<%-- <c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if> --%>
									<c:if test="${QX.CUSTOMERSETBILLAPPROVAL  == 1 }">
									<a class="btn btn-mini btn-success" onclick="approvalAll('确定要审批选中的数据吗?');" title="批量审批" >批量审批</a>
									</c:if>
								</td>
								
							</tr>
						</table>
						<!-- 检索  -->
						<!-- 表头  -->
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">录单日期</th>
									<th class="center">单据编号</th>
									<th class="center">单据状态</th>
									<th class="center">往来单位</th>
									<th class="center">收款方式</th>
									<th class="center">总金额</th>
									<th class="center">应收金额</th>
									<th class="center">实收金额</th>
									<th class="center">发票类型</th>
									<th class="center">票号</th>
									<th class="center">备注</th>
									<th class="center">经手人</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty customerSetList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${customerSetList}" var="var" varStatus="vs">
										<tr id="${var.CUSTOMERSETBILL_ID}" onclick=clickaction('${var.CUSTOMERSETBILL_ID}') >
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' id="customercheckbox" value="${var.CUSTOMERSETBILL_ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.LDATE}</td>
											<td class='center'>${var.BILLCODE}</td>
											<c:if test="${var.BILLSTATUS == 1 }">
											<td class='center' id='BILLSTATUS'><font color="blue">未审核</font></td>
											</c:if>
											<c:if test="${var.BILLSTATUS == 2 }">
											<td class='center' id='BILLSTATUS'><font color="green">已审核</font></td>
											</c:if>
											<c:if test="${var.BILLSTATUS == 3 }">
											<td class='center' id='BILLSTATUS'><font color="red">作废</font></td>
											</c:if>
											<td class='center'>${var.FROMUNITNAME}</td>
											<td class='center'>${var.PAYMETHOD}</td>
											<td class='center'>${var.PAYABLEALLAM}</td>
											<td class='center'>${var.PAYABLEAMOUNT}</td>
											<td class='center'>${var.PAYMENTAMOUNT}</td>
											<td class='center'>${var.INVOICETYPE}</td>
											<td class='center'>${var.BILLNO}</td>
											
											<td class='center'>${var.NOTE}</td>
											<td class='center'>${var.PSI_NAME}</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="审批" id="approvalone"  onclick="approvalone('${var.CUSTOMERSETBILL_ID}','${var.PAYMENTAMOUNT}','${var.BILLSTATUS}');">
														<i  title="审批">审批</i>
													</a>
													<a class="btn btn-xs btn-danger" onclick="unapprovalone('${var.CUSTOMERSETBILL_ID}','${var.BILLSTATUS}');">
														<i  title="反审">反审</i>
													</a>
													
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.CUSTOMERSETBILL_ID}','${var.BILLSTATUS}');">
														<i title="编辑">编辑</i>
													</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" id="delone" onclick="del('${var.CUSTOMERSETBILL_ID}','${var.BILLSTATUS}');">
														<i title="删除">删除</i>
													</a>
													</c:if>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="approvalone('${var.CUSTOMERSETBILL_ID}','${var.PAYMENTAMOUNT}','${var.BILLSTATUS}');" class="tooltip-success" data-rel="tooltip" title="审批" id="approvalone">
																	<span class="green">
																		<i class="ace-icon fa fa-eye bigger-120"></i>
																	</span>
																</a>
															</li>
															<li>
																<a style="cursor:pointer;" onclick="unapprovalone('${var.CUSTOMERSETBILL_ID}','${var.BILLSTATUS}');" class="tooltip-success" data-rel="tooltip" title="反审" id="approvalone">
																	<span class="green">
																		<i class="ace-icon fa fa-eye bigger-120"></i>
																	</span>
																</a>
															</li>
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.CUSTOMERSETBILL_ID}','${var.BILLSTATUS}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.CUSTOMERSETBILL_ID}','${var.BILLSTATUS}');" class="tooltip-error" data-rel="tooltip" title="删除">
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
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
						</form>
						</div>
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<!-- <th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th> -->
									<th class="center" style="width:50px;">序号</th>
									<th class="center">单据编号</th>
									<th class="center">单据类型</th>
									<th class="center">客户</th>
									<th class="center">总金额</th>
									<th class="center">未收金额</th>
									<th class="center">已收金额</th>
									<th class="center">本次收款</th>
									<th class="center">结算状态</th>
									<th class="center">经手人</th>
									<th class="center">备注</th>
									<!-- <th class="center" style="width:110px;">操作</th> -->
								</tr>
							</thead>
							<tbody id="realtbody">
							
							</tbody>
						</table>
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
			window.location.href='<%=basePath%>customersetbill/excel.do?lastStart='+lastStart+'&lastEnd='+lastEnd;
		}
		
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>customersetbill/goAdd.do';
			 diag.Width = 1200;
			 diag.Height = 1000;
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
		
				
		//删除
		function del(Id,BILLSTATUS){
			if(BILLSTATUS == 3){
				$("#"+Id+" #customercheckbox").tips({
					side:1,
		            msg:'作废单不可以删除',
		            bg:'#AE81FF',
		            time:5
		        });
				return;
			}
			if(BILLSTATUS == 2){
				$("#"+Id+" #customercheckbox").tips({
					side:1,
		            msg:'已审核的客户结算单不可以删除',
		            bg:'#AE81FF',
		            time:5
		        });
				return;
			}
			
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>customersetbill/delete.do?CUSTOMERSETBILL_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		//修改
		function edit(Id,status){
			if(status == 1){
				top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="编辑";
				 diag.URL = '<%=basePath%>customersetbill/goEdit.do?CUSTOMERSETBILL_ID='+Id;
				 diag.Width = 1200;
				 diag.Height = 1000;
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
			}else{//customercheckbox
				$("#"+Id+" #customercheckbox").tips({
					side : 1,
					msg : "该单据已审核，无法修改！",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			
			 
		}
		function unapprovalone(Id,BILLSTATUS){
			if(BILLSTATUS == 3){
				$("#"+Id+" #customercheckbox").tips({
					side:1,
		            msg:'作废单不可反审',
		            bg:'#AE81FF',
		            time:5
		        });
				return;
			}
			//1、先判断结算单里的进货单是否有二次结算的单，如果有，该单不能反审
			if(BILLSTATUS != 2){
				$("#"+Id+" #customercheckbox").tips({
					side:1,
		            msg:'未审核不可进行反审',
		            bg:'#AE81FF',
		            time:5
		        });
				return;
			}
			bootbox.confirm("确定要反审吗?", function(result) {
				if(result) {
					top.jzts();
					var note;
					note=prompt("请你输入备注"); 
					$.ajax({
						type: "POST",
						url: '<%=basePath%>customersetbill/unapprovalone.do?tm='+new Date().getTime()+"&NOTE="+note,
				    	data: {CUSTOMERSETBILL_ID:Id},
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.msg == "success"){
								
								tosearch();
							}else {
								alert(data.msg);
								top.hangge();
								return;
							}
						}
					});
				}
			});
		}
		
		//单张审批
		function approvalone(Id,paidam,BILLSTATUS){
			if(paidam == 0.0 || paidam == 0){
				$("#"+Id+" #approvalone").tips({
					side:1,
		            msg:'结算单实收金额不可为0,审批无效',
		            bg:'#AE81FF',
		            time:8
		        });
				return;
			}
			if(BILLSTATUS == 3){
				$("#"+Id+" #customercheckbox").tips({
					side:1,
		            msg:'作废单不可审批',
		            bg:'#AE81FF',
		            time:8
		        });
				return;
			}
			if(BILLSTATUS == 2){
				$("#"+Id+" #customercheckbox").tips({
					side:1,
		            msg:'已审批，不可重复审批',
		            bg:'#AE81FF',
		            time:8
		        });
				return;
			}
			bootbox.confirm("确定要审批吗?", function(result) {
				if(result) {
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>customersetbill/approvalone.do?tm='+new Date().getTime(),
				    	data: {CUSTOMERSETBILL_ID:Id},
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.msg == "success"){
								$("#"+Id+" #approvalone").tips({
									side:1,
						            msg:'审批成功',
						            bg:'#AE81FF',
						            time:8
						        });
								tosearch();
							}else if(data.msg == "error"){
								$("#"+Id+" #customercheckbox").tips({
									side:1,
						            msg:'审批失败',
						            bg:'#AE81FF',
						            time:8
						        });
								return;
							}
						}
					});
				}
			});
			
		}
		//批量审批
		function approvalAll(msg){
			//1、获取结算单id
			//2、根据结算单的进货单主键获取进货单表头
			//3、先对当前快照做一份备份，为了后面反审用
			//4、根据结算单的实收金额对进货单依次结算
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  	console.log($("#"+document.getElementsByName('ids')[i].value+" #BILLSTATUS").text().match(RegExp(/未审核/)));
					  	if(!$("#"+document.getElementsByName('ids')[i].value+" #BILLSTATUS").text().match(RegExp(/未审核/)) ){
					  		$("#"+document.getElementsByName('ids')[i].value+" #customercheckbox").tips({
								side:1,
					            msg:'该结算单不可参与批量审核',
					            bg:'#AE81FF',
					            time:8
					        });
							return;
						}
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
						if(msg == '确定要审批选中的数据吗?'){
							/* top.jzts(); */
							$.ajax({
								type: "POST",
								url: '<%=basePath%>customersetbill/approvalAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								cache: false,
								success: function(data){
									if(data.msg == "success"){
										tosearch();
									}else if(data.msg == "error"){
										alert("审批失败")
										return;
									}
								}
							});
						}
					}
				}
			});
		};
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
								url: '<%=basePath%>customersetbill/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>customersetbill/excel.do';
		}
		
		function clickaction(CUSTOMERSETBILL_ID){
			$.ajax({
		        method:'POST',
		        url:'salebill/salebilllistForCustomer',
		        data:{CUSTOMERSETBILL_ID:CUSTOMERSETBILL_ID},
		        dataType:'json',
		        success: function (res) {
		        	printsalebillhtml(res);
		        }
		    });
		}
		function printsalebillhtml(res){
			var strhtml = "";
            if(res.varList.length == 0){
            	strhtml +="<tr class='main_info'>";
            	strhtml +="<td colspan='100' class='center' >没有相关数据</td>";
            	strhtml +="</tr>";
            	
            }else{
            	for(var i = 0; i < res.varList.length; i++){
	            	var html = "";
            		if(res.QX.cha == 1){
	            		html +="<tr id='"+res.varList[i].SALEBILL_ID+"'>";
            			/* 
		            	html +="<td class='center'>";
		            	html +="	<label class='pos-rel'><input type='checkbox' name='ids' value='"+res.varList[i].SALEBILL_ID+"' class='ace' /><span class='lbl'></span></label>";
	            		html +="</td>";
	            		 */
	            		html +="<td class='center' style='width: 30px;'>"+(i+1)+"</td>";
	            		html +="<td class='center'>"+res.varList[i].BILLCODE+"</td>";
	            		if(res.varList[i].BILLTYPE=='2'){
	            			html +="<td class='center' style='color:green'>销售单</td>";
	            			html +="<td class='center'>"+res.varList[i].CUATOMERNAME+"</td>";
		            		html +="<td class='center' id='ALLAMOUNT' >"+res.varList[i].ALLAMOUNT+"</td>";
		            		html +="<td class='center' id='UNPAIDAMOUNT'>"+res.varList[i].UNPAIDAMOUNT+"</td>";
		            		html +="<td class='center' id='PAIDAMOUNT'>"+res.varList[i].PAIDAMOUNT+"</td>";
		            		html +="<td class='center' id='THISPAY'>"+res.varList[i].THISPAY+"</td>";
	            		}else{
	            			html +="<td class='center' style='color:red'>退货单</td>";
	            			html +="<td class='center'>"+res.varList[i].CUATOMERNAME+"</td>";
		            		html +="<td class='center' id='ALLAMOUNT' >-"+res.varList[i].ALLAMOUNT+"</td>";
		            		if(res.varList[i].ISSETTLEMENTED == 1){
		            			html +="<td class='center' id='UNPAIDAMOUNT'>"+res.varList[i].UNPAIDAMOUNT+"</td>";
		            			html +="<td class='center' id='PAIDAMOUNT'>-"+res.varList[i].PAIDAMOUNT+"</td>";
			            		html +="<td class='center' id='THISPAY'>-"+res.varList[i].THISPAY+"</td>";
		            			
		            		}else{
		            			html +="<td class='center' id='UNPAIDAMOUNT'>-"+res.varList[i].UNPAIDAMOUNT+"</td>";
		            			html +="<td class='center' id='PAIDAMOUNT'>"+res.varList[i].PAIDAMOUNT+"</td>";
		            			html +="<td class='center' id='THISPAY'>"+res.varList[i].THISPAY+"</td>";
		            		}
	            		}
	           
	            		if(res.varList[i].ISSETTLEMENTED == 2){
		            		html +="<td class='center' id='ISSETTLEMENTEDName'>结算中</td>";
		            		html +="<td class='center' id='ISSETTLEMENTED' style='display:none' >2</td>";
	            		}else if(res.varList[i].ISSETTLEMENTED == 1){
	            			html +="<td class='center' id='ISSETTLEMENTEDName'>已结算</td>";
	            			html +="<td class='center' id='ISSETTLEMENTED' style='display:none' >1</td>";
	            		}else if(res.varList[i].ISSETTLEMENTED == 0){
	            			html +="<td class='center' id='ISSETTLEMENTEDName'>未结算</td>";
	            			html +="<td class='center' id='ISSETTLEMENTED' style='display:none' >0</td>";
	            		}
	            		html +="<td class='center'>"+res.varList[i].PSI_NAME+"</td>";
	            		html +="<td class='center'>"+res.varList[i].NOTE+"</td>";
	            		
	            		
	            		/* html +="<td class='center'>";
	            		if(res.QX.edit != 1 && QX.del != 1){
		            		html +="<span class='label label-large label-grey arrowed-in-right arrowed-in'><i class='ace-icon fa fa-lock' title='无权限'></i></span>";
	            		}
	            		if(res.QX.CUSTOMERSETBILLSET == 1 ){
		            		html += "	<div class='hidden-sm hidden-xs btn-group'> ";
							html += "		<a class='btn btn-xs btn-success' title='结算' id='settleOnInorder' onclick=\"settleone('"+res.varList[i].SALEBILL_ID+"');\"> ";
							html += "			<i class='ace-icon fa fa-eye bigger-120' title='结算'></i> ";
							html += "		</a> ";
	            		}
						if(res.QX.RETRIALINORDERINCUSTOMERSET == 1){
							html += "		<a class='btn btn-xs btn-success' title='反审' id='retrialInorder' onclick=\"retrial('"+res.varList[i].SALEBILL_ID+"');\"> ";
							html += "			<i title='反审'>反审</i> ";
							html += "		</a> ";
						}
						if(res.QX.del == 1){
							html += "		<a class='btn btn-xs btn-danger' id='delInorder' onclick=\"del('"+res.varList[i].SALEBILL_ID+"');\" > ";
							html += "			<i class='ace-icon fa fa-trash-o bigger-120' title='删除'></i> ";
							html += "		</a> ";
						}
						html += "	</div> ";
						html += "	<div class='hidden-md hidden-lg'> ";
						html += "		<div class='inline pos-rel'> ";
						html += "			<button class='btn btn-minier btn-primary dropdown-toggle' data-toggle='dropdown' data-position='auto'> ";
						html += "				<i class='ace-icon fa fa-cog icon-only bigger-110'></i> ";
						html += "			</button> ";
						html += "";
						html += "			<ul class='dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close'> ";
						if(res.QX.CUSTOMERSETBILLSET == 1){
							html += "				<li> ";
							html += "					<a style='cursor:pointer;' id='settleOnInorder' onclick=\"settleone('"+res.varList[i].SALEBILL_ID+"');\" class='tooltip-success' data-rel='tooltip' title='结算'> ";
							html += "						<span class='green'> ";
							html += "							<i class='ace-icon fa fa-eye bigger-120'></i> ";
							html += "						</span> ";
							html += "					</a> ";
							html += "				</li> ";
						}
						if(res.QX.RETRIALINORDERINCUSTOMERSET == 1){
							html += "				<li> ";
							html += "					<a style='cursor:pointer;' id='retrialInorder' onclick='retrial('"+res.varList[i].SALEBILL_ID+"');' class='tooltip-success' data-rel='tooltip' title='反审'> ";
							html += "						<span class='green'> ";
							html += "							<i class='ace-icon fa fa-pencil-square-o bigger-120'></i> ";
							html += "						</span> ";
							html += "					</a> ";
							html += "				</li> ";
						}
						if(res.QX.del == 1){
							html += "				<li> ";
							html += "					<a style='cursor:pointer;' id='delInorder' onclick='del('"+res.varList[i].SALEBILL_ID+"');' class='tooltip-error' data-rel='tooltip' title='删除'> ";
							html += "						<span class='red'> ";
							html += "							<i class='ace-icon fa fa-trash-o bigger-120'></i> ";
							html += "						</span> ";
							html += "					</a> ";
							html += "				</li> ";
						}
						html += "			</ul> ";
						html += "		</div> ";
						html += "	</div> ";
						html += " </td>";
						 */
						
						html += " </tr>";
	            	}else if(res.QX.cha == 0){
	            		html += "<tr> ";
	            		html += "	<td colspan='100' class='center'>您无权查看</td> ";
					    html += "</tr> ";
	            	}
            		strhtml += html;
	            }
            }
            $("#realtbody").html("");
            $("#realtbody").html(strhtml);
		};
	</script>


</body>
</html>