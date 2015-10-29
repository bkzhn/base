<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Subutai | Login</title>

    <link href="resources/css/style.css" rel="stylesheet">

</head>

<body>

	<div class="b-login-layout">

		<div class="b-login">
			<div class="b-login__title">
				<div class="b-login-title">
					<div class="b-login-title__welcom">
						Welcome
					</div>
					<div class="b-login-title__progect-name">
						Subutai
					</div>
					<div class="clear">
						<%
              //****************************************************
							if(request.getAttribute( "error" )!=null)
							{
								String error = (String)request.getAttribute( "error" );
                out.print("<label class=\"error\">asdasd Username</label>");
							}
              //****************************************************
						%>

					</div>
				</div>
			</div>
			<div class="b-login__form">
				<form action="/usercontrol" method="post">
					<div class="b-login-form">
						<div class="b-login-form__item">
							<label for="login">Username</label>
							<input type="text" name="login" value="admin" id="login">
						</div>
						<div class="b-login-form__item">
							<label for="">Password</label>
							<input type="password" name="password" value="secret">
						</div>
						<div class="b-login-form__item b-login-form__item_no-label b-login-form__item_last">
							<button class="b-button">Sign In</button>
						</div>
						<div class="clear"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>