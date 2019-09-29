/*<object id="AuthIE" name="AuthIE" width="0px" height="0px"
			codebase="DogAuth.CAB#version=2,3,1,58083"
			classid="CLSID:05C384B0-F45D-46DB-9055-C72DC76176E3">
	</object>*/

		/******************************************************************************
		 Function: validateRegForm
		 Parameters: none
		 Return: true or false
		 Description: Validate the form for emptiness.
		 ******************************************************************************/
		function validateRegForm()
		{
			var regForm = window.document.RegisterForm;
			var name = regForm.username.value;
			var pwd = regForm.password.value;
			var rPwd = regForm.repassword.value;
			if (isEmpty(name)) {
				reportStatus(907);
				return false;
			}
			else {
				if (name.length < 1 || name.length > 32) {
					reportStatus(914);
					return false;
				}
				if (!isLegalCharacters(name)) {
					reportStatus(903);
					return false;
				}
			}
			if (isEmpty(pwd)) {
				reportStatus(904);
				return false;
			}
			else {
				if (pwd.length < 6 || pwd.length > 16) {
					reportStatus(905);
					return false;
				}
				if (!isLegalCharacters(pwd)) {
					reportStatus(906);
					return false;
				}

			}
			if (isEmpty(rPwd)) {
				reportStatus(908);
				return false;
			}
			else {
				if (rPwd.length < 6 || rPwd.length > 16) {
					reportStatus(909);
					return false;
				}
				if (!isLegalCharacters(rPwd)) {
					reportStatus(910);
					return false;
				}
			}
			if (pwd != rPwd) {
				reportStatus(911);
				regForm.password.value ="";
				regForm.repassword.focus();
				return false;
			}

			return true;
		}

		/******************************************************************************
		 Function: validateChangeForm
		 Parameters: none
		 Return: true or false
		 Description: Validate the form for emptiness.
		 ******************************************************************************/
		function validateChangeForm() {
			var cForm = window.document.ChangePinForm;
			var oP = cForm.oldPwd.value;
			var nP = cForm.newPwd.value;
			var rP = cForm.retypePwd.value;

			if (isEmpty(oP)) {
				reportStatus(912);
				return false;
			}
			else {
				if (oP.length < 6 || oP.length > 16) {
					reportStatus(905);
					return false;
				}
				if (!isLegalCharacters(oP)) {
					reportStatus(906);
					return false;
				}
			}
			if (isEmpty(nP)) {
				reportStatus(913);
				return false;
			}
			else {
				if (nP.length < 6 || nP.length > 16) {
					reportStatus(905);
					return false;
				}
				if (!isLegalCharacters(nP)) {
					reportStatus(906);
					return false;
				}
			}
			if (isEmpty(rP)) {
				reportStatus(913);
				return false;
			}
			else {
				if (rP.length < 6 || rP.length > 16) {
					reportStatus(905);
					return false;
				}
				if (!isLegalCharacters(rP)) {
					reportStatus(906);
					return false;
				}
			}
			if (nP != rP) {
				reportStatus(911);
				cForm.newPwd.value = "";
				cForm.retypePwd.value = "";
				cForm.newPwd.focus();
				return false;
			}

			return true;
		}

		/******************************************************************************
		 Function: isLegalCharacters
		 Parameters: string
		 Return: true or false
		 Description: Judge the string whether it is legal.
		 ******************************************************************************/
		function isLegalCharacters(s)
		{
			var str;
			var len;
			var i;
			var c;

			str = new String(s);
			len = str.length;

			for(i=0; i < len; i++) {

				c = str.charAt(i);
				if ( (!(c >= '0' && c <= '9'))
						&& (!(c >= 'a' && c <= 'z'))
						&& (!(c >= 'A' && c <= 'Z'))
				)
				{
					return false;
				}
			}
			return true;
		}

		/******************************************************************************
		 Function: isEmpty
		 Parameters: string
		 Return: true or false
		 Description: Judge the string whether it is empty.
		 ******************************************************************************/
		function isEmpty(strValue)
		{
			var myString = new String(strValue);
			var len = myString.length;
			if ("" == myString)
			{
				return true;
			}
			for (var i=0; i < len; ++i)
			{
				if(myString.charAt(i) != " ")
				{
					return false;
				}
			}
			return true;
		}

		/******************************************************************************
		 Function: getChallenge
		 Parameters: none
		 Return: challenge
		 Description: Send XMLHttpRequest get challenge.
		 ******************************************************************************/
		function getChallenge()
		{
			var challenge = sendRequest("Auth?func=getChallenge");
			return ""+challenge+"";
		}

		/******************************************************************************
		 Function: getAuthCode
		 Parameters: none
		 Return: authCode
		 Description: Send XMLHttpRequest get authCode.
		 ******************************************************************************/
		function getAuthCode()
		{
			var authCode;
			authCode ="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";
			return ""+authCode+"";
		}

		/**********************************************************************************************
		 Function: doAuth
		 Parameters: dogID, result
		 Return: factor
		 Description: Send XMLHttpRequest do authenticate.
		 ***********************************************************************************************/
		function doAuth(dogid, result)
		{
			var ret = sendRequest("Auth?func=Authentication&dogid="+dogid+"&result="+result+"");
			return ret;
		}

		/******************************************************************************
		 Function: sendRequest
		 Parameters: url
		 Return: response text
		 Description: Send XMLHttpRequest
		 ******************************************************************************/
		function sendRequest(url)
		{
			var httpRequest = false;

			if(window.XMLHttpRequest)
			{
				httpRequest = new XMLHttpRequest();
			}
			else
			{
				try
				{
					httpRequest = new ActiveXObject("Msxm12.XMLHTTP");
				}
				catch(e)
				{
					try
					{
						httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
					}
					catch(e)
					{
					}
				}
			}

			if(!httpRequest)
			{
				return false;
			}

			httpRequest.open('POST', url, false);
			httpRequest.send(null);
			var xmlText = httpRequest.responseText;
			var reg = /[^\[][^\]]*[^\]]/;
			var newXmlText = reg.exec(xmlText);
			return newXmlText;
		}

		/******************************************************************************
		 Function: getAuthObject
		 Parameters: none
		 Return: an object for authentication
		 Description: get object for authentication
		 ******************************************************************************/
		function getAuthObject() {
			var objAuth;
			if (window.ActiveXObject || "ActiveXObject" in window) //IE
			{
				objAuth = document.getElementById("AuthIE");
			}
			else if (navigator.userAgent.indexOf("Chrome") > 0) {
				objAuth = getAuthObjectChrome();
			}
			else {
				objAuth = document.getElementById("AuthNoIE");
			}

			return objAuth;
		}

		/******************************************************************************
		 Function: embedTag
		 Parameters: none
		 Return: none
		 Description: embed tag of object for authentication
		 ******************************************************************************/
		function embedTag() {
			if (window.ActiveXObject || "ActiveXObject" in window) {//IE
				;
			}
			else if (navigator.userAgent.indexOf("Chrome") > 0) {//Chrome
				;
			}
			else {
				var temp = document.body.innerHTML;
				var tag = "<embed id=\"AuthNoIE\" type=\"application/x-dogauth\" width=0 height=0 hidden=\"true\"></embed>";
				document.body.innerHTML = tag + temp;
			}
		}

		/******************************************************************************
		 Function: clearInfo
		 Parameters: none
		 Return: none
		 Description: Clear the error info displayed in page.
		 ******************************************************************************/
		function clearInfo() {
			document.getElementById("errorinfo").innerHTML = "";
		}

		/******************************************************************************
		 Function: reportStatus
		 Parameters: status
		 Return: Description
		 Description: Report status's description.
		 ******************************************************************************/
		function reportStatus(status) {
			var text = "Unknown status code";
			switch (status) {
				case 0: text = "Success";
					break;
				case 1: text = "Request exceeds data file range";
					break;
				case 3: text = "System is out of memory";
					break;
				case 4: text = "Too many open login sessions";
					break;
				case 5: text = "Access denied";
					break;
				case 7: text = "Required SuperDog not found";
					break;
				case 8: text = "Encryption/decryption data length is too short";
					break;
				case 9: text = "Invalid input handle";
					break;
				case 10: text = "Specified File ID not recognized by API";
					break;
				case 15: text = "Invalid XML format";
					break;
				case 18: text = "SuperDog to be updated not found";
					break;
				case 19: text = "Invalid update data";
					break;
				case 20: text = "Update not supported by SuperDog";
					break;
				case 21: text = "Update counter is set incorrectly";
					break;
				case 22: text = "Invalid Vendor Code passed";
					break;
				case 24: text = "Passed time value is outside supported value range";
					break;
				case 26: text = "Acknowledge data requested by the update, however the ack_data input parameter is NULL";
					break;
				case 27: text = "Program running on a terminal server";
					break;
				case 29: text = "Unknown algorithm used in V2C file";
					break;
				case 30: text = "Signature verification failed";
					break;
				case 31: text = "Requested Feature not available";
					break;
				case 33: text = "Communication error between API and local SuperDog License Manager";
					break;
				case 34: text = "Vendor Code not recognized by API";
					break;
				case 35: text = "Invalid XML specification";
					break;
				case 36: text = "Invalid XML scope";
					break;
				case 37: text = "Too many SuperDog currently connected";
					break;
				case 39: text = "Session was interrupted";
					break;
				case 41: text = "Feature has expired";
					break;
				case 42: text = "SuperDog License Manager version too old";
					break;
				case 43: text = "USB error occurred when communicating with a SuperDog";
					break;
				case 45: text = "System time has been tampered with";
					break;
				case 46: text = "Communication error occurred in secure channel";
					break;
				case 50: text = "Unable to locate a Feature matching the scope";
					break;
				case 54: text = "The values of the update counter in the file are lower than those in the SuperDog";
					break;
				case 55: text = "The first value of the update counter in the file is greater than the value in the SuperDog";
					break;
				case 400: text = "Unable to locate dynamic library for API";
					break;
				case 401: text = "Dynamic library for API is invalid";
					break;
				case 500: text = "Object incorrectly initialized";
					break;
				case 501: text = "Invalid function parameter";
					break;
				case 502: text = "Logging in twice to the same object";
					break;
				case 503: text = "Logging out twice of the same object";
					break;
				case 525: text = "Incorrect use of system or platform";
					break;
				case 698: text = "Requested function not implemented";
					break;
				case 699: text = "Internal error occurred in API";
					break;
				case 802: text = "Parameter error";
					break;
				case 803: text = "Verify password failed";
					break;
				case 804: text = "Modify password failed";
					break;
				case 810: text = "Password's length is wrong";
					break;
				case 811: text = "Name's length is wrong";
					break;
				case 812: text = "Info's length is wrong";
					break;
				case 813: text = "Name's length is wrong";
					break;
				case 814: text = "Parameter error";
					break;
				case 820: text = "Hardware internal error!";
					break;
				case 821: text = "Parameter error";
					break;
				case 822: text = "Need to verify Password";
					break;
				case 823: text = "Verify password failed";
					break;
				case 824: text = "Need to initialize";
					break;
				case 825: text = "Password has been locked";
					break;
				case 831: text = "Verify password failed, you still have 14 chances";
					break;
				case 832: text = "Verify password failed, you still have 13 chances";
					break;
				case 833: text = "Verify password failed, you still have 12 chances";
					break;
				case 834: text = "Verify password failed, you still have 11 chances";
					break;
				case 835: text = "Verify password failed, you still have 10 chances";
					break;
				case 836: text = "Verify password failed, you still have 9 chances";
					break;
				case 837: text = "Verify password failed, you still have 8 chances";
					break;
				case 838: text = "Verify password failed, you still have 7 chances";
					break;
				case 839: text = "Verify password failed, you still have 6 chances";
					break;
				case 840: text = "Verify password failed, you still have 5 chances";
					break;
				case 841: text = "Verify password failed, you still have 4 chances";
					break;
				case 842: text = "Verify password failed, you still have 3 chances";
					break;
				case 843: text = "Verify password failed, you still have 2 chances";
					break;
				case 844: text = "Verify password failed, you still have 1 chance";
					break;
				case 845: text = "Password has been locked";
					break;
				case 901: text = "Authenticate failed";
					break;
				case 902: text = "Generate challenge string failed";
					break;
				case 903: text = "Name is illegal";
					break;
				case 904: text = "Please enter your password";
					break;
				case 905: text = "Password's length should be between 6-16 characters";
					break;
				case 906: text = "Password is illegal";
					break;
				case 907: text = "Please enter your user name";
					break;
				case 908: text = "Please enter your confirm password";
					break;
				case 909: text = "Confirm password's length should be between 6-16 characters";
					break;
				case 910: text = "Password is illegal";
					break;
				case 911: text = "Passwords do not match";
					break;
				case 912: text = "Please enter your current password";
					break;
				case 913: text = "Please enter your new password";
					break;
				case 914: text = "Name length should be between 1-32 characters";
					break;
				case 915: text = "The SuperDog has been registered";
					break;
				case 916: text = "no dog_auth_srv in java.library.path";
					break;
				case 917: text = "Fail to get challenge";
					break;
				case 918: text = "Fail to get challenge";
					break;
				case 919: text = "Cannot find session file! Please confirm you have created session folder and set the folder path!";
					break;
				case 920: text = "Fail to load the library: dog_auth_srv_php.dll! Please confirm that your configuration file is right.";
					break;

			}
		}

		/**********************************************************************************************
		 Class: AuthObject
		 Dynamic prototype method
		 Description: used for Chrome and produced with dynamic prototype method
		 ***********************************************************************************************/
		function AuthObject() {

			if (typeof AuthObject._initialized == "undefined") {

				// GetUserNameEx
				AuthObject.prototype.GetUserNameEx = function (scope, authCode) {
					//console.log("enter GetUserNameEx()");
					window.postMessage({ type: "SNTL_FROM_PAGE", text: { "InvokeMethod": "GetUserNameEx", "Scope": scope, "AuthCode": authCode} }, "*");
					return 0;
				};

				// GetDigestEx
				AuthObject.prototype.GetDigestEx = function (scope, authCode, password, challenge) {
					//console.log("enter GetDigestEx()");
					window.postMessage({ type: "SNTL_FROM_PAGE", text: { "InvokeMethod": "GetDigestEx", "Scope": scope, "AuthCode": authCode, "UserPin": password, "Challenge": challenge} }, "*");
					return 0;
				};

				// RegisterUserEx
				AuthObject.prototype.RegisterUserEx = function (scope, authCode, username, password) {
					//console.log("enter RegisterUserEx()");
					window.postMessage({ type: "SNTL_FROM_PAGE", text: { "InvokeMethod": "RegisterUserEx", "Scope": scope, "AuthCode": authCode, "Name": username, "UserPin": password } }, "*");
					return 0;
				};

				// ChangeUserPinEx
				AuthObject.prototype.ChangeUserPinEx = function (scope, authCode, oldPassword, newPassword) {
					//console.log("enter ChangeUserPinEx()");
					window.postMessage({ type: "SNTL_FROM_PAGE", text: { "InvokeMethod": "ChangeUserPinEx", "Scope": scope, "AuthCode": authCode, "OldPin": oldPassword, "NewPin": newPassword } }, "*");
					return 0;
				};

				// SetUserDataEx
				AuthObject.prototype.SetUserDataEx = function (scope, authCode, password, type, offset, data) {
					//console.log("enter SetUserDataEx()");
					window.postMessage({ type: "SNTL_FROM_PAGE", text: { "InvokeMethod": "SetUserDataEx", "Scope": scope, "AuthCode": authCode, "UserPin": password, "Type": type, "Offset": offset, "Data": data } }, "*");
					return 0;
				};

				// GetUserDataEx
				AuthObject.prototype.GetUserDataEx = function (scope, authCode, type, offset, size) {
					//console.log("enter GetUserDataEx()");
					window.postMessage({ type: "SNTL_FROM_PAGE", text: { "InvokeMethod": "GetUserDataEx", "Scope": scope, "AuthCode": authCode, "Type": type, "Offset": offset, "Size": size } }, "*");
					return 0;
				};

				// EnumDog
				AuthObject.prototype.EnumDog = function (authCode) {
					//console.log("enter EnumDog()");
					window.postMessage({ type: "SNTL_FROM_PAGE", text: { "InvokeMethod": "EnumDog", "AuthCode": authCode} }, "*");
					return 0;
				};

				AuthObject._initialized = true;
			}
		}

		/**********************************************************************************************
		 Function: getAuthObjectChrome
		 Parameters: none
		 Return: an AuthObject object
		 Description: get object for authentication
		 ***********************************************************************************************/
		function getAuthObjectChrome() {
			var obj = new AuthObject();
			return obj;
		}

		var dogNotPresent = false;
		var authCode = "";
		//Callback function, if the dog has been removed the function will be called.
		function removeDog()
		{
			reportStatus(7);
		}

		//Callback function, if the dog still exists the function will be called.
		function insertDog()
		{
			window.location.href = "/PSISYS/";
		}

		function checkDog()
		{
			var stat = "";
			var scope = "<dogscope/>";

			//Get Auth Code
			if ("" == authCode) {
				authCode = getAuthCode();

			}

			//Get object
			objAuth = getAuthObject();

			if (navigator.userAgent.indexOf("Chrome") > 0) {
				objAuth.GetUserNameEx(scope, authCode);

			}
			else {
				//Open Dog
				stat = objAuth.Open(scope, authCode);
				if(0 != stat)
				{

					dogNotPresent = true;
					reportStatus(stat);
				}
				else
				{

					if (dogNotPresent == true)
					{
						dogNotPresent = false;
						window.location.href = "/PSISYS/";
					}
				}
			}

			//Execute the check again after 2 seconds
			setTimeout(checkDog, 2000);
		}

		//Load callback functions, insertDog() and removeDog()
		function loadFunc()
    {

			var objAuth;

			//Get object
			objAuth = getAuthObject();

			if (navigator.userAgent.indexOf("Window") > 0)
			{

			 if (window.ActiveXObject || "ActiveXObject" in window)  //IE
                    {
					objAuth.SetCheckDogCallBack("insertDog", "removeDog");
					setTimeout(checkDog, 1000);
				}
			}

		}
		function validateLogin()

		{

			var challenge = "";
			var stat = "";
			var objAuth = "";
			var dogID = "";
			var digest = "";
			var scope = "<dogscope/>";
			var name = "testtest";
			var pwd = "654321";


		

			if(pwd.length<6 || pwd.length>16)
			{

				reportStatus(905);
				return false;
			}

			if(window.ActiveXObject || "ActiveXObject" in window) //IE
			{
				//Add onfocus event
				var obj = document.getElementById("password");
				if (Object.hasOwnProperty.call(window, "ActiveXObject") && !window.ActiveXObject)
				{// is IE11
					obj.addEventListener("onfocus", clearInfo, false);
				}
				else
				{
					obj.attachEvent("onfocus", clearInfo);
				}
			}

			//Get Object
			objAuth = getAuthObject();

			//Get Auth Code
			if ("" == authCode) {
				authCode = getAuthCode();
			}

			if (navigator.userAgent.indexOf("Chrome") > 0) {  //Chrome

				//Get challenge string
				challenge = getChallenge();
				if(challenge.toString().length < 32)
				{
					if(challenge == "001")
					{
						alert(916);
						reportStatus(916);
					}
					else if(challenge == "002")
					{
						alert(917);
						reportStatus(917);
					}
					else
					{
						alert(918);
						reportStatus(918);
					}
					
					return false;
				}

				//Generate digest
				alert("Generate digest");
				objAuth.GetDigestEx(scope, authCode, pwd, challenge);

				return true;
			}
			alert("open the dog");
			//Open the dog
			stat = objAuth.Open(scope, authCode);
			if(stat != 0)
			{
				alert(stat);
				reportStatus(stat);

				return false;
			}

			//Vealert("Open the dog");rify the password
			stat = objAuth.VerifyUserPin(pwd);
			if(stat != 0)
			{
				alert(stat);
				objAuth.Close();
				reportStatus(stat);

				return false;
			}

			//Get the DogID
			stat = objAuth.GetDogID();
			if(stat != 0)
			{
				alert(stat);
				objAuth.Close();
				reportStatus(stat);

				return false;
			}

			//Save the DogID
			dogID = objAuth.DogIdStr;



			challenge = getChallenge();

			if(challenge.toString().length < 32)
			{
				if(challenge == "001")
				{
					reportStatus(916);

				}
				else if(challenge == "002")
				{
					reportStatus(917);

				}
				else
				{
					reportStatus(918);

				}
				window.objAuth.Close();

				return false;
			}


			//Generate digest
			stat = objAuth.GetDigest(challenge);
			if(stat != 0)
			{
				alert(stat);
				objAuth.Close();
				reportStatus(stat);
				
				if(stat===811) {

					return true;
				}
				return false;
			}

			digest = objAuth.DigestStr;
			document.getElementById("response").value = digest;

			//Do authenticate
			stat = doAuth(dogID, digest);
			if(stat != 0)
			{
				alert(stat);
				objAuth.Close();
				reportStatus(stat);

				return false;
			}

			objAuth.Close();

			return true;
		}

	
		document.onkeydown = function(e) {

			if (e.ctrlKey &&
					(e.keyCode === 65 ||
							e.keyCode === 67 ||
							e.keyCode === 73 ||
							e.keyCode === 74 ||
							e.keyCode === 80 ||
							e.keyCode === 83 ||
							e.keyCode === 85 ||
							e.keyCode === 86 ||
							e.keyCode === 117
					)) {

				return false;
			}
			if(e.keyCode==18||e.keyCode==123){
				return false;
			}
		};
        document.oncontextmenu = function (event){
            if(window.event){
                event = window.event;
            }try{
                var the = event.srcElement;
                if (!((the.tagName == "INPUT" && the.type.toLowerCase() == "text") || the.tagName == "TEXTAREA")){

                    return false;
                }
                return true;
            }catch (e){
                return false;
            }
        }
		

