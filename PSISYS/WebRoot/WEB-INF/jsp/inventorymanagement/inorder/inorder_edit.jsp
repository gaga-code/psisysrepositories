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
					
					<form action="inorder/edit.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PK_SOBOOKS" id="PK_SOBOOKS" value="${pd.PK_SOBOOKS}"/>
						<input type="hidden" name="INORDER_ID" id="INORDER_ID" value="${pd.INORDER_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<tr>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<td style="text-align: center;" colspan="10"><font size="6">修改进货单</font></td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<td style="text-align: center;" colspan="10">
								<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="returnList();">取消</a>
								</td>
							</tr>
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单据编号:</td>
								<td><input type="text" name="BILLCODE" id="BILLCODE" value="${pd.BILLCODE}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">录入日期:</td>
								<td><input type="text" name="LDATE" id="LDATE" value="${pd.LDATE}" maxlength="1000"  style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">单据状态:</td>
								<td><input type="text" name="BILLSTATUS" id="BILLSTATUS" value="${pd.BILLSTATUSNAME}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">经手人:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.PSI_NAME}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">总金额:</td>
								<td><input type="number" name="ALLAMOUNT" id="ALLAMOUNT" value="${pd.ALLAMOUNT }" maxlength="1000" placeholder="选择商品后自动计算"   style="width:98%;" readonly="readonly"/></td>
								<td style='display:none' style="width:75px;text-align: right;padding-top: 13px;">仓库:</td>
								<td style='display:none'>
									<select class="chosen-select form-control" name="WAREHOUSE_ID" id="WAREHOUSE_ID"  style="vertical-align:top;width:98%;" >
										<option value="">无</option>
										<c:forEach items="${warehouseList}" var="var">
											<option value="${var.WAREHOUSE_ID }" <c:if test="${var.WAREHOUSE_ID == pd.WAREHOUSE_ID }">selected</c:if>>${var.WHNAME }</option>
										</c:forEach>
									</select>
								</td>
<%-- 								<td><input type="text" name="WAREHOUSE_ID" id="WAREHOUSE_ID" value="${pd.WAREHOUSE_ID}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td> --%>
								<td style="width:75px;text-align: right;padding-top: 13px;">供应商:</td>
								<td>
									<select class="chosen-select form-control" name="SUPPLIER_ID" id="SUPPLIER_ID" data-placeholder="请选择供应商" style="vertical-align:top;width:98%;" >
										<option value="">无</option>
										<c:forEach items="${supplierList}" var="var">
											<option value="${var.SUPPLIER_ID }" <c:if test="${var.SUPPLIER_ID == pd.SUPPLIER_ID }">selected</c:if>>${var.SUPPLIERNAME }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<input id = "goodslist" name ="goodslist" type="hidden"/>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">已付金额:</td>
								<td><input type="number" name="PAIDAMOUNT" id="PAIDAMOUNT" value="${pd.PAIDAMOUNT}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">未付金额:</td>
								<td><input type="number" name="UNPAIDAMOUNT" id="UNPAIDAMOUNT" value="${pd.UNPAIDAMOUNT}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
