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
$json_array = file_get_contents("http://proj-309-sd-4.cs.iastate.edu:8080/demo/getAllUserInfo?User_Email=".$_SESSION['Username']); 
$json_data=json_decode($json_array,true);
$fullname = ($json_data['All_User_Info'][0]['First_Name']).' '.($json_data['All_User_Info'][0]['Last_Name']);

echo <<<_HTML
<div class="header">
	<div class="container border"> 
		<div class="row">
			<div class="twelve columns center">
				<h1 class="logo">  $fullname's Order Status </h1>
			</div>
		</div>
	</div>
</div>
<div class="container">	
	<div class="row>"> 
			<div class="container">	
				<div class="three columns center">
					<p class="button"> <a href=InstantHomePage.php> Home </a> </p>
				</div>
				<div class="three columns center">
					<p class="button">  <a href=OrderStatus.php> Order Status </a> </p>
				</div>
				<div class="three columns center">
					<p class="button">  <a href=Map.php> Order Food </a> </p>
				</div>
				<div class="three columns center">
					<p class="button">  <a href=LogInPage.php> Logout </a> </p>
				</div>									
			</div>
	</div>
_HTML

?>
<?php 
echo "<h1 align = 'center'> Orders </h1>";
$user_ID = $json_data['All_User_Info'][0]['User_ID'];
$json_array = file_get_contents("http://proj-309-sd-4.cs.iastate.edu:8080/demo/getAllOrdersForUser?User_ID=".$user_ID);
$json_data=json_decode($json_array,true);
$count = count($json_data['All_Orders_For_User']);
for ($i = 0; $i < $count; $i++) {
	$orderNum = $i+1;
	$temp_array = $json_data['All_Orders_For_User'][$i];
	$space = ' ';
	echo <<<HTML
	<div class = "row">
		<div class = "tweleve columns">
				<table>
				<tr>
				<td width=100 style="text-align:center"> Order # $orderNum <br> </td>
				<td style="text-align:left"> Restaurant: {$temp_array['Rest_Name']} <br>
				 Food Name: {$temp_array['Food_Name']} <br> 
				 Food Price: {$temp_array['Food_Price']} <br>
				 Order Status: {$temp_array['Order_Status']} <br>
				 Order Pick Up Date: {$temp_array['Order_Date_Pick_Up']} 
				 </td>
				</tr>
				<table>
		</div> 
	</div> 
HTML;
				
}
?> 
<?php
echo <<<_HTML
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