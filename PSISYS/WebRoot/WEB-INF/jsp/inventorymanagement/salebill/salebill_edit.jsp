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
	<!-- 自定義CSS文件 -->
	<link rel="stylesheet" href="static/myCSS/style.css"/>
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
					
					<form action="salebill/edit.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PK_SOBOOKS" id="PK_SOBOOKS" value="${pd.PK_SOBOOKS}"/>
						<input type="hidden" name="SALEBILL_ID" id="SALEBILL_ID" value="${pd.SALEBILL_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<tr>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<td style="text-align: center;" colspan="10"><font size="6">修改销售单</font></td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<td style="text-align: center;" colspan="10">
								<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
								<a class="btn btn-mini btn-primary" onclick="shenpi();">审批</a>
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
								<td style="width:75px;text-align: right;padding-top: 13px;">经手人:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.PSI_NAME}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">总金额:</td>
								<td><input type="number" name="ALLAMOUNT" id="ALLAMOUNT" value="${pd.ALLAMOUNT }" maxlength="1000" placeholder="选择商品后自动计算"   style="width:98%;" readonly="readonly"/></td>
								<td style='display:none' style="width:75px;text-align: right;padding-top: 13px;">仓库:</td>
								<td style='display:none' id="tishi">
									<select class="chosen-select form-control" name="WAREHOUSE_ID" id="WAREHOUSE_ID"  style="vertical-align:top;width:98%;" >
										<option value="">无</option>
										<c:forEach items="${warehouseList}" var="var">
											<option value="${var.WAREHOUSE_ID }" <c:if test="${var.WAREHOUSE_ID == pd.WAREHOUSE_ID }">selected</c:if>>${var.WHNAME }</option>
										</c:forEach>
									</select>
								</td>
<%-- 								<td><input type="text" name="WAREHOUSE_ID" id="WAREHOUSE_ID" value="${pd.WAREHOUSE_ID}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td> --%>
								<td style="width:75px;text-align: right;padding-top: 13px;">客户:</td>
								<td id="CUSTOMER_select">
									<select class="chosen-select form-control" name="CUSTOMER_ID" id="CUSTOMER_ID" style="vertical-align:top;width:98%;" >
										<option value="">无</option>
										<c:forEach items="${customerList}" var="var">
											<option value="${var.CUSTOMER_ID }" <c:if test="${var.CUSTOMER_ID == pd.CUSTOMER_ID }">selected</c:if>>${var.CUATOMERNAME }</option>
										</c:forEach>
									</select>
								</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">结款日期:</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="PAYDATE" id="PAYDATE"  value="${pd.PAYDATE}" type="text" data-date-format="yyyy-mm-dd"  style="width:98%;" /></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">客户订单号:</td>
								<td><input type="text" name="CUSBILLNO" id="CUSBILLNO"  maxlength="1000"  style="width:98%;" value="${pd.CUSBILLNO}"/></td>
							</tr>
							<input id = "goodslist" name ="goodslist" type="hidden"/>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">已付金额:</td>
								<td><input type="number" name="PAIDAMOUNT" id="PAIDAMOUNT" value="${pd.PAIDAMOUNT}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">未付金额:</td>
								<td><input type="number" name="UNPAIDAMOUNT" id="UNPAIDAMOUNT" value="${pd.UNPAIDAMOUNT}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<!-- colspan="10" -->
								<td ><input  type="text" name="NOTE" id="NOTE" value="${pd.NOTE}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;padding-left: 0px;padding-right: 0px;">送货地址:</td>
								<td><input type="text" name="TOADDRESS" id="TOADDRESS"  maxlength="1000" style="width:98%;" value="${pd.TOADDRESS}" /></td>
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
									<th class="center">赠送</th>
									<th class="center">备注</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody id ="tbody">
							<tr></tr>
							</tbody>
						</table>
						<a id="addgoods" class="btn btn-mini btn-primary" onclick="addgoods();">添加商品</a>
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

<!-- 	<script src="https://libs.baidu.com/jquery/1.4.2/jquery.min.js"></script> -->
<!-- 	<script src="static/js/jquery-1.7.2.js" type="text/javascript"></script>  -->
	<script src="static/js/jquery.cookie.js" type="text/javascript"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
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
	    var shuliangmap = new Map();