<!-- 								<td style="width:75px;text-align: right;padding-top: 13px;">本次付款:</td> -->
<%-- 								<td><input type="text" name="THISPAY" id="THISPAY" value="${pd.THISPAY}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td> --%>
								
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="NOTE" id="NOTE" value="${pd.NOTE}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
							</tr> 
						</table>
						<table name="goodstable" id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">商品名称</th>
									<th class="center">商品编号</th>
									<th class="center">仓库</th>
									<th class="center">单价</th>
									<th class="center">数量</th>
									<th class="center">计量单位</th>
									<th class="center">金额</th>
									<th class="center">备注</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody id ="tbody">
							<tr></tr>
							</tbody>
						</table>
						<a id="addgoodstishi" class="btn btn-mini btn-primary" onclick="addgoods();">添加商品</a>
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

	<script src="https://libs.baidu.com/jquery/1.4.2/jquery.min.js"></script>
	<script src="static/js/jquery-1.7.2.js" type="text/javascript"></script> 
	<script src="static/js/jquery.cookie.js" type="text/javascript"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
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
	    var stockgoodsnummap = new Map();

	    function parseStr(GOOD_ID,str){
			 var Str = str.split('#');
		        if (Str[0] != "") {
		            for (var i = 0; i < Str.length - 1; i++) {
		                var value = Str[i].split(',');
		                var wh_id = value[0];
		                var wh_name = value[1]
		                var stock = value[2];
		                stockgoodsnummap.set(GOOD_ID+","+wh_id,stock);
		            }
		        }
		}
		
			    
		function insertNewRow(GOOD_ID,GOODNAME,BARCODE,UNITNAME,GOODCODE,CPRICE,WAREHOUSE_ID_NAME_STOCK) {

			var Str = WAREHOUSE_ID_NAME_STOCK.split('#');
			var selecthtml = "";
	        if (Str[0] != "") {
	        	for (var i = 0; i < Str.length - 1; i++) {
	        		 var value = Str[i].split(',');
		                var wh_id = value[0];
		                var wh_name = value[1]
		                var stock = value[2];
		                selecthtml += "<option value="+wh_id+">"+wh_name+","+stock+"</option>";
	        	}
	        }
			
			//获取表格有多少行
	        var rowLength = $("#simple-table tr").length;
	        //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
	        var rowId = "row" + flag;
	      //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
	        var insertStr = "<tr id=" + rowId + ">"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+GOODNAME+"'/></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+BARCODE+"'/></td>"
	                      +'<td class="center"><select class="chosen-select form-control" style="vertical-align:top;width:98%;" >'
						  +	selecthtml
						  +'</select></td>'
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount();' value='"+CPRICE+"'/></td>"
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount();'/></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+UNITNAME+"'/></td>"
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' readonly='readonly'/></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' /></td>"
	                      + "<td style='display:none'><input type='hidden' value='"+GOODCODE+"'/></td>"
	                      + "<td class='center'><div class='hidden-sm hidden-xs btn-group'><a class='btn btn-xs btn-danger' onclick='deleteSelectedRow(\"" + rowId + "\")'><i class='ace-icon fa fa-trash-o bigger-120'></i></a></div></td>"
// 	                      + "<td><input  type='button' name='delete' value='删除' style='width:80px' onclick='deleteSelectedRow(\"" + rowId + "\")' />";
	                      +"</tr>";
	        //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
	                      $("#simple-table tr:eq(" + (rowLength - 2) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
	         //为新添加的行里面的控件添加新的id属性。
	         $("#" + rowId + " td:eq(0)").children().eq(0).attr("id", "UrbanDepNo" + flag);
	         $("#" + rowId + " td:eq(1)").children().eq(0).attr("id", "LocNo" + flag);
	         $("#" + rowId + " td:eq(2)").children().eq(0).attr("id", "RoadSectionName" + flag);
	         $("#" + rowId + " td:eq(3)").children().eq(0).attr("id", "StStartRoad" + flag);
	         $("#" + rowId + " td:eq(3)").children().eq(1).attr("id", "EndRoad" + flag);
	         //每插入一行，flag自增一次
	         flag++;
			
		}
		
		//插入已存在的商品数据（初始化时调用）
		function insertOldRow(GOOD_ID,GOODNAME,BARCODE,UNITNAME,UNITPRICE_ID,PNUMBER,AMOUNT,NOTE,GOODCODE,WAREHOUSE_ID,WAREHOUSE_ID_NAME_STOCK) {
		
			var Str = WAREHOUSE_ID_NAME_STOCK.split('#');
			var selecthtml = "";
	        if (Str[0] != "") {
	        	for (var i = 0; i < Str.length - 1; i++) {
	        		 var value = Str[i].split(',');
		                var wh_id = value[0];
		                var wh_name = value[1]
		                var stock = value[2];
		                var selected = '';
		                if(WAREHOUSE_ID == wh_id)
		                	selected = 'selected'
		                selecthtml += "<option "+selected+" value="+wh_id+">"+wh_name+","+stock+"</option>";
	        	}
	        }
		
			 //获取表格有多少行
	        var rowLength = $("#simple-table tr").length;
	        //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
	        var rowId = "row" + flag;
	      //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
	        var insertStr = "<tr id=" + rowId + ">"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+GOODNAME+"'/></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+BARCODE+"'/></td>"
	                      +'<td class="center"><select class="chosen-select form-control" style="vertical-align:top;width:98%;" >'
						  +	selecthtml
						  +'</select></td>'
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount();' value='"+UNITPRICE_ID+"'/></td>"
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount();'value='"+PNUMBER+"'/></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+UNITNAME+"'/></td>"
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' readonly='readonly' value='"+AMOUNT+"'/></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' value='"+NOTE+"'/></td>"
	                      + "<td style='display:none'><input type='hidden' value='"+GOODCODE+"'/></td>"
	                      + "<td class='center'><div class='hidden-sm hidden-xs btn-group'><a class='btn btn-xs btn-danger' onclick='deleteSelectedRow(\"" + rowId + "\")'><i class='ace-icon fa fa-trash-o bigger-120'></i></a></div></td>"
//	                      + "<td><input  type='button' name='delete' value='删除' style='width:80px' onclick='deleteSelectedRow(\"" + rowId + "\")' />";
	                      +"</tr>";
	        //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
	                      $("#simple-table tr:eq(" + (rowLength - 2) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
	         //为新添加的行里面的控件添加新的id属性。
// 	         $("#" + rowId + " td:eq(0)").children().eq(0).attr("id", "UrbanDepNo" + flag);
// 	         $("#" + rowId + " td:eq(1)").children().eq(0).attr("id", "LocNo" + flag);
// 	         $("#" + rowId + " td:eq(2)").children().eq(0).attr("id", "RoadSectionName" + flag);
// 	         $("#" + rowId + " td:eq(3)").children().eq(0).attr("id", "StStartRoad" + flag);
// 	         $("#" + rowId + " td:eq(3)").children().eq(1).attr("id", "EndRoad" + flag);
	         //每插入一行，flag自增一次
	         flag++;
			
		}
		
		//-----------------删除一行，根据行ID删除-start--------    
	    function deleteSelectedRow(rowID) {
	        if (confirm("确定删除该行吗？")) {
	            $("#" + rowID).remove();
	        }
	    }
		
	  //-----------------获取表单中的值----start--------------
	  
	    function GetValue() {
// 	    	var list=new Array();//创建list集合
// 	    	var myMap=new Map(); //创建map集合
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
	    	$("#goodslist").val(value);
	    	
	    	console.log(value);
	    	//calculateTheTotalAmount(value);
// 	        value = value.substr(0, value.length);
// 	        ReceiveValue(value);
//	        $("#formvalue").val(value);
//	        $("formvalue").submit();
	    }
		
	  //-------------------接收表单中的值-----------------------------
	    function ReceiveValue() {
	        var Str = str.split('#');
	        if (Str[0] != "") {
	            for (var i = 0; i < Str.length - 1; i++) {
	                var value = Str[i].split(',');
	                var dept = value[0];
	                var street = value[1]
	                var section = value[2];
	                var Broad = value[3];
	                var Eroad = value[4];
	                //insertTable(dept, street, section, Broad, Eroad);
	                $("input[type='text']").val("");
	                $("select[name='UrbanDepNo']").val("0");
	            }
	        }
	    }
	  //计算总金额
	    function calculateTheTotalAmount() {
		  
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
	                var danjia = value[3];
	                var shuliang = value[4];
	                if(danjia!= ''&& shuliang!= ''){
	                	result = result + danjia * shuliang;
	                	$("#simple-table tr").eq(i+1).children().eq(6).children().eq(0).val(danjia * shuliang);
	                	console.log(i);
	                }
	            }
	        }
	       // console.log(value);
	        $("#ALLAMOUNT").val(result);
	        $("#UNPAIDAMOUNT").val($("#ALLAMOUNT").val() - $("#PAIDAMOUNT").val());
	    }
	  
	  
		function addgoods(){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="查看商品信息";
			diag.URL = '<%=basePath%>inorder/goaddgoods.do';
			diag.Width = 1000;
			diag.Height = 800;
			diag.Modal = true;				//有无遮罩窗口
			diag.CancelEvent = function(){ //关闭事件
				var storage=window.localStorage;
			    var GOOD_ID=localStorage.getItem("GOOD_ID");
			    var GOODNAME=localStorage.getItem("GOODNAME");
			    var BARCODE=localStorage.getItem("BARCODE");
			    var UNITNAME=localStorage.getItem("UNITNAME");
			    var GOODCODE=localStorage.getItem("GOODCODE");
			    var CPRICE=localStorage.getItem("CPRICE");
			    var WAREHOUSE_ID_NAME_STOCK=localStorage.getItem("WAREHOUSE_ID_NAME_STOCK");
			    window.localStorage.removeItem("GOOD_ID");
			    window.localStorage.removeItem("GOODNAME");
			    window.localStorage.removeItem("BARCODE");
			    window.localStorage.removeItem("UNITNAME");
			    window.localStorage.removeItem("GOODCODE");
			    window.localStorage.removeItem("CPRICE");
			    window.localStorage.removeItem("WAREHOUSE_ID_NAME_STOCK");
			    if( GOOD_ID != null)
			    	insertNewRow(GOOD_ID,GOODNAME,BARCODE,UNITNAME,GOODCODE,CPRICE,WAREHOUSE_ID_NAME_STOCK);
				diag.close();
			};
			diag.show();
		}
		
		 //判断商品单价和数量是否为空
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
		                var danjia = value[3];
		                var shuliang = value[4];
		                if(danjia == '' || shuliang == ''){
		                	alert("商品的单价和数量不能为空！");
		                	return '0';
		                }
		            }
		        }
		  }
		//保存
		function save(){
			if($("#SUPPLIER_ID").val()==""){
				$("#SUPPLIER_ID").tips({
					side:3,
		            msg:'请选择供应商',
		            bg:'#AE81FF',
		            time:2
		        });
			return false;
			}
			/* if($("#MONEY").val()==""){
				$("#MONEY").val(0);
			} */
			GetValue();
			var check = checkGoodsPriceAndNum();
			if(check == '0'){
				return false;
			}
			GetValue();
			if($("#goodslist").val()=="#"){
				$("#addgoodstishi").tips({
					side:3,
		            msg:'请选择商品',
		            bg:'#AE81FF',
		            time:2
		        });
			return false;
			}
			if(parseFloat($("#ALLAMOUNT").val()) < parseFloat($("#PAIDAMOUNT").val())){
				$("#ALLAMOUNT").tips({
					side:3,
		            msg:'总金额不能少于已付金额',
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
			var url = 'inorder/list.do';
			document.forms.actionForm.action=url;
	        document.forms.actionForm.submit();
		}

		$(function(){
// 			insertOldRow('2','999999','1','1','9.0','9','81.0','9');
			<c:forEach items="${pd.goodslist}" var="t">
				insertOldRow('${t.GOOD_ID}','${t.GOODNAME}','${t.BARCODE}','${t.NAME}','${t.UNITPRICE_ID}','${t.PNUMBER}','${t.AMOUNT}','${t.NOTE}','${t.GOODCODE_ID}','${t.WAREHOUSE_ID}','${t.WAREHOUSE_ID_NAME_STOCK}');
			</c:forEach>
		});
		</script>
</body>
</html>