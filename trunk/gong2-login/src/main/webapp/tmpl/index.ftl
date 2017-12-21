<#include "/lib/const.ftl">
<!DOCTYPE html>
<html>
<head>
	<title>我的宫廷-后台登录</title>
	<meta http-equiv="Content-type" content="text/html;charset=utf-8"/>
	<meta name="keywords" content="关键字"/>
	<meta name="description" content="网页描述"/>
	<meta name="author" content="hadoit"/>
	<meta name="version" content="1.0"/>
	<style type="text/css">
	* {
		overflow: hidden;
		font-size: 9pt;
	}
	a, img {
		border: 0;
	}
	body {
		margin-left: 0px;
		margin-top: 0px;
		margin-right: 0px;
		margin-bottom: 0px;
		background-image: url(${baseRoot}/style/img/i/lbg.gif);
		background-repeat: repeat-x;
	}
	form, input {
		font-size: 14px;
		font-family: Arial, Helvetica, sans-serif;
	}
	.err {
		height: 20px;
		color: #f00;
		text-align: center;
	}
	</style>
</head>
<body onload="document.getElementById('username').focus()">
<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0">
  <tbody>
    <tr>
      <td><table width="100%" cellspacing="0" cellpadding="0" border="0">
          <tbody>
            <tr>
              <td height="561" style="background: url(${baseRoot}/style/img/i/lbg.gif&quot;) repeat scroll 0% 0% transparent;"><table width="940" cellspacing="0" cellpadding="0" border="0" align="center">
                  <tbody>
                    <tr>
                      <td height="238" style="background: url(${baseRoot}/style/img/i/login_03.gif&quot;) repeat scroll 0% 0% transparent;">&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="190"><table width="100%" cellspacing="0" cellpadding="0" border="0">
                          <tbody>
                            <tr>
                              <td width="208" height="190" style="background: url(${baseRoot}/style/img/i/login02.jpg&quot;) repeat scroll 0% 0% transparent;">&nbsp;</td>
                              <td width="518" style="background: url(${baseRoot}/style/img/i/login03.jpg&quot;) repeat scroll 0% 0% transparent;"><form id="js_login" action="${basePath}/login" method="post">
                                  <input type="hidden" value="" name="ref" />
                                  <input type="hidden" value="${redirect?default("")}" name="redirect" />
                                  <table width="320" cellspacing="0" cellpadding="0" border="0" align="center">
                                    <tbody>
                                      <tr>
                                        <td width="40" height="50"><img width="30" height="30" src="${baseRoot}/style/img/i/user.gif"> </td>
                                        <td width="38" height="50">账号</td>
                                        <td width="242" height="50"><input type="text" style="width: 164px; height: 25px; line-height: 34px; background: url(${baseRoot}/style/img/i/inputbg.gif&quot;) repeat-x scroll 0% 0% transparent; border: 1px solid rgb(209, 209, 209); padding-left: 5px; font-size: 16pt;" id="username" name="username">
                                        </td>
                                      </tr>
                                      <tr>
                                        <td height="50"><img width="28" height="32" src="${baseRoot}/style/img/i/password.gif"> </td>
                                        <td height="50">密码</td>
                                        <td height="50"><input type="password" style="width: 164px; height: 25px; line-height: 34px; background: url(${baseRoot}/style/img/i/inputbg.gif&quot;) repeat-x scroll 0% 0% transparent; border: 1px solid rgb(209, 209, 209); font-size: 16pt; padding-left: 5px;" id="passwd" onkeydown="if(event.keyCode == 13){document.getElementById('js_login').submit();}" name="passwd">
                                        </td>
                                      </tr>
                                      <tr>
                                        <td height="40">&nbsp;</td>
                                        <td height="40">&nbsp;</td>
                                        <td height="60"><a id="login_submit" onclick="document.getElementById('js_login').submit();" href="javascript:;"><img width="95" height="34" src="${baseRoot}/style/img/i/login.gif"></a>&nbsp;&nbsp;${code!!} </td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </form></td>
                              <td width="214" style="background: url(${baseRoot}/style/img/i/login04.jpg&quot;) repeat scroll 0% 0% transparent;">&nbsp;</td>
                            </tr>
                          </tbody>
                        </table></td>
                    </tr>
                    <tr>
                      <td height="133" style="background: url(${baseRoot}/style/img/i/login05.jpg&quot;) repeat scroll 0% 0% transparent;">&nbsp;</td>
                    </tr>
                  </tbody>
                </table></td>
            </tr>
          </tbody>
        </table></td>
    </tr>
  </tbody>
</table>
</body>
</html>
