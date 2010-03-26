(function() {
	var port = chrome.extension.connect( {
		name : "clt"
	});
	/**
	 * Register mouse up event handler
	 */
	document.body.addEventListener('mouseup', function(evt) {
		var keys = [ 'Ctrl', 'Alt', 'Shift', 'Meta' ], hotkeys = [];
		for ( var k in keys) {
			if (evt[keys[k].toLowerCase() + 'Key']) {
				hotkeys.push(keys[k]);
			}
		}

		var text = window.getSelection().toString();
		if (text.length < 1) {
			return;
		}

		port.postMessage( {
			type : 'SelectTextMessage',
			text : text,
			keys : hotkeys,
			offsetX : evt.pageX,
			offsetY : evt.pageY
		});

	}, false);

	document.body.addEventListener('mousedown', function(evt) {
		var wrapper = document
				.getElementById('-chrome-clt-ext-dialog-wrapper-container');
		if (wrapper.style.display != 'none') {
			// TODO: need to be closed by close button
			// wrapper.style.display = 'none';
			// wrapper.innerHTML = '';
		}
	}, false);

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
	
	var assignLoadWordsClickHandler = function(){
		var btn = document.getElementById('cltLoadWords');
		btn
				.addEventListener(
						'click',
						function(evt) {
							port.postMessage( {
								type : 'LoadWordsMessage'
							});
						});
	};
	// add messaging listener handler
	chrome.extension.onRequest.addListener(connectionHandler);
}());

/**
 * 
 */
if (!document.getElementById('-chrome-clt-ext-dialog-wrapper-container')) {
	// create dialog
	(function() {
		var d = document.createElement('DIV');
		d.id = '-chrome-clt-ext-dialog-wrapper-container';
		d.style.display = 'none';
		d.setAttribute('style', 'display: none;' + 'opacity: 1 !important;'
				+ 'border-color: none !important;'
				+ 'background: transparent !important;'
				+ 'padding: 0 !important;' + 'margin: 0 !important;'
				+ 'position: absolute !important;' + 'top: 0;' + 'left: 0;'
				+ 'overflow: visible !important;'
				+ 'z-index: 999999 !important;'
				+ 'text-align: left !important;');
		document.body.appendChild(d);
	})();
}
// timer
(function() {
	var Timer = function() {
		this.secs; 
		this.timerID = null;
		this.timerRunning = false;
		this.delay = 1000;

		this.initializeTimer = function() {
			// Set the length of the timer, in seconds
			this.secs = 10;
			this.stop();
			this.start();
		};

		this.stop = function() {
			if (this.timerRunning);
				clearTimeout(this.timerID);
			this.timerRunning = false;
		};

		this.start = function() {
			var self = this;
			if (this.secs == 0) {
				this.stop();
				// Here's where you put something useful that's
				// supposed to happen after the allotted time.
				// For example, you could display a message:
				alert("You have just wasted 10 seconds of your life.");
			} else {
				self.status = this.secs;
				secs = secs - 1;
				timerRunning = true;
				timerID = self.setTimeout("start()", this.delay);
			}
		};
	};
}());