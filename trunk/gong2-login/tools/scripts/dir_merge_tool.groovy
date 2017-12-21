package tools
import org.tmatesoft.svn.core.wc.*
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;
class DirMergeTool {
    def count = 0;
    def srcRoot;
    def destRoot;
    def cmdList = [];
    def slash = '/';
    def exclude = /(?:.*\.svn|.*\.bak|.*\.db|.*\.txt|.*\.mex|login(?:Mail|Circle)?\.html|popoblogshow\.html|friend\.html|search\.html)/;
    def expath = /.+\/(webroot_cur|webroot_rc|compressed)\/(?:style\/common\/index\/(?:image|css)(?:\/.*)?|js\/index(?:\/.*)?|pub(?:\/.*)?|style\/css\/fixed(?:\/.*)?)/
    def excmp = /.*\.jar|.*\.exe|.*\.msi/;
    def wcClient;
    
    static void main(args) {
        if (args.length != 2) {
            println 'usage:cmd <src> <dest>';
            return;
        }

        def tool = new DirMergeTool();
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager clientManager = SVNClientManager.newInstance(options);
        tool.wcClient = clientManager.getWCClient();
        
	def os = System.getProperty("os.name").toLowerCase();
        tool.destRoot = args[1];
        //'/home/space/cur_branch/release/webroot_rc';
        if (tool.destRoot.endsWith("/"))
            tool.destRoot = tool.destRoot.substring(0, tool.destRoot.length() - 1);
        tool.srcRoot = args[0];
        if (tool.srcRoot.endsWith("/"))
            tool.srcRoot = tool.srcRoot.substring(0, tool.srcRoot.length() - 1);
        //'/home/space/cur_branch/trunk/compressed';
        if (os.contains("windows")) {
            tool.slash = '\\';
        }
        try {
            tool.compare('/');
        } catch (e) {
            e.printStackTrace();
        }
        tool.execute();
    }

    void execute() {
        println '========================================================';
        println '===================begin execute========================';
        println '========================================================';
        cmdList.each {
            println 'executing............ ' + it;
            def proc = it.execute();
            proc.in.eachLine {}
        }
    }

    void compare(path) {
        def df = new File(destRoot + path);
        if (!df.canRead())
            return;
        def sf = new File(srcRoot + path);
        def srcMap = [:];
        def fileList = [];
        sf.eachFile {f ->
            if (isSpecFile(f))
                return;
            def key = f.name;
            def stt = new FileState();
            stt.file = f;
            srcMap[key] = stt;
            if (f.isDirectory())
                fileList.add(f.name + '/');
        }

        df.eachFile {f ->
            count++;
            if (count % 100 == 0)
                println count + ' files processed~~'
            if (isSpecFile(f))
                return;
            def key = f.name;
            //println 'dest file ++++++++++++++'+ key;
            def stt = srcMap[key];
            if (stt == null) {
                //file not exist in new version,delete it
                deleteFile(f.getAbsolutePath());
            }
            else {
                if (!f.isDirectory() && !isFileEqual(f, stt.file))
                    updateFile(f.getAbsolutePath(), stt.file.getAbsolutePath());
                srcMap.remove(key);
                /*
                  if(f.isDirectory())
                      println 'key is ..........' + key;
                  */
            }
        }

        srcMap.each {
            if (it.value.file.isDirectory())
                createDir(df.getAbsolutePath() + slash + it.value.file.name)
            else
                updateFile(df.getAbsolutePath(), it.value.file.getAbsolutePath());
            //println 'add it.value.file.getAbsolutePath()';
        }

        fileList.each {
            compare(path + it);
        }
    }

    boolean isFileEqual(f1, f2) {
        //if (!needsCmp(f1))
        //    return false;
        def f1Checksum = null;
        def f2Checksum = calcChecksum(f2);
        def info = wcClient.doInfo(f1, SVNRevision.BASE);
        if(info == null)
            return false;
        if(info.checksum == f2Checksum)
            return true;

        return false;
    }

    String calcChecksum(f){
        def ab = new byte[4096];
        def md = MessageDigest.getInstance("MD5");
        def len = -1;
        def mdRes;
        f.withInputStream{i ->
            while((len = i.read(ab)) != -1){
                md.update(ab, 0, len);
            }
            mdRes = md.digest();
        }
        return new String(Hex.encodeHex(mdRes));
    }

    boolean needsCmp(f) {
        def m = f.name =~ excmp;
        if (m.matches())
            return false;
        return true;
    }

    boolean isSpecFile(f) {
        //println f.path;
        /*
        if(f.name.startsWith('.svn')
                ||f.path.contains('style/common/index/image')
                ||f.path.contains('/style/common/index/css')
                ||f.path.contains('/js/index')
                ||f.name.equals('login.html')
                ||f.name.equals('loginMail.html')
                ||f.name.equals('loginCircle.html'))
              return true;
          */
        def mp = (f.path + '/') =~ expath;
        if (mp.matches())
            return true;
        def m = f.name =~ exclude;
        if (m.matches())
            return true;

        return false;

    }

    void createDir(path) {
        def cmd = "mkdir ${path}";
        //cmdList << cmd;
        cmd.execute().in.eachLine {}
        println cmd + " queued";
    }

    void deleteFile(path) {
        def cmd = "rm ${path} -rf";
        cmdList << cmd;
        println cmd + " queued";
    }

    void updateFile(destPath, srcPath) {
        def cmd = "cp ${srcPath} ${destPath} -f";
        cmdList << cmd;
        println cmd + " queued";
    }

}

class FileState {
    def file;
    def flag = 0; //0:unmodified 1:updated 2:missing
}
