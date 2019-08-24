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
						<div id="zhongxin" style="padding-top: 13px;">
						<input type="hidden" id="msg" name="msg" value="${msg}"   /> 
						<input type="hidden" id="suppsetbill_id" name="suppsetbill_id" value="${pd.SUPPSETBILL_ID}"   /> 
						<table style="margin-top:5px;">
							<tr>
								<td style="vertical-align:top;padding-left:2px;">
									<c:if test="${QX.add == 1 && msg == 'save' }">
									<a class="btn btn-mini btn-success" onclick="savesuppsetbill();">保存供应商结算单</a>
									</c:if>
									<c:if test="${QX.edit == 1 && msg == 'edit' }">
									<a class="btn btn-mini btn-success" onclick="editsuppsetbill();">保存供应商结算单</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" id="delalldata" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'>批量删除</i></a>
									</c:if>
									<c:if test="${QX.SUPPSETBILLSET == 1 }">
									<a class="btn btn-mini btn-success" onclick="settleAll('确定要结算选中的数据吗?');" title="批量结算" >批量结算</a>
									</c:if>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
								
							</tr>
						</table>
						
						<!-- 表头  -->
						<table id="billhead" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
							<tr>
								<td style="width:90px;text-align: right;padding-top: 1px;" id="select_suppsetbill_name">供应商:</td>
								<td style="vertical-align:top;">
								 	<select class="chosen-select form-control" name="select_suppsetbill" id="select_suppsetbill" data-placeholder="请选择供应商"  style="vertical-align:top;width: 98%;"  >
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
								<td style="width:90px;text-align: right;padding-top: 1px;" id="select_paymethod_name">付款方式:</td>
								<input type="hidden" id="PAYMETHOD" name="PAYMETHOD" value="${pd.PAYMETHOD}"   /> 
								<%-- <td><input type="text" name="PAYMETHOD" id="PAYMETHOD" value="${pd.PAYMETHOD }" maxlength="32" placeholder="这里输入付款方式" title="付款方式" style="width:98%;"  /></td> --%>
								<td style="vertical-align:top;">
								 	<select class="chosen-select form-control" name="select_paymethod" id="select_paymethod" data-placeholder="请选择付款方式" style="vertical-align:top;width: 98%;"  >
								  	</select>
								</td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 1px;">应付总金额:</td>
								<td><input type="number" name="PAYABLEALLAM" id="PAYABLEALLAM" value="${pd.PAYABLEALLAM }" maxlength="32" placeholder="这里输入应付总金额" title="应付总金额" style="width:98%;" readonly="readonly" /></td>
								<td style="width:90px;text-align: right;padding-top: 1px;">应付金额:</td>
								<td><input type="number" name="PAYABLEAMOUNT" id="PAYABLEAMOUNT" value="${pd.PAYABLEAMOUNT }" maxlength="32" placeholder="这里输入应付金 额" title="应付金额" style="width:98%;" readonly="readonly" /></td>
								<td style="width:90px;text-align: right;padding-top: 1px;">实付金额:</td>
								<td><input type="number" name="PAYMENTAMOUNT" id="PAYMENTAMOUNT" onchange="changepay()" value="${pd.PAYMENTAMOUNT }" maxlength="32" placeholder="这里输入实付金额" title="实付金额" style="width:98%;"  /></td>
								<td style="width:90px;text-align: right;padding-top: 1px;">备注:</td>
								<td><input type="text" name="NOTE" id="NOTE" value="${pd.NOTE }" maxlength="32" placeholder="这里输入备注" title="备注" style="width:98%;" /></td>
							</tr>
						</table>
						</form>
						<form action="suppsetbill/list.do" method="post" name="ListForm" id="InorderListForm">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">单据编号</th>
									<th class="center">单据类型</th>
									<th class="center">供应商</th>
									<th class="center">供应商票号</th>
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
        var unpaidamountmap=new Map(); //未付金额 --结算会修改
        var firstmap=new Map(); //原先未付金额 -- 结算不修改
        var paidamoutmap=new Map();  // 已付金额 
        var supphaspaidamount=0.0;   //已结算进货单的总额 
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
			
			
			
			//获取供应商列表
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
			//获取支付方式列表
		    $.ajax({
				method:'POST',
				url:'payment/listjson',
				dataType:'json',
				success:function(res){
					var html="<option value='0'>请选择付款方式</option>";
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
		
		$("#select_suppsetbill").on('change',function(e,params){
			var sid = $("#select_suppsetbill").val();
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
			        url:'inorder/inOrderlistForSuppAdd',
			        data:{ISSETTLEMENTED:settlestatus,SUPPLIER_ID:sid},
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
					            	html +="	<label class='pos-rel'><input type='checkbox' name='ids' value='"+res.varList[i].INORDER_ID+","+res.varList[i].BILLTYPE+"' class='ace' /><span class='lbl'></span></label>";
				            		html +="</td>";
				            		html +="<td class='center' style='width: 30px;'>"+(i+1)+"</td>";
				            		html +="<td class='center'>"+res.varList[i].BILLCODE+"</td>";
				            		if(res.varList[i].BILLTYPE=='1'){
				            			html +="<td class='center' style='color:green'>进货单</td>";
				            			html +="<td class='center'>"+res.varList[i].SUPPLIERNAME+"</td>";
					            		html +="<td class='center'>"+res.varList[i].SUPPLIERNO+"</td>";
					            		html +="<td class='center' id='ALLAMOUNT' >"+res.varList[i].ALLAMOUNT+"</td>";
					            		html +="<td class='center' id='UNPAIDAMOUNT'>"+res.varList[i].UNPAIDAMOUNT+"</td>";
					            		html +="<td class='center' id='PAIDAMOUNT'>"+res.varList[i].PAIDAMOUNT+"</td>";
					            		html +="<td class='center' id='THISPAY'>"+res.varList[i].THISPAY+"</td>";
				            		}else if(res.varList[i].BILLTYPE=='8'){
				            			html +="<td class='center' style='color:red'>退货单</td>";
				            			html +="<td class='center'>"+res.varList[i].SUPPLIERNAME+"</td>";
					            		html +="<td class='center'>"+res.varList[i].SUPPLIERNO+"</td>";
					            		html +="<td class='center' id='ALLAMOUNT' >-"+res.varList[i].ALLAMOUNT+"</td>";
					            		html +="<td class='center' id='UNPAIDAMOUNT'>-"+res.varList[i].UNPAIDAMOUNT+"</td>";
					            		html +="<td class='center' id='PAIDAMOUNT'>"+res.varList[i].PAIDAMOUNT+"</td>";
					            		html +="<td class='center' id='THISPAY'>"+res.varList[i].THISPAY+"</td>";
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
				            		html +="<td class='center'>";
				            		if(res.QX.edit != 1 && QX.del != 1){
					            		html +="<span class='label label-large label-grey arrowed-in-right arrowed-in'><i class='ace-icon fa fa-lock' title='无权限'></i></span>";
				            		}
				            		if(res.QX.SUPPSETBILLSET == 1 ){
					            		html += "	<div class='hidden-sm hidden-xs btn-group'> ";
										html += "		<a class='btn btn-xs btn-success' title='结算' id='settleOnInorder' onclick=\"settleone('"+res.varList[i].INORDER_ID+"','"+res.varList[i].BILLTYPE+"');\"> ";
										html += "			<i class='ace-icon fa fa-eye bigger-120' title='结算'></i> ";
										html += "		</a> ";
				            		}
									if(res.QX.del == 1){
										html += "		<a class='btn btn-xs btn-danger' id='delInorder' onclick=\"del('"+res.varList[i].INORDER_ID+"','"+res.varList[i].BILLTYPE+"');\" > ";
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
										html += "					<a style='cursor:pointer;' id='settleOnInorder' onclick=\"settleone('"+res.varList[i].INORDER_ID+"','"+res.varList[i].BILLTYPE+"');\" class='tooltip-success' data-rel='tooltip' title='结算'> ";
										html += "						<span class='green'> ";
										html += "							<i class='ace-icon fa fa-eye bigger-120'></i> ";
										html += "						</span> ";
										html += "					</a> ";
										html += "				</li> ";
									}
									if(res.QX.del == 1){
										html += "				<li> ";
										html += "					<a style='cursor:pointer;' id='delInorder' onclick='del('"+res.varList[i].INORDER_ID+"','"+res.varList[i].BILLTYPE+"');' class='tooltip-error' data-rel='tooltip' title='删除'> ";
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
									allamountmap.set(res.varList[i].INORDER_ID+","+res.varList[i].BILLTYPE,res.varList[i].ALLAMOUNT);  //总金额
				            		unpaidamountmap.set(res.varList[i].INORDER_ID+","+res.varList[i].BILLTYPE,res.varList[i].UNPAIDAMOUNT);  //未付金额
				            		firstmap.set(res.varList[i].INORDER_ID+","+res.varList[i].BILLTYPE,res.varList[i].UNPAIDAMOUNT);  //未付金额
				            		paidamoutmap.set(res.varList[i].INORDER_ID+","+res.varList[i].BILLTYPE,res.varList[i].PAIDAMOUNT);  //已付金额
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
			            	var str=key.split(",");
			            	if(str[1]=='1'){
			            		unpaidam += value;
			            	}else{
			            		unpaidam -= value;
			            	}
			            	
						});
			            $("#PAYABLEAMOUNT").val(unpaidam);
			            $("#realtbody").html("");
			            $("#realtbody").html(strhtml);
			        }
			    }); 
			}
		}
		function changepay(){
			if($("#select_paymethod").val() == '0' && $("#select_suppsetbill").val() == '0'){
            	$("#PAYMENTAMOUNT").tips({
					side : 1,
					msg : "请先选择供应商和付款方式",
					bg : '#FF5080',
					time : 3
				});
            }else  if($("#select_suppsetbill").val() == '0' ){
            	$("#PAYMENTAMOUNT").tips({
					side : 1,
					msg : "请先选择供应商",
					bg : '#FF5080',
					time : 3
				});
            }else  if($("#select_paymethod").val() == '0' ){
            	$("#PAYMENTAMOUNT").val('');
            	$("#PAYMENTAMOUNT").tips({
					side : 1,
					msg : "请先选择付款方式",
					bg : '#FF5080',
					time : 3
				});
            }else{
            	if(parseFloat($("#PAYABLEAMOUNT").val())+supphaspaidamount < parseFloat($("#PAYMENTAMOUNT").val())){
	            	$("#PAYMENTAMOUNT").tips({
						side : 1,
						msg : "付款多了",
						bg : '#FF5080',
						time : 3
					});      
	            	$("#PAYMENTAMOUNT").val(parseFloat($("#PAYABLEAMOUNT").val()));
	            }
	            if(parseFloat($("#PAYMENTAMOUNT").val()) < supphaspaidamount && supphaspaidamount != 0.0){
	            	$("#PAYMENTAMOUNT").tips({
						side : 1,
						msg : "当前付款总额至少为已结算进货单的额度",
						bg : '#FF5080',
						time : 3
					});
	            	$(this).val(supphaspaidamount);
	            }
            }
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
					var which = parseFloat(target.val());
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
		function del(Id,type){
			var ids=Id+","+type;
			if(settledinordermap.get(ids) == null){
				bootbox.confirm("确定要删除吗?", function(result) {
					if(result) {
						delreal(Id,type);
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
		function delreal(Id,type){
			var ids=Id+","+type;
			allamountmap.delete(ids);
			paidamoutmap.delete(ids);
			unpaidamountmap.delete(ids);
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
            	var str=key.split(",");
            	if(str[1]=='1'){
            		unpaidam += value;
            	}else{
            		unpaidam -= value;
            	}
			});
            $("#PAYABLEAMOUNT").val(unpaidam);
		}
		//统计额度
		function compu(){
			var allam = 0.0;
            allamountmap.forEach(function(value, key) {
            	var str=key.split(",");
            	if(str[1]=='1'){
           		 	allam += value;
            	}else{
            	 	allam -= value;
            	}
			});
            var paidam=0.0;
            paidamoutmap.forEach(function(value, key) {
            	var str=key.split(",");
            	if(str[1]=='1'){
            		paidam += value;
            	}else{
            		paidam -= value;	
            	}
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
			supphaspaidamount = 0.0;
			settleList = [];
		}
		//清空除了实付金额的其他数值       修改时调用到
		function cleanvalue(){
            $("#PAYABLEALLAM").val('');
            $("#PAYABLEAMOUNT").val('');
			inorderitemmap.clear();
			allamountmap.clear();
			paidamoutmap.clear();
			unpaidamountmap.clear();
			settledinordermap.clear();
			supphaspaidamount = 0.0;
			settleList = [];
		}
		
		//反审进货单 retrialInorder
		function retrial(Id,type){
			var ids=Id+","+type;
			if(settledinordermap.get(ids) != null){
				bootbox.confirm("确定要反审该进货单吗?", function(result) {
					if(result) {
						$.ajax({
					        method:'POST',
					        url:'inorder/retrialInorder',
					        data:{INORDER_ID:Id},
					        dataType:'json',
					        success: function (res) {
					        	delreal(Id,type);
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
		function settleone(Id,type){
			var ids=Id+","+type;
			if(settledinordermap.get(ids) != 1){
				var unpaid = unpaidamountmap.get(ids);
				var allamount = allamountmap.get(ids);
				var paidamout = paidamoutmap.get(ids);
			 	var thispayamount = parseFloat($("#PAYMENTAMOUNT").val());
				var ablepayamount =parseFloat($("#PAYABLEAMOUNT").val()); 
				if(thispayamount==ablepayamount){
					thispayamount=0;
					allamountmap.forEach(function(value, key) {
						var str=key.split(",");
						var type=str[1];
						if(type=='1'){
							thispayamount+=value;
						}
					});
				}
				if(thispayamount == 0.0){
					$("#PAYMENTAMOUNT").tips({
						side : 1,
						msg : "供应商结算单的实付金额不可为空",
						bg : '#FF5080',
						time : 5
					});
				}
				if(type=='1'){
					var canpaidamount = thispayamount-supphaspaidamount;  //可进行进货单结算的余额=结算单付款金额-已结算进货单总额 
					/* if( thispayamount==ablepayamount){//如果应付金额等于实付金额，
						canpaidamount =unpaid;
					}		 */
					if(canpaidamount == 0.0){
						$("#PAYMENTAMOUNT").tips({
							side : 1,
							msg : "当前实付金额已用于结算了，请增加额度，才可进行接下来的进货单结算",
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
								supphaspaidamount += nextinordercanpaid;
					       		$("#"+Id+" #UNPAIDAMOUNT").html(unpaid-nextinordercanpaid);
					       	    $("#"+Id+" #PAIDAMOUNT").html(paidamout+nextinordercanpaid);
					       		$("#"+Id+" #THISPAY").html(nextinordercanpaid);
					       		$("#"+Id+" #ISSETTLEMENTED").html(2);
					       		settledinordermap.set(ids,nosettle);
					       		paidamoutmap.set(ids,paidamoutmap.get(ids)+nextinordercanpaid);
					       		unpaidamountmap.set(ids,unpaidamountmap.get(ids)-nextinordercanpaid);
					       		compu();
							}
						})
					}
				}else{
					bootbox.confirm("确定要结算该进货单吗?", function(result) {
						if(result) {
			        		$("#"+Id+" #UNPAIDAMOUNT").html(0);
			        	    $("#"+Id+" #PAIDAMOUNT").html("-"+allamount);
			        		$("#"+Id+" #THISPAY").html("-"+allamount);
			        		$("#"+Id+" #ISSETTLEMENTED").html(2);
			        		settledinordermap.set(ids,1);
			        		paidamoutmap.set(ids,allamount);
			        		unpaidamountmap.set(ids,0);
			        		compu();
						}
					})
				
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
			
			var thispayamount = parseFloat($("#PAYMENTAMOUNT").val());
			if(thispayamount == 0.0 || thispayamount == null ){
				$("#PAYMENTAMOUNT").tips({
					side : 1,
					msg : "供应商结算单的实付金额不可为空",
					bg : '#FF5080',
					time : 5
				});
			}
			
			var ablepayamount =parseFloat($("#PAYABLEAMOUNT").val()); 
			if(thispayamount==ablepayamount){
				thispayamount=0;
				allamountmap.forEach(function(value, key) {
					var str=key.split(",");
					var type=str[1];
					if(type=='1'){
						thispayamount+=value;
					}
				});
			}
			
			var canpaidamount = thispayamount-supphaspaidamount;  //可进行进货单结算的余额=结算单付款金额-已结算进货单总额 
			var canback = canpaidamount;
			if(canpaidamount == 0.0){
				$("#PAYMENTAMOUNT").tips({
					side : 1,
					msg : "当前实付金额已用于结算了，请增加额度，才可进行接下来的进货单结算",
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
										  var ids = document.getElementsByName('ids')[i].value;
										  var str=ids.split(",");
										  var id=str[0];
										  var type=str[1];
										  if(type=='1'){
											  var unpaid = unpaidamountmap.get(ids);
											  if(canpaidamount == 0 || canpaidamount == 0.0){
												  break;
											  }
											  if(canpaidamount >= unpaid){//可结算的单
												  canpaidamount -= unpaid;
												  $("#"+id+" #UNPAIDAMOUNT").html(unpaidamountmap.get(ids)-unpaid);
									        	  $("#"+id+" #PAIDAMOUNT").html(paidamoutmap.get(ids)+unpaid);
									        	  $("#"+id+" #THISPAY").html(unpaid);
									        	  $("#"+id+" #ISSETTLEMENTED").html(2);
									        	  paidamoutmap.set(ids,paidamoutmap.get(ids)+unpaid);
									              unpaidamountmap.set(id,unpaidamountmap.get(ids)-unpaid);
									              settledinordermap.set(ids,1);
											  }else{//不可结算
												  $("#"+id+" #UNPAIDAMOUNT").html(unpaidamountmap.get(ids)-canpaidamount);
									        	  $("#"+id+" #PAIDAMOUNT").html(paidamoutmap.get(ids)+canpaidamount);
									        	  $("#"+id+" #THISPAY").html(canpaidamount);
									        	  $("#"+id+" #ISSETTLEMENTED").html(2);
									              settledinordermap.set(ids,0);
									        	  paidamoutmap.set(ids,paidamoutmap.get(ids)+canpaidamount);
									              unpaidamountmap.set(ids,unpaidamountmap.get(ids)-canpaidamount);
												  break;
											  }
										  }else{
											  var amount=allamountmap.get(ids);
											  $("#"+id+" #UNPAIDAMOUNT").html(0);
								        	  $("#"+id+" #PAIDAMOUNT").html("-"+amount);
								        	  $("#"+id+" #THISPAY").html("-"+amount);
								        	  $("#"+id+" #ISSETTLEMENTED").html(2);
								        	  paidamoutmap.set(ids,amount);
								              unpaidamountmap.set(ids,0);
								              settledinordermap.set(ids,1);
										  }
									}
								}
								compu();
								supphaspaidamount += canback;
							
								
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
									  	var ids = document.getElementsByName('ids')[i].value;
									  	var str = ids.split(",");
									  	var id=str[0];
									  	allamountmap.delete(ids);
										paidamoutmap.delete(ids);
										unpaidamountmap.delete(ids);
										inorderitemmap.delete(id);
										
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
				            	var str = key.split(",");
				            	if(str[0]=='1'){
				            		unpaidam += value;
				            	}else{
				            		unpaidam -= value;
				            	}
							});
				            $("#PAYABLEAMOUNT").val(unpaidam);
						}
					}
				}
			});
		};
		
		//新增保存
		function savesuppsetbill(){
			if($("#select_suppsetbill").val() == 0){
				$("#select_suppsetbill_name").tips({
					side : 1,
					msg : "请先选择供应商，然后对其进货单进行结算",
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
					msg : "请先选择供应商，然后对其进货单进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			
			if($("#PAYABLEALLAM").val() == 0){
				$("#PAYABLEALLAM").tips({
					side : 1,
					msg : "应付总金额不可为空，请选择供应商进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#PAYABLEAMOUNT").val() == 0){
				$("#PAYABLEAMOUNT").tips({
					side : 1,
					msg : "应付金额不可为空，请选择供应商进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#PAYMENTAMOUNT").val() == 0){
				$("#PAYMENTAMOUNT").tips({
					side : 1,
					msg : "实付金额不可为空，请选择供应商进行结算",
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
			var hasmoney= $("#PAYMENTAMOUNT").val();
			var settleInOrder_ID = "";
			var flag=1;
			settledinordermap.forEach(function(value, key){
				var str = key.split(",");
				var id=str[0];
				var type=str[1];
				if(type=='1'){
					var unpaid = firstmap.get(key);
					flag=0;
					if(hasmoney <= unpaid){
						if(settleInOrder_ID == ""){
							settleInOrder_ID += "'"+id+"'";
						}else{
							settleInOrder_ID += ",'"+id+"'";
						}
						return;
					}else{
						if(hasmoney>=0){
							if(settleInOrder_ID == ""){
								settleInOrder_ID += "'"+id+"'";
							}else{
								settleInOrder_ID += ",'"+id+"'";
							}
						}
						hasmoney-=unpaid;
					}
				}else{
					if(flag==1){
						settleInOrder_ID += "'"+id+"'";
					}else{
						settleInOrder_ID += ",'"+id+"'";
					}
				}
				
			});
			
			/* select_suppsetbill DISTRIBUTIONMODE INVOICETYPE BILLNO select_paymethod PAYABLEALLAM PAYABLEAMOUNT PAYMENTAMOUNT NOTE unpaidamountmap */
			var FROMUNIT= $("#select_suppsetbill").val();
			var DISTRIBUTIONMODE= $("#DISTRIBUTIONMODE").val();
			var INVOICETYPE= $("#INVOICETYPE").val()==null?"":$("#INVOICETYPE").val();
			var BILLNO= $("#BILLNO").val()==null?"":$("#BILLNO").val();
			var PAYMETHOD= $("#select_paymethod").val();
			var PAYABLEALLAM= $("#PAYABLEALLAM").val();
			var PAYABLEAMOUNT= $("#PAYABLEAMOUNT").val();
			var PAYMENTAMOUNT= $("#PAYMENTAMOUNT").val();//实付
			
			var NOTE= $("#NOTE").val() == null ?"":$("#NOTE").val();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>suppsetbill/save.do?tm='+new Date().getTime(),
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
		function editsuppsetbill(){
			if($("#select_suppsetbill").val() == 0){
				$("#select_suppsetbill_name").tips({
					side : 1,
					msg : "请先选择供应商，然后对其进货单进行结算",
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
					msg : "请先选择供应商，然后对其进货单进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			
			if($("#PAYABLEALLAM").val() == 0){
				$("#PAYABLEALLAM").tips({
					side : 1,
					msg : "应付总金额不可为空，请选择供应商进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#PAYABLEAMOUNT").val() == 0){
				$("#PAYABLEAMOUNT").tips({
					side : 1,
					msg : "应付金额不可为空，请选择供应商进行结算",
					bg : '#FF5080',
					time : 5
				});
				return ;
			}
			if($("#PAYMENTAMOUNT").val() == 0){
				$("#PAYMENTAMOUNT").tips({
					side : 1,
					msg : "实付金额不可为空，请选择供应商进行结算",
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
			var hasmoney= $("#PAYMENTAMOUNT").val();
			var settleInOrder_ID = "";
			var flag=1;
			settledinordermap.forEach(function(value, key){
				var unpaid = firstmap.get(key);
				var str=key.split(",");
				var id=str[0];
				var type=str[1];
				if(type=='1'){
					flag=0;
					hasmoney-=unpaid;
					if(hasmoney>=0){
						if(settleInOrder_ID == ""){
							settleInOrder_ID += "'"+id+"'";
						}else{
							settleInOrder_ID += ",'"+id+"'";
						}
					}
				}else{
					if(flag==1){
						settleInOrder_ID += "'"+id+"'";
					}else{
						settleInOrder_ID += ",'"+id+"'";
					}
				}
			});
			
			/* select_suppsetbill DISTRIBUTIONMODE INVOICETYPE BILLNO select_paymethod PAYABLEALLAM PAYABLEAMOUNT PAYMENTAMOUNT NOTE */
			var FROMUNIT= $("#select_suppsetbill").val();
			var DISTRIBUTIONMODE= $("#DISTRIBUTIONMODE").val();
			var INVOICETYPE= $("#INVOICETYPE").val()==null?"":$("#INVOICETYPE").val();
			var BILLNO= $("#BILLNO").val()==null?"":$("#BILLNO").val();
			var PAYMETHOD= $("#select_paymethod").val();
			var PAYABLEALLAM= $("#PAYABLEALLAM").val();
			var PAYABLEAMOUNT= $("#PAYABLEAMOUNT").val();
			var PAYMENTAMOUNT= $("#PAYMENTAMOUNT").val();
			var NOTE= $("#NOTE").val() == null ?"":$("#NOTE").val();
			var suppsetbill_id = $("#suppsetbill_id").val();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>suppsetbill/edit.do?tm='+new Date().getTime(),
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
		    		SUPPSETBILL_ID:suppsetbill_id
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
			window.location.href='<%=basePath%>suppsetbill/excel.do';
		}
	</script>


</body>
</html>