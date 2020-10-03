// pokemon json processor

function getAllMoves() {
	for(var x=1; x<=796; x++){
		sendRequest(x);
	}
}

function sendRequest(number){
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		// 	Check if request is completed
		if (xhr.readyState == XMLHttpRequest.DONE) {
			//	Do what needs to be done here
			var body = JSON.parse(xhr.response);
			
			var accuracy = body.accuracy;
			var points = body.power;
			var movementType = body.damage_class.name;
			var id = body.id;
			var name = body.name;
			var priority = body.priority;
			var type = body.type.name;
			
			body = JSON.stringify(body);
			
			var query = "insert into movement(id, name, type, movement_type, points, accuracy, priority) values ("+id+",'"+name+"','"+type+"','"+movementType+"','"+points+"','"+accuracy+"',"+priority+");</br>";
			query = query.replaceAll("'null'","null");
			
			document.querySelector("body").innerHTML = document.querySelector("body").innerHTML+query;
		}
	}

	// Set the request URL and request method
	xhr.open("GET", "https://pokeapi.co/api/v2/move/"+number);

	// Set the `Content-Type` Request header
	xhr.setRequestHeader("Content-Type", "application/json");

	// Send the requst with Data
	xhr.send();
}