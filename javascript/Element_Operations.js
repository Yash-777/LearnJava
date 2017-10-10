/**
 * https://stackoverflow.com/a/9432023/5081877
 * https://developer.mozilla.org/en-US/docs/Web/API/Window/getComputedStyle
 * 
 * Element add or remove class « https://stackoverflow.com/a/7487686/5081877
 * https://developer.mozilla.org/en-US/docs/Web/API/Element/classList
 * https://jaketrent.com/post/addremove-classes-raw-javascript/
 */
var locator = '//*[@id="main"]/div[1]/div[3]';
var elem = document.evaluate( locator, window.document, null, 9, null ).singleNodeValue;
console.log( elem );

getAllStyles( elem );

function getStyleById(id) {
    return getAllStyles(document.getElementById(id));
}
function getAllStyles(elem) {
    if (!elem) return []; // Element does not exist, empty list.
    var win = document.defaultView || window, style, styleNode = [];
    if (win.getComputedStyle) { /* Modern browsers */
        style = win.getComputedStyle(elem, '');
        for (var i=0; i<style.length; i++) {
            styleNode.push( style[i] + ':' + style.getPropertyValue(style[i]) );
            //               ^name ^           ^ value ^
        }
    } else if (elem.currentStyle) { /* IE */
        style = elem.currentStyle;
        for (var name in style) {
            styleNode.push( name + ':' + style[name] );
        }
    } else { /* Ancient browser..*/
        style = elem.style;
        for (var i=0; i<style.length; i++) {
            styleNode.push( style[i] + ':' + style[style[i]] );
        }
    }
    return styleNode;
}