/**
 * The factorial of a positive integer n is equal to 1*2*3*...n. 
 * You will learn to calculate the factorial of a number using for loop in this example.
 * 
 * Tail Calls « http://babeljs.io/learn-es2015/#ecmascript-2015-features-tail-calls
Calls in tail-position are guaranteed to not grow the stack unboundedly.
Makes recursive algorithms safe in the face of unbounded inputs.

 */
function factorial(n) {
    var acc = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 1;

    if (n <= 1) {
        return acc;
    }
    return factorial(n - 1, n * acc);
}

factorial( 4 );

var factNum = function factNum(param) {

var acc = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 1;
console.log(param, 'Call Back Function', acc);
  param < 1 ? acc : factNum(param - 1, param * acc);
};

var res = factNum(4);
console.log('Factorial of Number : ', res );