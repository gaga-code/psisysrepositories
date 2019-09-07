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
					
					<form action="goods/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="GOOD_ID" id="GOOD_ID" value="${pd.GOOD_ID}"/>
						<textarea name="DESCRIPTION" id="DESCRIPTION" style="display:none" >${pd.DESCRIPTION}</textarea>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 商品名称:</td>
								<td><input type="text" name="GOODNAME" id="GOODNAME" value="${pd.GOODNAME}" maxlength="255"  style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 商品条码:</td>
								<!-- <td colspan="10"> --><td><input type="text" name="BARCODE" id="BARCODE" value="${pd.BARCODE}" maxlength="255"  style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 商品分类:</td>
								<td>
									<select class="chosen-select form-control" name="GOODTYPE_ID" id="GOODTYPE_ID"  onchange="selectTpye()" style="vertical-align:top;width:210px;" >
										<option value="">无</option>
										<c:forEach items="${goodsTypeList}" var="var">
											<option value="${var.GOODTYPE_ID }" <c:if test="${var.GOODTYPE_ID == pd.GOODTYPE_ID }">selected</c:if>>${var.TYPENAME }</option>
										</c:forEach>
									</select>
								</td>
							
								<%-- <td><input type="text" name="GOODTYPE_ID" id="GOODTYPE_ID" value="${pd.GOODTYPE_ID}" maxlength="30"   style="width:98%;"/></td> --%>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 商品编号:</td>
								<td><input type="text" onblur="getByBm(this.value);" name="GOODCODE" id="GOODCODE" value="${pd.GOODCODE}" maxlength="30"  style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">简称:</td>
								<td><input type="text" name="SIMPLENAME" id="SIMPLENAME" value="${pd.SIMPLENAME}" maxlength="30"  style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">经手人:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.PSI_NAME}" maxlength="30"   style="width:98%;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品规格:</td>
								<td><input type="text" name="GOODSPECIF" id="GOODSPECIF" value="${pd.GOODSPECIF}" maxlength="30"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属柜组:</td>
								<td><input type="text" name="SUBGZ_ID" id="SUBGZ_ID" value="${pd.SUBGZ_ID}" maxlength="30"   style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 计量单位:</td>
								<td>
									<select class="chosen-select form-control" name="CUNIT_ID" id="CUNIT_ID" data-placeholder="请选择计量单位" oninput="getUNITPROPtips();" style="vertical-align:top;width:210px;" >
										<option value="">无</option>
										<c:forEach items="${spunitList}" var="var">
											<option value="${var.SPUNIT_ID }" <c:if test="${var.SPUNIT_ID == pd.CUNIT_ID }">selected</c:if>>${var.NAME }</option>
										</c:forEach>
									</select>
								</td>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 辅助单位:</td>
								<td>
									<select class="chosen-select form-control" name="FZUNIT_ID" id="FZUNIT_ID" data-placeholder="请选择计量单位" oninput="getUNITPROPtips();" style="vertical-align:top;width:210px;" >
										<option value="">无</option>
										<c:forEach items="${spunitList}" var="var">
											<option value="${var.SPUNIT_ID }" <c:if test="${var.SPUNIT_ID == pd.FZUNIT_ID }">selected</c:if>>${var.NAME }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单位比例:</td>
								<td><input type="number" name="UNITPROP" id="UNITPROP" value="${pd.UNITPROP}" maxlength="30"   style="width:98%;" oninput="getUNITPROPtips();"/></td>
								<td colspan="5"><input type="text" name="UNITPROPtips" id="UNITPROPtips" maxlength="30"  readonly="readonly" style="width:98%; color: red;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品型号:</td>
								<td><input type="text" name="GOODTYPECODE" id="GOODTYPECODE" value="${pd.GOODTYPECODE}" maxlength="30"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">拼音编码:</td>
								<td><input type="text" name="YICODE" id="YICODE" value="${pd.YICODE}" maxlength="30"   style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">主供应商:</td>
								<td>
