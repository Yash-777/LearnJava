console.log('Input Elements : ', getPaths() );

function getPaths() {

	var tagsCheck = [ 'INPUT', 'A', 'BUTTON' ];

	var domTree = [];
	var allelems = document.querySelectorAll(tagsCheck );
	for(i = 0, j = 0; i < allelems.length; i++) { 
		var ele = allelems[i];
		var singleTag = [];
		
			var jsonData = getTagInfo( ele ); 
			
			singleTag.push(getFullXPath( ele ));
			singleTag.push(jsonData);
			
			setStyleOption( ele );
			if( ele.type="text" ) sendKeys( ele, 'Yash' );
			
			domTree.push(singleTag);
	}
	return domTree;
}

/*myFun = function (event) {
 	e = event.target || event.srcElement || event.originalTarget;
	if(e.tagName == 'SVG' || e.tagName == "svg") {
		var attributeList = e.attributes;
		var mapType = attributeList.getNamedItem('viewBox');
		var valueSVG = mapType.value;
		console.log('SVG : ', valueSVG);
	}
}*/

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
function spyPath( mouseEvent ) {
	var tagIndex = null;
	var spyEdditor_XPath = ''; // Global Variable
	var window_size = getWindowSize();
	try{ 
		// e - eventTag
		e = mouseEvent.target || mouseEvent.srcElement || mouseEvent.originalTarget;
		spyEdditor_XPathEle = e.outerHTML;
	
		for (var path = ''; e && e.nodeType == 1; e = e.parentNode) {
			var currentTagName = e.tagName || e.nodeName || e.localName;
			currentTagName = currentTagName.toUpperCase();
			
			var siblings = e.parentNode.children; // Same Tree level Elements
			var sameTagCount = 0;
			for ( var i = 0; siblings && ( i < siblings.length ); i++ ) {
				
				if( siblings[i].tagName == currentTagName ) {
					
					sameTagCount++;
					if ( siblings[i] == e ) { // Found Exact Element
						tagIndex = sameTagCount;
					}
				}
			}
			
			if( currentTagName == 'BODY' || currentTagName == 'HTML' ) {
				tagIndex = null;
			}
			
			// Get Current Tag Text and replace special characters.
			var text = e.textContent;
			var tagText = e.textContent.trim();
			console.log("textContent:[", str, "]");
			if( (currentTagName == 'A' || currentTagName == 'BUTTON') && (tagText && tagText.length <= 25) && path == '' ) { // 'A', 'SPAN'
				if ( tagText == text ) {
					tagText = text.replace(/&amp;/g,'&').trim();
					path = '/' + currentTagName.toLowerCase() + "[.=\""+tagText+"\"]";
				} else {
					tagText = text.replace(/&amp;/g,'&').trim();
					path = '/' + currentTagName.toLowerCase() +"[normalize-space(.) = '" + tagText + "']";
				}
			} else if(currentTagName == 'SVG') {
				// https://wetransfer.com/sign-in « Got Plus? « Close panel(SVG)
				path = "/*[name()='svg']" + "["+tagIndex+"]";
				console.log('SVG ELEMENT : ', path);	
			} else {
				tagIndex = ( tagIndex ) ? ('[' + tagIndex + ']') : '';
				path='/' + currentTagName.toLowerCase() + tagIndex + path;
			}
			
			var uniquePathCount = 0;
			uniquePathCount = window.document.evaluate('count(/'+path+')', window.document, null, 0, null);
			
			// We are going to break the loop if we find Unique Locator in DOM.
			if( uniquePathCount.numberValue == 1 ) {
				path  = '/'+path;
				spyEdditor_XPath = path;
				break; // To Break for loop.
			}
		}
	} catch(e) {
		spy_Status = 'Exception in spyPath : '+ e;
	}
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