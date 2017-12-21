var util = require('util');

if (typeof $GM_CMDS == "undefined") {
	var $GM_CMDS = {};
}

function init() {
	require('./cmd_com').init()
}

function filter(group, f) {
	var ret = [];
	var gm_cmd;
	var desc
	for (op in $GM_CMDS) {
		gm_cmd = $GM_CMDS[op];
		if (gm_cmd.group > group) {
			continue;
		}
		if (f == null || f.trim() == "" || gm_cmd.desc.toLowerCase().indexOf(f.toLowerCase()) > -1) {
			ret.push(gm_cmd.desc);
		}
	}
	return ret;
}

function cmd(group, op, args) {
	var gm_cmd = $GM_CMDS[op];
	if (gm_cmd && gm_cmd.callback && gm_cmd.group <= group) {
		//权限判断
		try {
			var r = gm_cmd.callback.apply(null, args);
			if (r == null) {
				return '['+ op + ']' + '执行成功';
			} else {
				return '['+ op + ']' + util.inspect(r);
			}
		} catch (err) {
			console.error(err);
			return '['+ op + ']' + '执行失败: ' + err;
		}
	} else {
		return '['+ op + ']' + '不存在';
	}
}

function reg(callback, op, params, desc, group) {
	if (group == null) group = com.gamejelly.gong.utils.GongConstants.USER_GROUP_DEV;
	desc = op + "#" + desc + "#" + params;
	$GM_CMDS[op] = {callback:callback, desc:desc, group:group};
}

exports.init = init;
exports.filter = filter;
exports.cmd = cmd;
exports.reg = reg;