<%-- 								<input type="text" name="SUPPLIER_ID" id="SUPPLIER_ID" value="${pd.SUPPLIER_ID}" maxlength="255"  style="width:98%;"/> --%>
									<select class="chosen-select form-control" name="SUPPLIER_ID" id="SUPPLIER_ID" data-placeholder="请选择供应商" style="vertical-align:top;width:98%;" >
										<option value="">无</option>
										<c:forEach items="${supplierList}" var="var">
											<option value="${var.SUPPLIER_ID }" <c:if test="${var.SUPPLIER_ID == pd.SUPPLIER_ID }">selected</c:if>>${var.SUPPLIERNAME }</option>
										</c:forEach>
									</select>
								</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="NOTE" id="NOTE" value="${pd.NOTE}" maxlength="30"   style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 最后进价:</td>
								<td><input type="number" name="LASTPPRICE" id="LASTPPRICE" value="${pd.LASTPPRICE}" maxlength="30"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 成本价:</td>
								<td><input type="number" name="CPRICE" id="CPRICE" value="${pd.CPRICE}" maxlength="30"   style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 零售价:</td>
								<td><input type="number" name="RPRICE" id="RPRICE" value="${pd.RPRICE}" maxlength="30"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 最后辅助单位进价:</td>
								<td><input type="number" name="LFZUPPRICE" id="LFZUPPRICE" value="${pd.LFZUPPRICE}" maxlength="30"   style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 辅助单位零售价:</td>
								<td><input type="number" name="FZUCPRICE" id="FZUCPRICE" value="${pd.FZUCPRICE}" maxlength="30"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 会员价:</td>
								<td><input type="number" name="MPRICE" id="MPRICE" value="${pd.MPRICE}" maxlength="30"   style="width:98%;"/></td>
							</tr>
							<c:if test="${'edit' == msg }">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">库存数量:</td>
								<td colspan="10"><input type="number" name="STOCKNUM" id="STOCKNUM" value="${pd.STOCKNUM}" maxlength="255"  style="width:98%;"/></td>
							</tr>
							</c:if>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 库存上限:</td>
								<td><input type="number" name="STOCKUPNUM" id="STOCKUPNUM" value="${pd.STOCKUPNUM}" maxlength="30"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;"><i style="color:red">*&nbsp;</i> 库存下限:</td>
								<td><input type="number" name="STOCKDOWNNUM" id="STOCKDOWNNUM" value="${pd.STOCKDOWNNUM}" maxlength="30"   style="width:98%;"/></td>
								<input type="hidden" id="wh" name="wh" value="${pd.wh}"   />
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 1px;" id="select_stock_name"><i style="color:red">*&nbsp;</i> 仓库:</td>
								<input type="hidden" id="WAREHOUSE_IDs" name="WAREHOUSE_IDs" value="${pd.WAREHOUSE_IDs}"   />
								<td style="vertical-align:top;">
								 	<select class="chosen-select form-control" name="Select_WAREHOUSE_IDs" id="Select_WAREHOUSE_IDs" data-placeholder="请选择仓库" multiple="multiple"  style="vertical-align:top;width: 98%;"  >
									<option value=""></option>
								  	</select>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
</div>
<!-- /.main-container -->

<c:if test="${'edit' == msg }">
	<div>
		<iframe name="treeFrame" id="treeFrame" frameborder="0" src="<%=basePath%>/goodsmx/list.do?MASTER_ID=${pd.GOOD_ID}" style="margin:0 auto;width:805px;height:368px;;"></iframe>
	</div>
</c:if>

<footer>
<div style="width: 100%;padding-bottom: 2px;" class="center">
	<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
	<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
