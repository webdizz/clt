var CltTimer = function() {
	// Property: Frequency of elapse event of the timer in millisecond
	this.interval = 1000;

	// Property: Whether the timer is enable or not
	this.enable = new Boolean(false);

	// Event: Timer tick
	this.tick;

	// Member variable: Hold interval id of the timer
	var timerId = 0;

	// Member variable: Hold instance of this class
	var thisObject;

	// Function: Start the timer
	this.start = function() {
		this.enable = new Boolean(true);

		thisObject = this;
		if (thisObject.enable) {
			thisObject.timerId = setInterval(function() {
				thisObject.tick();
			}, thisObject.interval);
		}
	};

	// Function: Stops the timer
	this.stop = function() {
		thisObject.enable = new Boolean(false);
		clearInterval(thisObject.timerId);
	};
};

(function() {
	var port = chrome.extension.connect( {
		name : "clt"
	});
	
	var connectionHandler = function(m) {
		var wrapper = document
				.getElementById('-chrome-clt-ext-dialog-wrapper-container');
		switch (m.type) {
		case 'ShowTranslatedTextMessage':
			wrapper.style.display = '';
			wrapper.innerHTML = jsonParse(m.widget);
			wrapper.style['border-color'] = 'red';
			performPositioning(wrapper, m.message);
			assignStoreTranslationClickHandler();
			assignLoadWordsClickHandler();
			break;
		case 'ShowWordMessage':
			alert(m.word);
			break;
		}
	};

	var performPositioning = function(elem, message) {
		var offset = 120;
		// alert(message.offsetY +'-'+document.body.scrollTop+'
		// '+elem.offsetHeight);
		if (message.offsetY - document.body.scrollTop >= elem.offsetHeight) {
			var height = (message.offsetY - 40) + 'px';
			elem.style.top = height;
		} else {
			var height = (message.offsetY + 200) + 'px';
			elem.style.top = height;
		}
		elem.style.left = message.offsetX + 40 + 'px';
	};

	var assignStoreTranslationClickHandler = function() {
		var btn = document.getElementById('cltStoreWord');
		btn
				.addEventListener(
						'click',
						function(evt) {
							var data = document
									.getElementById('cltTranslationHolder').childNodes[1];
							port.postMessage( {
								type : 'StoreTranslationMessage',
								translation : data.getAttribute('translation'),
								translateable : data
										.getAttribute('translateable')
							});
						});
	};
	//start timer to load words periodicaly
	var cltTimer = new CltTimer();
	cltTimer.interval = 60000;
	cltTimer.tick = function(){
		port.postMessage( {
			type : 'LoadWordsMessage'
		});
	};
	//cltTimer.start();
}());
