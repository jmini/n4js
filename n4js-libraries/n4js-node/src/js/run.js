/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
/*eslint-disable no-process-exit */

(function() {
    "use strict";

    exports.runWith = function(options, safeExit) {
        if (options.ideExecData) { // additionally mixin all command line args as options
            process.argv.slice(2).forEach(function(v) {
                var i = v.indexOf("=");
                options[i < 0 ? v : v.substring(0, i)] = i < 0 ? true : v.substring(i + 1);
            });
        }

        return new Promise(function(resolve) {
            options = require("./rt/node-bootstrap.js").installN4JSRuntime(options);

            var CJSLoader = require("./rt/node-cjs-loader-polyfill.js").Loader;
            var sys = options.cjs /* enforces commonJS */ ? new CJSLoader(require, module) : global.System;

            resolve(n4.handleMainModule(sys).then(function(res) {
                if (options.debug) {
                    console.log("## node.js exec done.");
                }
                return res;
            }));
        }).catch(function(err) {
            console.error(err.stack || err);
            if (safeExit) {
                process.exit(1);
            } else {
                throw err;
            }
        });
    };
})();
