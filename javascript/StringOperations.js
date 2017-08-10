/**
 * Reverse a String
 */

/* ===== Using map to reverse a string ===== 
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/map#Using_map_to_reverse_a_string
 * 
 * ===== Reversing a String using split() =====
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/split#Reversing_a_String_using_split()
 * 
 * ===== ***** Easier way would be using Array.from() method. ***** =====
 * https://stackoverflow.com/a/36525647/5081877
 */

var str = '12321';
var reverse = Array.prototype.map.call(str, function(x) {
  return x;
}).reverse().join(''); 

var strReverse = str.split('').reverse().join('');
// split() returns an array on which reverse() and join() can be applied

var reverseStr = Array.from(str).reverse().join('');

console.log('Array.map.call « Str:', str, '\t Reverse:', reverse);
if( str == reverse ) console.log('Array.map.call « Both Strings are equal');
if( str === reverse ) console.log('Array.map.call « Palindrome String');

console.log('split « Str:', str, '\t Reverse:', strReverse );
if( str == strReverse ) console.log('split « Both Strings are equal');
if( str === strReverse ) console.log('split « Palindrome String');

console.log('Array.from « Str:', str, '\t Reverse:', reverseStr );
if( str == reverseStr ) console.log('Array.from « Both Strings are equal');
if( str === reverseStr ) console.log('Array.from « Palindrome String');

