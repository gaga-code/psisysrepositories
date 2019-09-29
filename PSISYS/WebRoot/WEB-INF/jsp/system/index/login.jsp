<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = "http://localhost:80/PSISYS/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${pd.SYSNAME}</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!--使用加密狗 -->
<!-- <object id="AuthIE" name="AuthIE" width="0px" height="0px"
			codebase="DogAuth.CAB#version=2,3,1,58083"
			classid="CLSID:05C384B0-F45D-46DB-9055-C72DC76176E3">
</object> -->

<link rel="stylesheet" href="static/login/bootstrap.min.css" />
<link rel="stylesheet" href="static/login/css/camera.css" />
<link rel="stylesheet" href="static/login/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="static/login/matrix-login.css" />
<link href="static/login/font-awesome.css" rel="stylesheet" />

<!-- <!-- 使用加密狗 static/js/superdog/login.js-->
<script type="text/javascript" src="static/js/superdog/login.js"></script>
 -->
<script type="text/javascript" src="static/login/js/jquery-1.5.1.min.js"></script>
<!-- 软键盘控件start -->
<!-- <link href="static/login/keypad/css/framework/form.css" rel="stylesheet" type="text/css"/> -->
<!-- 软键盘控件end -->
 <style type="text/css">
   
    *{
	    font-family: "微软雅黑";
	    font-size: 16px;
	    border: 0;
	    padding: 0;
	    margin: 0;
	    color: #666;
	    box-sizing:border-box;
	    -moz-box-sizing:border-box;
	    -webkit-box-sizing:border-box;
	}
	 html,body{
	    width:100%;
	}
	 body{
        background:url(static/login/images/back.jpg) no-repeat;
	    background-size: 100%;
	}
    .cavs{
	   	z-index:1;
	   	position: fixed;
	   	width:95%;
	   	margin-left: 20px;
	   	margin-right: 20px;
    }
	.bg1{
		width:100%;
		height: 237px;
		background: #347ebc;
		opacity: 0.6;
	    position:absolute;
	    left:0;
	    top:0px;
	    right:0px;
	    bottom:0px;
	    margin:auto;
	}
	.gyl{
		width:600px;
		height: 237px;
		color: #FFFFFF;
		font-size: 72px;
		position:absolute;
	    left:15%;
	    top:11%;
	    
	    bottom:0px;
	    margin:auto;
	}
	.logo{
		width: 240px;
		background: rgba(255,255,255,0.4);
		text-align: center;
	}
	.gy2{
		color: #fff;
		margin-left: 6px;
		font-size: 18px;
		text-align: center;
		margin-top: 10px;
	}
	.bg{
		width: 375px;
		height: 400px;
		background: #fff;
		position: relative;
	    position:absolute;
	    left:56%;
	    top:0px;
	    right:0px;
	    bottom:0px;
	    margin:auto;
	    z-index: 1;
	    box-shadow:0px 0px 50px rgba(0, 0, 0, 0.4);
	    border-radius: 5px;
	}
	.loginBox{
		position: absolute;
		top: 6rem;
	    right: 2.5rem;
	    width: 80%;
	}
	
	.loginBox .btn{
	     border: none;
	     color: #fff;
	     width: 18.825rem;
	     text-align: center;
	     background-color: #347ebc;
	     text-indent: 0rem;
	     border-radius: 5px;
	}
	.img{
		width: 100%;
		position: absolute;
	    left: 0;
	    right: 0;
		margin: 0 auto;
	    z-index: -1;
	}
	.wel{
		width: 375px;
		height: 35px;
		color: #347ebc;
	    font-size: 1.5rem;
	    position: absolute;
	    top: 2rem;
	    text-align: center;
	}
	
	input{
	     height: 2.5625rem;
	     width: 15.8125rem;
	     /* text-indent: 3rem; */
	     outline:none;
	}
	.user{
		height: 47px;
		display: flex;
		justify-content: space-between;
		border-bottom:1px solid #347ebc;
	}
	
	.user .form-control{
		display: inline-block;
		width: 70%;
		border: none;
		padding: 0;
		height: calc(1.5em + .75rem + 2px);
		line-height: calc(1.5em + .75rem + 2px);
		text-indent: 10px;
		font-size:16px;
	}
	.user label{
		font-size:18px;
		display: inline-block;
		width: 30%;
		height: calc(1.5em + .75rem + 2px);
		line-height: calc(1.5em + .75rem + 2px);
	}
	.form-group.user{
		margin-bottom: 0;
		margin-top: 5px;
	}
	.remeber{
		display: flex;
		justify-content: space-between;
		height: 3rem;
	}
	.rem{
	     border: none;
	     color: #323333;
	}
	.rem input{
		width: 0.8125rem;
	}
	.fg{
	     border: none;
	     color: #323333;
	}
	.yonghu{
		height: 41px;
		line-height: 41px;
	}
	#reb{
		margin-top: -33px;
		margin-left: 20px;
		font-size: 11px;
	}
	.bottom{
		width: 60%;
		line-height: 30px;
		position: absolute;
		left: 20%;
		bottom: 0;
		text-align: center;
		font-size: 12px;
		border-top: 1px solid #347ebc;
	}
	.bottom p{
		color: #fff;
	}
	
	@media (min-width:1201px) and (max-width:1500px){
		.gyl{
			width: 535px;
	    	font-size: 60px;
	    	top: 16%;
	    }
	}
	@media (min-width:800px) and (max-width:1200px)
	
	{
	   .bg{
	        width: 355px;
		    height: 300px;
	    }
	    .gyl{
	    	font-size: 50px;
	    }
	   .wel{
	   	   width: 355px;
	   } 
	    .user{
	    	top: 5rem;
	        right: 3.5rem;
	    }
	    .password{
	    	top: 8rem;
	        right: 3.5rem;
	    }
	    input{
	    	
	        width: 12rem;
	    }   
	    .rem{
		    top: 11rem;
	        right: 14.3rem;
	    }
	    .fg{
		    top: 11rem;
	        right: 3.5rem;
	    }
	    .btn{
	    	top: 14rem;
	        width: 15rem;
	    }
	}
    
  </style>
  <script>
  
 /*  $(function(){ 
	    alert("${pd.varList}"); 
	});  */
  
  		//window.setTimeout(showfh,3000); 
  		var timer;
		function showfh(){
			fhi = 1;
			//关闭提示晃动屏幕，注释掉这句话即可
			//timer = setInterval(xzfh2, 10); 
		};
		/* var current = 0;
		function xzfh(){
			current = (current)%360;
			document.body.style.transform = 'rotate('+current+'deg)';
			current ++;
			if(current>360){current = 0;}
		};
		var fhi = 1;
		var current2 = 1;
		function xzfh2(){
			if(fhi>50){
				document.body.style.transform = 'rotate(0deg)';
				clearInterval(timer);
				return;
			}
			current = (current2)%360;
			document.body.style.transform = 'rotate('+current+'deg)';
			current ++;
			if(current2 == 1){current2 = -1;}else{current2 = 1;}
			fhi++;
		}; */
	</script>