// 	    $(function() {
// 	        //初始化第一行
// 	        firstCell = $("#row0 td:eq(0)").html();
// 	        secondCell = $("#row0 td:eq(1)").html();
// 	        thirdCell = $("#row0 td:eq(2)").html();
// 	        fourthCell = $("#row0 td:eq(3)").html();
// 	    });

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
	
		function insertNewRow(GOOD_ID,GOODNAME,BARCODE,UNITNAME,GOODCODE,RPRICE,WAREHOUSE_ID_NAME_STOCK) {
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
	                      +'<td class="center"><select class="chosen-select form-control" onchange="changewh(\''+flag+'\',\''+rowId+'\');" style="vertical-align:top;width:98%;" id="select_wh" >'
						  +	selecthtml
						  +'</select></td>'
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount();' value='"+RPRICE+"'/></td>"
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' id='goodsnum"+ flag +"' onchange='checkstocknum(\"goodsnum"+ flag +"\",\""+GOODCODE+"\");'/></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+UNITNAME+"'/></td>"
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' readonly='readonly'/></td>"
	                      + "<td class='center'><input type='checkbox' id='checkbox"+ flag +"' value='0' onclick='exe(\"checkbox"+ flag +"\");' /></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' /></td>"
	                      + "<td style='display:none'><input type='hidden' value='"+GOODCODE+"'/></td>"
	                      + "<td class='center'><div class='hidden-sm hidden-xs btn-group'><a class='btn btn-xs btn-danger' onclick='deleteSelectedRow(\"" + rowId + "\")'><i class='ace-icon fa fa-trash-o bigger-120'></i></a></div></td>"
// 	                      + "<td><input  type='button' name='delete' value='删除' style='width:80px' onclick='deleteSelectedRow(\"" + rowId + "\")' />";
	                      +"</tr>";
	        //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
	                      $("#simple-table tr:eq(" + (rowLength - 2) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
	         //为新添加的行里面的控件添加新的id属性。
	         //每插入一行，flag自增一次
	         flag++;
		}
		function changewh(flag,rowId){
			$("#"+rowId+" #goodsnum"+flag).val(0);
			calculateTheTotalAmount();
		}
		//商品数量修改时，判断是否超过了库存量
		function checkstocknum(goodsnumID, GOOD_ID){
			
			var WAREHOUSE_ID = $("#WAREHOUSE_ID").val();
			console.log("test");
			$.ajax({
				type: "POST",
				url: '<%=basePath%>salebill/checkstock.do?tm='+new Date().getTime(),
		    	data: {"GOOD_ID":GOOD_ID, "WAREHOUSE_ID":WAREHOUSE_ID},
				dataType:'json',
				cache: false,
				success: function(data){
					if(data.msg == "success"){//存在商品
						if(data.stockNum >= $("#"+goodsnumID).val()){
							return ;
						}else{
							$("#"+goodsnumID).tips({
								side:3,
					            msg:'仓库中商品数量不足，当前库存为' + data.stockNum,
					            bg:'#AE81FF',
					            time:5
					        });
							$("#"+goodsnumID).focus();
						}
					}else {
						return false;
					}
				}
			});
			calculateTheTotalAmount()
		}
		
		//-----------------点击赠送复选框--------    
		function exe(checkboxId){
			var vals = $("#"+ checkboxId).val();
			if(vals=='0'){
				$("#"+ checkboxId).val('1');
	        }
	        if(vals=='1'){
			 	$("#"+ checkboxId).val('0');
	        }
	        calculateTheTotalAmount();
		}
		
		//插入已存在的商品数据（初始化时调用）
		function insertOldRow(GOOD_ID,GOODNAME,BARCODE,UNITNAME,UNITPRICE_ID,PNUMBER,AMOUNT,NOTE,GOODCODE,ISFREE,WAREHOUSE_ID,WAREHOUSE_ID_NAME_STOCK) {
			
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
	      	if(ISFREE == '1'){
	        	var insertStr = "<tr id=" + rowId + ">"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+GOODNAME+"'/></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+BARCODE+"'/></td>"
	                      +'<td class="center"><select class="chosen-select form-control" onchange="changewh(\''+flag+'\',\''+rowId+'\');" style="vertical-align:top;width:98%;" >'
						  +	selecthtml
						  +'</select></td>'
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount();' value='"+UNITPRICE_ID+"'/></td>"
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' id='goodsnum"+ flag +"' onchange='checkstocknum(\"goodsnum"+ flag +"\",\""+GOODCODE+"\");' value='"+PNUMBER+"'/></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+UNITNAME+"'/></td>"
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' readonly='readonly' value='"+AMOUNT+"'/></td>"
	                      + "<td class='center'><input type='checkbox' id='checkbox"+ flag +"' value='1' checked='checked' onclick='exe(\"checkbox"+ flag +"\");' /></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' value='"+NOTE+"'/></td>"
	                      + "<td style='display:none'><input type='hidden' value='"+GOODCODE+"'/></td>"
	                      + "<td class='center'><div class='hidden-sm hidden-xs btn-group'><a class='btn btn-xs btn-danger' onclick='deleteSelectedRow(\"" + rowId + "\")'><i class='ace-icon fa fa-trash-o bigger-120'></i></a></div></td>"
//	                      + "<td><input  type='button' name='delete' value='删除' style='width:80px' onclick='deleteSelectedRow(\"" + rowId + "\")' />";
	                      +"</tr>";
	      	}else{
	      		var insertStr = "<tr id=" + rowId + ">"
		                + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+GOODNAME+"'/></td>"
		                + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+BARCODE+"'/></td>"
		                +'<td class="center"><select class="chosen-select form-control" onchange="changewh(\''+flag+'\',\''+rowId+'\');" style="vertical-align:top;width:98%;" >'
						+ selecthtml
						+'</select></td>'
		                + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount();' value='"+UNITPRICE_ID+"'/></td>"
		                + "<td class='center'><input type='number' maxlength='100' style='width:100px'id='goodsnum"+ flag +"' onchange='checkstocknum(\"goodsnum"+ flag +"\",\""+GOODCODE+"\");' value='"+PNUMBER+"'/></td>"
		                + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+UNITNAME+"'/></td>"
		                + "<td class='center'><input type='number' maxlength='100' style='width:100px' readonly='readonly' value='"+AMOUNT+"'/></td>"
		                + "<td class='center'><input type='checkbox' id='checkbox"+ flag +"' value='0' onclick='exe(\"checkbox"+ flag +"\");' /></td>"
		                + "<td class='center'><input type='text' maxlength='100' style='width:100px' value='"+NOTE+"'/></td>"
		                + "<td style='display:none'><input type='hidden' value='"+GOODCODE+"'/></td>"
		                + "<td class='center'><div class='hidden-sm hidden-xs btn-group'><a class='btn btn-xs btn-danger' onclick='deleteSelectedRow(\"" + rowId + "\")'><i class='ace-icon fa fa-trash-o bigger-120'></i></a></div></td>"
		                +"</tr>";
	      	}
	        //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
	                      $("#simple-table tr:eq(" + (rowLength - 2) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
	         //为新添加的行里面的控件添加新的id属性。
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
		  
	    	/* var wh = $("#WAREHOUSE_ID").val();
			if(wh ==""){
				$("#tishi").tips({
					side:3,
		            msg:'请选择仓库',
		            bg:'#AE81FF',
		            time:2
		        });
				//$("#WAREHOUSE_ID").focus();
				return false;
			} */
		  
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
	                var free = value[7];
	                var goodcode = value[9];
	                var wh_id = value[2];
	                if(danjia!= ''&& shuliang!= ''){
	                	if(free == '0'){
		                	result = result + danjia * shuliang;
	                	}
	                	$("#simple-table tr").eq(i+1).children().eq(6).children().eq(0).val(danjia * shuliang);
	                }
	                shuliangmap.set(goodcode+","+wh_id,shuliang);
	            }
	        }
	       // console.log(value);
	        $("#ALLAMOUNT").val(result);
	        $("#UNPAIDAMOUNT").val($("#ALLAMOUNT").val() - $("#PAIDAMOUNT").val());
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
	  
		function addgoods(){
			//如果没有选择仓库，不能选择商品
			var wh = $("#WAREHOUSE_ID").val();
			if(wh ==""){
				$("#tishi").tips({
					side:3,
		            msg:'请先选择仓库',
		            bg:'#AE81FF',
		            time:2
		        });
				//$("#WAREHOUSE_ID").focus();
				return false;
			}
			
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="查看商品信息";
			diag.URL = '<%=basePath%>salebill/goaddgoods.do?WAREHOUSE_ID=' + wh;
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
			    var RPRICE=localStorage.getItem("RPRICE");
			    var WAREHOUSE_ID_NAME_STOCK=localStorage.getItem("WAREHOUSE_ID_NAME_STOCK");
			    window.localStorage.removeItem("GOOD_ID");
			    window.localStorage.removeItem("GOODNAME");
			    window.localStorage.removeItem("BARCODE");
			    window.localStorage.removeItem("UNITNAME");
			    window.localStorage.removeItem("GOODCODE");
			    window.localStorage.removeItem("RPRICE");
			    window.localStorage.removeItem("WAREHOUSE_ID_NAME_STOCK");
			    if( GOOD_ID != null){
			    	parseStr(GOODCODE,WAREHOUSE_ID_NAME_STOCK);			    	
			    	insertNewRow(GOOD_ID,GOODNAME,BARCODE,UNITNAME,GOODCODE,RPRICE,WAREHOUSE_ID_NAME_STOCK);
			    }
				diag.close();
			};
			diag.show();
		}
		
		//保存
		function save(){
			console.log("11");
			
			if($("#CUSTOMER_ID").val()==""){
				$("#CUSTOMER_select").tips({
					side:3,
		            msg:'请选择客户',
		            bg:'#AE81FF',
		            time:2
		        });
			return false;
			}
			if($("#PAYDATE").val()==""){
				$('#PAYDATE').val(formatDate(new Date()));
			}
			/* if($("#MONEY").val()==""){
				$("#MONEY").val(0);
			} */
			var check = checkGoodsPriceAndNum();
			if(check == '0'){
				return false;
			}
			GetValue();
			if($("#goodslist").val()=="#"){
				$("#addgoods").tips({
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
			//stockgoodsnummap shuliangmap
			shuliangmap.forEach(function(value, key) {
            	if(stockgoodsnummap.get(key) < value){
            		$("#save").tips({
    					side:3,
    		            msg:'出售商品数量不可大于库存数量',
    		            bg:'#AE81FF',
    		            time:5
    		        });
    			return false;
            	}
			});
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
		}
		function returnList(){
			var url = 'salebill/list.do';
			document.forms.actionForm.action=url;
	        document.forms.actionForm.submit();
		}

		var formatDate = function (date) {  
		    var y = date.getFullYear();  
		    var m = date.getMonth() + 1;  
		    m = m < 10 ? '0' + m : m;  
		    var d = date.getDate();  
		    d = d < 10 ? ('0' + d) : d;  
		    return y + '-' + m + '-' + d;  
		};  
		
		$(function(){
// 			insertOldRow('2','999999','1','1','9.0','9','81.0','9');
		console.log("1");
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			console.log(formatDate(new Date()))
			$('.date-picker').datepicker('setStartDate', formatDate(new Date()));
			$('#PAYDATE').val(formatDate(new Date()));
			<c:forEach items="${pd.goodslist}" var="t">
				insertOldRow('${t.GOOD_ID}','${t.GOODNAME}','${t.BARCODE}','${t.NAME}','${t.UNITPRICE_ID}','${t.PNUMBER}','${t.AMOUNT}','${t.NOTE}','${t.GOODCODE_ID}','${t.ISFREE}','${t.WAREHOUSE_ID}','${t.WAREHOUSE_ID_NAME_STOCK}');
			</c:forEach>
		});
		//审批  GOODNAME,GOODCODE,STOCKNUM,STOCKDOWNNUM	
		function shenpi(){
			 var Id = $("#SALEBILL_ID").val();
			 top.jzts();
			 $.ajax({
					type: "POST",
					url: '<%=basePath%>salebill/shenpi.do?tm='+new Date().getTime(),
			    	data: {SALEBILL_ID:Id},
					dataType:'json',
					//beforeSend: validateData,
					cache: false,
					success: function(data){
						//tosearch();
						top.hangge();
						alert($('#goodslist').val())
						if(data.goodslist != null){
							if($("#alertBox").is(":hidden")){
								$('#alertBox').show();
							}else{
								var htmlStr = "<div id='alertBox'><div class='boxTop'><span>库存预警</span><span id='boxClose'> X </span></div><ul id=\"alertGoodsList\">";
								
								for( var i = 0; i < data.goodslist.length; i++ ){
									htmlStr += "<li><span>"+data.goodslist[i].GOODCODE+"</span><span>"+data.goodslist[i].GOODNAME+"</span><span>"+data.goodslist[i].STOCKNUM+"</span></li>"
								}
								htmlStr += "</ul></div>";
								$(".page-content").append(htmlStr);
	
							}
						
							//预警弹窗消失
							var closeObj = document.getElementById('boxClose');
							closeObj.onclick=function(){
								$('#alertBox').hide();
							};
						} 
					}
				});
		}		
		</script>
</body>
</html>