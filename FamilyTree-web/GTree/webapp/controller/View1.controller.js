sap.ui.define([
	'jquery.sap.global',
	'sap/ui/core/mvc/Controller',
	'sap/m/Popover',
	'sap/m/Button',
	"sap/ui/model/json/JSONModel"
], function(jQuery, Controller, Popover, Dialog, List, StandardListItem, Button, JSONModel) {
	"use strict";

	var _oView;
	var _oController;

	var data = [{
			"id": 1,
			"name": "Me",
			"children": [4],
			"partners": [2, 3],
			root: true,
			level: 0,
			"parents": [8, 9]
		}, {
			"id": 2,
			"name": "Mistress",
			"children": [4],
			"partners": [1],
			level: 0,
			"parents": []
		}, {
			"id": 3,
			"name": "Wife",
			"children": [5],
			"partners": [1],
			level: 0,
			"parents": []
		}, {
			"id": 4,
			"name": "Son",
			"children": [],
			"partners": [],
			level: -1,
			"parents": [1, 2]
		}, {
			"id": 5,
			"name": "Daughter",
			"children": [7],
			"partners": [6],
			level: -1,
			"parents": [1, 3]
		}, {
			"id": 6,
			"name": "Boyfriend",
			"children": [7],
			"partners": [5],
			level: -1,
			"parents": []
		}, {
			"id": 7,
			"name": "Son Last",
			"children": [],
			"partners": [],
			level: -2,
			"parents": [5, 6]
		}, {
			"id": 8,
			"name": "Jeff",
			"children": [1],
			"partners": [9],
			level: 1,
			"parents": [10, 11]
		}, {
			"id": 9,
			"name": "Maggie",
			"children": [1],
			"partners": [8],
			level: 1,
			"parents": [13, 14]
		}, {
			"id": 10,
			"name": "Bob",
			"children": [8],
			"partners": [11],
			level: 2,
			"parents": [12]
		}, {
			"id": 11,
			"name": "Mary",
			"children": [],
			"partners": [10],
			level: 2,
			"parents": []
		}, {
			"id": 12,
			"name": "John",
			"children": [10],
			"partners": [],
			level: 3,
			"parents": []
		}, {
			"id": 13,
			"name": "Robert",
			"children": [9],
			"partners": [14],
			level: 2,
			"parents": []
		}, {
			"id": 14,
			"name": "Jessie",
			"children": [9],
			"partners": [13],
			level: 2,
			"parents": [15, 16]
		}, {
			"id": 15,
			"name": "Raymond",
			"children": [14],
			"partners": [16],
			level: 3,
			"parents": []
		}, {
			"id": 16,
			"name": "Betty",
			"children": [14],
			"partners": [15],
			level: 3,
			"parents": []
		},{
			"id": 17,
			"name": "Pat",
			"children": [18],
			"partners": [],
			level: -3,
			"parents": [7]
		},{
			"id": 18,
			"name": "Bet",
			"children": [19],
			"partners": [],
			level: -4,
			"parents": [17]
		},{
			"id": 19,
			"name": "Ty",
			"children": [],
			"partners": [],
			level: -5,
			"parents": [18]
		} ],
		elements = [],
		root, levels = [],
		levelMap = [],
		tree,
		startTop, startLeft, gap = 32,
		size = 64;

	return Controller.extend("sap.m.sample.Popover.C", {
		
		 updateRedrawInfo: function(sHtmlId) {
				var oDomRef = jQuery("#" + sHtmlId)[0];
				var id = undefined;
				if ( oDomRef ) {
					jQuery.data(oDomRef);
					id = oDomRef[jQuery.expando] || oDomRef.uniqueID || id;
				}
				jQuery("#redrawInfo-" + sHtmlId + "-date").text(""+new Date()); 
				jQuery("#redrawInfo-" + sHtmlId + "-node").text(id);
			},
		
		onInit: function() {
			_oView = this.getView();
			_oController = this;
			
			_oController.updateRedrawInfo(tree);
		},
		
		onAfterRendering: function() {
			tree = _oView.byId('tree');

			startTop = (window.innerHeight / 2) - (size / 2);
			startLeft = (window.innerWidth / 2) - (size / 2);

			data.forEach(function(elem) {
				if (levels.indexOf(elem.level) < 0) {
					levels.push(elem.level);
				}
			});
			levels.sort(function(a, b) {
				return a - b;
			});

			levels.forEach(function(level) {
				var startAt = data.filter(function(person) {
					return person.level == level;
				});
				startAt.forEach(function(start) {
					var person = _oController.findPerson(start.id);
					_oController.plotNode(person, 'self');
					_oController.plotParents(person);
				});

			});

			_oController.adjustNegatives();
		},

		onEditProfilePress: function(event) {
			_oController.EditProfile = new sap.ui.xmlfragment("GTree.fragment.EditProfile", _oController);
			sap.ui.getCore().byId("button_Dialog_Edit_Save").attachPress(_oController.onRefreshDialog_Close);
			sap.ui.getCore().byId("button_Dialog_Edit_Close").attachPress(_oController.onRefreshDialog_Close);
			_oController.EditProfile.open();
		},

		onRefreshDialog_Close: function() {
			if (_oController.EditProfile) {
				_oController.EditProfile.close();
				_oController.EditProfile.destroyContent();
				_oController.EditProfile.destroy();
			}
		},

		saveEdit: function() {

			sap.ui.getCore().byId("editDialog").close();

		},
		
		plotParents: function(start) {
			if (!start) {
				return;
			}
			start.parents.reduce(function(previousId, currentId) {
				var previousParent = _oController.findPerson(previousId),
					currentParent = _oController.findPerson(currentId);
				_oController.plotNode(currentParent, 'parents', start, start.parents.length);
				if (previousParent) {
					_oController.plotConnector(previousParent, currentParent, 'partners');
				}
				_oController.plotConnector(start, currentParent, 'parents');
				_oController.plotParents(currentParent);
				return currentId;
			}, 0);
		},

		plotNode: function() {
			var person = arguments[0],
				personType = arguments[1],
				relative = arguments[2],
				numberOfParents = arguments[3],
				node = _oController.get(person.id),
				relativeNode, element = {},
				thisLevel, exists;
			if (node) {
				return;
			}
			node = document.createElement('div');
			node.id = person.id;
			node.classList.add('node');
			node.classList.add('asset');
			node.textContent = person.name;
			node.setAttribute('data-level', person.level);
			node.addEventListener("click",_oController.onEditProfilePress);
			//function() {_oController.onNodePress(node);}
			thisLevel = _oController.findLevel(person.level);
			if (!thisLevel) {
				thisLevel = {
					'level': person.level,
					'top': startTop
				};
				levelMap.push(thisLevel);
			}

			if (personType == 'self') {
				node.style.left = startLeft + 'px';
				node.style.top = thisLevel.top + 'px';
			} else {
				relativeNode = _oController.get(relative.id);
			}
			if (personType == 'partners') {
				node.style.left = (parseInt(relativeNode.style.left) + size + (gap * 2)) + 'px';
				node.style.top = parseInt(relativeNode.style.top) + 'px';
			}
			if (personType == 'children') {
				node.style.left = (parseInt(relativeNode.style.left) - size) + 'px';
				node.style.top = (parseInt(relativeNode.style.top) + size + gap) + 'px';
			}
			if (personType == 'parents') {
				if (numberOfParents == 1) {
					node.style.left = parseInt(relativeNode.style.left) + 'px';
					node.style.top = (parseInt(relativeNode.style.top) - gap - size) + 'px';
				} else {
					node.style.left = (parseInt(relativeNode.style.left) - size) + 'px';
					node.style.top = (parseInt(relativeNode.style.top) - gap - size) + 'px';
				}
			}
			while (exists = _oController.detectCollision(node)) {
				node.style.left = (exists.left + size + (gap * 2)) + 'px';
			}

			if (thisLevel.top > parseInt(node.style.top)) {
				_oController.updateLevel(person.level, 'top', parseInt(node.style.top));
			}

			element.id = node.id;
			element.left = parseInt(node.style.left);
			element.top = parseInt(node.style.top);
			elements.push(element);
			tree.getDomRef().appendChild(node);
		},

		/* Helper Functions */
		get: function(id) {
			return document.getElementById(id);
		},

		findPerson: function(id) {
			var element = data.filter(function(elem) {
				return elem.id == id;
			});
			return element.pop();
		},

		findLevel: function(level) {
			var element = levelMap.filter(function(elem) {
				return elem.level == level;
			});
			return element.pop();
		},

		updateLevel: function(id, key, value) {
			levelMap.forEach(function(level) {
				if (level.level === id) {
					level[key] = value;
				}
			});
		},

		detectCollision: function(node) {
			var element = elements.filter(function(elem) {
				var left = parseInt(node.style.left);
				return ((elem.left == left || (elem.left < left && left < (elem.left + size + gap))) && elem.top == parseInt(node.style.top));
			});
			return element.pop();
		},

		adjustNegatives: function() {
			var allNodes = document.querySelectorAll('div.asset'),
				minTop = startTop,
				diff = 0;
			for (var i = 0; i < allNodes.length; i++) {
				if (parseInt(allNodes[i].style.top) < minTop) {
					minTop = parseInt(allNodes[i].style.top);
				}
			};
			if (minTop < startTop) {
				diff = Math.abs(minTop) + gap;
				for (var i = 0; i < allNodes.length; i++) {
				allNodes[i].style.top = parseInt(allNodes[i].style.top) + diff + 'px';
				}
			}
		},

		plotConnector: function(source, destination, relation) {
			var connector = document.createElement('div'),
				orientation, comboId, comboIdInverse, start, stop,
				x1, y1, x2, y2, length, angle, transform;
			// We do not plot a connector if already present
			comboId = source.id + '-' + destination.id;
			comboIdInverse = destination.id + '-' + source.id;
			if (document.getElementById(comboId)) {
				return;
			}
			if (document.getElementById(comboIdInverse)) {
				return;
			}

			connector.id = comboId;
			orientation = relation == 'partners' ? 'h' : 'v';
			connector.classList.add('asset');
			connector.classList.add('connector');
			connector.classList.add(orientation);
			start = _oController.get(source.id);
			stop = _oController.get(destination.id);
			if (relation == 'partners') {
				x1 = parseInt(start.style.left) + size;
				y1 = parseInt(start.style.top) + (size / 2);
				x2 = parseInt(stop.style.left);
				y2 = parseInt(stop.style.top);
				length = (x2 - x1) + 'px';

				connector.style.width = length;
				connector.style.left = x1 + 'px';
				connector.style.top = y1 + 'px';
				// Avoid collision moving down
				while (_oController.exists = _oController.detectConnectorCollision(connector)) {
					connector.style.top = (parseInt(_oController.exists.style.top) + 4) + 'px';
				}
			}
			if (relation == 'parents') {
				x1 = parseInt(start.style.left) + (size / 2);
				y1 = parseInt(start.style.top);
				x2 = parseInt(stop.style.left) + (size / 2);
				y2 = parseInt(stop.style.top) + (size - 2);

				length = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
				angle = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
				transform = 'rotate(' + angle + 'deg)';

				connector.style.width = length + 'px';
				connector.style.left = x1 + 'px';
				connector.style.top = y1 + 'px';
				connector.style.transform = transform;
			}
			tree.getDomRef().appendChild(connector);
		},

		detectConnectorCollision: function(connector) {
			var connectors = [].slice.call(document.querySelectorAll('div.connector.h'));
			var element = connectors.filter(function(elem) {
				return ((elem.style.left == connector.style.left) && (elem.style.top == connector.style.top));
			});
			return element.pop();
		}

	});
});