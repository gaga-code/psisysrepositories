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
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
	
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
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
					
					<form action="expense/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PK_SOBOOKS" id="PK_SOBOOKS" value="${pd.PK_SOBOOKS}"/>
						<input type="hidden" name="flag" id="flag" value="${msg}"/>
						<input type="hidden" name="EXPENSE_ID" id="EXPENSE_ID" value="${pd.EXPENSE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;
							<c:if test="${QX.add == 1 && msg == 'save' }">
							<td style="text-align: center;" colspan="10"><font size="6">添加费用开支单</font></td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</c:if>
							<c:if test="${QX.edit == 1 && msg == 'edit' }">
							<td style="text-align: center;" colspan="10"><font size="6">修改费用开支单</font></td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</c:if>
							<tr>
								<td style="text-align: center;" colspan="10">
									<c:if test="${QX.add == 1 && msg == 'save' }">
									<a class="btn btn-mini btn-success" onclick="save();">保存费用开支单</a>
									</c:if>
									<c:if test="${QX.edit == 1 && msg == 'edit' }">
									<a class="btn btn-mini btn-success" onclick="save();">保存费用开支单</a>
									</c:if>
									<a class="btn btn-mini btn-danger" onclick="returnList();">取消</a>
								</td>
							</tr>
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<c:if test="${msg == 'edit' }">
								<td style="width:75px;text-align: right;padding-top: 13px;">单据编号:</td> 
 								<td><input type="text" name="BILLCODE" id="BILLCODE" value="${pd.BILLCODE}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td> 
								<td style="width:75px;text-align: right;padding-top: 13px;">录入日期:</td> 
 								<td><input type="text" name="LDATE" id="LDATE" value="${pd.LDATE}" maxlength="1000"  style="width:98%;" readonly="readonly"/></td>
 								</c:if>
								<td style="width:75px;text-align: right;padding-top: 13px;" id="Select_PAYMETHOD_ID" >付款方式:</td>
								<td style="vertical-align:top;" >
									<select class="chosen-select form-control" name="PAYMETHOD_ID" id="PAYMETHOD_ID"  style="vertical-align:top;width:98%;" >
										<option value="0">无</option>
										<c:forEach items="${paymethodList}" var="var">
											<option value="${var.PAYMETHOD_ID }" <c:if test="${var.PAYMETHOD_ID == pd.PAYMETHOD_ID }">selected</c:if>>${var.PAYMETHOD_NAME }</option>
										</c:forEach>
									</select>
								</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">付款来源:</td>
								<td><input type="text" name="PAYORIGIN" id="PAYORIGIN"  maxlength="1000"  style="width:98%;" value="${pd.PAYORIGIN }" /></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">票号:</td>
								<td><input type="text" name="TICKETNUM" id="TICKETNUM"  maxlength="1000"  style="width:98%;" value="${pd.TICKETNUM }" /></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">付款总金额:</td>
								<td><input type="number" name="PAYAMOUNT" id="PAYAMOUNT" value="${pd.PAYAMOUNT}" maxlength="1000" placeholder="选择费用开支明细后自动计算"   style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">经手人:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.PSI_NAME}" maxlength="1000"    style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="NOTE" id="NOTE" value="${pd.NOTE}" maxlength="1000"   style="width:98%;"/></td>
							</tr>
							<input id = "expensebodylist" name ="expensebodylist" type="hidden" value="${pd.expensebodylist }" />
							<%-- <input id = "ioctypeList" name ="ioctypeList" type="hidden" value="${ioctypeList }" /> --%>
						</table>
						<table name="expensetable" id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
<!-- 									<th class="center">费用编号</th> -->
									<th class="center">费用名称</th>
									<th class="center">金额</th>
									<th class="center">备注</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody id ="tbody">
							
							<tr></tr>
							</tbody>
						</table>
						<c:if test="${QX.add == 1 && msg == 'save' }">
						<a id="addgoods" class="btn btn-mini btn-primary" onclick="newRow();">添加费用开支明细</a>
						</c:if>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
					<form action="#" method="post" name="actionForm" id="actionForm"></form>
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




<!-- <script src="common/jquery-1.4.2.js" type="text/javascript"></script> -->
<!--    <script src="common/jquery-1.7.2.js" type="text/javascript"></script> -->

	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	
