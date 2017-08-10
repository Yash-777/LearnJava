/**
 * Walk Horizontal (Branch - by - Branch) through DOM.
 */

/* ===== Walk Horizontal (Branch - by - Branch) through DOM ===== */

var domTree = ['body > *'];  
var allelems = document.querySelectorAll( domTree );
console.log('The DOM(Document Object Model) represents a document as a tree.- Element and its children, \n', elementName_ID_CSS());

function elementName_ID_CSS() {
	
	var Array_like_to_array = [];
	for(i = 0, j = 0; i < allelems.length; i++) {
		//console.log(allelems[i].tagName , ' : ', allelems[i].children.length);
		var nodeinfo =  allelems[i].tagName;
		
		var id = Array.prototype.slice.call(allelems[i].id).join("");
		if(typeof(id) === 'string' && id.length != 0 ) nodeinfo = nodeinfo+'#'+id;
		
		var css = Array.prototype.slice.call(allelems[i].className).join("");
		css = css.replace(/\s/g, '.');
		if(typeof(css) === 'string' && css.length != 0 ) nodeinfo = nodeinfo+'.'+css;
		
		Array_like_to_array.push(nodeinfo);
	}
}

/*===================================================================================================================
Test URL - http://stackoverflow.com/a/26172084/5081877

https://developer.mozilla.org/en-US/docs/Web/API/NodeIterator
https://developer.mozilla.org/en-US/docs/Web/API/NodeFilter
*/
function htmlElemnt_Used_OverDOM( testNode, checkSpecificElement, css, className, id, idName ) {
	var nodeIterator;
	if ( checkSpecificElement ) {
		nodeIterator = document.createNodeIterator( document.documentElement, NodeFilter.SHOW_ELEMENT,
			function(node) {
				if( css )
					return node.classList.contains(className) ? NodeFilter.FILTER_ACCEPT : NodeFilter.FILTER_REJECT;
				if( id )
					return node.id === idName ? NodeFilter.FILTER_ACCEPT : NodeFilter.FILTER_REJECT;
				
				return node.nodeName.toLowerCase() === testNode ? NodeFilter.FILTER_ACCEPT : NodeFilter.FILTER_REJECT;
			}
		);
	} else {
		nodeIterator = document.createNodeIterator(document.documentElement, NodeFilter.SHOW_ELEMENT);
	}
	var pars = [];
	var currentNode;
	
	while (currentNode = nodeIterator.nextNode()) {
	  pars.push(currentNode);
	}
	return pars;
}

console.log('Element using NodeIterator');
console.log('Olny Div : ', htmlElemnt_Used_OverDOM('footer', true) );

console.log('Olny Div : ', htmlElemnt_Used_OverDOM('div', true) );
console.log('Div with calss : ', htmlElemnt_Used_OverDOM('div', true, true, 'answer') );
console.log('Div with ID : ', htmlElemnt_Used_OverDOM('div', true, false, '', true, 'answer-26172084') );

console.log('Any Element : ', htmlElemnt_Used_OverDOM('div', false) );

/*
====================================================================================
*/

var elements = document.getElementsByTagName( '*' );
console.log('L:', walkDOM(elements));
function walkDOM(main) {
    var arr = [];
    var loop = function(main) {
        do {
            if(main.nodeType == 1)
                arr.push(main);
            if(main.hasChildNodes()) // .parentNode.childNodes
                loop(main.firstChild);
        }
        while (main = main.nextSibling);
    }
    loop(main);
    return arr;
}

