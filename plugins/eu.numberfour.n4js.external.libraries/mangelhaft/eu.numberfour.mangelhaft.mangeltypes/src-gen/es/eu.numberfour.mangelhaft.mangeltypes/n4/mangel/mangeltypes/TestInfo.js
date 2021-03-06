(function(System) {
	'use strict';
	System.register([], function($n4Export) {
		var TestInfo;
		TestInfo = function TestInfo(spec) {
			this.origin = spec && 'origin' in spec ? spec.origin : undefined;
			this.fqn = spec && 'fqn' in spec ? spec.fqn : undefined;
			this.module = spec && 'module' in spec ? spec.module : undefined;
			this.testMethods = spec && 'testMethods' in spec ? spec.testMethods : undefined;
			if (spec) {}
		};
		$n4Export('TestInfo', TestInfo);
		return {
			setters: [],
			execute: function() {
				$makeClass(TestInfo, Object, [], {
					origin: {
						value: undefined,
						writable: true
					},
					fqn: {
						value: undefined,
						writable: true
					},
					module: {
						value: undefined,
						writable: true
					},
					testMethods: {
						value: undefined,
						writable: true
					}
				}, {}, function(instanceProto, staticProto) {
					var metaClass = new N4Class({
						name: 'TestInfo',
						origin: 'eu.numberfour.mangelhaft.mangeltypes',
						fqn: 'n4.mangel.mangeltypes.TestInfo.TestInfo',
						n4superType: N4Object.n4type,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4DataField({
								name: 'origin',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'fqn',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'module',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'testMethods',
								isStatic: false,
								annotations: []
							}),
							new N4Method({
								name: 'constructor',
								isStatic: false,
								jsFunction: instanceProto['constructor'],
								annotations: []
							})
						],
						consumedMembers: [],
						annotations: []
					});
					return metaClass;
				});
			}
		};
	});
})(typeof module !== 'undefined' && module.exports ? require('n4js-node/index').System(require, module) : System);
//# sourceMappingURL=TestInfo.map
