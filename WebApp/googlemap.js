//google example


var map;

function initMap() {
	 var Helser = {lat: 42.02424, lng: -93.65183789999998};
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 15,
          center: Helser
        });

	
	
	google.maps.event.addListener(map, 'bounds_changed', function() {
		console.log("hi");
    });
	

    
}	


function setSessionStorage(Rest_Name) {

	
	var Rest_ID =  sessionStorage.getItem(Rest_Name);
	sessionStorage.setItem('Restaurant', Rest_Name);
	window.location.href = "OrderFood.php?Rest_ID="+Rest_ID;
	}
	


function addMarkertoMap(coords, data, key, i, Rest_Name, id) {
	console.log(Rest_Name);
	var length = Rest_Name.length;
	var rest = Rest_Name.toString();
	var length = rest.length;
	rest = rest.substring(1, length-1);
	console.log(rest);
		var marker = new google.maps.Marker({
		position: coords,
		map:map
		
	
	
	});
	var infoWindow = new google.maps.InfoWindow({
		content: '<a onclick="setSessionStorage(\'' + rest + '\')"/>' + JSON.stringify(data[key][i]["Rest_Name"]) + '</a>'

		
	});
	sessionStorage.setItem(rest , JSON.stringify(data[key][i]["Rest_ID"]));
	google.maps.event.addListener(marker, 'click', function(){
		infoWindow.open(map,marker);

	
	
	});

}

