sap.ui.define(['jquery.sap.global',
		'sap/ui/core/mvc/Controller',
		'sap/ui/model/json/JSONModel',
		'sap/ui/Device'
	],

	function(jQuery, Controller, JSONModel, Device) {
		"use strict";
		var _oController;
		var _oView;
		var _oGraph;
		var _oModel;
		var _oRoot;
		return Controller.extend("sap.ui.layout.sample.DynamicSideContentProduct.DynamicSideContent", {
			onInit: function() {
				this._oDSC = this.byId("DynamicSideContent");
				this._showSideContentButton = this.byId("showSideContentButton");
				_oController = this;
				_oView = _oController.getView();
				_oGraph = _oController.byId("graph");
				_oModel = new JSONModel({

		"nodes": [{
			"key": 0,
			"title": "José",
			"icon": "sap-icon://customer",
			"gender": "Male",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 1,
			"title": "Bárbara",
			"icon": "sap-icon://customer",
			"email": "a@b.com",
			"gender": "Female",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 2,
			"title": "Maria",
			"icon": "sap-icon://customer",
			"group": "S2",
			"email": "a@b.com",
			"gender": "Female",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 3,
			"title": "Carlos",
			"icon": "sap-icon://customer",
			"group": "S2",
			"email": "a@b.com",
			"gender": "Male",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 4,
			"title": "Marcelino",
			"icon": "sap-icon://customer",
			"group": "S1",
			"email": "a@b.com",
			"gender": "Male",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 5,
			"title": "Olivia",
			"icon": "sap-icon://customer",
			"group": "S1",
			"email": "a@b.com",
			"gender": "Female",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 6,
			"title": "Manuel",
			"icon": "sap-icon://customer",
			"group": "V1",
			"email": "a@b.com",
			"gender": "Male",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 7,
			"title": "Rosa",
			"icon": "sap-icon://customer",
			"group": "V1",
			"email": "a@b.com",
			"gender": "Female",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 8,
			"title": "Jorge",
			"icon": "sap-icon://customer",
			"email": "a@b.com",
			"gender": "Male",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 9,
			"title": "Fatima",
			"icon": "sap-icon://customer",
			"email": "a@b.com",
			"gender": "Female",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 10,
			"title": "Lucas",
			"icon": "sap-icon://customer",
			"email": "a@b.com",
			"gender": "Male",
			"birthday": "21-04-1992",
			"lname": "Sousa",
			"status": "Success"
		}, {
			"key": 11,
			"title": "Caio",
			"icon": "sap-icon://customer",
			"email": "a@b.com",
			"gender": "Male",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 12,
			"title": "Ana",
			"icon": "sap-icon://customer",
			"email": "a@b.com",
			"gender": "Female",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}, {
			"key": 13,
			"title": "Lia",
			"icon": "sap-icon://customer",
			"email": "a@b.com",
			"gender": "Female",
			"birthday": "21-04-1992",
			"lname": "Sousa"
		}],
		"lines": [{
			"from": 7,
			"to": 2
		}, {
			"from": 6,
			"to": 2
		}, {
			"from": 4,
			"to": 3
		}, {
			"from": 5,
			"to": 3
		}, {
			"from": 2,
			"to": 0
		}, {
			"from": 2,
			"to": 1
		}, {
			"from": 3,
			"to": 0
		}, {
			"from": 3,
			"to": 1
		}, {
			"from": 4,
			"to": 8
		}, {
			"from": 5,
			"to": 8
		}, {
			"from": 4,
			"to": 12
		}, {
			"from": 5,
			"to": 12
		}, {
			"from": 8,
			"to": 10
		}, {
			"from": 9,
			"to": 10
		}, {
			"from": 8,
			"to": 11
		}, {
			"from": 9,
			"to": 11
		}, {
			"from": 8,
			"to": 13
		}],
		"groups": [{
			"key": "S1",
			"title": "Sousa"
		}, {
			"key": "S2",
			"title": "Sousa"
		}, {
			"key": "V1",
			"title": "Vale"
		}, {
			"key": "S3",
			"title": "Sousa"
		}]
	});
				_oView.setModel(_oModel);
				_oGraph.getNodes().forEach(function (oNode) {
					if(oNode.getKey() == 10) {_oRoot = oNode;}
				});
					
				_oView.setModel(new JSONModel({
				name: _oRoot.getTitle(),
				email: _oController._getCustomDataValue(_oRoot, "email"),
				birthday: _oController._getCustomDataValue(_oRoot, "birthday"),
				lname: _oController._getCustomDataValue(_oRoot, "lname"),
				gender: _oController._getCustomDataValue(_oRoot, "gender")
			}), "modelo");
			},
			onAfterRendering: function() {
				var sCurrentBreakpoint = this._oDSC.getCurrentBreakpoint();
				this.updateToggleButtonState(sCurrentBreakpoint);
			},
			handleSliderChange: function(oEvent) {
				var iValue = oEvent.getParameter("value");
				this.updateControlWidth(iValue);
			},
			updateControlWidth: function(iValue) {
				var $DSCContainer = this.byId("sideContentContainer").$();
				if (iValue) {
					$DSCContainer.width(iValue + "%");
				}
			},
			handleBreakpointChangeEvent: function(oEvent) {
				var sCurrentBreakpoint = oEvent.getParameter("currentBreakpoint");
				this.updateToggleButtonState(sCurrentBreakpoint);
				this.updateShowSideContentButtonVisibility(sCurrentBreakpoint);
			},
			updateToggleButtonState: function(sCurrentBreakpoint) {
				var oToggleButton = this.byId("toggleButton");
				oToggleButton.setEnabled(sCurrentBreakpoint === "S");
			},
			updateShowSideContentButtonVisibility: function(sCurrentBreakpoint) {
				var bShowButton = !(sCurrentBreakpoint === "S" || this._oDSC.getShowSideContent());
				this._showSideContentButton.setVisible(bShowButton);
			},
			handleToggleClick: function(oEvent) {
				this.byId("DynamicSideContent").toggle();
			},
			handleSideContentHide: function(oEvent) {
				this._oDSC.setShowSideContent(false);
				this.updateShowSideContentButtonVisibility(this._oDSC.getCurrentBreakpoint());
			},
			handleSideContentShow: function(oEvent) {
				this._oDSC.setShowSideContent(true);
				this.updateShowSideContentButtonVisibility(this._oDSC.getCurrentBreakpoint());
			},
			renderGraph: function() {
				_oController._graph.attachEvent("beforeLayouting", function(oEvent) {

					_oController._graph.getNodes().forEach(function(oNode) {
						var oDetailButton, oRemoveButton;

					//	oNode.removeAllActionButtons();

					/*	// add detail link -> custom popover
						oDetailButton = new ActionButton({
							title: "Detail",
							icon: "sap-icon://person-placeholder",
							press: function(oEvent) {
								_oController._openDetail(oNode, oEvent.getParameter("buttonElement"));
							}.bind(_oController)
						});
						oNode.addActionButton(oDetailButton);
						// add remove link -> custom popover
						if (oNode.getStatus() != "Success") {
							oRemoveButton = new ActionButton({
								title: "Remove",
								icon: "sap-icon://decline",
								press: function(oEvent) {
									_oController.onRemoveDialog(oNode);
								}.bind(_oController)
							});
							oNode.addActionButton(oRemoveButton);
						}*/

					}, _oController);

				/*	_oController._graph.getGroups().forEach(function(oGroup) {
						oGroup.attachPress(oGroup, _oController.onGroupClick, _oController);
					}, _oController);*/

					_oController._graph.preventInvalidation(false);
				}.bind(_oController));

			},
			_getCustomDataValue: function (oNode, sName) {
			var aItems = oNode.getCustomData().filter(function (oData) {
				return oData.getKey() === sName;
			});

			return aItems.length > 0 && aItems[0].getValue();
		},
		nodePress: function(oEvent) {
		var array =	oEvent.getSource().getBindingContext().getPath().split("/");
		var node;
		var key = array[2];
		_oGraph.getNodes().foreach(function(oNode) {
			if(oNode.getKey() == key){ node = oNode;}
		},_oController);
		/*	var person = new JSONModel({
				name: oNode.getTitle(),
				email: _oController._getCustomDataValue(oNode, "email"),
				birthday: _oController._getCustomDataValue(oNode, "birthday"),
				lname: _oController._getCustomDataValue(oNode, "lname"),
				gender: _oController._getCustomDataValue(oNode, "gender")
			});
			_oView.getModel("modelo").setData(person);*/
		}
		});

	});