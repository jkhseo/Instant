<!Doctype html> 
<html>
<head>
<script src='jquery-1.11.2.min.js'> </script>
	<title> Instant HomePage </title> 
   <meta charset="utf-8">
   	<link rel="stylesheet" href="css/normalize.css"> 
	<link rel="stylesheet" href="css/skeleton.css">
    <link rel="stylesheet" href="css/custom.css">
<style>
	body {background-color: #ffa500;}
	table, th, td {
    border: 1px solid white;
    text-align: center;
	}
	@media (max-width:550px) {
	.button {
    font-size:.8em;
	}
	}
	@media (min-width: 550px) {
	p img {
    width:100%;
    height:auto;
    }
    table{
    width:100%;
    }
	}
</style>
</head>
<body>

<?php
session_start();

	
if(isset($_POST['Username'])){
	$json_array = file_get_contents("http://proj-309-sd-4.cs.iastate.edu:8080/demo/getPassword?User_Email=".$_POST['Username']);

	$json_data=json_decode($json_array,true);
	if(!empty($json_data['Get_Password'])){
		$password = ($json_data['Get_Password'][0]['User_Password']);
		if ($_POST['Password']==$password){
			echo "Logged in";
			$_SESSION['Username'] = $_POST['Username'];
			header('location: InstantHomePage.php');
		} 
		else {
			echo "Wrong username or password. Please Try again";
		}

	}
	else {
		echo "Wrong username or password. Please Try again";
	}
	



}

echo <<<_HTML
<div class="header">
	<div class="container border"> 
		<div class="row">
			<div class="twelve columns center">
				<h1 class="logo"> Instant </h1>
		<div class = "row"> 
			<div class = twleve columns>
				<form method="post" action="LogInPage.php">
				  Username:
				  <input type="text" name="Username"> <br>
				  Password:
				  <input type="password" name="Password"> <br>
				  <input type="submit" value="Log in!"> 
				  <button> <a href=SignUp.php> Sign Up Here! </a> </button> 
 
			</div>
		</div>
	</div>
</div>
</body>

</html> 
_HTML
?>
 <!-- footer -->
<div class="container">
  <div class="row" align='center'>
      <p>© Instant</p>
  </div>
</div>
</body></html>