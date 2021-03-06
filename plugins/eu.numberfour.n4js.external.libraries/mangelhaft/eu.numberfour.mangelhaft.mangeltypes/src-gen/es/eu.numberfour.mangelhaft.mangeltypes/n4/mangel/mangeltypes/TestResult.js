(function(System) {
	'use strict';
	System.register([], function($n4Export) {
		var TestResult;
		TestResult = function TestResult(spec) {
			this.testStatus = spec && 'testStatus' in spec ? spec.testStatus : undefined;
			this.description = spec && 'description' in spec ? spec.description : "";
			this.message = spec && 'message' in spec ? spec.message : "";
			this.expected = spec && 'expected' in spec ? spec.expected : "";
			this.actual = spec && 'actual' in spec ? spec.actual : "";
			this.elapsedTime = spec && 'elapsedTime' in spec ? spec.elapsedTime : 0;
			this.trace = spec && 'trace' in spec ? spec.trace : [];
			if (spec) {}
		};
		$n4Export('TestResult', TestResult);
		return {
			setters: [],
			execute: function() {
				$makeClass(TestResult, Object, [], {
					testStatus: {
						value: undefined,
						writable: true
					},
					description: {
						value: undefined,
						writable: true
					},
					message: {
						value: undefined,
						writable: true
					},
					expected: {
						value: undefined,
						writable: true
					},
					actual: {
						value: undefined,
						writable: true
					},
					elapsedTime: {
						value: undefined,
						writable: true
					},
					trace: {
						value: undefined,
						writable: true
					}
				}, {}, function(instanceProto, staticProto) {
					var metaClass = new N4Class({
						name: 'TestResult',
						origin: 'eu.numberfour.mangelhaft.mangeltypes',
						fqn: 'n4.mangel.mangeltypes.TestResult.TestResult',
						n4superType: N4Object.n4type,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4DataField({
								name: 'testStatus',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'description',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'message',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'expected',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'actual',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'elapsedTime',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'trace',
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
//# sourceMappingURL=TestResult.map
