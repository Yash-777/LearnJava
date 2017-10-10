/* JSON is a more lightweight plain-text alternative to XML which is also commonly used for client-server 
 * communication in web applications. JSON is based on JavaScript syntax, but is supported in other programming 
 * languages as well.
 * 
 * https://stackoverflow.com/a/45592715/5081877
 * 
 * Prototype Inheritance:A prototype is a working object instance.
 * Objects inherit directly from other objects.
 * */

Object.prototype.getKeyByValue = function( value ) {
	for( var prop in this ) {
		if( this.hasOwnProperty( prop ) ) {
			if( this[ prop ] === value ) return prop;
		}
	}
}
Object.prototype.getValueByKey = function( key ) {
	for( var prop in this ) {
		if( this.hasOwnProperty( prop ) ) {
			if( prop === key ) return this[ prop ];
		}
	}
}
function listObjectKey_Values( JSONObject ) {
	// Get Key Set and get its corresponding value.
	for( var prop in JSONObject ) {
		console.log('Keys : Values');
		if( JSONObject.hasOwnProperty( prop ) ) {
			console.log(prop, ' : ', JSONObject[ prop ] );
			//currentClass[ prop ] = staticVariales[ prop ];
		}
	}
}

var test = {
	age: 26,
	name: 'Yash'
};

console.log("getKeyByValue : ", test.getKeyByValue( 26 ) );
console.log("getValueByKey : ", test.getValueByKey( 'name' ) );
console.log("Normal With out prototype : ", test( 'name' ) );

/* ===== MAP ===== 
ES6-Mapify : Convert JS Objects to ES6 Maps and vice versa.

MAP - https://appendto.com/2016/07/es5-objects-vs-es6-maps-the-differences-and-similarities/
Object Property Assignment - http://es6-features.org/#ObjectPropertyAssignment
 * */
var map = new Map();
	map.set("Key1", "Val1");
	map.set("Key2", "Val2");
	
var json = {"Key3":"V3", "k4":"V4"};
console.log( json );

let arrMap = mapify([1, {foo: 'bar'}, 3]);
arrMap[2];            // 3
arrMap[1].get('foo'); // 'bar'
console.log( arrMap );

let myMap = mapify({foo: {bar: 'baz'}});
myMap.get('foo').get('bar'); // 'baz';
console.log( myMap );

map.set("map1", json);
map.set("mapify", mapify( json ) );
console.log( map );

// https://github.com/jlipps/mapify/blob/master/lib/es6/main.js
function mapify (obj) {
	let m = new Map();
	if (typeof obj !== 'object' || obj === null) {
		return obj;
	}
	if (obj instanceof Array) {
		let newArr = [];
		for (let x of obj) {
			newArr.push(mapify(x));
		}
		return newArr;
	}
	for (let k in obj) {
		if (obj.hasOwnProperty(k)) {
			m.set(k, mapify(obj[k]));
		}
	};
	return m;
}

function demapify (map) {
	if (map instanceof Array) {
		let newArr = [];
		for (let x of map) {
			newArr.push(demapify(x));
		}
		return newArr;
	} else if (!(map instanceof Map)) {
		return map;
	}
	let obj = {};
	for (let [k, v] of map) {
		obj[k] = demapify(v);
	}
	return obj;
};