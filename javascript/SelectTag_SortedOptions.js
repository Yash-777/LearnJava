/**
 * A jQuery Select Box Plugin for Mobile, Tablet, and Desktop.
 * « http://gregfranko.com/jquery.selectBoxIt.js/#SetSize
 * [
 * http://gregfranko.com/jquery.selectBoxIt.js/css/jquery.selectBoxIt.css
 * http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js
 * http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js
 * http://gregfranko.com/jquery.selectBoxIt.js/js/jquery.selectBoxIt.min.js
 * ]
 * 
 * Mouse Event Responsive Move DIV tag « http://api.jqueryui.com/position/
 */

var elementOptions = ['Button','CheckBox','DropDown','Image','Label','Link','Menu','SubMenu',
	'RadioButton','Table','TextBox', 'Navigate_Forward', 'Navigate_Backward', 'Accordian'];
//console.log( elementOptions.join() );
elementOptions.sort();
elementOptions.splice(0, 0, ' -- Select -- ');
//console.log( elementOptions );
function selectOptions() {
	var selectString = "";
	try {
		for ( var i = 0; i < elementOptions.length ; i++ ) {
			selectString = selectString+"<option value ='"+elementOptions[i]+"'>"+elementOptions[i]+"</option>";
		}
	} catch (e) {	}
	return selectString;
}
/*
<select id='options777' value=' -- Select -- ' onchange='generatePath(this)' style='"+inlineSelectCss+"'>
selectOptions()
</select>
*/

var isForward = false, isBackward = false;
function generatePath() {
	var type = window.document.getElementById('options777');
	var optionValue = type.options[ type.selectedIndex ].text;
	console.log('Option Value : ', optionValue);
	
	if( optionValue == 'Navigate_Forward' || optionValue == 'Navigate_Backward' ) {
		
		var target = window.document.getElementById('spy_Target');
		if( optionValue == 'NavigateBack' ) { // window.history.go(-1);
			isBackward = true;
			target.value = "I want to Navigate Back";
		} else if( optionValue == 'NavigateFront' ) { // window.history.go(1);
			isForward = true;
			target.value = "I want to Navigate Fornt";
		}
	}
}

/* Angular JS - http://tutlane.com/example/angularjs/angularjs-dropdown-validation-example */


