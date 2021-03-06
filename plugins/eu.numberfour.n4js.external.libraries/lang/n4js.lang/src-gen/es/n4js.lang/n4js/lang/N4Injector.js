(function(System) {
	'use strict';
	System.register([], function($n4Export) {
		var DI_PROP_NAME, InjectedTypeMeta, InjectedClassMeta, BindingInfo, ProvideMethodInfo, BinderMeta, DIComponentMeta, hasDIMeta, getInjectedClassMeta, getBinderMeta, getDIComponentMeta, _$, getOFQN, DIConfigurationError, DIUnsatisfiedBindingError, N4Injector;
		InjectedTypeMeta = {};
		$n4Export('InjectedTypeMeta', InjectedTypeMeta);
		InjectedClassMeta = {};
		$n4Export('InjectedClassMeta', InjectedClassMeta);
		BindingInfo = {};
		$n4Export('BindingInfo', BindingInfo);
		ProvideMethodInfo = {};
		$n4Export('ProvideMethodInfo', ProvideMethodInfo);
		BinderMeta = {};
		$n4Export('BinderMeta', BinderMeta);
		DIComponentMeta = {};
		$n4Export('DIComponentMeta', DIComponentMeta);
		hasDIMeta = function hasDIMeta(type) {
			return typeof (type)[DI_PROP_NAME] === typeof {};
		};
		$n4Export('hasDIMeta', hasDIMeta);
		getInjectedClassMeta = function getInjectedClassMeta(type) {
			return (type)[DI_PROP_NAME];
		};
		$n4Export('getInjectedClassMeta', getInjectedClassMeta);
		getBinderMeta = function getBinderMeta(type) {
			return (type)[DI_PROP_NAME];
		};
		$n4Export('getBinderMeta', getBinderMeta);
		getDIComponentMeta = function getDIComponentMeta(type) {
			return (type)[DI_PROP_NAME];
		};
		$n4Export('getDIComponentMeta', getDIComponentMeta);
		_$ = function _$(fn) {
			this.fn = undefined;
			this.fn = fn;
		};
		getOFQN = function getOFQN(ctor) {
			return ctor.n4type.origin + ctor.n4type.fqn;
		};
		DIConfigurationError = function DIConfigurationError(message) {
			var err = new Error(message);
			this.message = err.message;
			this.name = this.constructor.n4type.name;
			Object.defineProperty(this, 'stack', { get: function() { return err.stack; }, set: function(value) { err.stack = value; } });
		};
		$n4Export('DIConfigurationError', DIConfigurationError);
		DIUnsatisfiedBindingError = function DIUnsatisfiedBindingError(unsatisfiedBinding, message) {
			DIConfigurationError.prototype.constructor.call(this, message || '');
			this.binding = undefined;
			this.binding = unsatisfiedBinding;
		};
		$n4Export('DIUnsatisfiedBindingError', DIUnsatisfiedBindingError);
		N4Injector = function N4Injector(dicInfo, bindings, parent) {
			this.explicitBindings = undefined;
			this.scopedInstances = new Map();
			this.dicInfo = undefined;
			this.parent = undefined;
			this.dicInfo = dicInfo;
			if (parent) {
				this.parent = parent;
			}
			this.explicitBindings = bindings;
		};
		$n4Export('N4Injector', N4Injector);
		return {
			setters: [],
			execute: function() {
				DI_PROP_NAME = '$di';
				InjectedTypeMeta.$fieldInit = function InjectedTypeMeta_fieldInit(target, spec, mixinExclusion) {
					if (spec) {
						if (!(mixinExclusion.hasOwnProperty('type') || target.hasOwnProperty('type'))) {
							target.type = 'type' in spec ? spec.type : undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('name') || target.hasOwnProperty('name'))) {
							target.name = 'name' in spec ? spec.name : undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('typeVar') || target.hasOwnProperty('typeVar'))) {
							target.typeVar = 'typeVar' in spec ? spec.typeVar : undefined;
						}
					} else {
						if (!(mixinExclusion.hasOwnProperty('type') || target.hasOwnProperty('type'))) {
							target.type = undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('name') || target.hasOwnProperty('name'))) {
							target.name = undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('typeVar') || target.hasOwnProperty('typeVar'))) {
							target.typeVar = undefined;
						}
					}
				};
				InjectedTypeMeta.$methods = {};
				$makeInterface(InjectedTypeMeta, function(instanceProto, staticProto) {
					var metaClass = new N4Interface({
						name: 'InjectedTypeMeta',
						origin: 'n4js.lang',
						fqn: 'n4js.lang.N4Injector.InjectedTypeMeta',
						n4superType: undefined,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4DataField({
								name: 'type',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'name',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'typeVar',
								isStatic: false,
								annotations: []
							})
						],
						consumedMembers: [],
						annotations: []
					});
					return metaClass;
				});
				InjectedClassMeta.$fieldInit = function InjectedClassMeta_fieldInit(target, spec, mixinExclusion) {
					if (spec) {
						if (!(mixinExclusion.hasOwnProperty('scope') || target.hasOwnProperty('scope'))) {
							target.scope = 'scope' in spec ? spec.scope : undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('superType') || target.hasOwnProperty('superType'))) {
							target.superType = 'superType' in spec ? spec.superType : undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('injectCtorParams') || target.hasOwnProperty('injectCtorParams'))) {
							target.injectCtorParams = 'injectCtorParams' in spec ? spec.injectCtorParams : undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('fieldsInjectedTypes') || target.hasOwnProperty('fieldsInjectedTypes'))) {
							target.fieldsInjectedTypes = 'fieldsInjectedTypes' in spec ? spec.fieldsInjectedTypes : undefined;
						}
					} else {
						if (!(mixinExclusion.hasOwnProperty('scope') || target.hasOwnProperty('scope'))) {
							target.scope = undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('superType') || target.hasOwnProperty('superType'))) {
							target.superType = undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('injectCtorParams') || target.hasOwnProperty('injectCtorParams'))) {
							target.injectCtorParams = undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('fieldsInjectedTypes') || target.hasOwnProperty('fieldsInjectedTypes'))) {
							target.fieldsInjectedTypes = undefined;
						}
					}
				};
				InjectedClassMeta.$methods = {};
				$makeInterface(InjectedClassMeta, function(instanceProto, staticProto) {
					var metaClass = new N4Interface({
						name: 'InjectedClassMeta',
						origin: 'n4js.lang',
						fqn: 'n4js.lang.N4Injector.InjectedClassMeta',
						n4superType: undefined,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4DataField({
								name: 'scope',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'superType',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'injectCtorParams',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'fieldsInjectedTypes',
								isStatic: false,
								annotations: []
							})
						],
						consumedMembers: [],
						annotations: []
					});
					return metaClass;
				});
				BindingInfo.$fieldInit = function BindingInfo_fieldInit(target, spec, mixinExclusion) {
					if (spec) {
						if (!(mixinExclusion.hasOwnProperty('from') || target.hasOwnProperty('from'))) {
							target.from = 'from' in spec ? spec.from : undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('to') || target.hasOwnProperty('to'))) {
							target.to = 'to' in spec ? spec.to : undefined;
						}
					} else {
						if (!(mixinExclusion.hasOwnProperty('from') || target.hasOwnProperty('from'))) {
							target.from = undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('to') || target.hasOwnProperty('to'))) {
							target.to = undefined;
						}
					}
				};
				BindingInfo.$methods = {};
				$makeInterface(BindingInfo, function(instanceProto, staticProto) {
					var metaClass = new N4Interface({
						name: 'BindingInfo',
						origin: 'n4js.lang',
						fqn: 'n4js.lang.N4Injector.BindingInfo',
						n4superType: undefined,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4DataField({
								name: 'from',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'to',
								isStatic: false,
								annotations: []
							})
						],
						consumedMembers: [],
						annotations: []
					});
					return metaClass;
				});
				ProvideMethodInfo.$fieldInit = function ProvideMethodInfo_fieldInit(target, spec, mixinExclusion) {
					if (spec) {
						if (!(mixinExclusion.hasOwnProperty('to') || target.hasOwnProperty('to'))) {
							target.to = 'to' in spec ? spec.to : undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('name') || target.hasOwnProperty('name'))) {
							target.name = 'name' in spec ? spec.name : undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('args') || target.hasOwnProperty('args'))) {
							target.args = 'args' in spec ? spec.args : undefined;
						}
					} else {
						if (!(mixinExclusion.hasOwnProperty('to') || target.hasOwnProperty('to'))) {
							target.to = undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('name') || target.hasOwnProperty('name'))) {
							target.name = undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('args') || target.hasOwnProperty('args'))) {
							target.args = undefined;
						}
					}
				};
				ProvideMethodInfo.$methods = {};
				$makeInterface(ProvideMethodInfo, function(instanceProto, staticProto) {
					var metaClass = new N4Interface({
						name: 'ProvideMethodInfo',
						origin: 'n4js.lang',
						fqn: 'n4js.lang.N4Injector.ProvideMethodInfo',
						n4superType: undefined,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4DataField({
								name: 'to',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'name',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'args',
								isStatic: false,
								annotations: []
							})
						],
						consumedMembers: [],
						annotations: []
					});
					return metaClass;
				});
				BinderMeta.$fieldInit = function BinderMeta_fieldInit(target, spec, mixinExclusion) {
					if (spec) {
						if (!(mixinExclusion.hasOwnProperty('bindings') || target.hasOwnProperty('bindings'))) {
							target.bindings = 'bindings' in spec ? spec.bindings : undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('methodBindings') || target.hasOwnProperty('methodBindings'))) {
							target.methodBindings = 'methodBindings' in spec ? spec.methodBindings : undefined;
						}
					} else {
						if (!(mixinExclusion.hasOwnProperty('bindings') || target.hasOwnProperty('bindings'))) {
							target.bindings = undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('methodBindings') || target.hasOwnProperty('methodBindings'))) {
							target.methodBindings = undefined;
						}
					}
					InjectedClassMeta.$fieldInit(target, spec, mixinExclusion);
				};
				BinderMeta.$methods = {};
				$makeInterface(BinderMeta, function(instanceProto, staticProto) {
					var metaClass = new N4Interface({
						name: 'BinderMeta',
						origin: 'n4js.lang',
						fqn: 'n4js.lang.N4Injector.BinderMeta',
						n4superType: undefined,
						allImplementedInterfaces: [
							'n4js.lang.N4Injector.InjectedClassMeta'
						],
						ownedMembers: [
							new N4DataField({
								name: 'bindings',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'methodBindings',
								isStatic: false,
								annotations: []
							})
						],
						consumedMembers: [],
						annotations: []
					});
					return metaClass;
				});
				DIComponentMeta.$fieldInit = function DIComponentMeta_fieldInit(target, spec, mixinExclusion) {
					if (spec) {
						if (!(mixinExclusion.hasOwnProperty('parent') || target.hasOwnProperty('parent'))) {
							target.parent = 'parent' in spec ? spec.parent : undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('binders') || target.hasOwnProperty('binders'))) {
							target.binders = 'binders' in spec ? spec.binders : undefined;
						}
					} else {
						if (!(mixinExclusion.hasOwnProperty('parent') || target.hasOwnProperty('parent'))) {
							target.parent = undefined;
						}
						if (!(mixinExclusion.hasOwnProperty('binders') || target.hasOwnProperty('binders'))) {
							target.binders = undefined;
						}
					}
					InjectedClassMeta.$fieldInit(target, spec, mixinExclusion);
				};
				DIComponentMeta.$methods = {};
				$makeInterface(DIComponentMeta, function(instanceProto, staticProto) {
					var metaClass = new N4Interface({
						name: 'DIComponentMeta',
						origin: 'n4js.lang',
						fqn: 'n4js.lang.N4Injector.DIComponentMeta',
						n4superType: undefined,
						allImplementedInterfaces: [
							'n4js.lang.N4Injector.InjectedClassMeta'
						],
						ownedMembers: [
							new N4DataField({
								name: 'parent',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'binders',
								isStatic: false,
								annotations: []
							})
						],
						consumedMembers: [],
						annotations: []
					});
					return metaClass;
				});
				$makeClass(_$, Object, [], {
					get: {
						value: function get___n4() {
							return this.fn();
						}
					},
					fn: {
						value: undefined,
						writable: true
					}
				}, {}, function(instanceProto, staticProto) {
					var metaClass = new N4Class({
						name: '_$',
						origin: 'n4js.lang',
						fqn: 'n4js.lang.N4Injector._$',
						n4superType: N4Object.n4type,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4DataField({
								name: 'fn',
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
								name: 'get',
								isStatic: false,
								jsFunction: instanceProto['get'],
								annotations: []
							})
						],
						consumedMembers: [],
						annotations: []
					});
					return metaClass;
				});
				$makeClass(DIConfigurationError, Error, [], {}, {}, function(instanceProto, staticProto) {
					var metaClass = new N4Class({
						name: 'DIConfigurationError',
						origin: 'n4js.lang',
						fqn: 'n4js.lang.N4Injector.DIConfigurationError',
						n4superType: undefined,
						allImplementedInterfaces: [],
						ownedMembers: [],
						consumedMembers: [],
						annotations: []
					});
					return metaClass;
				});
				$makeClass(DIUnsatisfiedBindingError, DIConfigurationError, [], {
					unsatisfiedBinding: {
						get: function getUnsatisfiedBinding___n4() {
							return this.binding;
						}
					},
					binding: {
						value: undefined,
						writable: true
					}
				}, {}, function(instanceProto, staticProto) {
					var metaClass = new N4Class({
						name: 'DIUnsatisfiedBindingError',
						origin: 'n4js.lang',
						fqn: 'n4js.lang.N4Injector.DIUnsatisfiedBindingError',
						n4superType: DIConfigurationError.n4type,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4DataField({
								name: 'binding',
								isStatic: false,
								annotations: []
							}),
							new N4Method({
								name: 'constructor',
								isStatic: false,
								jsFunction: instanceProto['constructor'],
								annotations: []
							}),
							new N4Accessor({
								name: 'unsatisfiedBinding',
								getter: true,
								isStatic: false,
								annotations: []
							})
						],
						consumedMembers: [],
						annotations: []
					});
					return metaClass;
				});
				Object.defineProperty(DIUnsatisfiedBindingError, '$di', {
					value: {
						superType: DIConfigurationError,
						fieldsInjectedTypes: []
					}
				});
				$makeClass(N4Injector, Object, [], {
					create: {
						value: function create___n4(ctor) {
							var cachedInstances = new Map();
							return this.internalCreate(ctor, this, cachedInstances);
						}
					},
					internalCreate: {
						value: function internalCreate___n4(ctor, delegate, cachedInstances) {
							if (!ctor) {
								throw new DIConfigurationError('Requested injection of undefined or null ctor.');
							}
							var fqn = ctor.n4type.fqn;
							if (!fqn) {
								throw new DIConfigurationError('Cannot resolve FQN for ' + ctor + '.');
							}
							if (fqn === N4Type.of(this).fqn) {
								return this;
							}
							if (fqn === N4Provider.n4type.fqn) {
								var pMeta = Object.create(null);
								pMeta.type = ctor;
								return this.createAnonymousProvider(pMeta);
							}
							var pairInjectorBinding = this.findBinding(ctor);
							if (pairInjectorBinding) {
								var $destruct = $sliceToArrayForDestruct((pairInjectorBinding), 2), injector = $destruct[0], binder = $destruct[1];
								if (binder && injector) {
									return injector.createFromBinder(ctor, binder, delegate, cachedInstances);
								}
								throw new DIConfigurationError("Cannot obtain Injector->Binder pair");
							}
							if (this.declaredSingleton(ctor)) {
								var isBindingTarget = this.checkOwnBindingTargets(ctor);
								if (isBindingTarget) {
									return this.createNewInjected(ctor, delegate, cachedInstances);
								}
								var scopedInstance = this.findScopedInstanceInParentChain(ctor);
								if (scopedInstance) {
									return scopedInstance;
								}
								return this.getRootParent().createNewInjected(ctor, delegate, cachedInstances);
							}
							return this.createInstance(ctor, delegate, cachedInstances);
						}
					},
					checkOwnBindingTargets: {
						value: function checkOwnBindingTargets___n4(ctor) {
							if (!this.explicitBindings.size) {
								return false;
							}
							for(var bx of this.explicitBindings.values()) {
								var binderMeta = getBinderMeta(bx.constructor);
								if (binderMeta.bindings.some((function(b) {
									return b.to === ctor;
								}).bind(this)) || binderMeta.methodBindings.some((function(m) {
									return m.to === ctor;
								}).bind(this))) {
									return true;
								}
							}
							return false;
						}
					},
					findScopedInstanceInParentChain: {
						value: function findScopedInstanceInParentChain___n4(ctor) {
							var key = getOFQN(ctor);
							if (this.scopedInstances.has(key)) {
								return this.scopedInstances.get(key);
							}
							if (this.parent) {
								if (this.parent.scopedInstances.has(key)) {
									return this.parent.scopedInstances.get(key);
								}
								return this.parent.findScopedInstanceInParentChain(ctor);
							}
						}
					},
					getRootParent: {
						value: function getRootParent___n4() {
							if (this.parent) {
								return this.parent.getRootParent();
							}
							return this;
						}
					},
					createInstance: {
						value: function createInstance___n4(ctor, delegate, cachedInstances) {
							if (hasDIMeta(ctor)) {
								return this.createNewInjected(ctor, delegate, cachedInstances);
							}
							if (!hasDIMeta(ctor)) {
								return this.createNewNotInjected(ctor);
							}
							throw new DIConfigurationError('Type ' + getOFQN(ctor) + ' could not be created.');
						}
					},
					createNewNotInjected: {
						value: function createNewNotInjected___n4(ctor) {
							var typeInstance = undefined;
							typeInstance = this.createNew(ctor, []);
							if (!typeInstance) {
								throw new DIConfigurationError('Could not create valid instance for ' + getOFQN(ctor) + '.');
							}
							return typeInstance;
						}
					},
					declaredSingleton: {
						value: function declaredSingleton___n4(ctor) {
							if (hasDIMeta(ctor)) {
								return getInjectedClassMeta(ctor).scope === "Singleton";
							}
							return false;
						}
					},
					findBinding: {
						value: function findBinding___n4(ctor) {
							var key = getOFQN(ctor);
							if (this.explicitBindings.has(key)) {
								return [
									this,
									this.explicitBindings.get(key)
								];
							}
							if (this.parent) {
								return this.parent.findBinding(ctor);
							}
						}
					},
					createFromBinder: {
						value: function createFromBinder___n4(ctor, binder, delegate, cachedInstances) {
							var bm = getBinderMeta(binder.constructor);
							var annotationMappings = bm.bindings.filter((function(k) {
								return k.from === ctor;
							}).bind(this));
							if (annotationMappings.length > 1) {
								throw new Error("too many mappings for" + ctor);
							}
							if (annotationMappings.length === 1) {
								var target = annotationMappings[0].to;
								if (this === delegate) {
									if (this.scopedInstances.has(getOFQN(target))) {
										return this.scopedInstances.get(getOFQN(target));
									}
								}
								if (target === ctor) {
									return this.createInstance(ctor, delegate, cachedInstances);
								}
								return delegate.internalCreate(target, delegate, cachedInstances);
							}
							var methodMappings = bm.methodBindings.filter((function(k) {
								return k.to === ctor;
							}).bind(this));
							if (methodMappings.length > 1) {
								throw new Error("too many mappings for" + ctor);
							}
							if (methodMappings.length === 1) {
								if (this === delegate) {
									if (this.scopedInstances.has(getOFQN(ctor))) {
										return this.scopedInstances.get(getOFQN(ctor));
									}
								}
								return this.createProvided(binder, methodMappings[0], delegate, cachedInstances);
							}
							throw new DIConfigurationError("trouble resolving binding for " + ctor);
						}
					},
					createNew: {
						value: function createNew___n4(ctor, parameters) {
							if (!ctor) {
								throw new DIConfigurationError("Tried to create instance from null or undefined ctor" + ctor);
							}
							if (!ctor.n4type.isClass) {
								if (ctor.n4type.isInterface) {
									throw new DIUnsatisfiedBindingError((ctor), 'No binding for ' + getOFQN(ctor) + ' interface');
								}
								if (ctor.n4type.isEnum) {
									throw new DIConfigurationError("Tried to create instance from enum" + getOFQN(ctor));
								}
							}
							var instance = Object.create(ctor.prototype);
							instance = (ctor).apply(instance, parameters) || instance;
							return instance;
						}
					},
					createFromInjectedTypeMeta: {
						value: function createFromInjectedTypeMeta___n4(meta, delegate, cachedInstances) {
							if (!meta.type) {
								throw new DIConfigurationError("DI meta information corrupted, no type available.");
							}
							if (meta.type.n4type.fqn === N4Provider.n4type.fqn) {
								if (!meta.typeVar) {
									throw new DIConfigurationError('Cannot create provider ' + meta.type + " typeVar is " + meta.typeVar);
								}
								return this.createAnonymousProvider(meta.typeVar);
							}
							return this.internalCreate(meta.type, delegate, cachedInstances);
						}
					},
					createNewInjected: {
						value: function createNewInjected___n4(ctor, delegate, cachedInstances) {
							var ofqn = getOFQN(ctor);
							if (cachedInstances.has(ofqn)) {
								return cachedInstances.get(ofqn);
							}
							if (this.scopedInstances.has(ofqn)) {
								return this.scopedInstances.get(ofqn);
							}
							var ctorParams = this.getInjectorParams(ctor).map((function(fp) {
								return delegate.createFromInjectedTypeMeta(fp, delegate, new Map(cachedInstances));
							}).bind(this));
							var instance = this.createNew(ctor, ctorParams);
							cachedInstances.set(ofqn, instance);
							delegate.injectDataFields(instance, ctor, new Set(), delegate, cachedInstances);
							if (this.declaredSingleton(ctor)) {
								this.scopedInstances.set(ofqn, instance);
							}
							return instance;
						}
					},
					getInjectorParams: {
						value: function getInjectorParams___n4(type) {
							if (hasDIMeta(type)) {
								var meta = getInjectedClassMeta(type);
								if (meta.injectCtorParams) {
									return meta.injectCtorParams;
								}
								if (meta.superType) {
									return this.getInjectorParams(meta.superType);
								}
							}
							return [];
						}
					},
					injectDataFields: {
						value: function injectDataFields___n4(instance, type, usedNames, delegate, cachedInstances) {
							if (hasDIMeta(type)) {
								var meta = getInjectedClassMeta(type);
								meta.fieldsInjectedTypes.forEach((function(f) {
									if (!usedNames.has(f.name)) {
										usedNames.add(f.name);
										(instance)[f.name] = delegate.createFromInjectedTypeMeta(f, delegate, new Map(cachedInstances));
									}
								}).bind(this));
								if (meta.superType) {
									delegate.injectDataFields(instance, meta.superType, usedNames, delegate, cachedInstances);
								}
							}
						}
					},
					createProvided: {
						value: function createProvided___n4(binder, info, delegate, cachedInstances) {
							var params = info.args.map((function(e) {
								return this.createFromInjectedTypeMeta(e, delegate, cachedInstances);
							}).bind(this));
							return ((binder)[info.name]).apply(binder, params);
						}
					},
					createAnonymousProvider: {
						value: function createAnonymousProvider___n4(meta) {
							var injector = this;
							var f;
							if (!meta.typeVar) {
								f = function() {
									return injector.internalCreate(meta.type, injector, new Map());
								};
							} else {
								f = (function() {
									var p = injector.createAnonymousProvider(meta.typeVar);
									return function() {
										return p;
									};
								})();
							}
							return new _$(f);
						}
					},
					isInjectorOf: {
						value: function isInjectorOf___n4(dic) {
							return this.dicInfo === getOFQN(dic);
						}
					},
					isCompatibleWith: {
						value: function isCompatibleWith___n4(dic) {
							return this.isInjectorOf(dic) || N4Injector.isParentCompatible(this, dic);
						}
					},
					canBeParentOf: {
						value: function canBeParentOf___n4(dic) {
							if (!hasDIMeta(dic)) {
								return false;
							}
							var expectedParent = getDIComponentMeta(dic).parent;
							return expectedParent ? this.isCompatibleWith(expectedParent) : false;
						}
					},
					explicitBindings: {
						value: undefined,
						writable: true
					},
					scopedInstances: {
						value: undefined,
						writable: true
					},
					dicInfo: {
						value: undefined,
						writable: true
					},
					parent: {
						value: undefined,
						writable: true
					}
				}, {
					prepareBindingsMap: {
						value: function prepareBindingsMap___n4(providedBinders, expectedBindersTypes) {
							var bindings = [];
							if (expectedBindersTypes) {
								var expectedBindersMap = new Map();
								expectedBindersTypes.forEach((function(eb) {
									return expectedBindersMap.set(getOFQN(eb), eb);
								}).bind(this));
								providedBinders.forEach((function(bi) {
									if (bi) {
										var biType = bi.constructor;
										if (!expectedBindersMap.has(getOFQN(biType))) {
											throw new DIConfigurationError("provided Binder instance is not of any expected type");
										}
										bindings.push(bi);
										expectedBindersMap.delete(getOFQN(bi.constructor));
									}
								}).bind(this));
								expectedBindersMap.forEach((function(bt) {
									var _b = new (bt)();
									bindings.push(_b);
								}).bind(this));
							}
							var bindingsMap = new Map();
							bindings.forEach((function(binder) {
								var binderType = binder.constructor;
								if (hasDIMeta(binderType)) {
									var meta = getBinderMeta(binderType);
									meta.bindings.forEach((function(b) {
										return bindingsMap.set(getOFQN(b.from), binder);
									}).bind(this));
									meta.methodBindings.forEach((function(b) {
										return bindingsMap.set(getOFQN(b.to), binder);
									}).bind(this));
								}
							}).bind(this));
							return bindingsMap;
						}
					},
					expectedParent: {
						value: function expectedParent___n4(ctorDIC) {
							var parent = getDIComponentMeta(ctorDIC).parent;
							if (!parent) {
								return void (0);
							}
							return getOFQN(parent);
						}
					},
					checkParent: {
						value: function checkParent___n4(dic, parent) {
							var expectedParent = getDIComponentMeta(dic).parent;
							if (parent) {
								if (!expectedParent) {
									throw new DIConfigurationError('Injector of ' + getOFQN(dic) + ' expects no parent, but was given ' + parent.dicInfo + '.');
								}
								if (!parent.isCompatibleWith(expectedParent)) {
									throw new DIConfigurationError('Injector of ' + getOFQN(dic) + ' expects parent compatible with ' + expectedParent + ', but was given incompatible ' + parent.dicInfo + '.');
								}
							} else {
								if (expectedParent) {
									throw new DIConfigurationError('Injector of ' + getOFQN(dic) + ' parent compatible with ' + expectedParent + ', but no injector was passed.');
								}
							}
						}
					},
					getMetaData: {
						value: function getMetaData___n4(type) {
							if (!type) {
								throw new DIConfigurationError("cannot create N4Injector without DIComponent type");
							}
							if (!hasDIMeta(type)) {
								throw new DIConfigurationError("provided DIComponent did not had proper di meta information");
							}
							return getDIComponentMeta(type);
						}
					},
					of: {
						value: function of___n4(type, parent) {
							var providedBinders = Array.prototype.slice.call(arguments, 2);
							var dicMeta = N4Injector.getMetaData(type);
							N4Injector.checkParent(type, parent);
							var bindings = N4Injector.prepareBindingsMap(providedBinders, dicMeta.binders);
							return new N4Injector(getOFQN(type), bindings, parent);
						}
					},
					isParentCompatible: {
						value: function isParentCompatible___n4(injector, dic) {
							if (injector.parent) {
								var inj = injector.parent;
								return inj.isInjectorOf(dic) || N4Injector.isParentCompatible(inj, dic);
							}
							return false;
						}
					}
				}, function(instanceProto, staticProto) {
					var metaClass = new N4Class({
						name: 'N4Injector',
						origin: 'n4js.lang',
						fqn: 'n4js.lang.N4Injector.N4Injector',
						n4superType: N4Object.n4type,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4DataField({
								name: 'explicitBindings',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'scopedInstances',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'dicInfo',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'parent',
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
								name: 'create',
								isStatic: false,
								jsFunction: instanceProto['create'],
								annotations: []
							}),
							new N4Method({
								name: 'internalCreate',
								isStatic: false,
								jsFunction: instanceProto['internalCreate'],
								annotations: []
							}),
							new N4Method({
								name: 'checkOwnBindingTargets',
								isStatic: false,
								jsFunction: instanceProto['checkOwnBindingTargets'],
								annotations: []
							}),
							new N4Method({
								name: 'findScopedInstanceInParentChain',
								isStatic: false,
								jsFunction: instanceProto['findScopedInstanceInParentChain'],
								annotations: []
							}),
							new N4Method({
								name: 'getRootParent',
								isStatic: false,
								jsFunction: instanceProto['getRootParent'],
								annotations: []
							}),
							new N4Method({
								name: 'createInstance',
								isStatic: false,
								jsFunction: instanceProto['createInstance'],
								annotations: []
							}),
							new N4Method({
								name: 'createNewNotInjected',
								isStatic: false,
								jsFunction: instanceProto['createNewNotInjected'],
								annotations: []
							}),
							new N4Method({
								name: 'declaredSingleton',
								isStatic: false,
								jsFunction: instanceProto['declaredSingleton'],
								annotations: []
							}),
							new N4Method({
								name: 'findBinding',
								isStatic: false,
								jsFunction: instanceProto['findBinding'],
								annotations: []
							}),
							new N4Method({
								name: 'createFromBinder',
								isStatic: false,
								jsFunction: instanceProto['createFromBinder'],
								annotations: []
							}),
							new N4Method({
								name: 'createNew',
								isStatic: false,
								jsFunction: instanceProto['createNew'],
								annotations: []
							}),
							new N4Method({
								name: 'createFromInjectedTypeMeta',
								isStatic: false,
								jsFunction: instanceProto['createFromInjectedTypeMeta'],
								annotations: []
							}),
							new N4Method({
								name: 'createNewInjected',
								isStatic: false,
								jsFunction: instanceProto['createNewInjected'],
								annotations: []
							}),
							new N4Method({
								name: 'getInjectorParams',
								isStatic: false,
								jsFunction: instanceProto['getInjectorParams'],
								annotations: []
							}),
							new N4Method({
								name: 'injectDataFields',
								isStatic: false,
								jsFunction: instanceProto['injectDataFields'],
								annotations: []
							}),
							new N4Method({
								name: 'createProvided',
								isStatic: false,
								jsFunction: instanceProto['createProvided'],
								annotations: []
							}),
							new N4Method({
								name: 'createAnonymousProvider',
								isStatic: false,
								jsFunction: instanceProto['createAnonymousProvider'],
								annotations: []
							}),
							new N4Method({
								name: 'prepareBindingsMap',
								isStatic: true,
								jsFunction: staticProto['prepareBindingsMap'],
								annotations: []
							}),
							new N4Method({
								name: 'expectedParent',
								isStatic: true,
								jsFunction: staticProto['expectedParent'],
								annotations: []
							}),
							new N4Method({
								name: 'checkParent',
								isStatic: true,
								jsFunction: staticProto['checkParent'],
								annotations: []
							}),
							new N4Method({
								name: 'getMetaData',
								isStatic: true,
								jsFunction: staticProto['getMetaData'],
								annotations: []
							}),
							new N4Method({
								name: 'of',
								isStatic: true,
								jsFunction: staticProto['of'],
								annotations: []
							}),
							new N4Method({
								name: 'isInjectorOf',
								isStatic: false,
								jsFunction: instanceProto['isInjectorOf'],
								annotations: []
							}),
							new N4Method({
								name: 'isCompatibleWith',
								isStatic: false,
								jsFunction: instanceProto['isCompatibleWith'],
								annotations: []
							}),
							new N4Method({
								name: 'isParentCompatible',
								isStatic: true,
								jsFunction: staticProto['isParentCompatible'],
								annotations: []
							}),
							new N4Method({
								name: 'canBeParentOf',
								isStatic: false,
								jsFunction: instanceProto['canBeParentOf'],
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
//# sourceMappingURL=N4Injector.map
