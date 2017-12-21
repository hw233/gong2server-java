var gm_cmd = require('./gm_cmd');
var GongCommonNotify = com.gamejelly.gong.utils.GongCommonNotify;

exports.main = function() {
	gm_cmd.init();

	process.on('handleGmList', function(rpcResult, owner, filter) {
		var cmds = gm_cmd.filter(owner.getAvatarModel().getUserGroup(), filter);

		rpcResult.result(js2JavaObject(cmds))
	});

	process.on('handleGm', function() {
		var args = Array.from(arguments);
		var rpcResult = args.shift();
		var owner = args.shift();
		var op = args.shift();
		args = [ owner ].concat(args);
		var ret = gm_cmd.cmd(owner.getAvatarModel().getUserGroup(), op, args);

		rpcResult.result(js2JavaObject(ret));
	});

}