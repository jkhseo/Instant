//google example
var map;

function initMap() {
	 var Helser = {lat: 42.02424, lng: -93.65183789999998};
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 15,
          center: Helser
        });
	addMarkertoMap({lat: 42.02424, lng: -93.65183789999998}); 
	
	
	google.maps.event.addListener(map, 'bounds_changed', function() {

    });
	

    
}	



function addMarkertoMap(coords) {

	var marker = new google.maps.Marker({
		position: coords,
		map:map
		
	
	
	});


}

