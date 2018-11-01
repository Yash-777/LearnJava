/**
 * https://developer.mozilla.org/en-US/docs/Web/API/EventTarget/addEventListener
 * 
 * 
 */
// Remove all event handlers from the element and its children.[node + subtree].

var ele = document.getElementById('yash777');

// Cloning Element « Node with static data, node's subtree is small.
// cloning the node is slower than just removing the listeners
function CloningElement( ele ) {
	var old_element = ele;
	var new_element = old_element.cloneNode(true);
	old_element.parentNode.replaceChild(new_element, old_element);
}