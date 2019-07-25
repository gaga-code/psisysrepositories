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
						<form action="customersetbill/${msg }.do" method="post" name="Form" id="Form">
						<!-- 检索  -->
						<div id="zhongxin" style="padding-top: 13px;">
						<input type="hidden" id="msg" name="msg" value="${msg}"   /> 
						<input type="hidden" id="customersetbill_id" name="customersetbill_id" value="${pd.CUSTOMERSETBILL_ID}"   /> 
						<table style="margin-top:5px;">
							<tr>
								<td style="vertical-align:top;padding-left:2px;">
									<c:if test="${QX.add == 1 && msg == 'save' }">
									<a class="btn btn-mini btn-success" onclick="savecustomersetbill();">保存客户结算单</a>
									</c:if>
									<c:if test="${QX.add == 1 && msg == 'edit' }">
									<a class="btn btn-mini btn-success" onclick="editcustomersetbill();">保存客户结算单</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" id="delalldata" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'>批量删除</i></a>
									</c:if>
									<c:if test="${QX.CUSTOMERSETBILLSET == 1 }">
									<a class="btn btn-mini btn-success" onclick="settleAll('确定要结算选中的数据吗?');" title="批量结算" >批量结算</a>
									</c:if>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
								
							</tr>
						</table>
						
						<!-- 表头  -->
						<table id="billhead" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
							<tr>
								<td style="width:90px;text-align: right;padding-top: 1px;" id="select_customersetbill_name">客户:</td>
								<td style="vertical-align:top;">
								 	<select class="chosen-select form-control" name="select_customersetbill" id="select_customersetbill" data-placeholder="请选择客户" style="vertical-align:top;width: 98%;"  >
									<option value=""></option>
								  	</select>
								</td>
								<c:if test="${msg == 'edit' }">
								<td style="width:90px;text-align: right;padding-top: 1px;" >录单日期:</td>
								<td style="padding-left:2px;">${pd.LDATE}</td>
								</c:if>
								<c:if test="${msg == 'edit' }">
								<td style="width:90px;text-align: right;padding-top: 1px;">单据编号:</td>
								<td><input type="text" name="BILLCODE" id="BILLCODE" value="${pd.BILLCODE }" maxlength="32" readonly="readonly" placeholder="这里输入单据编号" title="单据编号" style="width:98%;"  /></td>
								</c:if>
								
								<td style="width:90px;text-align: right;padding-top: 1px;">经手人:</td>
								<td><input type="text" name="PSI_NAME" id="PSI_NAME" value="${pd.PSI_NAME }" maxlength="32" placeholder="这里输入经手人" title="经手人" style="width:98%;" readonly="readonly" /></td>
								<input type="hidden" id="FROMUNIT" name="FROMUNIT" value="${pd.FROMUNIT}"   /> 
								
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 1px;">经销方式:</td>
								<td>
									<select name="DISTRIBUTIONMODE" id="DISTRIBUTIONMODE" placeholder="请选择" title="经销方式" style="width:98%;background-color:#EBEBEB"  >
									<c:forEach items="${varListL}" var="var">
										<option value="${var.LEVEL_ID }" <c:if test="${var.LEVEL_ID == pd.DISTRIBUTIONMODE }">selected</c:if>>${var.TITLE }</option>
									</c:forEach>
									</select>
								</td>
								<td style="width:90px;text-align: right;padding-top: 1px;">发票类型:</td> 
								<td><input type="text" name="INVOICETYPE" id="INVOICETYPE" value="${pd.INVOICETYPE }" maxlength="32" placeholder="这里输入发票类型" title="发票类型" style="width:98%;" /></td>
								<td style="width:90px;text-align: right;padding-top: 1px;">票号:</td>
								<td><input type="text" name="BILLNO" id="BILLNO" value="${pd.BILLNO }" maxlength="32" placeholder="这里输入票号" title="票号" style="width:98%;"  /></td>
								<td style="width:90px;text-align: right;padding-top: 1px;" id="select_paymethod_name">收款方式:</td>
								<input type="hidden" id="PAYMETHOD" name="PAYMETHOD" value="${pd.PAYMETHOD}"   /> 
								<%-- <td><input type="text" name="PAYMETHOD" id="PAYMETHOD" value="${pd.PAYMETHOD }" maxlength="32" placeholder="这里输入收款方式" title="收款方式" style="width:98%;"  /></td> --%>
								<td style="vertical-align:top;">
								 	<select class="chosen-select form-control" name="select_paymethod" id="select_paymethod" data-placeholder="请选择收款方式" style="vertical-align:top;width: 98%;"  >
								  	</select>
								</td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 1px;">应收总金额:</td>
								<td><input type="number" name="PAYABLEALLAM" id="PAYABLEALLAM" value="${pd.PAYABLEALLAM }" maxlength="32" placeholder="这里输入应收总金额" title="应收总金额" style="width:98%;" readonly="readonly" /></td>
								<td style="width:90px;text-align: right;padding-top: 1px;">应收金额:</td>
								<td><input type="number" name="PAYABLEAMOUNT" id="PAYABLEAMOUNT" value="${pd.PAYABLEAMOUNT }" maxlength="32" placeholder="这里输入应收金 额" title="应收金额" style="width:98%;" readonly="readonly" /></td>
								<td style="width:90px;text-align: right;padding-top: 1px;">实收金额:</td>
								<td><input type="number" name="PAYMENTAMOUNT" id="PAYMENTAMOUNT" value="${pd.PAYMENTAMOUNT }" maxlength="32" placeholder="这里输入实收金额" title="实收金额" style="width:98%;"  /></td>
								<td style="width:90px;text-align: right;padding-top: 1px;">备注:</td>
								<td><input type="text" name="NOTE" id="NOTE" value="${pd.NOTE }" maxlength="32" placeholder="这里输入备注" title="备注" style="width:98%;" /></td>
							</tr>
						</table>
						</form>
						<form action="customersetbill/list.do" method="post" name="ListForm" id="InorderListForm">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">单据编号</th>
									<th class="center">客户</th>
									<th class="center">总金额</th>
									<th class="center">未收金额</th>
									<th class="center">已收金额</th>
									<th class="center">本次收款</th>
									<th class="center">是否结算</th>
									<th class="center">经手人</th>
									<th class="center">备注</th>
									<th class="center" style="width:110px;">操作</th>
								</tr>
							</thead>
							<tbody id="realtbody">
							
							</tbody>
						</table>
						
						
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
	$(top.hangge());
		var inorderitemmap = new Map(); //进货单每个子项目的html代码
        var allamountmap=new Map();  //总金额
        var unpaidamountmap=new Map(); //未收金额
        var paidamoutmap=new Map();  // 已收金额 
        var customerhaspaidamount=0.0;   //已结算进货单的总额 
        var settledinordermap = new Map();  //结过算的进货单主键 
        var settleList = [];//结算进货单的数据
        var msg = "";
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(window).on('load', function () {
			var sid = $("#FROMUNIT").val();
			var payid = $("#PAYMETHOD").val();
			msg = $("#msg").val();
			
			
			
			//获取客户列表
		    $.ajax({
		        method:'POST',
		        url:'customer/listNameAndID',
		        dataType:'json',
		        success: function (res) {
		            var html="<option value='0'>请选择客户</option>";
		            console.log(res);
		            for (var i = 0; i < res.varList.length; i++) {
		                if (sid == res.varList[i].CUSTOMER_ID) {
		                    html += "<option  value='" + res.varList[i].CUSTOMER_ID + "' selected='selected' data-name='"+res.varList[i].CUATOMERNAME+"'>" + res.varList[i].CUATOMERNAME + "</option>";
		                } else {
		                    html += "<option  value='" + res.varList[i].CUSTOMER_ID + "' data-name='"+res.varList[i].CUATOMERNAME+"'>"+ res.varList[i].CUATOMERNAME + "</option>";
		                }
		            }
		            $("#select_customersetbill").html(html);
		            $('#select_customersetbill').trigger("chosen:updated");//重置下拉框  
		            $('#select_customersetbill').chosen();//下拉框检索框架
		        }
		    }); 
			//获取支收方式列表
		    $.ajax({
				method:'POST',
				url:'payment/listjson',
				dataType:'json',
				success:function(res){
					var html="<option value='0'>请选择收款方式</option>";
		            console.log(res);
		            for (var i = 0; i < res.varList.length; i++) {
		                if (payid == res.varList[i].PAYMETHOD_ID) {
		                    html += "<option  value='" + res.varList[i].PAYMETHOD_ID + "' selected='selected' data-name='"+res.varList[i].PAYMETHODNAME+"'>" + res.varList[i].PAYMETHODNAME + "</option>";
		                } else {
		                    html += "<option  value='" + res.varList[i].PAYMETHOD_ID + "' data-name='"+res.varList[i].PAYMETHODNAME+"'>"+ res.varList[i].PAYMETHODNAME + "</option>";
		                }
		            }
		            $("#select_paymethod").html(html);
		            $('#select_paymethod').trigger("chosen:updated");//重置下拉框  
		            $('#select_paymethod').chosen();//下拉框检索框架
				}
			});
			
		    if(msg == "edit"){
		    	cleanvalue();
				printrealtbody(4,sid); //结算状态只有3种，4代表0未结算 和 2结算中
			}
			
		});
		
		$("#select_customersetbill").on('change',function(e,params){
			var sid = $("#select_customersetbill").val();
			cleanAllvalue();
			printrealtbody(0,sid);//新增时，获取未结算（0）的进货单
		});
		
		function printrealtbody(settlestatus,sid){
			if(sid == 0){
				$("#realtbody").html("");
				var strhtml="";
				strhtml +="<tr class='main_info'>";
            	strhtml +="<td colspan='100' class='center' >没有相关数据</td>";
            	strhtml +="</tr>";
            	$("#realtbody").html(strhtml);
			}else{
				$.ajax({
			        method:'POST',
			        url:'salebill/salebilllistForCustomerAdd',
			        data:{ISSETTLEMENTED:settlestatus,CUSTOMER_ID:sid},
			        dataType:'json',
			        success: function (res) {
			            var strhtml = "";
			            if(res.varList.length == 0){
			            	strhtml +="<tr class='main_info'>";
			            	strhtml +="<td colspan='100' class='center' >没有相关数据</td>";
			            	strhtml +="</tr>";
			            }else{
			            	for(var i = 0; i < res.varList.length; i++){
				            	var html = "";
			            		if(res.QX.cha == 1){
				            		html +="<tr id='"+res.varList[i].INORDER_ID+"'>";
					            	html +="<td class='center'>";
					            	html +="	<label class='pos-rel'><input type='checkbox' name='ids' value='"+res.varList[i].INORDER_ID+"' class='ace' /><span class='lbl'></span></label>";
				            		html +="</td>";
				            		html +="<td class='center' style='width: 30px;'>"+(i+1)+"</td>";
				            		html +="<td class='center'>"+res.varList[i].BILLCODE+"</td>";
				            		html +="<td class='center'>"+res.varList[i].CUATOMERNAME+"</td>";
				            		html +="<td class='center' id='ALLAMOUNT' >"+res.varList[i].ALLAMOUNT+"</td>";
				            		html +="<td class='center' id='UNPAIDAMOUNT'>"+res.varList[i].UNPAIDAMOUNT+"</td>";
				            		html +="<td class='center' id='PAIDAMOUNT'>"+res.varList[i].PAIDAMOUNT+"</td>";
				            		html +="<td class='center' id='THISPAY'>"+res.varList[i].THISPAY+"</td>";
				            		
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
				            		html +="<td class='center'>";
				            		if(res.QX.edit != 1 && QX.del != 1){
					            		html +="<span class='label label-large label-grey arrowed-in-right arrowed-in'><i class='ace-icon fa fa-lock' title='无权限'></i></span>";
				            		}
				            		if(res.QX.SUPPSETBILLSET == 1 ){
					            		html += "	<div class='hidden-sm hidden-xs btn-group'> ";
										html += "		<a class='btn btn-xs btn-success' title='结算' id='settleOnInorder' onclick=\"settleone('"+res.varList[i].INORDER_ID+"');\"> ";
										html += "			<i class='ace-icon fa fa-eye bigger-120' title='结算'></i> ";
										html += "		</a> ";
				            		}
									if(res.QX.RETRIALINORDERINSUPPSET == 1){
										html += "		<a class='btn btn-xs btn-success' title='反审' id='retrialInorder' onclick=\"retrial('"+res.varList[i].INORDER_ID+"');\"> ";
										html += "			<i title='反审'>反审</i> ";
										html += "		</a> ";
									}
									if(res.QX.del == 1){
										html += "		<a class='btn btn-xs btn-danger' id='delInorder' onclick=\"del('"+res.varList[i].INORDER_ID+"');\" > ";
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
									if(res.QX.SUPPSETBILLSET == 1){
										html += "				<li> ";
										html += "					<a style='cursor:pointer;' id='settleOnInorder' onclick=\"settleone('"+res.varList[i].INORDER_ID+"');\" class='tooltip-success' data-rel='tooltip' title='结算'> ";
										html += "						<span class='green'> ";
										html += "							<i class='ace-icon fa fa-eye bigger-120'></i> ";
										html += "						</span> ";
										html += "					</a> ";
										html += "				</li> ";
									}
									if(res.QX.RETRIALINORDERINSUPPSET == 1){
										html += "				<li> ";
										html += "					<a style='cursor:pointer;' id='retrialInorder' onclick='retrial('"+res.varList[i].INORDER_ID+"');' class='tooltip-success' data-rel='tooltip' title='反审'> ";
										html += "						<span class='green'> ";
										html += "							<i class='ace-icon fa fa-pencil-square-o bigger-120'></i> ";
										html += "						</span> ";
										html += "					</a> ";
										html += "				</li> ";
									}
									if(res.QX.del == 1){
										html += "				<li> ";
										html += "					<a style='cursor:pointer;' id='delInorder' onclick='del('"+res.varList[i].INORDER_ID+"');' class='tooltip-error' data-rel='tooltip' title='删除'> ";
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
									html += " </tr>";
									inorderitemmap.set(res.varList[i].INORDER_ID,html);
									allamountmap.set(res.varList[i].INORDER_ID,res.varList[i].ALLAMOUNT);  //总金额
				            		unpaidamountmap.set(res.varList[i].INORDER_ID,res.varList[i].UNPAIDAMOUNT);  //未收金额
				            		paidamoutmap.set(res.varList[i].INORDER_ID,res.varList[i].PAIDAMOUNT);  //已收金额
				            	}else if(res.QX.cha == 0){
				            		html += "<tr> ";
				            		html += "	<td colspan='100' class='center'>您无权查看</td> ";
								    html += "</tr> ";
				            	}
			            		strhtml += html;
				            }
			            }
			            compu();//统计额度
			            var unpaidam=0.0;
			            unpaidamountmap.forEach(function(value, key) {
			            	unpaidam += value;
						});
			            $("#PAYABLEAMOUNT").val(unpaidam);
			            $("#realtbody").html("");
			            $("#realtbody").html(strhtml);
			        }
			    }); 
			}
		}
		
		$(function() {
			$("#LDATE").attr("disabled","disabled");
			
			$('#PAYMENTAMOUNT').bind('input porpertychange',function(){
	            if($("#select_paymethod").val() == '0' && $("#select_customersetbill").val() == '0'){
	            	$("#PAYMENTAMOUNT").tips({
						side : 1,
						msg : "请先选择客户和收款方式",
						bg : '#FF5080',
						time : 3
					});
	            }else  if($("#select_customersetbill").val() == '0' ){
	            	$("#PAYMENTAMOUNT").tips({
						side : 1,
						msg : "请先选择客户",
						bg : '#FF5080',
						time : 3
					});
	            }else  if($("#select_paymethod").val() == '0' ){
	            	$("#PAYMENTAMOUNT").tips({
						side : 1,
						msg : "请先选择收款方式",
						bg : '#FF5080',
						time : 3
					});
	            }else{
	            	if(parseInt($("#PAYABLEAMOUNT").val())+customerhaspaidamount < parseInt($("#PAYMENTAMOUNT").val())){
		            	$("#PAYMENTAMOUNT").tips({
							side : 1,
							msg : "收款多了",
							bg : '#FF5080',
							time : 3
						});
		            	$("#PAYMENTAMOUNT").val(parseInt($("#PAYABLEAMOUNT").val())+customerhaspaidamount)
		            }
		            if(parseInt($("#PAYMENTAMOUNT").val()) < customerhaspaidamount && customerhaspaidamount != 0.0){
		            	$("#PAYMENTAMOUNT").tips({
							side : 1,
							msg : "当前收款总额至少为已结算进货单的额度",
							bg : '#FF5080',
							time : 3
						});
		            	$(this).val(customerhaspaidamount);
		            }
	            }
	            
	            
	        });
			
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
		
				
		//删除进货单
		function del(Id){
			if(settledinordermap.get(Id) == null){
				bootbox.confirm("确定要删除吗?", function(result) {
					if(result) {
						delreal(Id);
					}
				});
				
			}else{
				$("#"+Id+" #delInorder").tips({
					side : 1,
					msg : "该进货单已经结算过,不可删除",
					bg : '#FF5080',
					time : 5
				});
			}
		}
		function delreal(Id){
			allamountmap.delete(Id);
			paidamoutmap.delete(Id);
			unpaidamountmap.delete(Id);
			inorderitemmap.delete(Id);
			var strhtml = "";
			inorderitemmap.forEach(function(value, key) {
				 strhtml += value;
			});
			$('#realtbody').html("");
			$('#realtbody').html(strhtml);
			compu();
			var unpaidam=0.0;
            unpaidamountmap.forEach(function(value, key) {
            	unpaidam += value;
			});
            $("#PAYABLEAMOUNT").val(unpaidam);
		}
		//统计额度
		function compu(){
			var allam = 0.0;
            allamountmap.forEach(function(value, key) {
            	allam += value;
			});
            var paidam=0.0;
            paidamoutmap.forEach(function(value, key) {
            	paidam += value;
			});
           
            $("#PAYABLEALLAM").val(allam);
			/* $("#PAYABLEAMOUNT").val(paidam); */
			
		}
		
		//清空所有数值    新增时调用到
		function cleanAllvalue(){
            $("#PAYABLEALLAM").val('');
			$("#PAYMENTAMOUNT").val('');
			$("#PAYABLEAMOUNT").val('');
            $("#INVOICETYPE").val('');
			$("#BILLNO").val('');
			$("#PAYMETHOD").val('');
			$("#NOTE").val('');
			inorderitemmap.clear();
			allamountmap.clear();
			paidamoutmap.clear();
			unpaidamountmap.clear();
			settledinordermap.clear();
			customerhaspaidamount = 0.0;
			settleList = [];
		}
		//清空除了实收金额的其他数值       修改时调用到
		function cleanvalue(){
            $("#PAYABLEALLAM").val('');
            $("#PAYABLEAMOUNT").val('');
			inorderitemmap.clear();
			allamountmap.clear();
			paidamoutmap.clear();
			unpaidamountmap.clear();
			settledinordermap.clear();
			customerhaspaidamount = 0.0;
			settleList = [];
		}
		
		//反审进货单 retrialInorder
		function retrial(Id){
			if(settledinordermap.get(Id) != null){
				bootbox.confirm("确定要反审该进货单吗?", function(result) {
					if(result) {
						$.ajax({
					        method:'POST',
					        url:'salebill/retrialInorder',
					        data:{INORDER_ID:Id},
					        dataType:'json',
					        success: function (res) {
					        	delreal(Id);
					        }
					    });
					}
				});
			}else{
				$("#"+Id+" #retrialInorder").tips({
					side : 1,
					msg : "该进货单已经结算,不可反审",
					bg : '#FF5080',
					time : 5
				});
			}
		}
		//结算一张进货单
		function settleone(Id){
			if(settledinordermap.get(Id) != 1){
				var unpaid = unpaidamountmap.get(Id);
				var allamount = allamountmap.get(Id);
				var paidamout = paidamoutmap.get(Id);
				var thispayamount = parseInt($("#PAYMENTAMOUNT").val());
				if(thispayamount == 0.0){
					$("#PAYMENTAMOUNT").tips({
						side : 1,
						msg : "客户结算单的实收金额不可为空",
						bg : '#FF5080',
						time : 5
					});
				}
				var canpaidamount = thispayamount-customerhaspaidamount;  //可进行进货单结算的余额=结算单收款金额-已结算进货单总额 
				if(canpaidamount == 0.0){
					$("#PAYMENTAMOUNT").tips({
						side : 1,
						msg : "当前实收金额已用于结算了，请增加额度，才可进行接下来的进货单结算",
						bg : '#FF5080',
						time : 5
					});
				}else {
					var nextinordercanpaid = 0.0;
					var nosettle = 0;
					if(canpaidamount < unpaid){
						nextinordercanpaid = canpaidamount;
						nosettle = 0;
					}else{
						nextinordercanpaid = unpaid;
						nosettle = 1;
					}
					bootbox.confirm("确定要结算该进货单吗?", function(result) {
						if(result) {
							customerhaspaidamount += nextinordercanpaid;
			        		$("#"+Id+" #UNPAIDAMOUNT").html(unpaid-nextinordercanpaid);
			        	    $("#"+Id+" #PAIDAMOUNT").html(paidamout+nextinordercanpaid);
			        		$("#"+Id+" #THISPAY").html(nextinordercanpaid);
			        		$("#"+Id+" #ISSETTLEMENTED").html(2);
			        		settledinordermap.set(Id,nosettle);
			        		paidamoutmap.set(Id,paidamoutmap.get(Id)+nextinordercanpaid);
			        		unpaidamountmap.set(Id,unpaidamountmap.get(Id)-nextinordercanpaid);
			        		compu();
						}
					})
					
					
					
					/*					bootbox.confirm("确定要结算该进货单吗?", function(result) {
						if(result) {
							customerhaspaidamount += nextinordercanpaid;
			        		$("#"+Id+" #UNPAIDAMOUNT").html(unpaid-nextinordercanpaid);
			        	    $("#"+Id+" #PAIDAMOUNT").html(paidamout+nextinordercanpaid);
			        		$("#"+Id+" #THISPAY").html(nextinordercanpaid);
			        		$("#"+Id+" #ISSETTLEMENTED").html(0);
			        		settledinordermap.set(Id,Id);
			        		paidamoutmap.set(Id,paidamoutmap.get(Id)+nextinordercanpaid);
			        		unpaidamountmap.set(Id,unpaidamountmap.get(Id)-nextinordercanpaid);
			        		compu();
 							$.ajax({
						        method:'POST',
						        url:'salebill/settleOneInOrder',
						        data:{INORDER_ID:Id,CANPAID:nextinordercanpaid,UNPAIDAMOUNT:unpaid,ALLAMOUNT:allamount,PAIDAMOUNT:paidamout},
						        dataType:'json',
						        success: function (res) {
						        	if(res.msg == "success"){// UNPAIDAMOUNT  PAIDAMOUNT  THISPAY  ISSETTLEMENTED
						        		$("#"+res.pd.INORDER_ID+" #UNPAIDAMOUNT").html(res.pd.UNPAIDAMOUNT);
						        	    $("#"+res.pd.INORDER_ID+" #PAIDAMOUNT").html(res.pd.PAIDAMOUNT);
						        		$("#"+res.pd.INORDER_ID+" #THISPAY").html(res.pd.THISPAY);
						        		$("#"+res.pd.INORDER_ID+" #ISSETTLEMENTED").html(res.pd.ISSETTLEMENTED);
						        		settledinordermap.set(res.pd.INORDER_ID,res.pd.INORDER_ID);
						        		paidamoutmap.set(res.pd.INORDER_ID,paidamoutmap.get(res.pd.INORDER_ID)+res.pd.THISPAY);
						        		unpaidamountmap.set(res.pd.INORDER_ID,unpaidamountmap.get(res.pd.INORDER_ID)-res.pd.THISPAY);
						        		compu();
						        	}else if(res.msg == "error"){
						        		customerhaspaidamount -= nextinordercanpaid;
						        		
						        	}
						        }
						    }); 
						}
					});*/
				}
			}else{
				$("#"+Id+" #settleOnInorder").tips({
					side : 1,
					msg : "该进货单已经结算",
					bg : '#FF5080',
					time : 5
				});
			}
		}
		
		//批量结算
		function settleAll(msg){
			var thispayamount = $("#PAYMENTAMOUNT").val();
			
			if(thispayamount == 0.0 || thispayamount == null ){
				$("#PAYMENTAMOUNT").tips({
					side : 1,
					msg : "客户结算单的实收金额不可为空",
					bg : '#FF5080',
					time : 5
				});
			}
			var canpaidamount = thispayamount-customerhaspaidamount;  //可进行进货单结算的余额=结算单收款金额-已结算进货单总额 
			var canback = canpaidamount;
			if(canpaidamount == 0.0){
				$("#PAYMENTAMOUNT").tips({
					side : 1,
					msg : "当前实收金额已用于结算了，请增加额度，才可进行接下来的进货单结算",
					bg : '#FF5080',
					time : 5
				});
			}else {
				bootbox.confirm(msg, function(result) {
					if(result) {
						var isnone = 0;
						for(var i=0;i < document.getElementsByName('ids').length;i++){
						  if(document.getElementsByName('ids')[i].checked){
							  
						  	isnone = 1;
						  	break;
						  }
						}
						var candel = 1;
						for(var i=0;i < document.getElementsByName('ids').length;i++){
						  if(settledinordermap.get(document.getElementsByName('ids')[i].value) == 1){
							  candel = 0;
						  	break;
						  }
						}
						if(candel == 0){
							$("#delalldata").tips({
								side : 1,
								msg : "不可批量结算，部分单据结算过",
								bg : '#FF5080',
								time : 5
							});
							return ;
						}
						
						if(isnone==0){
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
							if(msg == '确定要结算选中的数据吗?'){
								for(var i=0;i < document.getElementsByName('ids').length;i++){
									if(document.getElementsByName('ids')[i].checked){
										  var id = document.getElementsByName('ids')[i].value;
										  var unpaid = unpaidamountmap.get(id);
										  if(canpaidamount >= unpaid){//可结算的单
											  canpaidamount -= unpaid;
											  $("#"+id+" #UNPAIDAMOUNT").html(unpaidamountmap.get(id)-unpaid);
								        	  $("#"+id+" #PAIDAMOUNT").html(paidamoutmap.get(id)+unpaid);
								        	  $("#"+id+" #THISPAY").html(unpaid);
								        	  $("#"+id+" #ISSETTLEMENTED").html(2);
								        	  paidamoutmap.set(id,paidamoutmap.get(id)+unpaid);
								              unpaidamountmap.set(id,unpaidamountmap.get(id)-unpaid);
								              settledinordermap.set(id,1);
										  }else{//不可结算
											  $("#"+id+" #UNPAIDAMOUNT").html(unpaidamountmap.get(id)-canpaidamount);
								        	  $("#"+id+" #PAIDAMOUNT").html(paidamoutmap.get(id)+canpaidamount);
								        	  $("#"+id+" #THISPAY").html(canpaidamount);
								        	  $("#"+id+" #ISSETTLEMENTED").html(2);
								              settledinordermap.set(id,0);
								        	  paidamoutmap.set(id,paidamoutmap.get(id)+canpaidamount);
								              unpaidamountmap.set(id,unpaidamountmap.get(id)-canpaidamount);
											  break;
										  }
									}
								}
								compu();
								customerhaspaidamount += canback;
								
/* 								var j=0;
								for(var i=0;i < document.getElementsByName('ids').length;i++){
									if(document.getElementsByName('ids')[i].checked && settledinordermap.get(document.getElementsByName('ids')[i].value) == null){
										  settleList[j] = new Object();
										  var id = document.getElementsByName('ids')[i].value;
										  settleList[j].INORDER_ID=id;
										  var unpaid = unpaidamountmap.get(id);
										  if(canpaidamount >= unpaid){//可结算的单
											  canpaidamount -= unpaid;
											  settleList[j].THISPAY=unpaid;
											  settleList[j].UNPAIDAMOUNT=unpaid;
											  settleList[j].ALLAMOUNT=allamountmap.get(id);
											  settleList[j].PAIDAMOUNT=paidamoutmap.get(id);
											  settleList[j].ISSETTLEMENTED="1";
											  j++;
										  }else{//不可结算
											  settleList[j].THISPAY=canpaidamount;
											  settleList[j].UNPAIDAMOUNT=unpaid;
											  settleList[j].ALLAMOUNT=allamountmap.get(id);
											  settleList[j].PAIDAMOUNT=paidamoutmap.get(id);
											  settleList[j].ISSETTLEMENTED="0";
											  break;
										  }
									}
								}
								customerhaspaidamount += canback;
								$.ajax({
							        method:'POST',
							        url:'salebill/settleAllInOrder',
							        data:{SettleList:JSON.stringify(eval(settleList))},
							        dataType:'json',
							        success: function (res) {
							        	if(res.msg == "success"){// UNPAIDAMOUNT  PAIDAMOUNT  THISPAY  ISSETTLEMENTED JSON.stringify(eval(settleList))
							        		for(var i = 0; i < res.varList.length; i++){
							        			$("#"+res.varList[i].INORDER_ID+" #UNPAIDAMOUNT").html(res.varList[i].UNPAIDAMOUNT);
								        	    $("#"+res.varList[i].INORDER_ID+" #PAIDAMOUNT").html(res.varList[i].PAIDAMOUNT);
								        		$("#"+res.varList[i].INORDER_ID+" #THISPAY").html(res.varList[i].THISPAY);
								        		$("#"+res.varList[i].INORDER_ID+" #ISSETTLEMENTED").html(res.varList[i].ISSETTLEMENTED);
								        		paidamoutmap.set(res.varList[i].INORDER_ID,paidamoutmap.get(res.varList[i].INORDER_ID)+res.varList[i].THISPAY);
								        		unpaidamountmap.set(res.varList[i].INORDER_ID,unpaidamountmap.get(res.varList[i].INORDER_ID)-res.varList[i].THISPAY);
								        		settledinordermap.set(res.varList[i].INORDER_ID,res.varList[i].INORDER_ID);
							        		}
							        		compu();
							        	}else if(res.msg == "error"){
							        		customerhaspaidamount -= canback;
							        		
							        	}
							        }
							    }); */
								
								
							}
						}
					}
				});
			}
			
			
		};
		
		
		
		
		
		
		//批量删除
		function makeAll(msg){
			
			bootbox.confirm(msg, function(result) {
				if(result) {
					var isnone = 0;
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	isnone = 1;
					  	break;
					  }
					}
					var candel = 1;
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(settledinordermap.get(document.getElementsByName('ids')[i].value) == 1){
						  candel = 0;
					  	break;
					  }
					}
					if(candel == 0){
						$("#zcheckbox").tips({
							side : 1,
							msg : "不可批量删除，部分单据结算过",
							bg : '#FF5080',
							time : 5
						});
						return ;
					}
					if(isnone==0){
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
							for(var i=0;i < document.getElementsByName('ids').length;i++){
								  if(document.getElementsByName('ids')[i].checked){
									  	var Id = document.getElementsByName('ids')[i].value;
									  	allamountmap.delete(Id);
										paidamoutmap.delete(Id);
										unpaidamountmap.delete(Id);
										inorderitemmap.delete(Id);
										
								  }
							}
							var strhtml = "";
							inorderitemmap.forEach(function(value, key) {
								 strhtml += value;
							});
							$('#realtbody').html("");
							$('#realtbody').html(strhtml);
							compu();
							var unpaidam=0.0;
				            unpaidamountmap.forEach(function(value, key) {
				            	unpaidam += value;
							});
				            $("#PAYABLEAMOUNT").val(unpaidam);
						}
					}
				}
			});
		};
		
		//新增保存
		function savecustomersetbill(){
			if($("#select_customersetbill").val() == 0){
				$("#select_customersetbill_name").tips({
					side : 1,
					msg : "请先选择客户，然后对其进货单进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if(settledinordermap.size == 0){
				$("#zcheckbox").tips({
					side : 1,
					msg : "请先对进货单进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#select_paymethod").val() == 0){
				$("#select_paymethod_name").tips({
					side : 1,
					msg : "请先选择客户，然后对其进货单进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			
			if($("#PAYABLEALLAM").val() == 0){
				$("#PAYABLEALLAM").tips({
					side : 1,
					msg : "应收总金额不可为空，请选择客户进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#PAYABLEAMOUNT").val() == 0){
				$("#PAYABLEAMOUNT").tips({
					side : 1,
					msg : "应收金额不可为空，请选择客户进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#PAYMENTAMOUNT").val() == 0){
				$("#PAYMENTAMOUNT").tips({
					side : 1,
					msg : "实收金额不可为空，请选择客户进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#DISTRIBUTIONMODE").val() == 0){
				$("#DISTRIBUTIONMODE").tips({
					side : 1,
					msg : "经销方式不可为空",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			var settleInOrder_ID = "";
			settledinordermap.forEach(function(value, key){
				if(settleInOrder_ID == ""){
					settleInOrder_ID += "'"+key+"'";
				}else{
					settleInOrder_ID += ",'"+key+"'";
				}
			});
			
			/* select_customersetbill DISTRIBUTIONMODE INVOICETYPE BILLNO select_paymethod PAYABLEALLAM PAYABLEAMOUNT PAYMENTAMOUNT NOTE */
			var FROMUNIT= $("#select_customersetbill").val();
			var DISTRIBUTIONMODE= $("#DISTRIBUTIONMODE").val();
			var INVOICETYPE= $("#INVOICETYPE").val()==null?"":$("#INVOICETYPE").val();
			var BILLNO= $("#BILLNO").val()==null?"":$("#BILLNO").val();
			var PAYMETHOD= $("#select_paymethod").val();
			var PAYABLEALLAM= $("#PAYABLEALLAM").val();
			var PAYABLEAMOUNT= $("#PAYABLEAMOUNT").val();
			var PAYMENTAMOUNT= $("#PAYMENTAMOUNT").val();
			var NOTE= $("#NOTE").val() == null ?"":$("#NOTE").val();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>customersetbill/save.do?tm='+new Date().getTime(),
		    	data: {
		    		INORDER_IDS:settleInOrder_ID,
		    		FROMUNIT:FROMUNIT,
		    		DISTRIBUTIONMODE:DISTRIBUTIONMODE,
		    		INVOICETYPE:INVOICETYPE,
		    		BILLNO:BILLNO,
		    		PAYMETHOD:PAYMETHOD,
		    		PAYABLEALLAM:PAYABLEALLAM,
		    		PAYABLEAMOUNT:PAYABLEAMOUNT,
		    		PAYMENTAMOUNT:PAYMENTAMOUNT,
		    		NOTE:NOTE
		    	},
				dataType:'json',
				success: function(data){
					 if(data.msg=="success" || data.msg==""){
						 top.Dialog.close();
					 }
					 if(data.msg=="error"){
						 $("#zhongxin2").hide();
					 }
				}
			});
		}
		//修改保存
		function editcustomersetbill(){
			if($("#select_customersetbill").val() == 0){
				$("#select_customersetbill_name").tips({
					side : 1,
					msg : "请先选择客户，然后对其进货单进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if(settledinordermap.size == 0){
				$("#zcheckbox").tips({
					side : 1,
					msg : "请先对进货单进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#select_paymethod").val() == 0){
				$("#select_paymethod_name").tips({
					side : 1,
					msg : "请先选择客户，然后对其进货单进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			
			if($("#PAYABLEALLAM").val() == 0){
				$("#PAYABLEALLAM").tips({
					side : 1,
					msg : "应收总金额不可为空，请选择客户进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#PAYABLEAMOUNT").val() == 0){
				$("#PAYABLEAMOUNT").tips({
					side : 1,
					msg : "应收金额不可为空，请选择客户进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#PAYMENTAMOUNT").val() == 0){
				$("#PAYMENTAMOUNT").tips({
					side : 1,
					msg : "实收金额不可为空，请选择客户进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#DISTRIBUTIONMODE").val() == 0){
				$("#DISTRIBUTIONMODE").tips({
					side : 1,
					msg : "经销方式不可为空",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			var settleInOrder_ID = "";
			settledinordermap.forEach(function(value, key){
				if(settleInOrder_ID == ""){
					settleInOrder_ID += "'"+key+"'";
				}else{
					settleInOrder_ID += ",'"+key+"'";
				}
			});
			
			/* select_customersetbill DISTRIBUTIONMODE INVOICETYPE BILLNO select_paymethod PAYABLEALLAM PAYABLEAMOUNT PAYMENTAMOUNT NOTE */
			var FROMUNIT= $("#select_customersetbill").val();
			var DISTRIBUTIONMODE= $("#DISTRIBUTIONMODE").val();
			var INVOICETYPE= $("#INVOICETYPE").val()==null?"":$("#INVOICETYPE").val();
			var BILLNO= $("#BILLNO").val()==null?"":$("#BILLNO").val();
			var PAYMETHOD= $("#select_paymethod").val();
			var PAYABLEALLAM= $("#PAYABLEALLAM").val();
			var PAYABLEAMOUNT= $("#PAYABLEAMOUNT").val();
			var PAYMENTAMOUNT= $("#PAYMENTAMOUNT").val();
			var NOTE= $("#NOTE").val() == null ?"":$("#NOTE").val();
			var customersetbill_id = $("#customersetbill_id").val();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>customersetbill/edit.do?tm='+new Date().getTime(),
		    	data: {
		    		INORDER_IDS:settleInOrder_ID,
		    		FROMUNIT:FROMUNIT,
		    		DISTRIBUTIONMODE:DISTRIBUTIONMODE,
		    		INVOICETYPE:INVOICETYPE,
		    		BILLNO:BILLNO,
		    		PAYMETHOD:PAYMETHOD,
		    		PAYABLEALLAM:PAYABLEALLAM,
		    		PAYABLEAMOUNT:PAYABLEAMOUNT,
		    		PAYMENTAMOUNT:PAYMENTAMOUNT,
		    		NOTE:NOTE,
		    		CUSTOMERSETBILL_ID:customersetbill_id
		    	},
				dataType:'json',
				success: function(data){
					 if(data.msg=="success" || data.msg==""){
						 top.Dialog.close();
					 }
					 if(data.msg=="error"){
						 $("#zhongxin2").hide();
					 }
				}
			});
		}
		
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>customersetbill/excel.do';
		}
	</script>


</body>
</html>