</head>
<!-- <body onload="loadFunc()">  --><!-- 使用加密狗  onload="loadFunc()-->
 <body>	
	<%-- <c:if test="${pd.isMusic == 'yes' }">
	<div style="display: none">
	    <audio src="static/login/music/fh1.mp3" autoplay=""></audio>
	</div>	
	</c:if> --%>
	<canvas class="cavs"></canvas>
	<!-- <div style="width:100%;text-align: center;margin: 0 auto;position: absolute;"> -->
		<div class="bg1"></div>
		<div class="gyl">
		       <!-- 丰盛行 -->
			   <div class="logo">
				   <img src="static/login/images/kplogo.png" width="200" height="auto" alt=""/>
			   </div>
			   
				<div class="gy2">致力于打造国内最具规模的、最符合行业标准的饰品制造供应平台 </div>   
		</div>
		<div class="bg">
			<div class="wel">ERP进销存系统登录</div>		
			<div class="loginBox">
				<div class="form-group user">
				    <label for="name">公司</label>
				      <select class="form-control" name="accountset" id="accountset" onchange="selectAccount(this.value)" placeholder="请选择账套" title="账套">
						<c:forEach items="${pd.varList}" var="var">
							<option value="${var.SOBOOKS_ID }" <c:if test="${var.SOBOOKS_ID == pd.SOBOOKS_ID }">selected</c:if>>${var.ENTERPRISENAME }</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group user">
				    <label class="yonghu" for="name">用户名</label>
				    <input type="text" class="form-control" list="list1" onmousedown="selectAccount(this.value)"  name="loginname" id="loginname" value="" placeholder="请输入用户名" />
					<datalist id="list1" >
							<option value=""></option>
							<option value=""></option>
							<option value=""></option>
							<option value=""></option>
							<option value=""></option>
					</datalist>
				</div>
				<div class="form-group user">
				    <label class="yonghu" for="name">密&nbsp;&nbsp;&nbsp;码</label>
					<input type="password" name="password" list="list1"id="password" placeholder="请输入密码" class="form-control keypad" keypadMode="full" allowKeyboard="true" value=""/>
				</div>
				<div class="remeber">
					<div class="fg">
					    <!-- <div style="font-size: 11px;margin-top: 11px;">
					    	<a style="font-size: 11px;" href="#">忘记密码？</a>
					    </div> -->
					</div>
					<div class="rem">
					  <input name="form-field-checkbox" id="saveid" type="checkbox"
							onclick="savePaw();"/>
						 <div id="reb">
						 	记住密码
						 </div>
					</div>
					
				</div>
				<input id="to-recover" class="flip-link btn" type="button" name="登录" value="登录" onclick="severCheck();">
			</div>
		</div>
		<div class="bottom">
			<p>Copyright © 2019 丰盛行有限公司 版权所有</p>
		</div>
		
		<!-- 登录 -->
		<!-- <div id="windows1">
		<div id="loginbox" >
			<form action="" method="post" name="loginForm" id="loginForm">
				<div class="control-group normal_text">
					<h3>ERP进销存系统</h3>
				</div> -->
				<!-- <div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i><img height="37" src="static/login/user.png" /></i>
							</span><input type="text" name="accountset" id="accountset" value="" placeholder="请选择账套" />
						</div>
					</div>
				</div> -->
				<!-- <div>
					<select name="accountset" id="accountset" placeholder="请选择账套" title="账套" style="width:81%; height:38px" >
						<c:forEach items="${pd.varList}" var="var">
							<option value="${var.SOBOOKS_ID }" <c:if test="${var.SOBOOKS_ID == pd.SOBOOKS_ID }">selected</c:if>>${var.ENTERPRISENAME }</option>
						</c:forEach>
					</select>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i><img height="37" src="static/login/user.png" /></i>
							</span><input type="text" name="loginname" id="loginname" value="" placeholder="请输入用户名" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_ly">
							<i><img height="37" src="static/login/suo.png" /></i>
							</span><input type="password" name="password" id="password" placeholder="请输入密码" class="keypad" keypadMode="full" allowKeyboard="true" value=""/>
						</div>
					</div>
				</div>
				<div style="float:right;padding-right:10%;">
					<div style="float: left;margin-top:3px;margin-right:2px;">
						<font color="white">记住密码</font>
					</div>
					<div style="float: left;">
						<input name="form-field-checkbox" id="saveid" type="checkbox"
							onclick="savePaw();" style="padding-top:0px;" />
					</div>
				</div>
				<div class="form-actions">
					<div style="width:86%;padding-left:8%;">