</div>
</footer>

	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 百度富文本编辑框-->
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
	<!-- 百度富文本编辑框-->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
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
		
		function selectTpye(){
			var GOODTYPE_ID=$('#GOODTYPE_ID').val();
			  $.ajax({
			        method:'POST',
			        url:'goods/findCodeByGOODTYPEID',
			        data:{GOODTYPE_ID:GOODTYPE_ID},
			        dataType:'json',
			        success: function (data) {
			          	$("#GOODCODE").val(data);
			        },
			        error: function (jqXHR, textStatus, errorThrown) {
			    	     $("#GOODCODE").val(jqXHR.responseText);
			        }
			    }); 
		}
		
		//单位比例提示
		function getUNITPROPtips(){
			var danwei=$("#CUNIT_ID option:selected");//获取当前选择项.
			danwei.val();//获取当前选择项的值
			var fuzu=$("#FZUNIT_ID option:selected");//获取当前选择项.
			fuzu.val();//获取当前选择项的值
			if(fuzu.val() != '' && $("#UNITPROP").val()!='')
				$("#UNITPROPtips").val($("#UNITPROP").val() +  danwei.text() +' = 1' + fuzu.text());
		}
		//通过商品编码读取数据
		function getByBm(BIANMA){
			if(""==BIANMA)return;
			$.ajax({
				type: "POST",
				url: 'goods/getByBm.do',
		    	data: {BIANMA:BIANMA,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" == data.result){
						$("#GOODCODE").tips({
							side:3,
				            msg:'此编号已存在',
				            bg:'#AE81FF',
				            time:2
				        });
						$("#GOODCODE").focus();
					}

				}
			});
		}
		//保存
		function save(){
			if($("#GOODNAME").val()==""){
				$("#GOODNAME").tips({
					side:3,
		            msg:'请输入商品名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GOODNAME").focus();
			return false;
			}
			if($("#BARCODE").val()==""){
				$("#BARCODE").tips({
					side:3,
		            msg:'请输入条码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BARCODE").focus();
			return false;
			}
			if($("#GOODTYPE_ID").val()==""){
				$("#GOODTYPE_ID").tips({
					side:3,
		            msg:'请输入商品分类编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GOODTYPE_ID").focus();
			return false;
			}
			if($("#GOODCODE").val()==""){
				$("#GOODCODE").tips({
					side:3,
		            msg:'请输入商品编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GOODCODE").focus();
			return false;
			}
			if($("#CUNIT_ID").val()==""){
				$("#CUNIT_ID").tips({
					side:3,
		            msg:'请选择单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CUNIT_ID").focus();
			return false;
			}
			if($("#FZUNIT_ID").val()==""){
				$("#FZUNIT_ID").tips({
					side:3,
		            msg:'请选择辅助单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FZUNIT_ID").focus();
			return false;
			}
// 			if($("#SUPPLIER_ID").val()==""){
// 				$("#SUPPLIER_ID").tips({
// 					side:3,
// 		            msg:'请选择供应商',
// 		            bg:'#AE81FF',
// 		            time:2
// 		        });
// 				$("#SUPPLIER_ID").focus();
// 			return false;
// 			}
			if($("#LASTPPRICE").val()==""){
				$("#LASTPPRICE").tips({
					side:3,
		            msg:'请输入最后进价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LASTPPRICE").focus();
			return false;
			}
			if($("#CPRICE").val()==""){
				$("#CPRICE").tips({
					side:3,
		            msg:'请输入成本价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CPRICE").focus();
			return false;
			}
			if($("#RPRICE").val()==""){
				$("#RPRICE").tips({
					side:3,
		            msg:'请输入零售价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#RPRICE").focus();
			return false;
			}
			if($("#LFZUPPRICE").val()==""){
				$("#LFZUPPRICE").tips({
					side:3,
		            msg:'请输入最后辅助单位进价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LFZUPPRICE").focus();
			return false;
			}
			if($("#FZUCPRICE").val()==""){
				$("#FZUCPRICE").tips({
					side:3,
		            msg:'请输入辅助单位零售价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FZUCPRICE").focus();
			return false;
			}
			if($("#MPRICE").val()==""){
				$("#MPRICE").tips({
					side:3,
		            msg:'请输入会员价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#MPRICE").focus();
			return false;
			}
			if($("#STOCKNUM").val()==""){
				$("#STOCKNUM").tips({
					side:3,
		            msg:'请输入库存数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STOCKNUM").focus();
			return false;
			}
			if($("#STOCKUPNUM").val()==""){
				$("#STOCKUPNUM").tips({
					side:3,
		            msg:'请输入库存上限',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STOCKUPNUM").focus();
			return false;
			}
			if($("#STOCKDOWNNUM").val()==""){
				$("#STOCKDOWNNUM").tips({
					side:3,
		            msg:'请输入库存下限',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STOCKDOWNNUM").focus();
			return false;
			}
			if($("#Select_WAREHOUSE_IDs").val()==""){
				$("#Select_WAREHOUSE_IDs").tips({
					side:3,
		            msg:'请选择仓库',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#Select_WAREHOUSE_IDs").focus();
			return false;
			}
			var array = $("#Select_WAREHOUSE_IDs").val();			
			var str="";
			for(var i = 0; i <  array.length; i++){
				if(i != array.length-1){
					str += array[i]+",";
				}else{
					str += array[i];
				}
			}
			$("#WAREHOUSE_IDs").val(str);
			$("#wh").val(str);
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
		//百度富文本
		setTimeout("ueditor()",500);
		function ueditor(){
			UE.getEditor('editor');
		}
		
		//ueditor有标签文本
		function getContent() {
		    var arr = [];
		    arr.push(UE.getEditor('editor').getContent());
		    return arr.join("");
		}
		
		</script>
</body>
</html>