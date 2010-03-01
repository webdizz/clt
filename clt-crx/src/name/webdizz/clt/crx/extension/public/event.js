/**
*  Register mouse up event handler 
*/
document.body.addEventListener('mouseup', function(evt) {
	var keys = ['Ctrl', 'Alt', 'Shift', 'Meta'],
		hotkeys = [];
	for(var k in keys) {
		if(evt[keys[k].toLowerCase() + 'Key']) {
			hotkeys.push(keys[k]);
		}
	}

	var text = window.getSelection().toString();
	if(text.length < 1) {
		return;
	}

	var port = chrome.extension.connect();
	port.postMessage({
		type: 'SelectTextMessage',
		text: text,
		keys: hotkeys
	});

}, false);

// register handler for show translation
/*new function() {
    var port = chrome.extension.connect();
    port.onMessage.addListener(function(m) {
        switch(m.type) {
            case 'ShowTranslatedTextMessage':
            	alert('I\'m herer');
        	break;
        }
    };
};*/