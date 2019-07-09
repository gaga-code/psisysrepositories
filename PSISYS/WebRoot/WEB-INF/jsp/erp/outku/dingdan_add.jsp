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
					
					<form action="outku/saveOne.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ORDER_NUMBERS" id="ORDER_NUMBERS" value="${pd.ORDER_NUMBERS}"/>
						<input type="hidden" name="INTOKU_ID" id="INTOKU_ID" value="${pd.INTOKU_ID}"/>
						<input type="hidden" name="GOODS_ID" id="GOODS_ID" value="${pd.GOODS_ID}"/>
						<input type="hidden" name="CUSTOMER_ID" id="CUSTOMER_ID" value="${pd.CUSTOMER_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品编码:</td>
								<td><input onblur="getByBm(this.value);" type="text" name="BIANMA" id="BIANMA" value="" maxlength="32" placeholder="这里输入商品编码" title="商品商品编码" style="width:99%;" /></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">手动选择:</td>
								<td id="xzsp">
								<select onchange="setGoogsName();" class="chosen-select form-control" name="SGOODS_ID" id="SGOODS_ID" data-placeholder="请选择商品(录入商品编码就不用选择了)" style="vertical-align:top;width:100px;" >
										<option value=""></option>
										<c:forEach items="${goodsList}" var="var">
											<option value="${var.GOODS_ID }" <c:if test="${var.GOODS_ID == pd.GOODS_ID }">selected</c:if>>${var.TITLE }&nbsp;(${var.ZCOUNT }${var.NAME })</option>
										</c:forEach>
								</select>
								</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品名称:</td>
								<td><input type="text" name="GOODS_NAME" id="GOODS_NAME" value="${pd.GOODS_NAME}" maxlength="32" placeholder="这里不需要手动输入" title="商品名称" style="width:99%;" disabled="disabled"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">数量:</td>
								<td><input onblur="jisuanz();" type="number" name="INCOUNT" id="INCOUNT" value="${pd.INCOUNT}" maxlength="32" placeholder="这里输入销售数量" title="数量" style="width:99%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">单价:</td>
								<td><input onblur="jisuanz();" type="number" name="PRICE" id="PRICE" value="${pd.PRICE}" maxlength="32" placeholder="这里输入单价（销售价）" title="单价" style="width:89%;"/>&nbsp;元</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">总价:</td>
								<td><input type="number" name="ZPRICE" id="ZPRICE" value="${pd.ZPRICE}" maxlength="32" placeholder="总价" title="总价" style="width:89%;"/>&nbsp;元</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td  colspan="3" >
								<input type="text" name="BZ" id="BZ" value="${pd.BZ}" maxlength="200" placeholder="备注" title="备注" style="width:98%;"/>
								</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">客户:</td>
								<td>
									<input type="text" name="CUSTOMER_NAME" id="CUSTOMER_NAME" value="${pd.CUSTOMER_NAME}" maxlength="32" placeholder="这里输入客户" title="客户" readonly="readonly" style="width:69%;"/>
									<a class="btn btn-xs btn-info" title="选择客户" onclick="choiceCus();" style="margin-top: -3px;">
										<i class="ace-icon glyphicon glyphicon-user"></i>
									</a>	
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();" id="save">保存并录入下一个 </a>
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
	
	<div>
		<iframe name="treeFrame" id="treeFrame" frameborder="0" src="<%=basePath%>/outku/listTemp.do?ORDER_NUMBERS=${pd.ORDER_NUMBERS}"" style="margin:0 auto;width:100%;height:430px;"></iframe>
	</div>
	<div class="center">
		<a class="btn btn-mini btn-success" onclick="saveDingdan('${pd.ORDER_NUMBERS}');">提交订单</a>
	</div>
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
		<c:if test="${pd.MSG != '' && pd.MSG != null}">
			<script type="text/javascript">
			$(success());
			//保存单个商品订单信息提示下
			function success(){
				$("#save").tips({
					side : 3,
					msg : "已保存",
					bg : '#9ACD32',
					time : 3
				});
			}
			</script>
		</c:if>
		<script type="text/javascript">
		$(top.hangge());
		
		//提交订单
		function saveDingdan(ORDER_NUMBERS){
			$.ajax({
				type: "POST",
				url: 'outku/transfer.do',
		    	data: {ORDER_NUMBERS:ORDER_NUMBERS,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" == data.result){
						$("#Form").submit();
						$("#zhongxin").hide();
						top.Dialog.close();
					}
				}
			});
		}
		
		//通过商品编码读取数据
		function getByBm(BIANMA){
			if('' == BIANMA){return;}
			$.ajax({
				type: "POST",
				url: 'goods/getByBm.do',
		    	data: {BIANMA:BIANMA,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" == data.result){
						$("#GOODS_ID").val(data.pd.GOODS_ID);
						$("#GOODS_NAME").val(data.pd.TITLE);
					}else{
						$("#BIANMA").tips({
							side : 3,
							msg : "此编码不存在",
							bg : '#FF5080',
							time : 15
						});
					}
				}
			});
		}
		
		//设置商品ID和名称
		function setGoogsName(){
			var selectVale = $("#SGOODS_ID").val();
			var selectText = $("#SGOODS_ID").find("option:selected").text();
			$("#GOODS_ID").val(selectVale);
			$("#GOODS_NAME").val(selectText);
		}
		
		//计算总价
		function jisuanz(){
			var INCOUNT = Number("" == $("#INCOUNT").val()?"0":$("#INCOUNT").val());
			var PRICE = Number("" == $("#PRICE").val()?"0":$("#PRICE").val());
			if(0-INCOUNT>0){
				$("#INCOUNT").tips({
					side:3,
		            msg:'数量不能小于零',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#INCOUNT").focus();
				return false;
			}
			if(0-PRICE>0){
				$("#PRICE").tips({
					side:3,
		            msg:'单价不能小于零',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRICE").focus();
				return false;
			}
			$("#ZPRICE").val(INCOUNT*PRICE);
		}
		
		//保存
		function save(){
			if($("#GOODS_ID").val()==""){
				$("#xzsp").tips({
					side:3,
		            msg:'请选择商品',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GOODS_ID").focus();
			return false;
			}
			if($("#INCOUNT").val()==""){
				$("#INCOUNT").tips({
					side:3,
		            msg:'请输入数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#INCOUNT").focus();
			return false;
			}
			if($("#PRICE").val()==""){
				$("#PRICE").tips({
					side:3,
		            msg:'请输入单价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRICE").focus();
			return false;
			}
			if($("#ZPRICE").val()==""){
				$("#ZPRICE").tips({
					side:3,
		            msg:'请输入总价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ZPRICE").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			$("#ZPRICE").attr("readonly","readonly");
			$("#ZPRICE").css("color","gray");
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
		
		//打开选择客户窗口
		function choiceCus(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="选择客户";
			 diag.URL = '<%=basePath%>customer/windowsList.do';
			 diag.Width = 700;
			 diag.Height = 545;
			 diag.Modal = false;			//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 var CUSTOMER_ID = diag.innerFrame.contentWindow.document.getElementById('CUSTOMER_ID').value;
				 if("" != CUSTOMER_ID){
					var NAME = diag.innerFrame.contentWindow.document.getElementById('NAME').value;
					$("#CUSTOMER_ID").val(CUSTOMER_ID); //客户ID
					$("#CUSTOMER_NAME").val(NAME);		//客户姓名
				}
				diag.close();
			 };
			 diag.show();
		}
		</script>
</body>
</html>