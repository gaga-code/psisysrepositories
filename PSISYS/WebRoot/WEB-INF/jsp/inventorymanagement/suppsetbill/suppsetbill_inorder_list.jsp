<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="suppsetbill/list.do" method="post" name="ListForm"
		id="ListForm">
		<table id="simple-table"
			class="table table-striped table-bordered table-hover"
			style="margin-top: 5px;">
			<thead>
				<tr>
					<th class="center" style="width: 35px;"><label class="pos-rel"><input
							type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
					</th>
					<th class="center" style="width: 50px;">序号</th>
					<th class="center">单据编号</th>
					<th class="center">供应商</th>
					<th class="center">总金额</th>
					<th class="center">未付金额</th>
					<th class="center">已付金额</th>
					<th class="center">本次付款</th>
					<th class="center">是否结算</th>
					<th class="center">经手人</th>
					<th class="center">备注</th>
					<th class="center" style="width: 110px;">操作</th>
				</tr>
			</thead>

			<tbody>
				<!-- 开始循环 -->
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									<td class='center'><label class="pos-rel"><input
											type='checkbox' name='ids' value="${var.INORDER_ID}"
											class="ace" /><span class="lbl"></span></label></td>
									<td class='center' style="width: 30px;">${vs.index+1}</td>
									<td class='center'>${var.BILLCODE}</td>
									<td class='center'>${var.SUPPLIERNAME}</td>
									<td class='center'>${var.ALLAMOUNT}</td>
									<td class='center'>${var.UNPAIDAMOUNT}</td>
									<td class='center'>${var.PAIDAMOUNT}</td>
									<td class='center'>${var.THISPAY}</td>
									<td class='center'><c:if test="${var.ISSETTLEMENTED == 1}">
													已结算
												</c:if> <c:if test="${var.ISSETTLEMENTED == 0}">
													未结算
												</c:if></td>
									<td class='center'>${var.PSI_NAME}</td>
									<td class='center'>${var.NOTE}</td>
									<td class="center"><c:if
											test="${QX.edit != 1 && QX.del != 1 }">
											<span
												class="label label-large label-grey arrowed-in-right arrowed-in"><i
												class="ace-icon fa fa-lock" title="无权限"></i></span>
										</c:if>
										<div class="hidden-sm hidden-xs btn-group">
											<a class="btn btn-xs btn-success" title="查看"
												onclick="view('${var.INORDER_ID}');"> <i
												class="ace-icon fa fa-eye bigger-120" title="查看"></i>
											</a>
											<c:if test="${QX.edit == 1 }">
												<a class="btn btn-xs btn-success" title="编辑"
													onclick="edit('${var.INORDER_ID}');"> <i
													class="ace-icon fa fa-pencil-square-o bigger-120"
													title="编辑"></i>
												</a>
											</c:if>
											<c:if test="${QX.del == 1 }">
												<a class="btn btn-xs btn-danger"
													onclick="del('${var.INORDER_ID}');"> <i
													class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
												</a>
											</c:if>
										</div>
										<div class="hidden-md hidden-lg">
											<div class="inline pos-rel">
												<button class="btn btn-minier btn-primary dropdown-toggle"
													data-toggle="dropdown" data-position="auto">
													<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
												</button>

												<ul
													class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
													<li><a style="cursor: pointer;"
														onclick="view('${var.INORDER_ID}');"
														class="tooltip-success" data-rel="tooltip" title="查看">
															<span class="green"> <i
																class="ace-icon fa fa-eye bigger-120"></i>
														</span>
													</a></li>
													<c:if test="${QX.edit == 1 }">
														<li><a style="cursor: pointer;"
															onclick="edit('${var.INORDER_ID}');"
															class="tooltip-success" data-rel="tooltip" title="修改">
																<span class="green"> <i
																	class="ace-icon fa fa-pencil-square-o bigger-120"></i>
															</span>
														</a></li>
													</c:if>
													<c:if test="${QX.del == 1 }">
														<li><a style="cursor: pointer;"
															onclick="del('${var.INORDER_ID}');" class="tooltip-error"
															data-rel="tooltip" title="删除"> <span class="red">
																	<i class="ace-icon fa fa-trash-o bigger-120"></i>
															</span>
														</a></li>
													</c:if>
												</ul>
											</div>
										</div></td>
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
							<td colspan="100" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div class="page-header position-relative">
			<table style="width: 100%;">
				<tr>
				</tr>
			</table>
		</div>
	</form>

</body>
</html>