<!-- 						<div style="float: left;padding-top:2px;"> -->
<!-- 							<i><img src="static/login/yan.png" /></i> -->
<!-- 						</div> -->
<!-- 						<div style="float: left;" class="codediv"> -->
<!-- 							<input type="text" name="code" id="code" class="login_code" -->
<!-- 								style="height:16px; padding-top:4px;" /> -->
<!-- 						</div> -->
<!-- 						<div style="float: left;"> -->
<!-- 							<i><img style="height:22px;" id="codeImg" alt="点击更换" title="点击更换" src="" /></i> -->
<!-- 						</div> -->
						<!-- <c:if test="${pd.isZhuce == 'yes' }">
						<span class="pull-right" style="padding-right:3%;"><a href="javascript:changepage(1);" class="btn btn-success">注册</a></span>
						</c:if>
						<span class="pull-right"><a onclick="severCheck();" class="flip-link btn btn-info" id="to-recover">登录</a></span>
					</div>
				</div>
			</form>
			<div class="controls">
				<div class="main_input_box">
					<font color="white"><span id="nameerr">Copyright © 2019</span></font>
				</div>
			</div>
		</div>
		</div>-->
		<!-- 注册 -->
		<div id="windows2" style="display: none;">
		<div id="loginbox">
			<form action="" method="post" name="loginForm2" id="loginForm2">
				<div class="control-group normal_text">
					<h3>
						<img src="static/login/logo.png" alt="Logo" />
					</h3>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i>用户</i>
							</span><input type="text" name="USERNAME" id="USERNAME" value="" placeholder="请输入用户名" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_ly">
							<i>密码</i>
							</span><input type="password" name="PASSWORD" id="PASSWORD" placeholder="请输入密码" class="keypad" keypadMode="full" allowKeyboard="true" value=""/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_ly">
							<i>重输</i>
							</span><input type="password" name="chkpwd" id="chkpwd" placeholder="请重新输入密码" class="keypad" keypadMode="full" allowKeyboard="true" value=""/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i>姓名</i>
							</span><input type="text" name="NAME" id="name" value="" placeholder="请输入姓名" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i>邮箱</i>
							</span><input type="text" name="EMAIL" id="EMAIL" value="" placeholder="请输入邮箱" />
						</div>
					</div>
				</div>
				<div class="form-actions">
					<div style="width:86%;padding-left:8%;">

						<div style="float: left;padding-top:2px;">
							<i><img src="static/login/yan.png" /></i>
						</div>
						<div style="float: left;" class="codediv">
							<input type="text" name="rcode" id="rcode" class="login_code"
								style="height:16px; padding-top:4px;" />
						</div>
						<div style="float: left;">
							<i><img style="height:22px;" id="zcodeImg" alt="点击更换" title="点击更换" src="" /></i>
						</div>
						<span class="pull-right" style="padding-right:3%;"><a href="javascript:changepage(2);" class="btn btn-success">取消</a></span>
						<span class="pull-right"><a onclick="register();" class="flip-link btn btn-info" id="to-recover">提交</a></span>
					</div>
				</div>
			</form>
			<div class="controls">
				<div class="main_input_box">
					<font color="white"><span id="nameerr">Copyright © 2019</span></font>
				</div>
			</div>
		</div>
		</div>
		
	</div>
	<%-- <div id="templatemo_banner_slide" class="container_wapper">
		<div class="camera_wrap camera_emboss" id="camera_slide">
			<!-- 背景图片 -->
			<c:choose>
				<c:when test="${not empty pd.listImg}">
					<c:forEach items="${pd.listImg}" var="var" varStatus="vs">
						<div data-src="static/login/images/${var.FILEPATH }"></div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div data-src="static/login/images/banner_slide_01.jpg"></div>
					<div data-src="static/login/images/banner_slide_02.jpg"></div>
					<div data-src="static/login/images/banner_slide_03.jpg"></div>
					<div data-src="static/login/images/banner_slide_04.jpg"></div>
					<div data-src="static/login/images/banner_slide_05.jpg"></div>
				</c:otherwise>
			</c:choose> 
		</div>
		<!-- #camera_wrap_3 -->
	</div>--%>

	<script type="text/javascript">
		window.history.go(1);
		//服务器校验
		function severCheck(){
			if(check()){
				var loginname = $("#loginname").val();
				var password = $("#password").val();
				var accountset = $("#accountset").val();
				var code = loginname+","+password+","+$("#code").val() + "," + accountset;
				$.ajax({
					type: "POST",
					url: 'login_login',
			    	data: {KEYDATA:code,tm:new Date().getTime()},
					dataType:'json',
					cache: false,
					success: function(data){
						if("success" == data.result){
							
								saveCookie();
								window.location.href = "main/index";  
								
							// <!-- 使用加密狗  validateLogin-->
							/* if(validateLogin()===true) {
								saveCookie();
								window.location.href = "main/index";
							}
							else {
								$("#loginname").tips({
									side: 1,
									msg: "加密狗错误",
									bg: '#FF5080',
									time: 15
								});
							} */
						}else if("usererror" == data.result){
							$("#loginname").tips({
								side : 1,
								msg : "用户名或密码或账套有误",
								bg : '#FF5080',
								time : 15
							});
							showfh();
							$("#loginname").focus();
						}else if("codeerror" == data.result){
							$("#code").tips({
								side : 1,
								msg : "验证码输入有误",
								bg : '#FF5080',
								time : 15
							});
							showfh();
							$("#code").focus();
						}else{
							$("#loginname").tips({
								side : 1,
								msg : "缺少参数",
								bg : '#FF5080',
								time : 15
							});
							showfh();
							$("#loginname").focus();
						}
					}
				});
			}
		}
	
		$(document).ready(function() {
			changeCode1();
			$("#codeImg").bind("click", changeCode1);
			$("#zcodeImg").bind("click", changeCode2);
		});

		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		function changeCode1() {
			$("#codeImg").attr("src", "code.do?t=" + genTimestamp());
		}
		function changeCode2() {
			$("#zcodeImg").attr("src", "code.do?t=" + genTimestamp());
		}

		//客户端校验
		function check() {

			if ($("#loginname").val() == "") {
				$("#loginname").tips({
					side : 2,
					msg : '用户名不得为空',
					bg : '#AE81FF',
					time : 3
				});
				showfh();
				$("#loginname").focus();
				return false;
			} else {
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}
			if ($("#password").val() == "") {
				$("#password").tips({
					side : 2,
					msg : '密码不得为空',
					bg : '#AE81FF',
					time : 3
				});
				showfh();
				$("#password").focus();
				return false;
			}
			if ($("#code").val() == "") {
				$("#code").tips({
					side : 1,
					msg : '验证码不得为空',
					bg : '#AE81FF',
					time : 3
				});
				showfh();
				$("#code").focus();
				return false;
			};
			/* $("#loginbox").tips({
				side : 1,
				msg : '正在登录 , 请稍后 ...',
				bg : '#68B500',
				time : 10
			}); */

			return true;
		}

		function savePaw() {
			if (!$("#saveid").attr("checked")) {
				$.cookie('loginname', '', {
					expires : -1
				});
				$.cookie('password', '', {
					expires : -1
				});
				$("#loginname").val('');
				$("#password").val('');
			}
		}

		function saveCookie() {
			if ($("#saveid").attr("checked")) {
				$.cookie('loginname', $("#loginname").val(), {
					expires : 7
				});
				$.cookie('password', $("#password").val(), {
					expires : 7
				});
			}
		}
		
		jQuery(function() {
			var loginname = $.cookie('loginname');
			var password = $.cookie('password');
			if (typeof(loginname) != "undefined"
					&& typeof(password) != "undefined") {
				$("#loginname").val(loginname);
				$("#password").val(password);
				$("#saveid").attr("checked", true);
				$("#code").focus();
			}
		});
		
		
		function selectAccount(accountset){
			var accountset1=$('#accountset').val();
			$('#loginname').val("");
			var flag=0;
			var num;
		    var arr = new Array();
		    <c:forEach items="${pd.varList}" var= "item">
		      var item = {"SOBOOKS_ID":"${item.SOBOOKS_ID}"};
			   if(accountset1==item.SOBOOKS_ID){
					num=flag;
			   }
			   flag+=1;
			</c:forEach>
			flag=0;
		   var array =new Array();
		   <c:forEach items="${map}" var= "item">
		  		if(flag==num){
		  			<c:forEach items="${item.value}" var="it">          
		  		        array.push("${it}");
		  			</c:forEach>
		  		}
		  	 flag+=1;
			</c:forEach> 
			
			
			var list1= document.getElementById("list1");
			var op= document.getElementById("list1").options;
			 for(var i=0;i<array.length;i++){
				list1.options[i].text=array[i];
				list1.options[i].value=array[i];
			} 
		}
		
		
		//登录注册页面切换
		function changepage(value) {
			if(value == 1){
				$("#windows1").hide();
				$("#windows2").show();
				changeCode2();
			}else{
				$("#windows2").hide();
				$("#windows1").show();
				changeCode1();
			}
		}
		
	//注册
	function rcheck(){
		if($("#USERNAME").val()==""){
			$("#USERNAME").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#USERNAME").focus();
			$("#USERNAME").val('');
			return false;
		}else{
			$("#USERNAME").val(jQuery.trim($('#USERNAME').val()));
		}
		if($("#PASSWORD").val()==""){
			$("#PASSWORD").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PASSWORD").focus();
			return false;
		}
		if($("#PASSWORD").val()!=$("#chkpwd").val()){
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#chkpwd").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		if($("#EMAIL").val()==""){
			$("#EMAIL").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!ismail($("#EMAIL").val())){
			$("#EMAIL").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}
		if ($("#rcode").val() == "") {
			$("#rcode").tips({
				side : 1,
				msg : '验证码不得为空',
				bg : '#AE81FF',
				time : 3
			});
			$("#rcode").focus();
			return false;
		}
		return true;
	}
	
	//提交注册
	function register(){
		if(rcheck()){
			var nowtime = date2str(new Date(),"yyyyMMdd");
			$.ajax({
				type: "POST",
				url: 'appSysUser/registerSysUser.do',
		    	data: {USERNAME:$("#USERNAME").val(),PASSWORD:$("#PASSWORD").val(),NAME:$("#name").val(),EMAIL:$("#EMAIL").val(),rcode:$("#rcode").val(),FKEY:$.md5('USERNAME'+nowtime+',fh,'),tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("00" == data.result){
						$("#windows2").hide();
						$("#windows1").show();
						$("#loginbox").tips({
							side : 1,
							msg : '注册成功,请登录',
							bg : '#68B500',
							time : 3
						});
						changeCode1();
					}else if("04" == data.result){
						$("#USERNAME").tips({
							side : 1,
							msg : "用户名已存在",
							bg : '#FF5080',
							time : 15
						});
						showfh();
						$("#USERNAME").focus();
					}else if("06" == data.result){
						$("#rcode").tips({
							side : 1,
							msg : "验证码输入有误",
							bg : '#FF5080',
							time : 15
						});
						showfh();
						$("#rcode").focus();
					}
				}
			});
		}
	}
	
	//邮箱格式校验
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
	}
	//js  日期格式
	function date2str(x,y) {
	     var z ={y:x.getFullYear(),M:x.getMonth()+1,d:x.getDate(),h:x.getHours(),m:x.getMinutes(),s:x.getSeconds()};
	     return y.replace(/(y+|M+|d+|h+|m+|s+)/g,function(v) {return ((v.length>1?"0":"")+eval('z.'+v.slice(-1))).slice(-(v.length>2?v.length:2))});
	 	};
	</script>
	<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
	<c:if test="${'1' == pd.msg}">
		<script type="text/javascript">
		$(tsMsg());
		function tsMsg(){
			alert('此用户在其它终端已经早于您登录,您暂时无法登录');
		}
		</script>
	</c:if>
	<c:if test="${'2' == pd.msg}">
		<script type="text/javascript">
			$(tsMsg());
			function tsMsg(){
				alert('您被系统管理员强制下线或您的帐号在别处登录');
			}
		</script>
	</c:if>
	<script src="static/login/js/bootstrap.min.js"></script>
	<script src="static/js/jquery-1.7.2.js"></script>
	<script src="static/login/js/jquery.easing.1.3.js"></script>
	<script src="static/login/js/jquery.mobile.customized.min.js"></script>
	<script src="static/login/js/camera.min.js"></script>
	<script src="static/login/js/templatemo_script.js"></script>
	<script src="static/login/js/ban.js"></script>
	<script type="text/javascript" src="static/js/jQuery.md5.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
	
	<!-- 软键盘控件start -->
	<!-- <script type="text/javascript" src="static/login/keypad/js/form/keypad.js"></script>
	<script type="text/javascript" src="static/login/keypad/js/framework.js"></script> -->
	<!-- 软键盘控件end -->
</body>

</html>