// pokemon json processor
//807

var timeout = 0;
var indice = 0;

function getAllPokemons() {
	for (var x=1; x<=807; x++){
		sendPokeRequest(x);
	}
}

function sendPokeRequest(number){
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		// 	Check if request is completed
		if (xhr.readyState == XMLHttpRequest.DONE) {
			//	Do what needs to be done here
			var body = JSON.parse(xhr.response);
			
			delete body["sprites"];
			delete body["moves"];
			
			var id = body.id;
			var name = body.name;
			var hp = body.stats[0].base_stat;
			var attack = body.stats[1].base_stat;
			var defense = body.stats[2].base_stat;
			var spAttack = body.stats[3].base_stat;
			var spDefense = body.stats[4].base_stat;
			var speed = body.stats[5].base_stat;
			
			var type1 = null;
			var type2 = null;
			var ability1 = null;
			var ability2 = null;
			
			if(body.types.length==2) {
				type1 = body.types[0].type.name;
				type2 = body.types[1].type.name;
			} else {
				type1 = body.types[0].type.name;
				type2 = null;
			}
			
			if(body.abilities.length==2) {
				ability1 = body.abilities[0].ability.name;
				ability2 = body.abilities[1].ability.name;
			} else {
				ability1 = body.abilities[0].ability.name;
				ability2 = null;
			}
			
			var query = "insert into pokemon(id, name, ability_1, ability_2, type_1, type_2, hp, attack, defense, sp_attack, sp_defense, speed) values ("+id+",'"+name+"','"+ability1+"','"+ability2+"','"+type1+"','"+type2+"',"+hp+","+attack+","+defense+","+spAttack+","+spDefense+","+speed+");</br>";
			query = query.replaceAll("'null'","null");
			
			document.querySelector("body").innerHTML = document.querySelector("body").innerHTML+query;
		}
	}

	// Set the request URL and request method
	xhr.open("GET", "https://pokeapi.co/api/v2/pokemon/"+number);

	// Set the `Content-Type` Request header
	xhr.setRequestHeader("Content-Type", "application/json");

	// Send the requst with Data
	xhr.send();
}