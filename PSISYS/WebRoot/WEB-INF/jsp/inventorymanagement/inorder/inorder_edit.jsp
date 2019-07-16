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
					
					<form action="inorder/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PK_SOBOOKS" id="PK_SOBOOKS" value="${pd.PK_SOBOOKS}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否结算:</td>
								<td><input type="text" name="ISSETTLEMENTED" id="ISSETTLEMENTED" value="${pd.ISSETTLEMENTED}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
							
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单据编号:</td>
								<td><input type="text" name="BILLCODE" id="BILLCODE" value="${pd.BILLCODE}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">录入日期:</td>
								<td><input type="text" name="LDATE" id="LDATE" value="${pd.LDATE}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">供应商:</td>
								<td>
									<select class="chosen-select form-control" name="SUPPLIER_ID" id="SUPPLIER_ID" data-placeholder="请选择供应商" style="vertical-align:top;width:98%;" >
										<option value="">无</option>
										<c:forEach items="${supplierList}" var="var">
											<option value="${var.SUPPLIER_ID }" <c:if test="${var.SUPPLIER_ID == pd.SUPPLIER_ID }">selected</c:if>>${var.SUPPLIERNAME }</option>
										</c:forEach>
									</select>
								</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">经手人:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.PSI_NAME}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">仓库:</td>
								<td><input type="text" name="WAREHOUSE_ID" id="WAREHOUSE_ID" value="${pd.WAREHOUSE_ID}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">总金额:</td>
								<td><input type="number" name="ALLAMOUNT" id="ALLAMOUNT" value="${pd.ALLAMOUNT}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">单据类型:</td>
								<td><input type="text" name="BILLTYPE" id="BILLTYPE" value="${pd.BILLTYPE}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">单据状态:</td>
								<td><input type="text" name="BILLSTATUS" id="BILLSTATUS" value="未审批" maxlength="1000" placeholder="这里输入备注"   style="width:98%;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">已付金额:</td>
								<td><input type="text" name="PAIDAMOUNT" id="PAIDAMOUNT" value="${pd.PAIDAMOUNT}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">未付金额:</td>
								<td><input type="number" name="UNPAIDAMOUNT" id="UNPAIDAMOUNT" value="${pd.UNPAIDAMOUNT}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">本次付款:</td>
								<td><input type="text" name="THISPAY" id="THISPAY" value="${pd.THISPAY}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="NOTE" id="NOTE" value="${pd.NOTE}" maxlength="1000" placeholder="这里输入备注"   style="width:98%;"/></td>
							</tr>
						</table>
						<table name="zdytable" id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">商品名称</th>
									<th class="center">商品编号</th>
									<th class="center">商品规格</th>
									<th class="center">计量单位</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>
												${var.GOODNAME}
												<a onclick="erweima('${var.GOOD_ID}');"><img style="cursor:pointer;" width="15" src="static/images/erwei.png"  title="商品二维码"/></a>
												<a onclick="barcode('${var.GOOD_ID}','${var.BARCODE}');"><img style="cursor:pointer;" width="15" src="static/images/barcode.png"  title="商品条形码"/></a>
											</td>
											<td class='center'>${var.BARCODE}</td>
											<td class='center'>${var.GOODSPECIF}</td>
											<td class='center'>${var.UNITNAME}</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<a class="btn btn-xs btn-info" title="查看商品信息" onclick="view('${var.GOOD_ID}');">
														<i class="ace-icon fa fa-eye bigger-120" title="查看商品信息"></i>
													</a>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<li>
																<a style="cursor:pointer;" onclick="view('${var.GOOD_ID}');" class="tooltip-info" data-rel="tooltip" title="查看商品信息">
																	<span class="blue">
																		<i class="ace-icon fa fa-eye bigger-120"></i>
																	</span>
																</a>
															</li>
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
						<a class="btn btn-mini btn-primary" onclick="addgoods();">添加商品</a>
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


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>

	<script src="static/js/jquery-1.7.2.js" type="text/javascript"></script> 
	<script src="static/js/jquery.cookie.js" type="text/javascript"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
		function addgoods(){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="查看商品信息";
			diag.URL = '<%=basePath%>inorder/goaddgoods.do';
			diag.Width = 1000;
			diag.Height = 800;
			diag.Modal = true;				//有无遮罩窗口
			//diag. ShowMaxButton = true;	//最大化按钮
		    //diag.ShowMinButton = true;		//最小化按钮
		    diag.OKEvent = function(){
		    	alert("OK");
	    		var a =  diag.innerFrame.contentWindow.document.getElementById('a');
	    		$("#b").value = a.val();
		    	diag.close();
		    };
				/*
				var GoodsSelected = $.cookie('GoodsSelected'); // 若cookie存在则返回'cookieValue'；若cookie不存在则返回null
		    	alert(GoodsSelected); */
			diag.CancelEvent = function(){ //关闭事件
		    	 /* var x = document.cookie;
				alert(x); */
				var storage=window.localStorage;
			     //第一种方法读取
			    var goods=localStorage.getItem("GoodsSelected");
			    alert(goods);
			    $("#varList").val(goods);
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
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		
		</script>
</body>
</html>