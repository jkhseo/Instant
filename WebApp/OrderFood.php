<!Doctype html> 
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
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
// 	
// 	require_once 'login.php';
// 	$connect = new mysqli($hostname, $username, $password, $database);
// 	$query = "SELECT * FROM db309sd4.Restaurant WHERE Rest_ID = 1";
// 
// 	if ($connect->connect_error) {
// 	die("Connection failed: ". $connect->connect_error);
// 	}
// 	$result = $connect->query($query);
session_start();
$json_array = file_get_contents("http://proj-309-sd-4.cs.iastate.edu:8080/demo/getAllUserInfo?User_Email=".$_SESSION['Username']); 
$json_data=json_decode($json_array,true);
$fullname = ($json_data['All_User_Info'][0]['First_Name']).' '.($json_data['All_User_Info'][0]['Last_Name']);
$rest_id = $_GET["Rest_ID"]; 
$user_id = ($json_data['All_User_Info'][0]['User_ID']);
echo <<<_HTML


<script>
window.localStorage.clear(); 
function addFoodItem(id) {
	var food_id = id.split("_")[1];
	var food_quantity = document.getElementById(food_id).value;
	if (food_quantity != "" && !isNaN(food_quantity)) {
		if (localStorage.getItem("food_ids") == null) {
			localStorage.setItem("food_ids", food_id);
			localStorage.setItem("food_quantities", food_quantity);
			localStorage.setItem("food_comments", "nothing");
		}
		else {	
			localStorage.setItem("food_ids", localStorage.getItem("food_ids")+ "," + food_id);	
			localStorage.setItem("food_quantities", localStorage.getItem("food_quantities")+ "," + food_quantity);
			localStorage.setItem("food_comments", localStorage.getItem("food_comments") + "NEWCOMMENTBLOCK" + "nothing");
		}
		
		
	}
	else {
	//donothing;
	}
	console.log(localStorage.getItem("food_ids"));
	console.log(localStorage.getItem("food_quantities"));

}


function sendOrder() {
	var orderquery = "http://proj-309-sd-4.cs.iastate.edu:8080/demo/addOrder_Unencrypted"
	orderquery += "?Rest_ID=$rest_id";
	orderquery += "&User_ID=$user_id";
	orderquery += "&Food=" +localStorage.getItem("food_ids")
	orderquery += "&Comments=" +localStorage.getItem("food_comments")
	orderquery += "&Quantity=" +localStorage.getItem("food_quantities")
	orderquery += "&Order_Date_Pick_Up=2018-04-24%2014:22:18"
	orderquery += "&VersionNumber=1"
	console.log(orderquery);
	$.getJSON(orderquery, function(data){
		window.location.href = "OrderFood.php?Rest_ID="+$rest_id;
	});
	
	
// addNewOrder(@RequestParam String Rest_ID, @RequestParam String User_ID, @RequestParam String Food, @RequestParam String Comments,  
// @RequestParam String Quantity, @RequestParam String Order_Date_Pick_Up, @RequestParam String VersionNumber)
}



</script>
<div class="header">
	<div class="container border"> 
		<div class="row">
			<div class="twelve columns center">
				<h1 class="logo"> Let's Order Food $fullname </h1>
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
echo "<h1 align = 'center'> Menu </h1>";
$Rest_ID = $_GET["Rest_ID"];
$json_array = file_get_contents("http://proj-309-sd-4.cs.iastate.edu:8080/demo/getMenu?Restaurant_ID=".$Rest_ID);
$json_data=json_decode($json_array,true);
$count = count($json_data['Menu']);
for($i = 0; $i <$count; $i++){
	$orderNum = $i+1;
	$temp_array = $json_data['Menu'][$i];
	$food_id = $temp_array['Food_ID']; 
	$space = ' ';
	echo <<<HTML
	<div class = "row">
		<div class = "tweleve columns">
				<table>
				<tr>
				<td width=100 style="text-align:center"> Food Name:{$temp_array['Food_Name']}  <br> </td>
				<td width=300 style="text-align:left"> Price: {$temp_array['Food_Price']} <br>
				 Description: {$temp_array['Food_Desc']} <br> 
				 Type: {$temp_array['Food_Tags_Main']} <br>
				</td>
				<td id="boxfor$food_id" width=100> Quantity: <input id="$food_id" type="text"> <br>
				<div style="text-align:center" class="ten columns center">
				<p id="add_$food_id" onclick="addFoodItem(this.id)" width=200 class="button"> Add food </p> 
				</div>
	

				</td>
				
				</tr>
				
				<table>
		</div> 
	</div> 


HTML;



}
echo "(YYYY-MM-DD HH:MM:SS) Date: <input id='date' style='text-align:center' type='text'> <br>";
echo "<p width=100 class='button' onclick='sendOrder()'> Order </p>";


	

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