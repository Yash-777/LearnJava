/**
 * https://developer.mozilla.org/en-US/docs/Introduction_to_using_XPath_in_JavaScript
 * XPath stands for XML Path Language.
 * It uses a non-XML syntax to provide a flexible way of addressing (pointing to) different parts of an XML document. 
 * 
 * XPath/locator is a way of addressing an element by navigating through the Document tree structure.
 * 
 *  The XPath language is based on a tree representation of the XML document,
 *  and provides the ability to navigate around the tree, selecting nodes by a variety of criteria.
 *  
XML Path Language (XPath) « https://github.com/ilinsky/jquery-xpath
	Version 1.0 « https://www.w3.org/TR/xpath/
	2.0 (Second Edition) « https://www.w3.org/TR/xpath20/

XPath Tester Online « document.evaluate( -- ) | $x( -- ) //https://stackoverflow.com/a/17082043/5081877
	« https://www.freeformatter.com/xpath-tester.html
	« http://videlibri.sourceforge.net/cgi-bin/xidelcgi

 * XPATH -https://stackoverflow.com/a/32201731/5081877
 *   Absolute XPath (/): /html/body/div[5]/div[4]
 *   Relative xpath (//): //div[@id=’social-media’]/ul/li[3]/a
 * 
 * CSS Path - https://stackoverflow.com/a/32306303/5081877
 * CSS locator work with simple attributes like ID and Class
 *   Full Path  - html body div.myclass a#myID
 *   Short Path - a#myID
 *   Nth Child CSS - http://stackoverflow.com/a/499845/5081877
 *     {"html:nth-child(2) > body:nth-child(2) > div:nth-child(2) > a#myID"}
 * 
 * Dimension Position - Coordinates, Width, Height - https://stackoverflow.com/a/32268701/5081877
 * https://stackoverflow.com/a/32201731/5081877
 */

/* ===== XPath of DOM Specified Elements ===== */
function getPaths() {

	var tagsCheck = ['HEAD', 'HEADER', 'FOOTER', 'BODY', 'FORM', 'DIV','Span', 'A', 'IFRAME', 'P', 'IMG', 
		'INPUT', 'BUTTON', 'OL', 'UL','TABLE', 'THEAD', 'TBODY', 'TFOOT', 'H1', 'H2', 'H3', 'H4', 'H5', 'H6'];

	var domTree = [];
	var allelems = document.querySelectorAll(tagsCheck );
	for(i = 0, j = 0; i < allelems.length; i++) { 

		var singleTag = [];
		
			var jsonData = getTagInfo(allelems[i]); 
			
			singleTag.push(getFullXPath(allelems[i]));
			singleTag.push(jsonData);
			
			/*setStyleOption( ele ); // For Input TAG.
			if( ele.type="text" ) sendKeys( ele, 'Yash' );*/
			
			domTree.push(singleTag);
	}
	return domTree;
}

function setStyleOption( htmlEle ) {
	htmlEle.style.setProperty( 'outline', "3px solid #45FF17","important");
}
function removeStyleOption( htmlEle ) {
	htmlEle.style.removeProperty("outline");
}

function sendKeys( htmlEle, value ) {
	htmlEle.value = value;
}

function getTagInfo(element){ 
	return '{ \"Xpath\": \"'+ getFullXPath(element) + '\" ,\"CSSpath\":  \"'+ getFullCSSPath(element) + 
	'\", \"left\": '+element.getBoundingClientRect().left+',\"top\":  '+element.getBoundingClientRect().top+
	',\"width\": '+element.offsetWidth+',\"height\" : '+element.offsetHeight+ 
	'}';
}

function getFullXPath(element) {
	if (element.tagName == 'HTML')    return '/html';
	if (element===document.body)      return '/html/body';
	
	var position = 0;
	var siblings = element.parentNode.childNodes;
	for (var i = 0; i < siblings.length; i++) {
		var sibling = siblings[i];
		if (sibling === element) { 
			var pos = '';
			if( position !== 0) pos += '['+(position+1)+']';
			return getFullXPath(element.parentNode)+'/'+element.tagName.toLowerCase()+pos;
		}
		var type = sibling.nodeType;
		if (type === 1 && sibling.tagName === element.tagName)            position++;
	}
}
function getFullXPathTree(element) {
	if (element.tagName == 'HTML')    return '/html';
	if (element===document.body)      return '/html/body';
	
	var position = 0;
	var order = '';
	
	var siblings = element.parentNode.childNodes;
	for (var i = 0; i < siblings.length; i++) {
		var sibling = siblings[i];
		
		if (sibling === element) { 
			var pos = '';
			var preNodes = '';
			if( position !== 0 ) pos += '['+(position+1)+']';
			if( order.length > 0 ) preNodes += '[ '+order+']';
			return getFullXPath(element.parentNode)+'/' + preNodes + element.tagName.toLowerCase() + pos;
		}else{
			var type = sibling.nodeType;
			if (type === 1 && sibling.tagName === element.tagName)  {      position++;   }
			else if(sibling.tagName !== element.tagName ){   
				var res = sibling.nodeName.substring(0,1);          
				if(res !== '#')    order += (sibling.nodeName.toLowerCase()+' ');
			}
		}
	}
}

function getFullCSSPath(element) {
	if (element.tagName == 'HTML')    return '';
	if (element===document.body)      return getShortCSSPath(element);
	
	
	var siblings = element.parentNode.childNodes;
	for (var i = 0; i < siblings.length; i++) {
		var sibling = siblings[i];
		if (sibling === element)    {
			var elemCssPath = getShortCSSPath(element);
			return getFullCSSPath(element.parentNode)+' '+elemCssPath; 
		}
	}
}
function getShortCSSPath(element) {
	var path = element.tagName.toLowerCase();
	if(element.id) {
		path += "#" + (element.id).replace(/\s/g,"#");	
	}		
	if(element.className) {
		path += "." + (element.className).replace(/\s/g,".");
	}
	
	return path;
}
var cssNthChildPath = function(el) {
	if (!(el instanceof Element)) return;
	var path = [];
	while (el.nodeType === Node.ELEMENT_NODE) {
		var selector = el.nodeName.toLowerCase();
		if (el.id) {
			selector += '#' + el.id;
		} else {
			var sib = el, nth = 1;
			while (sib.nodeType === Node.ELEMENT_NODE && (sib = sib.previousSibling) && nth++);
			selector += ":nth-child("+nth+")";
		}
		path.unshift(selector);
		el = el.parentNode;
	}
	return path.join(" > ");
}

cssNthChildPath ( document.getElementsByTagName('div')[20] );
