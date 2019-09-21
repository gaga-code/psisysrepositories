		<div id="navbar" class="navbar navbar-default">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed');}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<!-- #section:basics/sidebar.mobile.toggle -->
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<!-- /section:basics/sidebar.mobile.toggle -->
				<div class="navbar-header pull-left">
					<!-- #section:basics/navbar.layout.brand -->
					<a class="navbar-brand" style="position: absolute; left: 47%; top: 0;">
						<small> <!-- <i class="fa fa-leaf"></i> --> ${pd.SYSNAME} </small>
					</a>
					
					<div style="position: absolute; left: 0; top: 0px; background: rgba(255,255,255,0.5);width:189px; height: 45px; text-align: center;">
					   <img src="static/login/images/kplogo.png" width="auto" height="45px" alt=""/>
				   </div>

					<!-- /section:basics/navbar.layout.brand -->

					<!-- #section:basics/navbar.toggle -->

					<!-- /section:basics/navbar.toggle -->
				</div>

				<!-- #section:basics/navbar.dropdown -->
				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
					
					<!-- 隐藏头像旁边的功能按钮 -->
						<!-- <li class="grey">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-tasks"></i>
								<span class="badge badge-grey">2</span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-check"></i>
									这只是个图标而已,不是按钮
								</li>
								<li class="dropdown-footer">
									<a href="javascript:">
										这只是个图标而已,不是按钮
										<i class="ace-icon fa fa-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<li title="即时聊天" class="purple">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#" id="myIm">
								<i class="ace-icon fa fa-bell icon-animated-bell"></i>
								<span class="badge badge-important"></span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-bell-o"></i>
									即时通讯连接提醒
								</li>
							</ul>
						</li>

						<li title="站内信" class="green" onclick="fhsms();" id="fhsmstss">fhsms()在 WebRoot\static\js\myjs\head.js中
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
								<span class="badge badge-success" id="fhsmsCount"></span>
							</a>
						</li> -->

						<!-- #section:basics/navbar.user_menu -->
						<li class="light-blue">
							<a data-toggle="dropdown"  class="dropdown-toggle" href="#">
								<!-- <img class="nav-user-photo" src="static/ace/avatars/user.jpg" alt="Jason's Photo" id="userPhoto" /> -->
								<span id="Name" style="display: inline-block; width: 40px; height: 40px; line-height: 40px; font-size: 20px; margin-top: 2px; background: rgba(0,0,0,0.5); border-radius: 50%;"></span>
								<span class="user-info" id="user_info">
								</span>
								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a onclick="editPhoto();" style="cursor:pointer;"><i class="ace-icon glyphicon glyphicon-picture"></i>修改头像</a><!-- editUserH()在 WebRoot\static\js\myjs\head.js中 -->
								</li>
								<li>
									<a onclick="editUserH();" style="cursor:pointer;"><i class="ace-icon fa fa-user"></i>修改资料</a><!-- editUserH()在 WebRoot\static\js\myjs\head.js中 -->
								</li>
							<!-- 	<li id="systemset">
									<a onclick="editSys();" style="cursor:pointer;"><i class="ace-icon fa fa-cog"></i>系统设置</a>editSys()在 WebRoot\static\js\myjs\head.js中
								</li> -->
								<li class="divider"></li>
								<li>
									<a href="logout"><i class="ace-icon fa fa-power-off"></i>退出登录</a>
								</li>
							</ul>
						</li>

						<!-- /section:basics/navbar.user_menu -->
					</ul>
				</div>
				<!-- /section:basics/navbar.dropdown -->
			</div><!-- /.navbar-container -->
		</div>
		<div id="fhsmsobj"><!-- 站内信声音消息提示 --></div>
		<script>
			window.onload = function(){
				var str = document.getElementById('user_info').innerHTML;
				var name = str.split("</small>")[1];
				var zi = name.split("")[2];
				document.getElementById('Name').innerHTML = zi;
			}
			
			
		</script>