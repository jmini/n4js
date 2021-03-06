(function(System) {
	'use strict';
	System.register([], function($n4Export) {
		var replacer, truncate, AssertionError;
		replacer = function replacer(key, value) {
			if (value === undefined) {
				return '' + value;
			}
			if (typeof value === 'number' && (isNaN(value) || !isFinite(value))) {
				return (value).toString();
			}
			if (typeof value === 'function' || $instanceof(value, RegExp)) {
				return (value).toString();
			}
			return value;
		};
		truncate = function truncate(s, n) {
			if (typeof s == 'string') {
				return s.length < n ? s : s.slice(0, n);
			} else {
				return s;
			}
		};
		AssertionError = function AssertionError(spec) {
			spec = spec || {};
			let stackHolder = {
				stack: ""
			}, msg = spec.message ? spec.message : "";
			;
			var err = new Error(msg);
			this.message = err.message;
			this.name = this.constructor.n4type.name;
			Object.defineProperty(this, 'stack', { get: function() { return err.stack; }, set: function(value) { err.stack = value; } });
			this.actual = undefined;
			this.expected = undefined;
			this.operator = undefined;
			this.stackStartFunction = undefined;
			this.name = "AssertionError";
			const Er = Error;
			if (!spec.stack) {
				if (Er.captureStackTrace && spec.stackStartFunction) {
					Er.captureStackTrace(stackHolder, spec.stackStartFunction);
				} else {
					try {
						let err = new Error();
						stackHolder.stack = err.stack.toString();
					} catch(e) {}
				}
			}
			if (typeof stackHolder.stack === "string") {
				spec.stack = stackHolder.stack.split("\n");
			}
			this.stack = spec.stack.join("\n");
			this.actual = spec.actual;
			this.expected = spec.expected;
			this.operator = spec.operator;
			this.stackStartFunction = spec.stackStartFunction;
		};
		$n4Export('AssertionError', AssertionError);
		return {
			setters: [],
			execute: function() {
				$makeClass(AssertionError, Error, [], {
					toString: {
						value: function toString___n4() {
							let str = "";
							try {
								str += this.name + ": ";
								if (this.message) {
									str += this.message;
								} else {
									str += [
										truncate(JSON.stringify(this.actual, replacer), 128),
										this.operator,
										truncate(JSON.stringify(this.expected, replacer), 128)
									].join(' ');
								}
							} catch(ex) {
								console.log("could not provide helpful assertion error message because of error", ex);
							}
							return str;
						}
					},
					actual: {
						value: undefined,
						writable: true
					},
					expected: {
						value: undefined,
						writable: true
					},
					operator: {
						value: undefined,
						writable: true
					},
					stackStartFunction: {
						value: undefined,
						writable: true
					}
				}, {}, function(instanceProto, staticProto) {
					var metaClass = new N4Class({
						name: 'AssertionError',
						origin: 'eu.numberfour.mangelhaft.assert',
						fqn: 'n4.mangel.assert.AssertionError.AssertionError',
						n4superType: undefined,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4DataField({
								name: 'actual',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'expected',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'operator',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'stackStartFunction',
								isStatic: false,
								annotations: []
							}),
							new N4Method({
								name: 'constructor',
								isStatic: false,
								jsFunction: instanceProto['constructor'],
								annotations: []
							}),
							new N4Method({
								name: 'toString',
								isStatic: false,
								jsFunction: instanceProto['toString'],
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
//# sourceMappingURL=AssertionError.map
