/**
https://stackoverflow.com/a/19483767 - http://jsobjects.org/

A modern, open source text editor - http://brackets.io/

 * https://babeljs.io/repl/#
 * https://es6console.com/

Object-Oriented JavaScript — A Deep Dive into ES6 Classes
Class:1 Uncaught TypeError: Identifier 'Rectangle' has already been declared

https://www.sitepoint.com/object-oriented-javascript-deep-dive-es6-classes/
https://stackoverflow.com/questions/12018759/how-to-check-the-class-of-an-instance-in-javascript

if (typeof obj !== 'undefined' && typeof obj === 'function') { ... }
function isClass(v) { return typeof v === 'function' && v.prototype.constructor === v; }

https://stackoverflow.com/questions/11970141/javascript-whats-the-difference-between-a-function-and-a-class
 */

// Class can be declared only once and used to create multiple instances of it.
Object.prototype.class = function(){
	return this.constructor.name;
}
'use strict';

var Shape = function ( superClass ) {
	var currentClass = Shape;
	_inherits(currentClass, superClass); // Prototype Chain - Extends
	
	function Shape(id) { superClass.call(this); // Linking with SuperClass Constructor.
		// Instance Variables list.
		this.id = id;	return this;
	}
	var staticVariablesJOSN = { "parent_S_V" : 777 };
	staticVariable( currentClass, staticVariablesJOSN );
	
	// Setters, Getters, instanceMethods. [{}, {}];
	var instanceFunctions = [
		{
			key: 'uniqueID',
			get: function get() { return this.id; },
			set: function set(changeVal) { this.id = changeVal; }
		}
	];
	instanceMethods( currentClass, instanceFunctions );
	
	return currentClass;
}(Object);

var Rectangle = function ( superClass ) {
	var currentClass = Rectangle;
	
	_inherits(currentClass, superClass);

	function Rectangle(id, width, height) { superClass.call(this, id);

		this.width = width;
		this.height = height;	return this;
	}

	var staticVariablesJOSN = { "_staticVar" : 77777 };
	staticVariable( currentClass, staticVariablesJOSN );

	var staticFunctions = [
		{
			key: 'println',
			value: function println() { console.log('Static Method'); }
		}
	];
	staticMethods(currentClass, staticFunctions);
	
	var instanceFunctions = [
		{
			key: 'setStaticVar',
			value: function setStaticVar(staticVal) {
				currentClass.parent_S_V = staticVal;
				console.log('SET Instance Method Parent Class Static Value : ', currentClass.parent_S_V);
			}
		}, {
			key: 'getStaticVar',
			value: function getStaticVar() {
				console.log('GET Instance Method Parent Class Static Value : ', currentClass.parent_S_V);
				return currentClass.parent_S_V;
			}
		}, {
			key: 'area',
			get: function get() {
				console.log('Area : ', this.width * this.height);
				return this.width * this.height;
				}
		}, {
			key: 'globalValue',
			get: function get() {
				console.log('GET ID : ', currentClass._staticVar);
				return currentClass._staticVar;
			},
			set: function set(value) {
				currentClass._staticVar = value;
				console.log('SET ID : ', currentClass._staticVar);
			}
		}
	];
	instanceMethods( currentClass, instanceFunctions );
	
	return currentClass;
}(Shape);

	// ===== ES5 Class Conversion Supported Functions =====
	function defineProperties(target, props) {
		console.log(target, ' : ', props);
		for (var i = 0; i < props.length; i++) {
			var descriptor = props[i];
			descriptor.enumerable = descriptor.enumerable || false;
			descriptor.configurable = true;
			if ("value" in descriptor) descriptor.writable = true;
			Object.defineProperty(target, descriptor.key, descriptor);
		}
	}
	function staticMethods( currentClass, staticProps ) {
		defineProperties(currentClass, staticProps);
	};
	function instanceMethods( currentClass, protoProps ) {
		defineProperties(currentClass.prototype, protoProps);
	};
	function staticVariable( currentClass, staticVariales ) {
		// Get Key Set and get its corresponding value.
		// currentClass.key = value;
		for( var prop in staticVariales ) {
			console.log('Keys : Values');
			if( staticVariales.hasOwnProperty( prop ) ) {
				console.log(prop, ' : ', staticVariales[ prop ] );
				currentClass[ prop ] = staticVariales[ prop ];
			}
		}
	};
	function _inherits(subClass, superClass) {
		console.log( subClass, ' : extends : ', superClass );
		if (typeof superClass !== "function" && superClass !== null) {
			throw new TypeError("Super expression must either be null or a function, not " + typeof superClass);
		}
		subClass.prototype = Object.create(superClass && superClass.prototype, 
				{ constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });
		if (superClass)
			Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

var objTest = new Rectangle('Yash_777', 8, 7);
console.dir(objTest);

// ===== ES6 - Prototype Inheritance =====
class Shape {
	constructor (id) {
		this.id = id
	}
	
	get uniqueID() { return this.id; }
	set uniqueID( changeVal ) { this.id = changeVal; }
}
Shape.parent_S_V = 777;

// Class Inheritance
class Rectangle extends Shape {
	
	constructor (id, width, height) {
		super(id)
		this.width  = width
		this.height = height
	}
	// Duplicate constructor in the same class are not allowed.
	/*constructor (width, height) { this._width  = width; this._height = height; }*/

	get globalValue() { console.log('GET ID : ', Rectangle._staticVar); return Rectangle._staticVar; }
	set globalValue(value) { Rectangle._staticVar = value; console.log('SET ID : ', Rectangle._staticVar); }
	
	static println() {
		console.log('Static Method');
	}
	
	// this.constructor.parent_S_V - Static property can be accessed by it's instances
	setStaticVar(staticVal) { // https://sckoverflow.com/a/42853205/5081877
		Rectangle.parent_S_V = staticVal;
		console.log('SET Instance Method Parent Class Static Value : ', Rectangle.parent_S_V);
	}
	
	getStaticVar(){
		console.log('GET Instance Method Parent Class Static Value : ', Rectangle.parent_S_V);
		return Rectangle.parent_S_V;
	}
}
Rectangle._staticVar = 77777;

// =====  TEST INHERITACE, INSTANCE, STATIC DATA =====
var objTest = new Rectangle('Yash_777', 8, 7);
console.dir(objTest);

var obj1 = new Rectangle('R_1', 50, 20);
Rectangle.println(); // Static Method
console.log( obj1 );    // Rectangle {id: "R_1", width: 50, height: 20}
obj1.area;              // Area :  1000
obj1.globalValue;       // GET ID :  77777
obj1.globalValue = 88;  // SET ID :  88
obj1.globalValue;       // GET ID :  88  

var obj2 = new Rectangle('R_2', 5, 70);
console.log( obj2 );    // Rectangle {id: "R_2", width: 5, height: 70}
obj2.area;              // Area :  350    
obj2.globalValue;       // GET ID :  88
obj2.globalValue = 999; // SET ID :  999
obj2.globalValue;       // GET ID :  999

console.log('Static Variable Actions.');
obj1.globalValue;        // GET ID :  999

console.log('Parent Class Static variables');
obj1.getStaticVar();    // GET Instance Method Parent Class Static Value :  777
obj1.setStaticVar(7);   // SET Instance Method Parent Class Static Value :  7
obj1.getStaticVar();    // GET Instance Method Parent Class Static Value :  7

