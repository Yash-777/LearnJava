/**
 * http://usejsdoc.org/
 */

/* JSON is a more lightweight plain-text alternative to XML which is also commonly used for client-server 
 * communication in web applications. JSON is based on JavaScript syntax, but is supported in other programming 
 * languages as well.
 * 
 * Prototype Inheritance:A prototype is a working object instance.
 * Objects inherit directly from other objects.
 * */

Object.prototype.getKeyByValue = function( value ) {
    for( var prop in this ) {
        if( this.hasOwnProperty( prop ) ) {
             if( this[ prop ] === value )
                 return prop;
        }
    }
}
Object.prototype.getValueByKey = function( key ) {
    for( var prop in this ) {
        if( this.hasOwnProperty( prop ) ) {
             if( prop === key )
                 return this[ prop ];
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
