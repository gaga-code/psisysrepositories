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
					
					<form action="stockcheck/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PK_SOBOOKS" id="PK_SOBOOKS" value="${pd.PK_SOBOOKS}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						
							<tr>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<td style="text-align: center;" colspan="10"><font size="6">盘点详情</font></td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<td style="text-align: center;" colspan="10">
<!-- 									<a class="btn btn-mini btn-primary" onclick="save();">保存</a> -->
									<a class="btn btn-mini btn-danger" onclick="returnList();">返回列表</a>
								</td>
							</tr>
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单据编号:</td>
								<td><input type="text" name="BILLCODE" id="BILLCODE" value="${pd.BILLCODE}" maxlength="1000" placeholder="这里输入单据编号"   style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">录入日期:</td>
								<td><input type="text" name="LDATE" id="LDATE" value="${pd.LDATE}" maxlength="1000"  style="width:98%;" readonly="readonly"/></td>
<!-- 								<td style="width:75px;text-align: right;padding-top: 13px;">单据状态:</td> -->
<%-- 								<td><input type="text" name="BILLSTATUS" id="BILLSTATUS" value="${pd.BILLSTATUSNAME}" maxlength="1000" placeholder="单据状态"   style="width:98%;" readonly="readonly"/></td> --%>
								<td style="width:75px;text-align: right;padding-top: 13px;">经手人:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.PSI_NAME}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库:</td>
								<td><input type="text" name="WAREHOUSE_ID" id="WAREHOUSE_ID" value="${pd.WHNAME}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">批号:</td>
								<td><input type="text" name="NOTE" id="NOTE" value="${pd.LOTNUM}" maxlength="1000"    style="width:98%;" readonly="readonly"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="NOTE" id="NOTE" value="${pd.NOTE}" maxlength="1000"    style="width:98%;" readonly="readonly"/></td>
							</tr>
							<input id = "goodslist" name ="goodslist" type="hidden"/>
						</table>
						<table name="goodstable" id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center">仓库</th>
									<th class="center">商品名称</th>
									<th class="center">商品编号</th>
									<th class="center">单位</th>
									<th class="center">型号</th>
									<th class="center">规格</th>
									<th class="center">盘点数量</th>
									<th class="center">盈亏数量</th>
									<th class="center">原库存数量</th>
									<th class="center">备注</th>
<!-- 									<th class="center">操作</th> -->
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty pd.goodslist}">
<%-- 									<c:if test="${QX.cha == 1 }"> --%>
									<c:forEach items="${pd.goodslist}" var="var" varStatus="vs">
										<tr>
<%-- 											<td class='center' style="width: 30px;">${vs.index+1}</td> --%>
											<td class='center'>${var.WAREHOUSE_NAME}</td>
											<td class='center'>${var.GOODNAME}</td>
											<td class='center'>${var.GOODCODE_ID}</td>
											<td class='center'>${var.NAME}</td>
											<td class='center'>${var.GOODTYPECODE}</td>
											<td class='center'>${var.GOODSPECIF}</td>
											<td class='center'>${var.CHENKNUM}</td>
											<td class='center'>${var.LOSSNUM}</td>
											<td class='center'>${var.CHENKNUM - var.LOSSNUM}</td>
											<td class='center'>${var.NOTE}</td>
										</tr>
									
									</c:forEach>
<%-- 									</c:if> --%>
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
<!-- 						<a class="btn btn-mini btn-primary" onclick="addgoods();">添加商品</a> -->
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
// 	    $(function() {
// 	        //初始化第一行
// 	        firstCell = $("#row0 td:eq(0)").html();
// 	        secondCell = $("#row0 td:eq(1)").html();
// 	        thirdCell = $("#row0 td:eq(2)").html();
// 	        fourthCell = $("#row0 td:eq(3)").html();
// 	    });
	
		function insertNewRow(GOOD_ID,GOODNAME,BARCODE,UNITNAME,ISFREE) {
			 //获取表格有多少行
	        var rowLength = $("#simple-table tr").length;
	        //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
	        var rowId = "row" + flag;
	      //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
	        if(ISFREE == '1'){
	        	var insertStr = "<tr id=" + rowId + ">"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+GOODNAME+"'/></td>"
	                      + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+BARCODE+"'/></td>"
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount();' value='"+UNITPRICE_ID+"'/></td>"
	                      + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount();'value='"+PNUMBER+"'/></td>"
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
		                + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount();' value='"+UNITPRICE_ID+"'/></td>"
		                + "<td class='center'><input type='number' maxlength='100' style='width:100px' onchange='calculateTheTotalAmount();'value='"+PNUMBER+"'/></td>"
		                + "<td class='center'><input type='text' maxlength='100' style='width:100px' readonly='readonly' value='"+UNITNAME+"'/></td>"
		                + "<td class='center'><input type='number' maxlength='100' style='width:100px' readonly='readonly' value='"+AMOUNT+"'/></td>"
		                + "<td class='center'><input type='checkbox' id='checkbox"+ flag +"' readonly='readonly' value='0' onclick='exe(\"checkbox"+ flag +"\");' /></td>"
		                + "<td class='center'><input type='text' maxlength='100' style='width:100px' value='"+NOTE+"'/></td>"
		                + "<td style='display:none'><input type='hidden' value='"+GOODCODE+"'/></td>"
		                + "<td class='center'><div class='hidden-sm hidden-xs btn-group'><a class='btn btn-xs btn-danger' onclick='deleteSelectedRow(\"" + rowId + "\")'><i class='ace-icon fa fa-trash-o bigger-120'></i></a></div></td>"
		                +"</tr>";
	      	}
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
	                var danjia = value[2];
	                var shuliang = value[3];
	                if(danjia!= ''&& shuliang!= ''){
	                	result = result + danjia * shuliang;
	                	$("#simple-table tr").eq(i+1).children().eq(5).children().eq(0).val(danjia * shuliang);
	                	console.log(i);
	                }
	            }
	        }
	       // console.log(value);
	        $("#ALLAMOUNT").val(result);
	        
	    }
	  
	  
		function addgoods(){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="查看商品信息";
			diag.URL = '<%=basePath%>stockcheck/goaddgoods.do';
			diag.Width = 1000;
			diag.Height = 800;
			diag.Modal = true;				//有无遮罩窗口
			diag.CancelEvent = function(){ //关闭事件
				var storage=window.localStorage;
			    var GOOD_ID=localStorage.getItem("GOOD_ID");
			    var GOODNAME=localStorage.getItem("GOODNAME");
			    var BARCODE=localStorage.getItem("BARCODE");
			    var UNITNAME=localStorage.getItem("UNITNAME");
			    window.localStorage.removeItem("GOOD_ID");
			    window.localStorage.removeItem("GOODNAME");
			    window.localStorage.removeItem("BARCODE");
			    window.localStorage.removeItem("UNITNAME");
			    if( GOOD_ID != null)
			    	insertNewRow(GOOD_ID,GOODNAME,BARCODE,UNITNAME);
				diag.close();
			};
			diag.show();
		}
		
		//保存
		function save(){
			if($("#ENTERPRISENAME").val()==""){
				$("#ENTERPRISENAME").tips({
					side:3,
		            msg:'请输入企业名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ENTERPRISENAME").focus();
			return false;
			}
			/* if($("#MONEY").val()==""){
				$("#MONEY").val(0);
			} */
			GetValue();
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
		}
		function returnList(){
			var url = 'stockcheck/list.do';
			document.forms.actionForm.action=url;
	        document.forms.actionForm.submit();
		}

		
		</script>
</body>
</html>