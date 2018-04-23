<!Doctype html> 
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBTHLxyrImyn45wgP5aqzUKg7NxO732NiM"></script>
<script src='googlemap.js'> </script>
	<title> Map </title> 
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
    #map{
    height:400px;
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

echo <<<_HTML
<div class="header">
	<div class="container border"> 
		<div class="row">
			<div class="twelve columns center">
				<h1 class="logo"> Welcome to Instant $fullname </h1>
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
	
	
		</div>
	</div>
</div>

<div class="container">	

<div id="map"> </div>
</div>

</body>

<script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBTHLxyrImyn45wgP5aqzUKg7NxO732NiM&callback=initMap">
</script>



<script>

$.getJSON("http://proj-309-sd-4.cs.iastate.edu:8080/demo/getRestaurantsInView?max_Lat=42.0597&max_Long=-93.399&min_Lat=41.8919&min_Long=-93.88", function(data){
	for(var key in data) {
		var length = data[key].length;
		for(var i = 0; i < length; i++){
		//alert(JSON.stringify(data[key][0]["Rest_Coordinate_Long"]));
		var Rest_Name = JSON.stringify(data[key][i]["Rest_Name"]);
		var long = parseFloat(JSON.stringify(data[key][i]["Rest_Coordinate_Long"]));
		var lat = parseFloat(JSON.stringify(data[key][i]["Rest_Coordinate_Lat"]));
		addMarkertoMap({lat: lat, lng: long}, data, key, i, Rest_Name); 
		}
	} 
	console.log(data);

});


</script>
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