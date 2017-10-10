/**
 * Load list of Scripts and StyleSheets to HTML.
 *   https://togetherjs.com/togetherjs.js
 *   https://stackoverflow.com/a/32143855/5081877
 * 
 * Adding Element to an element whose XPath is provided.
 *   https://stackoverflow.com/a/33669996/5081877
 */
function removeTagAttibute( ) {
	console.log('Tab Index');
	var allTags = '*';
	
	var allelems = document.querySelectorAll( allTags );
	for(i = 0, j = 0; i < allelems.length; i++) { 
		allelems[i].removeAttribute( 'tabindex' );
	}
}

function insertHTML_ByXPath( xpath, position, newElement) {
	var element = document.evaluate(xpath, window.document, null, 9, null ).singleNodeValue;
	element.insertAdjacentHTML(position, newElement);
	element.style='border:3px solid orange';
}

var xpath_DOMElement = '//*[@id="answer-33669996"]/table/tbody/tr[1]/td[2]/div';
var childHTML = 
	'<div id="Yash">Hi My name is <B>\"YASHWANTH\"</B></div>';

var divEle = document.createElement("div"); // Create li element.
divEle.innerHTML = "Hi My name is <B>\"YASHWANTH\"</B>";
var newElemAsString = new XMLSerializer().serializeToString(divEle);

var position = 'beforeend';
insertHTML_ByXPath(xpath_DOMElement, position, childHTML);

/* ===== Script, StyleSheets ===== */
var scriptFiles = [];
scriptFiles.push("https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js");
scriptFiles.push("https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0-beta.2/angular.js");
scriptFiles.push("https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js");

for(serverJS of scriptFiles) { // in - indexes, of - values
	var getHeadTag		=	document.getElementsByTagName('head')[0];
	var newScriptTag	=	document.createElement('script');
	newScriptTag.type	=	'text/javascript';
	newScriptTag.src	=	serverJS;
	getHeadTag.appendChild(newScriptTag); // adding <script> to the end of <head> tag.
}

var styleSheet = [];
styleSheet.push("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css");
styleSheet.push("https://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css");
styleSheet.push("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css");
styleSheet.push("https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker3.standalone.min.css");
for(serverCSS of styleSheet) { // in - indexes, of - values
	var getHeadTag		=	document.getElementsByTagName('head')[0];
	var newLinkTag	=	document.createElement('link'); 
	newLinkTag.type	=	'text/css';
	newLinkTag.rel	=	'stylesheet';
	newLinkTag.href	=	serverCSS;
	getHeadTag.appendChild(newLinkTag); // adding <link> to the end of <head> tag.
}

