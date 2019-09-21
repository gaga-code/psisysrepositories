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

<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<!-- 百度echarts -->
<script src="plugins/echarts/echarts.min.js"></script>
<script type="text/javascript">
setTimeout("top.hangge()",500);
</script>
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- 自定義CSS文件 -->
<link rel="stylesheet" href="static/myCSS/style.css"/>
<script type="text/javascript" src="static/js/myjs/head.js"></script>
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
							<form action="login_default" method="post" name="Form" id="Form">
								<table style="margin-top:5px;">
									<tr>
										<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="${pd.lastStart }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
										<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="${pd.lastEnd }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
										<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>	
									</tr>
								</table>
							</form>
							<div  style="width: 800px;padding-top: 15px;">
								<table class="table table-striped table-bordered table-hover">
									<tr>
										<td style="width: 100px;">总进货金额</td>
										<td><font style="color: red"><b>${pd.injine }</b></font>&nbsp;元</td>
										<td style="width: 100px;">总销售金额</td>
										<td><font style="color: red"><b>${pd.outjine }</b></font>&nbsp;元</td>
										<td style="width: 100px;">总销售利润</td>
										<td><font style="color: red"><b>${pd.lirun}</b></font>&nbsp;元</td>
									</tr>
								</table>
							</div>
							<div id="main" style="width: 650px;height:300px;"></div>
							
							<script type="text/javascript">
						        // 基于准备好的dom，初始化echarts实例
						        var myChart = echarts.init(document.getElementById('main'));
						
						        // 指定图表的配置项和数据
								var option = {
						            title: {
						                text: '客户统计'
						            },
						            tooltip: {},
						            xAxis: {
						                data: ["客户总数：${pd.userCount}","30天内新增客户数：${pd.newUuserCount}"]
						            },
						            yAxis: {},
						            series: [
						               {
						                name: '',
						                type: 'bar',
						                data: [${pd.userCount},${pd.newUuserCount}],
						                itemStyle: {
						                    normal: {
						                        color: function(params) {
						                            // build a color map as your need.
						                            var colorList = ['#6FB3E0','#87B87F'];
						                            return colorList[params.dataIndex];
						                        }
						                    }
						                }
						               }
						            ]
						        };	        

						        // 使用刚指定的配置项和数据显示图表。
						        myChart.setOption(option);
						    </script>
							
						</div>
						<!-- /.col -->
				<%-- 		
						<div class="span6" style="padding-top: 13px;">
									<div class="tabbable">
								            <ul class="nav nav-tabs" id="myTab">
								              <li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i>折线图</a></li>
								              <li><a data-toggle="tab" href="#profile"><i class="green icon-cog bigger-110"></i>柱状图</a></li>
								            </ul>
								            <div class="tab-content">
											  <div id="home" class="tab-pane in active">
												<table style="width:865px;" border="0px;">
													<tr>
														<td>
															<jsp:include
															page="../../FusionChartsHTMLRenderer.jsp" flush="true">
															<jsp:param name="chartSWF" value="static/FusionCharts/Line.swf" />
															<jsp:param name="strURL" value="" />
															<jsp:param name="strXML" value="${str2}" />
															<jsp:param name="chartId" value="myNext" />
															<jsp:param name="chartWidth" value="860" />
															<jsp:param name="chartHeight" value="300" />
															<jsp:param name="debugMode" value="false" />
															</jsp:include>
														</td>
													</tr>
												</table>
											  </div>
											  <div id="profile" class="tab-pane">
												 <table style="width:865px;" border="0px;">
													<tr>
														<td>
															<jsp:include
															page="../../FusionChartsHTMLRenderer.jsp" flush="true">
															<jsp:param name="chartSWF" value="static/FusionCharts/Column3D.swf" />
															<jsp:param name="strURL" value="" />
															<jsp:param name="strXML" value="${str1}" />
															<jsp:param name="chartId" value="myNext" />
															<jsp:param name="chartWidth" value="860" />
															<jsp:param name="chartHeight" value="300" />
															<jsp:param name="debugMode" value="false" />
															</jsp:include>
														</td>
													</tr>
												</table>
											  </div>
								            </div>
									</div>
								 </div><!--/span-->		
						
					</div> --%>
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
	<%@ include file="../index/foot.jsp"%>
	
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
	
		if (window.history && window.history.pushState) {
		    history.pushState("forward", null, location.href);
		    $(window).on("popstate", function() {
		         history.pushState("forward", null, location.href);
		     });
		}

		$(top.hangge());

		function shenpi(){
			 top.jzts();
			 $.ajax({
					type: "POST",
					url: '<%=basePath%>goods/check_goods_stock_down_num.do?tm='+new Date().getTime(),
			    	data: {},
					dataType:'json',
					cache: false,
					success: function(data){
						//tosearch();
						top.hangge();
						if(data != null){
							if($("#alertBox").is(":hidden")){
								$('#alertBox').show();
							}else{
								var htmlStr = "<div id='alertBox'><div class='boxTop'><span>库存预警</span><span id='boxClose'> X </span></div><ul id=\"alertGoodsList\">";
								
								for( var i = 0; i < data.length; i++ ){
									htmlStr += "<li><span>"+data[i].GOODCODE+"</span><span>"+data[i].GOODNAME+"</span><span>"+data[i].STOCKNUM+"</span></li>"
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
		
		
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
			shenpi();
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