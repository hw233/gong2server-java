def spath = './';
if (args.length > 0) {
	spath = args[0];
}
println 'try to svn up ' + spath + ' ..please waiting.......';
def cmd = "svn up " + spath ;
def proc = cmd.execute();
def regex = /^C[\s]+.+$/;
def i=0;
proc.in.eachLine {l->
	i++;
	println l;
	def m = l =~ regex;
	if(m.matches()){
		println '@conflict found@';
		System.err<<'@conflict found@'<<'\n';
	}
}
if(i<2) {
	println '@no update found@';
}
