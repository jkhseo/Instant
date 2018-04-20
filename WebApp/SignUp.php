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

$allfilled = true;	
$required = array('Email','Password','FirstName','LastName','Birthdate','Address');
foreach($required as $field) {
	if(empty($_POST[$field])) {
		$allfilled = false;
	}
}

if($allfilled == true){
	$email = $_POST['Email'];
	$password = $_POST['Password'];
	$firstname = $_POST['FirstName'];
	$lastname = $_POST['LastName'];
	$birthdate = $_POST['Birthdate'];
	$address = $_POST['Address'];
	$type = "Customer";



	$success = file_get_contents("http://proj-309-sd-4.cs.iastate.edu:8080/demo/addUser?User_Type=".$type.
	"&First_Name=".$firstname."&Last_Name=".$lastname."&User_Address=".$address."&User_Birthday=".$birthdate."&User_Email=".
	$email."&User_Password=".$password);
	
	}

echo <<<_HTML
<div class="header">
	<div class="container border"> 
		<div class="row">
			<div class="twelve columns center">
				<h1 class="logo"> Instant Sign Up Page </h1>
		<div class = "row"> 
			<div class = twleve columns>
				<form method="post" action="SignUp.php">
				  E-mail:
				  <input type="text" name="Email"> <br>
				  Password:
				  <input type="password" name="Password"> <br>
				  First Name:
				  <input type="text" name="FirstName"> <br>
				  Last Name:
				  <input type="text" name="LastName"> <br>
				  Birthdate:
				  <input type="text" name="Birthdate" value = YYYY-MM-DD> <br>
				  Address:
				  <input type="text" name="Address"> <br>
				  <input type="submit" value="Sign up!"> 
				  <button> <a href=LogInPage.php> Back to Login <a> </button>
_HTML;
 				?>
 <?php 
 				if($allfilled == false){
 					echo "<h5> Please fill out all fields </h5>";
 				}
 				else if($success[15] == "F"){
 					echo "<h5> Please make sure your Birthdate is in a correct format. If it is, please try a new email </h5>";
 				}
 				else if($success[15] == "T"){
 					echo "<h5> You have successfully registered </h5>";
 				}
 				
 ?>
 <?php
echo <<<_HTML
 
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