<!-- 	<script src="https://libs.baidu.com/jquery/1.4.2/jquery.min.js"></script> -->
<!-- 	<script src="static/js/jquery-1.7.2.js" type="text/javascript"></script>  -->
	<script src="static/js/jquery.cookie.js" type="text/javascript"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
		//声明全局变量
	    var formvalue = "";
	    var flag = 1;
	    var index=1;
	    var firstCell = "";
	    var secondCell = "";
	    var thirdCell = "";
	    var fourthCell = "";
	    
	    var allamount= new Map();;
	    var flagae = "";//修改还是新增的标识 
		var editamount = 0.0;
		
		function newRow(){
			 //获取表格有多少行
	        var rowLength = $("#simple-table tr").length;
	        //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
	        var rowId = "row" + flag;
	      //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
	        var insertStr = "<tr id='" + rowId + "'>"
	       				  + "<td class='center' style='width: 30px;'>"+flag+"</td>" 
	                      + "<td class='center' style='vertical-align:top;' ><select class='chosen-select form-control' name='INOUTCOMETYPE_ID' id='INOUTCOMETYPE_ID'  style='vertical-align:top;width:98%;' >"
	                      + "<option value='0'>无</option> "
	                      + "<c:forEach items='${ioctypeList}' var='var'>"
	                      + "<option value='${var.INOUTCOMETYPE_ID }' <c:if test='${var.INOUTCOMETYPE_ID == pd.INOUTCOMETYPE_ID }'>selected</c:if>>${var.TYPENAME }</option>"
	                      + "</c:forEach></select></td>"
	        			  + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount(\""+rowId+"\");' id='AMOUNT' value='${var.AMOUNT}'/></td>"
	        			  + "<td class='center'><input type='text' maxlength='100' style='width:100px'  value='${var.NOTE}'/></td>"
	        			  + "<td class='center'><div class='hidden-sm hidden-xs btn-group'><a class='btn btn-xs btn-danger' onclick='deleteSelectedRow(\"" + rowId + "\")'><i class='ace-icon fa fa-trash-o bigger-120'></i></a></div></td>"
	        			  +"</tr>";
	        //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
	                      $("#simple-table tr:eq(" + (rowLength - 2) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
	         //为新添加的行里面的控件添加新的id属性。
	         //每插入一行，flag自增一次
	         flag++;
		}
		function newRowForEdit(selectstr,INOUTCOMETYPE_ID,AMOUNT,NOTE,EXPENSEBODY_ID){
			 //获取表格有多少行
	        var rowLength = $("#simple-table tr").length;
	        //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
	        var rowId = "row" + flag;
	      //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
	        var insertStr = "<tr id='" + rowId + "'>"
	        				+ "<td style='display:none'><input type='hidden' value='"+EXPENSEBODY_ID+"'/></td>"
					        + "<td class='center' style='width: 30px;'>"+flag+"</td>" 
				            + "<td class='center' style='vertical-align:top;' ><select class='chosen-select form-control' name='INOUTCOMETYPE_ID' id='INOUTCOMETYPE_ID'  style='vertical-align:top;width:98%;' >"
				            + selectstr + "</select></td>"
							  + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount(\""+rowId+"\");' id='AMOUNT' value='"+AMOUNT+"'/></td>"
							  + "<td class='center'><input type='text' maxlength='100' style='width:100px'  value='"+NOTE+"'/></td>"
							  + "<td class='center'><div class='hidden-sm hidden-xs btn-group'><a class='btn btn-xs btn-danger' onclick='deleteSelectedRow(\"" + rowId + "\")'><i class='ace-icon fa fa-trash-o bigger-120'></i></a></div></td>"
							  +"</tr>";
				//这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
				            $("#simple-table tr:eq(" + (rowLength - 2) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
				//为新添加的行里面的控件添加新的id属性。
				//每插入一行，flag自增一次
				flag++;
				allamount.set(rowId,parseFloat(AMOUNT));
		}
		
		//-----------------删除一行，根据行ID删除-start--------    
	    function deleteSelectedRow(rowID) {
	        bootbox.confirm("确定要费用开支明细吗?", function(result) {
				if(result) {
					allamount.delete(rowID);
		            $("#" + rowID).remove();
		            compu();
				}
	        });	
	    }
		
		
	  //追加金额
	    function calculateTheTotalAmount(rowId) {
	    	if($("#INOUTCOMETYPE_ID").val() == '0' ){
            	$("#INOUTCOMETYPE_ID").tips({
					side : 1,
					msg : "请先选择费用方式",
					bg : '#FF5080',
					time : 3
				});
            	$("#"+rowId+" #AMOUNT").val(0.0)
            	return;
            }
		  	allamount.set(rowId,parseFloat($("#"+rowId+" #AMOUNT").val()));
		  	compu();
	    }
	    
	   function compu(){
		   var aa=0.0;
		   allamount.forEach(function(value, key) {
           	aa += value;
			});
		   $("#PAYAMOUNT").val(aa);
	   }
	 //-----------------获取表单中的值----start--------------
		  
	    function GetValue() {
			var value = "";
	    	$("#simple-table tr").each(function(i) {
	            if (i >= 1) {
	                $(this).children().each(function(j) {
	                    if ($("#simple-table tr").eq(i).children().length - 1 != j) {
	                        value += $(this).children().eq(0).val() + "," //获取每个单元格里的第一个控件的值
	                        if ($(this).children().length > 1) {
	                            value += $(this).children().eq(1).val() + "," //如果单元格里有两个控件，获取第二个控件的值
	                        }
	                    }
	                });
	                value = value.substr(0, value.length - 1) + "#"; //每个单元格的数据以“，”分割，每行数据以“#”号分割
	            }
	        });
	    	value=value.substr(0,value.length-1);
	    	$("#expensebodylist").val(value);
	 }
		//费用名称或金额是否为空
		  function checkGoodsPriceAndNum(){
			  var value = "";
		    	$("#simple-table tr").each(function(i) {
		            if (i >= 1) {
		                $(this).children().each(function(j) {
		                    if ($("#simple-table tr").eq(i).children().length - 1 != j) {
		                        value += $(this).children().eq(0).val() + "," //获取每个单元格里的第一个控件的值
		                        if ($(this).children().length > 1) {
		                            value += $(this).children().eq(1).val() + "," //如果单元格里有两个控件，获取第二个控件的值
		                        }
		                    }
		                });
		                value = value.substr(0, value.length - 1) + "#"; //每个单元格的数据以“，”分割，每行数据以“#”号分割
		            }
		        });
			  	var str = value;
		        var Str = str.split('#');
		        var result = 0;
		        if (Str[0] != "") {
		            for (var i = 0; i < Str.length - 1; i++) {
		                var value = Str[i].split(',');
		                var danjia = value[1];
		                var shuliang = value[2];
		                if(danjia == '' || shuliang == ''){
		                	alert("费用名称或金额不能为空！");
		                	return '0';
		                }
		            }
		        }
		  }
		
		//保存
		function save(){
			if($("#PAYMETHOD_ID").val() == '0' ){
            	$("#Select_PAYMETHOD_ID").tips({
					side : 1,
					msg : "请先选择付款方式",
					bg : '#FF5080',
					time : 3
				});
            	return;
            }
			if($("#PAYAMOUNT").val() == 0 ){
				$("#PAYAMOUNT").tips({
					side : 1,
					msg : "请先添加费用开支明细",
					bg : '#FF5080',
					time : 3
				});
            	return;
			}
			var check = checkGoodsPriceAndNum();
			if(check == '0'){
				return false;
			}
			GetValue();
			//console.log($("#expensebodylist").val());
			if($("#expensebodylist").val()=="#"){
				$("#tbody").tips({
					side:3,
		            msg:'请添加费用明细',
		            bg:'#AE81FF',
		            time:2
		        });
			return false;
			}
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
		}
		function returnList(){
			var url = 'expense/list.do';
			document.forms.actionForm.action=url;
	        document.forms.actionForm.submit();
		}

		$(function() {
			flagae=$('#flag').val();
			if(flagae == 'edit'){
				editamount=$("#PAYAMOUNT").val();			
				/* ioctypeList=${ioctypeList}; */
				<c:forEach items="${pd.expensebodylist}" var="t">
					var selectstr = "";
					<c:forEach items="${ioctypeList}" var="tt">
						<c:if test="${tt.INOUTCOMETYPE_ID == t.INOUTCOMETYPE_ID}">
						selectstr+="<option value='${tt.INOUTCOMETYPE_ID}' selected='selected' >${tt.TYPENAME}</option>";
						</c:if>
						<c:if test="${tt.INOUTCOMETYPE_ID != t.INOUTCOMETYPE_ID}">
						selectstr+="<option value='${tt.INOUTCOMETYPE_ID}'  >${tt.TYPENAME}</option>";
						</c:if>
					</c:forEach> 
					newRowForEdit(selectstr,'${t.INOUTCOMETYPE_ID}','${t.AMOUNT}','${t.NOTE}','${t.EXPENSEBODY_ID}');
				</c:forEach>
			}
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