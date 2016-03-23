package allen.com.rsstest.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Allen on 2015/5/26.
 */
public class MagnetFilePojo {

    private String fileName ;
    private String fileSize ;
    private String fileMagnet ;
    private String fileUrl ;

    public MagnetFilePojo() {
    }

    public MagnetFilePojo(String fileMagnet, String fileName, String fileSize, String fileUrl) {
        this.fileMagnet = trimAllBlank(fileMagnet);
        this.fileName = trimAllBlank(fileName);
        this.fileSize = trimAllBlank(fileSize);
        this.fileUrl = trimAllBlank(fileUrl);
    }

    private String trimAllBlank(String s){
        String ss = "";
        if (s == ""){
            return ss;
        }
        Pattern p = Pattern.compile("\t|\r|\n");
        Matcher m = p.matcher(s);
        ss = m.replaceAll("").trim();
        return ss;
    }

    public String getFileMagnet() {
        return fileMagnet;
    }

    public void setFileMagnet(String fileMagnet) {
        this.fileMagnet = trimAllBlank(fileMagnet);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = trimAllBlank(fileName);
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = trimAllBlank(fileSize);
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = trimAllBlank(fileUrl);
    }

    @Override
    public String toString() {
        return fileMagnet+fileName+fileSize+fileUrl;
    }

}
